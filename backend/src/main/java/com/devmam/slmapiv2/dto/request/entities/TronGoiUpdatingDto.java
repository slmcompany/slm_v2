package com.devmam.slmapiv2.dto.request.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TronGoiUpdatingDto {
    Integer id;
    String ten;
    String loaiHeThong;
    String loaiPha;
    Double congSuatHeThong;
    Double sanLuongToiThieu;
    Double sanLuongToiDa;
    Double tongGia;
    Double gmTong;
    Boolean banChay;
    Integer trangThai;
    List<VatTuTronGoiUpdatingDto> vatTuTronGois;
}
