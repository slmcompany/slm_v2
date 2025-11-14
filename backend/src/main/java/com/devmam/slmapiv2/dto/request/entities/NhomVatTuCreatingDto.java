package com.devmam.slmapiv2.dto.request.entities;

import com.devmam.slmapiv2.entities.NhomVatTu;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NhomVatTuCreatingDto {
    String ma;
    Integer nghanhHangId;
    @Size(max = 400)
    String ten;
    Map<String, ThuocTinh> thuocTinhRieng;
    Double gm;
    Boolean vatTuChinh;
    Integer trangThai;

    public static NhomVatTu toEntity(NhomVatTuCreatingDto dto) {
        Map<String, Object> thuocTinhRiengObj = new HashMap<>(dto.getThuocTinhRieng());
        return NhomVatTu.builder()
                .ma(dto.getMa())
                .ten(dto.getTen())
                .thuocTinhRieng(thuocTinhRiengObj)
                .gm(dto.getGm())
                .vatTuChinh(dto.getVatTuChinh())
                .trangThai(dto.getTrangThai())
                .build();
    }
}
