package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.eneity.Location;
import edu.ssafy.lastmarket.repository.LocationRepository;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {

    @Mock
    LocationRepository locationRepository;

    LocationServiceImpl locationService;


    @Test
    @DisplayName("지역명으로 동코드 찾기")
    public void locationExist() {
        mocking();
        //given
        String address = "서울시 용산구 도원동";
        //when
        Optional<Location> locationOptional = locationService.findDongCodeByAddress(address);
        //then
        assertThat(locationOptional.get().getDongCode()).isEqualTo("1117012000");
    }

    @Test
    @DisplayName("지역명이 4개로 나눠짐")
    public void locationSplit4() {
        locationService = new LocationServiceImpl(locationRepository);
        //given
        String address = "서울시 용산구 무슨동 3가";
        //when
        assertThatThrownBy(() -> locationService.findDongCodeByAddress(address))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public void mocking() {
        Location location = Location.builder()
                .sido("서울시")
                .gugun("용산구")
                .dong("도원동")
                .dongCode("1117012000")
                .build();
        doReturn(Optional.of(location)).when(locationRepository).findBySidoAndGugunAndDong("서울시", "용산구", "도원동");
        locationService = new LocationServiceImpl(locationRepository);
    }
}