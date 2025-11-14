package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.request.entities.TronGoiCreatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.TronGoiDto;
import com.devmam.slmapiv2.entities.TronGoi;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.TronGoiMapper;
import com.devmam.slmapiv2.services.impl.enities.TronGoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/basic-api/tron-goi")
public class TronGoiController {

    @Autowired
    private TronGoiService tronGoiService;
    @Autowired
    private TronGoiMapper tronGoiMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<TronGoiDto>> getById(@PathVariable Integer id) {
        Optional<TronGoi> tronGoi = tronGoiService.getOne(id);

        if(tronGoi.isEmpty()){
            throw new CommonException("Vat tu not found for id: " + id);
        }

        return ResponseEntity.ok(
                ResponseData.<TronGoiDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(tronGoiMapper.toDto(tronGoi.get()))
                        .build()
        );
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<TronGoiDto>>> filter(@RequestBody BaseFilterRequest filter) {
        return ResponseEntity.ok(
                ResponseData.<Page<TronGoiDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(tronGoiMapper.toDtoPage(tronGoiService.filter(filter)))
                        .build()
        );
    }

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseData<List<TronGoiDto>>> create(@RequestPart("dto") TronGoiCreatingDto dto, @RequestPart("file") MultipartFile file) {
        return tronGoiService.create(dto,file);
    }

}
