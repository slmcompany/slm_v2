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
@Table(name = "khach_hang")
public class KhachHang implements Serializable {
    private static final long serialVersionUID = 8558374921483694864L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 20)
    @NotNull
    @Column(name = "sdt", nullable = false, length = 20)
    private String sdt;

    @Size(max = 100)
    @Column(name = "ho_va_ten", length = 100)
    private String hoVaTen;

    @ColumnDefault("true")
    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "sinh_nhat")
    private Instant sinhNhat;

    @Size(max = 500)
    @Column(name = "dia_chi", length = 500)
    private String diaChi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_gioi_thieu")
    private NguoiDung nguoiGioiThieu;

    @ColumnDefault("false")
    @Column(name = "da_ban_duoc_hang")
    private Boolean daBanDuocHang;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

}