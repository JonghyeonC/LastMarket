package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.dto.ProductReadDto;
import edu.ssafy.lastmarket.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     *
     * @param locationOptional
     * @param categoryOptional
     * @param dealState
     * @param pageabl
     * @return
     */
    List<ProductListDto> getProducts(Optional<Location> locationOptional, Optional<Category> categoryOptional, DealState dealState, Pageable pageabl);
    Optional<Product> read(Long id);
    ProductReadDto getDtoById(Long id, boolean isFavoriteChecked);
    Product save(ProductDto productDto, Member member);
    Product saveImgs(Product product, List<Image> image, Member member);

    void delete(Member member, Long id);

}
