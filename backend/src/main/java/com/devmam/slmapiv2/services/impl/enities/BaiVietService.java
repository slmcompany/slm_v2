package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.FileType;
import com.devmam.slmapiv2.dto.request.entities.BaiVietCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.BaiVietUpdatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.BaiVietDto;
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
    public ResponseEntity<ResponseData<BaiVietDto>> create(BaiVietCreatingDto dto, MultipartFile anhBia,
                                                           MultipartFile anhNgoai, MultipartFile noiDung) {
        NguoiDung nguoiDung = null;
        if (dto.getTaoBoi() != null) {
            Optional<NguoiDung> nguoiDungFinding = nguoiDungService.getOne(dto.getTaoBoi());
            if (nguoiDungFinding.isEmpty()) {
                throw new CommonException("Không tìm thấy người dùng id: " + dto.getTaoBoi());
            }
            nguoiDung = nguoiDungFinding.get();
        }

        BaiViet creatingBaiViet = BaiViet.builder()
                .loaiBaiViet(dto.getLoaiBaiViet())
                .tieuDe(dto.getTieuDe())
                .taoBoi(nguoiDung)
                .lienQuan(dto.getLienQuan())
                .hoatDong(true)
                .taoLuc(Instant.now())
                .trangThai(dto.getTrangThai())
                .build();

        creatingBaiViet = create(creatingBaiViet);
        try {
            Date now = new Date();
            if (anhBia != null) {
                String objectNameAnhBia = minioService.upload(anhBia, "bai_viet_anh_bia_" + dto.getTieuDe() + "_" + now.getTime());
                TepTin tepTinAnhBia = TepTin.builder()
                        .tenTepGoc(dto.getTieuDe() + "_anh_bia")
                        .tenTaiLen(dto.getTieuDe() + "_anh_bia")
                        .tenLuuTru(objectNameAnhBia)
                        .duongDan(minioService.getPublicUrl(objectNameAnhBia))
                        .loaiTepTin(FileType.IMAGE.toString())
                        .duoiTep(minioService.getObjectInfo(objectNameAnhBia).getUserMetadata().get("file-extension"))
                        .trangThai(1)
                        .taoLuc(creatingBaiViet.getTaoLuc())
                        .build();
                tepTinAnhBia = tepTinService.create(tepTinAnhBia);
                creatingBaiViet.setAnhBia(tepTinAnhBia);
            }
            if (anhNgoai != null) {
                String objectNameAnhNgoai = minioService.upload(anhNgoai, "bai_viet_anh_ngoai_" + dto.getTieuDe() + "_" + now.getTime());
                TepTin tepTinAnhNgoai = TepTin.builder()
                        .tenTepGoc(dto.getTieuDe() + "_anh_ngoai")
                        .tenTaiLen(dto.getTieuDe() + "_anh_ngoai")
                        .tenLuuTru(objectNameAnhNgoai)
                        .duongDan(minioService.getPublicUrl(objectNameAnhNgoai))
                        .loaiTepTin(FileType.IMAGE.toString())
                        .duoiTep(minioService.getObjectInfo(objectNameAnhNgoai).getUserMetadata().get("file-extension"))
                        .trangThai(1)
                        .taoLuc(creatingBaiViet.getTaoLuc())
                        .build();
                tepTinAnhNgoai = tepTinService.create(tepTinAnhNgoai);
                creatingBaiViet.setAnhNgoai(tepTinAnhNgoai);
            }

            if (noiDung != null) {
                String objectNameNoiDung = minioService.upload(noiDung, "bai_viet_noi_dung_" + dto.getTieuDe() + "_" + now.getTime());
                TepTin tepTinNoiDung = TepTin.builder()
                        .tenTepGoc(dto.getTieuDe() + "_noi_dung")
                        .tenTaiLen(dto.getTieuDe() + "_noi_dung")
                        .tenLuuTru(objectNameNoiDung)
                        .duongDan(minioService.getPublicUrl(objectNameNoiDung))
                        .loaiTepTin(FileType.TEXT.toString())
                        .duoiTep(minioService.getObjectInfo(objectNameNoiDung).getUserMetadata().get("file-extension"))
                        .trangThai(1)
                        .taoLuc(creatingBaiViet.getTaoLuc())
                        .build();
                tepTinNoiDung = tepTinService.create(tepTinNoiDung);
                creatingBaiViet.setNoiDung(tepTinNoiDung);
            }

            creatingBaiViet = update(creatingBaiViet.getId(), creatingBaiViet);

        } catch (Exception e) {
            log.error("Lỗi khi tạo tệp tin cho bài viết: {}", dto.getTieuDe(), e);
            throw new RuntimeException("Lỗi khi tạo tệp tin cho bài viết: " + dto.getTieuDe(), e);
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

    @Transactional
    public ResponseEntity<ResponseData<BaiVietDto>> update(BaiVietUpdatingDto dto, MultipartFile anhBia, MultipartFile anhNgoai, MultipartFile noiDung) {
        Optional<BaiViet> findingBaiViet = getOne(dto.getId());

        if (findingBaiViet.isEmpty()) {
            throw new CommonException("Bài viết không tồn tại");
        }

        BaiViet baiViet = findingBaiViet.get();

        TepTin tepTinAnhBia = baiViet.getAnhBia();

        TepTin tepTinAnhNgoai = baiViet.getAnhNgoai();

        TepTin tepTinNoiDung = baiViet.getNoiDung();

        boolean tepTinAnhBiaIsNew = false;
        boolean tepTinAnhNgoaiIsNew = false;
        boolean tepTinNoiDungIsNew = false;

        if (tepTinAnhBia == null) {
            tepTinAnhBiaIsNew = true;
            tepTinAnhBia = TepTin.builder().build();
        }

        if (tepTinAnhNgoai == null) {
            tepTinAnhNgoaiIsNew = true;
            tepTinAnhNgoai = TepTin.builder().build();
        }

        if (tepTinNoiDung == null) {
            tepTinNoiDungIsNew = true;
            tepTinNoiDung = TepTin.builder().build();
        }

        try {
            if (!tepTinAnhBiaIsNew) {
                minioService.delete(tepTinAnhBia.getTenLuuTru());
            }
            if (!tepTinAnhNgoaiIsNew) {
                minioService.delete(tepTinAnhNgoai.getTenLuuTru());
            }
            if (!tepTinNoiDungIsNew) {
                minioService.delete(tepTinNoiDung.getTenLuuTru());
            }
        } catch (Exception ignore) {
        }

        try {
            baiViet.setTieuDe(dto.getTieuDe());
            baiViet.setLienQuan(dto.getLienQuan());
            baiViet.setTrangThai(dto.getTrangThai());
            baiViet = update(baiViet.getId(), baiViet);
            Date now = new Date();
            if (anhBia != null) {
                String objectNameAnhBia = minioService.upload(anhBia, "bai_viet_anh_bia_" + dto.getTieuDe() + "_" + now.getTime());

                tepTinAnhBia.setTenTepGoc(dto.getTieuDe() + "_anh_bia");
                tepTinAnhBia.setTenTaiLen(dto.getTieuDe() + "_anh_bia");
                tepTinAnhBia.setTenLuuTru(objectNameAnhBia);
                tepTinAnhBia.setDuongDan(minioService.getPublicUrl(objectNameAnhBia));
                tepTinAnhBia.setTrangThai(1);
                tepTinAnhBia.setTaoLuc(baiViet.getTaoLuc());
                tepTinAnhBia = tepTinService.update(tepTinAnhBia.getId(), tepTinAnhBia);
            }

            if (anhNgoai != null) {
                String objectNameAnhNgoai = minioService.upload(anhNgoai, "bai_viet_anh_ngoai_" + dto.getTieuDe() + "_" + now.getTime());
                tepTinAnhNgoai.setTenTepGoc(dto.getTieuDe() + "_anh_ngoai");
                tepTinAnhNgoai.setTenTaiLen(dto.getTieuDe() + "_anh_ngoai");
                tepTinAnhNgoai.setTenLuuTru(objectNameAnhNgoai);
                tepTinAnhNgoai.setDuongDan(minioService.getPublicUrl(objectNameAnhNgoai));
                tepTinAnhNgoai.setTrangThai(1);
                tepTinAnhNgoai.setTaoLuc(baiViet.getTaoLuc());
                tepTinAnhNgoai = tepTinService.update(tepTinAnhNgoai.getId(), tepTinAnhNgoai);
            }

            if (noiDung != null) {
                String objectNameNoiDUng = minioService.upload(noiDung, "bai_viet_noi_dung_" + dto.getTieuDe() + "_" + now.getTime());

                tepTinNoiDung.setTenTepGoc(dto.getTieuDe() + "_noi_dung");
                tepTinNoiDung.setTenTaiLen(dto.getTieuDe() + "_noi_dung");
                tepTinNoiDung.setTenLuuTru(objectNameNoiDUng);
                tepTinNoiDung.setDuongDan(minioService.getPublicUrl(objectNameNoiDUng));
                tepTinNoiDung.setTrangThai(1);
                tepTinNoiDung.setTaoLuc(baiViet.getTaoLuc());
                tepTinNoiDung = tepTinService.update(tepTinNoiDung.getId(), tepTinNoiDung);
            }

            if (tepTinAnhBiaIsNew) {
                tepTinAnhBia = tepTinService.create(tepTinAnhBia);
            }

            if (tepTinNoiDungIsNew) {
                tepTinNoiDung = tepTinService.create(tepTinNoiDung);
            }

            baiViet.setAnhBia(tepTinAnhBia);
            baiViet.setAnhNgoai(tepTinAnhNgoai);
            baiViet.setNoiDung(tepTinNoiDung);
            baiViet = update(baiViet.getId(), baiViet);

        } catch (Exception e) {
            log.error("Lỗi khi tạo tệp tin cho bài viết: {}", dto.getTieuDe(), e);
            throw new CommonException("Lỗi xảy ra trong quá trình sửa bài viết: " + baiViet.getTieuDe() + ": " + e.getMessage(), e);
        }

        return ResponseEntity.ok(
                ResponseData.<BaiVietDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(baiVietMapper.toDto(baiViet))
                        .build()
        );

    }
}
