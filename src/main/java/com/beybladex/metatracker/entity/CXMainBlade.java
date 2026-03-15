package com.beybladex.metatracker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CXMainBlades")
public class CXMainBlade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXMainBladeID")
    private Integer id;

    @Column(name = "CXMainBladeName")
    private String name;
}