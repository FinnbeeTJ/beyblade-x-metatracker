package com.beybladex.metatracker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data // Lombok automatically creates Getters, Setters, and toString()
@Entity
@Table(name = "Blades") // This matches your SQL Table name
public class Blade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BladeID")
    private Integer id;

    @Column(name = "BladeName", unique = true, nullable = false)
    private String name;

    @Column(name = "BladeType")
    private String type; // Attack, Stamina, etc.
}