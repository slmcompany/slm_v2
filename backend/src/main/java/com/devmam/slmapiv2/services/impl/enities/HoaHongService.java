package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.entities.HoaHong;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class HoaHongService extends BaseServiceImpl<HoaHong, Integer> {

    @Autowired
    private EntityManager entityManager;

    public HoaHongService(JpaRepository<HoaHong, Integer> repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
