package edu.ssafy.lastmarket.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotMemberUsernameException extends RuntimeException{
    public NotMemberUsernameException(String message){
        super(message);
    }

}
