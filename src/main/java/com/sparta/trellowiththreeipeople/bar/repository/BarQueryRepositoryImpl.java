package com.sparta.trellowiththreeipeople.bar.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trellowiththreeipeople.bar.dto.BarResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.sparta.trellowiththreeipeople.bar.entity.QBar.bar;

@Repository
@RequiredArgsConstructor
public class BarQueryRepositoryImpl implements BarQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void updateBarAndDeletedBy(long id, Long lastModifierId) {
        queryFactory
                .update(bar)
                .set(bar.deletedAt, LocalDateTime.now())
                .set(bar.lastModifierId, lastModifierId)
                .where(bar.id.eq(id))
                .execute();
    }

    @Override
    public List<BarResponseDto> findAllByBoard(Long boardId) {
        return queryFactory.select(Projections.fields(BarResponseDto.class,
                        bar.id,
                        bar.title
                ))
                .from(bar)
                .where(bar.board.boardId.eq(boardId))
                .orderBy(bar.orderNum.asc())
                .fetch();
    }
}
