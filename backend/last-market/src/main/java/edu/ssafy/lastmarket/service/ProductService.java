package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ProductService {

    Page<ProductListDto> getProducts(Pageable pageable);
    Product save(ProductDto productDto, Member member);
    Optional<Product> saveImgs(Product product, MultipartFile[] multipartFiles, Member member);
    void delete(Product product, Member member);

}
