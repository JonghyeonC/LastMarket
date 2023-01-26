package edu.ssafy.lastmarket.exception;

public class BanExistException extends RuntimeException{
    public BanExistException(String message) {
        super(message);
    }
}
