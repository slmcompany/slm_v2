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
    date = "2026-02-25T15:17:10+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.45.0.v20260128-0750, environment: Java 21.0.9 (Eclipse Adoptium)"
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

        tronGoiDto.banChay( tronGoi.getBanChay() );
        tronGoiDto.coSo( coSoToCoSoDto( tronGoi.getCoSo() ) );
        tronGoiDto.congSuatHeThong( tronGoi.getCongSuatHeThong() );
        tronGoiDto.giaKhungSat( tronGoi.getGiaKhungSat() );
        tronGoiDto.gmTong( tronGoi.getGmTong() );
        tronGoiDto.id( tronGoi.getId() );
        tronGoiDto.loaiHeThong( tronGoi.getLoaiHeThong() );
        tronGoiDto.loaiPha( tronGoi.getLoaiPha() );
        tronGoiDto.moTa( tronGoi.getMoTa() );
        tronGoiDto.nhomTronGoi( nhomTronGoiToNhomTronGoiDto( tronGoi.getNhomTronGoi() ) );
        tronGoiDto.sanLuongToiDa( tronGoi.getSanLuongToiDa() );
        tronGoiDto.sanLuongToiThieu( tronGoi.getSanLuongToiThieu() );
        tronGoiDto.taoLuc( tronGoi.getTaoLuc() );
        tronGoiDto.ten( tronGoi.getTen() );
        tronGoiDto.tepTin( tepTinMapper.toDto( tronGoi.getTepTin() ) );
        tronGoiDto.tongGia( tronGoi.getTongGia() );
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

        tronGoi.banChay( tronGoiDto.getBanChay() );
        tronGoi.coSo( coSoDtoToCoSo( tronGoiDto.getCoSo() ) );
        tronGoi.congSuatHeThong( tronGoiDto.getCongSuatHeThong() );
        tronGoi.giaKhungSat( tronGoiDto.getGiaKhungSat() );
        tronGoi.gmTong( tronGoiDto.getGmTong() );
        tronGoi.id( tronGoiDto.getId() );
        tronGoi.loaiHeThong( tronGoiDto.getLoaiHeThong() );
        tronGoi.loaiPha( tronGoiDto.getLoaiPha() );
        tronGoi.moTa( tronGoiDto.getMoTa() );
        tronGoi.nhomTronGoi( nhomTronGoiDtoToNhomTronGoi( tronGoiDto.getNhomTronGoi() ) );
        tronGoi.sanLuongToiDa( tronGoiDto.getSanLuongToiDa() );
        tronGoi.sanLuongToiThieu( tronGoiDto.getSanLuongToiThieu() );
        tronGoi.taoLuc( tronGoiDto.getTaoLuc() );
        tronGoi.ten( tronGoiDto.getTen() );
        tronGoi.tepTin( tepTinDtoToTepTin( tronGoiDto.getTepTin() ) );
        tronGoi.tongGia( tronGoiDto.getTongGia() );
        tronGoi.trangThai( tronGoiDto.getTrangThai() );
        tronGoi.vatTuTronGois( vatTuTronGoiDtoSetToVatTuTronGoiList( tronGoiDto.getVatTuTronGois() ) );

        return tronGoi.build();
    }

    protected CoSoDto coSoToCoSoDto(CoSo coSo) {
        if ( coSo == null ) {
            return null;
        }

        CoSoDto.CoSoDtoBuilder coSoDto = CoSoDto.builder();

        coSoDto.dcKho( coSo.getDcKho() );
        coSoDto.dcVanPhong( coSo.getDcVanPhong() );
        coSoDto.id( coSo.getId() );
        coSoDto.ma( coSo.getMa() );
        coSoDto.taoLuc( coSo.getTaoLuc() );
        coSoDto.ten( coSo.getTen() );
        coSoDto.trangThai( coSo.getTrangThai() );

        return coSoDto.build();
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

    protected ThuongHieuDto thuongHieuToThuongHieuDto(ThuongHieu thuongHieu) {
        if ( thuongHieu == null ) {
            return null;
        }

        ThuongHieuDto.ThuongHieuDtoBuilder thuongHieuDto = ThuongHieuDto.builder();

        thuongHieuDto.email( thuongHieu.getEmail() );
        thuongHieuDto.id( thuongHieu.getId() );
        thuongHieuDto.moTa( thuongHieu.getMoTa() );
        thuongHieuDto.quocGia( thuongHieu.getQuocGia() );
        thuongHieuDto.sdt( thuongHieu.getSdt() );
        thuongHieuDto.ten( thuongHieu.getTen() );
        thuongHieuDto.tenQuocTe( thuongHieu.getTenQuocTe() );
        thuongHieuDto.tepTin( tepTinMapper.toDto( thuongHieu.getTepTin() ) );
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
        nhomTronGoiDto.taoLuc( nhomTronGoi.getTaoLuc() );
        nhomTronGoiDto.ten( nhomTronGoi.getTen() );
        nhomTronGoiDto.thuongHieuInverter( thuongHieuToThuongHieuDto( nhomTronGoi.getThuongHieuInverter() ) );
        nhomTronGoiDto.thuongHieuPinLuuTru( thuongHieuToThuongHieuDto( nhomTronGoi.getThuongHieuPinLuuTru() ) );
        nhomTronGoiDto.thuongHieuTamPin( thuongHieuToThuongHieuDto( nhomTronGoi.getThuongHieuTamPin() ) );
        nhomTronGoiDto.trangThai( nhomTronGoi.getTrangThai() );

        return nhomTronGoiDto.build();
    }

    protected AnhVatTuDto anhVatTuToAnhVatTuDto(AnhVatTu anhVatTu) {
        if ( anhVatTu == null ) {
            return null;
        }

        AnhVatTuDto.AnhVatTuDtoBuilder anhVatTuDto = AnhVatTuDto.builder();

        anhVatTuDto.anhChinh( anhVatTu.getAnhChinh() );
        anhVatTuDto.id( anhVatTu.getId() );
        anhVatTuDto.tepTin( tepTinMapper.toDto( anhVatTu.getTepTin() ) );
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

    protected VatTuDto vatTuToVatTuDto(VatTu vatTu) {
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
        vatTuDto.gm( vatTu.getGm() );
        vatTuDto.id( vatTu.getId() );
        vatTuDto.ma( vatTu.getMa() );
        vatTuDto.moTaBaoGia( vatTu.getMoTaBaoGia() );
        vatTuDto.moTaHopDong( vatTu.getMoTaHopDong() );
        vatTuDto.nhomVatTu( nhomVatTuToNhomVatTuDto( vatTu.getNhomVatTu() ) );
        vatTuDto.sheetLink( vatTu.getSheetLink() );
        vatTuDto.taoLuc( vatTu.getTaoLuc() );
        vatTuDto.ten( vatTu.getTen() );
        vatTuDto.thoiGianBaoHanh( vatTu.getThoiGianBaoHanh() );
        vatTuDto.thongTinGias( thongTinGiaListToThongTinGiaDtoList( vatTu.getThongTinGias() ) );
        vatTuDto.thuongHieu( thuongHieuToThuongHieuDto( vatTu.getThuongHieu() ) );
        vatTuDto.trangThai( vatTu.getTrangThai() );
        vatTuDto.vatTuChinh( vatTu.getVatTuChinh() );

        return vatTuDto.build();
    }

    protected VatTuTronGoiDto vatTuTronGoiToVatTuTronGoiDto(VatTuTronGoi vatTuTronGoi) {
        if ( vatTuTronGoi == null ) {
            return null;
        }

        VatTuTronGoiDto.VatTuTronGoiDtoBuilder vatTuTronGoiDto = VatTuTronGoiDto.builder();

        vatTuTronGoiDto.duocBaoHanh( vatTuTronGoi.getDuocBaoHanh() );
        vatTuTronGoiDto.duocXem( vatTuTronGoi.getDuocXem() );
        vatTuTronGoiDto.gia( vatTuTronGoi.getGia() );
        vatTuTronGoiDto.gm( vatTuTronGoi.getGm() );
        vatTuTronGoiDto.id( vatTuTronGoi.getId() );
        vatTuTronGoiDto.moTa( vatTuTronGoi.getMoTa() );
        vatTuTronGoiDto.soLuong( vatTuTronGoi.getSoLuong() );
        vatTuTronGoiDto.taoLuc( vatTuTronGoi.getTaoLuc() );
        vatTuTronGoiDto.thoiGianBaoHanh( vatTuTronGoi.getThoiGianBaoHanh() );
        vatTuTronGoiDto.trangThai( vatTuTronGoi.getTrangThai() );
        vatTuTronGoiDto.vatTu( vatTuToVatTuDto( vatTuTronGoi.getVatTu() ) );

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

        coSo.dcKho( coSoDto.getDcKho() );
        coSo.dcVanPhong( coSoDto.getDcVanPhong() );
        coSo.id( coSoDto.getId() );
        coSo.ma( coSoDto.getMa() );
        coSo.taoLuc( coSoDto.getTaoLuc() );
        coSo.ten( coSoDto.getTen() );
        coSo.trangThai( coSoDto.getTrangThai() );

        return coSo.build();
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

    protected TepTin tepTinDtoToTepTin(TepTinDto tepTinDto) {
        if ( tepTinDto == null ) {
            return null;
        }

        TepTin.TepTinBuilder tepTin = TepTin.builder();

        tepTin.duoiTep( tepTinDto.getDuoiTep() );
        tepTin.duongDan( tepTinDto.getDuongDan() );
        tepTin.id( tepTinDto.getId() );
        tepTin.kichCo( tepTinDto.getKichCo() );
        tepTin.loaiTepTin( tepTinDto.getLoaiTepTin() );
        tepTin.moTa( tepTinDto.getMoTa() );
        tepTin.suaLuc( tepTinDto.getSuaLuc() );
        tepTin.taoLuc( tepTinDto.getTaoLuc() );
        tepTin.tenLuuTru( tepTinDto.getTenLuuTru() );
        tepTin.tenTaiLen( tepTinDto.getTenTaiLen() );
        tepTin.tenTepGoc( tepTinDto.getTenTepGoc() );
        tepTin.trangThai( tepTinDto.getTrangThai() );

        return tepTin.build();
    }

    protected ThuongHieu thuongHieuDtoToThuongHieu(ThuongHieuDto thuongHieuDto) {
        if ( thuongHieuDto == null ) {
            return null;
        }

        ThuongHieu.ThuongHieuBuilder thuongHieu = ThuongHieu.builder();

        thuongHieu.email( thuongHieuDto.getEmail() );
        thuongHieu.id( thuongHieuDto.getId() );
        thuongHieu.moTa( thuongHieuDto.getMoTa() );
        thuongHieu.quocGia( thuongHieuDto.getQuocGia() );
        thuongHieu.sdt( thuongHieuDto.getSdt() );
        thuongHieu.ten( thuongHieuDto.getTen() );
        thuongHieu.tenQuocTe( thuongHieuDto.getTenQuocTe() );
        thuongHieu.tepTin( tepTinDtoToTepTin( thuongHieuDto.getTepTin() ) );
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
        nhomTronGoi.taoLuc( nhomTronGoiDto.getTaoLuc() );
        nhomTronGoi.ten( nhomTronGoiDto.getTen() );
        nhomTronGoi.thuongHieuInverter( thuongHieuDtoToThuongHieu( nhomTronGoiDto.getThuongHieuInverter() ) );
        nhomTronGoi.thuongHieuPinLuuTru( thuongHieuDtoToThuongHieu( nhomTronGoiDto.getThuongHieuPinLuuTru() ) );
        nhomTronGoi.thuongHieuTamPin( thuongHieuDtoToThuongHieu( nhomTronGoiDto.getThuongHieuTamPin() ) );
        nhomTronGoi.trangThai( nhomTronGoiDto.getTrangThai() );

        return nhomTronGoi.build();
    }

    protected AnhVatTu anhVatTuDtoToAnhVatTu(AnhVatTuDto anhVatTuDto) {
        if ( anhVatTuDto == null ) {
            return null;
        }

        AnhVatTu.AnhVatTuBuilder anhVatTu = AnhVatTu.builder();

        anhVatTu.anhChinh( anhVatTuDto.getAnhChinh() );
        anhVatTu.id( anhVatTuDto.getId() );
        anhVatTu.tepTin( tepTinDtoToTepTin( anhVatTuDto.getTepTin() ) );
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

    protected NhomVatTu nhomVatTuDtoToNhomVatTu(NhomVatTuDto nhomVatTuDto) {
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

    protected ThongTinGia thongTinGiaDtoToThongTinGia(ThongTinGiaDto thongTinGiaDto) {
        if ( thongTinGiaDto == null ) {
            return null;
        }

        ThongTinGia.ThongTinGiaBuilder thongTinGia = ThongTinGia.builder();

        List<GiaInfo> list = thongTinGiaDto.getDsGia();
        if ( list != null ) {
            thongTinGia.dsGia( new ArrayList<GiaInfo>( list ) );
        }
        thongTinGia.id( thongTinGiaDto.getId() );
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

        vatTu.anhVatTus( anhVatTuDtoListToAnhVatTuList( vatTuDto.getAnhVatTus() ) );
        vatTu.donVi( vatTuDto.getDonVi() );
        Map<String, Object> map = vatTuDto.getDuLieuRieng();
        if ( map != null ) {
            vatTu.duLieuRieng( new LinkedHashMap<String, Object>( map ) );
        }
        vatTu.gm( vatTuDto.getGm() );
        vatTu.id( vatTuDto.getId() );
        vatTu.ma( vatTuDto.getMa() );
        vatTu.moTaBaoGia( vatTuDto.getMoTaBaoGia() );
        vatTu.moTaHopDong( vatTuDto.getMoTaHopDong() );
        vatTu.nhomVatTu( nhomVatTuDtoToNhomVatTu( vatTuDto.getNhomVatTu() ) );
        vatTu.sheetLink( vatTuDto.getSheetLink() );
        vatTu.taoLuc( vatTuDto.getTaoLuc() );
        vatTu.ten( vatTuDto.getTen() );
        vatTu.thoiGianBaoHanh( vatTuDto.getThoiGianBaoHanh() );
        vatTu.thongTinGias( thongTinGiaDtoListToThongTinGiaList( vatTuDto.getThongTinGias() ) );
        vatTu.thuongHieu( thuongHieuDtoToThuongHieu( vatTuDto.getThuongHieu() ) );
        vatTu.trangThai( vatTuDto.getTrangThai() );
        vatTu.vatTuChinh( vatTuDto.getVatTuChinh() );

        return vatTu.build();
    }

    protected VatTuTronGoi vatTuTronGoiDtoToVatTuTronGoi(VatTuTronGoiDto vatTuTronGoiDto) {
        if ( vatTuTronGoiDto == null ) {
            return null;
        }

        VatTuTronGoi.VatTuTronGoiBuilder vatTuTronGoi = VatTuTronGoi.builder();

        vatTuTronGoi.duocBaoHanh( vatTuTronGoiDto.getDuocBaoHanh() );
        vatTuTronGoi.duocXem( vatTuTronGoiDto.getDuocXem() );
        vatTuTronGoi.gia( vatTuTronGoiDto.getGia() );
        vatTuTronGoi.gm( vatTuTronGoiDto.getGm() );
        vatTuTronGoi.id( vatTuTronGoiDto.getId() );
        vatTuTronGoi.moTa( vatTuTronGoiDto.getMoTa() );
        vatTuTronGoi.soLuong( vatTuTronGoiDto.getSoLuong() );
        vatTuTronGoi.taoLuc( vatTuTronGoiDto.getTaoLuc() );
        vatTuTronGoi.thoiGianBaoHanh( vatTuTronGoiDto.getThoiGianBaoHanh() );
        vatTuTronGoi.trangThai( vatTuTronGoiDto.getTrangThai() );
        vatTuTronGoi.vatTu( vatTuDtoToVatTu( vatTuTronGoiDto.getVatTu() ) );

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
