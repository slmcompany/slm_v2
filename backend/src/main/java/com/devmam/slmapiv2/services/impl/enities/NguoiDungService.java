package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.RoleType;
import com.devmam.slmapiv2.dto.request.LoginRequest;
import com.devmam.slmapiv2.dto.request.RegisterRequest;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.entities.CoSo;
import com.devmam.slmapiv2.entities.NguoiDung;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.NguoiDungMapper;
import com.devmam.slmapiv2.repository.NguoiDungRepository;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class NguoiDungService extends BaseServiceImpl<NguoiDung, Integer> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CoSoService coSoService;

    @Autowired
    private NguoiDungMapper nguoiDungMapper;


    public NguoiDungService(NguoiDungRepository repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ResponseEntity<ResponseData<NguoiDungDto>> login(LoginRequest loginRequest) {
        NguoiDungRepository repo = (NguoiDungRepository) super.getRepository();

        Optional<NguoiDung> findingNguoiDung = repo.findBySdt(loginRequest.getSdt());
        if (findingNguoiDung.isPresent() && findingNguoiDung.get().getMatKhau().equals(loginRequest.getMatKhau())) {
            findingNguoiDung.get().setMatKhau("");
            return ResponseEntity.ok(
                    ResponseData.<NguoiDungDto>builder()
                            .status(200)
                            .error(null)
                            .message("Success")
                            .data(nguoiDungMapper.toDto(findingNguoiDung.get()))
                            .build()
            );
        }
        throw new CommonException("Tài khoản hoặc mật khẩu không đúng");
    }

    public ResponseEntity<ResponseData<NguoiDungDto>> register(RegisterRequest registerRequest) {
        NguoiDungRepository repo = (NguoiDungRepository) super.getRepository();
        if (registerRequest.getMaCoSo() == null || registerRequest.getMaCoSo().isEmpty()) {
            registerRequest.setMaCoSo("HN");
        }
        Optional<CoSo> coSoFinding = coSoService.findByMa(registerRequest.getMaCoSo());
        if (coSoFinding.isEmpty()) {
            throw new CommonException("Không tim thaấy cơ sở ma: HN");
        }
        NguoiDung nguoiDungCreating = NguoiDung.builder()
                .coSo(coSoFinding.get())
                .taoLuc(Instant.now())
                .phanQuyen(RoleType.CUSTOMER.name())
                .email(registerRequest.getSdt())
                .sdt(registerRequest.getSdt())
                .matKhau(registerRequest.getMatKhau())
                .hoVaTen(registerRequest.getHoVaTen())
                .phanTramHoaHong(0.0)
                .tongHoaHong(0.0)
                .gioiTinh(true)
                .trangThai(1)
                .build();

        nguoiDungCreating = repo.save(nguoiDungCreating);

        return ResponseEntity.ok(
                ResponseData.<NguoiDungDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nguoiDungMapper.toDto(nguoiDungCreating))
                        .build()
        );
    }
}
