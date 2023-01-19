package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.exception.NotMemberUsernameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotMemberUsernameException.class)
    public ResponseEntity<?> jwtValidate(Exception e){
        e.printStackTrace();
        Map<String,Object> result = new HashMap<>();
        result.put("error msg",e.toString());
        result.put("msg", "your username is not validate");
        return  new ResponseEntity<>(result, HttpStatus.OK);

    }

}
