package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.dto.request.entities.LoginRequest;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.entities.NguoiDung;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.NguoiDungMapper;
import com.devmam.slmapiv2.repository.NguoiDungRepository;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NguoiDungService extends BaseServiceImpl<NguoiDung,Integer> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private NguoiDungMapper nguoiDungMapper;

    public NguoiDungService(NguoiDungRepository repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ResponseEntity<ResponseData<NguoiDungDto>> login(LoginRequest loginRequest){
        NguoiDungRepository repo = (NguoiDungRepository) super.getRepository();

        Optional<NguoiDung> findingNguoiDung = repo.findBySdt(loginRequest.getSdt());
        if(findingNguoiDung.isPresent() && findingNguoiDung.get().getMatKhau().equals(loginRequest.getMatKhau())){
            findingNguoiDung.get().setMatKhau(null);
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
}
