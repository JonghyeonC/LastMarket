package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Location;

import java.util.Optional;

public interface LocationService {
   Optional<Location> findDongCodeByAddress(String address);

}
