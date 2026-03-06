package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.ThongTinGiaDto;
import com.devmam.slmapiv2.entities.ThongTinGia;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ThongTinGiaMapper {

    ThongTinGiaDto toDto(ThongTinGia thongTinGia);

    default List<ThongTinGiaDto> toDtoList(List<ThongTinGia> thongTinGiaList) {
        if (thongTinGiaList == null) return null;
        List<ThongTinGiaDto> dtoList = new java.util.ArrayList<>(thongTinGiaList.stream().map(this::toDto).toList());
        // sắp xếp dto list theo id tăng dần
        dtoList.sort(Comparator.comparing(ThongTinGiaDto::getId));
        return dtoList;
    }
}