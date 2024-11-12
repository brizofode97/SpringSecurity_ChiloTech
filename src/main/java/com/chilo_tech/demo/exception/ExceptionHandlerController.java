package com.chilo_tech.demo.exception;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.web.dto.response.UtilisateurResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    //OPTIMIZE: Gestion des exceptions pour les champs uniques
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response<Object> handleDuplicateAttribute(DataIntegrityViolationException ex, WebRequest webRequest){
        return Response
                .duplicate(null, ex.getMessage(), webRequest.getDescription(false));
    }

    //OPTIMIZE: Gestion des exceptions sur les champs des DTOs ne respectants pas les annotations spécifiées sur eux
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> handleMethodArgumentNotValid(Exception exception, WebRequest webRequest){
        Map<String, String> errors = new HashMap<>();

        if(exception instanceof MethodArgumentNotValidException ex) {
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        if(exception instanceof ConstraintViolationException ex){
            ex.getConstraintViolations().forEach((violation) -> {
                String fieldName = violation.getPropertyPath().toString();
                String errorMessage = violation.getMessage();
                errors.put(fieldName, errorMessage);
            });
        }


        StringBuilder messageDesErreurs = new StringBuilder();
        for(String key : errors.keySet()){
            messageDesErreurs.append(key).append(": ").append(errors.get(key)).append("\n");
        }

        return  Response.badRequest(null, messageDesErreurs.toString(), webRequest.getDescription(false));
    }

}
