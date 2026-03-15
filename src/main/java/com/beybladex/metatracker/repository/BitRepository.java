package com.beybladex.metatracker.repository;

import com.beybladex.metatracker.entity.Bit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BitRepository extends JpaRepository<Bit, Integer> {
    Optional<Bit> findByName(String name);
}