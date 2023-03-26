package it.beata.ordermanager.qrcode.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class QrCodeService {

    public BufferedImage generateQRCodeImage(Object object) throws Exception {
        ObjectWriter objectWriter = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writer().withDefaultPrettyPrinter();
        String text = objectWriter.writeValueAsString(object);
        BufferedImage result = generateQRCodeImage(text);
        return result;
    }

    private BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public Object decodeQRCodeImage (BufferedImage bufferedImage, Class toClass) throws Exception {
        String result = decodeQRCodeImage(bufferedImage);
        ObjectReader objectReader = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).reader();
        Object object = objectReader.readValue(result, toClass);
        return object;
    }

    private String decodeQRCodeImage (BufferedImage bufferedImage) throws NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }
}
