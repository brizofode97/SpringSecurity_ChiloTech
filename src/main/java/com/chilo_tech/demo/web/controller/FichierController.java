package com.chilo_tech.demo.web.controller;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.service.interfaces.IFichierService;
import com.chilo_tech.demo.web.dto.response.FichierResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/fichier")
@RequiredArgsConstructor
public class FichierController {

    private final IFichierService iFichierService;

    @PostMapping(path = "/creation", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<List<FichierResponseDTO>> creation(@RequestPart("fichier") List<MultipartFile> fichiers) throws Exception {
        return iFichierService.creer(fichiers);
    }

    @PostMapping(path = "/byte_fichier/{identifiantFichier}")
    public Response<Optional<byte[]>> recupereByteFichier(@PathVariable Long identifiantFichier) throws IOException {
        return  iFichierService.recupereByteFichier(identifiantFichier);
    }

}
