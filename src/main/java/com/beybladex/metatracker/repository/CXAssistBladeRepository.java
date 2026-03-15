package com.beybladex.metatracker.repository;

import com.beybladex.metatracker.entity.CXAssistBlade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CXAssistBladeRepository extends JpaRepository<CXAssistBlade, Integer> {
    Optional<CXAssistBlade> findByName(String name);
}