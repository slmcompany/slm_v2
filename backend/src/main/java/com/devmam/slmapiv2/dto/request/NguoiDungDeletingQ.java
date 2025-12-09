package com.devmam.slmapiv2.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@ToString
@EqualsAndHashCode( of = {"id"})
public class NguoiDungDeletingQ {
    Integer id;
    Instant thoiGianXoa;
}
