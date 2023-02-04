package edu.ssafy.lastmarket.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member seller;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member buyer;

    public Trade(Product product, Member seller, Member buyer){
        this.product = product;
        this.seller = seller;
        this.buyer = buyer;
    }

}
