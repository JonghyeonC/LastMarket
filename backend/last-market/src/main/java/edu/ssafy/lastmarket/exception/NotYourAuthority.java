package edu.ssafy.lastmarket.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotYourAuthority extends RuntimeException {
    public NotYourAuthority(String message) {
        super(message);
    }
}
