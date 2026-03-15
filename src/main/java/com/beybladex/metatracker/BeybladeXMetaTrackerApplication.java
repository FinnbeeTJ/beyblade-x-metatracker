package com.beybladex.metatracker;

import com.beybladex.metatracker.repository.MatchWinRepository;
import com.beybladex.metatracker.service.ChallongeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BeybladeXMetaTrackerApplication {

	private static final Logger log = LoggerFactory.getLogger(BeybladeXMetaTrackerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BeybladeXMetaTrackerApplication.class, args);
	}

	@Bean
	public CommandLineRunner runChallongeImport(ChallongeService challongeService, MatchWinRepository matchWinRepository) {
		return args -> {
			log.info("==========================================");
			log.info("   SPIN TO WIN: META TRACKER - API MODE");
			log.info("==========================================");

			// 1. Check if data already exists to prevent duplicates
			long existingCount = matchWinRepository.count();

			if (existingCount > 0) {
				log.info("Database already contains {} matches. Skipping auto-import to prevent duplicates.", existingCount);
			} else {
				log.info("Database is empty. Starting first-time import...");

				// 2. DEFINE YOUR LIST OF TOURNAMENTS
				List<String> tournamentList = Arrays.asList(
						"lhoh8drd",  // World Championship 2025
						"7xxs2qff",  // S1 Nagano Cup (Open Bracket)
						"3mpfdd7j",  // June 2025 Meta Analysis
						"wb0s0wse"   // November 2025 Meta Analysis
				);

				log.info("Found {} tournaments to process...", tournamentList.size());

				// 3. LOOP THROUGH THE LIST
				for (String tournamentId : tournamentList) {
					log.info(">>> STARTING IMPORT FOR: {}", tournamentId);

					try {
						challongeService.processTournamentWins(tournamentId);
						log.info(">>> FINISHED IMPORT FOR: {}", tournamentId);
					} catch (Exception e) {
						log.error("!!! FAILED TO PROCESS TOURNAMENT: " + tournamentId, e);
					}
				}
			}

			log.info("==========================================");
			log.info("   SYSTEM INITIALIZATION COMPLETE");
			log.info("==========================================");
		};
	}
}