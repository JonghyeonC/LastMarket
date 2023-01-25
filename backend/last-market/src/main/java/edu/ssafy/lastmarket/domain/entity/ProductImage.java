package edu.ssafy.lastmarket.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public ProductImage(Product product, Image image){
        this.product = product;
        this.image = image;
    }
}
