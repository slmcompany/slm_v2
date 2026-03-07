package com.devmam.slmapiv2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "thong_tin_ten_mien")
public class ThongTinTenMien implements Serializable {
    private static final long serialVersionUID = -539332074520500842L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mien_id")
    private Mien mien;

    @Size(max = 20)
    @Column(name = "sdt", length = 20)
    private String sdt;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "tao_luc")
    private Instant taoLuc;

    @ColumnDefault("0")
    @Column(name = "trang_thai")
    private Integer trangThai;


}