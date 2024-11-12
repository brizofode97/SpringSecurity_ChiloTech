package com.chilo_tech.demo.service.interfaces;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.entity.Utilisateur;
import com.chilo_tech.demo.entity.Validation;

public interface IValidationService {

    Response<Validation> enregistrerValidation(Utilisateur utilisateur);
    Validation recupererValidationParEmailUtilisateurEtCode(String email, String code);
    Validation editerDateActivation(Validation validation);
}
