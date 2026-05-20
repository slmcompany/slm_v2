package com.devmam.slmapiv2.controller;

import com.devmam.slmapiv2.dto.request.BaseFilterRequest;
import com.devmam.slmapiv2.dto.request.entities.HopDongDefineKhachHangCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.HopDongUndefineKhachHangCreatingDto;
import com.devmam.slmapiv2.dto.request.entities.HopDongUpdatingDto;
import com.devmam.slmapiv2.dto.response.ResponseData;
import com.devmam.slmapiv2.dto.response.entities.HopDongDto;
import com.devmam.slmapiv2.mapper.HopDongMapper;
import com.devmam.slmapiv2.services.impl.enities.HopDongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basic-api/hop-dong")
public class HopDongController {

    @Autowired
    private HopDongService hopDongService;

    @Autowired
    private HopDongMapper hopDongMapper;

    @PostMapping("/filter")
    public ResponseEntity<ResponseData<Page<HopDongDto>>> filter(@RequestBody BaseFilterRequest filter){
        return ResponseEntity.ok(
                ResponseData.<Page<HopDongDto>>builder()
                        .status(200)
                        .error(null)
                        .message("Success")
                        .data(hopDongMapper.toDtoPage(hopDongService.filter(filter)))
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseData<HopDongDto>> create(@RequestBody HopDongUndefineKhachHangCreatingDto dto){
        return hopDongService.create(dto);
    }

    @PostMapping("/creat-known-customer")
    public ResponseEntity<ResponseData<HopDongDto>> create(@RequestBody HopDongDefineKhachHangCreatingDto dto){
        return hopDongService.create(dto);
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseData<HopDongDto>> update(@RequestBody HopDongUpdatingDto dto){
        return hopDongService.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<HopDongDto>> delete(@PathVariable Integer id){
        return hopDongService.deleteHopDong(id);
    }
}
