package com.example.demo.repository;

import com.example.demo.domain.Pessimistic;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class PessimisticSimpleJpaRepository extends SimpleJpaRepository<Pessimistic, Long> {
    private final EntityManager em;

    public PessimisticSimpleJpaRepository(EntityManager em) {
        super(Pessimistic.class, em);
        this.em = em;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Pessimistic> findById(Long id) {
        Query query = em.createQuery("from Pessimistic where id = :id");
        query.setParameter("id", id);
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);

        return Optional.ofNullable(query.getResultList())
                .filter(list -> 0 < list.size())
                .map(list -> (Pessimistic) list.get(0));
    }
}
