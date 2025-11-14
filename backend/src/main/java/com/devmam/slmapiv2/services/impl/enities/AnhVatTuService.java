package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.entities.AnhVatTu;
import com.devmam.slmapiv2.repository.AnhVatTuRepository;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnhVatTuService extends BaseServiceImpl<AnhVatTu, Integer> {

    @Autowired
    private EntityManager entityManager;

    public AnhVatTuService(AnhVatTuRepository repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
