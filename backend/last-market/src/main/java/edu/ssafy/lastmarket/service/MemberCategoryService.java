package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.eneity.Category;
import edu.ssafy.lastmarket.domain.eneity.Member;
import edu.ssafy.lastmarket.domain.eneity.MemberCategory;

import java.util.ArrayList;
import java.util.List;

public interface MemberCategoryService {

    List<MemberCategory> save(List<Category> categories, Member member) ;

}
