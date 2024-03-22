package com.sparta.trellowiththreeipeople.card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CardDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime deadline;

    public CardDTO(Long id, String title, String content, LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
    }
}
