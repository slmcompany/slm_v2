package com.devmam.slmapiv2.dto.request.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class VatTuTronGoiUpdatingDto {
    Integer id;
    Boolean duocBaoHanh;
    Integer thoiGianBaoHanh;
    Boolean duocXem;
    Integer trangThai;
}
