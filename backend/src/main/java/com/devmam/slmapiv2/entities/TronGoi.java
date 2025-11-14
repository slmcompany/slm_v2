package com.devmam.slmapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tron_goi")
public class TronGoi implements Serializable {
    private static final long serialVersionUID = -6162546317248557160L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_so_id")
    private CoSo coSo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhom_tron_goi_id")
    private NhomTronGoi nhomTronGoi;

    @Size(max = 400)
    @Column(name = "ten", length = 400)
    private String ten;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tep_tin_id")
    private TepTin tepTin;

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

    @Column(name = "mo_ta", length = Integer.MAX_VALUE)
    private String moTa;

    @Column(name = "tong_gia")
    private Double tongGia;

    @ColumnDefault("true")
    @Column(name = "ban_chay")
    private Boolean banChay;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "tronGoi", fetch = FetchType.LAZY)
    List<VatTuTronGoi> vatTuTronGois;

}