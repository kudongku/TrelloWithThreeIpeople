package com.sparta.trellowiththreeipeople.board.entity;

import com.sparta.trellowiththreeipeople.common.BaseEntity;
import com.sparta.trellowiththreeipeople.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board_user")
@Entity
@SQLDelete(sql = "UPDATE board_user SET deleted_at=CURRENT_TIMESTAMP where board_userid=?")
@Where(clause = "deleted_at IS NULL")
public class BoardUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardUserid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public BoardUser(User user, Board board) {
        this.board = board;
        this.user = user;
    }
}
