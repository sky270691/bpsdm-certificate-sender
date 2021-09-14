package com.psikku.certificatemailsender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MailSenderService {

    private final String CERTIFICATE_BASE_URL = "http://pio.psikku.com:8084/app-sertifikat/20210315001/";
    private static int ROW_COUNTER = 0;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ResourceLoader resourceLoader;

    @Scheduled(fixedDelay = 60 * 1000)
    public void sendEmail() throws IOException {
        Map<String,String> certEmailMap = getCertDataFromFile();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(certEmailMap.get("email"));
        mailMessage.setSubject("SERTIFIKAT TEST OLEH BPSDM SULAWESI UTARA");
        StringBuilder sb = new StringBuilder();
        sb.append("Silahkan klik link berikut untuk dapat mendownload sertifikat:");
        sb.append("\n");
        sb.append("\n");
        sb.append(CERTIFICATE_BASE_URL);
        sb.append(certEmailMap.get("certificate"));
        mailMessage.setText(new String(sb));
        try {
            javaMailSender.send(mailMessage);
            System.out.println("ROW COUNTER: "+ROW_COUNTER);
            System.out.println("email sent to: "+certEmailMap.get("email"));
            ROW_COUNTER +=1;
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    private Map<String,String> getCertDataFromFile() throws IOException {

        Map<String,String> emailCert = new LinkedHashMap<>();
        Resource resource = resourceLoader.getResource("classpath:email3.csv");
        List<String> certEmailList =
                Files.lines(Paths.get(resource.getURI())).collect(Collectors.toList());
        String cert = certEmailList.get(ROW_COUNTER).split(",")[0];
        String email = certEmailList.get(ROW_COUNTER).split(",")[1];
        emailCert.put("certificate",cert);
        emailCert.put("email",email);
        return emailCert;
    }

}
