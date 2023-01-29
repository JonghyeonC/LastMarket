package edu.ssafy.lastmarket.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotAuthenticated extends RuntimeException{

    public NotAuthenticated(String message) {
        super(message);
    }
}
