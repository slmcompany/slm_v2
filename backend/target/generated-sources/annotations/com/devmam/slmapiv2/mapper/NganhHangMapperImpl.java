package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.entities.NganhHang;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T14:35:48+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class NganhHangMapperImpl implements NganhHangMapper {

    @Override
    public NganhHangDto toDto(NganhHang nganhHang) {
        if ( nganhHang == null ) {
            return null;
        }

        NganhHangDto.NganhHangDtoBuilder nganhHangDto = NganhHangDto.builder();

        nganhHangDto.id( nganhHang.getId() );
        nganhHangDto.ma( nganhHang.getMa() );
        nganhHangDto.ten( nganhHang.getTen() );
        nganhHangDto.sdtSale( nganhHang.getSdtSale() );
        nganhHangDto.sdtTech( nganhHang.getSdtTech() );
        nganhHangDto.anhNgang( nganhHang.getAnhNgang() );
        nganhHangDto.anhVuong( nganhHang.getAnhVuong() );
        nganhHangDto.trangThai( nganhHang.getTrangThai() );

        return nganhHangDto.build();
    }

    @Override
    public List<NganhHangDto> toDtoList(List<NganhHang> nganhHangList) {
        if ( nganhHangList == null ) {
            return null;
        }

        List<NganhHangDto> list = new ArrayList<NganhHangDto>( nganhHangList.size() );
        for ( NganhHang nganhHang : nganhHangList ) {
            list.add( toDto( nganhHang ) );
        }

        return list;
    }
}
