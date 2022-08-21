package com.canhlabs.shorten.service.impl;

import com.canhlabs.shorten.service.QRCodeGenerator;
import com.canhlabs.shorten.share.exception.CustomException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
public class QRCodeGeneratorImpl implements QRCodeGenerator {
    @Override
    public void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height,
                com.google.common.collect.ImmutableMap.of(com.google.zxing.EncodeHintType.MARGIN, 0));

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    @Override
    public byte[] getQRCodeImage(String text, int width, int height) {
        try {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height,
                    com.google.common.collect.ImmutableMap.of(com.google.zxing.EncodeHintType.MARGIN, 0));

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFFFFC041);

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
            return pngOutputStream.toByteArray();

        } catch (WriterException | IOException we) {
            throw CustomException.builder()
                    .message("Error get qr code")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
