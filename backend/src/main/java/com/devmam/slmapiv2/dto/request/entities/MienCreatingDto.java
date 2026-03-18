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
@ToString
public class MienCreatingDto {
    String tenMien;
    Integer coSoId;
    Integer thoiGianThayDoiHotline;
    List<ThongTinMienCreatingDto> thongTinMiens;
}
