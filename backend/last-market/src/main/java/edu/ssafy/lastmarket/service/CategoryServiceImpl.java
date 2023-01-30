package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Category;
import edu.ssafy.lastmarket.domain.entity.CategoryName;
import edu.ssafy.lastmarket.repository.CategoryRepository;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findByCategoryNameString(String categoryNameString) {
        CategoryName categoryName = null;
        try{
            if(categoryNameString ==null){
                categoryNameString = "";
            }
            categoryName = CategoryName.valueOf(categoryNameString);
        }catch (IllegalArgumentException e1){
            return Optional.ofNullable(null);
        }finally {
            return categoryRepository.findByCategoryName(categoryName);
        }
    }

    @Override
    public Optional<Category> findByCategoryName(CategoryName categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }
}
