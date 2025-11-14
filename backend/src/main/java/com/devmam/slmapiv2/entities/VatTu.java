package com.devmam.slmapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vat_tu")
public class VatTu implements Serializable {
    private static final long serialVersionUID = 1869059146306112201L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhom_vat_tu_id")
    private NhomVatTu nhomVatTu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thuong_hieu_id")
    private ThuongHieu thuongHieu;

    @Size(max = 400)
    @Column(name = "ten", length = 400)
    private String ten;

    @Size(max = 1000)
    @Column(name = "sheet_link", length = 1000)
    private String sheetLink;

    @Size(max = 20)
    @Column(name = "don_vi", length = 20)
    private String donVi;

    @Column(name = "mo_ta_bao_gia", length = Integer.MAX_VALUE)
    private String moTaBaoGia;

    @Column(name = "mo_ta_hop_dong", length = Integer.MAX_VALUE)
    private String moTaHopDong;

    @Column(name = "du_lieu_rieng")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> duLieuRieng;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "vatTu", fetch = FetchType.LAZY)
    private List<AnhVatTu> anhVatTus;

    @OneToMany(mappedBy = "vatTu", fetch = FetchType.LAZY)
    private List<ThongTinGia> thongTinGias;

}