package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.exception.BanExistException;
import edu.ssafy.lastmarket.exception.NotMemberUsernameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotMemberUsernameException.class)
    public ResponseEntity<?> jwtValidate(Exception e) {
        e.printStackTrace();
        Map<String, Object> result = new HashMap<>();
        result.put("error msg", e.toString());
        result.put("msg", "your username is not validate");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(BanExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> alreadyBan(Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put("error msg", e.toString());
        result.put("msg", "user is alreay banned");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

}
