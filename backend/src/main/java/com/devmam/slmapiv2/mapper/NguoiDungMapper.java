package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.entities.NguoiDung;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NguoiDungMapper {

    NguoiDungDto toDto(NguoiDung nguoiDung);

    List<NguoiDungDto> toDtoList(List<NguoiDung> nguoiDungList);
}
