package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.QuangCaoDto;
import com.devmam.slmapiv2.entities.QuangCao;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TepTinMapper.class})
public interface QuangCaoMapper {

    QuangCaoDto toDto(QuangCao quangCao);

    List<QuangCaoDto> toDtoList(List<QuangCao> quangCaoList);

    default Page<QuangCaoDto> toDtoPage(Page<QuangCao> quangCaoPage){
        if(quangCaoPage == null){
            return Page.empty();
        }
        return quangCaoPage.map(this::toDto);
    }
}
