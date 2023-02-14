package edu.ssafy.lastmarket.domain.entity;

import lombok.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createdDateTime;
    @LastModifiedDate
    private LocalDateTime lastModifiedDateTime;

    public void setLastModifiedDateTime(LocalDateTime localDateTime){
        this.lastModifiedDateTime = localDateTime;
    }
}
