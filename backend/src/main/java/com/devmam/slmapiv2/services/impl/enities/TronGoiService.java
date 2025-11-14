package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.FileType;
import com.devmam.slmapiv2.dto.request.entities.ThongTinTronGoiCoSoCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.TronGoiCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.VatTuTronGoiCreatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.TronGoiDto;
import com.devmam.slmapiv2.entities.*;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.TronGoiMapper;
import com.devmam.slmapiv2.services.JwtService;
import com.devmam.slmapiv2.services.MinioService;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TronGoiService extends BaseServiceImpl<TronGoi, Integer> {


    @Autowired
    private TepTinService tepTinService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MinioService minioService;

    @Autowired
    private VatTuService vatTuService;

    @Autowired
    private CoSoService coSoService;

    @Autowired
    private NhomTronGoiService nhomTronGoiService;

    @Autowired
    private VatTuTronGoiService vatTuTronGoiService;

    @Autowired
    private TronGoiMapper tronGoiMapper;

    @Autowired
    private EntityManager entityManager;

    public TronGoiService(JpaRepository<TronGoi, Integer> repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    public ResponseEntity<ResponseData<List<TronGoiDto>>> create(TronGoiCreatingDto dto, MultipartFile file) {


        List<TronGoiDto> results = new ArrayList<>();

        for (ThongTinTronGoiCoSoCreatingDto tronGoiCoSo : dto.getTronGoiCoSos()) {
            TronGoi tronGoi = TronGoiCreatingDto.toEntity(dto);
            Optional<CoSo> coSo = coSoService.getOne(tronGoiCoSo.getCoSoId());
            if (coSo.isEmpty()) {
                throw new CommonException("Không tìm thấy cơ sở id: " + tronGoiCoSo.getCoSoId());
            }
            Optional<NhomTronGoi> nhomTronGoi = nhomTronGoiService.getOne(dto.getNhomTronGoiId());
            if (nhomTronGoi.isEmpty()) {
                throw new CommonException("Không tìm thấy nhóm trọn gói id:" + dto.getNhomTronGoiId());
            }

            tronGoi.setCoSo(coSo.get());
            tronGoi.setNhomTronGoi(nhomTronGoi.get());
            tronGoi.setSanLuongToiThieu(tronGoiCoSo.getSanLuongToiThieu());
            tronGoi.setSanLuongToiDa(tronGoiCoSo.getSanLuongToiDa());
            tronGoi = create(tronGoi);

            for (VatTuTronGoiCreatingDto vatTuTronGoiDto : dto.getVatTuTronGois()) {
                Optional<VatTu> vatTu = vatTuService.getOne(vatTuTronGoiDto.getVatTuId());
                if (vatTu.isEmpty()) {
                    throw new CommonException("Không tìm thấy vật tư id: " + vatTuTronGoiDto.getVatTuId());
                }
                vatTuTronGoiService.create(VatTuTronGoi.builder()
                        .tronGoi(tronGoi)
                        .vatTu(vatTu.get())
                        .moTa(vatTuTronGoiDto.getMoTa())
                        .soLuong(vatTuTronGoiDto.getSoLuong())
                        .gia(vatTuTronGoiDto.getGia())
                        .gm(vatTuTronGoiDto.getGm())
                        .duocBaoHanh(vatTuTronGoiDto.getDuocBaoHanh())
                        .trangThai(vatTuTronGoiDto.getTrangThai())
                        .build());
            }

            try {
                String objectName = minioService.upload(file);
                TepTin creatingTepTin = tepTinService.create(
                        TepTin.builder()
                                .tenTepGoc(tronGoi.getTen()+"_"+coSo.get().getMa())
                                .tenTaiLen(tronGoi.getTen()+"_"+coSo.get().getMa())
                                .tenLuuTru(objectName)
                                .duongDan(minioService.getPublicUrl(objectName))
                                .loaiTepTin(FileType.IMAGE.toString())
                                .duoiTep(minioService.getObjectInfo(objectName).getUserMetadata().get("file-extension"))
                                .build()
                );
                tronGoi.setTepTin(creatingTepTin);
                tronGoi = update(tronGoi.getId(), tronGoi);
                results.add(tronGoiMapper.toDto(tronGoi));
            } catch (Exception e) {
                log.error("Lỗi tạo tệp tin cho trọn gói: {}", dto.getTen(), e);
                throw new RuntimeException("Lỗi tạo tệp tin cho trọn gói: " + dto.getTen(), e);
            }
        }
        return ResponseEntity.ok(
                ResponseData.<List<TronGoiDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(results)
                        .build()
        );
    }
}
