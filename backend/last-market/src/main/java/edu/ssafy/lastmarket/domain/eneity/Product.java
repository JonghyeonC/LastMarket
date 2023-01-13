package edu.ssafy.lastmarket.domain.eneity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dongCode")
    private Location location;
    private DealState dealState;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_image_id")
    private List<ProductImage> images = new ArrayList<>();
    private LocalDateTime liveTime;
    @OneToOne(fetch = FetchType.LAZY)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;
    private Integer favoriteCnt;
    private Long startingPrice;
    private Long instantPrice;
}

