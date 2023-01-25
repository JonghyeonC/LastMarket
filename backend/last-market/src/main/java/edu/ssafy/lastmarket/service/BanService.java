package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.eneity.Ban;
import edu.ssafy.lastmarket.domain.eneity.Member;

import java.util.List;

public interface BanService {
    Ban banUser(Member from, Member to);

    List<Ban> findBanList(Member from);
}
