package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ProductDto;
import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdoctServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    @Override
    public Page<ProductListDto> getProducts(Pageable pageable) {
        return null;
    }

    @Override
    public Product save(ProductDto productDto, Member member) {
        Product product = ProductDto.convert(productDto);
        product.setSeller(member);

        return productRepository.save(product);

    }

    @Override
    public Optional<Product> saveImgs(Product product, MultipartFile[] multipartFiles, Member member) {
        return Optional.empty();
    }

    @Override
    public void delete(Product product, Member member) {
        productRepository.delete(product);
    }
}
