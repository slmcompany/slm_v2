package com.devmam.slmapiv2.dto.request.entities;

import com.devmam.slmapiv2.entities.TronGoi;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TronGoiUpdatingDto {

     Integer id;

    // Thêm nhomTronGoiId để cho phép cập nhật nhóm trọn gói
     Integer nhomTronGoiId;

     String ten;
     String loaiHeThong;
     String loaiPha;
     String moTa;

     Double congSuatHeThong;
     Double sanLuongToiThieu;
     Double sanLuongToiDa;

    // Tách tổng giá theo khu vực (giống TronGoiCreatingDto)
     Double tongGiaMienBac;
     Double tongGiaMienNam;

     Double gmTong;
     Boolean banChay;
     Integer trangThai;

    /**
     * Danh sách vật tư mới sẽ thay thế hoàn toàn danh sách cũ.
     * Dùng VatTuTronGoiCreatingDto (không cần id) để tạo lại từ đầu.
     */
     List<VatTuTronGoiCreatingDto> vatTuTronGois;
}