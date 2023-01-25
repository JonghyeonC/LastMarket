package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.eneity.Ban;
import edu.ssafy.lastmarket.domain.eneity.Member;
import edu.ssafy.lastmarket.exception.BanExistException;
import edu.ssafy.lastmarket.exception.NotMemberUsernameException;
import edu.ssafy.lastmarket.repository.BanRepository;
import edu.ssafy.lastmarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BanServiceImpl implements BanService {
    private final BanRepository banRepository;
    private final MemberRepository memberRepository;

    @Override
    public Ban banUser(Member from, String banUser) {
        Member to = memberRepository.findByUsername(banUser)
                .orElseThrow(() -> new NotMemberUsernameException("없는 유저입니다."));
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
