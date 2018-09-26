package com.example.project.lib;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.var;

import org.apache.commons.codec.binary.Base64;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QRCodeGenerator {

    public static String generateQRCodeImage(@NonNull String text, int width, int height)
            throws WriterException, IOException {

        var qrCodeWriter = new QRCodeWriter();

        var bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        var img = MatrixToImageWriter.toBufferedImage(bitMatrix);

        @Cleanup var arrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img,"png",arrayOutputStream);
        var b64 = Base64.encodeBase64(arrayOutputStream.toByteArray());

        return new String(b64);
    }

    public static String decodeQRCode(@NonNull String b64) {
        try {
            var decode = Base64.decodeBase64(b64);
            @Cleanup var byteArrayInputStream = new ByteArrayInputStream(decode);
            var image = ImageIO.read(byteArrayInputStream);

            var source = new BufferedImageLuminanceSource(image);
            var bitmap = new BinaryBitmap(new HybridBinarizer(source));

            var result = new MultiFormatReader().decode(bitmap);
            return result.getText();

        } catch (Exception e) {
            e.printStackTrace();
            return "Not Found";
        }
    }
}
