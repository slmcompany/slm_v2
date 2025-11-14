package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.request.entities.NhomTronGoiCreatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NhomTronGoiDto;
import com.devmam.slmapiv2.mapper.NhomTronGoiMapper;
import com.devmam.slmapiv2.services.impl.enities.NhomTronGoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basic-api/nhom-tron-goi")
public class NhomTronGoiController {

    @Autowired
    private NhomTronGoiService nhomTronGoiService;

    @Autowired
    private NhomTronGoiMapper nhomTronGoiMapper;


    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<NhomTronGoiDto>>> filter(@RequestBody BaseFilterRequest filter) {
        return ResponseEntity.ok(
                ResponseData.<Page<NhomTronGoiDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nhomTronGoiMapper.toDtoPage(nhomTronGoiService.filter(filter)))
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseData<NhomTronGoiDto>> create(@RequestBody NhomTronGoiCreatingDto dto) {
        return nhomTronGoiService.create(dto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<NhomTronGoiDto>>> getAll() {
        return ResponseEntity.ok(
                ResponseData.<List<NhomTronGoiDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nhomTronGoiMapper.toDtoList(nhomTronGoiService.getAll()))
                        .build()
        );
    }
}
