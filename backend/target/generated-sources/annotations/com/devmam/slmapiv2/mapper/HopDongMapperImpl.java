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
    date = "2025-12-26T00:31:42+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
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

        hopDongDto.id( hopDong.getId() );
        hopDongDto.coSo( coSoToCoSoDto( hopDong.getCoSo() ) );
        hopDongDto.nghanhHang( nganhHangToNganhHangDto( hopDong.getNghanhHang() ) );
        hopDongDto.ten( hopDong.getTen() );
        hopDongDto.loaiHeThong( hopDong.getLoaiHeThong() );
        hopDongDto.loaiPha( hopDong.getLoaiPha() );
        hopDongDto.sanLuongToiThieu( hopDong.getSanLuongToiThieu() );
        hopDongDto.sanLuongToiDa( hopDong.getSanLuongToiDa() );
        hopDongDto.giaKhungSat( hopDong.getGiaKhungSat() );
        hopDongDto.moTa( hopDong.getMoTa() );
        hopDongDto.nguoiGioiThieu( nguoiDungToNguoiDungDto( hopDong.getNguoiGioiThieu() ) );
        hopDongDto.khachHang( khachHangToKhachHangDto( hopDong.getKhachHang() ) );
        hopDongDto.tongGia( hopDong.getTongGia() );
        hopDongDto.taoLuc( hopDong.getTaoLuc() );
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

        coSoDto.id( coSo.getId() );
        coSoDto.ma( coSo.getMa() );
        coSoDto.ten( coSo.getTen() );
        coSoDto.dcVanPhong( coSo.getDcVanPhong() );
        coSoDto.dcKho( coSo.getDcKho() );
        coSoDto.taoLuc( coSo.getTaoLuc() );
        coSoDto.trangThai( coSo.getTrangThai() );

        return coSoDto.build();
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

    protected KhachHangDto khachHangToKhachHangDto(KhachHang khachHang) {
        if ( khachHang == null ) {
            return null;
        }

        KhachHangDto.KhachHangDtoBuilder khachHangDto = KhachHangDto.builder();

        khachHangDto.id( khachHang.getId() );
        khachHangDto.email( khachHang.getEmail() );
        khachHangDto.sdt( khachHang.getSdt() );
        khachHangDto.hoVaTen( khachHang.getHoVaTen() );
        khachHangDto.gioiTinh( khachHang.getGioiTinh() );
        khachHangDto.sinhNhat( khachHang.getSinhNhat() );
        khachHangDto.diaChi( khachHang.getDiaChi() );
        khachHangDto.daBanDuocHang( khachHang.getDaBanDuocHang() );
        khachHangDto.taoLuc( khachHang.getTaoLuc() );
        khachHangDto.trangThai( khachHang.getTrangThai() );

        return khachHangDto.build();
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

        nguoiDungDto.id( nguoiDung.getId() );
        nguoiDungDto.coSo( coSoToCoSoDto( nguoiDung.getCoSo() ) );
        nguoiDungDto.phanQuyen( nguoiDung.getPhanQuyen() );
        nguoiDungDto.email( nguoiDung.getEmail() );
        nguoiDungDto.sdt( nguoiDung.getSdt() );
        nguoiDungDto.matKhau( nguoiDung.getMatKhau() );
        nguoiDungDto.hoVaTen( nguoiDung.getHoVaTen() );
        nguoiDungDto.gioiTinh( nguoiDung.getGioiTinh() );
        nguoiDungDto.sinhNhat( nguoiDung.getSinhNhat() );
        nguoiDungDto.phanTramHoaHong( nguoiDung.getPhanTramHoaHong() );
        nguoiDungDto.tongHoaHong( nguoiDung.getTongHoaHong() );
        nguoiDungDto.diaChi( nguoiDung.getDiaChi() );
        nguoiDungDto.nganHang( nguoiDung.getNganHang() );
        nguoiDungDto.maNganHang( nguoiDung.getMaNganHang() );
        nguoiDungDto.taoLuc( nguoiDung.getTaoLuc() );
        nguoiDungDto.trangThai( nguoiDung.getTrangThai() );
        nguoiDungDto.khachHangs( khachHangListToKhachHangDtoList( nguoiDung.getKhachHangs() ) );

        return nguoiDungDto.build();
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
        vatTuDto.thoiGianBaoHanh( vatTu.getThoiGianBaoHanh() );
        vatTuDto.gm( vatTu.getGm() );
        vatTuDto.vatTuChinh( vatTu.getVatTuChinh() );
        vatTuDto.taoLuc( vatTu.getTaoLuc() );
        vatTuDto.trangThai( vatTu.getTrangThai() );
        vatTuDto.anhVatTus( anhVatTuListToAnhVatTuDtoList( vatTu.getAnhVatTus() ) );
        vatTuDto.thongTinGias( thongTinGiaListToThongTinGiaDtoList( vatTu.getThongTinGias() ) );

        return vatTuDto.build();
    }

    protected VatTuHopDongDto vatTuHopDongToVatTuHopDongDto(VatTuHopDong vatTuHopDong) {
        if ( vatTuHopDong == null ) {
            return null;
        }

        VatTuHopDongDto.VatTuHopDongDtoBuilder vatTuHopDongDto = VatTuHopDongDto.builder();

        vatTuHopDongDto.id( vatTuHopDong.getId() );
        vatTuHopDongDto.vatTu( vatTuToVatTuDto( vatTuHopDong.getVatTu() ) );
        vatTuHopDongDto.moTa( vatTuHopDong.getMoTa() );
        vatTuHopDongDto.soLuong( vatTuHopDong.getSoLuong() );
        vatTuHopDongDto.gm( vatTuHopDong.getGm() );
        vatTuHopDongDto.giaHeThong( vatTuHopDong.getGiaHeThong() );
        vatTuHopDongDto.giaHienThi( vatTuHopDong.getGiaHienThi() );
        vatTuHopDongDto.thoiGianBaoHanh( vatTuHopDong.getThoiGianBaoHanh() );
        vatTuHopDongDto.baoHanhBatDau( vatTuHopDong.getBaoHanhBatDau() );
        vatTuHopDongDto.baoHanhKetThuc( vatTuHopDong.getBaoHanhKetThuc() );
        vatTuHopDongDto.duocBaoHanh( vatTuHopDong.getDuocBaoHanh() );
        vatTuHopDongDto.taoLuc( vatTuHopDong.getTaoLuc() );
        vatTuHopDongDto.trangThai( vatTuHopDong.getTrangThai() );

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
