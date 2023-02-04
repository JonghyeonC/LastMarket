package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.dto.ProductReadDto;
import edu.ssafy.lastmarket.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     *
     * @param locationOptional
     * @param categoryOptional
     * @param dealStateList
     * @param lifestyleOptional
     * @param keyword
     * @param pageabl
     * @return
     */
    Page<ProductListDto> getProducts(Optional<Location> locationOptional,
                                     Optional<Category> categoryOptional,
                                     List<DealState> dealStateList,
                                     Lifestyle lifestyleOptional,
                                     String keyword,
                                     Pageable pageabl);
    Optional<Product> read(Long id);

    /**
     *
     * @param id
     * @return
     */
    Optional<Product> findProductMemberById(Long id);

    /**
     *
     * @param id
     * @param isFavoriteChecked
     * @return
     */
    ProductReadDto getDtoById(Long id, boolean isFavoriteChecked);

    Product save(ProductDto productDto, Member member);
    Product saveImgs(Product product, List<Image> image, Member member);

    /**
     *
     * @param member
     * @param productId
     * @param productDto
     * @param categoryOptional
     * @return
     */
    Product updateProduct(Member member, Long productId, ProductDto productDto, Optional<Category> categoryOptional);

    Product sellProduct(Product product);

    void delete(Member member, Long id);

    List<Product> findProductByLivetime(LocalDateTime localDateTime);

    void changeDealstateToAfterbroadcast(List<Product> productList);

}
