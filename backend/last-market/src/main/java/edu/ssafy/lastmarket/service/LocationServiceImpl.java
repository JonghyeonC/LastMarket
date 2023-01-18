package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public String findDongCodeByAddress(String address) {
        String[] addressArgs = address.split(" ");

        if (addressArgs.length > 3) {
            throw new IllegalArgumentException("지역명 오류");
        }

        return locationRepository
                .findBySidoAndDongAndGugun(addressArgs[0], addressArgs[1], addressArgs[2])
                .orElseThrow(() -> new NoSuchElementException("없는 지역입니다."))
                .getDongCode();

    }
}
