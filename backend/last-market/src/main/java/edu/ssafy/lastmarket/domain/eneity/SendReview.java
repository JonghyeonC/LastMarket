package edu.ssafy.lastmarket.domain.eneity;

import javax.persistence.*;

@Entity
public class SendReview extends BaseEntity{
    @Id
    @Column(name = "send_review_id")
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
