package com.chilo_tech.demo.service.interfaces;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.web.dto.request.AuthentificationDTO;
import com.chilo_tech.demo.web.dto.request.UtilisateurRequestDTO;
import com.chilo_tech.demo.web.dto.response.UtilisateurResponseDTO;
import jakarta.validation.constraints.Null;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Map;

public interface IUtilisateurService {

    Response<UtilisateurResponseDTO> inscription(UtilisateurRequestDTO utilisateurRequestDTO);
    Response<Null> supprimerTousLesUtilisateurs();
    Response<UtilisateurResponseDTO> ActiverUtilisateur(String email, String code);
    Response<Map<String, String>> authentification(AuthentificationDTO authentificationDTO) throws Exception;

}
