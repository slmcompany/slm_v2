package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.MienDto;
import com.devmam.slmapiv2.entities.Mien;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MienMapper {
    MienDto toDto(Mien mien);
    List<MienDto> toDtoList(List<Mien> mienList);

    default Page<MienDto> toDtoPage(Page<Mien> mienPage){
        if(mienPage == null){
            return Page.empty();
        }
        return mienPage.map(this::toDto);
    }
}