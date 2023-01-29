package edu.ssafy.lastmarket.domain.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import edu.ssafy.lastmarket.exception.NotFoundException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Product extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dongCode")
    private Location location;
    @Enumerated(EnumType.STRING)
    private DealState dealState;
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private List<ProductImage> images = new ArrayList<>();
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime liveTime;
    @OneToOne(fetch = FetchType.LAZY)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;
    private Integer favoriteCnt;
    private Long startingPrice;
    private Long instantPrice;


    public static void isProductNull(Optional<Product> productOptional){
        if(productOptional.isEmpty()){
            throw new NotFoundException("notFoundException");
        }
    }

    public List<String> getImgUrls(){
        List<String> result = new ArrayList<>();

        for (ProductImage image : images) {
            result.add(image.getImage().getImageURL());
        }

        return result;
    }
}

