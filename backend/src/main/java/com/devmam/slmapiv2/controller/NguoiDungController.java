package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.entities.NguoiDung;
import com.devmam.slmapiv2.mapper.NguoiDungMapper;
import com.devmam.slmapiv2.services.impl.enities.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/basic-api/nguoi-dung")
public class NguoiDungController {
    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private NguoiDungMapper nguoiDungMapper;


    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<NguoiDungDto>>> getAll(){
        return ResponseEntity.ok(
                ResponseData.<List<NguoiDungDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nguoiDungMapper.toDtoList(nguoiDungService.getAll()))
                        .build()
        );
    }
}
