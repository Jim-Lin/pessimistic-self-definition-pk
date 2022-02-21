package com.example.demo.repository;

import com.example.demo.domain.Pessimistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessimisticJpaRepository extends JpaRepository<Pessimistic, Long> {

    Pessimistic findTopByOrderByIdDesc();
}
