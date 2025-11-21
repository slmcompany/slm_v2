//package com.devmam.slmapiv2.workers;
//
//import com.devmam.slmapiv2.entities.KhachHang;
//import com.devmam.slmapiv2.services.impl.enities.KhachHangService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//@Component
//@Slf4j
//public class KhachHangCheckingAndCleanupJob {
//    @Autowired
//    private KhachHangService khachHangService;
//
//    @Scheduled(cron = "0 0 0 * * *")
//    public void checkAndCleanupExpiredKhachHangs() {
//        log.info("Starting khach hang cleanup job...");
//        Instant now = Instant.now();
//
//        List<KhachHang> needToCleanKhachHangList = khachHangService
//                .findByDaBanDuocHangAndTaoLucIsBefore(false,
//                        Instant.now().plus(20, ChronoUnit.DAYS));
//        String dsSdt = "";
//        for (KhachHang khachHang : needToCleanKhachHangList) {
//            dsSdt += khachHang.getSdt() + ", ";
//            log.info("Cleaned up expired khach hang: {}", khachHang.getSdt());
//            khachHangService.delete(khachHang.getId());
//        }
//    }
//}
