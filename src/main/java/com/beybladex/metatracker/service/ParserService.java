package com.beybladex.metatracker.service;

import com.beybladex.metatracker.entity.*;
import com.beybladex.metatracker.repository.*;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParserService {

    private final BladeRepository bladeRepo;
    private final RatchetRepository ratchetRepo;
    private final BitRepository bitRepo;
    private final CXLockChipRepository cxLockRepo;
    private final CXMainBladeRepository cxMainRepo;
    private final CXAssistBladeRepository cxAssistRepo;
    private final MatchWinRepository matchWinRepo;

    public ParserService(BladeRepository bladeRepo, RatchetRepository ratchetRepo, BitRepository bitRepo,
                         CXLockChipRepository cxLockRepo, CXMainBladeRepository cxMainRepo,
                         CXAssistBladeRepository cxAssistRepo, MatchWinRepository matchWinRepo) {
        this.bladeRepo = bladeRepo;
        this.ratchetRepo = ratchetRepo;
        this.bitRepo = bitRepo;
        this.cxLockRepo = cxLockRepo;
        this.cxMainRepo = cxMainRepo;
        this.cxAssistRepo = cxAssistRepo;
        this.matchWinRepo = matchWinRepo;
    }

    public MatchWin parseAndSaveMatch(String inputString) {
        // CLEANUP STEP: Extract text inside parentheses if they exist
        // Input: "Player1 (Dran Sword 3-60 Flat)" -> Output: "Dran Sword 3-60 Flat"
        String comboToParse = inputString;
        if (inputString.contains("(") && inputString.contains(")")) {
            comboToParse = inputString.substring(inputString.indexOf("(") + 1, inputString.indexOf(")"));
        }

        // Remove confusing "1x " or "3x " counts if they exist from the raw list
        comboToParse = comboToParse.replaceAll("^\\d+x\\s*", "").trim();

        MatchWin match = new MatchWin();

        // 1. Find the Ratchet (Anchor point)
        String ratchetName = extractRatchet(comboToParse);
        Optional<Ratchet> ratchet = ratchetRepo.findByName(ratchetName);

        if (ratchet.isEmpty()) {
            // Silently fail if no ratchet found (it's likely just a player name like "Teddy")
            return null;
        }
        match.setRatchet(ratchet.get());

        // 2. Find the Bit (Right side of Ratchet)
        String[] splitString = comboToParse.split(ratchetName);
        if (splitString.length < 2) return null;

        String bitPart = splitString[1].trim();
        Optional<Bit> bit = bitRepo.findByName(bitPart);
        if (bit.isEmpty()) return null;

        match.setBit(bit.get());

        // 3. Find the Blade (Left side of Ratchet)
        String bladePart = splitString[0].trim();

        // CHECK A: Standard Blade
        Optional<Blade> stdBlade = bladeRepo.findByName(bladePart);
        if (stdBlade.isPresent()) {
            match.setBlade(stdBlade.get());
            return matchWinRepo.save(match);
        }

        // CHECK B: CX Combo (Split by spaces)
        String[] cxParts = bladePart.split(" ");
        if (cxParts.length >= 3) {
            Optional<CXLockChip> lock = cxLockRepo.findByName(cxParts[0]);
            Optional<CXMainBlade> main = cxMainRepo.findByName(cxParts[1]);
            Optional<CXAssistBlade> assist = cxAssistRepo.findByName(cxParts[2]);

            if (lock.isPresent() && main.isPresent() && assist.isPresent()) {
                match.setLockChip(lock.get());
                match.setMainBlade(main.get());
                match.setAssistBlade(assist.get());
                return matchWinRepo.save(match);
            }
        }

        System.out.println("Could not identify blade: " + bladePart);
        return null;
    }

    private String extractRatchet(String input) {
        Pattern pattern = Pattern.compile("\\d-\\d{2}");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return "Unknown";
    }
}