package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.request.entities.MienCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.MienUpdatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.MienDto;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.MienMapper;
import com.devmam.slmapiv2.services.impl.enities.MienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/basic-api/mien")
public class MienController {
    @Autowired
    private MienService mienService;
    @Autowired
    private MienMapper mienMapper;

    @GetMapping("/all")
    public ResponseEntity<ResponseData<List<MienDto>>> getAll() {
        return ResponseEntity.ok(
                ResponseData.<List<MienDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(mienMapper.toDtoList(mienService.getAll()))
                        .build()
        );
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ResponseData<MienDto>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                ResponseData.<MienDto>builder()
                        .status(200)
                        .message("Success")
                        .data(mienMapper
                                .toDto(
                                        mienService.getOne(id).orElseThrow(
                                                () -> new CommonException("Không tìm thấy miền id: " + id)
                                        )
                                )
                        ).build()
        );
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<MienDto>>> filter(@RequestBody BaseFilterRequest filter) {
        return ResponseEntity.ok(
                ResponseData.<Page<MienDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(mienMapper.toDtoPage(mienService.filter(filter)))
                        .build()
        );
    }

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseData<MienDto>> create(@RequestPart("dto") MienCreatingDto creating, @RequestPart("file") MultipartFile file) {
        return mienService.create(creating, file);
    }

    @PutMapping(
            value="/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseData<String>> update(
            @RequestPart("dto") MienUpdatingDto updating,
            @RequestPart("file") MultipartFile file
    ){
        return mienService.update(updating, file);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<String>> hardDelete(@PathVariable Integer id){
        return mienService.hardDelete(id);
    }


}
