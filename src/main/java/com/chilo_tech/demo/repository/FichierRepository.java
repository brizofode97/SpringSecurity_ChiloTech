package com.chilo_tech.demo.repository;

import com.chilo_tech.demo.entity.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FichierRepository extends JpaRepository<Fichier, Long> {
    Optional<Fichier> findByIdentifiant(Long idfichier);
}
