package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.entity.Category;
import edu.ssafy.lastmarket.domain.entity.Product;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {
    private String title;
    private String content;
    private Category category;
    private LocalDateTime liveTime;
    private Long startingPrice;
    private Long instantPrice;

     public static Product convert(ProductDto productDto){
         return Product.builder()
                 .title(productDto.title)
                 .content(productDto.content)
                 .category(productDto.category)
                 .liveTime(productDto.liveTime)
                 .startingPrice(productDto.startingPrice)
                 .instantPrice(productDto.instantPrice)
                 .build();
     }

}
