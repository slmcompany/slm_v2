package com.devmam.slmapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "tep_tin", uniqueConstraints = {
        @UniqueConstraint(name = "tep_tin_ten_luu_tru_key", columnNames = {"ten_luu_tru"})
})
public class TepTin implements Serializable {
    private static final long serialVersionUID = -8670717574587185615L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 1000)
    @Column(name = "ten_tep_goc", length = 1000)
    private String tenTepGoc;

    @Size(max = 1000)
    @Column(name = "ten_tai_len", length = 1000)
    private String tenTaiLen;

    @Size(max = 1000)
    @NotNull
    @Column(name = "ten_luu_tru", nullable = false, length = 1000)
    private String tenLuuTru;

    @Column(name = "duong_dan", length = Integer.MAX_VALUE)
    private String duongDan;

    @Size(max = 100)
    @Column(name = "loai_tep_tin", length = 100)
    private String loaiTepTin;

    @Size(max = 10)
    @Column(name = "duoi_tep", length = 10)
    private String duoiTep;

    @Column(name = "kich_co")
    private Integer kichCo;

    @Size(max = 400)
    @Column(name = "mo_ta", length = 400)
    private String moTa;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "sua_luc")
    private Instant suaLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

}