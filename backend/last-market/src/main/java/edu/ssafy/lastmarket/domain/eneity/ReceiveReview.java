package edu.ssafy.lastmarket.domain.eneity;

import javax.persistence.*;

@Entity
public class ReceiveReview extends BaseEntity{
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
    @OneToOne(fetch = FetchType.LAZY)
    private Trade trade;
}
