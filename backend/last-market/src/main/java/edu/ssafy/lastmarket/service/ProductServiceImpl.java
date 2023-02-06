package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.dto.ProductReadDto;
import edu.ssafy.lastmarket.domain.entity.*;
import edu.ssafy.lastmarket.exception.NotFoundException;
import edu.ssafy.lastmarket.exception.NotYourAuthority;
import edu.ssafy.lastmarket.repository.CategoryRepository;
import edu.ssafy.lastmarket.repository.ProductImageRepository;
import edu.ssafy.lastmarket.repository.ProductRepository;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    public Page<ProductListDto> getProducts(Optional<Location> locationOptional,
                                            Optional<Category> categoryOptional,
                                            List<DealState> dealStates,
                                            Lifestyle lifestyle,
                                            String keyword,
                                            Pageable pageabl) {


        Page<Product> products = productRepository.getProductList(locationOptional, categoryOptional,
                dealStates, lifestyle, keyword, pageabl);
        PageImpl<ProductListDto> result = new PageImpl<>(
                products.getContent().stream()
                        .map(product -> new ProductListDto(product, false))
                        .collect(Collectors.toList())
                , pageabl, products.getTotalPages());

        return result;
    }

    @Override
    public Optional<Product> read(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findProductMemberById(Long id) {
        return productRepository.findProductMemberById(id);
    }

    @Override
    public ProductReadDto getDtoById(Long id, boolean isFavoriteCehcked) {
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

        if (categoryOptional.isEmpty()) {
            Category category = new Category(productDto.getCategory());
            categoryRepository.save(category);
            categoryOptional = Optional.of(category);
        }


        Product product = ProductDto.convert(productDto);
        if(product.getLiveTime()!=null){
            LocalDateTime localDateTime = localDateTimeFloor(product.getLiveTime());
            product.setLiveTime(localDateTime);
        }

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

        checkSeller(Optional.ofNullable(product), member);

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
    public Product updateProduct(Member member, Long productId, ProductDto productDto, Optional<Category> categoryOptional) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Product.isProductNull(productOptional);
        Product product = productOptional.get();
        if (productDto.getLiveTime() != null) {
            LocalDateTime localDateTime = localDateTimeFloor(productDto.getLiveTime());
            product.setLiveTime(localDateTime);
        }
        if (!StringUtil.isNullOrEmpty(productDto.getTitle())) {
            product.setTitle(productDto.getTitle());
        }
        if (!StringUtil.isNullOrEmpty(productDto.getContent())) {
            product.setContent(productDto.getContent());
        }
        if (productDto.getLifestyle() != null) {
            product.setLifestyle(productDto.getLifestyle());
        }
        if (!categoryOptional.isEmpty()) {
            product.setCategory(categoryOptional.get());
        }

        if (productDto.getStartingPrice() != 0) {
            product.setStartingPrice(productDto.getStartingPrice());
        }
        if (productDto.getInstantPrice() != 0) {
            product.setInstantPrice(productDto.getInstantPrice());
        }

        return product;
    }

    @Override
    @Transactional
    public Product sellProduct(Product product) {
        product.setDealState(DealState.FINISH);
        return product;
    }


    @Override
    @Transactional
    public void delete(Member member, Long id) {

        Optional<Product> productOptional = productRepository.findById(id);
        Product.isProductNull(productOptional);


        productRepository.delete(productOptional.get());

    }

    @Override
    public List<Product> findProductByLivetime(LocalDateTime localDateTime) {
        return productRepository.findByLiveTimeBetweenAndDealState(
                localDateTime, localDateTime.plusMinutes(10), DealState.DEFAULT);
    }

    @Override
    @Transactional
    public void changeDealstateToAfterbroadcast(List<Product> productList) {


        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            product.setDealState(DealState.AFTERBROADCAST);
            productRepository.save(product);
        }
//        productList.forEach(product -> {
//            product.setDealState(DealState.AFTERBROADCAST);
//            System.out.println(product.getId() + " " + product.getDealState());
//        });
    }

    private void checkSeller(Optional<Product> productOptional, Member member) {
        if (productOptional.isEmpty()) {
            throw new NotFoundException("product NotFoundException");
        }
        if (productOptional.get().getSeller().equals(member)) {
            throw new NotYourAuthority();
        }
    }

    private LocalDateTime localDateTimeFloor(LocalDateTime time) {
        return LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour(), time.getMinute() / 10 * 10);
    }


}
