package com.chilo_tech.demo.mapper;

import com.chilo_tech.demo.entity.Utilisateur;
import com.chilo_tech.demo.web.dto.request.UtilisateurRequestDTO;
import com.chilo_tech.demo.web.dto.response.UtilisateurResponseDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUtilisateurMapper {

    IUtilisateurMapper INSTANCE = Mappers.getMapper(IUtilisateurMapper.class);

    //NOTE : Mappage entre utilisateur et utilisateur request DTO
    @Mappings({
            @Mapping(source = "utilisateur.nomComplet", target = "nomComplet"),
            @Mapping(source = "utilisateur.mdp", target = "mdp"),
            @Mapping(source = "utilisateur.email", target = "email"),
    })
    UtilisateurRequestDTO MappageUtilisateurToUtilisateurRequestDTO(Utilisateur utilisateur);

    @InheritInverseConfiguration
    Utilisateur MappageUtilisateurRequestDTOToUtilisateur(UtilisateurRequestDTO utilisateurRequestDTO);


    //NOTE : Mappage entre utilisateur et utilisateur response DTO
    @Mappings({
            @Mapping(source = "utilisateur.identifiant", target = "identifiant"),
            @Mapping(source = "utilisateur.nomComplet", target = "nomComplet"),
            @Mapping(source = "utilisateur.email", target = "email"),
            @Mapping(source = "utilisateur.actif", target = "actif"),
            @Mapping(source = "utilisateur.role", target = "role"),
    })
    UtilisateurResponseDTO MappageUtilisateurToUtilisateurResponseDTO(Utilisateur utilisateur);

    @InheritInverseConfiguration

    Utilisateur MappageUtilisateurResponseDTOToUtilisateur(UtilisateurResponseDTO utilisateurResponseDTO);
}
