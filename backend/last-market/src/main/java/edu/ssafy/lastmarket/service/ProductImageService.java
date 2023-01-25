package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.ProductImage;

import java.util.List;

public interface ProductImageService {

    List<ProductImage> save(Product product, List<Image> images);

}

