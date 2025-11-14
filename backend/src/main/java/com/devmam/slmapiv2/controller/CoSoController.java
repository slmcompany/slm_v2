package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.CoSoDto;
import com.devmam.slmapiv2.mapper.CoSoMapper;
import com.devmam.slmapiv2.services.impl.enities.CoSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/basic-api/co-so")
public class CoSoController {
    @Autowired
    private CoSoService coSoService;
    @Autowired
    private CoSoMapper coSoMapper;

    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<CoSoDto>>> getAll(){
        return ResponseEntity.ok(
                ResponseData.<List<CoSoDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(coSoMapper.toDtoList(coSoService.getAll()))
                        .build()
        );
    }
}
