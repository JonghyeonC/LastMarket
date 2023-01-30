package edu.ssafy.lastmarket.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import edu.ssafy.lastmarket.domain.entity.*;
import edu.ssafy.lastmarket.service.ProductService;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductReadDto {


    private Long productId;
    private String title;
    private String content;
    private Long sellerId;
    private String sellerNickname;
    private DealState dealState;
    private LocalDateTime createdDateTime;
    private LocalDateTime liveTime;
    private List<String> imgURIs = new ArrayList<>();
    private Integer favoriteCnt;

    private boolean isFavorite;
    private String location;
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private CategoryName category;

    private Lifestyle lifestyle;

    private Long startingPrice;
    private Long instantPrice;


    public ProductReadDto(Product product, boolean isFavorite){

        this.productId = product.getId();
        this.title =product.getTitle();
        this.content= product.getContent();
        this.sellerId = product.getSeller().getId();
        this.sellerNickname = product.getSeller().getNickname();
        this.createdDateTime = product.getCreatedDateTime();
        this.imgURIs = product.getImgUrls();
        this.isFavorite = isFavorite;
        this.location = product.getLocation().toString();
        this.dealState = product.getDealState();
        this.liveTime = product.getLiveTime();
        this.category = product.getCategory().getCategoryName();
        this.lifestyle = product.getLifestyle();
        this.favoriteCnt = product.getFavoriteCnt();
        this.startingPrice = product.getStartingPrice();
        this.instantPrice = product.getInstantPrice();
    }
}
