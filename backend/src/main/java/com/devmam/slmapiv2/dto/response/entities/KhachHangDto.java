package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.KhachHang}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class KhachHangDto implements Serializable {
    Integer id;
    String email;
    String sdt;
    String hoVaTen;
    Boolean gioiTinh;
    Instant sinhNhat;
    String diaChi;
    Boolean daBanDuocHang;
    Instant taoLuc;
    Integer trangThai;
}