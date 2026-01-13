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
@Table(name = "bai_viet")
public class BaiViet implements Serializable {
    private static final long serialVersionUID = 8156970440012086900L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 200)
    @Column(name = "loai_bai_viet", length = 200)
    private String loaiBaiViet;

    @Size(max = 500)
    @Column(name = "tieu_de", length = 500)
    private String tieuDe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anh_bia")
    private TepTin anhBia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anh_ngoai")
    private TepTin anhNgoai;

    @Size(max = 1000)
    @Column(name = "duong_dan_youtube")
    private String duongDanYoutube;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noi_dung")
    private TepTin noiDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tao_boi")
    private NguoiDung taoBoi;

    @Size(max = 500)
    @Column(name = "lien_quan", length = 500)
    private String lienQuan;

    @ColumnDefault("true")
    @Column(name = "hoat_dong")
    private Boolean hoatDong;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

}