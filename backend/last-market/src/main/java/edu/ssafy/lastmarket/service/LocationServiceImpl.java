package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Location;
import edu.ssafy.lastmarket.repository.LocationRepository;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public Optional<Location> findDongCodeByAddress(String address) {
        String[] addressArgs = address.split(" ");

        if(StringUtil.isNullOrEmpty(address)){
            return Optional.ofNullable(null);
        }
        if (addressArgs.length > 4 || addressArgs.length<3) {
            throw new IllegalArgumentException("지역명 오류");
        }

        if(addressArgs.length==4){
            return locationRepository
                    .findBySidoAndGugunAndDong(addressArgs[0], addressArgs[1], addressArgs[2]+" "+addressArgs[3]);
        }

        return locationRepository
                .findBySidoAndGugunAndDong(addressArgs[0], addressArgs[1], addressArgs[2]);

    }
}
