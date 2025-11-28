package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.BaiVietDto;
import com.devmam.slmapiv2.entities.BaiViet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-28T17:41:51+0700",
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
        baiVietDto.duongDanYoutube( baiViet.getDuongDanYoutube() );
        baiVietDto.noiDung( tepTinMapper.toDto( baiViet.getNoiDung() ) );
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
}
