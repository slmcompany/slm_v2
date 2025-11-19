package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.BaiViet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaiVietRepository extends JpaRepository<BaiViet, Integer>, JpaSpecificationExecutor<BaiViet> {
}