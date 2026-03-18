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
public class MienUpdatingDto {
    Integer id;
    String tenMien;
    Integer coSoId;
    Integer thoiGianThayDoiHotline;
    Integer trangThai;
    List<ThongTinMienUpdatingDto> thongTinMiens;
}