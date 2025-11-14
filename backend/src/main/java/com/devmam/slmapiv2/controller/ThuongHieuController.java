package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.mapper.ThuongHieuMapper;
import com.devmam.slmapiv2.services.impl.enities.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/basic-api/thuong-hieu")
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
}
