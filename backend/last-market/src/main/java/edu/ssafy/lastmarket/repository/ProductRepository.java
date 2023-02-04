package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.DealState;
import edu.ssafy.lastmarket.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, ProductRepositoryCustom {
    @Query("select m from Product m join fetch m.location join fetch m.seller where m.id = :id")
    Optional<Product> findProductFetchJoinById(@Param("id")Long id);

    @Query("select p from Product p join fetch p.seller where p.id = :id")
    Optional<Product> findProductMemberById(@Param("id")Long id);

    List<Product> findByLiveTimeBetweenAndDealState(LocalDateTime start, LocalDateTime end, DealState dealState);



}
