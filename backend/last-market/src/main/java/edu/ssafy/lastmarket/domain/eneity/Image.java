package edu.ssafy.lastmarket.domain.eneity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String imageName;
    private String imageURL;


    public Image(String imageName, String imageURL){
        this.imageName=imageName;
        this.imageURL=imageURL;
    }
}
