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
    date = "2026-03-14T09:30:02+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
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

        quangCaoDto.id( quangCao.getId() );
        quangCaoDto.nganhHang( nganhHangToNganhHangDto( quangCao.getNganhHang() ) );
        quangCaoDto.tepTin( tepTinMapper.toDto( quangCao.getTepTin() ) );
        quangCaoDto.tieuDe( quangCao.getTieuDe() );
        quangCaoDto.viTri( quangCao.getViTri() );
        quangCaoDto.hoatDong( quangCao.getHoatDong() );
        quangCaoDto.taoLuc( quangCao.getTaoLuc() );
        quangCaoDto.trangThai( quangCao.getTrangThai() );

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
}
