package com.devmam.slmapiv2.dto.request.entities;

import com.devmam.slmapiv2.entities.HopDong;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class HopDongDefineKhachHangCreatingDto {
    Integer coSoId;
    Integer nghanhHangId;
    String ten;
    String loaiHeThong;
    String loaiPha;
    Double sanLuongToiThieu;
    Double sanLuongToiDa;
    Double giaKhungSat;
    String moTa;
    Integer khachHangId;
    Double tongGia;
    Instant taoLuc;
    List<VatTuHopDongCreatingDto> vatTuHopDongs;
    public static HopDong toEntity(HopDongDefineKhachHangCreatingDto dto){
        return HopDong.builder()
                .ten(dto.getTen())
                .loaiHeThong(dto.getLoaiHeThong())
                .loaiPha(dto.getLoaiPha())
                .sanLuongToiThieu(dto.getSanLuongToiThieu())
                .sanLuongToiDa(dto.getSanLuongToiDa())
                .giaKhungSat(dto.getGiaKhungSat())
                .moTa(dto.getMoTa())
                .tongGia(dto.getTongGia())
                .taoLuc(dto.getTaoLuc())
                .build();
    }
}
