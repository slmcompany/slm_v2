package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.ThongTinTenMien}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class ThongTinTenMienDto implements Serializable {
    Integer id;
    @Size(max = 20)
    String sdt;
    @Size(max = 100)
    String email;
    Instant taoLuc;
    Integer trangThai;
}