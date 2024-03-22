package com.sparta.trellowiththreeipeople.bar.repository;

import com.sparta.trellowiththreeipeople.bar.entity.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarRepository extends JpaRepository<Bar, Long>, BarQueryRepository {
}
