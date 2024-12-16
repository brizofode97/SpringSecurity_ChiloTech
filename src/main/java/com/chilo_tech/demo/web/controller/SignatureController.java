package com.chilo_tech.demo.web.controller;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.service.interfaces.ISignatureService;
import com.google.zxing.WriterException;
import io.jsonwebtoken.io.Decoders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping(path = "/signature")
@RequiredArgsConstructor
public class SignatureController {

    private final ISignatureService iSignatureService;

    @PostMapping(path = "/signature_QRCode/{idSigner}")
    @Operation(summary = "Signer un fichier PDF",
            description = "Permet de signer un document PDF en envoyant le fichier sous forme de tableau de bytes.")
    public Response<byte[]> SignatureParQrCode(
            @RequestParam("codePin") String codePin,
            @RequestBody String fileReceivefile,
            @PathVariable("idSigner") int idSigner,
            @RequestParam("workerId") int workerId,
            @RequestParam(value = "X", defaultValue = "185", required = false) int positionX,
            @RequestParam(value = "Y", defaultValue = "330", required = false) int positionY
    ) throws WriterException, IOException {
        return iSignatureService.signatureParQrCode(codePin, Decoders.BASE64.decode(fileReceivefile), idSigner, workerId, positionX, positionY);
    }

}
