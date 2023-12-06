package com.tigon.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

@Service
public interface GenerateQRService {
	 public static void generateQRCode(String data, String filePath, int width, int height) {
	        try {
	            QRCodeWriter qrCodeWriter = new QRCodeWriter();
	            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

	            Path path = FileSystems.getDefault().getPath(filePath);
	            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) {
	        // Thay thế "Hello, World!" bằng chuỗi bạn muốn tạo QR code
	        String data = "Hello, World!";

	        // Thay đổi "/images/qr/myqrcode.png" thành đường dẫn bạn muốn lưu mã QR
	        String filePath = "/images/qr/myqrcode.png";

	        // Kích thước của mã QR (có thể điều chỉnh)
	        int width = 300;
	        int height = 300;

	        // Gọi phương thức để tạo và lưu mã QR
	        generateQRCode(data, filePath, width, height);
	    }
}