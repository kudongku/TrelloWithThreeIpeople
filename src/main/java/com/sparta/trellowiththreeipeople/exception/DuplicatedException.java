package com.sparta.trellowiththreeipeople.exception;

public class DuplicatedException extends ApiException {

    public DuplicatedException(ExceptionStatus ex) {
        super(ex);
    }
}
