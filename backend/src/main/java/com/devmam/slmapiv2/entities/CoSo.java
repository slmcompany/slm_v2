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
@Table(name = "co_so")
public class CoSo implements Serializable {
    private static final long serialVersionUID = -6063366457844942742L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "ma", length = 50)
    private String ma;

    @Size(max = 200)
    @Column(name = "ten", length = 200)
    private String ten;

    @Size(max = 500)
    @Column(name = "dc_van_phong", length = 500)
    private String dcVanPhong;

    @Size(max = 500)
    @Column(name = "dc_kho", length = 500)
    private String dcKho;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

}