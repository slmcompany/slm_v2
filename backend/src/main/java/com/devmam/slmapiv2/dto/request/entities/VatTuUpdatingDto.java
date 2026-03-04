package com.devmam.slmapiv2.dto.request.entities;

import com.devmam.slmapiv2.entities.commons.GiaInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class VatTuUpdatingDto {
    Integer id;
    String ten;
    Integer thoiGianBaoHanh;
    Double gm;
    Integer trangThai;
    List<GiaInfo> dsGia;
}
