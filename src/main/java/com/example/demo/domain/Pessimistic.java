package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "pessimistic")
public class Pessimistic {

    @Id
    private Long id;
}
