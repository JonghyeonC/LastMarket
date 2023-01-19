package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.eneity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<Location> findBySidoAndDongAndGugun(String sido, String dong, String gugun);
}
