package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.VatTuTronGoi}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class VatTuTronGoiDto implements Serializable {
    Integer id;
    VatTuDto vatTu;
    @Size(max = 500)
    String moTa;
    Integer soLuong;
    Double gia;
    Double gm;
    Instant taoLuc;
    Integer thoiGianBaoHanh;
    Boolean duocBaoHanh;
    Boolean duocXem;
    Integer trangThai;
}