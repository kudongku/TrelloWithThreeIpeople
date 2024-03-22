package com.sparta.trellowiththreeipeople.bar.repository;

import com.sparta.trellowiththreeipeople.bar.dto.BarResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarQueryRepository {
    void updateBarAndDeletedBy(long id, Long lastModifierId);

    List<BarResponseDto> findAllByBoard(Long boardId);
}
