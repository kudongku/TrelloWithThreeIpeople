package com.sparta.trellowiththreeipeople.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trellowiththreeipeople.board.entity.Board;
import com.sparta.trellowiththreeipeople.board.entity.BoardUser;
import com.sparta.trellowiththreeipeople.board.entity.QBoardUser;
import com.sparta.trellowiththreeipeople.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardUserRepositoryQueryImpl implements BoardUserRepositoryQuery {

    private final JPAQueryFactory queryFactory;
    private static QBoardUser boardUser = QBoardUser.boardUser;
    private static QUser user = QUser.user;

    @Override
    public List<Board> findBoardListByUserIdFetchBoardUser(Long userId) {
        List<BoardUser> boardUsers = queryFactory.selectFrom(boardUser)
                .leftJoin(boardUser.user, user)
                .where(boardUser.user.id.eq(userId))
                .fetchJoin()
                .fetch();
        return boardUsers.stream()
                .map(BoardUser::getBoard)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardUser> getBoardUserByBoardId(Long boardId) {
        QBoardUser boardUser = QBoardUser.boardUser;

        return queryFactory.selectFrom(boardUser)
                .where(boardUser.board.boardId.eq(boardId))
                .fetch();
    }

    @Override
    public Optional<BoardUser> findBoardUserByBoardIdAndUserId(Long boardId, Long userId) {
        QBoardUser boardUser = QBoardUser.boardUser;

        return Optional.ofNullable(queryFactory.selectFrom(boardUser)
                .where(boardUser.board.boardId.eq(boardId)
                        .and(boardUser.user.id.eq(userId)))
                .fetchOne());
    }

}
