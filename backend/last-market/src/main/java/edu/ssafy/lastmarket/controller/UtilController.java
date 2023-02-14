package edu.ssafy.lastmarket.controller;

import com.amazonaws.Response;
import edu.ssafy.lastmarket.domain.entity.CategoryName;
import edu.ssafy.lastmarket.domain.entity.Lifestyle;
import edu.ssafy.lastmarket.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UtilController {

    private final CategoryService categoryService;


    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {

        Map<String, List<CategoryName>> result = new HashMap<>();
        List<CategoryName> categoryNameList = categoryService.getCategoryList();
        result.put("categories", categoryNameList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/lifstyle")
    public ResponseEntity<?> getLifestyle() {

        Map<String, List<Lifestyle>> result = new HashMap<>();
        List<Lifestyle> lifestyleList = List.of(Lifestyle.values());
        result.put("lifestyles", lifestyleList);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
