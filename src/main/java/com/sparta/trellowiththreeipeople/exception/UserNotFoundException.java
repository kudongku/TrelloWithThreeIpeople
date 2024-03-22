package com.sparta.trellowiththreeipeople.exception;


public class UserNotFoundException extends ApiException {


    public UserNotFoundException(ExceptionStatus ex) {
        super(ex);
    }
}
