package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Category;
import edu.ssafy.lastmarket.domain.entity.CategoryName;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findByCategoryNameString(String string);
    Optional<Category> findByCategoryName(CategoryName categoryName);
    List<CategoryName> getCategoryList();
}
