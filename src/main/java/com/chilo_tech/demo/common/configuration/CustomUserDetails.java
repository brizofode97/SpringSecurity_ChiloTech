package com.chilo_tech.demo.common.configuration;

import com.chilo_tech.demo.entity.Utilisateur;
import com.chilo_tech.demo.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(username);
        if(utilisateurOptional.isEmpty()){
            throw new UsernameNotFoundException("L'utilisateur n'existe pas");
        }
        return utilisateurOptional.get();
    }
}
