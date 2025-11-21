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
    Integer vatTuId;
    String moTa;
    Integer soLuong;
    Double giaBan;
    Double gm;
    Integer thoiGianBaoHanh;
    Boolean duocBaoHanh;
    Integer trangThai;
}
