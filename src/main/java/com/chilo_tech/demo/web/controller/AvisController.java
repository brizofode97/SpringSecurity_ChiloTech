package com.chilo_tech.demo.web.controller;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.service.interfaces.IAvisService;
import com.chilo_tech.demo.web.dto.request.AvisRequestDTO;
import com.chilo_tech.demo.web.dto.response.AvisResponseDTO;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/avis")
@RequiredArgsConstructor
public class AvisController {

    private final IAvisService iAvisService;

    @PostMapping(path="/creation")
    public Response<AvisResponseDTO> creerAvis(@RequestBody(required = true) AvisRequestDTO avisRequestDTO) {
        return iAvisService.creer(avisRequestDTO);
    }

    @GetMapping(path = "/suppression-tous-avis")
    public Response<Null> suppressionTousAvis(){
        return iAvisService.supprimerTousAvis();
    }

}
