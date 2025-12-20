package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.CoSoDto;
import com.devmam.slmapiv2.dto.response.entities.KhachHangDto;
import com.devmam.slmapiv2.dto.response.entities.NguoiDungDto;
import com.devmam.slmapiv2.entities.CoSo;
import com.devmam.slmapiv2.entities.KhachHang;
import com.devmam.slmapiv2.entities.NguoiDung;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-20T19:13:58+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class NguoiDungMapperImpl implements NguoiDungMapper {

    @Override
    public NguoiDungDto toDto(NguoiDung nguoiDung) {
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

    @Override
    public List<NguoiDungDto> toDtoList(List<NguoiDung> nguoiDungList) {
        if ( nguoiDungList == null ) {
            return null;
        }

        List<NguoiDungDto> list = new ArrayList<NguoiDungDto>( nguoiDungList.size() );
        for ( NguoiDung nguoiDung : nguoiDungList ) {
            list.add( toDto( nguoiDung ) );
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
}
