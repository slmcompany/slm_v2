package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.entities.NganhHang;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NganhHangMapper {

    NganhHangDto toDto(NganhHang nganhHang);
//    NganhHang toEntity(NganhHangDto dto);
    List<NganhHangDto> toDtoList(List<NganhHang> nganhHangList);
}
