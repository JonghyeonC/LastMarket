package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.eneity.Ban;
import edu.ssafy.lastmarket.domain.eneity.Member;
import edu.ssafy.lastmarket.exception.BanExistException;
import edu.ssafy.lastmarket.repository.BanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BanServiceImpl implements BanService {
    private final BanRepository banRepository;

    @Override
    public Ban banUser(Member from, Member to) {
        Ban ban = Ban.builder()
                .from(from)
                .to(to)
                .build();

        Optional<Ban> found = banRepository.findByFromAndTo(from, to);
        if (found.isPresent()) {
            throw new BanExistException("이미 벤한 유저입니다.");
        }

        return banRepository.save(ban);
    }

    @Override
    public List<Ban> findBanList(Member from) {
        return banRepository.findByFrom(from);
    }
}
