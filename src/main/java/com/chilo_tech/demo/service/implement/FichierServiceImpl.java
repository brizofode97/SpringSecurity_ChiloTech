package com.chilo_tech.demo.service.implement;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.entity.Fichier;
import com.chilo_tech.demo.entity.Utilisateur;
import com.chilo_tech.demo.mapper.IFichierMapper;
import com.chilo_tech.demo.repository.FichierRepository;
import com.chilo_tech.demo.service.interfaces.IFichierService;
import com.chilo_tech.demo.web.dto.response.FichierResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.copy;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@RequiredArgsConstructor
public class FichierServiceImpl implements IFichierService {

    private final FichierRepository fichierRepository;

    @Value("${chilo.chemin.fichier}")
    private String cheminFichier;

    @Override
    public Response<Optional<byte[]>> recupereByteFichier(Long identifiantFichier) throws IOException {
        Optional<Fichier> fichierOptional = fichierRepository.findByIdentifiant(identifiantFichier);
        if (fichierOptional.isPresent()) {
            Fichier fichierObigatoire = fichierOptional.get();
            Path path = Paths.get(fichierObigatoire.getUrl());
            byte[] pdfBytes = Files.readAllBytes(path);
//            byte[] fichierByte = fichierObigatoire.getUrl().getBytes();
            return Response.ok(Optional.of(pdfBytes), "le byte du fichier");
        }
        return Response.ok(Optional.empty(), "le byte du fichier n'est pas disponible");
    }

    @Override
    public Response<List<FichierResponseDTO>> creer(List<MultipartFile> fichiers) throws Exception {

        List<FichierResponseDTO> fichierList = new ArrayList<>();

        for(MultipartFile file : fichiers){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename() != null ? file.getOriginalFilename() : "");

            if(fileName.isEmpty() || fileName.contains("..")){
                return Response.badRequest("Fichier", "/fichier/creation");
            }

            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            String type = file.getContentType();
            Long taille = file.getSize();
            Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Path directory = Paths.get(cheminFichier, fileName).toAbsolutePath().normalize();
            if(Files.notExists(directory)){
                Files.createDirectories(directory);
            }
            copy(file.getInputStream(), directory, REPLACE_EXISTING);
            Fichier fichier = new Fichier(fileName, directory.toString(), type, extension, taille, utilisateur);
            Fichier fichierSave = fichierRepository.save(fichier);
            FichierResponseDTO fichierResponseDTO = IFichierMapper.INSTANCE.FichierToFichierResponseDTO(fichierSave);
            fichierList.add(fichierResponseDTO);
        }

        return Response.ok(fichierList, "L'ensemble des fichiers est enregistré");
    }

}
