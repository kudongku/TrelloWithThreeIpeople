package com.sparta.trellowiththreeipeople.board.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {

    @NotNull(message = "수정할 보드이름을 적어주세요.")
    private String boardName;

    @NotNull(message = "수정할 보드 설명을 적어주세요.")
    private String boardInfo;

    @Pattern(regexp = "^(RED|GREEN|BLUE|YELLOW|WHITE|BLACK)$", message = "잘못된 보드 배경 입력입니다. ")
    private String boardColorEnum;


}
