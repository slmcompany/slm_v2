package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.HopDong}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class HopDongDto implements Serializable {
    Integer id;
    CoSoDto coSo;
    NganhHangDto nghanhHang;
    @Size(max = 400)
    String ten;
    @Size(max = 50)
    String loaiHeThong;
    @Size(max = 50)
    String loaiPha;
    Double sanLuongToiThieu;
    Double sanLuongToiDa;
    Double giaKhungSat;
    Double giaLapKhungSat;
    String moTa;
    NguoiDungDto nguoiGioiThieu;
    KhachHangDto khachHang;
    Double tongGia;
    Instant taoLuc;
    Integer trangThai;
    List<VatTuHopDongDto> vatTuHopDongs;
}