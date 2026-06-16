package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.RoleType;
import com.devmam.slmapiv2.dto.request.entities.HopDongDefineKhachHangCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.HopDongUndefineKhachHangCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.HopDongUpdatingDto;
import com.devmam.slmapiv2.dto.request.entities.VatTuHopDongCreatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.HopDongDto;
import com.devmam.slmapiv2.entities.*;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.HopDongMapper;
import com.devmam.slmapiv2.repository.HopDongRepository;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class HopDongService extends BaseServiceImpl<HopDong, Integer> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CoSoService coSoService;
    @Autowired
    private NganhHangService nganhHangService;
    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private VatTuService vatTuService;
    @Autowired
    private VatTuHopDongService vatTuHopDongService;

    @Autowired
    private HoaHongService hoaHongService;
    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private HopDongMapper hopDongMapper;

    public HopDongService(HopDongRepository repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }


    @Transactional
    public ResponseEntity<ResponseData<HopDongDto>> create(HopDongUndefineKhachHangCreatingDto dto) {

        String sdt = dto.getSdtKhachHang();
        sdt = sdt.replaceAll("[^0-9]", "");
        dto.setSdtKhachHang(sdt);

        Optional<CoSo> coSoFinding = coSoService.getOne(dto.getCoSoId());

        if (coSoFinding.isEmpty()) {
            throw new CommonException("Không tìm thấy cơ sở id: " + dto.getCoSoId());
        }

        Optional<NganhHang> nganhHangFinding = nganhHangService.getOne(dto.getNghanhHangId());

        if (nganhHangFinding.isEmpty()) {
            throw new CommonException("Không tìm thấy ngành hàng id: " + dto.getNghanhHangId());
        }

        Optional<NguoiDung> nguoiGioiThieuFinding = nguoiDungService.getOne(dto.getNguoiGioiThieuId());

        if (nguoiGioiThieuFinding.isEmpty()) {
            throw new CommonException("Không tìm thấy người giới thiệu id: " + dto.getNguoiGioiThieuId());
        }

        if (dto.getEmailKhachHang() == null || dto.getEmailKhachHang().isEmpty()) {
            dto.setEmailKhachHang(dto.getSdtKhachHang());
        }

        KhachHang khachHangCreating = khachHangService.findBySdt(dto.getSdtKhachHang()).orElse(null);

        if (khachHangCreating == null) {
            khachHangCreating = KhachHang.builder()
                    .email(dto.getEmailKhachHang())
                    .sdt(dto.getSdtKhachHang())
                    .hoVaTen(dto.getHoVaTenKhachHang())
                    .gioiTinh(dto.getGioiTinhKhachHang())
                    .sinhNhat(dto.getSinhNhatKhachHang())
                    .diaChi(dto.getDiaChiKhachHang())
                    .nguoiGioiThieu(nguoiGioiThieuFinding.get())
                    .daBanDuocHang(true)
                    .taoLuc(dto.getTaoLuc())
                    .trangThai(1)
                    .build();
            khachHangCreating = khachHangService.create(khachHangCreating);
        }


        if (khachHangCreating == null) {
            throw new CommonException("Tạo khách hànng thất bại, sđt khach hàng: " + dto.getSdtKhachHang());
        }

        NguoiDung taiKhoanKhachHangMoi = nguoiDungService.findBySdtOrEmail(dto.getSdtKhachHang(), dto.getEmailKhachHang()).orElse(null);

        if (taiKhoanKhachHangMoi == null) {
            taiKhoanKhachHangMoi = NguoiDung.builder()
                    .coSo(coSoFinding.get())
                    .phanQuyen(RoleType.CUSTOMER.name())
                    .email(dto.getEmailKhachHang())
                    .sdt(dto.getSdtKhachHang())
                    .matKhau("slm123slm123")
                    .hoVaTen(dto.getHoVaTenKhachHang())
                    .gioiTinh(dto.getGioiTinhKhachHang())
                    .sinhNhat(dto.getSinhNhatKhachHang())
                    .phanTramHoaHong(5.0)
                    .tongHoaHong(0.0)
                    .diaChi(dto.getDiaChiKhachHang())
                    .nganHang(null)
                    .maNganHang(null)
                    .taoLuc(dto.getTaoLuc())
                    .trangThai(1)
                    .build();

            nguoiDungService.create(taiKhoanKhachHangMoi);
        }


        HopDong hopDongCreating = HopDong.builder()
                .coSo(coSoFinding.get())
                .nghanhHang(nganhHangFinding.get())
                .ten(dto.getTen())
                .loaiHeThong(dto.getLoaiHeThong())
                .loaiPha(dto.getLoaiPha())
                .sanLuongToiThieu(dto.getSanLuongToiThieu())
                .sanLuongToiDa(dto.getSanLuongToiDa())
                .giaKhungSat(dto.getGiaKhungSat())
                .moTa(dto.getMoTa())
                .nguoiGioiThieu(nguoiGioiThieuFinding.get())
                .khachHang(khachHangCreating)
                .tongGia(dto.getTongGia())
                .taoLuc(dto.getTaoLuc())
                .trangThai(1)
                .build();
        NguoiDung nguoiGioiThieu = nguoiGioiThieuFinding.get();

        hopDongCreating = create(hopDongCreating);
        if (hopDongCreating == null) {
            throw new CommonException("Tạo hợp đồng thất bại, sđt khách hàng: " + dto.getSdtKhachHang());
        }


        for (VatTuHopDongCreatingDto vatTuHopDongCreatingDto : dto.getVatTuHopDongs()) {
            Optional<VatTu> vatTuFinding = vatTuService.getOne(vatTuHopDongCreatingDto.getVatTuId());
            if (vatTuFinding.isEmpty()) {
                throw new CommonException("Không tìm thấy vật tư id: " + vatTuHopDongCreatingDto.getVatTuId());
            }
            VatTuHopDong vatTuHopDongCreating = VatTuHopDong.builder()
                    .hopDong(hopDongCreating)
                    .vatTu(vatTuFinding.get())
                    .moTa(vatTuHopDongCreatingDto.getMoTa())
                    .soLuong(vatTuHopDongCreatingDto.getSoLuong())
                    .gm(vatTuHopDongCreatingDto.getGm())
                    .giaHeThong(vatTuHopDongCreatingDto.getGiaBan())
                    .giaHienThi(vatTuHopDongCreatingDto.getGiaBan())
                    .thoiGianBaoHanh(vatTuHopDongCreatingDto.getThoiGianBaoHanh())
                    .baoHanhBatDau(dto.getTaoLuc())
                    .baoHanhKetThuc(
                            dto.getTaoLuc()
                                    .atZone(ZoneId.systemDefault()) // Chuyển Instant -> ZonedDateTime
                                    .plusMonths(vatTuHopDongCreatingDto.getThoiGianBaoHanh())
                                    .toInstant() // Chuyển lại thành Instant
                    )
                    .duocBaoHanh(vatTuHopDongCreatingDto.getDuocBaoHanh())
                    .taoLuc(dto.getTaoLuc())
                    .trangThai(1)
                    .build();
            vatTuHopDongService.create(vatTuHopDongCreating);
        }

        HoaHong hoaHongCreating = HoaHong.builder()
                .hopDong(hopDongCreating)
                .phanTram(nguoiGioiThieu.getPhanTramHoaHong())
                .thanhTien(hopDongCreating.getTongGia() * nguoiGioiThieu.getPhanTramHoaHong() / 1.08 / 100)
                .daTra(true)
                .taoLuc(dto.getTaoLuc())
                .trangThai(1)
                .build();
        hoaHongService.create(hoaHongCreating);
        Optional<HopDong> hopDongFinding = getOne(hopDongCreating.getId());

        if (hopDongFinding.isEmpty()) {
            throw new CommonException("Tạo hợp đồng thất bại, sđt khách hàng: " + dto.getSdtKhachHang());
        }

        nguoiGioiThieu.setTongHoaHong(nguoiGioiThieu.getTongHoaHong() + nguoiGioiThieu.getPhanTramHoaHong() * hopDongCreating.getTongGia() / 100);
        if (nguoiGioiThieu.getPhanQuyen().equals(RoleType.CUSTOMER.name())) {
            nguoiGioiThieu.setPhanQuyen(RoleType.AGENT.name());
        }
        nguoiDungService.update(nguoiGioiThieu.getId(), nguoiGioiThieu);
        return ResponseEntity.ok(
                ResponseData.<HopDongDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(hopDongMapper.toDto(hopDongFinding.get()))
                        .build()
        );
    }


    @Transactional
    public ResponseEntity<ResponseData<HopDongDto>> create(HopDongDefineKhachHangCreatingDto dto) {
        CoSo coSo = coSoService.getOne(dto.getCoSoId()).orElseThrow(
                () -> new CommonException("Không tìm thấy cơ sở id: " + dto.getCoSoId())
        );

        NganhHang nganhHang = nganhHangService.getOne(dto.getNghanhHangId()).orElseThrow(
                () -> new CommonException("Không tìm thấy ngành hàng id: " + dto.getNghanhHangId())
        );

        KhachHang khachHang = khachHangService.getOne(dto.getKhachHangId()).orElseThrow(
                () -> new CommonException("Không tìm thấy khách hàng id: " + dto.getKhachHangId())
        );

        HopDong hopDong = HopDongDefineKhachHangCreatingDto.toEntity(dto);
        hopDong.setCoSo(coSo);
        hopDong.setNghanhHang(nganhHang);
        hopDong.setKhachHang(khachHang);
        hopDong.setNguoiGioiThieu(khachHang.getNguoiGioiThieu());
        hopDong = create(hopDong);

        if (dto.getVatTuHopDongs() != null && !dto.getVatTuHopDongs().isEmpty()) {
            createVatTuHopDongList(hopDong, dto.getVatTuHopDongs());
        }
        return ResponseEntity.ok(
                ResponseData.<HopDongDto>builder()
                        .status(HttpStatus.OK.value())
                        .data(hopDongMapper.toDto(hopDong))
                        .message("Success")
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseData<HopDongDto>> update(HopDongUpdatingDto dto) {
        HopDong hopDong = getOne(dto.getId()).orElseThrow(
                () -> new CommonException("Không tìm thấy hợp đồng id: " + dto.getId())
        );

        CoSo coSo = coSoService.getOne(dto.getCoSoId()).orElseThrow(
                () -> new CommonException("Không tìm thấy cơ sở id: " + dto.getCoSoId())
        );

        KhachHang khachHang = hopDong.getKhachHang();

        NguoiDung nguoiDung = nguoiDungService.findBySdtOrEmail(khachHang.getSdt(), khachHang.getSdt()).orElseThrow(
                () -> new CommonException("Không tìm thấy người dùng sdt: " + khachHang.getSdt())
        );
        // Logic người giới thiệu và hoa hồng
        NguoiDung nguoiGioiThieu = nguoiDungService.getOne(dto.getNguoiGioiThieuId()).orElseThrow(
                () -> new CommonException("Không tìm thấy người dùng id: " + dto.getNguoiGioiThieuId())
        );

        // Update thông tin hợp đồng
        hopDong.setCoSo(coSo);
        hopDong.setTen(dto.getTen());
        hopDong.setLoaiHeThong(dto.getLoaiHeThong());
        hopDong.setLoaiPha(dto.getLoaiPha());
        hopDong.setSanLuongToiThieu(dto.getSanLuongToiThieu());
        hopDong.setSanLuongToiDa(dto.getSanLuongToiDa());
        hopDong.setGiaKhungSat(dto.getGiaKhungSat());
        hopDong.setMoTa(dto.getMoTa());
        hopDong.setTongGia(dto.getTongGia());
        hopDong.setNguoiGioiThieu(nguoiGioiThieu);

        //Update thông tin khách hàng
        khachHang.setNguoiGioiThieu(nguoiGioiThieu);
        khachHang.setEmail(dto.getEmailKhachHang());
        khachHang.setSdt(dto.getSdtKhachHang());
        khachHang.setHoVaTen(dto.getHoVaTenKhachHang());
        khachHang.setDiaChi(dto.getDiaChiKhachHang());
        khachHangService.update(khachHang.getId(), khachHang);

        //Update thông tin tài khoản của khách hàng
        nguoiDung.setEmail(dto.getEmailKhachHang());
        nguoiDung.setSdt(dto.getSdtKhachHang());
        nguoiDung.setHoVaTen(dto.getHoVaTenKhachHang());
        nguoiDung.setDiaChi(dto.getDiaChiKhachHang());
        nguoiDungService.update(nguoiDung.getId(), nguoiDung);

        // Logic vật tư trong hợp đồng
        if (dto.getVatTuHopDongs() != null && !dto.getVatTuHopDongs().isEmpty()) {
            vatTuHopDongService.delete(hopDong.getVatTuHopDongs());
            hopDong.getVatTuHopDongs().clear();
            entityManager.flush();
            createVatTuHopDongList(hopDong, dto.getVatTuHopDongs());
        }

        hopDong = update(hopDong);

        return ResponseEntity.ok(
                ResponseData.<HopDongDto>builder()
                        .status(HttpStatus.OK.value())
                        .data(hopDongMapper.toDto(hopDong))
                        .message("Success")
                        .build()
        );
    }

    private void createVatTuHopDongList(HopDong hopDong, List<VatTuHopDongCreatingDto> dtos) {
        Instant now = Instant.now();
        for (VatTuHopDongCreatingDto vatTuDto : dtos) {

            Optional<VatTu> vatTu = vatTuService.getOne(vatTuDto.getVatTuId());
            if (vatTu.isEmpty()) {
                throw new CommonException("Không tìm thấy vật tư id: " + vatTuDto.getVatTuId());
            }

            VatTuHopDong vatTuHopDongCreating = VatTuHopDong.builder()
                    .hopDong(hopDong)
                    .vatTu(vatTu.get())
                    .moTa(hopDong.getMoTa())
                    .soLuong(vatTuDto.getSoLuong())
                    .gm(vatTuDto.getGm())
                    .giaHeThong(vatTuDto.getGiaBan())
                    .giaHienThi(vatTuDto.getGiaBan())
                    .thoiGianBaoHanh(vatTuDto.getThoiGianBaoHanh())
                    .baoHanhBatDau(hopDong.getTaoLuc())
                    .baoHanhKetThuc(
                            hopDong.getTaoLuc()
                                    .atZone(ZoneId.systemDefault()) // Chuyển Instant -> ZonedDateTime
                                    .plusMonths(vatTuDto.getThoiGianBaoHanh())
                                    .toInstant() // Chuyển lại thành Instant
                    )
                    .duocBaoHanh(vatTuDto.getDuocBaoHanh())
                    .taoLuc(now)
                    .trangThai(1)
                    .build();
            vatTuHopDongService.create(vatTuHopDongCreating);
        }
    }

    @Transactional
    public ResponseEntity<ResponseData<HopDongDto>> deleteHopDong(Integer id) {
        Optional<HopDong> hopDongFinding = getOne(id);

        if (hopDongFinding.isEmpty()) {
            throw new CommonException("Không tìm thấy hợp đồng id: " + id);
        }

        Optional<HoaHong> hoaHongFinding = hoaHongService.getOne(hopDongFinding.get().getId());
        hoaHongFinding.ifPresent(hoaHong -> {
            NguoiDung nguoiGioiThieu = hopDongFinding.get().getNguoiGioiThieu();
            nguoiGioiThieu.setTongHoaHong(nguoiGioiThieu.getTongHoaHong() - hoaHong.getThanhTien());
            nguoiDungService.update(nguoiGioiThieu.getId(), nguoiGioiThieu);
            hoaHongService.delete(hoaHong.getId());
        });

        HopDongDto hopDongDto = HopDongDto.builder()
                .id(hopDongFinding.get().getId())
                .ten(hopDongFinding.get().getTen())
                .build();

        KhachHang khachHang = hopDongFinding.get().getKhachHang();

        khachHangService.delete(khachHang.getId());


        delete(hopDongFinding.get().getId());
        return ResponseEntity.ok(
                ResponseData.<HopDongDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(hopDongDto)
                        .build()
        );
    }


}
