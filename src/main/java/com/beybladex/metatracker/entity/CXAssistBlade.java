package com.beybladex.metatracker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CXAssistBlades")
public class CXAssistBlade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXAssistBladeID")
    private Integer id;

    @Column(name = "CXAssistBladeName")
    private String name;
}