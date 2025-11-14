package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.TepTin}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class TepTinDto implements Serializable {
    Integer id;
    @Size(max = 1000)
    String tenTepGoc;
    @Size(max = 1000)
    String tenTaiLen;
    @NotNull
    @Size(max = 1000)
    String tenLuuTru;
    String duongDan;
    @Size(max = 100)
    String loaiTepTin;
    @Size(max = 10)
    String duoiTep;
    Integer kichCo;
    @Size(max = 400)
    String moTa;
    Instant taoLuc;
    Instant suaLuc;
    Integer trangThai;
}