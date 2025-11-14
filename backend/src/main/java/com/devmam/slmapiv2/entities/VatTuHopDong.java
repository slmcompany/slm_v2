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
@Table(name = "vat_tu_hop_dong")
public class VatTuHopDong implements Serializable {
    private static final long serialVersionUID = 4758901965419950087L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hop_dong_id")
    private HopDong hopDong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vat_tu_id")
    private VatTu vatTu;

    @Size(max = 500)
    @Column(name = "mo_ta", length = 500)
    private String moTa;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gm")
    private Double gm;

    @Column(name = "gia_he_thong")
    private Double giaHeThong;

    @Column(name = "gia_hien_thi")
    private Double giaHienThi;

    @Column(name = "thoi_gian_bao_hanh")
    private Integer thoiGianBaoHanh;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "bao_hanh_bat_dau")
    private Instant baoHanhBatDau;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "bao_hanh_ket_thuc")
    private Instant baoHanhKetThuc;

    @ColumnDefault("true")
    @Column(name = "duoc_bao_hanh")
    private Boolean duocBaoHanh;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

}