package com.beybladex.metatracker.repository;

import com.beybladex.metatracker.dto.PartStatsDTO;
import com.beybladex.metatracker.entity.MatchWin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MatchWinRepository extends JpaRepository<MatchWin, Integer> {

    // Unified Blade Leaderboard: Combines BX/UX and CX into one list
    @Query("SELECT new com.beybladex.metatracker.dto.PartStatsDTO(" +
            "COALESCE(b.name, mb.name), COUNT(m)) " +
            "FROM MatchWin m " +
            "LEFT JOIN m.blade b " +
            "LEFT JOIN m.mainBlade mb " +
            "GROUP BY COALESCE(b.name, mb.name) " +
            "ORDER BY COUNT(m) DESC")
    List<PartStatsDTO> countAllTopBlades();

    // Unified Ratchet Leaderboard
    @Query("SELECT new com.beybladex.metatracker.dto.PartStatsDTO(r.name, COUNT(m)) " +
            "FROM MatchWin m " +
            "JOIN m.ratchet r " +
            "GROUP BY r.name " +
            "ORDER BY COUNT(m) DESC")
    List<PartStatsDTO> countTopRatchets();

    // Unified Bit Leaderboard
    @Query("SELECT new com.beybladex.metatracker.dto.PartStatsDTO(bt.name, COUNT(m)) " +
            "FROM MatchWin m " +
            "JOIN m.bit bt " +
            "GROUP BY bt.name " +
            "ORDER BY COUNT(m) DESC")
    List<PartStatsDTO> countTopBits();
}