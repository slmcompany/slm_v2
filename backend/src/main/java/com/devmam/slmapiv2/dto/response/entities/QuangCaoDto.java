package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.QuangCao}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class QuangCaoDto implements Serializable {
    Integer id;
    NganhHangDto nganhHang;
    TepTinDto tepTin;
    @Size(max = 200)
    String tieuDe;
    @Size(max = 100)
    String viTri;
    Boolean hoatDong;
    Instant taoLuc;
    Integer trangThai;
}