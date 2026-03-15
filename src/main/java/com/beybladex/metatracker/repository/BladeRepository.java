package com.beybladex.metatracker.repository;

import com.beybladex.metatracker.entity.Blade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BladeRepository extends JpaRepository<Blade, Integer> {
    Optional<Blade> findByName(String name);
}