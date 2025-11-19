package com.devmam.slmapiv2.dto.response;

import com.devmam.slmapiv2.dto.response.entities.TepTinDto;
import com.devmam.slmapiv2.entities.NguoiDung;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.BaiViet}
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BaiVietDto implements Serializable {
    private final Integer id;
    @Size(max = 200)
    private final String loaiBaiViet;
    @Size(max = 500)
    private final String tieuDe;
    private final TepTinDto anhBia;
    private final TepTinDto noiDung;
    private final NguoiDung taoBoi;
    @Size(max = 500)
    private final String lienQuan;
    private final Boolean hoatDong;
    private final Instant taoLuc;
    private final Integer trangThai;
}