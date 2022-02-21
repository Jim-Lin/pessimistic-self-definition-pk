package com.example.demo.service;

import com.example.demo.domain.Pessimistic;
import com.example.demo.domain.TypeLock;
import com.example.demo.repository.TypeLockJpaRepository;
import com.example.demo.repository.PessimisticJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PessimisticService {
    private final PessimisticJpaRepository pessimisticJpaRepository;
    private final TypeLockJpaRepository typeLockJpaRepository;

    public PessimisticService(PessimisticJpaRepository pessimisticJpaRepository,
                              TypeLockJpaRepository typeLockJpaRepository) {
        this.pessimisticJpaRepository = pessimisticJpaRepository;
        this.typeLockJpaRepository = typeLockJpaRepository;
    }

    @Transactional
    public void add() {
        Pessimistic latest = pessimisticJpaRepository.findTopByOrderByIdDesc();
        Pessimistic pessimistic = new Pessimistic();
        if (null == latest) {
            pessimistic.setId(1L);
        } else {
            pessimistic.setId(latest.getId() + 1);
        }

        pessimisticJpaRepository.save(pessimistic);
    }

    @Transactional
    public boolean tryLock() {
        Optional<TypeLock> opt = typeLockJpaRepository.findById(1L);
        if (!opt.isPresent()) {
            throw new RuntimeException();
        }

        TypeLock lock = opt.get();
        if (lock.getStatus()) {
            return false;
        }

        lock.setStatus(true);
        typeLockJpaRepository.save(lock);

        return true;
    }

    @Transactional
    public void releaseLock() {
        Optional<TypeLock> opt = typeLockJpaRepository.findById(1L);
        if (!opt.isPresent()) {
            throw new RuntimeException();
        }

        TypeLock lock = opt.get();
        lock.setStatus(false);
        typeLockJpaRepository.save(lock);
    }
}
