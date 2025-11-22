package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.BaiVietDto;
import com.devmam.slmapiv2.dto.response.entities.TepTinDto;
import com.devmam.slmapiv2.entities.BaiViet;
import com.devmam.slmapiv2.entities.TepTin;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-22T15:17:33+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class BaiVietMapperImpl implements BaiVietMapper {

    @Override
    public BaiVietDto toDto(BaiViet baiViet) {
        if ( baiViet == null ) {
            return null;
        }

        BaiVietDto.BaiVietDtoBuilder baiVietDto = BaiVietDto.builder();

        baiVietDto.id( baiViet.getId() );
        baiVietDto.loaiBaiViet( baiViet.getLoaiBaiViet() );
        baiVietDto.tieuDe( baiViet.getTieuDe() );
        baiVietDto.anhBia( tepTinToTepTinDto( baiViet.getAnhBia() ) );
        baiVietDto.noiDung( tepTinToTepTinDto( baiViet.getNoiDung() ) );
        baiVietDto.taoBoi( baiViet.getTaoBoi() );
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
