package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.FileType;
import com.devmam.slmapiv2.dto.request.entities.VatTuCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.VatTuUpdatingDto;
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

import java.text.Normalizer;
import java.time.Instant;
import java.util.Date;
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

        VatTu creatingVattu = VatTuCreatingDto.toEntity(dto);
        Optional<NhomVatTu> nhomVatTu = nhomVatTuService.getOne(dto.getNhomVatTuId());

        if (nhomVatTu.isEmpty()) {
            throw new CommonException("Không tìm thấy nhóm vật tư id: " + dto.getNhomVatTuId());
        }
        Optional<ThuongHieu> thuongHieu = thuongHieuService.getOne(dto.getThuongHieuId());
        if (thuongHieu.isEmpty()) {
            throw new CommonException("Không tìm thấy thương hiệu id: " + dto.getThuongHieuId());
        }
        creatingVattu.setThuongHieu(thuongHieu.get());
        creatingVattu.setNhomVatTu(nhomVatTu.get());

        creatingVattu = create(creatingVattu);

        int i = 0;
        Date now = new Date();
        if (files != null) {
            for (MultipartFile file : files) {
                i++;
                try {
                    String objectName = minioService.upload(file, "vat_tu_anh" + creatingVattu.getTen() + "_" + now.getTime() + "_" + i);
                    TepTin creatingTepTin = tepTinService.create(
                            TepTin.builder()
                                    .tenTepGoc(objectName)
                                    .tenTaiLen(objectName)
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

    @Transactional
    public ResponseEntity<ResponseData<VatTuDto>> update(VatTuUpdatingDto dto, MultipartFile sheet, List<MultipartFile> files) {
        Optional<VatTu> vatTuFinding = getOne(dto.getId());

        if (vatTuFinding.isEmpty()) {
            throw new CommonException("Không tìm thấy vật tư id: " + dto.getId());
        }

        VatTu vatTu = vatTuFinding.get();

        if (sheet != null) {
            try {
                String objectName = minioService.upload(sheet, "vat_tu_sheet" + dto.getTen());
                TepTin creatingTepTin = tepTinService.create(
                        TepTin.builder()
                                .tenTepGoc(dto.getTen())
                                .tenTaiLen(dto.getTen())
                                .tenLuuTru(objectName)
                                .duongDan(minioService.getPublicUrl(objectName))
                                .loaiTepTin(FileType.PDF.toString())
                                .trangThai(1)
                                .build()
                );
                tepTinService.create(creatingTepTin);
                vatTu.setSheetLink(minioService.getPublicUrl(objectName));
            } catch (Exception e) {
                throw new CommonException("Lỗi tạo data sheet cho vật tư: " + dto.getTen(), e);
            }
        }

        List<AnhVatTu> dsAnhVatTu = vatTuFinding.get().getAnhVatTus();

        int i = 0;
        if (files != null) {
            for (MultipartFile file : files) {
                AnhVatTu anhVatTu = null;
                TepTin tepTin = null;
                String objectName = null;
                try {
                    objectName = minioService.upload(file, "vat_tu_anh" + dto.getTen() + "_" + (i + 1));
                } catch (Exception e) {
                    log.error("Lỗi tạo tệp tin cho vật tư: {}", dto.getTen(), e);
                    throw new RuntimeException("Lỗi tạo tệp tin cho vật tư: " + dto.getTen(), e);
                }
                if (i <= dsAnhVatTu.size() - 1) {
                    anhVatTu = dsAnhVatTu.get(i);
                    tepTin = anhVatTu.getTepTin();
                    tepTin.setTenLuuTru(objectName);
                    tepTin.setDuongDan(minioService.getPublicUrl(objectName));
                    tepTin.setTenTepGoc(dto.getTen() + "_" + (i + 1));
                    tepTin.setTenTaiLen(dto.getTen() + "_" + (i + 1));
                    tepTin.setLoaiTepTin(FileType.IMAGE.toString());
                    try {
                        tepTin.setDuoiTep(minioService.getObjectInfo(objectName).getUserMetadata().get("file-extension"));
                    } catch (Exception ignored) {
                    }
                    tepTinService.update(tepTin.getId(), tepTin);
                    i++;
                } else {
                    tepTin = TepTin.builder()
                            .tenTepGoc(dto.getTen() + "_" + (i + 1))
                            .tenTaiLen(dto.getTen() + "_" + (i + 1))
                            .tenLuuTru(objectName)
                            .duongDan(minioService.getPublicUrl(objectName))
                            .loaiTepTin(FileType.IMAGE.toString())
                            .trangThai(1)
                            .build();
                    try {
                        tepTin.setDuoiTep(minioService.getObjectInfo(objectName).getUserMetadata().get("file-extension"));
                    } catch (Exception e) {
                        tepTin.setDuoiTep("jpg");
                    }
                    tepTin = tepTinService.create(tepTin);
                    anhVatTuService.create(
                            AnhVatTu.builder()
                                    .vatTu(vatTuFinding.get())
                                    .tepTin(tepTin)
                                    .anhChinh(i == 0)
                                    .trangThai(1)
                                    .taoLuc(Instant.now())
                                    .build()
                    );
                    i++;
                }
            }
        }

        int j = 0;
        for (AnhVatTu anhVatTu : dsAnhVatTu) {
            TepTin tepTin = anhVatTu.getTepTin();
            minioService.delete(tepTin.getTenLuuTru());
            if (j > i) {
                anhVatTuService.delete(anhVatTu.getId());
                tepTinService.delete(tepTin.getId());
            }
            j++;
        }
        vatTu.setTen(dto.getTen());
        vatTu.setMa(genMaVatTu(dto.getTen()));
        vatTu.setTrangThai(dto.getTrangThai());

        vatTuFinding = getOne(dto.getId());

        return ResponseEntity.ok(
                ResponseData.<VatTuDto>builder()
                        .error(null)
                        .status(200)
                        .message("Update success")
                        .data(vatTuMapper.toDto(vatTuFinding.get()))
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseData<VatTuDto>> deleteVatTu(Integer id) {

        VatTu vatTu = getOne(id)
                .orElseThrow(() -> new CommonException("Không tìm thấy vật tư id: " + id));

        List<AnhVatTu> dsAnhVatTu = vatTu.getAnhVatTus();
        for (AnhVatTu anhVatTu : dsAnhVatTu) {
            TepTin tepTin = anhVatTu.getTepTin();
            minioService.delete(tepTin.getTenLuuTru());
            anhVatTuService.delete(anhVatTu.getId());
            tepTinService.delete(tepTin.getId());
        }

        VatTuDto vatTuDto = VatTuDto.builder()
                .id(vatTu.getId())
                .ten(vatTu.getTen())
                .build();
        delete(id);

        return ResponseEntity.ok(
                ResponseData.<VatTuDto>builder()
                        .status(200)
                        .message("Delete success")
                        .data(vatTuDto)
                        .error(null)
                        .build()
        );
    }


    public String genMaVatTu(String tenString) {
        // Bỏ dấu tiếng Việt
        String khongDau = Normalizer.normalize(tenString, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Chuyển về chữ thường
        khongDau = khongDau.toLowerCase();

        // Thay thế khoảng trắng bằng dấu gạch dưới
        String ma = khongDau.replaceAll("\\s+", "_");

        return ma + '_' + new Date().getTime();
    }
}
