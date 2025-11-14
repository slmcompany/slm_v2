package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.TepTin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TepTinRepository extends JpaRepository<TepTin, Integer>, JpaSpecificationExecutor<TepTin> {
}