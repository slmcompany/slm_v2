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
@Table(name = "quang_cao")
public class QuangCao implements Serializable {
    private static final long serialVersionUID = -2843249799075114693L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nghanh_hang_id")
    private NganhHang nghanhHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tep_tin_id")
    private TepTin tepTin;

    @Size(max = 200)
    @Column(name = "tieu_de", length = 200)
    private String tieuDe;

    @Size(max = 100)
    @Column(name = "vi_tri", length = 100)
    private String viTri;

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