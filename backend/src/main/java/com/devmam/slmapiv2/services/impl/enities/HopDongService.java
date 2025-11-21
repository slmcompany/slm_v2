package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.RoleType;
import com.devmam.slmapiv2.dto.request.entities.HopDongUndefineKhachHangCreatingDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
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

        KhachHang khachHangCreating = KhachHang.builder()
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

        if (khachHangCreating == null) {
            throw new CommonException("Tạo khách hànng thất bại, sđt khach hàng: " + dto.getSdtKhachHang());
        }

        NguoiDung taiKhoanKhachHangMoi = NguoiDung.builder()
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

        taiKhoanKhachHangMoi = nguoiDungService.create(taiKhoanKhachHangMoi);

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
                    .baoHanhKetThuc(dto.getTaoLuc().plus(vatTuHopDongCreatingDto.getThoiGianBaoHanh(), ChronoUnit.YEARS))
                    .duocBaoHanh(vatTuHopDongCreatingDto.getDuocBaoHanh())
                    .taoLuc(dto.getTaoLuc())
                    .trangThai(1)
                    .build();
            vatTuHopDongService.create(vatTuHopDongCreating);
        }

        HoaHong hoaHongCreating = HoaHong.builder()
                .hopDong(hopDongCreating)
                .phanTram(5.0)
                .thanhTien(hopDongCreating.getTongGia() * 5 / 100)
                .daTra(true)
                .taoLuc(dto.getTaoLuc())
                .trangThai(1)
                .build();
        hoaHongService.create(hoaHongCreating);
        Optional<HopDong> hopDongFinding = getOne(hopDongCreating.getId());

        if (hopDongFinding.isEmpty()) {
            throw new CommonException("Tạo hợp đồng thất bại, sđt khách hàng: " + dto.getSdtKhachHang());
        }

        nguoiGioiThieu.setTongHoaHong(nguoiGioiThieu.getTongHoaHong() + 5 * hopDongCreating.getTongGia() / 100);

        return ResponseEntity.ok(
                ResponseData.<HopDongDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(hopDongMapper.toDto(hopDongFinding.get()))
                        .build()
        );
    }
}
