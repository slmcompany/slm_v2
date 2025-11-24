package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.FileType;
import com.devmam.slmapiv2.dto.request.entities.*;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.TronGoiDto;
import com.devmam.slmapiv2.entities.*;
import com.devmam.slmapiv2.entities.commons.GiaInfo;
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
import java.util.Date;
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
            tronGoi.setTongGia(dto.getTongGiaMienBac());
            if (coSo.get().getMa().equals("HCM")) {
                tronGoi.setTongGia(dto.getTongGiaMienNam());
            }
            tronGoi = create(tronGoi);

            for (VatTuTronGoiCreatingDto vatTuTronGoiDto : dto.getVatTuTronGois()) {
                Optional<VatTu> vatTu = vatTuService.getOne(vatTuTronGoiDto.getVatTuId());
                if (vatTu.isEmpty()) {
                    throw new CommonException("Không tìm thấy vật tư id: " + vatTuTronGoiDto.getVatTuId());
                }
                List<ThongTinGia> dsThongTinGia = vatTu.get().getThongTinGias();
                if (dsThongTinGia.isEmpty()) {
                    throw new CommonException("Giá của vật tư id:" + vatTu.get().getId() + " chưa được khởi tạo");
                }
                Double giaBanTheoKhuVuc = 0.0;
                boolean daChinhGia = false;
                for (GiaInfo giaInfo : dsThongTinGia.get(dsThongTinGia.size() - 1).getDsGia()) {
                    if (giaInfo.getMaCoSo().equals(coSo.get().getMa())) {
                        giaBanTheoKhuVuc = giaInfo.getGiaBan() + 0.0;
                        daChinhGia = true;
                        break;
                    }
                }
                if (!daChinhGia) {
                    giaBanTheoKhuVuc = dsThongTinGia.get(dsThongTinGia.size() - 1).getDsGia().get(0).getGiaBan() + 0.0;
                }
                vatTuTronGoiService.create(VatTuTronGoi.builder()
                        .tronGoi(tronGoi)
                        .vatTu(vatTu.get())
                        .moTa(vatTuTronGoiDto.getMoTa())
                        .soLuong(vatTuTronGoiDto.getSoLuong())
                        .gia(giaBanTheoKhuVuc)
                        .gm(vatTuTronGoiDto.getGm())
                        .duocBaoHanh(vatTuTronGoiDto.getDuocBaoHanh())
                        .duocXem(vatTuTronGoiDto.getDuocXem())
                        .trangThai(vatTuTronGoiDto.getTrangThai())
                        .build());
            }

            try {
                String objectName = minioService.upload(file, "tron_goi_" + tronGoi.getTen() + "_" + coSo.get().getMa());
                TepTin creatingTepTin = tepTinService.create(
                        TepTin.builder()
                                .tenTepGoc("tron_goi_" + tronGoi.getTen() + "_" + coSo.get().getMa())
                                .tenTaiLen("tron_goi_" + tronGoi.getTen() + "_" + coSo.get().getMa())
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

    @Transactional
    public ResponseEntity<ResponseData<TronGoiDto>> update(TronGoiUpdatingDto dto, MultipartFile file) {
        Optional<TronGoi> tronGoiFinding = getOne(dto.getId());

        if (tronGoiFinding.isEmpty()) {
            throw new CommonException("Không tìm thấy trọn gói id: " + dto.getId());
        }
        TronGoi tronGoi = tronGoiFinding.get();
        List<VatTuTronGoiUpdatingDto> dsVatTuTronGoiUpdatingDtos = dto.getVatTuTronGois();
        List<VatTuTronGoi> dsVatTuTronGoi = tronGoi.getVatTuTronGois();

        for (VatTuTronGoi vatTuTronGoi : dsVatTuTronGoi) {
            for (VatTuTronGoiUpdatingDto vatTuTronGoiUpdatingDto : dsVatTuTronGoiUpdatingDtos) {
                if (vatTuTronGoi.getId().equals(vatTuTronGoiUpdatingDto.getId())) {
                    vatTuTronGoi.setDuocBaoHanh(vatTuTronGoiUpdatingDto.getDuocBaoHanh());
                    vatTuTronGoi.setDuocXem(vatTuTronGoiUpdatingDto.getDuocXem());
                    vatTuTronGoi.setTrangThai(vatTuTronGoiUpdatingDto.getTrangThai());
                }
            }
        }

        TepTin tepTin = tronGoi.getTepTin();
        boolean isNew = false;
        if (tepTin == null) {
            isNew = true;
            tepTin = TepTin.builder().build();
        }

        if (file != null) {
            try {
                tronGoi.setTen(dto.getTen());
                tronGoi.setLoaiHeThong(dto.getLoaiHeThong());
                tronGoi.setLoaiPha(dto.getLoaiPha());
                tronGoi.setCongSuatHeThong(dto.getCongSuatHeThong());
                tronGoi.setSanLuongToiThieu(dto.getSanLuongToiThieu());
                tronGoi.setSanLuongToiDa(dto.getSanLuongToiDa());
                tronGoi.setTongGia(dto.getTongGia());
                tronGoi.setGmTong(dto.getGmTong());
                tronGoi.setBanChay(dto.getBanChay());
                tronGoi.setTrangThai(dto.getTrangThai());
                minioService.delete(tepTin.getTenLuuTru());
                String objectName = minioService.upload(file, "tron_goi" + "_" + tronGoi.getTen() + '_' + tronGoi.getCoSo().getMa()) + "_" + new Date().getTime();
                tepTin.setTenLuuTru(objectName);
                tepTin.setTenTepGoc("tron_goi_" + tronGoi.getTen() + '_' + tronGoi.getCoSo().getMa());
                tepTin.setDuongDan(minioService.getPublicUrl(objectName));
                tepTin.setLoaiTepTin(FileType.IMAGE.toString());
                tepTin.setDuoiTep(minioService.getObjectInfo(objectName).getUserMetadata().get("file-extension"));
                tepTin = tepTinService.update(tepTin.getId(), tepTin);
                if (isNew) {
                    tepTin = tepTinService.create(tepTin);
                } else {
                    tepTin = tepTinService.update(tepTin.getId(), tepTin);
                }

                tronGoi.setTepTin(tepTin);
                tronGoi = update(tronGoi.getId(), tronGoi);
            } catch (Exception e) {
                throw new RuntimeException("Lỗi trong quá trình upload file: ", e);
            }
        }
        return ResponseEntity.ok(
                ResponseData.<TronGoiDto>builder()
                        .status(200)
                        .message("Success")
                        .data(tronGoiMapper.toDto(tronGoi))
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseData<TronGoiDto>> deleteTronGoi(Integer id) {
        Optional<TronGoi> tronGoi = getOne(id);
        if (tronGoi.isEmpty()) {
            throw new CommonException("Không tồn tại trọn gói id: " + id);
        }
        TronGoiDto dto = TronGoiDto.builder()
                .ten(tronGoi.get().getTen())
                .id(tronGoi.get().getId())
                .build();
        delete(id);
        return ResponseEntity.ok(
                ResponseData.<TronGoiDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(dto)
                        .build()
        );
    }
}
