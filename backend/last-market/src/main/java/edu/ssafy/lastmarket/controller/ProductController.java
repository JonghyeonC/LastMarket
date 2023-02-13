package edu.ssafy.lastmarket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.dto.ProductReadDto;
import edu.ssafy.lastmarket.domain.entity.*;
import edu.ssafy.lastmarket.repository.CategoryRepository;
import edu.ssafy.lastmarket.service.*;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ProductController {

    private final ImageUploadService imageUploadService;
    private final ProductImageService productImageService;
    private final FavoriteService favoriteService;
    private final ProductService productService;
    private final LocationService locationService;
    private final CategoryService categoryService;
    private final EnumChechService enumChechService;

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getOneById(@Login Member member, @PathVariable("id") Long id) {
        Boolean isFavoriteChecked = false;
        if (!Objects.isNull(member)) {

            isFavoriteChecked = favoriteService.isFavoriteChecked(member, id);
        }
        ProductReadDto productReadDto = productService.getDtoById(id, isFavoriteChecked);

        return new ResponseEntity<>(productReadDto, HttpStatus.OK);
    }

    //TODO : 찜 여부 담아오기
    @GetMapping("/product")
    public ResponseEntity<?> getProductList(@Login Member member,
                                            @RequestParam(name = "location", required = false) String locationString,
                                            @RequestParam(name = "category", required = false) String categoryNameString,
                                            @RequestParam(name = "dealState", required = false) List<DealState> dealStates,
                                            @RequestParam(name = "lifestyle", required = false) String lifestyleString,
                                            @RequestParam(name = "keyword", required = false) String keyword,
                                            Pageable pageable) {
        Optional<Category> categoryOptional = categoryService.findByCategoryNameString(categoryNameString);
        Optional<Location> locationOptional = locationService.findDongCodeByAddress(locationString);
        Lifestyle lifestyle = enumChechService.checkLifestyleEnum(lifestyleString);

        Page<ProductListDto> products = productService.getProducts(locationOptional, categoryOptional,
                dealStates, lifestyle, keyword, pageable);


        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> saveProduct(@Login Member member,
                                         @RequestPart("product") String productDtoString,
                                         @RequestPart(name = "imgs", required = false) MultipartFile[] multipartFiles) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ProductDto productDto = objectMapper.readValue(productDtoString, ProductDto.class);
        Product product = productService.save(productDto, member);

        if (multipartFiles != null) {
            List<Image> upload = imageUploadService.upload(multipartFiles);
            List<ProductImage> productImageList = productImageService.save(product, upload);
            product.setImages(productImageList);
        }


        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<?> modifyProduct(@Login Member member,
                                           @RequestPart("product") String productDtoString,
                                           @RequestPart(name = "imgs", required = false) MultipartFile[] multipartFiles,
                                           @PathVariable("id") Long id) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ProductDto productDto = objectMapper.readValue(productDtoString, ProductDto.class);
        Optional<Category> categoryOptional = categoryService.findByCategoryName(productDto.getCategory());
        Product product = productService.updateProduct(member, id, productDto, categoryOptional);

        if (multipartFiles != null) {
            if (product.getImages() != null) {
                List<Image> imageList = productImageService.delete(product.getImages());
                imageUploadService.delete(imageList);
                product.setImages(new ArrayList<>());
            }

            List<Image> upload = imageUploadService.upload(multipartFiles);
            List<ProductImage> productImageList = productImageService.save(product, upload);
            product.setImages(productImageList);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@Login Member member, @PathVariable("id") Long id) {

        List<ProductImage> productImages = productImageService.getProductImageByProductId(id);
        List<Image> images = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            images.add(productImage.getImage());
        }
        productImageService.delete(productImages);
        imageUploadService.delete(images);
        productService.delete(member, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/pullup/{productid}")
    public ResponseEntity<?> pullupProduct(@Login Member member, @PathVariable("productid") Long productId) {
        productService.pullup(member, productId);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/product/{productId}/broadcast")
    public ResponseEntity<?> broadcast(@Login Member login, @PathVariable Long productId) {
        productService.broadcast(login, productId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/product/{productId}/finish")
//    public ResponseEntity<?> finishBroadcast(@Login Member login, @PathVariable Long productId) {
//        productService.finishBroadcast(login, productId);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
}
