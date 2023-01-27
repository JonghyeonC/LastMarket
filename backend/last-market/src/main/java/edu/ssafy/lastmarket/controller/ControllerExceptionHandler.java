package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.exception.BanExistException;
import edu.ssafy.lastmarket.exception.NotMemberUsernameException;
import edu.ssafy.lastmarket.exception.NotYourAuthority;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
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

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> jwtSignature(Exception e){
        e.printStackTrace();
        Map<String, Object> result = new HashMap<>();
        result.put("error msg", e.toString());
        result.put("msg", "your jwt signature is not validate");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> expiredJwt(Exception e){

        e.printStackTrace();
        Map<String, Object> result = new HashMap<>();
        result.put("error msg", e.toString());
        result.put("msg", "jwt is expired");
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BanExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> alreadyBan(Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put("error msg", e.toString());
        result.put("msg", "user is alreay banned");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotYourAuthority.class)
    public ResponseEntity<?> notYouAuthrity(Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put("error msg", e.toString());
        result.put("msg", "notYouAuthrity");
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> iOException(Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put("error msg", e.toString());
        result.put("msg", "iOException");
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}