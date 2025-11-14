package com.devmam.slmapiv2.entities.commons;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class GiaInfo {
    @JsonProperty(value = "maCoSo", access = JsonProperty.Access.READ_WRITE)
    @JsonAlias("ma_co_so")
    String maCoSo;

    @JsonProperty(value = "tenCoSo", access = JsonProperty.Access.READ_WRITE)
    @JsonAlias("ten_co_so")
    String tenCoSo;

    @JsonProperty(value = "giaNhap", access = JsonProperty.Access.READ_WRITE)
    @JsonAlias("gia_nhap")
    Float giaNhap;

    @JsonProperty(value = "giaBan", access = JsonProperty.Access.READ_WRITE)
    @JsonAlias("gia_ban")
    Float giaBan;
}
