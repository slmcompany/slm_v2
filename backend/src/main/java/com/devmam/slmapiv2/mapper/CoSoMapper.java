package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.CoSoDto;
import com.devmam.slmapiv2.entities.CoSo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoSoMapper {
    CoSoDto toDto(CoSo coSo);
    List<CoSoDto> toDtoList(List<CoSo> coSoList);
}
