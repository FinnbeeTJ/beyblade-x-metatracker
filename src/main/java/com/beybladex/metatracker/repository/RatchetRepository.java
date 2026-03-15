package com.beybladex.metatracker.repository;

import com.beybladex.metatracker.entity.Ratchet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RatchetRepository extends JpaRepository<Ratchet, Integer> {
    Optional<Ratchet> findByName(String name);
}