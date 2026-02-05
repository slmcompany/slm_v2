package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.request.entities.NhomTronGoiCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.NhomTronGoiUpdatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NhomTronGoiDto;
import com.devmam.slmapiv2.entities.NhomTronGoi;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.NhomTronGoiMapper;
import com.devmam.slmapiv2.services.impl.enities.NhomTronGoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basic-api/nhom-tron-goi")
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

    @PutMapping("/update")
    public ResponseEntity<ResponseData<NhomTronGoiDto>> update(@RequestBody NhomTronGoiUpdatingDto dto) {
        return nhomTronGoiService.update(dto);
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

    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<ResponseData<String>> softDelete(@PathVariable Integer id) {
        NhomTronGoi nhomTronGoi = nhomTronGoiService.getOne(id).orElseThrow(
                () -> new CommonException("Không tìm thấy nhóm trọn gói id: " + id)
        );

        nhomTronGoi.setTrangThai(0);
        nhomTronGoiService.update(id, nhomTronGoi);

        return ResponseEntity.ok(
                ResponseData.<String>builder()
                        .status(HttpStatus.OK.value())
                        .error(null)
                        .message("Success")
                        .data("Success")
                        .build()
        );
    }
}
