package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.*;
import com.devmam.slmapiv2.dto.request.entities.NguoiDungClientUpdatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.entities.NguoiDung;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.NguoiDungMapper;
import com.devmam.slmapiv2.services.impl.enities.NguoiDungService;
import com.devmam.slmapiv2.workers.CheckingAndCleanupJob;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/basic-api/nguoi-dung")
public class NguoiDungController {
    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungMapper nguoiDungMapper;

    @Autowired
    private CheckingAndCleanupJob checkingAndCleanupJob;


    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<NguoiDungDto>>> getAll() {
        return ResponseEntity.ok(
                ResponseData.<List<NguoiDungDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nguoiDungMapper.toDtoList(nguoiDungService.getAll()))
                        .build()
        );
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<NguoiDungDto>>> filter(@RequestBody BaseFilterRequest filter) {
        return ResponseEntity.ok(
                ResponseData.<Page<NguoiDungDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nguoiDungMapper.toDtoPage(nguoiDungService.filter(filter)))
                        .build()
        );
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ResponseData<NguoiDungDto>> getById(@PathVariable Integer id) {
        Optional<NguoiDung> findingNguoiDung = nguoiDungService.getOne(id);

        if (findingNguoiDung.isEmpty()) {
            throw new CommonException("Không tìm thấy người dùng id: " + id);
        }
        return ResponseEntity.ok(
                ResponseData.<NguoiDungDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nguoiDungMapper.toDto(findingNguoiDung.get()))
                        .build()
        );
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseData<NguoiDungDto>> login(@RequestBody LoginRequest loginRequest) {
        return nguoiDungService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData<NguoiDungDto>> register(@RequestBody RegisterRequest registerRequest) {
        return nguoiDungService.register(registerRequest);
    }

    @PostMapping("refresh-otp")
    public ResponseEntity<ResponseData<String>> refreshOtp(@RequestBody RefreshOtpRequest refreshOtpRequest) {
        return nguoiDungService.refreshOtp(refreshOtpRequest);
    }

    @PostMapping("/activate")
    public ResponseEntity<ResponseData<String>> activate(@RequestBody ActivateRequest activateRequest) {
        return nguoiDungService.activate(activateRequest);
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseData<NguoiDungDto>> update(@Valid @RequestBody NguoiDungClientUpdatingDto dto) {
        return nguoiDungService.updateNguoiDung(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<String>> hardDelete(@PathVariable Integer id) {
        Optional<NguoiDung> findingNguoiDung = nguoiDungService.getOne(id);
        if (findingNguoiDung.isEmpty()) {
            throw new CommonException("Không tìm thấy người dùng id: " + id);
        }

//        NguoiDungDeletingQ nguoiDungDeletingQ = NguoiDungDeletingQ.builder()
//                .id(findingNguoiDung.get().getId())
//                .thoiGianXoa(Instant.now().plus(Duration.ofDays(3)))
//                .build();
//
//        checkingAndCleanupJob.addDeletingNguoiDung(nguoiDungDeletingQ);

        nguoiDungService.delete(id);

        return ResponseEntity.ok(
                ResponseData.<String>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data("Xoá tài khoản thành công")
                        .build()
        );
    }


    @DeleteMapping("/roll-back-delete/{id}")
    public ResponseEntity<ResponseData<NguoiDungDto>> rollBackDelete(@PathVariable Integer id) {

        Optional<NguoiDung> findingNguoiDung = nguoiDungService.getOne(id);
        if (findingNguoiDung.isEmpty()) {
            throw new CommonException("Không tìm thấy người dùng id: " + id);
        }
        if (findingNguoiDung.get().getTrangThai() == 0) {
            throw new CommonException("Người dùng đã bị xoá");
        }

        Set<NguoiDungDeletingQ> deletingNguoiDungs = checkingAndCleanupJob.getDeletingNguoiDungs();
        deletingNguoiDungs.removeIf(n -> n.getId().equals(id));
        return ResponseEntity.ok(
                ResponseData.<NguoiDungDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nguoiDungMapper.toDto(findingNguoiDung.get()))
                        .build()
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseData<NguoiDungDto>> changePassword(@Valid @RequestBody ChangePasswordRequest changePassReq) {
        return nguoiDungService.changePassword(changePassReq);
    }
}
