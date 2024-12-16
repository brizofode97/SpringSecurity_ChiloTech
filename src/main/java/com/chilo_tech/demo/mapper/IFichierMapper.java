package com.chilo_tech.demo.mapper;

import com.chilo_tech.demo.entity.Fichier;
import com.chilo_tech.demo.web.dto.response.FichierResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IFichierMapper {

    IFichierMapper INSTANCE = Mappers.getMapper(IFichierMapper.class);

    @Mappings({
            @Mapping(source = "fichier.nom", target = "nom"),
            @Mapping(source = "fichier.url", target = "url"),
            @Mapping(source = "fichier.type", target = "type"),
            @Mapping(source = "fichier.extension", target = "extension"),
            @Mapping(source = "fichier.taille", target = "taille"),
            @Mapping(source = "fichier.utilisateur", target = "utilisateurResponseDTO"),
    })
    FichierResponseDTO FichierToFichierResponseDTO(Fichier fichier);

}
