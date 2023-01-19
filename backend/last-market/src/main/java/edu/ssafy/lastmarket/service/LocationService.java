package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.eneity.Location;

import java.util.Optional;

public interface LocationService {
   Optional<Location> findDongCodeByAddress(String address);

}
