package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.CoSoDto;
import com.devmam.slmapiv2.entities.CoSo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-01T19:30:01+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class CoSoMapperImpl implements CoSoMapper {

    @Override
    public CoSoDto toDto(CoSo coSo) {
        if ( coSo == null ) {
            return null;
        }

        CoSoDto.CoSoDtoBuilder coSoDto = CoSoDto.builder();

        coSoDto.id( coSo.getId() );
        coSoDto.ma( coSo.getMa() );
        coSoDto.ten( coSo.getTen() );
        coSoDto.dcVanPhong( coSo.getDcVanPhong() );
        coSoDto.dcKho( coSo.getDcKho() );
        coSoDto.taoLuc( coSo.getTaoLuc() );
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
