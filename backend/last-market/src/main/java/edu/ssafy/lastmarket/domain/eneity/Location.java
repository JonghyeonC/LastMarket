package edu.ssafy.lastmarket.domain.eneity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Location {
    @Id
    private String dongCode;
    private String sido;
    private String gugun;
    private String dong;

}
