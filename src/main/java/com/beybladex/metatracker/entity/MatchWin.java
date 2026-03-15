package com.beybladex.metatracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MatchWins")
public class MatchWin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MatchID")
    private Integer id;

    // --- SHARED PARTS (Every bey has these) ---
    @ManyToOne
    @JoinColumn(name = "WinningRatchetID", nullable = false)
    private Ratchet ratchet;

    @ManyToOne
    @JoinColumn(name = "WinningBitID", nullable = false)
    private Bit bit;

    // --- SYSTEM A: Standard BX/UX Blade ---
    @ManyToOne
    @JoinColumn(name = "WinningBladeID")
    private Blade blade; // Null if using CX system

    // --- SYSTEM B: CX System Parts ---
    @ManyToOne
    @JoinColumn(name = "WinningCXLockChipID")
    private CXLockChip lockChip;

    @ManyToOne
    @JoinColumn(name = "WinningCXMainBladeID")
    private CXMainBlade mainBlade;

    @ManyToOne
    @JoinColumn(name = "WinningCXAssistBladeID")
    private CXAssistBlade assistBlade;

    @Column(name = "MatchDate")
    private LocalDateTime matchDate;
}