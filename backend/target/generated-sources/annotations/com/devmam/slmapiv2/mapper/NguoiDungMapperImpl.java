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
    date = "2026-02-25T15:17:10+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.45.0.v20260128-0750, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class NguoiDungMapperImpl implements NguoiDungMapper {

    @Override
    public NguoiDungDto toDto(NguoiDung nguoiDung) {
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
