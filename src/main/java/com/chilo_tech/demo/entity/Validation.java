package com.chilo_tech.demo.entity;

import com.chilo_tech.demo.common.utility.IdentifiantUnique;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "validation")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Validation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long identifiant;

    @NonNull
    private Instant dateCreation;
    private Instant dateActivation;
    @NonNull
    private Instant dateExpiration;

    @NonNull
    private String code;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    private Utilisateur utilisateur;

    @PrePersist
    private void prePersist() {
        identifiant = IdentifiantUnique.setIdentifiantBytime();
    }

}
