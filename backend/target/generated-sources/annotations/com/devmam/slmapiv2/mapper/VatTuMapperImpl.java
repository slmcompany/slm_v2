package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.AnhVatTuDto;
import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.dto.response.entities.NhomVatTuDto;
import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.dto.response.entities.VatTuDto;
import com.devmam.slmapiv2.entities.AnhVatTu;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.NhomVatTu;
import com.devmam.slmapiv2.entities.ThuongHieu;
import com.devmam.slmapiv2.entities.VatTu;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-20T00:56:21+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class VatTuMapperImpl implements VatTuMapper {

    @Autowired
    private TepTinMapper tepTinMapper;
    @Autowired
    private ThongTinGiaMapper thongTinGiaMapper;

    @Override
    public VatTuDto toDto(VatTu vatTu) {
        if ( vatTu == null ) {
            return null;
        }

        VatTuDto.VatTuDtoBuilder vatTuDto = VatTuDto.builder();

        vatTuDto.id( vatTu.getId() );
        vatTuDto.ma( vatTu.getMa() );
        vatTuDto.nhomVatTu( nhomVatTuToNhomVatTuDto( vatTu.getNhomVatTu() ) );
        vatTuDto.thuongHieu( thuongHieuToThuongHieuDto( vatTu.getThuongHieu() ) );
        vatTuDto.ten( vatTu.getTen() );
        vatTuDto.sheetLink( vatTu.getSheetLink() );
        vatTuDto.donVi( vatTu.getDonVi() );
        vatTuDto.moTaBaoGia( vatTu.getMoTaBaoGia() );
        vatTuDto.moTaHopDong( vatTu.getMoTaHopDong() );
        Map<String, Object> map = vatTu.getDuLieuRieng();
        if ( map != null ) {
            vatTuDto.duLieuRieng( new LinkedHashMap<String, Object>( map ) );
        }
        vatTuDto.thoiGianBaoHanh( vatTu.getThoiGianBaoHanh() );
        vatTuDto.gm( vatTu.getGm() );
        vatTuDto.vatTuChinh( vatTu.getVatTuChinh() );
        vatTuDto.taoLuc( vatTu.getTaoLuc() );
        vatTuDto.trangThai( vatTu.getTrangThai() );
        vatTuDto.anhVatTus( anhVatTuListToAnhVatTuDtoList( vatTu.getAnhVatTus() ) );
        vatTuDto.thongTinGias( thongTinGiaMapper.toDtoList( vatTu.getThongTinGias() ) );

        return vatTuDto.build();
    }

    @Override
    public List<VatTuDto> toDtoList(List<VatTu> vatTuList) {
        if ( vatTuList == null ) {
            return null;
        }

        List<VatTuDto> list = new ArrayList<VatTuDto>( vatTuList.size() );
        for ( VatTu vatTu : vatTuList ) {
            list.add( toDto( vatTu ) );
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

    protected NhomVatTuDto nhomVatTuToNhomVatTuDto(NhomVatTu nhomVatTu) {
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

    protected AnhVatTuDto anhVatTuToAnhVatTuDto(AnhVatTu anhVatTu) {
        if ( anhVatTu == null ) {
            return null;
        }

        AnhVatTuDto.AnhVatTuDtoBuilder anhVatTuDto = AnhVatTuDto.builder();

        anhVatTuDto.id( anhVatTu.getId() );
        anhVatTuDto.tepTin( tepTinMapper.toDto( anhVatTu.getTepTin() ) );
        anhVatTuDto.anhChinh( anhVatTu.getAnhChinh() );
        anhVatTuDto.trangThai( anhVatTu.getTrangThai() );

        return anhVatTuDto.build();
    }

    protected List<AnhVatTuDto> anhVatTuListToAnhVatTuDtoList(List<AnhVatTu> list) {
        if ( list == null ) {
            return null;
        }

        List<AnhVatTuDto> list1 = new ArrayList<AnhVatTuDto>( list.size() );
        for ( AnhVatTu anhVatTu : list ) {
            list1.add( anhVatTuToAnhVatTuDto( anhVatTu ) );
        }

        return list1;
    }
}
