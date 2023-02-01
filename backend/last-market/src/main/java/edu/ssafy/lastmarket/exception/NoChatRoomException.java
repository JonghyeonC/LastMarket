package edu.ssafy.lastmarket.exception;

public class NoChatRoomException extends RuntimeException{
    public NoChatRoomException(String message) {
        super(message);
    }
}
