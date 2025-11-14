package com.devmam.slmapiv2.dto.response.entities;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * DTO for {@link com.devmam.slmapiv2.entities.TronGoi}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = {"id"})
public class TronGoiDto implements Serializable {
    Integer id;
    NhomTronGoiDto nhomTronGoi;
    CoSoDto coSo;
    @Size(max = 400)
    String ten;
    TepTinDto tepTin;
    @Size(max = 50)
    String loaiHeThong;
    @Size(max = 50)
    String loaiPha;
    Double sanLuongToiThieu;
    Double sanLuongToiDa;
    String moTa;
    Instant taoLuc;
    Double tongGia;
    Boolean banChay;
    Integer trangThai;
    Set<VatTuTronGoiDto> vatTuTronGois;
}