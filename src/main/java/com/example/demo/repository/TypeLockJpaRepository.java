package com.example.demo.repository;

import com.example.demo.domain.TypeLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface TypeLockJpaRepository extends JpaRepository<TypeLock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TypeLock> findById(Long id);
}
