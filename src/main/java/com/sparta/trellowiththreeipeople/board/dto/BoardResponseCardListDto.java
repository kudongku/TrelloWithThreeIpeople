package com.sparta.trellowiththreeipeople.board.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BoardResponseCardListDto {

    private final List<BoardResponseCardResponseDto> cards;

    public BoardResponseCardListDto(List<BoardResponseCardResponseDto> cards) {
        this.cards = cards;
    }
}
