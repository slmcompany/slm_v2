package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.BaiVietDto;
import com.devmam.slmapiv2.entities.BaiViet;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TepTinMapper.class})
public interface BaiVietMapper {

    BaiVietDto toDto(BaiViet baiViet);
    List<BaiVietDto> toDtoList(List<BaiViet> baiVietList);

    default Page<BaiVietDto> toDtoPage(Page<BaiViet> baiVietPage){
        if(baiVietPage == null){
            return Page.empty();
        }
        return baiVietPage.map(this::toDto);
    }
}
