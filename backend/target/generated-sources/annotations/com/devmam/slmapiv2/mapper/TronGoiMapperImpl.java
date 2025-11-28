package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.AnhVatTuDto;
import com.devmam.slmapiv2.dto.response.entities.CoSoDto;
import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.dto.response.entities.NhomTronGoiDto;
import com.devmam.slmapiv2.dto.response.entities.NhomVatTuDto;
import com.devmam.slmapiv2.dto.response.entities.TepTinDto;
import com.devmam.slmapiv2.dto.response.entities.ThongTinGiaDto;
import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.dto.response.entities.TronGoiDto;
import com.devmam.slmapiv2.dto.response.entities.VatTuDto;
import com.devmam.slmapiv2.dto.response.entities.VatTuTronGoiDto;
import com.devmam.slmapiv2.entities.AnhVatTu;
import com.devmam.slmapiv2.entities.CoSo;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.NhomTronGoi;
import com.devmam.slmapiv2.entities.NhomVatTu;
import com.devmam.slmapiv2.entities.TepTin;
import com.devmam.slmapiv2.entities.ThongTinGia;
import com.devmam.slmapiv2.entities.ThuongHieu;
import com.devmam.slmapiv2.entities.TronGoi;
import com.devmam.slmapiv2.entities.VatTu;
import com.devmam.slmapiv2.entities.VatTuTronGoi;
import com.devmam.slmapiv2.entities.commons.GiaInfo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T01:11:39+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class TronGoiMapperImpl implements TronGoiMapper {

    @Autowired
    private TepTinMapper tepTinMapper;

    @Override
    public TronGoiDto toDto(TronGoi tronGoi) {
        if ( tronGoi == null ) {
            return null;
        }

        TronGoiDto.TronGoiDtoBuilder tronGoiDto = TronGoiDto.builder();

        tronGoiDto.id( tronGoi.getId() );
        tronGoiDto.nhomTronGoi( nhomTronGoiToNhomTronGoiDto( tronGoi.getNhomTronGoi() ) );
        tronGoiDto.coSo( coSoToCoSoDto( tronGoi.getCoSo() ) );
        tronGoiDto.ten( tronGoi.getTen() );
        tronGoiDto.tepTin( tepTinMapper.toDto( tronGoi.getTepTin() ) );
        tronGoiDto.loaiHeThong( tronGoi.getLoaiHeThong() );
        tronGoiDto.loaiPha( tronGoi.getLoaiPha() );
        tronGoiDto.congSuatHeThong( tronGoi.getCongSuatHeThong() );
        tronGoiDto.sanLuongToiThieu( tronGoi.getSanLuongToiThieu() );
        tronGoiDto.sanLuongToiDa( tronGoi.getSanLuongToiDa() );
        tronGoiDto.moTa( tronGoi.getMoTa() );
        tronGoiDto.taoLuc( tronGoi.getTaoLuc() );
        tronGoiDto.tongGia( tronGoi.getTongGia() );
        tronGoiDto.gmTong( tronGoi.getGmTong() );
        tronGoiDto.banChay( tronGoi.getBanChay() );
        tronGoiDto.trangThai( tronGoi.getTrangThai() );
        tronGoiDto.vatTuTronGois( vatTuTronGoiListToVatTuTronGoiDtoSet( tronGoi.getVatTuTronGois() ) );

        return tronGoiDto.build();
    }

    @Override
    public TronGoi toEntity(TronGoiDto tronGoiDto) {
        if ( tronGoiDto == null ) {
            return null;
        }

        TronGoi.TronGoiBuilder tronGoi = TronGoi.builder();

        tronGoi.id( tronGoiDto.getId() );
        tronGoi.coSo( coSoDtoToCoSo( tronGoiDto.getCoSo() ) );
        tronGoi.nhomTronGoi( nhomTronGoiDtoToNhomTronGoi( tronGoiDto.getNhomTronGoi() ) );
        tronGoi.ten( tronGoiDto.getTen() );
        tronGoi.tepTin( tepTinDtoToTepTin( tronGoiDto.getTepTin() ) );
        tronGoi.loaiHeThong( tronGoiDto.getLoaiHeThong() );
        tronGoi.loaiPha( tronGoiDto.getLoaiPha() );
        tronGoi.congSuatHeThong( tronGoiDto.getCongSuatHeThong() );
        tronGoi.sanLuongToiThieu( tronGoiDto.getSanLuongToiThieu() );
        tronGoi.sanLuongToiDa( tronGoiDto.getSanLuongToiDa() );
        tronGoi.moTa( tronGoiDto.getMoTa() );
        tronGoi.tongGia( tronGoiDto.getTongGia() );
        tronGoi.gmTong( tronGoiDto.getGmTong() );
        tronGoi.banChay( tronGoiDto.getBanChay() );
        tronGoi.taoLuc( tronGoiDto.getTaoLuc() );
        tronGoi.trangThai( tronGoiDto.getTrangThai() );
        tronGoi.vatTuTronGois( vatTuTronGoiDtoSetToVatTuTronGoiList( tronGoiDto.getVatTuTronGois() ) );

        return tronGoi.build();
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
        thuongHieuDto.trangThai( thuongHieu.getTrangThai() );

        return thuongHieuDto.build();
    }

    protected NhomTronGoiDto nhomTronGoiToNhomTronGoiDto(NhomTronGoi nhomTronGoi) {
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

    protected CoSoDto coSoToCoSoDto(CoSo coSo) {
        if ( coSo == null ) {
            return null;
        }

        CoSoDto.CoSoDtoBuilder coSoDto = CoSoDto.builder();

        coSoDto.id( coSo.getId() );
        coSoDto.ma( coSo.getMa() );
        coSoDto.ten( coSo.getTen() );
        coSoDto.dcVanPhong( coSo.getDcVanPhong() );
        coSoDto.dcKho( coSo.getDcKho() );
        coSoDto.taoLuc( coSo.getTaoLuc() );
        coSoDto.trangThai( coSo.getTrangThai() );

        return coSoDto.build();
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

    protected ThongTinGiaDto thongTinGiaToThongTinGiaDto(ThongTinGia thongTinGia) {
        if ( thongTinGia == null ) {
            return null;
        }

        ThongTinGiaDto.ThongTinGiaDtoBuilder thongTinGiaDto = ThongTinGiaDto.builder();

        thongTinGiaDto.id( thongTinGia.getId() );
        List<GiaInfo> list = thongTinGia.getDsGia();
        if ( list != null ) {
            thongTinGiaDto.dsGia( new ArrayList<GiaInfo>( list ) );
        }
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

    protected VatTuDto vatTuToVatTuDto(VatTu vatTu) {
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
        vatTuDto.taoLuc( vatTu.getTaoLuc() );
        vatTuDto.trangThai( vatTu.getTrangThai() );
        vatTuDto.anhVatTus( anhVatTuListToAnhVatTuDtoList( vatTu.getAnhVatTus() ) );
        vatTuDto.thongTinGias( thongTinGiaListToThongTinGiaDtoList( vatTu.getThongTinGias() ) );

        return vatTuDto.build();
    }

    protected VatTuTronGoiDto vatTuTronGoiToVatTuTronGoiDto(VatTuTronGoi vatTuTronGoi) {
        if ( vatTuTronGoi == null ) {
            return null;
        }

        VatTuTronGoiDto.VatTuTronGoiDtoBuilder vatTuTronGoiDto = VatTuTronGoiDto.builder();

        vatTuTronGoiDto.id( vatTuTronGoi.getId() );
        vatTuTronGoiDto.vatTu( vatTuToVatTuDto( vatTuTronGoi.getVatTu() ) );
        vatTuTronGoiDto.moTa( vatTuTronGoi.getMoTa() );
        vatTuTronGoiDto.soLuong( vatTuTronGoi.getSoLuong() );
        vatTuTronGoiDto.gia( vatTuTronGoi.getGia() );
        vatTuTronGoiDto.gm( vatTuTronGoi.getGm() );
        vatTuTronGoiDto.taoLuc( vatTuTronGoi.getTaoLuc() );
        vatTuTronGoiDto.thoiGianBaoHanh( vatTuTronGoi.getThoiGianBaoHanh() );
        vatTuTronGoiDto.duocBaoHanh( vatTuTronGoi.getDuocBaoHanh() );
        vatTuTronGoiDto.duocXem( vatTuTronGoi.getDuocXem() );
        vatTuTronGoiDto.trangThai( vatTuTronGoi.getTrangThai() );

        return vatTuTronGoiDto.build();
    }

    protected Set<VatTuTronGoiDto> vatTuTronGoiListToVatTuTronGoiDtoSet(List<VatTuTronGoi> list) {
        if ( list == null ) {
            return null;
        }

        Set<VatTuTronGoiDto> set = new LinkedHashSet<VatTuTronGoiDto>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( VatTuTronGoi vatTuTronGoi : list ) {
            set.add( vatTuTronGoiToVatTuTronGoiDto( vatTuTronGoi ) );
        }

        return set;
    }

    protected CoSo coSoDtoToCoSo(CoSoDto coSoDto) {
        if ( coSoDto == null ) {
            return null;
        }

        CoSo.CoSoBuilder coSo = CoSo.builder();

        coSo.id( coSoDto.getId() );
        coSo.ma( coSoDto.getMa() );
        coSo.ten( coSoDto.getTen() );
        coSo.dcVanPhong( coSoDto.getDcVanPhong() );
        coSo.dcKho( coSoDto.getDcKho() );
        coSo.taoLuc( coSoDto.getTaoLuc() );
        coSo.trangThai( coSoDto.getTrangThai() );

        return coSo.build();
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

    protected ThuongHieu thuongHieuDtoToThuongHieu(ThuongHieuDto thuongHieuDto) {
        if ( thuongHieuDto == null ) {
            return null;
        }

        ThuongHieu.ThuongHieuBuilder thuongHieu = ThuongHieu.builder();

        thuongHieu.id( thuongHieuDto.getId() );
        thuongHieu.tenQuocTe( thuongHieuDto.getTenQuocTe() );
        thuongHieu.ten( thuongHieuDto.getTen() );
        thuongHieu.quocGia( thuongHieuDto.getQuocGia() );
        thuongHieu.sdt( thuongHieuDto.getSdt() );
        thuongHieu.email( thuongHieuDto.getEmail() );
        thuongHieu.trangThai( thuongHieuDto.getTrangThai() );

        return thuongHieu.build();
    }

    protected NhomTronGoi nhomTronGoiDtoToNhomTronGoi(NhomTronGoiDto nhomTronGoiDto) {
        if ( nhomTronGoiDto == null ) {
            return null;
        }

        NhomTronGoi.NhomTronGoiBuilder nhomTronGoi = NhomTronGoi.builder();

        nhomTronGoi.id( nhomTronGoiDto.getId() );
        nhomTronGoi.nganhHang( nganhHangDtoToNganhHang( nhomTronGoiDto.getNganhHang() ) );
        nhomTronGoi.ten( nhomTronGoiDto.getTen() );
        nhomTronGoi.thuongHieuTamPin( thuongHieuDtoToThuongHieu( nhomTronGoiDto.getThuongHieuTamPin() ) );
        nhomTronGoi.thuongHieuInverter( thuongHieuDtoToThuongHieu( nhomTronGoiDto.getThuongHieuInverter() ) );
        nhomTronGoi.thuongHieuPinLuuTru( thuongHieuDtoToThuongHieu( nhomTronGoiDto.getThuongHieuPinLuuTru() ) );
        nhomTronGoi.taoLuc( nhomTronGoiDto.getTaoLuc() );
        nhomTronGoi.trangThai( nhomTronGoiDto.getTrangThai() );

        return nhomTronGoi.build();
    }

    protected TepTin tepTinDtoToTepTin(TepTinDto tepTinDto) {
        if ( tepTinDto == null ) {
            return null;
        }

        TepTin.TepTinBuilder tepTin = TepTin.builder();

        tepTin.id( tepTinDto.getId() );
        tepTin.tenTepGoc( tepTinDto.getTenTepGoc() );
        tepTin.tenTaiLen( tepTinDto.getTenTaiLen() );
        tepTin.tenLuuTru( tepTinDto.getTenLuuTru() );
        tepTin.duongDan( tepTinDto.getDuongDan() );
        tepTin.loaiTepTin( tepTinDto.getLoaiTepTin() );
        tepTin.duoiTep( tepTinDto.getDuoiTep() );
        tepTin.kichCo( tepTinDto.getKichCo() );
        tepTin.moTa( tepTinDto.getMoTa() );
        tepTin.taoLuc( tepTinDto.getTaoLuc() );
        tepTin.suaLuc( tepTinDto.getSuaLuc() );
        tepTin.trangThai( tepTinDto.getTrangThai() );

        return tepTin.build();
    }

    protected NhomVatTu nhomVatTuDtoToNhomVatTu(NhomVatTuDto nhomVatTuDto) {
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

    protected AnhVatTu anhVatTuDtoToAnhVatTu(AnhVatTuDto anhVatTuDto) {
        if ( anhVatTuDto == null ) {
            return null;
        }

        AnhVatTu.AnhVatTuBuilder anhVatTu = AnhVatTu.builder();

        anhVatTu.id( anhVatTuDto.getId() );
        anhVatTu.tepTin( tepTinDtoToTepTin( anhVatTuDto.getTepTin() ) );
        anhVatTu.anhChinh( anhVatTuDto.getAnhChinh() );
        anhVatTu.trangThai( anhVatTuDto.getTrangThai() );

        return anhVatTu.build();
    }

    protected List<AnhVatTu> anhVatTuDtoListToAnhVatTuList(List<AnhVatTuDto> list) {
        if ( list == null ) {
            return null;
        }

        List<AnhVatTu> list1 = new ArrayList<AnhVatTu>( list.size() );
        for ( AnhVatTuDto anhVatTuDto : list ) {
            list1.add( anhVatTuDtoToAnhVatTu( anhVatTuDto ) );
        }

        return list1;
    }

    protected ThongTinGia thongTinGiaDtoToThongTinGia(ThongTinGiaDto thongTinGiaDto) {
        if ( thongTinGiaDto == null ) {
            return null;
        }

        ThongTinGia.ThongTinGiaBuilder thongTinGia = ThongTinGia.builder();

        thongTinGia.id( thongTinGiaDto.getId() );
        List<GiaInfo> list = thongTinGiaDto.getDsGia();
        if ( list != null ) {
            thongTinGia.dsGia( new ArrayList<GiaInfo>( list ) );
        }
        thongTinGia.taoLuc( thongTinGiaDto.getTaoLuc() );
        thongTinGia.trangThai( thongTinGiaDto.getTrangThai() );

        return thongTinGia.build();
    }

    protected List<ThongTinGia> thongTinGiaDtoListToThongTinGiaList(List<ThongTinGiaDto> list) {
        if ( list == null ) {
            return null;
        }

        List<ThongTinGia> list1 = new ArrayList<ThongTinGia>( list.size() );
        for ( ThongTinGiaDto thongTinGiaDto : list ) {
            list1.add( thongTinGiaDtoToThongTinGia( thongTinGiaDto ) );
        }

        return list1;
    }

    protected VatTu vatTuDtoToVatTu(VatTuDto vatTuDto) {
        if ( vatTuDto == null ) {
            return null;
        }

        VatTu.VatTuBuilder vatTu = VatTu.builder();

        vatTu.id( vatTuDto.getId() );
        vatTu.ma( vatTuDto.getMa() );
        vatTu.nhomVatTu( nhomVatTuDtoToNhomVatTu( vatTuDto.getNhomVatTu() ) );
        vatTu.thuongHieu( thuongHieuDtoToThuongHieu( vatTuDto.getThuongHieu() ) );
        vatTu.ten( vatTuDto.getTen() );
        vatTu.sheetLink( vatTuDto.getSheetLink() );
        vatTu.donVi( vatTuDto.getDonVi() );
        vatTu.moTaBaoGia( vatTuDto.getMoTaBaoGia() );
        vatTu.moTaHopDong( vatTuDto.getMoTaHopDong() );
        Map<String, Object> map = vatTuDto.getDuLieuRieng();
        if ( map != null ) {
            vatTu.duLieuRieng( new LinkedHashMap<String, Object>( map ) );
        }
        vatTu.taoLuc( vatTuDto.getTaoLuc() );
        vatTu.trangThai( vatTuDto.getTrangThai() );
        vatTu.anhVatTus( anhVatTuDtoListToAnhVatTuList( vatTuDto.getAnhVatTus() ) );
        vatTu.thongTinGias( thongTinGiaDtoListToThongTinGiaList( vatTuDto.getThongTinGias() ) );

        return vatTu.build();
    }

    protected VatTuTronGoi vatTuTronGoiDtoToVatTuTronGoi(VatTuTronGoiDto vatTuTronGoiDto) {
        if ( vatTuTronGoiDto == null ) {
            return null;
        }

        VatTuTronGoi.VatTuTronGoiBuilder vatTuTronGoi = VatTuTronGoi.builder();

        vatTuTronGoi.id( vatTuTronGoiDto.getId() );
        vatTuTronGoi.vatTu( vatTuDtoToVatTu( vatTuTronGoiDto.getVatTu() ) );
        vatTuTronGoi.moTa( vatTuTronGoiDto.getMoTa() );
        vatTuTronGoi.soLuong( vatTuTronGoiDto.getSoLuong() );
        vatTuTronGoi.gia( vatTuTronGoiDto.getGia() );
        vatTuTronGoi.gm( vatTuTronGoiDto.getGm() );
        vatTuTronGoi.thoiGianBaoHanh( vatTuTronGoiDto.getThoiGianBaoHanh() );
        vatTuTronGoi.duocBaoHanh( vatTuTronGoiDto.getDuocBaoHanh() );
        vatTuTronGoi.duocXem( vatTuTronGoiDto.getDuocXem() );
        vatTuTronGoi.taoLuc( vatTuTronGoiDto.getTaoLuc() );
        vatTuTronGoi.trangThai( vatTuTronGoiDto.getTrangThai() );

        return vatTuTronGoi.build();
    }

    protected List<VatTuTronGoi> vatTuTronGoiDtoSetToVatTuTronGoiList(Set<VatTuTronGoiDto> set) {
        if ( set == null ) {
            return null;
        }

        List<VatTuTronGoi> list = new ArrayList<VatTuTronGoi>( set.size() );
        for ( VatTuTronGoiDto vatTuTronGoiDto : set ) {
            list.add( vatTuTronGoiDtoToVatTuTronGoi( vatTuTronGoiDto ) );
        }

        return list;
    }
}
