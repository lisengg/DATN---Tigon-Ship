package com.tigon.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.apache.http.HttpEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

@Service
public class GenerateQRService {
	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));	
	public MultipartFile generateQRCodeAsMultipartFile(String data,String mahoadon) {
        try {
        	int width = 200;
        	int height = 200;
        	String filename = "mahoadon"+mahoadon;
            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
            byte[] qrCodeData = matrixToByteArray(bitMatrix, "PNG");

            ByteArrayResource resource = new ByteArrayResource(qrCodeData);
            return new CustomMultipartFile(resource, filename + ".png");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] matrixToByteArray(BitMatrix matrix, String format) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, format, outputStream);
        return outputStream.toByteArray();
    }

    private static class CustomMultipartFile implements MultipartFile {

        private final ByteArrayResource resource;
        private final String filename;

        public CustomMultipartFile(ByteArrayResource resource, String filename) {
            this.resource = resource;
            this.filename = filename;
        }

        @Override
        public String getName() {
            return filename;
        }

        @Override
        public String getOriginalFilename() {
            return filename;
        }

        @Override
        public String getContentType() {
            return MediaType.IMAGE_PNG_VALUE;
        }

        @Override
        public long getSize() {
            return resource.contentLength();
        }

        @Override
        public byte[] getBytes() throws IOException {
            return resource.getByteArray();
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return resource.getInputStream();
        }

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void transferTo(File dest) throws IOException, IllegalStateException {
			// TODO Auto-generated method stub
			
		}
    }
	public void generateQRCode(String data,String mahoadon) throws IOException {
		MultipartFile qrCodeFile = generateQRCodeAsMultipartFile(data,mahoadon);

		Path staticPath = Paths.get("src/main/resources/static");
		Path imagePath = Paths.get("images");
		Path qrPath = Paths.get("qr");
		if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(qrPath))) {
			Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
		}
		Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(qrPath)
				.resolve(qrCodeFile.getOriginalFilename());
		try (OutputStream os = Files.newOutputStream(file)) {
			os.write(qrCodeFile.getBytes());
		}
	}
}