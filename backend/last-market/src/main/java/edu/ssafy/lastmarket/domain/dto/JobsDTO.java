package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.eneity.Job;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class JobsDTO {
    public List<Job> jobs;

    public JobsDTO() {
        this. jobs = List.of(Job.values());
    }
}
