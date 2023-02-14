package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.entity.Lifestyle;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LifistylesDTO {
    public List<Lifestyle> lifestyles;

    public LifistylesDTO() {
        this.lifestyles = List.of(Lifestyle.values());
    }
}
