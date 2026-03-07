package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.entities.ThongTinTenMien;
import com.devmam.slmapiv2.repository.ThongTinTenMienRepository;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThongTinTenMienService extends BaseServiceImpl<ThongTinTenMien, Integer> {

    @Autowired
    private EntityManager entityManager;

    public ThongTinTenMienService(ThongTinTenMienRepository repository) {
        super(repository);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
