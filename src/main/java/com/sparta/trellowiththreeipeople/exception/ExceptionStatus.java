package com.sparta.trellowiththreeipeople.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

    // 400 - Bad Request
    DUPLICATED_BOARD(HttpStatus.BAD_REQUEST.value(), "같은 이름의 보드가 이미 존재합니다"),
    EXIST_BoardUser(HttpStatus.BAD_REQUEST.value(), "이 유저는 이미 보드에 초대된 유저입니다."),
    NOT_EXIST_AUTH(HttpStatus.FORBIDDEN.value(), "해당 권한이 없습니다."),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND.value(), "해당하는 보드를 찾을 수 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), "해당하는 유저를 찾을 수 없습니다."),
    NOT_FOUND_BOARD_USER(HttpStatus.NOT_FOUND.value(), "보드유저를 찾을 수 없습니다.");


    private final Integer statusCode;
    private final String message;
}
