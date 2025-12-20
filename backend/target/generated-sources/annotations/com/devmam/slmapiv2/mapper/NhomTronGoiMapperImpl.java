package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.dto.response.entities.NhomTronGoiDto;
import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.NhomTronGoi;
import com.devmam.slmapiv2.entities.ThuongHieu;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-20T18:55:15+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class NhomTronGoiMapperImpl implements NhomTronGoiMapper {

    @Autowired
    private TepTinMapper tepTinMapper;

    @Override
    public NhomTronGoiDto toDto(NhomTronGoi nhomTronGoi) {
        if ( nhomTronGoi == null ) {
            return null;
        }

        NhomTronGoiDto.NhomTronGoiDtoBuilder nhomTronGoiDto = NhomTronGoiDto.builder();

        nhomTronGoiDto.id( nhomTronGoi.getId() );
        nhomTronGoiDto.nganhHang( nganhHangToNganhHangDto( nhomTronGoi.getNganhHang() ) );
        nhomTronGoiDto.ten( nhomTronGoi.getTen() );
        nhomTronGoiDto.thuongHieuTamPin( thuongHieuToThuongHieuDto( nhomTronGoi.getThuongHieuTamPin() ) );
        nhomTronGoiDto.thuongHieuInverter( thuongHieuToThuongHieuDto( nhomTronGoi.getThuongHieuInverter() ) );
        nhomTronGoiDto.thuongHieuPinLuuTru( thuongHieuToThuongHieuDto( nhomTronGoi.getThuongHieuPinLuuTru() ) );
        nhomTronGoiDto.taoLuc( nhomTronGoi.getTaoLuc() );
        nhomTronGoiDto.trangThai( nhomTronGoi.getTrangThai() );

        return nhomTronGoiDto.build();
    }

    @Override
    public List<NhomTronGoiDto> toDtoList(List<NhomTronGoi> nhomTronGoiList) {
        if ( nhomTronGoiList == null ) {
            return null;
        }

        List<NhomTronGoiDto> list = new ArrayList<NhomTronGoiDto>( nhomTronGoiList.size() );
        for ( NhomTronGoi nhomTronGoi : nhomTronGoiList ) {
            list.add( toDto( nhomTronGoi ) );
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

    protected ThuongHieuDto thuongHieuToThuongHieuDto(ThuongHieu thuongHieu) {
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
        thuongHieuDto.moTa( thuongHieu.getMoTa() );
        thuongHieuDto.tepTin( tepTinMapper.toDto( thuongHieu.getTepTin() ) );
        thuongHieuDto.trangThai( thuongHieu.getTrangThai() );

        return thuongHieuDto.build();
    }
}
