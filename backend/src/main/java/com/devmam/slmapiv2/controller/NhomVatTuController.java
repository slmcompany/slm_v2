package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.request.entities.NhomVatTuCreatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.NhomVatTuDto;
import com.devmam.slmapiv2.entities.NhomVatTu;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.NhomVatTuMapper;
import com.devmam.slmapiv2.services.impl.enities.NhomVatTuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/basic-api/nhom-vat-tu")
public class NhomVatTuController {

    @Autowired
    private NhomVatTuService nhomVatTuService;
    @Autowired
    private NhomVatTuMapper nhomVatTuMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<NhomVatTuDto>> getById(@PathVariable Integer id) {
        Optional<NhomVatTu> nhomVatTu = nhomVatTuService.getOne(id);
        if (nhomVatTu.isEmpty()) {
            throw new CommonException("Không tìm thấy nhóm vật tư id: " + id);
        }
        return ResponseEntity.ok(
                ResponseData.<NhomVatTuDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nhomVatTuMapper.toDto(nhomVatTu.get()))
                        .build()
        );
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<NhomVatTuDto>>> filter(@RequestBody BaseFilterRequest filter) {
        return ResponseEntity.ok(
                ResponseData.<Page<NhomVatTuDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(nhomVatTuMapper.toDtoPage(nhomVatTuService.filter(filter)))
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseData<NhomVatTuDto>> create(@RequestBody NhomVatTuCreatingDto dto){
        return nhomVatTuService.create(dto);
    }
}
