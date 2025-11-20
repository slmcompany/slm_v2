package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.VatTuHopDong}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class VatTuHopDongDto implements Serializable {
    Integer id;
    VatTuDto vatTu;
    String moTa;
    Integer soLuong;
    Double gm;
    Double giaHeThong;
    Double giaHienThi;
    Integer thoiGianBaoHanh;
    Instant baoHanhBatDau;
    Instant baoHanhKetThuc;
    Boolean duocBaoHanh;
    Instant taoLuc;
    Integer trangThai;
}