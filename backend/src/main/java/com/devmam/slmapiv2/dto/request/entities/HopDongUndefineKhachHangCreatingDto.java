package com.devmam.slmapiv2.dto.request.entities;

import com.devmam.slmapiv2.entities.HopDong;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class HopDongUndefineKhachHangCreatingDto {
    Integer coSoId;
    Integer nghanhHangId;
    @Size(max = 400)
    String ten;
    @Size(max = 50)
    String loaiHeThong;
    @Size(max = 50)
    String loaiPha;
    Double sanLuongToiThieu;
    Double sanLuongToiDa;
    Double giaKhungSat;
    String moTa;
    Integer nguoiGioiThieuId;
    String emailKhachHang;
    String sdtKhachHang;
    String hoVaTenKhachHang;
    Boolean gioiTinhKhachHang;
    Instant sinhNhatKhachHang;
    String diaChiKhachHang;
    Double tongGia;
    Instant taoLuc;
    List<VatTuHopDongCreatingDto> vatTuHopDongs;


    public static HopDong toEntity(HopDongUndefineKhachHangCreatingDto dto){
        return HopDong.builder()
                .ten(dto.getTen())
                .loaiHeThong(dto.getLoaiHeThong())
                .loaiPha(dto.getLoaiPha())
                .sanLuongToiThieu(dto.getSanLuongToiThieu())
                .sanLuongToiDa(dto.getSanLuongToiDa())
                .giaKhungSat(dto.getGiaKhungSat())
                .moTa(dto.getMoTa())
                .tongGia(dto.getTongGia())
                .taoLuc(dto.getTaoLuc())
                .build();
    }
}
