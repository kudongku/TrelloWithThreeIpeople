package com.sparta.trellowiththreeipeople.exception;

public class BoardNotFoundException extends ApiException {
    public BoardNotFoundException(ExceptionStatus ex) {
        super(ex);
    }
}
