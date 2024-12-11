package com.chilo_tech.demo.service.implement;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.entity.Utilisateur;
import com.chilo_tech.demo.entity.Validation;
import com.chilo_tech.demo.repository.ValidationRepository;
import com.chilo_tech.demo.service.interfaces.IValidationService;
import com.chilo_tech.demo.service.utils.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements IValidationService {

    private final ValidationRepository validationRepository;
    private final NotificationService notificationService;

    @Override
//    @Transaction
    public Response<Validation> enregistrerValidation(Utilisateur utilisateur) {

        Instant dateCreation = Instant.now();
        Instant dateExpiration = dateCreation.plus(10, ChronoUnit.MINUTES);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String codeValidation = String.format("%06d", randomInteger);

        Validation validation = new Validation(dateCreation, dateExpiration, codeValidation, utilisateur);
        Validation validationSave = validationRepository.save(validation);
        notificationService.notificationApresEnregistrement(validationSave);

        return Response
                .ok(validationSave, "pour l'enregistrement de la validation");
    }

    @Override
    public Validation recupererValidationParEmailUtilisateurEtCode(String email, String code) {

        Optional<Validation> validationOptional = validationRepository.findByUtilisateurEmailAndCode(email, code);
        if(validationOptional.isEmpty()){
            return null;
        }
        Validation validationObligatoire = validationOptional.get();
        return validationObligatoire;
    }

    @Override
    public Validation editerDateActivation(Validation validation) {
        validation.setDateActivation(Instant.now());
        return validationRepository.save(validation);
    }

}

