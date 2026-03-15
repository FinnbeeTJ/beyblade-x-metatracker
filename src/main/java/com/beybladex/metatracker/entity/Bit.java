package com.beybladex.metatracker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Bits")
public class Bit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BitID")
    private Integer id;

    @Column(name = "BitName", unique = true, nullable = false)
    private String name;
}