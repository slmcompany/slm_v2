package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.request.LoginRequest;
import com.devmam.slmapiv2.dto.request.RegisterRequest;
import com.devmam.slmapiv2.dto.request.entities.NguoiDungUpdatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.mapper.NguoiDungMapper;
import com.devmam.slmapiv2.services.impl.enities.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basic-api/nguoi-dung")
public class NguoiDungController {
    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungMapper nguoiDungMapper;


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


    @PostMapping("/login")
    public ResponseEntity<ResponseData<NguoiDungDto>> login(@RequestBody LoginRequest loginRequest) {
        return nguoiDungService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData<NguoiDungDto>> register(@RequestBody RegisterRequest registerRequest) {
        return nguoiDungService.register(registerRequest);
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseData<NguoiDungDto>> update(@RequestBody NguoiDungUpdatingDto dto) {
        return null;
    }
}
