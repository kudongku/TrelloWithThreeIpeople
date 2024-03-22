package com.sparta.trellowiththreeipeople.card.repository;

import com.sparta.trellowiththreeipeople.card.dto.CardDTO;
import com.sparta.trellowiththreeipeople.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    // 모든 카드를 조회하는 메서드
    //fetch join 방식
    @Query("SELECT DISTINCT c FROM Card c JOIN FETCH c.user u JOIN FETCH c.bar b WHERE c.deletedAt IS NULL")
    List<Card> findAllWithUserAndBar();

    //DTO 매핑 방식
    @Query("SELECT new com.sparta.trellowiththreeipeople.card.dto.CardDTO(c.id, c.title, c.content, c.deadline) FROM Card c WHERE c.deletedAt IS NULL")
    List<CardDTO> findAllCardsWithDTO();
}
