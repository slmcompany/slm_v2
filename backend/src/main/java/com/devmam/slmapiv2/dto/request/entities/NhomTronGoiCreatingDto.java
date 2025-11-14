package com.devmam.slmapiv2.dto.request.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NhomTronGoiCreatingDto {
    Integer nghanhHangId;
    @Size(max = 400)
    String ten;
    Integer thuongHieuTamPinId;
    Integer thuongHieuInverterId;
    Integer thuongHieuPinLuuTruId;
}
