package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.BaiVietDto;
import com.devmam.slmapiv2.dto.response.entities.CoSoDto;
import com.devmam.slmapiv2.dto.response.entities.KhachHangDto;
import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.entities.BaiViet;
import com.devmam.slmapiv2.entities.CoSo;
import com.devmam.slmapiv2.entities.KhachHang;
import com.devmam.slmapiv2.entities.NguoiDung;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-12T19:29:31+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class BaiVietMapperImpl implements BaiVietMapper {

    @Autowired
    private TepTinMapper tepTinMapper;

    @Override
    public BaiVietDto toDto(BaiViet baiViet) {
        if ( baiViet == null ) {
            return null;
        }

        BaiVietDto.BaiVietDtoBuilder baiVietDto = BaiVietDto.builder();

        baiVietDto.id( baiViet.getId() );
        baiVietDto.loaiBaiViet( baiViet.getLoaiBaiViet() );
        baiVietDto.tieuDe( baiViet.getTieuDe() );
        baiVietDto.anhBia( tepTinMapper.toDto( baiViet.getAnhBia() ) );
        baiVietDto.anhNgoai( tepTinMapper.toDto( baiViet.getAnhNgoai() ) );
        baiVietDto.duongDanYoutube( baiViet.getDuongDanYoutube() );
        baiVietDto.noiDung( tepTinMapper.toDto( baiViet.getNoiDung() ) );
        baiVietDto.taoBoi( nguoiDungToNguoiDungDto( baiViet.getTaoBoi() ) );
        baiVietDto.lienQuan( baiViet.getLienQuan() );
        baiVietDto.hoatDong( baiViet.getHoatDong() );
        baiVietDto.taoLuc( baiViet.getTaoLuc() );
        baiVietDto.trangThai( baiViet.getTrangThai() );

        return baiVietDto.build();
    }

    @Override
    public List<BaiVietDto> toDtoList(List<BaiViet> baiVietList) {
        if ( baiVietList == null ) {
            return null;
        }

        List<BaiVietDto> list = new ArrayList<BaiVietDto>( baiVietList.size() );
        for ( BaiViet baiViet : baiVietList ) {
            list.add( toDto( baiViet ) );
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
        coSoDto.sdt( coSo.getSdt() );
        coSoDto.dcVanPhong( coSo.getDcVanPhong() );
        coSoDto.dcKho( coSo.getDcKho() );
        coSoDto.taoLuc( coSo.getTaoLuc() );
        coSoDto.trangThai( coSo.getTrangThai() );

        return coSoDto.build();
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
}
