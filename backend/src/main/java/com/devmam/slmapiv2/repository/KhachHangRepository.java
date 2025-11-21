package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer>, JpaSpecificationExecutor<KhachHang> {


    List<KhachHang> findByDaBanDuocHangAndTaoLucIsBefore(Boolean daBanDuocHang, Instant taoLucBefore);
}