package edu.ssafy.lastmarket.domain.eneity;

import javax.persistence.*;

@Entity
public class Favorite extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
