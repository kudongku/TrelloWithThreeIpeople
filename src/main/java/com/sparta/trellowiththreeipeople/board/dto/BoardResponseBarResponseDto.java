package com.sparta.trellowiththreeipeople.board.dto;

import com.sparta.trellowiththreeipeople.bar.entity.Bar;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardResponseBarResponseDto {

    private final Long barId;

    private final String title;

    private final List<BoardResponseCardResponseDto> cards;

    public BoardResponseBarResponseDto(
            Bar bar,
            List<BoardResponseCardResponseDto> cards
    ) {
        this.barId = bar.getId();
        this.title = bar.getTitle();
        this.cards = cards;

    }
}
