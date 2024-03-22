package com.sparta.trellowiththreeipeople.bar.entity;

import com.sparta.trellowiththreeipeople.board.entity.Board;
import com.sparta.trellowiththreeipeople.card.entity.Card;
import com.sparta.trellowiththreeipeople.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bar")
@DynamicInsert
@DynamicUpdate
@SQLRestriction("deleted_at IS NULL")
public class Bar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "bar", fetch = FetchType.LAZY)
    private final List<Card> cards = new ArrayList<>();

    @Column(nullable = false)
    private Long createrId;

    private Long lastModifierId;

    private Long orderNum;

    public Bar(String title, Board board, Long createrId) {
        this.title = title;
        this.board = board;
        this.createrId = createrId;
    }

    public Long getBoardId() {
        return this.board.getBoardId();
    }

    public void update(String title, Long lastModifierId) {
        this.title = title;
        this.lastModifierId = lastModifierId;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }
}
