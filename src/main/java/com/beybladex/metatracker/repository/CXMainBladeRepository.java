package com.beybladex.metatracker.repository;

import com.beybladex.metatracker.entity.CXMainBlade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CXMainBladeRepository extends JpaRepository<CXMainBlade, Integer> {
    Optional<CXMainBlade> findByName(String name);
}