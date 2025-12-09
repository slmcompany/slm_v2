package com.devmam.slmapiv2.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {

    @NonNull
    Integer id;
    @NonNull
    String matKhauCu;
    @NonNull
    String matKhauMoi;
}
