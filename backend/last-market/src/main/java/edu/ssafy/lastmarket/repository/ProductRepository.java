package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select m from Product m join fetch m.location join fetch m.seller where m.id = :id")
    Optional<Product> findProductFetchJoinById(@Param("id")Long id);

}
