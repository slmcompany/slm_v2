package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.dto.response.entities.NhomVatTuDto;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.NhomVatTu;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-18T19:20:39+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class NhomVatTuMapperImpl implements NhomVatTuMapper {

    @Override
    public NhomVatTuDto toDto(NhomVatTu nhomVatTu) {
        if ( nhomVatTu == null ) {
            return null;
        }

        NhomVatTuDto.NhomVatTuDtoBuilder nhomVatTuDto = NhomVatTuDto.builder();

        nhomVatTuDto.id( nhomVatTu.getId() );
        nhomVatTuDto.ma( nhomVatTu.getMa() );
        nhomVatTuDto.nghanhHang( nganhHangToNganhHangDto( nhomVatTu.getNghanhHang() ) );
        nhomVatTuDto.ten( nhomVatTu.getTen() );
        Map<String, Object> map = nhomVatTu.getThuocTinhRieng();
        if ( map != null ) {
            nhomVatTuDto.thuocTinhRieng( new LinkedHashMap<String, Object>( map ) );
        }
        nhomVatTuDto.gm( nhomVatTu.getGm() );
        nhomVatTuDto.vatTuChinh( nhomVatTu.getVatTuChinh() );
        nhomVatTuDto.taoLuc( nhomVatTu.getTaoLuc() );
        nhomVatTuDto.trangThai( nhomVatTu.getTrangThai() );

        return nhomVatTuDto.build();
    }

    @Override
    public NhomVatTu toEntity(NhomVatTuDto nhomVatTuDto) {
        if ( nhomVatTuDto == null ) {
            return null;
        }

        NhomVatTu.NhomVatTuBuilder nhomVatTu = NhomVatTu.builder();

        nhomVatTu.id( nhomVatTuDto.getId() );
        nhomVatTu.ma( nhomVatTuDto.getMa() );
        nhomVatTu.nghanhHang( nganhHangDtoToNganhHang( nhomVatTuDto.getNghanhHang() ) );
        nhomVatTu.ten( nhomVatTuDto.getTen() );
        Map<String, Object> map = nhomVatTuDto.getThuocTinhRieng();
        if ( map != null ) {
            nhomVatTu.thuocTinhRieng( new LinkedHashMap<String, Object>( map ) );
        }
        nhomVatTu.gm( nhomVatTuDto.getGm() );
        nhomVatTu.vatTuChinh( nhomVatTuDto.getVatTuChinh() );
        nhomVatTu.taoLuc( nhomVatTuDto.getTaoLuc() );
        nhomVatTu.trangThai( nhomVatTuDto.getTrangThai() );

        return nhomVatTu.build();
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

    protected NganhHang nganhHangDtoToNganhHang(NganhHangDto nganhHangDto) {
        if ( nganhHangDto == null ) {
            return null;
        }

        NganhHang.NganhHangBuilder nganhHang = NganhHang.builder();

        nganhHang.id( nganhHangDto.getId() );
        nganhHang.ma( nganhHangDto.getMa() );
        nganhHang.ten( nganhHangDto.getTen() );
        nganhHang.sdtSale( nganhHangDto.getSdtSale() );
        nganhHang.sdtTech( nganhHangDto.getSdtTech() );
        nganhHang.anhNgang( nganhHangDto.getAnhNgang() );
        nganhHang.anhVuong( nganhHangDto.getAnhVuong() );
        nganhHang.trangThai( nganhHangDto.getTrangThai() );

        return nganhHang.build();
    }
}
