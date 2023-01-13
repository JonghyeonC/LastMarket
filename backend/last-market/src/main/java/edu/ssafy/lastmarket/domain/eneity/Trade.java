package edu.ssafy.lastmarket.domain.eneity;

import javax.persistence.*;

@Entity
public class Trade extends BaseEntity {
    @Id
    @Column(name = "trade_id")
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member seller;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member buyer;
}
