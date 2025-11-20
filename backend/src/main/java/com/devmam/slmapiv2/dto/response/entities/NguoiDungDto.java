package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.NguoiDung}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class NguoiDungDto implements Serializable {
    Integer id;
    CoSoDto coSo;
    String phanQuyen;
    String email;
    String sdt;
    String matKhau;
    String hoVaTen;
    Boolean gioiTinh;
    Instant sinhNhat;
    Double phanTramHoaHong;
    Double tongHoaHong;
    String diaChi;
    String nganHang;
    String maNganHang;
    Instant taoLuc;
    Integer trangThai;
    List<KhachHangDto> khachHangs;
}