package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.entity.Category;
import edu.ssafy.lastmarket.domain.entity.CategoryName;
import edu.ssafy.lastmarket.domain.entity.Job;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class MemberRegistDto {
    private String nickname;
    private Job job;
    private String addr;
    private List<CategoryName> categories;
}
