package com.psikku.certificatemailsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CertificateMailSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CertificateMailSenderApplication.class, args);
    }

}
