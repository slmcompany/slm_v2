package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.NhomTronGoiDto;
import com.devmam.slmapiv2.entities.NhomTronGoi;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NhomTronGoiMapper {

    NhomTronGoiDto toDto(NhomTronGoi nhomTronGoi);
    List<NhomTronGoiDto> toDtoList(List<NhomTronGoi> nhomTronGoiList);

    default Page<NhomTronGoiDto> toDtoPage(Page<NhomTronGoi> nhomTronGoiPage){
        if(nhomTronGoiPage == null){
            return Page.empty();
        }
        return nhomTronGoiPage.map(this::toDto);
    }
}
