package com.sparta.trellowiththreeipeople.card.dto;


import com.sparta.trellowiththreeipeople.card.entity.Card;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardResponse {
    private String title;
    private String content;

    public CardResponse(Card card) {
        this.title = card.getTitle();
        this.content = card.getContent();
    }
}
