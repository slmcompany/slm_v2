package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.AnhVatTuDto;
import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.dto.response.entities.NhomVatTuDto;
import com.devmam.slmapiv2.dto.response.entities.TepTinDto;
import com.devmam.slmapiv2.dto.response.entities.ThongTinGiaDto;
import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.dto.response.entities.VatTuDto;
import com.devmam.slmapiv2.entities.AnhVatTu;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.NhomVatTu;
import com.devmam.slmapiv2.entities.TepTin;
import com.devmam.slmapiv2.entities.ThongTinGia;
import com.devmam.slmapiv2.entities.ThuongHieu;
import com.devmam.slmapiv2.entities.VatTu;
import com.devmam.slmapiv2.entities.commons.GiaInfo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-14T11:32:53+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class VatTuMapperImpl implements VatTuMapper {

    @Override
    public VatTuDto toDto(VatTu vatTu) {
        if ( vatTu == null ) {
            return null;
        }

        VatTuDto.VatTuDtoBuilder vatTuDto = VatTuDto.builder();

        vatTuDto.anhVatTus( anhVatTuListToAnhVatTuDtoList( vatTu.getAnhVatTus() ) );
        vatTuDto.donVi( vatTu.getDonVi() );
        Map<String, Object> map = vatTu.getDuLieuRieng();
        if ( map != null ) {
            vatTuDto.duLieuRieng( new LinkedHashMap<String, Object>( map ) );
        }
        vatTuDto.id( vatTu.getId() );
        vatTuDto.moTaBaoGia( vatTu.getMoTaBaoGia() );
        vatTuDto.moTaHopDong( vatTu.getMoTaHopDong() );
        vatTuDto.nhomVatTu( nhomVatTuToNhomVatTuDto( vatTu.getNhomVatTu() ) );
        vatTuDto.sheetLink( vatTu.getSheetLink() );
        vatTuDto.taoLuc( vatTu.getTaoLuc() );
        vatTuDto.ten( vatTu.getTen() );
        vatTuDto.thongTinGias( thongTinGiaListToThongTinGiaDtoList( vatTu.getThongTinGias() ) );
        vatTuDto.thuongHieu( thuongHieuToThuongHieuDto( vatTu.getThuongHieu() ) );
        vatTuDto.trangThai( vatTu.getTrangThai() );

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

    protected TepTinDto tepTinToTepTinDto(TepTin tepTin) {
        if ( tepTin == null ) {
            return null;
        }

        TepTinDto.TepTinDtoBuilder tepTinDto = TepTinDto.builder();

        tepTinDto.duoiTep( tepTin.getDuoiTep() );
        tepTinDto.duongDan( tepTin.getDuongDan() );
        tepTinDto.id( tepTin.getId() );
        tepTinDto.kichCo( tepTin.getKichCo() );
        tepTinDto.loaiTepTin( tepTin.getLoaiTepTin() );
        tepTinDto.moTa( tepTin.getMoTa() );
        tepTinDto.suaLuc( tepTin.getSuaLuc() );
        tepTinDto.taoLuc( tepTin.getTaoLuc() );
        tepTinDto.tenLuuTru( tepTin.getTenLuuTru() );
        tepTinDto.tenTaiLen( tepTin.getTenTaiLen() );
        tepTinDto.tenTepGoc( tepTin.getTenTepGoc() );
        tepTinDto.trangThai( tepTin.getTrangThai() );

        return tepTinDto.build();
    }

    protected AnhVatTuDto anhVatTuToAnhVatTuDto(AnhVatTu anhVatTu) {
        if ( anhVatTu == null ) {
            return null;
        }

        AnhVatTuDto.AnhVatTuDtoBuilder anhVatTuDto = AnhVatTuDto.builder();

        anhVatTuDto.anhChinh( anhVatTu.getAnhChinh() );
        anhVatTuDto.id( anhVatTu.getId() );
        anhVatTuDto.tepTin( tepTinToTepTinDto( anhVatTu.getTepTin() ) );
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

    protected NhomVatTuDto nhomVatTuToNhomVatTuDto(NhomVatTu nhomVatTu) {
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

    protected ThongTinGiaDto thongTinGiaToThongTinGiaDto(ThongTinGia thongTinGia) {
        if ( thongTinGia == null ) {
            return null;
        }

        ThongTinGiaDto.ThongTinGiaDtoBuilder thongTinGiaDto = ThongTinGiaDto.builder();

        List<GiaInfo> list = thongTinGia.getDsGia();
        if ( list != null ) {
            thongTinGiaDto.dsGia( new ArrayList<GiaInfo>( list ) );
        }
        thongTinGiaDto.id( thongTinGia.getId() );
        thongTinGiaDto.taoLuc( thongTinGia.getTaoLuc() );
        thongTinGiaDto.trangThai( thongTinGia.getTrangThai() );

        return thongTinGiaDto.build();
    }

    protected List<ThongTinGiaDto> thongTinGiaListToThongTinGiaDtoList(List<ThongTinGia> list) {
        if ( list == null ) {
            return null;
        }

        List<ThongTinGiaDto> list1 = new ArrayList<ThongTinGiaDto>( list.size() );
        for ( ThongTinGia thongTinGia : list ) {
            list1.add( thongTinGiaToThongTinGiaDto( thongTinGia ) );
        }

        return list1;
    }

    protected ThuongHieuDto thuongHieuToThuongHieuDto(ThuongHieu thuongHieu) {
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
}
