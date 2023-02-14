package edu.ssafy.lastmarket.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
