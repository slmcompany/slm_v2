package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.entities.CoSo;
import com.devmam.slmapiv2.repository.CoSoRepository;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoSoService extends BaseServiceImpl<CoSo, Integer> {

    @Autowired
    private EntityManager entityManager;

    public CoSoService(CoSoRepository repository) {
        super(repository);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public Optional<CoSo> findByMa(String ma){
        CoSoRepository repo = (CoSoRepository) getRepository();
        return repo.findByMa( ma);
    }
}
