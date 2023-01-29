package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.entity.CategoryName;
import edu.ssafy.lastmarket.domain.entity.DealState;
import edu.ssafy.lastmarket.domain.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class ProductListDto {

    private Long productId;
    private String title;
    private Long sellerId;
    private String sellerNickname;
    private DealState dealState;
    private LocalDateTime createdDateTime;
    private LocalDateTime liveTime;
    private String imgURI;
    private Integer favoriteCnt;
    private boolean isFavorite;
    private String location;
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private CategoryName category;
    private Long startingPrice;
    private Long instantPrice;


    public ProductListDto(Product product, boolean isFavorite){

        this.productId = product.getId();
        this.title =product.getTitle();
        this.sellerId = product.getSeller().getId();
        this.sellerNickname = product.getSeller().getNickname();
        this.createdDateTime = product.getCreatedDateTime();
        if(product.getImages().size()>0){
            this.imgURI = product.getImgUrls().get(0);
        }else{
            this.imgURI = "";
        }
        this.isFavorite = isFavorite;
        this.location = product.getLocation().toString();
        this.dealState = product.getDealState();
        this.liveTime = product.getLiveTime();
        this.category = product.getCategory().getCategoryName();
        this.favoriteCnt = product.getFavoriteCnt();
        this.startingPrice = product.getStartingPrice();
        this.instantPrice = product.getInstantPrice();
    }
}
