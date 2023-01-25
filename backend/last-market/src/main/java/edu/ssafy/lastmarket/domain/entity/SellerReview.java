package edu.ssafy.lastmarket.domain.entity;

import javax.persistence.*;

@Entity
public class SellerReview extends BaseEntity{
    @Id
    @Column(name = "receive_review_id")
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;
    @Enumerated(EnumType.STRING)
    private ReviewTemplate reviewTemplate;
    @OneToOne(fetch = FetchType.LAZY)
    private Trade trade;
}
