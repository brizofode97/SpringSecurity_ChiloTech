package com.chilo_tech.demo.repository;

import com.chilo_tech.demo.entity.Avis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisRepository extends CrudRepository<Avis, Long> {
}
