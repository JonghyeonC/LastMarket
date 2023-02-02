package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {

    Page<Product> getProductList(Optional<Location> locationOptional,
                                 Optional<Category> categoryOptional,
                                 DealState dealStateOptional,
                                 Lifestyle lifestyleOptional,
                                 String keyword,
                                 Pageable pageable);
}
