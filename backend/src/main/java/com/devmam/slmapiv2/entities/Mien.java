package com.devmam.slmapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mien", uniqueConstraints = {@UniqueConstraint(name = "mien_ten_mien_key",
        columnNames = {"ten_mien"})})
public class Mien implements Serializable {
    private static final long serialVersionUID = -4281865398098383702L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "ten_mien", nullable = false, length = 100)
    private String tenMien;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "co_so_id")
    private CoSo coSo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tep_tin_id")
    private TepTin tepTin;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;

    // danh sách thông tin tên miền
    @OneToMany(mappedBy = "mien", fetch = FetchType.LAZY)
    private List<ThongTinTenMien> thongTinTenMiens;

}