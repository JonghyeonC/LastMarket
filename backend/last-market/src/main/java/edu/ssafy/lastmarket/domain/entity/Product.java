package edu.ssafy.lastmarket.domain.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dongCode")
    private Location location;
    private DealState dealState;
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
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

