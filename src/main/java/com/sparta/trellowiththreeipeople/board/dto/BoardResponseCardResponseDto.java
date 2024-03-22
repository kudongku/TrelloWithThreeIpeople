package com.sparta.trellowiththreeipeople.board.dto;

import com.sparta.trellowiththreeipeople.card.entity.Card;
import lombok.Getter;

@Getter

public class BoardResponseCardResponseDto {

    private final Long cardId;
    private final String title;

    public BoardResponseCardResponseDto(Card card) {
        this.cardId = card.getId();
        this.title = card.getTitle();
    }
}
