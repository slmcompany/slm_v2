package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.Mien}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class MienDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 100)
    String tenMien;
    CoSoDto coSo;
    Instant taoLuc;
    Integer trangThai;
    List<ThongTinTenMienDto> thongTinTenMiens;
}