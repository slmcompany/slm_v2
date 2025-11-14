package com.devmam.slmapiv2.entities;

import com.devmam.slmapiv2.entities.commons.GiaInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "thong_tin_gia")
public class ThongTinGia implements Serializable {
    private static final long serialVersionUID = 1549014730534675735L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vat_tu_id")
    private VatTu vatTu;

    @Column(name = "ds_gia", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<GiaInfo> dsGia = new ArrayList<>();

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;
}