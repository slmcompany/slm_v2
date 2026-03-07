package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.Mien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MienRepository extends JpaRepository<Mien, Integer>, JpaSpecificationExecutor<Mien> {
    Optional<Mien> findByTenMien(String ten);
}