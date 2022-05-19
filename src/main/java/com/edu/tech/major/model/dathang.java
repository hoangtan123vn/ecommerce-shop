package com.edu.tech.major.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class dathang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long madathang;

    private String hoten;

    private String diachi;

    private String sodienthoai;

    private String thongtinthem;

    private double tongtien;


}
