package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.AnhVatTuDto;
import com.devmam.slmapiv2.dto.response.entities.CoSoDto;
import com.devmam.slmapiv2.dto.response.entities.HopDongDto;
import com.devmam.slmapiv2.dto.response.entities.KhachHangDto;
import com.devmam.slmapiv2.dto.response.entities.NganhHangDto;
import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.dto.response.entities.NhomVatTuDto;
import com.devmam.slmapiv2.dto.response.entities.ThongTinGiaDto;
import com.devmam.slmapiv2.dto.response.entities.ThuongHieuDto;
import com.devmam.slmapiv2.dto.response.entities.VatTuDto;
import com.devmam.slmapiv2.dto.response.entities.VatTuHopDongDto;
import com.devmam.slmapiv2.entities.AnhVatTu;
import com.devmam.slmapiv2.entities.CoSo;
import com.devmam.slmapiv2.entities.HopDong;
import com.devmam.slmapiv2.entities.KhachHang;
import com.devmam.slmapiv2.entities.NganhHang;
import com.devmam.slmapiv2.entities.NguoiDung;
import com.devmam.slmapiv2.entities.NhomVatTu;
import com.devmam.slmapiv2.entities.ThongTinGia;
import com.devmam.slmapiv2.entities.ThuongHieu;
import com.devmam.slmapiv2.entities.VatTu;
import com.devmam.slmapiv2.entities.VatTuHopDong;
import com.devmam.slmapiv2.entities.commons.GiaInfo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-25T15:17:09+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.45.0.v20260128-0750, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class HopDongMapperImpl implements HopDongMapper {

    @Autowired
    private TepTinMapper tepTinMapper;

    @Override
    public HopDongDto toDto(HopDong hopDong) {
        if ( hopDong == null ) {
            return null;
        }

        HopDongDto.HopDongDtoBuilder hopDongDto = HopDongDto.builder();

        hopDongDto.coSo( coSoToCoSoDto( hopDong.getCoSo() ) );
        hopDongDto.giaKhungSat( hopDong.getGiaKhungSat() );
        hopDongDto.id( hopDong.getId() );
        hopDongDto.khachHang( khachHangToKhachHangDto( hopDong.getKhachHang() ) );
        hopDongDto.loaiHeThong( hopDong.getLoaiHeThong() );
        hopDongDto.loaiPha( hopDong.getLoaiPha() );
        hopDongDto.moTa( hopDong.getMoTa() );
        hopDongDto.nghanhHang( nganhHangToNganhHangDto( hopDong.getNghanhHang() ) );
        hopDongDto.nguoiGioiThieu( nguoiDungToNguoiDungDto( hopDong.getNguoiGioiThieu() ) );
        hopDongDto.sanLuongToiDa( hopDong.getSanLuongToiDa() );
        hopDongDto.sanLuongToiThieu( hopDong.getSanLuongToiThieu() );
        hopDongDto.taoLuc( hopDong.getTaoLuc() );
        hopDongDto.ten( hopDong.getTen() );
        hopDongDto.tongGia( hopDong.getTongGia() );
        hopDongDto.trangThai( hopDong.getTrangThai() );
        hopDongDto.vatTuHopDongs( vatTuHopDongListToVatTuHopDongDtoList( hopDong.getVatTuHopDongs() ) );

        return hopDongDto.build();
    }

    @Override
    public List<HopDongDto> toDtoList(List<HopDong> hopDongList) {
        if ( hopDongList == null ) {
            return null;
        }

        List<HopDongDto> list = new ArrayList<HopDongDto>( hopDongList.size() );
        for ( HopDong hopDong : hopDongList ) {
            list.add( toDto( hopDong ) );
        }

        return list;
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

    protected KhachHangDto khachHangToKhachHangDto(KhachHang khachHang) {
        if ( khachHang == null ) {
            return null;
        }

        KhachHangDto.KhachHangDtoBuilder khachHangDto = KhachHangDto.builder();

        khachHangDto.daBanDuocHang( khachHang.getDaBanDuocHang() );
        khachHangDto.diaChi( khachHang.getDiaChi() );
        khachHangDto.email( khachHang.getEmail() );
        khachHangDto.gioiTinh( khachHang.getGioiTinh() );
        khachHangDto.hoVaTen( khachHang.getHoVaTen() );
        khachHangDto.id( khachHang.getId() );
        khachHangDto.sdt( khachHang.getSdt() );
        khachHangDto.sinhNhat( khachHang.getSinhNhat() );
        khachHangDto.taoLuc( khachHang.getTaoLuc() );
        khachHangDto.trangThai( khachHang.getTrangThai() );

        return khachHangDto.build();
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

    protected List<KhachHangDto> khachHangListToKhachHangDtoList(List<KhachHang> list) {
        if ( list == null ) {
            return null;
        }

        List<KhachHangDto> list1 = new ArrayList<KhachHangDto>( list.size() );
        for ( KhachHang khachHang : list ) {
            list1.add( khachHangToKhachHangDto( khachHang ) );
        }

        return list1;
    }

    protected NguoiDungDto nguoiDungToNguoiDungDto(NguoiDung nguoiDung) {
        if ( nguoiDung == null ) {
            return null;
        }

        NguoiDungDto.NguoiDungDtoBuilder nguoiDungDto = NguoiDungDto.builder();

        nguoiDungDto.coSo( coSoToCoSoDto( nguoiDung.getCoSo() ) );
        nguoiDungDto.diaChi( nguoiDung.getDiaChi() );
        nguoiDungDto.email( nguoiDung.getEmail() );
        nguoiDungDto.gioiTinh( nguoiDung.getGioiTinh() );
        nguoiDungDto.hoVaTen( nguoiDung.getHoVaTen() );
        nguoiDungDto.id( nguoiDung.getId() );
        nguoiDungDto.khachHangs( khachHangListToKhachHangDtoList( nguoiDung.getKhachHangs() ) );
        nguoiDungDto.maNganHang( nguoiDung.getMaNganHang() );
        nguoiDungDto.matKhau( nguoiDung.getMatKhau() );
        nguoiDungDto.nganHang( nguoiDung.getNganHang() );
        nguoiDungDto.phanQuyen( nguoiDung.getPhanQuyen() );
        nguoiDungDto.phanTramHoaHong( nguoiDung.getPhanTramHoaHong() );
        nguoiDungDto.sdt( nguoiDung.getSdt() );
        nguoiDungDto.sinhNhat( nguoiDung.getSinhNhat() );
        nguoiDungDto.taoLuc( nguoiDung.getTaoLuc() );
        nguoiDungDto.tongHoaHong( nguoiDung.getTongHoaHong() );
        nguoiDungDto.trangThai( nguoiDung.getTrangThai() );

        return nguoiDungDto.build();
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

    protected VatTuHopDongDto vatTuHopDongToVatTuHopDongDto(VatTuHopDong vatTuHopDong) {
        if ( vatTuHopDong == null ) {
            return null;
        }

        VatTuHopDongDto.VatTuHopDongDtoBuilder vatTuHopDongDto = VatTuHopDongDto.builder();

        vatTuHopDongDto.baoHanhBatDau( vatTuHopDong.getBaoHanhBatDau() );
        vatTuHopDongDto.baoHanhKetThuc( vatTuHopDong.getBaoHanhKetThuc() );
        vatTuHopDongDto.duocBaoHanh( vatTuHopDong.getDuocBaoHanh() );
        vatTuHopDongDto.giaHeThong( vatTuHopDong.getGiaHeThong() );
        vatTuHopDongDto.giaHienThi( vatTuHopDong.getGiaHienThi() );
        vatTuHopDongDto.gm( vatTuHopDong.getGm() );
        vatTuHopDongDto.id( vatTuHopDong.getId() );
        vatTuHopDongDto.moTa( vatTuHopDong.getMoTa() );
        vatTuHopDongDto.soLuong( vatTuHopDong.getSoLuong() );
        vatTuHopDongDto.taoLuc( vatTuHopDong.getTaoLuc() );
        vatTuHopDongDto.thoiGianBaoHanh( vatTuHopDong.getThoiGianBaoHanh() );
        vatTuHopDongDto.trangThai( vatTuHopDong.getTrangThai() );
        vatTuHopDongDto.vatTu( vatTuToVatTuDto( vatTuHopDong.getVatTu() ) );

        return vatTuHopDongDto.build();
    }

    protected List<VatTuHopDongDto> vatTuHopDongListToVatTuHopDongDtoList(List<VatTuHopDong> list) {
        if ( list == null ) {
            return null;
        }

        List<VatTuHopDongDto> list1 = new ArrayList<VatTuHopDongDto>( list.size() );
        for ( VatTuHopDong vatTuHopDong : list ) {
            list1.add( vatTuHopDongToVatTuHopDongDto( vatTuHopDong ) );
        }

        return list1;
    }
}
