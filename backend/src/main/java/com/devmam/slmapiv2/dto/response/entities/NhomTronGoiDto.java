package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.NhomTronGoi}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class NhomTronGoiDto implements Serializable {
    Integer id;
    NganhHangDto nghanhHang;
    @Size(max = 400)
    String ten;
    ThuongHieuDto thuongHieuTamPin;
    ThuongHieuDto thuongHieuInverter;
    ThuongHieuDto thuongHieuPinLuuTru;
    Integer trangThai;
}