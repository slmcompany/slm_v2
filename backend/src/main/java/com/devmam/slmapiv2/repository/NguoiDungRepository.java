package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer>, JpaSpecificationExecutor<NguoiDung> {

    Optional<NguoiDung> findBySdt(String sdt);
}