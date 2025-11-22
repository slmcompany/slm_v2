package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.CoSo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoSoRepository extends JpaRepository<CoSo, Integer>, JpaSpecificationExecutor<CoSo> {

    Optional<CoSo> findByMa(String maCoSo);
}