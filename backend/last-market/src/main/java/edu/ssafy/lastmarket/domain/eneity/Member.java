package edu.ssafy.lastmarket.domain.eneity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String nickname;
    @OneToOne(fetch = FetchType.LAZY)
    private Image profileImage;
    private Job job;
    @OneToOne(fetch = FetchType.LAZY)
    private Location location;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> products;
    @OneToMany(mappedBy = "from", fetch = FetchType.LAZY)
    private List<Ban> banList;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Favorite> favorites;
    @OneToMany(fetch = FetchType.LAZY)
    private List<ReceiveReview> receiveReviews;
    @OneToMany(fetch = FetchType.LAZY)
    private List<SendReview> sendReviews;
}
