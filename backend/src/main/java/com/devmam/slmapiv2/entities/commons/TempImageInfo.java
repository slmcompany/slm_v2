package com.devmam.slmapiv2.entities.commons;


import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TempImageInfo {
    private UUID tempId;
    private String imageUrl;
    private Integer uploadedBy; // userId
    private Instant uploadedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempImageInfo)) return false;
        return tempId.equals(((TempImageInfo) o).tempId);
    }

    @Override
    public int hashCode() {
        return tempId.hashCode();
    }
}
