package com.edu.hutech.major.dto;

import lombok.Data;


@Data
public class ProductDTO {
    private Long id;

    private String name;

    private int categoryId;

    private double price;

    private double weight;

    private String description;

    private String imageName;

    private String imageName2;
    private String imageName3;
    private String imageName4;
}
