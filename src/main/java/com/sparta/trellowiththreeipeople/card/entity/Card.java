package com.sparta.trellowiththreeipeople.card.entity;

import com.sparta.trellowiththreeipeople.bar.entity.Bar;
import com.sparta.trellowiththreeipeople.card.dto.CardRequest;
import com.sparta.trellowiththreeipeople.common.BaseEntity;
import com.sparta.trellowiththreeipeople.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE card SET deleted_at=CURRENT_TIMESTAMP where id=?")
@Where(clause = "deleted_at IS NULL")
@Table(name = "card", indexes = @Index(name = "idx_card_deleted_at", columnList = "deleted_at"))

public class Card extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    @Column(name = "deadline", nullable = true)
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bar_id")
    private Bar bar;


    //문자열 형식의 날짜 ex)20240319 를 LocalDateTime 형식으로 변환
    public Card(User user, Bar bar, CardRequest cardRequest) {
        this.title = cardRequest.getTitle();
        this.content = cardRequest.getContent();
        this.user = user;
        this.bar = bar;
        if (cardRequest.getDeadline() != null && !cardRequest.getDeadline().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate date = LocalDate.parse(cardRequest.getDeadline(), formatter);
            this.deadline = date.atStartOfDay(); // LocalDate를 LocalDateTime으로 변환
        }
    }

    public void updateCard(CardRequest cardRequest) {
        this.title = cardRequest.getTitle();
        this.content = cardRequest.getContent();
        if (cardRequest.getDeadline() != null && !cardRequest.getDeadline().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate date = LocalDate.parse(cardRequest.getDeadline(), formatter);
            this.deadline = date.atStartOfDay(); // LocalDate를 LocalDateTime으로 변환
        }
    }

    public long getBarId() {
        return this.bar.getId();
    }
}
//    public void deleteCard(){
//        this.deletedAt = LocalDateTime.now();
//    }
//    @PreUpdate
//    protected void onUpdate() {
//        this.modifiedAt = LocalDateTime.now();
//    }
