package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.ProductImage;
import edu.ssafy.lastmarket.repository.ProductImageRepository;
import edu.ssafy.lastmarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdoctServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Page<ProductListDto> getProducts(Pageable pageable) {
        return null;
    }

    @Override
    public Product save(ProductDto productDto, Member member) {
        Product product = ProductDto.convert(productDto);
        product.setSeller(member);
        product.setLocation(member.getLocation());

        return productRepository.save(product);

    }

    @Override
    public Product saveImgs(Product product, List<Image> images, Member member) {
        List<ProductImage> productImages = product.getImages();
        for (Image image : images) {
            ProductImage productImage = new ProductImage(product, image);
            ProductImage save = productImageRepository.save(productImage);
            productImages.add(save);

        }


        return product;
    }

    @Override
    public void delete(Product product, Member member) {
        productRepository.delete(product);
    }
}
