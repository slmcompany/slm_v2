package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.FileType;
import com.devmam.slmapiv2.dto.request.entities.*;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.TronGoiDto;
import com.devmam.slmapiv2.entities.*;
import com.devmam.slmapiv2.entities.commons.GiaInfo;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.TronGoiMapper;
import com.devmam.slmapiv2.services.CalcService;
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
    private CalcService calcService;

    @Autowired
    private EntityManager entityManager;

    public TronGoiService(JpaRepository<TronGoi, Integer> repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    // ============================================================
    //  CREATE
    // ============================================================
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

            // Tạo vật tư trọn gói
            createVatTuTronGoiList(tronGoi, dto.getVatTuTronGois().stream().toList(), coSo.get());

            // Upload ảnh
            Date now = new Date();
            try {
                String objectName = minioService.upload(
                        file,
                        "tron_goi_" + calcService.genTenKhongDau(tronGoi.getTen())
                                + "_" + coSo.get().getMa() + "_" + now.getTime()
                );
                TepTin creatingTepTin = tepTinService.create(
                        TepTin.builder()
                                .tenTepGoc(objectName)
                                .tenTaiLen(objectName)
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

    // ============================================================
    //  UPDATE
    // ============================================================
    @Transactional
    public ResponseEntity<ResponseData<TronGoiDto>> update(TronGoiUpdatingDto dto, MultipartFile file) {
        Optional<TronGoi> tronGoiFinding = getOne(dto.getId());
        if (tronGoiFinding.isEmpty()) {
            throw new CommonException("Không tìm thấy trọn gói id: " + dto.getId());
        }

        TronGoi tronGoi = tronGoiFinding.get();
        CoSo coSo = tronGoi.getCoSo(); // giữ nguyên cơ sở

        // --- Cập nhật nhóm trọn gói nếu có thay đổi ---
        if (dto.getNhomTronGoiId() != null
                && !dto.getNhomTronGoiId().equals(tronGoi.getNhomTronGoi().getId())) {
            Optional<NhomTronGoi> nhomTronGoi = nhomTronGoiService.getOne(dto.getNhomTronGoiId());
            if (nhomTronGoi.isEmpty()) {
                throw new CommonException("Không tìm thấy nhóm trọn gói id: " + dto.getNhomTronGoiId());
            }
            tronGoi.setNhomTronGoi(nhomTronGoi.get());
        }

        // --- Cập nhật thông tin cơ bản ---
        tronGoi.setTen(dto.getTen());
        tronGoi.setLoaiHeThong(dto.getLoaiHeThong());
        tronGoi.setLoaiPha(dto.getLoaiPha());
        if (dto.getMoTa() != null) tronGoi.setMoTa(dto.getMoTa());
        tronGoi.setCongSuatHeThong(dto.getCongSuatHeThong());
        tronGoi.setGmTong(dto.getGmTong());
        tronGoi.setBanChay(dto.getBanChay());
        tronGoi.setTrangThai(dto.getTrangThai());

        // Tổng giá theo khu vực
        Double tongGia = dto.getTongGiaMienBac();
        if (coSo.getMa().equals("HCM") && dto.getTongGiaMienNam() != null) {
            tongGia = dto.getTongGiaMienNam();
        }
        tronGoi.setTongGia(tongGia);

        // --- Xóa toàn bộ vật tư cũ, tạo lại mới ---
        if (dto.getVatTuTronGois() != null && !dto.getVatTuTronGois().isEmpty()) {
            // Xóa các bản ghi VatTuTronGoi cũ
            List<VatTuTronGoi> oldList = new ArrayList<>(tronGoi.getVatTuTronGois());
            for (VatTuTronGoi old : oldList) {
                vatTuTronGoiService.delete(old.getId());
            }
            tronGoi.getVatTuTronGois().clear();

            // Flush để đảm bảo delete được thực thi trước khi insert
            entityManager.flush();

            // Tạo danh sách vật tư mới
            createVatTuTronGoiList(tronGoi, dto.getVatTuTronGois(), coSo);
        }

        // --- Xử lý file ảnh ---
        if (file != null) {
            TepTin tepTin = tronGoi.getTepTin();
            boolean isNew = (tepTin == null);
            if (isNew) tepTin = TepTin.builder().build();
            String oldObjectName = isNew ? null : tepTin.getTenLuuTru();

            try {
                String newObjectName = minioService.upload(
                        file,
                        "tron_goi_" + calcService.genTenKhongDau(tronGoi.getTen())
                                + "_" + coSo.getMa() + "_" + new Date().getTime()
                );
                tepTin.setTenLuuTru(newObjectName);
                tepTin.setTenTepGoc(newObjectName);
                tepTin.setDuongDan(minioService.getPublicUrl(newObjectName));
                tepTin.setLoaiTepTin(FileType.IMAGE.toString());
                tepTin.setDuoiTep(minioService.getObjectInfo(newObjectName).getUserMetadata().get("file-extension"));

                tepTin = isNew ? tepTinService.create(tepTin) : tepTinService.update(tepTin.getId(), tepTin);
                tronGoi.setTepTin(tepTin);

                if (oldObjectName != null) {
                    try { minioService.delete(oldObjectName); } catch (Exception ignored) {}
                }
            } catch (Exception e) {
                throw new RuntimeException("Lỗi trong quá trình upload file: ", e);
            }
        }

        tronGoi = update(tronGoi.getId(), tronGoi);

        return ResponseEntity.ok(
                ResponseData.<TronGoiDto>builder()
                        .status(200)
                        .message("Success")
                        .data(tronGoiMapper.toDto(tronGoi))
                        .build()
        );
    }

    // ============================================================
    //  DELETE
    // ============================================================
    @Transactional
    public ResponseEntity<ResponseData<TronGoiDto>> deleteTronGoi(Integer id) {
        Optional<TronGoi> tronGoi = getOne(id);
        if (tronGoi.isEmpty()) {
            throw new CommonException("Không tồn tại trọn gói id: " + id);
        }
        TepTin tepTin = tronGoi.get().getTepTin();
        if (tepTin != null) {
            try { minioService.delete(tepTin.getTenLuuTru()); } catch (Exception ignored) {}
            tepTinService.delete(tepTin.getId());
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

    // ============================================================
    //  HELPER: tạo danh sách VatTuTronGoi từ DTO
    // ============================================================
    private void createVatTuTronGoiList(TronGoi tronGoi,
                                        List<VatTuTronGoiCreatingDto> dtos,
                                        CoSo coSo) {
        for (VatTuTronGoiCreatingDto vatTuDto : dtos) {
            Optional<VatTu> vatTu = vatTuService.getOne(vatTuDto.getVatTuId());
            if (vatTu.isEmpty()) {
                throw new CommonException("Không tìm thấy vật tư id: " + vatTuDto.getVatTuId());
            }

            List<ThongTinGia> dsThongTinGia = vatTu.get().getThongTinGias();
            if (dsThongTinGia.isEmpty()) {
                throw new CommonException("Giá của vật tư id: " + vatTu.get().getId() + " chưa được khởi tạo");
            }

            // Xác định giá bán theo khu vực
            Double giaBanTheoKhuVuc = 0.0;
            boolean daChinhGia = false;
            for (GiaInfo giaInfo : dsThongTinGia.get(dsThongTinGia.size() - 1).getDsGia()) {
                if (giaInfo.getMaCoSo().equals(coSo.getMa())) {
                    giaBanTheoKhuVuc = giaInfo.getGiaBan() + 0.0;
                    daChinhGia = true;
                    break;
                }
            }
            if (!daChinhGia) {
                giaBanTheoKhuVuc = dsThongTinGia.get(dsThongTinGia.size() - 1)
                        .getDsGia().get(0).getGiaBan() + 0.0;
            }

            vatTuTronGoiService.create(VatTuTronGoi.builder()
                    .tronGoi(tronGoi)
                    .vatTu(vatTu.get())
                    .moTa(vatTuDto.getMoTa())
                    .soLuong(vatTuDto.getSoLuong())
                    .gia(giaBanTheoKhuVuc)
                    .gm(vatTuDto.getGm())
                    .duocBaoHanh(vatTuDto.getDuocBaoHanh())
                    .thoiGianBaoHanh(vatTuDto.getThoiGianBaoHanh())
                    .duocXem(vatTuDto.getDuocXem())
                    .trangThai(vatTuDto.getTrangThai())
                    .build());
        }
    }
}