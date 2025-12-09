package com.devmam.slmapiv2.dto.request.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NguoiDungClientUpdatingDto {

    @NonNull
    Integer id;
    String email;
    @NonNull
    String sdt;
    @NonNull
    String hoVaTen;
    Instant sinhNhat;
    String diaChi;
}
