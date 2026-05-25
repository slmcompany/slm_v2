package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.KhachHangDto;
import com.devmam.slmapiv2.entities.KhachHang;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-25T23:40:30+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class KhachHangMapperImpl implements KhachHangMapper {

    @Override
    public KhachHangDto toDto(KhachHang khachHang) {
        if ( khachHang == null ) {
            return null;
        }

        KhachHangDto.KhachHangDtoBuilder khachHangDto = KhachHangDto.builder();

        khachHangDto.id( khachHang.getId() );
        khachHangDto.email( khachHang.getEmail() );
        khachHangDto.sdt( khachHang.getSdt() );
        khachHangDto.hoVaTen( khachHang.getHoVaTen() );
        khachHangDto.gioiTinh( khachHang.getGioiTinh() );
        khachHangDto.sinhNhat( khachHang.getSinhNhat() );
        khachHangDto.diaChi( khachHang.getDiaChi() );
        khachHangDto.daBanDuocHang( khachHang.getDaBanDuocHang() );
        khachHangDto.taoLuc( khachHang.getTaoLuc() );
        khachHangDto.trangThai( khachHang.getTrangThai() );

        return khachHangDto.build();
    }

    @Override
    public List<KhachHangDto> toDtos(List<KhachHang> khachHangs) {
        if ( khachHangs == null ) {
            return null;
        }

        List<KhachHangDto> list = new ArrayList<KhachHangDto>( khachHangs.size() );
        for ( KhachHang khachHang : khachHangs ) {
            list.add( toDto( khachHang ) );
        }

        return list;
    }
}
