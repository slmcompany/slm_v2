package com.devmam.slmapiv2.dto.request.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@ToString
public class BaiVietUpdatingDto {
    Integer id;
    String loaiBaiViet;
    String tieuDe;
    String duongDanYoutube;
    String lienQuan;
    Integer trangThai;
}
