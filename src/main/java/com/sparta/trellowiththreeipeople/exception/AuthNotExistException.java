package com.sparta.trellowiththreeipeople.exception;

public class AuthNotExistException extends ApiException {

    public AuthNotExistException(ExceptionStatus ex) {
        super(ex);
    }
}
