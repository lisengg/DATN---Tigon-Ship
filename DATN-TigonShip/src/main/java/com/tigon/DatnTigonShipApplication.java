package com.tigon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.tigon.dao.OTPDAO;

@SpringBootApplication
@EnableScheduling
public class DatnTigonShipApplication implements CommandLineRunner {
//
//    private final OTPDAO otpDAO;
//
//    @Autowired
//    public DatnTigonShipApplication(OTPDAO otpDAO) {
//        this.otpDAO = otpDAO;
//    }
//
//    @Scheduled(initialDelay = 300000,fixedRate = 300000) // Chạy sau mỗi 5 phút
//    public void deleteAllOTP() {
//        otpDAO.deleteAll();
//     // Lấy giờ hiện tại
//        LocalDateTime currentTime = LocalDateTime.now();
//
//        // Định dạng giờ
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
//        String formattedTime = currentTime.format(formatter);
//        System.out.println("Xóa tất cả OTP lúc : "+formattedTime);
//    }

    public static void main(String[] args) {
        SpringApplication.run(DatnTigonShipApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Các công việc khởi chạy khác có thể được thực hiện ở đây nếu cần
    }
}
