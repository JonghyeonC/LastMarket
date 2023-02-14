package edu.ssafy.lastmarket.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    private String dongCode;
    private String sido;
    private String gugun;
    private String dong;

    @Override
    public String toString(){
        return sido+" "+gugun+" "+ dong;
    }


}
