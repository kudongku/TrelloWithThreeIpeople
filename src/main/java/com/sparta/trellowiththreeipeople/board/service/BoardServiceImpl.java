package com.sparta.trellowiththreeipeople.board.service;

import com.sparta.trellowiththreeipeople.board.dto.*;
import com.sparta.trellowiththreeipeople.board.entity.Board;
import com.sparta.trellowiththreeipeople.board.entity.BoardUser;
import com.sparta.trellowiththreeipeople.board.repository.BoardRepository;
import com.sparta.trellowiththreeipeople.board.repository.BoardUserRepository;
import com.sparta.trellowiththreeipeople.card.entity.Card;
import com.sparta.trellowiththreeipeople.exception.*;
import com.sparta.trellowiththreeipeople.user.entity.User;
import com.sparta.trellowiththreeipeople.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta.trellowiththreeipeople.exception.ExceptionStatus.*;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardUserRepository boardUserRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long save(BoardRequestDto requestDto, User user) {
        checkIfBoardAlreadyExists(requestDto.getBoardName());
        Board board = Board.from(requestDto, user); // new 키워드 x -> static factory methods
        boardRepository.save(board);
        return board.getBoardId();
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponseDto getBoardByBoardId(Long boardId, User user) {
        BoardUser boardUser = getBoardUser(boardId, user);
        Board board = getBoard(boardId);


        List<BoardResponseUsersResponseDto> users = board.getUsers().stream()
                .map(BoardResponseUsersResponseDto::new)
                .toList();
        List<BoardResponseBarResponseDto> bars = board.getBars().stream()
                .map(bar -> new BoardResponseBarResponseDto(bar, createCardListDto(bar.getCards())))
                .toList();

        return new BoardResponseDto(board, users, bars);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardListResponseDto> getBoardListByUserId(User user) {

        List<Board> boards = boardUserRepository.findBoardListByUserIdFetchBoardUser(user.getId());
        if (boards == null) {
            throw new BoardNotFoundException(NOT_FOUND_BOARD);
        }
        return boards.stream()
                .map(BoardListResponseDto::new)
                .toList();
    }

    @Override
    @Transactional
    public Long updateBoard(Long boardId, BoardUpdateRequestDto requestDto, User user) {
        Board board = getBoard(boardId);
        BoardUser boardUser = getBoardUser(boardId, user);

        board.update(requestDto, boardUser);

        return board.getBoardId();

    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId, User user) {
        Board board = getBoard(boardId);

        if (!board.getCreatedUser().equals(user.getId())) {
            throw new AuthNotExistException(NOT_EXIST_AUTH);
        }
        List<BoardUser> boardUsers = boardUserRepository.getBoardUserByBoardId(boardId);

        boardRepository.delete(board);

    }

    @Override
    @Transactional
    public void inviteUserToBoard(Long boardId, Long userId, User user) {
        Board board = getBoard(boardId);
        BoardUser boardUser = getBoardUser(boardId, user);

        User invitedUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND_USER)
        );
        for (BoardUser boardUser1 : board.getUsers()) {
            if (boardUser1.getUser().getId().equals(userId)) {
                throw new BoardUserExistException(EXIST_BoardUser);
            }
        }
        board.inviteUser(invitedUser);
    }

    private void checkIfBoardAlreadyExists(String boardName) {
        if (boardRepository.existsBoardByBoardName(boardName)) {
            throw new DuplicatedException(DUPLICATED_BOARD);
        }
    }

    private BoardUser getBoardUser(Long boardId, User user) {
        return boardUserRepository.findBoardUserByBoardIdAndUserId(boardId, user.getId()).orElseThrow(
                () -> new AuthNotExistException(NOT_EXIST_AUTH));
    }

    private Board getBoard(Long boardId) {
        return boardRepository.findBoardByBoardId(boardId).orElseThrow(
                () -> new BoardNotFoundException(NOT_FOUND_BOARD));
    }

    private List<BoardResponseCardResponseDto> createCardListDto(List<Card> cards) {
        return cards.stream()
                .map(BoardResponseCardResponseDto::new)
                .toList();
    }
}
