package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.CoSoDto;
import com.devmam.slmapiv2.dto.response.entities.MienDto;
import com.devmam.slmapiv2.dto.response.entities.ThongTinTenMienDto;
import com.devmam.slmapiv2.entities.CoSo;
import com.devmam.slmapiv2.entities.Mien;
import com.devmam.slmapiv2.entities.ThongTinTenMien;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-05T15:17:11+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class MienMapperImpl implements MienMapper {

    @Autowired
    private TepTinMapper tepTinMapper;

    @Override
    public MienDto toDto(Mien mien) {
        if ( mien == null ) {
            return null;
        }

        MienDto.MienDtoBuilder mienDto = MienDto.builder();

        mienDto.id( mien.getId() );
        mienDto.tenMien( mien.getTenMien() );
        mienDto.coSo( coSoToCoSoDto( mien.getCoSo() ) );
        mienDto.thoiGianThayDoiHotline( mien.getThoiGianThayDoiHotline() );
        mienDto.tepTin( tepTinMapper.toDto( mien.getTepTin() ) );
        mienDto.taoLuc( mien.getTaoLuc() );
        mienDto.trangThai( mien.getTrangThai() );
        mienDto.thongTinTenMiens( thongTinTenMienListToThongTinTenMienDtoList( mien.getThongTinTenMiens() ) );

        return mienDto.build();
    }

    @Override
    public List<MienDto> toDtoList(List<Mien> mienList) {
        if ( mienList == null ) {
            return null;
        }

        List<MienDto> list = new ArrayList<MienDto>( mienList.size() );
        for ( Mien mien : mienList ) {
            list.add( toDto( mien ) );
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

    protected ThongTinTenMienDto thongTinTenMienToThongTinTenMienDto(ThongTinTenMien thongTinTenMien) {
        if ( thongTinTenMien == null ) {
            return null;
        }

        ThongTinTenMienDto.ThongTinTenMienDtoBuilder thongTinTenMienDto = ThongTinTenMienDto.builder();

        thongTinTenMienDto.id( thongTinTenMien.getId() );
        thongTinTenMienDto.sdt( thongTinTenMien.getSdt() );
        thongTinTenMienDto.email( thongTinTenMien.getEmail() );
        thongTinTenMienDto.taoLuc( thongTinTenMien.getTaoLuc() );
        thongTinTenMienDto.trangThai( thongTinTenMien.getTrangThai() );

        return thongTinTenMienDto.build();
    }

    protected List<ThongTinTenMienDto> thongTinTenMienListToThongTinTenMienDtoList(List<ThongTinTenMien> list) {
        if ( list == null ) {
            return null;
        }

        List<ThongTinTenMienDto> list1 = new ArrayList<ThongTinTenMienDto>( list.size() );
        for ( ThongTinTenMien thongTinTenMien : list ) {
            list1.add( thongTinTenMienToThongTinTenMienDto( thongTinTenMien ) );
        }

        return list1;
    }
}
