package edu.ssafy.lastmarket.domain.entity;

import javax.persistence.*;

@Entity
public class BuyerReview extends BaseEntity{
    @Id
    @Column(name = "send_review_id")
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
