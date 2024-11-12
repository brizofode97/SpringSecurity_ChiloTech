package com.chilo_tech.demo.service.implement;

import com.chilo_tech.demo.common.configuration.JwtConfig;
import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.constant.KProfit;
import com.chilo_tech.demo.entity.Role;
import com.chilo_tech.demo.entity.Utilisateur;
import com.chilo_tech.demo.entity.Validation;
import com.chilo_tech.demo.mapper.IUtilisateurMapper;
import com.chilo_tech.demo.repository.RoleRepository;
import com.chilo_tech.demo.repository.UtilisateurRepository;
import com.chilo_tech.demo.service.interfaces.IUtilisateurService;
import com.chilo_tech.demo.service.interfaces.IValidationService;
import com.chilo_tech.demo.web.dto.request.AuthentificationDTO;
import com.chilo_tech.demo.web.dto.request.UtilisateurRequestDTO;
import com.chilo_tech.demo.web.dto.response.UtilisateurResponseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements IUtilisateurService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtConfig jwtConfig;
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final IValidationService iValidationService;

    @Override
    @Transactional
    public Response<UtilisateurResponseDTO>  inscription(UtilisateurRequestDTO utilisateurRequestDTO) {
        boolean isCodeRole = (utilisateurRequestDTO.codeRole().isEmpty() || utilisateurRequestDTO.codeRole().isBlank());
        Optional<Role> roleOptional = roleRepository.findByCode(isCodeRole ? KProfit.CODE_UTILISATEUR : utilisateurRequestDTO.codeRole());
        if(roleOptional.isEmpty()) {
            return Response
                    .badRequest(null,"Le role de 'utilisateur n'est pas précisé", "/inscription");
        }
        Role roleObligatoire = roleOptional.get();
        String mdpCrypte = passwordEncoder.encode(utilisateurRequestDTO.mdp());
        UtilisateurRequestDTO utilisateurRequestDTOModifie = new UtilisateurRequestDTO(
                utilisateurRequestDTO.nomComplet(),
                mdpCrypte,
                utilisateurRequestDTO.email(),
                roleObligatoire.getCode()
        );
        Utilisateur utilisateur = IUtilisateurMapper.INSTANCE.MappageUtilisateurRequestDTOToUtilisateur(utilisateurRequestDTOModifie);
        utilisateur.setRole(roleObligatoire);
        Utilisateur utilisateurSave = utilisateurRepository.save(utilisateur);
        iValidationService.enregistrerValidation(utilisateurSave);
        UtilisateurResponseDTO utilisateurResponseDTO = IUtilisateurMapper.INSTANCE.MappageUtilisateurToUtilisateurResponseDTO(
                utilisateurSave
        );


        return Response
                .ok(utilisateurResponseDTO, "enregistrement utilisateur");
    }

    @Override
    public Response<Null> supprimerTousLesUtilisateurs() {
        utilisateurRepository.deleteAll();
        return Response
                .ok(null, "suppression de tous les utilisateurs");
    }

    @Override
    public Response<UtilisateurResponseDTO> ActiverUtilisateur(String email, String code) {
        Validation validation = iValidationService.recupererValidationParEmailUtilisateurEtCode(
                email, code
        );

        if(validation == null){
            return  Response.notFound(null, "Utilisateur n'existe pas");
        }
        if(Instant.now().isAfter(validation.getDateExpiration())){
            return Response.gone(null, "Code d'activation expiré");
        }
        Utilisateur utilisateur = validation.getUtilisateur();
        utilisateur.setActif(true);
        Utilisateur utilisateurModifie = utilisateurRepository.save(utilisateur);
        iValidationService.editerDateActivation(validation);

        UtilisateurResponseDTO utilisateurResponseDTOActif = IUtilisateurMapper.INSTANCE.MappageUtilisateurToUtilisateurResponseDTO(
                utilisateurModifie
        );
        return Response.ok(utilisateurResponseDTOActif, "L'utilisateur est maintenant activé");
    }

    @Override
    public Response<Map<String, String>> authentification(AuthentificationDTO authentificationDTO, AuthenticationManager authenticationManager) {
        Map<String, String> mapJwt;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );
        mapJwt = jwtConfig.generate(authentificationDTO.username());
        return Response.ok(mapJwt, "Votre token de connexion est maintenant disponible");
    }

}
