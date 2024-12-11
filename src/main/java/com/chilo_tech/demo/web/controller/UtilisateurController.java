package com.chilo_tech.demo.web.controller;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.service.interfaces.IUtilisateurService;
import com.chilo_tech.demo.web.dto.request.AuthentificationDTO;
import com.chilo_tech.demo.web.dto.request.UtilisateurRequestDTO;
import com.chilo_tech.demo.web.dto.response.UtilisateurResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
@Validated
public class UtilisateurController {

    private final IUtilisateurService iUtilisateurService;

    @PostMapping(path = "inscription")
    public Response<UtilisateurResponseDTO> inscription(@RequestBody @Valid UtilisateurRequestDTO utilisateurRequestDTO){
        return iUtilisateurService.inscription(utilisateurRequestDTO);
    }

    @PutMapping(path = "activer-utilisateur")
    public Response<UtilisateurResponseDTO> activerUtilisateur(
            @RequestParam("Email") @Email String email,
            @RequestParam("Code") String code){
        return iUtilisateurService.ActiverUtilisateur(email, code);
    }

    @GetMapping(path = "suppression/tous-les-utilisateurs")
    public Response<Null> suppressionTousLesUtilisateurs() {
        return iUtilisateurService.supprimerTousLesUtilisateurs();
    }

    @PostMapping(path = "connexion")
    public Response<Map<String, String>> authentification(
//            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody @Valid AuthentificationDTO authentificationDTO) throws Exception {

        return iUtilisateurService.authentification(authentificationDTO);
    }

}
