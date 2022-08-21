package com.canhlabs.shorten.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRCodeGenerator {

    void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException;

    byte[] getQRCodeImage(String text, int width, int height);
}