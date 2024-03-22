package com.sparta.trellowiththreeipeople.board.entity;

import com.sparta.trellowiththreeipeople.bar.entity.Bar;
import com.sparta.trellowiththreeipeople.board.dto.BoardRequestDto;
import com.sparta.trellowiththreeipeople.board.dto.BoardUpdateRequestDto;
import com.sparta.trellowiththreeipeople.common.BaseEntity;
import com.sparta.trellowiththreeipeople.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE board SET deleted_at=CURRENT_TIMESTAMP where board_id=?")
@Where(clause = "deleted_at IS NULL")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "board_name", nullable = false)
    private String boardName;

    @Column(name = "board_info", nullable = false)
    private String boardInfo;

    @Column(name = "color", nullable = false)
    private BoardColorEnum colorEnum = BoardColorEnum.RED;

    @OneToMany(mappedBy = "board")
    private List<Bar> bars = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BoardUser> users = new ArrayList<>();

    @Column(name = "created_user", nullable = false)
    private Long createdUser;

    @Column(name = "last_modified_user")
    private Long lastModifiedUser;


    public Board(BoardRequestDto requestDto, User user) {
        this.boardName = requestDto.getBoardName();
        this.boardInfo = requestDto.getBoardInfo();
        this.users.add(new BoardUser(user, this));
        this.createdUser = user.getId();
    }

    public static Board from(BoardRequestDto requestDto, User user) {
        return new Board(requestDto, user);
    }


    public void update(BoardUpdateRequestDto requestDto, BoardUser boardUser) {
        this.boardName = requestDto.getBoardName();
        this.boardInfo = requestDto.getBoardInfo();
        this.colorEnum = BoardColorEnum.valueOf(requestDto.getBoardColorEnum());
        this.lastModifiedUser = boardUser.getUser().getId();
    }

    public void inviteUser(User invitedUser) {
        this.users.add(new BoardUser(invitedUser, this));
    }
}
