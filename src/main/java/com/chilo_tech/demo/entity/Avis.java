package com.chilo_tech.demo.entity;

import com.chilo_tech.demo.common.utility.IdentifiantUnique;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="avis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long identifiant;

    private String message;

    private String status;

    @ManyToOne
    @JoinColumn(name="identifiant_utilisateur", referencedColumnName = "identifiant")
    Utilisateur utilisateur;

    @PrePersist
    void prePersist() {
        if(this.identifiant == null){
            this.identifiant = IdentifiantUnique.setIdentifiantBytime();
        }
    }

}
