package com.devmam.slmapiv2.mapper;

import com.devmam.slmapiv2.dto.response.entities.TepTinDto;
import com.devmam.slmapiv2.entities.TepTin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TepTinMapper {
    default TepTinDto toDto(TepTin tepTin){
        if(tepTin == null){
            return null;
        }
        String duongDan = tepTin.getDuongDan();
        // nếu đường dẫn bắt đầu bằng http://171.244.142.43 thay bằng https://v2.slmglobal.vn
        if(duongDan != null && duongDan.startsWith("http://171.244.142.43")){
            duongDan = "https://minio.slmglobal.vn" + duongDan.substring(21);
        }
        return TepTinDto.builder()
                .id(tepTin.getId())
                .tenTepGoc(tepTin.getTenTepGoc())
                .tenTaiLen(tepTin.getTenTaiLen())
                .tenLuuTru(tepTin.getTenLuuTru())
                .duongDan(duongDan)
                .loaiTepTin(tepTin.getLoaiTepTin())
                .duoiTep(tepTin.getDuoiTep())
                .kichCo(tepTin.getKichCo())
                .moTa(tepTin.getMoTa())
                .taoLuc(tepTin.getTaoLuc())
                .suaLuc(tepTin.getSuaLuc())
                .trangThai(tepTin.getTrangThai())
                .build();
    }
}
