package edu.ssafy.lastmarket.domain.eneity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    //카테고리명
    @Enumerated(EnumType.STRING)
    private CategoryName categoryName;


}
