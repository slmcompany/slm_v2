package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.QuangCao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuangCaoRepository extends JpaRepository<QuangCao, Integer>, JpaSpecificationExecutor<QuangCao> {
}