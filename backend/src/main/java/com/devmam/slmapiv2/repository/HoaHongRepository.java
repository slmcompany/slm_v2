package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.HoaHong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HoaHongRepository extends JpaRepository<HoaHong, Integer>, JpaSpecificationExecutor<HoaHong> {
}