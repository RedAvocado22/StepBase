package com.stepbase.product.metadata;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sizes")
@Data
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sizeEu;
    private boolean isDeleted;
}