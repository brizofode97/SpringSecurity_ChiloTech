package com.chilo_tech.demo.mapper;

import com.chilo_tech.demo.entity.Avis;
import com.chilo_tech.demo.web.dto.request.AvisRequestDTO;
import com.chilo_tech.demo.web.dto.response.AvisResponseDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IAvisMapper {

    IAvisMapper INSTANCE = Mappers.getMapper(IAvisMapper.class);

    //NOTE: Mappage entre Avis et request avis
    @Mappings({
            @Mapping(source="avis.message", target="message"),
            @Mapping(source="avis.status", target="status"),

    })
    AvisRequestDTO MapperAvisToAvisRequestDTO(Avis avis);

    @InheritInverseConfiguration
    Avis MapperAvisRequestDTOToAvis(AvisRequestDTO avisRequestDTO);


    //Mappage entre Avis et Avis response
    @Mappings({
            @Mapping(source="avis.identifiant", target="identifiant"),
            @Mapping(source="avis.message", target="message"),
            @Mapping(source="avis.status", target="status"),

    })
    AvisResponseDTO MapperAvisToAvisResponseDTO(Avis avis);

    @InheritInverseConfiguration
    Avis MapperAvisResponseDTOToAvis(AvisResponseDTO avisResponseDTO);

}
