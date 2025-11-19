package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.FileType;
import com.devmam.slmapiv2.dto.request.entities.BaiVietCreatingDto;
import com.devmam.slmapiv2.dto.response.BaiVietDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.entities.BaiViet;
import com.devmam.slmapiv2.entities.NguoiDung;
import com.devmam.slmapiv2.entities.TepTin;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.BaiVietMapper;
import com.devmam.slmapiv2.repository.BaiVietRepository;
import com.devmam.slmapiv2.services.MinioService;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class BaiVietService extends BaseServiceImpl<BaiViet, Integer> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TepTinService tepTinService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private MinioService minioService;
    @Autowired
    private BaiVietMapper baiVietMapper;

    public BaiVietService(BaiVietRepository repository) {
        super(repository);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    public ResponseEntity<ResponseData<BaiVietDto>> create(BaiVietCreatingDto dto, MultipartFile anhBia, MultipartFile noiDung) {
        NguoiDung nguoiDung = null;
        if(dto.getTaoBoi() != null){
            Optional<NguoiDung> nguoiDungFinding = nguoiDungService.getOne(dto.getTaoBoi());
            if (nguoiDungFinding.isEmpty()) {
                throw new CommonException("Không tìm thấy người dùng id: " + dto.getTaoBoi());
            }
            nguoiDung = nguoiDungFinding.get();
        }

        BaiViet creatingBaiViet  = BaiViet.builder()
                .loaiBaiViet(dto.getLoaiBaiViet())
                .tieuDe(dto.getTieuDe())
                .taoBoi(nguoiDung)
                .lienQuan(dto.getLienQuan())
                .hoatDong(true)
                .taoLuc(Instant.now())
                .trangThai(dto.getTrangThai())
                .build();

        creatingBaiViet = create(creatingBaiViet);
        if (anhBia != null && noiDung != null) {
            try {
                Date now = new Date();
                String objectNameAnhBia = minioService.upload(anhBia, "bai_viet_anh_bia_"+dto.getTieuDe()+"_"+now.getTime());
                String objectNameNoiDUng = minioService.upload(noiDung, "bai_viet_noi_dung_"+dto.getTieuDe()+"_"+now.getTime());

                TepTin tepTinAnhBia = TepTin.builder()
                        .tenTepGoc(dto.getTieuDe()+"_anh_bia")
                        .tenTaiLen(dto.getTieuDe()+"_anh_bia")
                        .tenLuuTru(objectNameAnhBia)
                        .duongDan(minioService.getPublicUrl(objectNameAnhBia))
                        .loaiTepTin(FileType.IMAGE.toString())
                        .duoiTep(minioService.getObjectInfo(objectNameAnhBia).getUserMetadata().get("file-extension"))
                        .trangThai(1)
                        .taoLuc(creatingBaiViet.getTaoLuc())
                        .build();

                TepTin tepTinNoiDung = TepTin.builder()
                        .tenTepGoc(dto.getTieuDe()+"_noi_dung")
                        .tenTaiLen(dto.getTieuDe()+"_noi_dung")
                        .tenLuuTru(objectNameNoiDUng)
                        .duongDan(minioService.getPublicUrl(objectNameNoiDUng))
                        .loaiTepTin(FileType.TEXT.toString())
                        .duoiTep(minioService.getObjectInfo(objectNameNoiDUng).getUserMetadata().get("file-extension"))
                        .trangThai(1)
                        .taoLuc(creatingBaiViet.getTaoLuc())
                        .build();

                tepTinAnhBia = tepTinService.create(tepTinAnhBia);
                tepTinNoiDung = tepTinService.create(tepTinNoiDung);

                creatingBaiViet.setAnhBia(tepTinAnhBia);
                creatingBaiViet.setNoiDung(tepTinNoiDung);
            } catch (Exception e) {
                log.error("Lỗi khi tạo tệp tin cho bài viết: {}", dto.getTieuDe(), e);
                throw new RuntimeException("Lỗi khi tạo tệp tin cho bài viết: " + dto.getTieuDe(), e);
            }
        }
        return ResponseEntity.ok(
                ResponseData.<BaiVietDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(baiVietMapper.toDto(creatingBaiViet))
                        .build()
        );
    }
}
