package com.sparta.trellowiththreeipeople.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto {

    @NotNull(message = "보드 이름을 필수로 입력해주세요.")
    private String boardName;

    @NotNull(message = "보드 정보를 필수로 입력해주세요.")
    private String boardInfo;

}
