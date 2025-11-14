package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.ThongTinGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ThongTinGiaRepository extends JpaRepository<ThongTinGia, Integer>, JpaSpecificationExecutor<ThongTinGia> {
}