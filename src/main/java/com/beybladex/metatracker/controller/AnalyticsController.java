package com.beybladex.metatracker.controller;

import com.beybladex.metatracker.dto.PartStatsDTO;
import com.beybladex.metatracker.repository.MatchWinRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class AnalyticsController {

    private final MatchWinRepository matchRepo;

    public AnalyticsController(MatchWinRepository matchRepo) {
        this.matchRepo = matchRepo;
    }

    // Displays the total count on the dashboard (e.g., 639)
    @GetMapping("/summary")
    public long getTotalMatches() {
        return matchRepo.count();
    }

    @GetMapping("/all-blades")
    public List<PartStatsDTO> getAllBlades() {
        return matchRepo.countAllTopBlades();
    }

    @GetMapping("/ratchets")
    public List<PartStatsDTO> getTopRatchets() {
        return matchRepo.countTopRatchets();
    }

    @GetMapping("/bits")
    public List<PartStatsDTO> getTopBits() {
        return matchRepo.countTopBits();
    }
}