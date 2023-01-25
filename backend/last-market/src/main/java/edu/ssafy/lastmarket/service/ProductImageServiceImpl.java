package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.ProductImage;
import edu.ssafy.lastmarket.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService{

    private final ProductImageRepository productImageRepository;

    @Override
    @Transactional
    public List<ProductImage> save(Product product, List<Image> images) {

        List<ProductImage> productImageList = new ArrayList<>();

        for (Image image : images) {
            ProductImage productImage = new ProductImage(product, image);
            productImageList.add(productImageRepository.save(productImage));
        }

        return productImageList;
    }
}
