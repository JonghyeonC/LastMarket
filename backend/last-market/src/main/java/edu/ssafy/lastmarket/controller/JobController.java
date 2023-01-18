package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.domain.dto.JobsDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @GetMapping
    public JobsDTO sendJobs() {
        return new JobsDTO();
    }
}
