package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.NhomVatTuDto;
import com.devmam.slmapiv2.entities.NhomVatTu;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface NhomVatTuMapper {

    NhomVatTuDto toDto(NhomVatTu nhomVatTu);

    NhomVatTu toEntity(NhomVatTuDto nhomVatTuDto);

    default Page<NhomVatTuDto> toDtoPage(Page<NhomVatTu> nhomVatTuPage){
        if(nhomVatTuPage == null){
            return Page.empty();
        }
        return nhomVatTuPage.map(this::toDto);
    }

}
