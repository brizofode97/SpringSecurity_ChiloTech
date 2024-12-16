package com.chilo_tech.demo.service.interfaces;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.web.dto.response.FichierResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IFichierService {

    Response<Optional<byte[]>> recupereByteFichier(Long identifiantFichier) throws IOException;
    Response<List<FichierResponseDTO>> creer(List<MultipartFile> fichiers) throws Exception;


}
