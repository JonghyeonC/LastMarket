package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Favorite;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService{

    private final FavoriteRepository favoriteRepository;

    @Override
    public boolean isFavoriteChecked(Member member, Long productId) {
        Optional<Favorite> favoriteOptional = favoriteRepository.findByMemberAndProductId(member,productId);
        return favoriteOptional.isEmpty()? false: true;

//        return favoriteRepository.existsByMemberAndProductId(member, productId);
    }
}
