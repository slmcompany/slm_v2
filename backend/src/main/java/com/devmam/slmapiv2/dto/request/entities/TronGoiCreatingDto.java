package com.devmam.slmapiv2.dto.request.entities;

import com.devmam.slmapiv2.entities.TronGoi;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TronGoiCreatingDto {
    ThongTinTronGoiCoSoCreatingDto[] tronGoiCoSos;
    Integer nhomTronGoiId;
    String ten;
    String loaiHeThong;
    String loaiPha;
    Double congSuatHeThong;
    String moTa;
    Double tongGia;
    Double gmTong;
    Boolean banChay;
    Integer trangThai;
    Set<VatTuTronGoiCreatingDto> vatTuTronGois;


    public static TronGoi toEntity(TronGoiCreatingDto dto) {
        return TronGoi.builder()
                .ten(dto.getTen())
                .loaiHeThong(dto.getLoaiHeThong())
                .loaiPha(dto.getLoaiPha())
                .congSuatHeThong(dto.getCongSuatHeThong())
                .moTa(dto.getMoTa())
                .tongGia(dto.getTongGia())
                .gmTong(dto.getGmTong())
                .banChay(dto.getBanChay())
                .trangThai(dto.getTrangThai())
                .build();
    }
}
