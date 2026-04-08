package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.FileType;
import com.devmam.slmapiv2.dto.request.entities.QuangCaoCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.QuangCaoUpdatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.QuangCaoDto;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.QuangCao;
import com.devmam.slmapiv2.entities.TepTin;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.QuangCaoMapper;
import com.devmam.slmapiv2.services.CalcService;
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

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class QuangCaoService extends BaseServiceImpl<QuangCao, Integer> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MinioService minioService;

    @Autowired
    private TepTinService tepTinService;

    @Autowired
    private NganhHangService nganhHangService;

    @Autowired
    private QuangCaoMapper quangCaoMapper;

    @Autowired
    private CalcService calcService;

    public QuangCaoService(JpaRepository<QuangCao, Integer> repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }


    @Transactional
    public ResponseEntity<ResponseData<QuangCaoDto>> create(QuangCaoCreatingDto dto, MultipartFile file) {

        Optional<NganhHang> nganhHangFinding = nganhHangService.getOne(dto.getNganhHangId());

        if (nganhHangFinding.isEmpty()) {
            throw new CommonException("Không tìm thấy ngành hàng id: " + dto.getNganhHangId());
        }
        TepTin creatingTepTin = null;

        try {
            // Bước 1: Upload
            log.info("Bắt đầu upload file cho quảng cáo: {}", dto.getTieuDe());
            String objectName = minioService.upload(file, "quang_cao_" + calcService.genTenKhongDau(dto.getTieuDe()) + "_" + new Date().getTime());
            log.info("Upload thành công, objectName: {}", objectName);

            // Bước 2: Lấy public URL (có thể lỗi ở đây)
            log.info("Đang lấy public URL...");
            String publicUrl = minioService.getPublicUrl(objectName);
            log.info("Public URL: {}", publicUrl);

            // Bước 3: Lấy object info (rất có thể lỗi ở đây)
            log.info("Đang lấy object info...");
            String fileExtension = minioService.getObjectInfo(objectName).getUserMetadata().get("file-extension");
            log.info("File extension: {}", fileExtension);

            creatingTepTin = tepTinService.create(
                    TepTin.builder()
                            .tenTepGoc(dto.getTieuDe())
                            .tenTaiLen(dto.getTieuDe())
                            .tenLuuTru(objectName)
                            .duongDan(publicUrl)
                            .loaiTepTin(FileType.IMAGE.toString())
                            .duoiTep(fileExtension)
                            .trangThai(1)
                            .taoLuc(Instant.now())
                            .build()
            );

        } catch (Exception e) {
            log.error("Lỗi tại bước nào đó: ", e);
            throw new RuntimeException("Lỗi tạo tệp tin cho quảng cáo: " + dto.getTieuDe(), e);
        }

        if (creatingTepTin == null) {
            CommonException e = new CommonException("Lỗi tạo tệp tin cho quảng cáo: " + dto.getTieuDe());
            log.error("Lỗi tạo tệp tin cho quản cáo: {}", dto.getTieuDe(), e);
            throw e;
        }

        QuangCao creatingQuangCao = QuangCao.builder()
                .nganhHang(nganhHangFinding.get())
                .tepTin(creatingTepTin)
                .tieuDe(dto.getTieuDe())
                .viTri(dto.getViTri())
                .taoLuc(Instant.now())
                .trangThai(1)
                .hoatDong(true)
                .build();

        creatingQuangCao = create(creatingQuangCao);

        return ResponseEntity.ok(
                ResponseData.<QuangCaoDto>builder()
                        .status(200)
                        .message("Success")
                        .error(null)
                        .data(quangCaoMapper.toDto(creatingQuangCao))
                        .build()
        );
    }


    @Transactional
    public ResponseEntity<ResponseData<QuangCaoDto>> update(QuangCaoUpdatingDto dto, MultipartFile file) {
        Optional<QuangCao> quangCaoFinding = getOne(dto.getId());

        if (quangCaoFinding.isEmpty()) {
            throw new CommonException("Không tìm thấy quảng cáo id: " + dto.getId());
        }

        Optional<NganhHang> nganhHangFinding = nganhHangService.getOne(dto.getNganhHangId());

        if (nganhHangFinding.isEmpty()) {
            throw new CommonException("Không tìm thấy ngành hàng id: " + dto.getNganhHangId());
        }

        QuangCao quangCaoUpdating = quangCaoFinding.get();

        TepTin tepTinUpdating = quangCaoUpdating.getTepTin();

        minioService.delete(tepTinUpdating.getTenLuuTru());

        try {
            String objectName = minioService.upload(file, "quang_cao_" + calcService.genTenKhongDau(dto.getTieuDe()) + "_" + new Date().getTime());
            tepTinUpdating.setTenLuuTru(objectName);
            tepTinUpdating.setDuongDan(minioService.getPublicUrl(objectName));
            tepTinUpdating.setDuoiTep(minioService.getObjectInfo(objectName).getUserMetadata().get("file-extension"));
            tepTinUpdating = tepTinService.update(tepTinUpdating.getId(), tepTinUpdating);

        } catch (Exception e) {
            log.error("Lỗi tạo tệp tin cho quảng cáo: {}", dto.getTieuDe(), e);
            throw new CommonException("Lỗi tạo tệp tin cho quảng cáo: " + dto.getTieuDe(), e);
        }
        if (tepTinUpdating == null) {
            CommonException e = new CommonException("Lỗi tạo tệp tin cho quảng cáo do tệp tin updating null: " + dto.getTieuDe());
            log.error("Lỗi tạo tệp tin cho quảng cáo: {}", dto.getTieuDe(), e);
            throw e;
        }

        quangCaoUpdating.setNganhHang(nganhHangFinding.get());
        quangCaoUpdating.setTieuDe(dto.getTieuDe());
        quangCaoUpdating.setViTri(dto.getViTri());
        quangCaoUpdating.setTepTin(tepTinUpdating);
        quangCaoUpdating = update(dto.getId(), quangCaoUpdating);

        return ResponseEntity.ok(
                ResponseData.<QuangCaoDto>builder()
                        .status(200)
                        .message("Success")
                        .error(null)
                        .data(quangCaoMapper.toDto(quangCaoUpdating))
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseData<String>> hardDelete(Integer id){
        QuangCao quangCao = getOne(id).orElseThrow(
                () -> new CommonException("Không tìm được quảng cáo id: "+id)
        );
        tepTinService.hardDelete(quangCao.getTepTin().getId());

        delete(id);
        return ResponseEntity.ok(
                ResponseData.<String>builder()
                        .status(200)
                        .message("Success")
                        .error(null)
                        .data(null)
                        .build()
        );
    }
}
