package com.stepbase.product.metadata;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "colors")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String hexCode;
    private boolean isDeleted;
}