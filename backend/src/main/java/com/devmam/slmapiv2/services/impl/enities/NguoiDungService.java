package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.entities.NguoiDung;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class NguoiDungService extends BaseServiceImpl<NguoiDung,Integer> {

    @Autowired
    private EntityManager entityManager;

    public NguoiDungService(JpaRepository<NguoiDung, Integer> repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
