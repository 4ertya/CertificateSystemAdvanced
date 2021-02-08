package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    private BigDecimal cost;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "orders_certificates",
            joinColumns = {@JoinColumn(name = "orders_id")},
            inverseJoinColumns = {@JoinColumn(name = "certificates_id")}
    )
    @OrderBy(value = "id")
    @Fetch(FetchMode.SUBSELECT)
    private List<Certificate> certificates = new ArrayList<>();
}
