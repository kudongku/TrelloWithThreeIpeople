package com.sparta.trellowiththreeipeople.comment.entity;

import com.sparta.trellowiththreeipeople.card.entity.Card;
import com.sparta.trellowiththreeipeople.comment.dto.request.CreateCommentRequestDto;
import com.sparta.trellowiththreeipeople.comment.dto.request.UpdateCommentRequestDto;
import com.sparta.trellowiththreeipeople.common.BaseEntity;
import com.sparta.trellowiththreeipeople.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

@Getter
@Entity
@Table(name = "comment", indexes = {
        @Index(name = "idx_card_id", columnList = "card_id"),
        @Index(name = "idx_user_id", columnList = "user_id")
})
@NoArgsConstructor
@SQLDelete(sql = "UPDATE comment SET deleted_at=CURRENT_TIMESTAMP where id=?")
@Where(clause = "deleted_at IS NULL")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;


    public Comment(Card card, CreateCommentRequestDto requestDto, User user) {
        this.content = requestDto.getContent();
        this.user = user;
        this.card = card;
    }

    public void update(UpdateCommentRequestDto requestDto) {
        this.content =
                Objects.nonNull(requestDto.getContent()) ? requestDto.getContent() : this.content;
    }
}
