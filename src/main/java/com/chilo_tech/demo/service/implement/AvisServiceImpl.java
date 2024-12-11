package com.chilo_tech.demo.service.implement;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.entity.Avis;
import com.chilo_tech.demo.entity.Utilisateur;
import com.chilo_tech.demo.mapper.IAvisMapper;
import com.chilo_tech.demo.repository.AvisRepository;
import com.chilo_tech.demo.service.interfaces.IAvisService;
import com.chilo_tech.demo.web.dto.request.AvisRequestDTO;
import com.chilo_tech.demo.web.dto.response.AvisResponseDTO;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvisServiceImpl implements IAvisService {

    private final AvisRepository avisRepository;

    @Override
    public Response<AvisResponseDTO> creer(AvisRequestDTO avisRequestDTO) {
        Avis avis = IAvisMapper.INSTANCE.MapperAvisRequestDTOToAvis(avisRequestDTO);
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        avis.setUtilisateur(utilisateur);
        Avis avisSauvegarde = avisRepository.save(avis);
        AvisResponseDTO avisResponseDTO = IAvisMapper.INSTANCE.MapperAvisToAvisResponseDTO(avis);
        return Response
                .ok(avisResponseDTO, "pour l'enregistrement d'un avis");

    }

    @Override
    public Response<Null> supprimerTousAvis() {
        avisRepository.deleteAll();
        return Response
                .ok(null, "Suppression de tous les avis");
    }
}
