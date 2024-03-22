package com.sparta.trellowiththreeipeople.board.dto;


import com.sparta.trellowiththreeipeople.board.entity.BoardUser;
import lombok.Getter;

@Getter
public class BoardResponseUsersResponseDto {

    private final Long userId;
    private final String username;


    public BoardResponseUsersResponseDto(BoardUser boardUser) {
        this.userId = boardUser.getUser().getId();
        this.username = boardUser.getUser().getUsername();
    }
}
