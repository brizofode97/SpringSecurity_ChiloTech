package com.chilo_tech.demo.service.implement;

import com.chilo_tech.demo.common.utility.Response;
import com.chilo_tech.demo.service.interfaces.ISignatureService;
import com.chilo_tech.demo.service.utils.QrCodeSignature;
import com.google.zxing.WriterException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignatureServiceImpl implements ISignatureService {

    @Value("${chilo.chemin.fichier.signature}")
    private String cheminFichierSigne;

    @Value("${chilo.chemin.signature}")
    private String cheminSignature;


    @Override
    public Response<String> signatureParQrCode(
            String codePin,
            byte[] fichierASigne,
            int numeroEnrolement,
            int workerId,
            int positionX,
            int positionY) throws WriterException, IOException {

        String cheminFichier = cheminFichierSigne + "outputQrCode.pdf";

        log.info("❤️❤️❤️la valeur byte du fichier pour QrCode : {}", fichierASigne);

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

            String cheminOriginal = cheminFichier.replaceAll("/", "\\\\");
            Path path = Paths.get(cheminOriginal).toAbsolutePath().normalize();
            FileInputStream fileInputStream = new FileInputStream(cheminOriginal);
            byte[] pdfBytes = fileInputStream.readAllBytes();
            log.info("💕💕💕Le chemin du fichier à signer : {}", cheminOriginal);
            log.info("💕💕💕Le chemin du fichier à signerqqw : {}", cheminFichier);
            log.info("💕💕💕Tableau de byte du fichier signé pour QrCode : {}", pdfBytes);

            fileInputStream.close();
            
            return Response.ok(Base64.getEncoder().encodeToString(pdfBytes), "Le fichier en byte après signature QrCode");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    C:\Users\User\Documents\mes_programmes_apprentissage\SpringBoot\spring security\fichier\co_VF 2.pdf

    @Override
    public Response<byte[]> signatureParSpecimen(String codePin, byte[] fichierASigne, int idSigner, int workerId) {

        String cheminFichier = cheminFichierSigne + "outputSpecimen.pdf";

        log.info("❤️❤️❤️la valeur byte du fichier pour Specimen : {}", fichierASigne);

        try (
                PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(fichierASigne));
                PdfWriter pdfWriter = new PdfWriter(cheminFichier);
                PdfDocument pdfDocument = new PdfDocument(pdfReader, pdfWriter);
                Document document = new Document(pdfDocument);
        ) {

            // Charger l'image de signature
            String cheminComplet = cheminSignature + "/signature_specimen.png";
            ImageData imageData = ImageDataFactory.create(cheminComplet);
            Image signatureImage = new Image(imageData);

            // Positionner l'image (par exemple, bas de la première page)
            signatureImage.setFixedPosition(470, 305); // Position X, Y
            signatureImage.scaleAbsolute(50, 20);    // Dimension de l'image

            // Ajouter l'image à la première page
            document.add(signatureImage);

            byte[] pdfBytes = Files.readAllBytes(Paths.get(cheminFichier));
            log.info("💕💕💕Tableau de byte du fichier signé pour specimen : {}", pdfBytes);

            return Response.ok(pdfBytes, "Le fichier en byte après signature Specimen");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
