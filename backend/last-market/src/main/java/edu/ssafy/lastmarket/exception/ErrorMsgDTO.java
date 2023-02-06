package edu.ssafy.lastmarket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMsgDTO {
    private String errorName;
    private String msg;
}
