package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.ProductImage;
import edu.ssafy.lastmarket.service.ImageUploadService;
import edu.ssafy.lastmarket.service.ProductImageService;
import edu.ssafy.lastmarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ImageUploadService imageUploadService;
    private final ProductImageService productImageService;


    @PostMapping("/product")
    public ResponseEntity<?> saveProduct(@Login Member member, ProductDto productDto, MultipartFile[] multipartFiles) throws IOException {

        Product product = productService.save(productDto, member);
        List<Image> upload =  imageUploadService.upload(multipartFiles);
        List<ProductImage> productImageList = productImageService.save(product, upload);
        product.setImages(productImageList);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}