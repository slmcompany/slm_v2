package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.entities.NguoiDung;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TepTinMapper.class})
public interface NguoiDungMapper {

    NguoiDungDto toDto(NguoiDung nguoiDung);

    List<NguoiDungDto> toDtoList(List<NguoiDung> nguoiDungList);

    default Page<NguoiDungDto> toDtoPage(Page<NguoiDung> nguoiDungPage) {
        if (nguoiDungPage == null) return Page.empty();
        return nguoiDungPage.map(this::toDto);
    }
}
