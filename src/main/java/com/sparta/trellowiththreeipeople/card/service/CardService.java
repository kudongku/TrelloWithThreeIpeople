package com.sparta.trellowiththreeipeople.card.service;


import com.sparta.trellowiththreeipeople.bar.entity.Bar;
import com.sparta.trellowiththreeipeople.bar.repository.BarRepository;
import com.sparta.trellowiththreeipeople.board.entity.BoardUser;
import com.sparta.trellowiththreeipeople.board.repository.BoardUserRepository;
import com.sparta.trellowiththreeipeople.card.dto.CardDTO;
import com.sparta.trellowiththreeipeople.card.dto.CardRequest;
import com.sparta.trellowiththreeipeople.card.dto.CardResponse;
import com.sparta.trellowiththreeipeople.card.entity.Card;
import com.sparta.trellowiththreeipeople.card.repository.CardRepository;
import com.sparta.trellowiththreeipeople.exception.BoardUserNotFoundException;
import com.sparta.trellowiththreeipeople.user.entity.User;
import com.sparta.trellowiththreeipeople.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.sparta.trellowiththreeipeople.exception.ExceptionStatus.NOT_FOUND_BOARD_USER;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final BarRepository barRepository;
    private final UserRepository userRepository;

    private final BoardUserRepository boardUserRepository;

    //카드 생성
    public Long createCard(Long barId, CardRequest cardRequest, Long userId) {
        User checkUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을수 없습니다"));
        Bar checkBar = barRepository.findById(barId).orElseThrow(() -> new IllegalArgumentException("컬럼을 찾을수 없습니다"));
        Card card = new Card(checkUser, checkBar, cardRequest);
        cardRepository.save(card);
        return card.getId();
    }

    //전체 카드 조회
    @Transactional(readOnly = true)
    public List<CardDTO> getAllCards(Long boardId, Long userId) {
        getBoardUser(boardId, userId);
        List<CardDTO> cardList = cardRepository.findAllCardsWithDTO();
        return cardList;
    }


    //선택한 카드 조회
    @Transactional(readOnly = true)
    public CardResponse getCard(Long cardId, Long barId, Long boardId, Long userId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을수 없습니다."));
        getBoardUser(boardId, userId);
        if (!Objects.equals(card.getBarId(), barId)) {
            throw new IllegalArgumentException("카드 아이디가 해당 카드의 바와 일치하지 않습니다.");
        }
        return new CardResponse(card);
    }

    //카드 수정 메서드
    @Transactional
    public CardResponse updateCard(Long cardId, CardRequest cardRequest, Long userId, Long boardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을수 없습니다."));
        getBoardUser(boardId, userId);
        if (card.getUser().getId() != userId) {
            throw new AccessDeniedException("카드를 삭제할 권한이 없습니다.");
        }
        card.updateCard(cardRequest);
        return new CardResponse(card);

    }


    public void deleteCard(Long cardId, Long userId, Long boardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을수 없습니다."));
        getBoardUser(boardId, userId);
        if (card.getUser().getId() != userId) {
            throw new AccessDeniedException("카드를 삭제할 권한이 없습니다.");
        }
        cardRepository.delete(card);
    }

    private BoardUser getBoardUser(Long boardId, Long userId) {
        return boardUserRepository.findBoardUserByBoardIdAndUserId(boardId, userId).orElseThrow(
                () -> new BoardUserNotFoundException(NOT_FOUND_BOARD_USER));
    }
}
