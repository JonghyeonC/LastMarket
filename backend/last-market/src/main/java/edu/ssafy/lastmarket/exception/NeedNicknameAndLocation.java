package edu.ssafy.lastmarket.exception;

public class NeedNicknameAndLocation extends  RuntimeException{
    public NeedNicknameAndLocation(String message) {
        super(message);
    }
}
