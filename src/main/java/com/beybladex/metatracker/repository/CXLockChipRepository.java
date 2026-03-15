package com.beybladex.metatracker.repository;

import com.beybladex.metatracker.entity.CXLockChip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CXLockChipRepository extends JpaRepository<CXLockChip, Integer> {
    Optional<CXLockChip> findByName(String name);
}