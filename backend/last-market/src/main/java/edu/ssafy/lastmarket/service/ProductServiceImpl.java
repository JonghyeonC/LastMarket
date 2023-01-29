package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.dto.ProductReadDto;
import edu.ssafy.lastmarket.domain.entity.*;
import edu.ssafy.lastmarket.exception.NotYourAuthority;
import edu.ssafy.lastmarket.repository.CategoryRepository;
import edu.ssafy.lastmarket.repository.ProductImageRepository;
import edu.ssafy.lastmarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;



    @Override
    public Page<ProductListDto> getProducts(Optional<Location> locationOptional, Optional<Category> categoryOptional, DealState dealState, Pageable pageabl) {


        Page<Product> products = productRepository.getProductList(locationOptional, categoryOptional,dealState,pageabl);
        PageImpl<ProductListDto> result= new PageImpl<>(
                products.getContent().stream()
                        .map(product -> new ProductListDto(product,false))
                        .collect(Collectors.toList())
                ,pageabl,products.getTotalPages());

        return result;
    }

    @Override
    public Optional<Product> read(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductReadDto getDtoById( Long id, boolean isFavoriteCehcked) {
        Optional<Product> productOptional = productRepository.findProductFetchJoinById(id);
        Product.isProductNull(productOptional);
        Product product = productOptional.get();

//        System.out.println(product.getTitle());
//        System.out.println(product.getLocation());
        ProductReadDto productReadDto = new ProductReadDto(product, isFavoriteCehcked);

        return productReadDto;
    }

    @Override
    @Transactional
    public Product save(ProductDto productDto, Member member) {
         Optional<Category> categoryOptional = categoryRepository.findByCategoryName(productDto.getCategory());

         if(categoryOptional.isEmpty()){
             Category category = new Category(productDto.getCategory());
             categoryRepository.save(category);
             categoryOptional = Optional.of(category);
         }


        Product product = ProductDto.convert(productDto);
        product.setSeller(member);
        product.setLocation(member.getLocation());
        product.setCategory(categoryOptional.get());
        product.setDealState(DealState.DEFAULT);
        product.setFavoriteCnt(0);


        return productRepository.save(product);

    }

    @Override
    @Transactional
    public Product saveImgs(Product product, List<Image> images, Member member) {

        checkSeller(product, member);

        List<ProductImage> productImages = product.getImages();
        for (Image image : images) {
            ProductImage productImage = new ProductImage(product, image);
            ProductImage save = productImageRepository.save(productImage);
            productImages.add(save);

        }

        return product;
    }



    @Override
    @Transactional
    public void delete(Member member, Long id) {

        Optional<Product> productOptional = productRepository.findById(id);
        Product.isProductNull(productOptional);
        if(!productOptional.get().getSeller().equals(member)){
            throw new NotYourAuthority();
        }

        productRepository.delete(productOptional.get());

    }

    private void checkSeller(Product product, Member member) {
        if (product.getSeller().equals(member)) {
            throw new NotYourAuthority();
        }
    }




}
