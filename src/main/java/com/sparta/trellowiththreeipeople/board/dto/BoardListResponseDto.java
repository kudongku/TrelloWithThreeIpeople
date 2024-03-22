package com.sparta.trellowiththreeipeople.board.dto;

import com.sparta.trellowiththreeipeople.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardListResponseDto {

    private final Long boardId;

    private final String boardName;

    public BoardListResponseDto(Board board) {

        this.boardId = board.getBoardId();
        this.boardName = board.getBoardName();
    }
}
