package com.chilo_tech.demo.entity;

import com.chilo_tech.demo.common.utility.IdentifiantUnique;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "utilisateur")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long identifiant;

    @Column(name = "prenom_nom", updatable = false, nullable = false)
    private String nomComplet;

    @Column(name = "mot_de_passe", updatable = true, nullable = false)
    private String mdp;

    @Column(unique = true, updatable = true, nullable = false)
    private String email;

    @Column(updatable = true, nullable = false)
    private boolean actif;

    @ManyToOne(cascade = CascadeType.ALL)
    private Role role;

    @Override
    public boolean isAccountNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isEnabled() {
        return this.actif;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.getLibelle()));
    }

    @Override
    public String getPassword() {
        return this.mdp;
    }

    @Override
    public String getUsername() {
        return this.nomComplet;
    }

    //OPTIMIZE: On retourne un role que l'on injecte avant de persister un utilisateur
//    Role ajoutRole(){
//        Role role = new Role();
//        role.setLibelle(TypeRole);
//        return role;
//    }

    //HACK: Ceci me permet d'initialiser l'identifiant unique
    @PrePersist
    public void prePersist() {

        actif = false;
        if(identifiant == null){
            identifiant = IdentifiantUnique.setIdentifiantBytime();
        }
    }
}
