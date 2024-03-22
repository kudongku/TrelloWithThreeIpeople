package com.sparta.trellowiththreeipeople.board.dto;

import com.sparta.trellowiththreeipeople.board.entity.Board;
import com.sparta.trellowiththreeipeople.board.entity.BoardColorEnum;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardResponseDto {

    private final Long boardId;
    private final String boardName;
    private final String boardInfo;
    private final BoardColorEnum colorEnum;
    private final List<BoardResponseUsersResponseDto> users;
    private final List<BoardResponseBarResponseDto> bars;


    public BoardResponseDto(
            Board board,
            List<BoardResponseUsersResponseDto> usersResponseDto,
            List<BoardResponseBarResponseDto> barResponseDto
    ) {
        this.boardId = board.getBoardId();
        this.boardName = board.getBoardName();
        this.boardInfo = board.getBoardInfo();
        this.colorEnum = board.getColorEnum();
        this.users = usersResponseDto;
        this.bars = barResponseDto;
    }

}
