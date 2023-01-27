package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Category;
import edu.ssafy.lastmarket.domain.entity.CategoryName;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.MemberCategory;

import java.util.List;

public interface MemberCategoryService {

    List<MemberCategory> save(List<CategoryName> categories, Member member) ;

}
