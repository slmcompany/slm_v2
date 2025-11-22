package com.devmam.slmapiv2.dto.request.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class QuangCaoUpdatingDto {
    Integer id;
    Integer nganhHangId;
    String tieuDe;
    String viTri;
    Boolean hoatDong;
    Integer trangThai;
}
