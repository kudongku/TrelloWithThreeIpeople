package com.sparta.trellowiththreeipeople.comment.service;

import com.sparta.trellowiththreeipeople.bar.entity.Bar;
import com.sparta.trellowiththreeipeople.bar.repository.BarRepository;
import com.sparta.trellowiththreeipeople.board.entity.Board;
import com.sparta.trellowiththreeipeople.board.repository.BoardRepository;
import com.sparta.trellowiththreeipeople.card.entity.Card;
import com.sparta.trellowiththreeipeople.card.repository.CardRepository;
import com.sparta.trellowiththreeipeople.comment.dto.request.CreateCommentRequestDto;
import com.sparta.trellowiththreeipeople.comment.dto.request.UpdateCommentRequestDto;
import com.sparta.trellowiththreeipeople.comment.dto.response.CommentResponseDto;
import com.sparta.trellowiththreeipeople.comment.dto.response.UpdateCommentResponseDto;
import com.sparta.trellowiththreeipeople.comment.entity.Comment;
import com.sparta.trellowiththreeipeople.comment.repository.CommentRepository;
import com.sparta.trellowiththreeipeople.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final BarRepository barRepository;
    private final CardRepository cardRepository;

    @Transactional
    public CommentResponseDto createComment(Long boardId, Long barId, Long cardId,
                                            CreateCommentRequestDto requestDto, User user) {
        Card card = getCard(boardId, barId, cardId);

        Comment comment = new Comment(card, requestDto, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }


    public List<CommentResponseDto> getComments(Long boardId, Long barId, Long cardId) {
        Card card = getCard(boardId, barId, cardId);

        return findComments(card).stream()
                .map(comment -> new CommentResponseDto(comment))
                .toList();
    }

    public CommentResponseDto getComment(Long boardId, Long barId, Long cardId, Long commentId) {
        Card card = getCard(boardId, barId, cardId);
        return new CommentResponseDto(findComment(commentId, card));
    }

    @Transactional
    public UpdateCommentResponseDto updateComment(Long boardId, Long barId, Long cardId,
                                                  Long commentId, UpdateCommentRequestDto requestDto, User user) {
        Card card = getCard(boardId, barId, cardId);

        Comment comment = findComment(commentId, card);
        isOwner(comment, user);

        comment.update(requestDto);
        commentRepository.save(comment);

        return new UpdateCommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long boardId, Long columnId, Long cardId, Long barId, User user) {

        Card card = getCard(boardId, barId, cardId);

        Comment comment = findComment(barId, card);
        isOwner(comment, user);

        commentRepository.delete(comment);
    }

    private Card getCard(Long boardId, Long barId, Long cardId) {
        findBoard(boardId);
        findBar(barId);
        return findCard(cardId);
    }

    private Board findBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 보드입니다"));
    }

    private Bar findBar(Long barId) {
        return barRepository.findById(barId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않은 컬럼입니다"));
    }

    private Card findCard(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않은 카드입니다"));
    }

    private List<Comment> findComments(Card card) {
        return commentRepository.findByCard(card);
    }

    private Comment findComment(Long commentId, Card card) {
        return commentRepository.findByIdAndCard(commentId, card)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 댓글입니다"));
    }

    private void isOwner(Comment comment, User user) {
        if (!(user.getId() == comment.getUser().getId())) {
            throw new IllegalArgumentException("댓글은 작성자만 수정 삭제할 수 있습니다.");
        }
    }


}
