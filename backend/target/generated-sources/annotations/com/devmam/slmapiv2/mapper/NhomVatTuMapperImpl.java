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
    date = "2025-11-14T11:32:53+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class NhomVatTuMapperImpl implements NhomVatTuMapper {

    @Override
    public NhomVatTuDto toDto(NhomVatTu nhomVatTu) {
        if ( nhomVatTu == null ) {
            return null;
        }

        NhomVatTuDto.NhomVatTuDtoBuilder nhomVatTuDto = NhomVatTuDto.builder();

        nhomVatTuDto.gm( nhomVatTu.getGm() );
        nhomVatTuDto.id( nhomVatTu.getId() );
        nhomVatTuDto.ma( nhomVatTu.getMa() );
        nhomVatTuDto.nghanhHang( nganhHangToNganhHangDto( nhomVatTu.getNghanhHang() ) );
        nhomVatTuDto.taoLuc( nhomVatTu.getTaoLuc() );
        nhomVatTuDto.ten( nhomVatTu.getTen() );
        Map<String, Object> map = nhomVatTu.getThuocTinhRieng();
        if ( map != null ) {
            nhomVatTuDto.thuocTinhRieng( new LinkedHashMap<String, Object>( map ) );
        }
        nhomVatTuDto.trangThai( nhomVatTu.getTrangThai() );
        nhomVatTuDto.vatTuChinh( nhomVatTu.getVatTuChinh() );

        return nhomVatTuDto.build();
    }

    @Override
    public NhomVatTu toEntity(NhomVatTuDto nhomVatTuDto) {
        if ( nhomVatTuDto == null ) {
            return null;
        }

        NhomVatTu.NhomVatTuBuilder nhomVatTu = NhomVatTu.builder();

        nhomVatTu.gm( nhomVatTuDto.getGm() );
        nhomVatTu.id( nhomVatTuDto.getId() );
        nhomVatTu.ma( nhomVatTuDto.getMa() );
        nhomVatTu.nghanhHang( nganhHangDtoToNganhHang( nhomVatTuDto.getNghanhHang() ) );
        nhomVatTu.taoLuc( nhomVatTuDto.getTaoLuc() );
        nhomVatTu.ten( nhomVatTuDto.getTen() );
        Map<String, Object> map = nhomVatTuDto.getThuocTinhRieng();
        if ( map != null ) {
            nhomVatTu.thuocTinhRieng( new LinkedHashMap<String, Object>( map ) );
        }
        nhomVatTu.trangThai( nhomVatTuDto.getTrangThai() );
        nhomVatTu.vatTuChinh( nhomVatTuDto.getVatTuChinh() );

        return nhomVatTu.build();
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

    protected NganhHang nganhHangDtoToNganhHang(NganhHangDto nganhHangDto) {
        if ( nganhHangDto == null ) {
            return null;
        }

        NganhHang.NganhHangBuilder nganhHang = NganhHang.builder();

        nganhHang.anhNgang( nganhHangDto.getAnhNgang() );
        nganhHang.anhVuong( nganhHangDto.getAnhVuong() );
        nganhHang.id( nganhHangDto.getId() );
        nganhHang.ma( nganhHangDto.getMa() );
        nganhHang.sdtSale( nganhHangDto.getSdtSale() );
        nganhHang.sdtTech( nganhHangDto.getSdtTech() );
        nganhHang.ten( nganhHangDto.getTen() );
        nganhHang.trangThai( nganhHangDto.getTrangThai() );

        return nganhHang.build();
    }
}
