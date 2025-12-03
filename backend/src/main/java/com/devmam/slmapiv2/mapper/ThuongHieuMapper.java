package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.entities.ThuongHieu;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TepTinMapper.class})
public interface ThuongHieuMapper {

    ThuongHieuDto toDto(ThuongHieu thuongHieu);

    List<ThuongHieuDto> toDtoList(List<ThuongHieu> thuongHieuList);

    default Page<ThuongHieuDto> toDtoPage(Page<ThuongHieu> thuongHieuPage){
        if(thuongHieuPage == null){
            return Page.empty();
        }
        return thuongHieuPage.map(this::toDto);
    }

}
