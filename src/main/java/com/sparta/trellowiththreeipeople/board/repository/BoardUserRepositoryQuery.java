package com.sparta.trellowiththreeipeople.board.repository;

import com.sparta.trellowiththreeipeople.board.entity.Board;
import com.sparta.trellowiththreeipeople.board.entity.BoardUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardUserRepositoryQuery {

    List<Board> findBoardListByUserIdFetchBoardUser(Long userId);

    List<BoardUser> getBoardUserByBoardId(Long BoardId);

    Optional<BoardUser> findBoardUserByBoardIdAndUserId(Long boardId, Long userId);


}
