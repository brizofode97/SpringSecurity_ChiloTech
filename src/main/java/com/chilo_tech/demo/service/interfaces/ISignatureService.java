package com.chilo_tech.demo.service.interfaces;

import com.chilo_tech.demo.common.utility.Response;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface ISignatureService {

    Response<String> signatureParQrCode(String codePin, byte[] fichierASigne, int numeroEnrolement, int workerId, int positionX, int positionY) throws WriterException, IOException;
    Response<byte[]> signatureParSpecimen(String codePin, byte[] fichierASigne, int idSigner, int workerId);

}
