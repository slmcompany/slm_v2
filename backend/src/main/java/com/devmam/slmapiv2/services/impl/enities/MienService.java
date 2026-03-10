package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.FileType;
import com.devmam.slmapiv2.dto.request.entities.MienCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.ThongTinMienCreatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.MienDto;
import com.devmam.slmapiv2.entities.CoSo;
import com.devmam.slmapiv2.entities.Mien;
import com.devmam.slmapiv2.entities.TepTin;
import com.devmam.slmapiv2.entities.ThongTinTenMien;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.MienMapper;
import com.devmam.slmapiv2.repository.MienRepository;
import com.devmam.slmapiv2.services.CalcService;
import com.devmam.slmapiv2.services.MinioService;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MienService extends BaseServiceImpl<Mien, Integer> {

    @Autowired
    private CoSoService coSoService;
    @Autowired
    private TepTinService tepTinService;
    @Autowired
    private ThongTinTenMienService thongTinTenMienService;
    @Autowired
    private MinioService minioService;
    @Autowired
    private CalcService calcService;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MienMapper mienMapper;

    private final MienRepository repository = (MienRepository) getRepository();

    public MienService(MienRepository repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    public ResponseEntity<ResponseData<MienDto>> create(MienCreatingDto creating, MultipartFile file) {
        creating.setTenMien(creating.getTenMien().trim().toLowerCase());

        Optional<Mien> findingMien = repository.findByTenMien(creating.getTenMien());

        if (findingMien.isPresent()) {
            throw new CommonException("Tên miền đã tồn tại");
        }

        CoSo coSo = coSoService.getOne(creating.getCoSoId()).orElseThrow(
                () -> new CommonException("Không tìm thấy cơ sở id: " + creating.getCoSoId())
        );

        Instant now = Instant.now();
        TepTin creatingTepTin = null;
        if (file != null) {
            try {
                Date dateNow = new Date();
                String objectName = minioService.upload(file, calcService.genTenKhongDau(creating.getTenMien()) + "_" + dateNow.getTime());
                creatingTepTin = tepTinService.create(
                        TepTin.builder()
                                .tenTepGoc(objectName)
                                .tenTaiLen(objectName)
                                .tenLuuTru(objectName)
                                .duongDan(minioService.getPublicUrl(objectName))
                                .loaiTepTin(FileType.IMAGE.toString())
                                .duoiTep(minioService.getObjectInfo(objectName).getUserMetadata().get("file-extension"))
                                .build()
                );
            } catch (Exception e) {
                throw new CommonException("Lỗi khi up load file ảnh: " + e.getMessage());
            }
        }


        Mien mien = Mien.builder()
                .tenMien(creating.getTenMien())
                .coSo(coSo)
                .trangThai(1)
                .taoLuc(now)
                .build();
        if (creatingTepTin != null) {
            mien.setTepTin(creatingTepTin);
        }
        List<ThongTinTenMien> thongTinTenMiens = new ArrayList<>();

        for (ThongTinMienCreatingDto thongTin : creating.getThongTinMiens()) {
            ThongTinTenMien thongTinTenMien = thongTinTenMienService.create(
                    ThongTinTenMien.builder()
                            .mien(mien)
                            .sdt(thongTin.getSdt())
                            .email(thongTin.getEmail())
                            .taoLuc(now)
                            .trangThai(1)
                            .build()
            );
            thongTinTenMiens.add(thongTinTenMien);
        }

        mien.setThongTinTenMiens(thongTinTenMiens);

        return ResponseEntity.ok(
                ResponseData.<MienDto>builder()
                        .status(200)
                        .data(mienMapper.toDto(mien))
                        .message("Success")
                        .build()
        );

    }

    @Transactional
    public ResponseEntity<ResponseData<String>> hardDelete(Integer id) {
        Mien mien = getOne(id).orElseThrow(
                () -> new CommonException("Không tìm thấy miền id: " + id)
        );
        if (mien.getTepTin() != null) {
            tepTinService.hardDelete(mien.getTepTin().getId());
        }

        delete(mien.getId());

        return ResponseEntity.ok(
                ResponseData.<String>builder()
                        .status(HttpStatus.OK.value())
                        .message("Success")
                        .data("Success")
                        .error(null)
                        .build()
        );

    }
}
