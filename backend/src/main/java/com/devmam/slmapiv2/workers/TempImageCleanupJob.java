//package com.devmam.slmapiv2.workers;
//
//import com.devmam.slmapiv2.entities.commons.TempImageInfo;
//import com.devmam.slmapiv2.services.MinioService;
//import com.devmam.slmapiv2.services.impl.utils.TempImageStore;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@Slf4j
//public class TempImageCleanupJob {
//
//    @Autowired
//    private TempImageStore tempImageStore;
//
//    @Autowired
//    private MinioService minioService;
//
//    @Scheduled(cron = "0 0 * * * *")
//    public void cleanupExpiredTempImages() {
//        log.info("Starting temp image cleanup job...");
//
//        List<TempImageInfo> expiredImages =
//                tempImageStore.getExpiredImages(24);
//
//        if (expiredImages.isEmpty()) {
//            log.info("No expired temp images found");
//            return;
//        }
//
//        log.info("Found {} expired temp images", expiredImages.size());
//        for (TempImageInfo imageInfo : expiredImages) {
//            try {
//                minioService.delete(imageInfo.getTempId().toString());
//                tempImageStore.remove(imageInfo.getTempId());
//                log.info("Cleaned up expired temp image: {}", imageInfo.getTempId());
//
//            } catch (Exception e) {
//                log.error("Failed to cleanup temp image: {}", imageInfo.getTempId(), e);
//            }
//        }
//        log.info("Cleanup job completed. Removed {} images", expiredImages.size());
//    }
//
//}
