package com.devmam.slmapiv2.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@ToString
public class RegisterRequest {
    String sdt;
    String matKhau;
    String hoVaTen;
    String email;
    String maCoSo;
}
