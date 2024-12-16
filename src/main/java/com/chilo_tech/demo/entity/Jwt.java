package com.chilo_tech.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jwt")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean desactive;

    private boolean expiration;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "identifiant_utilisateur", nullable = false, referencedColumnName = "identifiant")
    private Utilisateur utilisateur;

}
