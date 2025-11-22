package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.entities.ThuongHieu;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-22T18:43:18+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class ThuongHieuMapperImpl implements ThuongHieuMapper {

    @Override
    public ThuongHieuDto toDto(ThuongHieu thuongHieu) {
        if ( thuongHieu == null ) {
            return null;
        }

        ThuongHieuDto.ThuongHieuDtoBuilder thuongHieuDto = ThuongHieuDto.builder();

        thuongHieuDto.id( thuongHieu.getId() );
        thuongHieuDto.tenQuocTe( thuongHieu.getTenQuocTe() );
        thuongHieuDto.ten( thuongHieu.getTen() );
        thuongHieuDto.quocGia( thuongHieu.getQuocGia() );
        thuongHieuDto.sdt( thuongHieu.getSdt() );
        thuongHieuDto.email( thuongHieu.getEmail() );
        thuongHieuDto.trangThai( thuongHieu.getTrangThai() );

        return thuongHieuDto.build();
    }

    @Override
    public List<ThuongHieuDto> toDtoList(List<ThuongHieu> thuongHieuList) {
        if ( thuongHieuList == null ) {
            return null;
        }

        List<ThuongHieuDto> list = new ArrayList<ThuongHieuDto>( thuongHieuList.size() );
        for ( ThuongHieu thuongHieu : thuongHieuList ) {
            list.add( toDto( thuongHieu ) );
        }

        return list;
    }
}
