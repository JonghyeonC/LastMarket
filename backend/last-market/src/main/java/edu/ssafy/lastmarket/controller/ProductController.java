package edu.ssafy.lastmarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.dto.ProductReadDto;
import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.ProductImage;
import edu.ssafy.lastmarket.service.FavoriteService;
import edu.ssafy.lastmarket.service.ImageUploadService;
import edu.ssafy.lastmarket.service.ProductImageService;
import edu.ssafy.lastmarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ImageUploadService imageUploadService;
    private final ProductImageService productImageService;
    private final FavoriteService favoriteService;
    private final ProductService productService;

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getOneById(@Login Member member, @PathVariable("id") Long id) {
//        System.out.println("======================");
//        System.out.println(authentication.getPrincipal());
//        System.out.println(authentication.isAuthenticated());
        Boolean isFavoriteChecked = false;
        if (!Objects.isNull(member)) {

            isFavoriteChecked = favoriteService.isFavoriteChecked(member, id);
        }
//        System.out.println(isFavoriteChecked);

        ProductReadDto productReadDto = productService.getDtoById(id, isFavoriteChecked);


        return new ResponseEntity<>(productReadDto, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> saveProduct(@Login Member member,
                                         @RequestPart("product") String productDtoString,
                                         @RequestPart("imgs") MultipartFile[] multipartFiles) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ProductDto productDto = objectMapper.readValue(productDtoString, ProductDto.class);
        Product product = productService.save(productDto, member);
        List<Image> upload = imageUploadService.upload(multipartFiles);
        List<ProductImage> productImageList = productImageService.save(product, upload);
        product.setImages(productImageList);

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


}
