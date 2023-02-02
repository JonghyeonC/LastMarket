package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Category;
import edu.ssafy.lastmarket.domain.entity.CategoryName;
import edu.ssafy.lastmarket.repository.CategoryRepository;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<CategoryName> getCategoryList(){
        List<CategoryName> categoryNameList  = new ArrayList<>();
        for (CategoryName value : CategoryName.values()) {
            categoryNameList.add(value);
        }


        return categoryNameList;

//        List<Category> categoryList = categoryRepository.findAll();
//
//        return categoryList.stream()
//                .map(category -> category.getCategoryName())
//                .collect(Collectors.toList());
    }


}
