package com.beybladex.metatracker.dto;

public class PartStatsDTO {
    private String name;
    private long winCount;

    public PartStatsDTO(String name, long winCount) {
        this.name = name;
        this.winCount = winCount;
    }

    // Getters are required for JSON serialization
    public String getName() { return name; }
    public long getWinCount() { return winCount; }
}