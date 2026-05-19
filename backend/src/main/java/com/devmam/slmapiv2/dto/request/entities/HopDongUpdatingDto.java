package com.devmam.slmapiv2.dto.request.entities;

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
public class HopDongUpdatingDto {
    Integer id;
    Integer coSoId;
    String ten;
    String loaiHeThong;
    String loaiPha;
    Double sanLuongToiThieu;
    Double sanLuongToiDa;
    Double giaKhungSat;
    String moTa;
    Integer nguoiGioiThieuId;
    String emailKhachHang;
    String sdtKhachHang;
    String hoVaTenKhachHang;
    String diaChiKhachHang;
    Double tongGia;
    Instant taoLuc;
    List<VatTuHopDongCreatingDto> vatTuHopDongs;
}
