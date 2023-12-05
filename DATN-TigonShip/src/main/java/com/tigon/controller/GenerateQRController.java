package com.tigon.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
@RestController
public class GenerateQRController {
	@GetMapping(value = "generateQRCode")
	public ResponseEntity<byte[]> generateQRCode(@RequestParam String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);

     // Lưu hình ảnh vào thư mục templates/images/qr
        String fileName = "qr/" + text + "_qrcode.png";
        Resource resource = new ClassPathResource(fileName);

        File file = resource.getFile();
        Files.write(file.toPath(), byteArrayOutputStream.toByteArray(), StandardOpenOption.CREATE);


        // Trả về hình ảnh như là một phản hồi từ server
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(byteArrayOutputStream.toByteArray());
    }
}
