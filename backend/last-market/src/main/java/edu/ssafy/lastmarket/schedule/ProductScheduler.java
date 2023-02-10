package edu.ssafy.lastmarket.schedule;

import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductScheduler {

    private final ProductService productService;

    @Scheduled(cron = "0 0/10 * * * *")
    public void productCheck() {
        log.info("[scheduler]time={}", LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        List<Product> productByLivetime = productService.findProductByLivetime(LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusMinutes(20));
        for (Product product : productByLivetime) {
            log.info("[scheduler]productId={}", product.getId());
        }
        productService.changeDealstateToAfterbroadcast(productByLivetime);
    }
}
