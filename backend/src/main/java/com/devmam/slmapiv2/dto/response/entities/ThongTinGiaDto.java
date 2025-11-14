package com.devmam.slmapiv2.dto.response.entities;

import com.devmam.slmapiv2.entities.commons.GiaInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.ThongTinGia}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class ThongTinGiaDto implements Serializable {
    Integer id;
    List<GiaInfo> dsGia;
    Instant taoLuc;
    Integer trangThai;
}