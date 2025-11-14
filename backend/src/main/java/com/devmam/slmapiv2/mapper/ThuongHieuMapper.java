package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.entities.ThuongHieu;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThuongHieuMapper {

    ThuongHieuDto toDto(ThuongHieu thuongHieu);

    List<ThuongHieuDto> toDtoList(List<ThuongHieu> thuongHieuList);

}
