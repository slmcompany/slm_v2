package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.FileType;
import com.devmam.slmapiv2.dto.request.entities.VatTuCreatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.VatTuDto;
import com.devmam.slmapiv2.entities.*;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.VatTuMapper;
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

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VatTuService extends BaseServiceImpl<VatTu, Integer> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AnhVatTuService anhVatTuService;

    @Autowired
    private TepTinService tepTinService;

    @Autowired
    private NhomVatTuService nhomVatTuService;

    @Autowired
    private ThuongHieuService thuongHieuService;

    @Autowired
    private ThongTinGiaService thongTinGiaService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MinioService minioService;

    @Autowired
    private VatTuMapper vatTuMapper;



    public VatTuService(JpaRepository<VatTu, Integer> repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }


    @Transactional
    public ResponseEntity<ResponseData<VatTuDto>> create(VatTuCreatingDto dto, List<MultipartFile> files) {
//        Integer userId = jwtService.getUserId(jwtService.getTokenFromAuthHeader(request.getHeader("Authorization")));

        VatTu creatingVattu = VatTuCreatingDto.toEntity(dto);
        Optional<NhomVatTu> nhomVatTu = nhomVatTuService.getOne(dto.getNhomVatTuId());

        if(nhomVatTu.isEmpty()){
            throw new CommonException("Không tìm thấy nhóm vật tư id: "+dto.getNhomVatTuId());
        }
        Optional<ThuongHieu> thuongHieu = thuongHieuService.getOne(dto.getThuongHieuId());
        if(thuongHieu.isEmpty()){
            throw new CommonException("Không tìm thấy thương hiệu id: "+dto.getThuongHieuId());
        }
        creatingVattu.setThuongHieu(thuongHieu.get());
        creatingVattu.setNhomVatTu(nhomVatTu.get());

        creatingVattu = create(creatingVattu);

        int i = 0;
        for (MultipartFile file : files) {
            i++;
            try {
                String objectName = minioService.upload(file, "vat_tu_"+creatingVattu.getTen()+"_"+i);
                TepTin creatingTepTin = tepTinService.create(
                        TepTin.builder()
                                .tenTepGoc(creatingVattu.getTen()+"_"+i)
                                .tenTaiLen(creatingVattu.getTen()+"_"+i)
                                .tenLuuTru(objectName)
                                .duongDan(minioService.getPublicUrl(objectName))
                                .loaiTepTin(FileType.IMAGE.toString())
                                .duoiTep(minioService.getObjectInfo(objectName).getUserMetadata().get("file-extension"))
                                .trangThai(1)
                                .taoLuc(creatingVattu.getTaoLuc())
                                .build()
                );

                anhVatTuService.create(
                        AnhVatTu.builder()
                                .vatTu(creatingVattu)
                                .tepTin(creatingTepTin)
                                .anhChinh(i == 1)
                                .trangThai(1)
                                .taoLuc(creatingVattu.getTaoLuc())
                                .build()
                );


            } catch (Exception e) {
                log.error("Lỗi tạo tệp tin cho vật tư: {}", dto.getTen(), e);
                throw new RuntimeException("Lỗi tạo tệp tin cho vật tư: " + dto.getTen(), e);
            }
        }
        ThongTinGia creatingThongTinGia = ThongTinGia.builder()
                .vatTu(creatingVattu)
                .dsGia(dto.getDsGia())
                .trangThai(1)
                .taoLuc(creatingVattu.getTaoLuc())
                .build();
        thongTinGiaService.create(creatingThongTinGia);
        creatingVattu = getOne(creatingVattu.getId()).get();
        return ResponseEntity.ok(
                ResponseData.<VatTuDto>builder()
                        .status(200)
                        .message("Create success")
                        .data(vatTuMapper.toDto(creatingVattu))
                        .error(null)
                        .build()
        );
    }
}
