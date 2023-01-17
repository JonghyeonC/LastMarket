package edu.ssafy.lastmarket.domain.eneity;

import javax.persistence.*;

@Entity
public class Ban extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id")
    private Member from;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id")
    private Member to;
}
