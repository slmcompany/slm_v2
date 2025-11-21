package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.entities.KhachHang;
import com.devmam.slmapiv2.repository.KhachHangRepository;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class KhachHangService extends BaseServiceImpl<KhachHang,Integer> {

    @Autowired
    private EntityManager entityManager;
    public KhachHangService(KhachHangRepository repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }


    public List<KhachHang> findByDaBanDuocHangAndTaoLucIsBefore(Boolean daBanDuocHang, Instant taoLucBefore){
        KhachHangRepository khachHangRepository = (KhachHangRepository) repository;
        return (khachHangRepository.findByDaBanDuocHangAndTaoLucIsBefore(daBanDuocHang,taoLucBefore));
    }
}
