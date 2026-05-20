package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.KhachHangDto;
import com.devmam.slmapiv2.entities.KhachHang;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TepTinMapper.class})
public interface KhachHangMapper {
    KhachHangDto toDto(KhachHang khachHang);

    List<KhachHangDto> toDtos(List<KhachHang> khachHangs);

    default Page<KhachHangDto> toDtoPage(Page<KhachHang> page) {
        if (page == null || page.isEmpty()) {
            return Page.empty();
        }
        return page.map(this::toDto);
    }
}
