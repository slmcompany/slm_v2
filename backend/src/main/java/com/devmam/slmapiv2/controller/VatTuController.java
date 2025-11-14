package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.request.entities.VatTuCreatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.VatTuDto;
import com.devmam.slmapiv2.entities.VatTu;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.VatTuMapper;
import com.devmam.slmapiv2.services.JwtService;
import com.devmam.slmapiv2.services.impl.enities.TepTinService;
import com.devmam.slmapiv2.services.impl.enities.VatTuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/basic-api/vat-tu")
public class VatTuController {

    @Autowired
    private VatTuService vatTuService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private TepTinService tepTinService;

    @Autowired
    private VatTuMapper vatTuMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<VatTuDto>> getById(Integer id) {

        Optional<VatTu> vatTu = vatTuService.getOne(id);

        if (vatTu.isEmpty()) {
            throw new CommonException("Không tìm thấy vật tư id: " + id);
        }

        return ResponseEntity.ok(
                ResponseData.<VatTuDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(vatTuMapper.toDto(vatTu.get()))
                        .build()
        );
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<VatTuDto>>> filter(@RequestBody BaseFilterRequest filter) {
        return ResponseEntity.ok(
                ResponseData.<Page<VatTuDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(vatTuMapper.toDtoPage(vatTuService.filter(filter)))
                        .build()
        );
    }

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseData<VatTuDto>> create(@RequestPart("dto") VatTuCreatingDto dto,
                                                         @RequestPart("files") List<MultipartFile> files) {
        return vatTuService.create(dto, files);
    }
}
