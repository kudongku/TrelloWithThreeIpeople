package com.sparta.trellowiththreeipeople.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {
    private String prePassword;
    private String postPassword;
}
