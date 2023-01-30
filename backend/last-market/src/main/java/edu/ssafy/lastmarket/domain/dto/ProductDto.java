package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.entity.Category;
import edu.ssafy.lastmarket.domain.entity.CategoryName;
import edu.ssafy.lastmarket.domain.entity.Lifestyle;
import edu.ssafy.lastmarket.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String title;
    private String content;
    private Lifestyle lifestyle;
    private CategoryName category;
    private LocalDateTime liveTime;
    private Long startingPrice;
    private Long instantPrice;




    public static Product convert(ProductDto productDto){
         return Product.builder()
                 .title(productDto.title)
                 .content(productDto.content)
                 .liveTime(productDto.liveTime)
                 .startingPrice(productDto.startingPrice)
                 .instantPrice(productDto.instantPrice)
                 .lifestyle(productDto.lifestyle)
                 .build();
    }

}
