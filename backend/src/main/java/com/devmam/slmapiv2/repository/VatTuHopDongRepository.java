package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.VatTuHopDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VatTuHopDongRepository extends JpaRepository<VatTuHopDong, Integer>, JpaSpecificationExecutor<VatTuHopDong> {
}