package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.mapper.ThuongHieuMapper;
import com.devmam.slmapiv2.services.impl.enities.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basic-api/thuong-hieu")
public class ThuongHieuController {

    @Autowired
    private ThuongHieuService thuongHieuService;

    @Autowired
    private ThuongHieuMapper thuongHieuMapper;

    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<ThuongHieuDto>>> getAll() {
        return ResponseEntity.ok(
                ResponseData.<List<ThuongHieuDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(thuongHieuMapper.toDtoList(thuongHieuService.getAll()))
                        .build()
        );
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<ThuongHieuDto>>> filter(@RequestBody BaseFilterRequest filter) {
        return ResponseEntity.ok(
                ResponseData.<Page<ThuongHieuDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(thuongHieuMapper.toDtoPage(thuongHieuService.filter(filter)))
                        .build()
        );
    }
}
