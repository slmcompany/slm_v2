package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.TronGoiDto;
import com.devmam.slmapiv2.entities.TronGoi;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring")
public interface TronGoiMapper {
//    TronGoiMapper INSTANCE = Mappers.getMapper(TronGoiMapper.class);

    TronGoiDto toDto(TronGoi tronGoi);

    TronGoi toEntity(TronGoiDto tronGoiDto);

    default Page<TronGoiDto> toDtoPage(Page<TronGoi> tronGoiPage){
        if(tronGoiPage == null){
            return Page.empty();
        }
        return tronGoiPage.map(this::toDto);
    }
}
