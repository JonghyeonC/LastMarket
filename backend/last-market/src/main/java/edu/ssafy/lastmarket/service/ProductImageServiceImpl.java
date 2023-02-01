package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.ProductImage;
import edu.ssafy.lastmarket.repository.ProductImageRepository;
import edu.ssafy.lastmarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService{

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
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

    @Override
    public List<ProductImage> getProductImageByProductId(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product.isProductNull(productOptional);
        return productOptional.get().getImages();
    }

    @Override
    @Transactional
    public void delete(ProductImage productImage) {
        productImageRepository.delete(productImage);
    }

    @Override
    @Transactional
    public void delete(List<ProductImage> productImages) {
        productImageRepository.deleteAll(productImages);
    }
}
