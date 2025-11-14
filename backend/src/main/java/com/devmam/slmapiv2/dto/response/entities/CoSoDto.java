package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.CoSo}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class CoSoDto implements Serializable {
    Integer id;
    @Size(max = 50)
    String ma;
    @Size(max = 200)
    String ten;
    @Size(max = 500)
    String dcVanPhong;
    @Size(max = 500)
    String dcKho;
    Instant taoLuc;
    Integer trangThai;
}