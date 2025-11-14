package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.entities.CoSo;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CoSoService extends BaseServiceImpl<CoSo, Integer> {

    @Autowired
    private EntityManager entityManager;

    public CoSoService(JpaRepository<CoSo, Integer> repository) {
        super(repository);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
