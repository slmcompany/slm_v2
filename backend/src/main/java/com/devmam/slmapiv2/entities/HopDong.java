package com.devmam.slmapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hop_dong")
public class HopDong implements Serializable {
    private static final long serialVersionUID = 4717291732805897335L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_so_id")
    private CoSo coSo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nghanh_hang_id")
    private NganhHang nghanhHang;

    @Size(max = 400)
    @Column(name = "ten", length = 400)
    private String ten;

    @Size(max = 50)
    @Column(name = "loai_he_thong", length = 50)
    private String loaiHeThong;

    @Size(max = 50)
    @Column(name = "loai_pha", length = 50)
    private String loaiPha;

    @Column(name = "san_luong_toi_thieu")
    private Double sanLuongToiThieu;

    @Column(name = "san_luong_toi_da")
    private Double sanLuongToiDa;

    @Column(name = "gia_khung_sat")
    private Double giaKhungSat;

    @Column(name = "gia_lap_khung_sat")
    private Double giaLapKhungSat;

    @Column(name = "mo_ta", length = Integer.MAX_VALUE)
    private String moTa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_gioi_thieu")
    private NguoiDung nguoiGioiThieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;

    @Column(name = "tong_gia")
    private Double tongGia;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

}