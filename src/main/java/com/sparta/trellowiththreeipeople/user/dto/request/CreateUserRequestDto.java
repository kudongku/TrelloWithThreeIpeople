package com.sparta.trellowiththreeipeople.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserRequestDto {
    private String username;
    private String password;
}
