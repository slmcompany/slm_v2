package com.devmam.slmapiv2.dto.request.entities;

import com.devmam.slmapiv2.dto.response.entities.VatTuDto;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class VatTuHopDongCreatingDto {
    Integer id;
    Integer vatTuId;
    @Size(max = 500)
    String moTa;
    Integer soLuong;
    Double gm;
    Double giaBan;
    Integer thoiGianBaoHanh;
    Instant baoHanhBatDau;
    Instant baoHanhKetThuc;
    Boolean duocBaoHanh;
    Integer trangThai;
}
