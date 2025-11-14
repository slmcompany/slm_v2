package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.TronGoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TronGoiRepository extends JpaRepository<TronGoi, Integer>, JpaSpecificationExecutor<TronGoi> {
}