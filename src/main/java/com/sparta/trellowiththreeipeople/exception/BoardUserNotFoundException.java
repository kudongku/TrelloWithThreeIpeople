package com.sparta.trellowiththreeipeople.exception;

public class BoardUserNotFoundException extends ApiException {
    public BoardUserNotFoundException(ExceptionStatus ex) {
        super(ex);
    }
}
