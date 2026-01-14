package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.request.entities.BaiVietCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.BaiVietUpdatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.BaiVietDto;
import com.devmam.slmapiv2.entities.BaiViet;
import com.devmam.slmapiv2.exception.customize.CommonException;
import com.devmam.slmapiv2.mapper.BaiVietMapper;
import com.devmam.slmapiv2.services.MinioService;
import com.devmam.slmapiv2.services.impl.enities.BaiVietService;
import com.devmam.slmapiv2.services.impl.enities.NguoiDungService;
import com.devmam.slmapiv2.services.impl.enities.TepTinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController()
@RequestMapping("/api/basic-api/bai-viet")
public class BaiVietController {

    @Autowired
    private BaiVietService baiVietService;
    @Autowired
    private TepTinService tepTinService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private BaiVietMapper baiVietMapper;
    @Autowired
    private MinioService minioService;

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ResponseData<BaiVietDto>> getById(@PathVariable Integer id) {
        Optional<BaiViet> findingBaiViet = baiVietService.getOne(id);
        if (findingBaiViet.isEmpty()) {
            throw new CommonException("Không tìm thấy bài viết id: " + id);
        }

        return ResponseEntity.ok(
                ResponseData.<BaiVietDto>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(baiVietMapper.toDto(findingBaiViet.get()))
                        .build()
        );
    }


    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseData<BaiVietDto>> create(
            @RequestPart("dto") BaiVietCreatingDto dto,
            @RequestPart("anh_bia") MultipartFile anhBia,
            @RequestPart("anh_ngoai") MultipartFile anhNgoai,
            @RequestPart("noi_dung") MultipartFile noiDung
    ) {
        return baiVietService.create(dto, anhBia, anhNgoai, noiDung);
    }

    @PutMapping(
            value = "/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseData<BaiVietDto>> update(
            @RequestPart("dto") BaiVietUpdatingDto dto,
            @RequestPart("anh_bia") MultipartFile anhBia,
            @RequestPart("anh_ngoai") MultipartFile anhNgoai,
            @RequestPart("noi_dung") MultipartFile noiDung) {
        return baiVietService.update(dto, anhBia, anhNgoai, noiDung);
    }


    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<BaiVietDto>>> filter(@RequestBody BaseFilterRequest filter) {
        return ResponseEntity.ok(
                ResponseData.<Page<BaiVietDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(baiVietMapper.toDtoPage(baiVietService.filter(filter)))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<String>> hardDelete(@PathVariable Integer id) {
        return baiVietService.hardDelete(id);
    }

}
