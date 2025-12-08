package com.devmam.slmapiv2.repository;

import com.devmam.slmapiv2.entities.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer>, JpaSpecificationExecutor<NguoiDung> {

    @Query("select n from NguoiDung n where REPLACE(n.sdt, ' ', '') = REPLACE(:sdt, ' ', '')")
    Optional<NguoiDung> findBySdt(@Param("sdt") String sdt);
}