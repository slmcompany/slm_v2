package com.devmam.slmapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "nguoi_dung", uniqueConstraints = {
        @UniqueConstraint(name = "nguoi_dung_email_key", columnNames = {"email"}),
        @UniqueConstraint(name = "nguoi_dung_sdt_key", columnNames = {"sdt"})
})
public class NguoiDung implements Serializable {
    private static final long serialVersionUID = -2944132150935765947L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_so_id")
    private CoSo coSo;

    @Size(max = 20)
    @NotNull
    @Column(name = "phan_quyen", nullable = false, length = 20)
    private String phanQuyen;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 20)
    @NotNull
    @Column(name = "sdt", nullable = false, length = 20)
    private String sdt;

    @Size(max = 50)
    @Column(name = "mat_khau", length = 50)
    private String matKhau;

    @Size(max = 100)
    @Column(name = "ho_va_ten", length = 100)
    private String hoVaTen;

    @ColumnDefault("true")
    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "sinh_nhat")
    private Instant sinhNhat;

    @Column(name = "phan_tram_hoa_hong")
    private Double phanTramHoaHong;

    @Column(name = "tong_hoa_hong")
    private Double tongHoaHong;

    @Size(max = 500)
    @Column(name = "dia_chi", length = 500)
    private String diaChi;

    @Size(max = 20)
    @Column(name = "ngan_hang", length = 20)
    private String nganHang;

    @Size(max = 50)
    @Column(name = "ma_ngan_hang", length = 50)
    private String maNganHang;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "nguoiGioiThieu", fetch = FetchType.LAZY)
    private List<KhachHang> khachHangs;

}