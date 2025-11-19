package com.devmam.slmapiv2.dto.request.entities;

import com.devmam.slmapiv2.entities.VatTu;
import com.devmam.slmapiv2.entities.commons.GiaInfo;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.text.Normalizer;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class VatTuCreatingDto {
    Integer nhomVatTuId;
    Integer thuongHieuId;
    @Size(max = 400)
    String ten;
    @Size(max = 1000)
    String sheetLink;
    @Size(max = 20)
    String donVi;
    String moTaBaoGia;
    String moTaHopDong;
    Map<String, ThuocTinh> duLieuRieng;
    Integer trangThai;

    List<GiaInfo> dsGia;

    public static VatTu toEntity(VatTuCreatingDto dto) {
        Map<String, Object> thuocTinhRiengObj = new HashMap<>(dto.getDuLieuRieng());
        // Gen mã vật tư từ trường tên phân các từ bằng dấu gạch dưới và bỏ hết dấu cũng như viết hoa chẳng hạn kẹp Biên thành kep_bien
        String ma = genMaVatTu(dto.getTen())+ "_" + new Date().getTime();
        return VatTu.builder()
                .ma(ma)
                .ten(dto.getTen())
                .sheetLink(dto.getSheetLink())
                .donVi(dto.getDonVi())
                .moTaBaoGia(dto.getMoTaBaoGia())
                .moTaHopDong(dto.getMoTaHopDong())
                .duLieuRieng(thuocTinhRiengObj)
                .taoLuc(Instant.now())
                .trangThai(dto.getTrangThai())
                .build();
    }


    public static String genMaVatTu(String tenString) {
        // Bỏ dấu tiếng Việt
        String khongDau = Normalizer.normalize(tenString, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Chuyển về chữ thường
        khongDau = khongDau.toLowerCase();

        // Thay thế khoảng trắng bằng dấu gạch dưới
        String ma = khongDau.replaceAll("\\s+", "_");

        return ma;
    }

}
