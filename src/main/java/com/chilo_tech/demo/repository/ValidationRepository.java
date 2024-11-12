package com.chilo_tech.demo.repository;

import com.chilo_tech.demo.entity.Validation;
import jakarta.validation.constraints.Email;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation, Long> {

    Optional<Validation> findByUtilisateurEmailAndCode(String email, String code);

}
