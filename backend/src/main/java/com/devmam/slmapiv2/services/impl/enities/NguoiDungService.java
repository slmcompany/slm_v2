package com.devmam.slmapiv2.services.impl.enities;

import com.devmam.slmapiv2.constant.enums.RoleType;
import com.devmam.slmapiv2.dto.request.*;
import com.devmam.slmapiv2.dto.request.entities.NguoiDungClientUpdatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.entities.CoSo;
import com.devmam.slmapiv2.entities.NguoiDung;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.NguoiDungMapper;
import com.devmam.slmapiv2.repository.NguoiDungRepository;
import com.devmam.slmapiv2.services.CalcService;
import com.devmam.slmapiv2.services.EmailService;
import com.devmam.slmapiv2.services.impl.BaseServiceImpl;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NguoiDungService extends BaseServiceImpl<NguoiDung, Integer> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CoSoService coSoService;

    @Autowired
    private NguoiDungMapper nguoiDungMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CalcService calcService;

    public NguoiDungService(NguoiDungRepository repository) {
        super(repository);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ResponseEntity<ResponseData<NguoiDungDto>> login(LoginRequest loginRequest) {
        Optional<NguoiDung> findingNguoiDung = findBySdtOrEmail(loginRequest.getSdt(), loginRequest.getSdt());
        if (findingNguoiDung.isEmpty()) {
            throw new CommonException("Tài khoản hoặc mật khẩu không đúng");
        }
        NguoiDung nguoiDung = findingNguoiDung.get();
        if (nguoiDung.getMatKhau().equals(loginRequest.getMatKhau()) && nguoiDung.getTrangThai() == 1) {
            NguoiDungDto dto = nguoiDungMapper.toDto(nguoiDung);
            dto.setMatKhau("");
            return ResponseEntity.ok(
                    ResponseData.<NguoiDungDto>builder()
                            .status(200)
                            .error(null)
                            .message("Success")
                            .data(dto)
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
            throw new CommonException("Không tim thấy cơ sở ma: HN");
        }
        String sdt = registerRequest.getSdt();
        if (sdt != null && !sdt.trim().isEmpty()) {
            sdt = sdt.replaceAll("[^0-9]", "");
        } else {
            sdt = null;
        }
        Optional<NguoiDung> findingNguoiDungBySdt = findBySdtOrEmail(sdt, registerRequest.getEmail());
        if (findingNguoiDungBySdt.isPresent() &&
                (sdt != null || findingNguoiDungBySdt.get().getEmail().equals(registerRequest.getEmail()))
        ) {
            throw new CommonException("Tài khoản đã tồn tại: " + sdt + " & " + registerRequest.getEmail());
        }

        Instant now = Instant.now();
        String otp = calcService.getRandomActiveCode(6l);

        Map<String, Object> params = new HashMap<>();
        params.put("userName", registerRequest.getHoVaTen() != null ? registerRequest.getHoVaTen() : registerRequest.getEmail());
        params.put("activationCode", otp);
        params.put("expiryTime", "5 phút");

        emailService.sendHtmlEmailFromTemplate(registerRequest.getEmail(), "Mã kích hoạt tài khoản SLM", "activation.html", params);

        NguoiDung nguoiDungCreating = NguoiDung.builder()
                .coSo(coSoFinding.get())
                .taoLuc(now)
                .phanQuyen(RoleType.CUSTOMER.name())
                .email(registerRequest.getEmail().trim().toLowerCase())
                .sdt(sdt)
                .matKhau(registerRequest.getMatKhau())
                .hoVaTen(registerRequest.getHoVaTen())
                .phanTramHoaHong(0.0)
                .tongHoaHong(0.0)
                .gioiTinh(true)
                .otp(otp)
                .otpGuiLuc(now)
                .trangThai(0)
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


    @Transactional
    public ResponseEntity<ResponseData<NguoiDungDto>> updateNguoiDung(NguoiDungClientUpdatingDto dto) {
        Optional<NguoiDung> findingNguoiDung = getOne(dto.getId());

        if (findingNguoiDung.isEmpty()) {
            throw new CommonException("Không tìm thấy người dùng id: " + dto.getId());
        }

        Optional<NguoiDung> findingNguoiDungBySdt = findBySdtOrEmail(dto.getSdt(), dto.getSdt());

        if (findingNguoiDungBySdt.isPresent() && !findingNguoiDungBySdt.get().getId().equals(dto.getId())) {
            throw new CommonException("Số điện thoại đã có người sử dụng");
        }

        NguoiDung nguoiDung = findingNguoiDung.get();


        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            dto.setEmail(dto.getSdt());
        } else {
            dto.setEmail(dto.getEmail().trim().toLowerCase());
        }
        nguoiDung.setSdt(dto.getSdt());
        nguoiDung.setHoVaTen(dto.getHoVaTen());
        nguoiDung.setSinhNhat(dto.getSinhNhat());
        nguoiDung.setDiaChi(dto.getDiaChi());

        nguoiDung = update(dto.getId(), nguoiDung);

        return ResponseEntity.ok(
                ResponseData.<NguoiDungDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nguoiDungMapper.toDto(nguoiDung))
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseData<NguoiDungDto>> changePassword(ChangePasswordRequest changePassReq) {
        Optional<NguoiDung> findingNguoiDung = getOne(changePassReq.getId());
        if (findingNguoiDung.isEmpty()) {
            throw new CommonException("Không tìm thấy người dùng id: " + changePassReq.getId());
        }
        NguoiDung nguoiDung = findingNguoiDung.get();
        if (!nguoiDung.getMatKhau().equals(changePassReq.getMatKhauCu())) {
            throw new CommonException("Mật khẩu cũ không chính xác");
        }
        nguoiDung.setMatKhau(changePassReq.getMatKhauMoi());
        nguoiDung = update(changePassReq.getId(), nguoiDung);
        NguoiDungDto dto = nguoiDungMapper.toDto(nguoiDung);
        dto.setMatKhau("");
        return ResponseEntity.ok(
                ResponseData.<NguoiDungDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(dto)
                        .build()
        );
    }

    public Optional<NguoiDung> findBySdtOrEmail(String sdt, String email) {
        if (sdt == null || sdt.isEmpty() || email == null || email.isEmpty()) {
            return Optional.empty();
        }
        NguoiDungRepository repo = (NguoiDungRepository) super.getRepository();
        List<NguoiDung> nguoiDungs = repo.findBySdtOrEmail(sdt, email);
        return nguoiDungs.isEmpty() ? Optional.empty() : Optional.of(nguoiDungs.get(0));
    }

    public Optional<NguoiDung> findByOtp(String otp) {
        NguoiDungRepository repo = (NguoiDungRepository) super.getRepository();
        List<NguoiDung> nguoiDungs = repo.findByOtp(otp);
        NguoiDung nguoiDung = null;
        Instant temp = null;
        for (NguoiDung n : nguoiDungs) {
            if (temp == null || n.getOtpGuiLuc().isAfter(temp)) {
                nguoiDung = n;
                temp = n.getOtpGuiLuc();
            }
        }
        return nguoiDung == null ? Optional.empty() : Optional.of(nguoiDung);
    }

    @Transactional
    public ResponseEntity<ResponseData<String>> activate(ActivateRequest activateRequest) {
        Optional<NguoiDung> findingNguoiDung = findBySdtOrEmail(activateRequest.getEmail(), activateRequest.getEmail());
        if (findingNguoiDung.isEmpty()) {
            throw new CommonException("Không tìm thấy người dùng email: " + activateRequest.getEmail());
        }

        if (findingNguoiDung.get().getTrangThai() == 1) {
            throw new CommonException("Tài khoản đã được kích hoạt vui lòng không sử dụng lại otp");
        }

        if (!findingNguoiDung.get().getOtp().equals(activateRequest.getOtp())) {
            throw new CommonException("Mã kích hoạt sai hoặc hết hạn");
        }

        NguoiDung nguoiDung = findingNguoiDung.get();
        Instant now = Instant.now();

        if (nguoiDung.getOtpGuiLuc().plusSeconds(300l).isBefore(now)) {
            throw new CommonException("Mã kích hoạt sai hoặc hết hạn");
        }

        nguoiDung.setTrangThai(1);
        nguoiDung.setOtp("87@Slm");
        update(nguoiDung.getId(), nguoiDung);

        return ResponseEntity.ok(
                ResponseData.<String>builder()
                        .status(200)
                        .error(null)
                        .data("Kích hoạt tài khoản thành công")
                        .message("Kích hoạt tài khoản thành công")
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseData<String>> refreshOtp(RefreshOtpRequest refreshOtpRequest) {
        Optional<NguoiDung> findingNguoiDung = findBySdtOrEmail(refreshOtpRequest.getEmail(), refreshOtpRequest.getEmail());

        if (findingNguoiDung.isEmpty()) {
            throw new CommonException("Tài khoản không tồn tại: " + refreshOtpRequest.getEmail());
        }

        Instant now = Instant.now();

        if (findingNguoiDung.get().getOtpGuiLuc().plusSeconds(300l).isAfter(now)) {
            throw new CommonException("Mã kích hoạt vẫn còn hiệu lực vui lòng thử lại sau");
        }

        String otp = calcService.getRandomActiveCode(6l);

        findingNguoiDung.get().setOtp(otp);
        findingNguoiDung.get().setOtpGuiLuc(now);
        update(findingNguoiDung.get().getId(), findingNguoiDung.get());

        NguoiDung nguoiDung = findingNguoiDung.get();

        Map<String, Object> params = new HashMap<>();
        params.put("userName", nguoiDung.getHoVaTen() != null ? nguoiDung.getHoVaTen() : nguoiDung.getEmail());
        params.put("activationCode", otp);
        params.put("expiryTime", "5 phút");

        emailService.sendHtmlEmailFromTemplate(nguoiDung.getEmail(), "Mã kích hoạt tài khoản SLM", "activation.html", params);

        return ResponseEntity.ok(
                ResponseData.<String>builder()
                        .status(200)
                        .error(null)
                        .data("Mã kích hoạt đã được gửi lại thành công")
                        .message("Mã kích hoạt đã được gửi lại thành công")
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseData<String>> forgotPassword(String emailOrSdt) {
        Optional<NguoiDung> findingNguoiDung = findBySdtOrEmail(emailOrSdt, emailOrSdt);

        if (findingNguoiDung.isEmpty()) {
            throw new CommonException("Không tìm thấy tài khoản: " + emailOrSdt);
        }

        String otp = calcService.getRandomActiveCode(6l);
        NguoiDung nguoiDung = findingNguoiDung.get();

        if (nguoiDung.getEmail() == null || nguoiDung.getEmail().isEmpty()) {
            throw new CommonException("Tài khoản không có được cài đặt email không thể thay đổi mật khẩu bằng phương thức này");
        }

        nguoiDung.setOtp(otp);
        nguoiDung.setOtpGuiLuc(Instant.now());
        update(findingNguoiDung.get().getId(), nguoiDung);

        Map<String, Object> params = new HashMap<>();
        params.put("activationCode", otp);
        params.put("userName", nguoiDung.getHoVaTen() != null ? nguoiDung.getHoVaTen() : nguoiDung.getEmail());
        params.put("expiryTime", "5 phút");

        emailService.sendHtmlEmailFromTemplate(nguoiDung.getEmail(), "Yêu cầu thay đổi mật khẩu", "forgot-password.html", params);

        return ResponseEntity.ok(
                ResponseData.<String>builder()
                        .status(200)
                        .error(null)
                        .message("Yêu cầu thay đổi mật khẩu được gửi thành công")
                        .data("Yêu cầu thay đổi mật khẩu được gửi thành công")
                        .build()
        );
    }

    @Transactional
    public ResponseEntity<ResponseData<String>> resetPassword(ResetPasswordRequest resetPasswordRequest) {

        Optional<NguoiDung> findingNguoiDung = findByOtp(resetPasswordRequest.getOtp());

        if (findingNguoiDung.isEmpty()) {
            throw new CommonException("Mã kích hoạt không tồn tại hoặc đã hết hạn");
        }

        NguoiDung nguoiDung = findingNguoiDung.get();

        Instant now = Instant.now();

        Instant expiryTime = nguoiDung.getOtpGuiLuc().plusSeconds(300l);

        if (expiryTime.isBefore(now)) {
            throw new CommonException("Mã kích hoạt không tồn tại hoặc đã hết hạn");
        }

        nguoiDung.setOtp("87@Slm");
        nguoiDung.setMatKhau(resetPasswordRequest.getPassword());

        update(nguoiDung.getId(), nguoiDung);

        return ResponseEntity.ok(
                ResponseData.<String>builder()
                        .status(200)
                        .error(null)
                        .message("Tài khoản đã được kích hoạt thành công")
                        .data("Tài khoản đã được kích hoạt thành công")
                        .build()
        );

    }
}
