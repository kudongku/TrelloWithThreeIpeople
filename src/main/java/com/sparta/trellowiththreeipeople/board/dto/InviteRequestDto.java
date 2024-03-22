package com.sparta.trellowiththreeipeople.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InviteRequestDto {

    @NotNull(message = "초대할 유저 아이디를 필수로 입력해주세요.")
    private Long userId;
}
