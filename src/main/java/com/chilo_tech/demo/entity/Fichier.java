package com.chilo_tech.demo.entity;

import com.chilo_tech.demo.common.utility.IdentifiantUnique;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fichier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fichier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long identifiant;

    @Column(name = "nom_fichier")
    private String nom;

    @Column(name = "url_fichier")
    private String url;

    @Column(name = "type_fichier")
    private String type;

    @Column(name = "extension_fichier")
    private String extension;

    @Column(name = "taille_fichier")
    private Long taille;

    @ManyToOne
    @JoinColumn(name = "identifiant_utilisateur", referencedColumnName = "identifiant")
    Utilisateur utilisateur;

    public Fichier(String nom, String url, String type, String extension, Long taille, Utilisateur utilisateur) {

        this.nom = nom;
        this.url = url;
        this.type = type;
        this.extension = extension;
        this.taille = taille;
        this.utilisateur = utilisateur;
    }

    @PrePersist
    protected void onCreate() {
        if(this.identifiant == null){
            this.identifiant = IdentifiantUnique.setIdentifiantBytime();
        }
    }

}
