package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.TepTinDto;
import com.devmam.slmapiv2.mapper.TepTinMapper;
import com.devmam.slmapiv2.services.MinioService;
import com.devmam.slmapiv2.services.impl.enities.TepTinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tep-tin")
public class TepTinController {

    @Autowired
    private TepTinService tepTinService;

    @Autowired
    private TepTinMapper tepTinMapper;

    @Autowired
    private MinioService minioService;


    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<TepTinDto>>> filter(@RequestBody BaseFilterRequest filter) {
        return ResponseEntity.ok(
                ResponseData.<Page<TepTinDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(tepTinMapper.toDtoPage(tepTinService.filter(filter)))
                        .build()
        );
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ResponseData<TepTinDto>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ResponseData.<TepTinDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(tepTinMapper.toDto(tepTinService.getOne(id).get()))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<String>> hardDelete(@PathVariable Integer id) {
        return tepTinService.hardDelete(id);
    }

}
