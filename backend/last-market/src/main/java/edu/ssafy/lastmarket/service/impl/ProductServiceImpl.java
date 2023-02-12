package edu.ssafy.lastmarket.service.impl;

import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.dto.ProductReadDto;
import edu.ssafy.lastmarket.domain.entity.*;
import edu.ssafy.lastmarket.exception.NotFoundException;
import edu.ssafy.lastmarket.exception.NotMatchSellerException;
import edu.ssafy.lastmarket.exception.NotYourAuthority;
import edu.ssafy.lastmarket.exception.UpdateProductCooltimeException;
import edu.ssafy.lastmarket.repository.CategoryRepository;
import edu.ssafy.lastmarket.repository.ProductImageRepository;
import edu.ssafy.lastmarket.repository.ProductRepository;
import edu.ssafy.lastmarket.service.ProductService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        product.setDealState(DealState.AFTERBROADCAST);
        product.setSeller(member);
        product.setLocation(member.getLocation());
        product.setCategory(categoryOptional.get());
        product.setFavoriteCnt(0);
        if (product.getLiveTime() != null) {
            LocalDateTime localDateTime = localDateTimeFloor(product.getLiveTime());
            product.setLiveTime(localDateTime);
            product.setDealState(DealState.DEFAULT);
        }
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
        if (!checkUpdate30min(product)) {
            throw new UpdateProductCooltimeException("need 30 min");
        }
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

        if (productDto.getStartingPrice() != null) {
            product.setStartingPrice(productDto.getStartingPrice());
        }
        if (productDto.getInstantPrice() != null) {
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
    }

    @Override
    @Transactional
    public Boolean pullup(Member member, Long productId) {
        Optional<Product> productOptional = productRepository.findProductFetchJoinSellerById(productId);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("product NotFoundException");
        }
        Product product = productOptional.get();
        if (product.getSeller().getId() != member.getId()) {
            throw new NotYourAuthority();
        }

        boolean check = checkUpdate30min(product);
        if (!check) {
            throw new UpdateProductCooltimeException("need 30 min");
        }
        product.setLastModifiedDateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        return check;
    }

    @Override
    public void broadcast(Member member, Long productId) {
        Product find = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("없는 상품입니다."));
        if (!find.getSeller().getId().equals(member.getId())) {
            throw new NotMatchSellerException("판매자가 아닙니다.");
        }
        find.setDealState(DealState.ONBROADCAST);
        productRepository.save(find);
    }

    @Override
    public void finishBroadcast(Member member, Long productId) {
        Product find = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("없는 상품입니다."));
        if (!find.getSeller().getId().equals(member.getId())) {
            throw new NotMatchSellerException("판매자가 아닙니다.");
        }
        find.setDealState(DealState.AFTERBROADCAST);
        productRepository.save(find);
    }

    @Override
    public void successBid(Product product, String price) {
        Long longPrice = null;
        try {
            longPrice = Long.parseLong(price);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("맞지 않는 가격입니다.");
        }
        product.setStartingPrice(longPrice);
        product.setDealState(DealState.FINISH);
        productRepository.save(product);
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

    private Boolean checkUpdate30min(Product product) {
        LocalDateTime nowMinus30min = LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusMinutes(30);
        return !nowMinus30min.isBefore(product.getLastModifiedDateTime());
    }
}
