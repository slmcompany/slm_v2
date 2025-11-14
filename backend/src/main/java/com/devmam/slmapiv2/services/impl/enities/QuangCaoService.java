package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.entities.QuangCao;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class QuangCaoService extends BaseServiceImpl<QuangCao, Integer> {

    @Autowired
    private EntityManager entityManager;

    public QuangCaoService(JpaRepository<QuangCao, Integer> repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
