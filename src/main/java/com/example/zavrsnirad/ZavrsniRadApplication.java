package com.example.zavrsnirad;

import com.example.zavrsnirad.config.RsaKeys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeys.class)
@SpringBootApplication
public class ZavrsniRadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZavrsniRadApplication.class, args);
    }

}
