package edu.ssafy.lastmarket.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String nickname;
    @OneToOne(fetch = FetchType.LAZY)
    private Image profile;
    @Enumerated(EnumType.STRING)
    private Lifestyle lifestyle;
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Role role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locationDongCode")
    private Location location;
    @OneToMany(mappedBy = "seller",fetch = FetchType.LAZY)
    private List<Product> products;
    @OneToMany(mappedBy = "from", fetch = FetchType.LAZY)
    private List<Ban> banList;
    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Favorite> favorites;
    @OneToMany(mappedBy = "seller",fetch = FetchType.LAZY)
    private List<SellerReview> sellerReviews;
    @OneToMany(mappedBy = "buyer",fetch = FetchType.LAZY)
    private List<BuyerReview> buyerReviews;

    public Member(String username){
        this.username =username;
        this.role= Role.USER;
    }


}
