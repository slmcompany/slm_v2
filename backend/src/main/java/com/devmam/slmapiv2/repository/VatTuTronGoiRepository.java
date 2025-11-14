package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.VatTuTronGoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VatTuTronGoiRepository extends JpaRepository<VatTuTronGoi, Integer>, JpaSpecificationExecutor<VatTuTronGoi>{
}