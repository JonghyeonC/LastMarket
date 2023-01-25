package edu.ssafy.lastmarket.domain.entity;

import javax.persistence.*;

@Entity
public class ProductImage extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "product_image_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToOne(fetch = FetchType.LAZY)
    private Image image;
}
