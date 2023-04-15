package com.example.zavrsnirad;

import com.example.zavrsnirad.config.RsaKeys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(RsaKeys.class)
class ZavrsniRadApplicationTests {

    @Test
    void contextLoads() {
    }

}
