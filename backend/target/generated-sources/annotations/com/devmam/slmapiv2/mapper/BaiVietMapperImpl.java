package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.BaiVietDto;
import com.devmam.slmapiv2.dto.response.entities.TepTinDto;
import com.devmam.slmapiv2.entities.BaiViet;
import com.devmam.slmapiv2.entities.NguoiDung;
import com.devmam.slmapiv2.entities.TepTin;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-19T11:09:04+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class BaiVietMapperImpl implements BaiVietMapper {

    @Override
    public BaiVietDto toDto(BaiViet baiViet) {
        if ( baiViet == null ) {
            return null;
        }

        Integer id = null;
        String loaiBaiViet = null;
        String tieuDe = null;
        TepTinDto anhBia = null;
        TepTinDto noiDung = null;
        NguoiDung taoBoi = null;
        String lienQuan = null;
        Boolean hoatDong = null;
        Instant taoLuc = null;
        Integer trangThai = null;

        id = baiViet.getId();
        loaiBaiViet = baiViet.getLoaiBaiViet();
        tieuDe = baiViet.getTieuDe();
        anhBia = tepTinToTepTinDto( baiViet.getAnhBia() );
        noiDung = tepTinToTepTinDto( baiViet.getNoiDung() );
        taoBoi = baiViet.getTaoBoi();
        lienQuan = baiViet.getLienQuan();
        hoatDong = baiViet.getHoatDong();
        taoLuc = baiViet.getTaoLuc();
        trangThai = baiViet.getTrangThai();

        BaiVietDto baiVietDto = new BaiVietDto( id, loaiBaiViet, tieuDe, anhBia, noiDung, taoBoi, lienQuan, hoatDong, taoLuc, trangThai );

        return baiVietDto;
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

    protected TepTinDto tepTinToTepTinDto(TepTin tepTin) {
        if ( tepTin == null ) {
            return null;
        }

        TepTinDto.TepTinDtoBuilder tepTinDto = TepTinDto.builder();

        tepTinDto.id( tepTin.getId() );
        tepTinDto.tenTepGoc( tepTin.getTenTepGoc() );
        tepTinDto.tenTaiLen( tepTin.getTenTaiLen() );
        tepTinDto.tenLuuTru( tepTin.getTenLuuTru() );
        tepTinDto.duongDan( tepTin.getDuongDan() );
        tepTinDto.loaiTepTin( tepTin.getLoaiTepTin() );
        tepTinDto.duoiTep( tepTin.getDuoiTep() );
        tepTinDto.kichCo( tepTin.getKichCo() );
        tepTinDto.moTa( tepTin.getMoTa() );
        tepTinDto.taoLuc( tepTin.getTaoLuc() );
        tepTinDto.suaLuc( tepTin.getSuaLuc() );
        tepTinDto.trangThai( tepTin.getTrangThai() );

        return tepTinDto.build();
    }
}
