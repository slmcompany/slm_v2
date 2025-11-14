package com.devmam.slmapiv2.dto.request;

import com.devmam.slmapiv2.constant.enums.FilterOperation;
import com.devmam.slmapiv2.constant.enums.FilterLogicType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterCriteria {
    String fieldName;
    FilterOperation operation;
    Object value;
    @Builder.Default
    FilterLogicType logicType = FilterLogicType.AND;
}
