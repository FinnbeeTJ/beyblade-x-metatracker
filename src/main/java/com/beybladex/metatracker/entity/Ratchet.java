package com.beybladex.metatracker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Ratchets")
public class Ratchet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RatchetID")
    private Integer id;

    @Column(name = "RatchetName", unique = true, nullable = false)
    private String name;
}