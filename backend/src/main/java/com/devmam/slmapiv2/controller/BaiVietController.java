package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.entities.BaiVietCreatingDto;
import com.devmam.slmapiv2.dto.response.BaiVietDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.mapper.BaiVietMapper;
import com.devmam.slmapiv2.services.MinioService;
import com.devmam.slmapiv2.services.impl.enities.BaiVietService;
import com.devmam.slmapiv2.services.impl.enities.NguoiDungService;
import com.devmam.slmapiv2.services.impl.enities.TepTinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/basic-api/bai-viet")
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


    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResponseData<BaiVietDto>> create(
            @RequestPart("dto") BaiVietCreatingDto dto,
            @RequestPart("anh_bia") MultipartFile anhBia,
            @RequestPart("noi_dung") MultipartFile noiDung
    ) {
        return baiVietService.create(dto, anhBia, noiDung);
    }
}
