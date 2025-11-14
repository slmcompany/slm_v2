package com.devmam.slmapiv2.dto.response.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.AnhVatTu}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class AnhVatTuDto implements Serializable {
    Integer id;
    TepTinDto tepTin;
    Boolean anhChinh;
    Integer trangThai;
}