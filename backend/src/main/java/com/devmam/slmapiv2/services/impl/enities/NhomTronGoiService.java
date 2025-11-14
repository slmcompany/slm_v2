package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.dto.request.entities.NhomTronGoiCreatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NhomTronGoiDto;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.NhomTronGoi;
import com.devmam.slmapiv2.entities.ThuongHieu;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.NhomTronGoiMapper;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class NhomTronGoiService extends BaseServiceImpl<NhomTronGoi, Integer> {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ThuongHieuService thuongHieuService;
    @Autowired
    private NganhHangService nganhHangService;
    @Autowired
    private NhomTronGoiMapper nhomTronGoiMapper;

    public NhomTronGoiService(JpaRepository<NhomTronGoi, Integer> repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    public ResponseEntity<ResponseData<NhomTronGoiDto>> create(NhomTronGoiCreatingDto dto) {

        Optional<NganhHang> findingNganhHang = nganhHangService.getOne(dto.getNghanhHangId());
        if (findingNganhHang.isEmpty()) {
            throw new CommonException("Không tìm thấy ngành hàng id: " + dto.getNghanhHangId());
        }

        Optional<ThuongHieu> findingThuongHieuTamPin = thuongHieuService.getOne(dto.getThuongHieuTamPinId());
        if (findingThuongHieuTamPin.isEmpty()) {
            throw new CommonException("Không tìm thấy thương hiệu tấm pin id: " + dto.getThuongHieuTamPinId());
        }

        Optional<ThuongHieu> findingThuongHieuInverter = thuongHieuService.getOne(dto.getThuongHieuInverterId());
        if (findingThuongHieuInverter.isEmpty()) {
            throw new CommonException("Không tìm thấy thương hiệu inverter id: " + dto.getThuongHieuInverterId());
        }

        Optional<ThuongHieu> findingThuongHieuPinLuuTru = thuongHieuService.getOne(dto.getThuongHieuPinLuuTruId());
        if (findingThuongHieuPinLuuTru.isEmpty()) {
            throw new CommonException("Không tìm thấy thương hiệu pin lưu trữ id: " + dto.getThuongHieuPinLuuTruId());
        }

        NhomTronGoi creatingNhomTronGoi = NhomTronGoi.builder()
                .nghanhHang(findingNganhHang.get())
                .ten(dto.getTen())
                .thuongHieuTamPin(findingThuongHieuTamPin.get())
                .thuongHieuInverter(findingThuongHieuInverter.get())
                .thuongHieuPinLuuTru(findingThuongHieuPinLuuTru.get())
                .build();
        creatingNhomTronGoi = create(creatingNhomTronGoi);
        return ResponseEntity.ok(
                ResponseData.<NhomTronGoiDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nhomTronGoiMapper.toDto(creatingNhomTronGoi))
                        .build()
        );
    }
}
