package com.chilo_tech.demo.service.implement;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.service.interfaces.ISignatureService;
import com.chilo_tech.demo.service.utils.QrCodeSignature;
import com.google.zxing.WriterException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignatureServiceImpl implements ISignatureService {

    @Value("${chilo.chemin.fichier.signature}")
    private String cheminSignature;


    @Override
    public Response<byte[] >signatureParQrCode(
            String codePin,
            byte[] fichierASigne,
            int numeroEnrolement,
            int workerId,
            int positionX,
            int positionY) throws WriterException, IOException {

        String cheminFichier = cheminSignature + "output.pdf";

        log.info("❤️❤️❤️la valeur byte du fichier : {}", fichierASigne);

        try (
                PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(fichierASigne));
                PdfWriter pdfWriter = new PdfWriter(cheminFichier);
                PdfDocument pdfDocument = new PdfDocument(pdfReader, pdfWriter);
                Document document = new Document(pdfDocument);
                ) {

            //Générer un code
            String qrData = codePin + numeroEnrolement + workerId;
            Image qrImage = QrCodeSignature.generateQRCode(qrData);

            //Définir la position du QRCode sur la page
            qrImage.setFixedPosition(positionX, positionY);

            document.add(qrImage);

            byte[] pdfBytes = Files.readAllBytes(Paths.get(cheminFichier));
            log.info("💕💕💕Tableau de byte du fichier signé : {}", pdfBytes);

//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Custom-Header", "Value of the header");
//            headers.add("Content-Disposition", "attachment; filename=signed_document.pdf");

            return Response.ok(pdfBytes, "Le fichier en byte après signature");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
