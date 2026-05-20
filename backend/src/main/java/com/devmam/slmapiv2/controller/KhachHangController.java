package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.KhachHangDto;
import com.devmam.slmapiv2.mapper.KhachHangMapper;
import com.devmam.slmapiv2.services.impl.enities.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic-api/khach-hang")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private KhachHangMapper  khachHangMapper;

    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<KhachHangDto>>> filter(@RequestBody BaseFilterRequest filter){
        return ResponseEntity.ok(
                ResponseData.<Page<KhachHangDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(khachHangMapper.toDtoPage(khachHangService.filter(filter)))
                        .build()
        );
    }
}
