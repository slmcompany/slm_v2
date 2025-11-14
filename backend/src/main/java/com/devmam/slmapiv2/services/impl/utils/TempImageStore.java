package com.devmam.slmapiv2.services.impl.utils;

import com.devmam.slmapiv2.entities.commons.TempImageInfo;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class TempImageStore {
    // Thread-safe HashSet
    private final Set<TempImageInfo> tempImages = ConcurrentHashMap.newKeySet();

    public void add(TempImageInfo info) {
        tempImages.add(info);
    }

    public Optional<TempImageInfo> get(UUID tempId) {
        return tempImages.stream()
                .filter(img -> img.getTempId().equals(tempId))
                .findFirst();
    }

    public void remove(UUID tempId) {
        tempImages.removeIf(img -> img.getTempId().equals(tempId));
    }

    public List<TempImageInfo> getByIds(List<UUID> tempIds) {
        return tempImages.stream()
                .filter(img -> tempIds.contains(img.getTempId()))
                .collect(Collectors.toList());
    }

    public List<TempImageInfo> getExpiredImages(int hoursOld) {
        Instant cutoffTime = LocalDateTime.now().minusHours(hoursOld).toInstant(java.time.ZoneOffset.UTC);
        return tempImages.stream()
                .filter(img -> img.getUploadedAt().isBefore(cutoffTime))
                .collect(Collectors.toList());
    }

    public void removeAll(List<UUID> tempIds) {
        tempImages.removeIf(img -> tempIds.contains(img.getTempId()));
    }
}
