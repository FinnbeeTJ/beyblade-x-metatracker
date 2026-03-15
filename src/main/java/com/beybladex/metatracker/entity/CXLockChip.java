package com.beybladex.metatracker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CXLockChips")
public class CXLockChip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXLockChipID")
    private Integer id;

    @Column(name = "CXLockChipName")
    private String name;
}