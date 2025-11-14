package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.NganhHang}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class NganhHangDto implements Serializable {
    Integer id;
    @Size(max = 50)
    String ma;
    @Size(max = 100)
    String ten;
    @Size(max = 20)
    String sdtSale;
    @Size(max = 20)
    String sdtTech;
    @Size(max = 1000)
    String anhNgang;
    @Size(max = 1000)
    String anhVuong;
    Integer trangThai;
}