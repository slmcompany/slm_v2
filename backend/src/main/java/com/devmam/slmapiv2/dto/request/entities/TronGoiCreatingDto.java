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
    String moTa;
    Double tongGia;
    Boolean banChay;
    Set<VatTuTronGoiCreatingDto> vatTuTronGois;

    public static TronGoi toEntity(TronGoiCreatingDto dto) {
        return TronGoi.builder()
                .ten(dto.getTen())
                .loaiHeThong(dto.getLoaiHeThong())
                .loaiPha(dto.getLoaiPha())
                .moTa(dto.getMoTa())
                .tongGia(dto.getTongGia())
                .banChay(dto.getBanChay())
                .build();
    }
}
