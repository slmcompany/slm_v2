package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.ThongTinGiaDto;
import com.devmam.slmapiv2.entities.ThongTinGia;
import com.devmam.slmapiv2.entities.commons.GiaInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-06T00:24:45+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class ThongTinGiaMapperImpl implements ThongTinGiaMapper {

    @Override
    public ThongTinGiaDto toDto(ThongTinGia thongTinGia) {
        if ( thongTinGia == null ) {
            return null;
        }

        ThongTinGiaDto.ThongTinGiaDtoBuilder thongTinGiaDto = ThongTinGiaDto.builder();

        thongTinGiaDto.id( thongTinGia.getId() );
        List<GiaInfo> list = thongTinGia.getDsGia();
        if ( list != null ) {
            thongTinGiaDto.dsGia( new ArrayList<GiaInfo>( list ) );
        }
        thongTinGiaDto.taoLuc( thongTinGia.getTaoLuc() );
        thongTinGiaDto.trangThai( thongTinGia.getTrangThai() );

        return thongTinGiaDto.build();
    }
}
