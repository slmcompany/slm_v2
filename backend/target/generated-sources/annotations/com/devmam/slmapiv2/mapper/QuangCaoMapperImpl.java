package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.dto.response.entities.QuangCaoDto;
import com.devmam.slmapiv2.dto.response.entities.TepTinDto;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.QuangCao;
import com.devmam.slmapiv2.entities.TepTin;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-22T18:30:03+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class QuangCaoMapperImpl implements QuangCaoMapper {

    @Override
    public QuangCaoDto toDto(QuangCao quangCao) {
        if ( quangCao == null ) {
            return null;
        }

        QuangCaoDto.QuangCaoDtoBuilder quangCaoDto = QuangCaoDto.builder();

        quangCaoDto.id( quangCao.getId() );
        quangCaoDto.nganhHang( nganhHangToNganhHangDto( quangCao.getNganhHang() ) );
        quangCaoDto.tepTin( tepTinToTepTinDto( quangCao.getTepTin() ) );
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

    protected TepTinDto tepTinToTepTinDto(TepTin tepTin) {
        if ( tepTin == null ) {
            return null;
        }

        TepTinDto.TepTinDtoBuilder tepTinDto = TepTinDto.builder();

        tepTinDto.id( tepTin.getId() );
        tepTinDto.tenTepGoc( tepTin.getTenTepGoc() );
        tepTinDto.tenTaiLen( tepTin.getTenTaiLen() );
        tepTinDto.tenLuuTru( tepTin.getTenLuuTru() );
        tepTinDto.duongDan( tepTin.getDuongDan() );
        tepTinDto.loaiTepTin( tepTin.getLoaiTepTin() );
        tepTinDto.duoiTep( tepTin.getDuoiTep() );
        tepTinDto.kichCo( tepTin.getKichCo() );
        tepTinDto.moTa( tepTin.getMoTa() );
        tepTinDto.taoLuc( tepTin.getTaoLuc() );
        tepTinDto.suaLuc( tepTin.getSuaLuc() );
        tepTinDto.trangThai( tepTin.getTrangThai() );

        return tepTinDto.build();
    }
}
