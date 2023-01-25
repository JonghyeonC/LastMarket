package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Ban;
import edu.ssafy.lastmarket.domain.entity.Member;

import java.util.List;

public interface BanService {
    Ban banUser(Member from, Member to);

    List<Ban> findBanList(Member from);
}
