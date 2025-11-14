package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.entities.ThuongHieu;
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
public class ThuongHieuMapperImpl implements ThuongHieuMapper {

    @Override
    public ThuongHieuDto toDto(ThuongHieu thuongHieu) {
        if ( thuongHieu == null ) {
            return null;
        }

        ThuongHieuDto.ThuongHieuDtoBuilder thuongHieuDto = ThuongHieuDto.builder();

        thuongHieuDto.email( thuongHieu.getEmail() );
        thuongHieuDto.id( thuongHieu.getId() );
        thuongHieuDto.quocGia( thuongHieu.getQuocGia() );
        thuongHieuDto.sdt( thuongHieu.getSdt() );
        thuongHieuDto.ten( thuongHieu.getTen() );
        thuongHieuDto.tenQuocTe( thuongHieu.getTenQuocTe() );
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
