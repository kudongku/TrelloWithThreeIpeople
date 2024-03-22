package com.sparta.trellowiththreeipeople.board.service;

import com.sparta.trellowiththreeipeople.board.dto.BoardListResponseDto;
import com.sparta.trellowiththreeipeople.board.dto.BoardRequestDto;
import com.sparta.trellowiththreeipeople.board.dto.BoardResponseDto;
import com.sparta.trellowiththreeipeople.board.dto.BoardUpdateRequestDto;
import com.sparta.trellowiththreeipeople.user.entity.User;

import java.util.List;


public interface BoardService {

    Long save(BoardRequestDto requestDto, User user);

    BoardResponseDto getBoardByBoardId(Long boardId, User user);

    List<BoardListResponseDto> getBoardListByUserId(User user);

    Long updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto, User user);

    void deleteBoard(Long boardId, User user);

    void inviteUserToBoard(Long boardId, Long userId, User user);
}
