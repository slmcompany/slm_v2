package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.dto.response.entities.QuangCaoDto;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.QuangCao;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-25T15:17:09+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.45.0.v20260128-0750, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class QuangCaoMapperImpl implements QuangCaoMapper {

    @Autowired
    private TepTinMapper tepTinMapper;

    @Override
    public QuangCaoDto toDto(QuangCao quangCao) {
        if ( quangCao == null ) {
            return null;
        }

        QuangCaoDto.QuangCaoDtoBuilder quangCaoDto = QuangCaoDto.builder();

        quangCaoDto.hoatDong( quangCao.getHoatDong() );
        quangCaoDto.id( quangCao.getId() );
        quangCaoDto.nganhHang( nganhHangToNganhHangDto( quangCao.getNganhHang() ) );
        quangCaoDto.taoLuc( quangCao.getTaoLuc() );
        quangCaoDto.tepTin( tepTinMapper.toDto( quangCao.getTepTin() ) );
        quangCaoDto.tieuDe( quangCao.getTieuDe() );
        quangCaoDto.trangThai( quangCao.getTrangThai() );
        quangCaoDto.viTri( quangCao.getViTri() );

        return quangCaoDto.build();
    }

    @Override
    public List<QuangCaoDto> toDtoList(List<QuangCao> quangCaoList) {
        if ( quangCaoList == null ) {
            return null;
        }

        List<QuangCaoDto> list = new ArrayList<QuangCaoDto>( quangCaoList.size() );
        for ( QuangCao quangCao : quangCaoList ) {
            list.add( toDto( quangCao ) );
        }

        return list;
    }

    protected NganhHangDto nganhHangToNganhHangDto(NganhHang nganhHang) {
        if ( nganhHang == null ) {
            return null;
        }

        NganhHangDto.NganhHangDtoBuilder nganhHangDto = NganhHangDto.builder();

        nganhHangDto.anhNgang( nganhHang.getAnhNgang() );
        nganhHangDto.anhVuong( nganhHang.getAnhVuong() );
        nganhHangDto.id( nganhHang.getId() );
        nganhHangDto.ma( nganhHang.getMa() );
        nganhHangDto.sdtSale( nganhHang.getSdtSale() );
        nganhHangDto.sdtTech( nganhHang.getSdtTech() );
        nganhHangDto.ten( nganhHang.getTen() );
        nganhHangDto.trangThai( nganhHang.getTrangThai() );

        return nganhHangDto.build();
    }
}
