package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.CoSoDto;
import com.devmam.slmapiv2.entities.CoSo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-14T11:32:53+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CoSoMapperImpl implements CoSoMapper {

    @Override
    public CoSoDto toDto(CoSo coSo) {
        if ( coSo == null ) {
            return null;
        }

        CoSoDto.CoSoDtoBuilder coSoDto = CoSoDto.builder();

        coSoDto.dcKho( coSo.getDcKho() );
        coSoDto.dcVanPhong( coSo.getDcVanPhong() );
        coSoDto.id( coSo.getId() );
        coSoDto.ma( coSo.getMa() );
        coSoDto.taoLuc( coSo.getTaoLuc() );
        coSoDto.ten( coSo.getTen() );
        coSoDto.trangThai( coSo.getTrangThai() );

        return coSoDto.build();
    }

    @Override
    public List<CoSoDto> toDtoList(List<CoSo> coSoList) {
        if ( coSoList == null ) {
            return null;
        }

        List<CoSoDto> list = new ArrayList<CoSoDto>( coSoList.size() );
        for ( CoSo coSo : coSoList ) {
            list.add( toDto( coSo ) );
        }

        return list;
    }
}
