package com.chilo_tech.demo.service.interfaces;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.web.dto.request.AvisRequestDTO;
import com.chilo_tech.demo.web.dto.response.AvisResponseDTO;
import jakarta.validation.constraints.Null;

public interface IAvisService {

    Response<AvisResponseDTO> creer(AvisRequestDTO avisRequestDTO);
    Response<Null> supprimerTousAvis();


}
