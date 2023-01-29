package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.ProductImage;

import java.util.List;

public interface ProductImageService {

    List<ProductImage> save(Product product, List<Image> images);
    List<ProductImage> getProductImageByProductId(Long id);
    void delete(ProductImage productImage);
    void delete(List<ProductImage> productImages);
}

