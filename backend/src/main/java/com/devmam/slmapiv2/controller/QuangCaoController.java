package com.devmam.slmapiv2.controller;


import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.request.entities.QuangCaoCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.QuangCaoUpdateDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.QuangCaoDto;
import com.devmam.slmapiv2.mapper.QuangCaoMapper;
import com.devmam.slmapiv2.services.impl.enities.QuangCaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/basic-api/quang-cao")
public class QuangCaoController {

    @Autowired
    private QuangCaoService quangCaoService;

    @Autowired
    private QuangCaoMapper quangCaoMapper;


    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<QuangCaoDto>>> getAll(){
        return ResponseEntity.ok(
                ResponseData.<List<QuangCaoDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(quangCaoMapper.toDtoList(quangCaoService.getAll()))
                        .build()
        );
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<QuangCaoDto>>> filter(BaseFilterRequest filter){
        return ResponseEntity.ok(
                ResponseData.<Page<QuangCaoDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(quangCaoMapper.toDtoPage(quangCaoService.filter(filter)))
                        .build()
        );
    }

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseData<QuangCaoDto>> create(
            @RequestPart(value = "dto", required = true) QuangCaoCreatingDto dto,
            @RequestPart(value = "anh_bia", required = true) MultipartFile file
    ) {
        return quangCaoService.create(dto, file);
    }

    @PutMapping(
            value = "update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseData<QuangCaoDto>> update(
            @RequestPart(value = "dto", required = true) QuangCaoUpdateDto dto,
            @RequestPart(value = "anh_bia", required = true) MultipartFile file
    ) {
        return quangCaoService.update(dto, file);
    }
}
