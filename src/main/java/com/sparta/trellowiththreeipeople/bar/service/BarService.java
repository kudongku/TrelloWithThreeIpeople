package com.sparta.trellowiththreeipeople.bar.service;

import com.sparta.trellowiththreeipeople.bar.dto.BarResponseDto;
import com.sparta.trellowiththreeipeople.bar.entity.Bar;
import com.sparta.trellowiththreeipeople.bar.repository.BarRepository;
import com.sparta.trellowiththreeipeople.board.entity.BoardUser;
import com.sparta.trellowiththreeipeople.board.repository.BoardUserRepository;
import com.sparta.trellowiththreeipeople.exception.BoardUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.sparta.trellowiththreeipeople.exception.ExceptionStatus.NOT_FOUND_BOARD_USER;

@Service
@RequiredArgsConstructor
public class BarService {
    private final BarRepository barRepository;
    private final BoardUserRepository boardUserRepository;

    @Transactional
    public Long createBar(Long boardId, String title, Long userId) {
        BoardUser boardUser = getBoardUser(boardId, userId);
        Bar bar = new Bar(title, boardUser.getBoard(), userId);
        barRepository.save(bar);
        bar.setOrderNum(bar.getId());
        return bar.getId();
    }

    @Transactional(readOnly = true)
    public List<BarResponseDto> getBarList(Long boardId, Long userId) {
        getBoardUser(boardId, userId);
        return barRepository.findAllByBoard(boardId);
    }

    @Transactional(readOnly = true)
    public BarResponseDto getBar(Long boardId, Long barId, Long userId) {
        getBoardUser(boardId, userId);
        Bar bar = barRepository.findById(barId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 바 아이디입니다.")
        );

        if (!Objects.equals(bar.getBoardId(), boardId)) {
            throw new IllegalArgumentException("보드 아이디가 해당 바의 보드와 일치하지 않습니다.");
        }

        return new BarResponseDto(bar.getId(), bar.getTitle());
    }

    @Transactional
    public void updateBar(Long boardId, Long barId, String title, Long userId) {
        getBoardUser(boardId, userId);
        Bar bar = barRepository.findById(barId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 바 아이디입니다.")
        );

        if (!Objects.equals(bar.getBoardId(), boardId)) {
            throw new IllegalArgumentException("보드 아이디가 해당 바의 보드와 일치하지 않습니다.");
        }

        bar.update(title, userId);
    }

    @Transactional
    public void deleteBar(Long boardId, Long barId, Long userId) {
        getBoardUser(boardId, userId);
        Bar bar = barRepository.findById(barId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 바 아이디입니다.")
        );

        if (!Objects.equals(bar.getBoardId(), boardId)) {
            throw new IllegalArgumentException("보드 아이디가 해당 바의 보드와 일치하지 않습니다.");
        }

        barRepository.updateBarAndDeletedBy(barId, userId);
    }

    @Transactional
    public void switchOrder(Long boardId, Long barId, Long switchedBarId, Long userId) {
        getBoardUser(boardId, userId);

        Bar bar = barRepository.findById(barId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 바 아이디입니다.")
        );

        Bar switchedBar = barRepository.findById(switchedBarId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 바 아이디입니다.")
        );

        Long temp = bar.getOrderNum();

        bar.setOrderNum(switchedBar.getOrderNum());
        switchedBar.setOrderNum(temp);
    }

    private BoardUser getBoardUser(Long boardId, Long userId) {
        return boardUserRepository.findBoardUserByBoardIdAndUserId(boardId, userId).orElseThrow(
                () -> new BoardUserNotFoundException(NOT_FOUND_BOARD_USER));
    }
}
