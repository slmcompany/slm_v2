package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.entities.TepTin;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.TepTinMapper;
import com.devmam.slmapiv2.services.MinioService;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TepTinService extends BaseServiceImpl<TepTin, Integer> {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MinioService minioService;
    @Autowired
    private TepTinMapper tepTinMapper;

    public TepTinService(JpaRepository<TepTin, Integer> repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    public ResponseEntity<ResponseData<String>> hardDelete(Integer id) {

        Optional<TepTin> tepTin = getOne(id);

        if (tepTin.isEmpty()) {
            throw new CommonException("Không tìm thấy tệp tin id: " + id);
        }

        minioService.delete(tepTin.get().getTenLuuTru());

        delete(id);

        return ResponseEntity.ok(
                ResponseData.<String>builder()
                        .status(200)
                        .error(null)
                        .data("Success")
                        .message("Success").build());
    }
}
