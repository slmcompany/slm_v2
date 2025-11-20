package com.devmam.slmapiv2.dto.response;

import com.devmam.slmapiv2.dto.response.entities.TepTinDto;
import com.devmam.slmapiv2.entities.NguoiDung;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.BaiViet}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class BaiVietDto implements Serializable {
    Integer id;
    @Size(max = 200)
    String loaiBaiViet;
    @Size(max = 500)
    String tieuDe;
    TepTinDto anhBia;
    TepTinDto noiDung;
    NguoiDung taoBoi;
    @Size(max = 500)
    String lienQuan;
    Boolean hoatDong;
    Instant taoLuc;
    Integer trangThai;
}