package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.VatTu}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class VatTuDto implements Serializable {
    Integer id;
    NhomVatTuDto nhomVatTu;
    ThuongHieuDto thuongHieu;
    @Size(max = 400)
    String ten;
    @Size(max = 1000)
    String sheetLink;
    @Size(max = 20)
    String donVi;
    String moTaBaoGia;
    String moTaHopDong;
    Map<String, Object> duLieuRieng;
    Instant taoLuc;
    Integer trangThai;
    List<AnhVatTuDto> anhVatTus;
    List<ThongTinGiaDto> thongTinGias;
}