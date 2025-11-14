package com.devmam.slmapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "nhom_vat_tu", uniqueConstraints = {
        @UniqueConstraint(name = "nhom_vat_tu_ma_key", columnNames = {"ma"})
})
public class NhomVatTu implements Serializable {
    private static final long serialVersionUID = -9112334992147881083L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 200)
    @Column(name = "ma", length = 200)
    private String ma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nghanh_hang_id")
    private NganhHang nghanhHang;

    @Size(max = 400)
    @Column(name = "ten", length = 400)
    private String ten;

    @Column(name = "thuoc_tinh_rieng")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> thuocTinhRieng;

    @ColumnDefault("0")
    @Column(name = "gm")
    private Double gm;

    @ColumnDefault("false")
    @Column(name = "vat_tu_chinh")
    private Boolean vatTuChinh;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

}