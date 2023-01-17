package edu.ssafy.lastmarket.domain.eneity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Getter
@ToString(exclude = "dongCode")
public class Location {
    @Id
    private String dongCode;
    private String sido;
    private String gugun;
    private String dong;

}
