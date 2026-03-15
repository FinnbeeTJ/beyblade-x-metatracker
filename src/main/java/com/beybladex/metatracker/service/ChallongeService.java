package com.beybladex.metatracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChallongeService {

    private static final Logger log = LoggerFactory.getLogger(ChallongeService.class);
    private final RestTemplate restTemplate;
    private final ParserService parserService;

    @Value("${challonge.api.key}")
    private String apiKey;

    public ChallongeService(ParserService parserService) {
        this.restTemplate = new RestTemplate();
        this.parserService = parserService;
    }

    public void processTournamentWins(String tournamentUrlId) {
        log.info("--- Connecting to Challonge API for Tournament: {} ---", tournamentUrlId);

        // 1. Fetch Participants to map IDs to Names
        Map<Integer, String> playerMap = fetchParticipants(tournamentUrlId);
        log.info("Found {} participants.", playerMap.size());

        // 2. Fetch Matches to find Winners
        // Note: We include 'include_participants' and 'include_matches' to ensure we get data
        String url = "https://api.challonge.com/v1/tournaments/" + tournamentUrlId + "/matches.json?api_key=" + apiKey + "&state=complete";

        try {
            JsonNode root = restTemplate.getForObject(url, JsonNode.class);
            if (root == null) return;

            int winsProcessed = 0;

            for (JsonNode node : root) {
                JsonNode match = node.get("match");
                if (match != null && match.hasNonNull("winner_id")) {
                    Integer winnerId = match.get("winner_id").asInt();

                    // Look up the player name using the ID
                    String winnerName = playerMap.get(winnerId);

                    if (winnerName != null) {
                        try {
                            // 3. Send the Name string to the Parser
                            // Returns null if it fails, or a MatchWin object if it succeeds
                            if (parserService.parseAndSaveMatch(winnerName) != null) {
                                winsProcessed++;
                            }
                        } catch (Exception e) {
                            // Ignored: Player name didn't have parts or wasn't formatted correctly
                        }
                    }
                }
            }
            log.info("--- Success! Processed {} wins from Challonge data. ---", winsProcessed);

        } catch (Exception e) {
            log.error("Failed to fetch matches from Challonge", e);
        }
    }

    private Map<Integer, String> fetchParticipants(String tournamentId) {
        Map<Integer, String> map = new HashMap<>();
        String url = "https://api.challonge.com/v1/tournaments/" + tournamentId + "/participants.json?api_key=" + apiKey;

        try {
            JsonNode root = restTemplate.getForObject(url, JsonNode.class);
            if (root != null) {
                for (JsonNode node : root) {
                    JsonNode p = node.get("participant");
                    if (p != null) {
                        map.put(p.get("id").asInt(), p.get("name").asText());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to fetch participants", e);
        }
        return map;
    }
}