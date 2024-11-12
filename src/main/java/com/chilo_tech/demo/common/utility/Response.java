package com.chilo_tech.demo.common.utility;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private int codeStatus;
    private StatusResponse status;
    private T payload;
    private String message;
    private Object trace;
    private Object metadata;
    private String url;

    public static <T> Response<T> badRequest(T payload, String nomEntite, String url){
        Response<T> response = new Response<>();
        response.setCodeStatus(400);
        response.setStatus(StatusResponse.BAD_REQUEST);
        response.setPayload(payload);
        response.setMessage("Mauvaise requete envoyée. /n Cause: " + nomEntite);
        response.setUrl(url);

        return response;
    }

    public static <T> Response<T> unauthorized(T payload, String surMessage){
        Response<T> response = new Response<>();
        response.setCodeStatus(401);
        response.setStatus(StatusResponse.UNAUTHORIZED);
        response.setPayload(payload);
        response.setMessage("Cette requete n'est authorisée " + surMessage);

        return response;
    }

    public static <T> Response<T> forbidden(T payload, String surMessage){
        Response<T> response = new Response<>();
        response.setCodeStatus(403);
        response.setStatus(StatusResponse.FORBIDDEN);
        response.setPayload(payload);
        response.setMessage("Problème sur l'accés" + surMessage);

        return response;
    }

    public static <T> Response<T> notFound(T payload, String surMessage){
        Response<T> response = new Response<>();
        response.setCodeStatus(404);
        response.setStatus(StatusResponse.NOT_FOUND);
        response.setPayload(payload);
        response.setMessage("Problème sur l'accés : " + surMessage);

        return response;
    }

    public static <T> Response<T> internalServerError(String surMessage){
        Response<T> response = new Response<>();
        response.setCodeStatus(500);
        response.setStatus(StatusResponse.INTERNAL_SERVER_ERROR);
        response.setMessage("Problème serveur : " + surMessage);

        return response;
    }

    public static <T> Response<T> duplicate(T payload, String surMessage, String url){
        Response<T> response = new Response<>();
        StringBuilder msg = new StringBuilder();
        response.setCodeStatus(409);
        response.setStatus(StatusResponse.DUPLICATE);
        response.setPayload(payload);
        msg.append("Duplication sur un champ.").append("\nCause: ").append(surMessage);
        response.setMessage(msg.toString());
        response.setUrl(url);

        return response;
    }

    public static <T> Response<T> ok(T payload, String surMessage){
        Response<T> response = new Response<>();
        response.setCodeStatus(200);
        response.setStatus(StatusResponse.OK);
        response.setPayload(payload);
        response.setMessage("Requete reussie pour : " + surMessage);

        return response;
    }

    public static <T> Response<T> gone(T payload, String surMessage){
        Response<T> response = new Response<>();
        response.setCodeStatus(410);
        response.setStatus(StatusResponse.GONE);
        response.setPayload(payload);
        response.setMessage("Connection expired : " + surMessage);

        return response;
    }

}
