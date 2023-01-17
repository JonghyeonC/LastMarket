package edu.ssafy.lastmarket.domain.eneity;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    //카테고리명
    @Enumerated(EnumType.STRING)
    private CategoryName categoryName;
}
