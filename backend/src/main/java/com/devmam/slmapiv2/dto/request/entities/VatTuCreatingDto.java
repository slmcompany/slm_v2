package com.devmam.slmapiv2.dto.request.entities;

import com.devmam.slmapiv2.entities.VatTu;
import com.devmam.slmapiv2.entities.commons.GiaInfo;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class VatTuCreatingDto {
    Integer nhomVatTuId;
    Integer thuongHieuId;
    @Size(max = 400)
    String ten;
    @Size(max = 1000)
    String sheetLink;
    @Size(max = 20)
    String donVi;
    String moTaBaoGia;
    String moTaHopDong;
    Map<String, ThuocTinh> duLieuRieng;
    Integer trangThai;

    List<GiaInfo> dsGia;

    public static VatTu toEntity(VatTuCreatingDto dto) {
        Map<String, Object> thuocTinhRiengObj = new HashMap<>(dto.getDuLieuRieng());
        return VatTu.builder()
                .ten(dto.getTen())
                .sheetLink(dto.getSheetLink())
                .donVi(dto.getDonVi())
                .moTaBaoGia(dto.getMoTaBaoGia())
                .moTaHopDong(dto.getMoTaHopDong())
                .duLieuRieng(thuocTinhRiengObj)
                .taoLuc(Instant.now())
                .trangThai(dto.getTrangThai())
                .build();
    }
}
