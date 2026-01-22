package com.stepbase.product.metadata;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "colors")
@Data
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String hexCode;
    private boolean isDeleted;
}