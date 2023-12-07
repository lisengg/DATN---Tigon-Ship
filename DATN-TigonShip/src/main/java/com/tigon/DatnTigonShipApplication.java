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
public class DatnTigonShipApplication{

    public static void main(String[] args) {
        SpringApplication.run(DatnTigonShipApplication.class, args);
    }

}
