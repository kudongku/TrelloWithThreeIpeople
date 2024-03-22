package com.sparta.trellowiththreeipeople.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "예외 핸들링")
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String INTERNAL_ERROR_500 = "서버 내부 오류가 발생했습니다. / Please Contact Admin";

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleAllUncaughtException(Exception ex) {
        log.error(ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(INTERNAL_ERROR_500);
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<String> handleApiException(ApiException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(INTERNAL_ERROR_500);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<String> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String message = "지원하지 않는 메서드입니다. Expected: " + ex.getSupportedHttpMethods() + " Actual : " + ex.getMethod();
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> IllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> NullPointerException(NullPointerException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> UserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatusCode())
                .body(e.getMessage());
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<String> BoardNotFoundException(BoardNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatusCode())
                .body(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> BadRequestException(BadRequestException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(BoardUserNotFoundException.class)
    public ResponseEntity<String> BoardUserNotFoundException(BoardUserNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatusCode())
                .body(e.getMessage());
    }

    @ExceptionHandler(BoardUserExistException.class)
    public ResponseEntity<String> BoardUserExistException(BoardUserExistException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatusCode())
                .body(e.getMessage());
    }

    @ExceptionHandler(AuthNotExistException.class)
    public ResponseEntity<String> AuthNotExistException(AuthNotExistException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatusCode())
                .body(e.getMessage());
    }

}
