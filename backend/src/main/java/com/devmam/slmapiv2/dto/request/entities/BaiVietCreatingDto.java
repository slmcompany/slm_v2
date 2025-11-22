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
public class BaiVietCreatingDto {
    String loaiBaiViet;
    String tieuDe;
    String duongDanYoutube;
    Integer taoBoi;
    String lienQuan;
    Integer trangThai;
}
