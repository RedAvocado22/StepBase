package com.stepbase.order;

import com.stepbase.user.User;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "consignee_name", nullable = false)
    private String consigneeName;

    @Column(nullable = false)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String shippingAddress;

    private String orderStatus;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "is_paid")
    private boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> items;
}
