package com.devmam.slmapiv2.dto.request.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NguoiDungUpdatingDto {
    String phanQuyen;
    String email;
    String sdt;
    String matKhau;
    String hoVaTen;
    Boolean gioiTinh;
    Instant sinhNhat;
    Double phanTramHoaHong;
    String diaChi;
    String nganHang;
    String maNganHang;
    Integer trangThai;
}
