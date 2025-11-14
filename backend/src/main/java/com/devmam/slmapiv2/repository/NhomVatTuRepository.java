package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.NhomVatTu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NhomVatTuRepository extends JpaRepository<NhomVatTu, Integer>, JpaSpecificationExecutor<NhomVatTu> {
}