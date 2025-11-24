package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.HopDongDto;
import com.devmam.slmapiv2.entities.HopDong;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring" , uses = {TepTinMapper.class})
public interface HopDongMapper {
    HopDongDto toDto(HopDong hopDong);
    List<HopDongDto> toDtoList(List<HopDong> hopDongList);
    default Page<HopDongDto> toDtoPage(Page<HopDong> hopDongPage){
        if(hopDongPage == null){
            return Page.empty();
        }
        return hopDongPage.map(this::toDto);
    }
}
