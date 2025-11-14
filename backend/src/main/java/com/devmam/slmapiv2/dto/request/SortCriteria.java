package com.devmam.slmapiv2.dto.request;

import com.devmam.slmapiv2.constant.enums.SortDirection;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SortCriteria {
    String fieldName;
    SortDirection direction;
}
