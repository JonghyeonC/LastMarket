package edu.ssafy.lastmarket.controller;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import edu.ssafy.lastmarket.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotMemberUsernameException.class)
    public ErrorMsgDTO jwtValidate(Exception e) {
        return new ErrorMsgDTO(e.toString(), "your username is not validate");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SignatureException.class)
    public ErrorMsgDTO jwtSignature(Exception e) {
        return new ErrorMsgDTO(e.toString(), "your jwt signature is not validate");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorMsgDTO expiredJwt(Exception e) {
        return new ErrorMsgDTO(e.toString(), "jwt is expired");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BanExistException.class)
    public ErrorMsgDTO alreadyBan(Exception e) {
        return new ErrorMsgDTO(e.toString(), "user is alreay banned");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotYourAuthority.class)
    public ErrorMsgDTO notYouAuthrity(Exception e) {
        return new ErrorMsgDTO(e.toString(), "notYouAuthrity");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IOException.class)
    public ErrorMsgDTO iOException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "iOException");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorMsgDTO notFoundException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "notFoundException");
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthenticated.class)
    public ErrorMsgDTO notAuthenticated(Exception e) {
        return new ErrorMsgDTO(e.toString(), "notAuthenticated");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorMsgDTO missingServletRequestParameterException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "missingServletRequestParameterException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ErrorMsgDTO missingServletRequestPartException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "missingServletRequestPartException");
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductSoldException.class)
    public ErrorMsgDTO productSoldException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "productSoldException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoChatRoomException.class)
    public ErrorMsgDTO noChatRoomException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "noChatRoomException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorMsgDTO illegalArgumentException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "illegalArgumentException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReviewAlreadyExistException.class)
    public ErrorMsgDTO reviewAlreadyExistException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "reviewAlreadyExistException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotMatchSellerException.class)
    public ErrorMsgDTO notMatchSellerException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "notMatchSellerException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UpdateProductCooltimeException.class)
    public ErrorMsgDTO updateProductCooltimeException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "updateProductCooltimeException");
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ErrorMsgDTO unrecognizedPropertyException(Exception e) {
        return new ErrorMsgDTO(e.toString(), "unrecognizedPropertyException");
    }
}
