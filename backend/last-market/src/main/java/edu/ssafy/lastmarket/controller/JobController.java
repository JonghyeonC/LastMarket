package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.domain.dto.LifistylesDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lifestyles")
public class JobController {
    @GetMapping
    public LifistylesDTO sendLifestyles() {
        return new LifistylesDTO();
    }
}
