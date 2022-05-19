package com.edu.tech.major.model;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class chitietdathang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machitietdat;

    private String tensanpham;

    private double soluong;

    private double tiensanpham;

    private String hinhanhsanpham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madathang", referencedColumnName = "madathang")
    private dathang dathang;

}
