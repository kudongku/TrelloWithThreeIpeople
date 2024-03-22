package com.sparta.trellowiththreeipeople.exception;

public class BoardUserExistException extends ApiException {
    public BoardUserExistException(ExceptionStatus ex) {
        super(ex);
    }
}
