package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "type_lock")
public class TypeLock {

    @Id
    private Long id;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean status;
}
