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
@Table(name = "nganh_hang")
public class NganhHang implements Serializable {
    private static final long serialVersionUID = 1738019422333840757L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "ma", length = 50)
    private String ma;

    @Size(max = 100)
    @Column(name = "ten", length = 100)
    private String ten;

    @Size(max = 20)
    @Column(name = "sdt_sale", length = 20)
    private String sdtSale;

    @Size(max = 20)
    @Column(name = "sdt_tech", length = 20)
    private String sdtTech;

    @Size(max = 1000)
    @Column(name = "anh_ngang", length = 1000)
    private String anhNgang;

    @Size(max = 1000)
    @Column(name = "anh_vuong", length = 1000)
    private String anhVuong;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

}