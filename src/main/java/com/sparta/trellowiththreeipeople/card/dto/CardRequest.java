package com.sparta.trellowiththreeipeople.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
    private String title;
    private String content;
    private String deadline;
}
