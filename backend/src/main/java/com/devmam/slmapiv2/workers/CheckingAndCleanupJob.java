package com.devmam.slmapiv2.workers;

import com.devmam.slmapiv2.dto.request.NguoiDungDeletingQ;
import com.devmam.slmapiv2.services.impl.enities.KhachHangService;
import com.devmam.slmapiv2.services.impl.enities.NguoiDungService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
@Getter
public class CheckingAndCleanupJob {

    private final Set<NguoiDungDeletingQ> deletingNguoiDungs = new HashSet<>();

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void cleanDeletedNguoiDungRequest() {

        Instant now = Instant.now();

        deletingNguoiDungs.removeIf(nguoiDung -> {
            if (nguoiDung != null && nguoiDung.getThoiGianXoa().isBefore(now)) {
                khachHangService.changeStatus(nguoiDung.getId(), 0);
                return true;
            }
            return false;
        });

    }

    public void addDeletingNguoiDung(NguoiDungDeletingQ nguoiDungDeletingQ) {
        deletingNguoiDungs.add(nguoiDungDeletingQ);
    }
}
