package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.VatTuDto;
import com.devmam.slmapiv2.entities.VatTu;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VatTuMapper {

//    VatTuMapper INSTANCE = Mappers.getMapper(VatTuMapper.class);

    VatTuDto toDto(VatTu vatTu);

    List<VatTuDto> toDtoList(List<VatTu> vatTuList);

    default Page<VatTuDto> toDtoPage(Page<VatTu> vatTuPage){
        if(vatTuPage == null){
            return Page.empty();
        }
        return vatTuPage.map(this::toDto);
    }
}
