package com.tigon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class DatnTigonShipApplication{

    public static void main(String[] args) {
        SpringApplication.run(DatnTigonShipApplication.class, args);
    }
    
}
