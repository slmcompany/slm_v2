package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.mapper.NganhHangMapper;
import com.devmam.slmapiv2.services.impl.enities.NganhHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/basic-api/nganh-hang")
public class NganhHangController {

    @Autowired
    private NganhHangService nganhHangService;

    @Autowired
    private NganhHangMapper nganhHangMapper;

    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<NganhHangDto>>> getAll() {
        return ResponseEntity.ok(
                ResponseData.<List<NganhHangDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nganhHangMapper.toDtoList(nganhHangService.getAll()))
                        .build()
        );
    }
}
