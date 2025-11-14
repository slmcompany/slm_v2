package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.dto.request.entities.NhomVatTuCreatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NhomVatTuDto;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.NhomVatTu;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.NhomVatTuMapper;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class NhomVatTuService extends BaseServiceImpl<NhomVatTu, Integer> {

    @Autowired
    private NhomVatTuMapper nhomVatTuMapper;

    @Autowired
    private NganhHangService nganhHangService;

    @Autowired
    private EntityManager entityManager;

    public NhomVatTuService(JpaRepository<NhomVatTu, Integer> repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    public ResponseEntity<ResponseData<NhomVatTuDto>> create(NhomVatTuCreatingDto dto) {
        Optional<NganhHang> findingNganhHang = nganhHangService.getOne(dto.getNghanhHangId());
        if(findingNganhHang.isEmpty()){
            throw new CommonException("Không tìm thấy ngành hàng id: "+dto.getNghanhHangId());
        }

        NhomVatTu creatingNhomVatTu = NhomVatTuCreatingDto.toEntity(dto);
        creatingNhomVatTu.setNghanhHang(findingNganhHang.get());
        creatingNhomVatTu.setTaoLuc(Instant.now());
        creatingNhomVatTu = create(creatingNhomVatTu);

        return ResponseEntity.ok(
                ResponseData.<NhomVatTuDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nhomVatTuMapper.toDto(creatingNhomVatTu))
                        .build()
        );
    }
}
