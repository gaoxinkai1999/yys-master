package com.example.yysmaster;

import com.example.yysmaster.Cv.Cv;
import com.example.yysmaster.Dm.DmConfig;
import com.example.yysmaster.Dm.DmSoft;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("all")
class YysMasterApplicationTests {
    @Autowired
    DmConfig config;

    @Autowired
    DmSoft dmSoft;

    @Autowired
    Random random;
    @Autowired
    Cv cv;
    @Autowired
    Tools tools;
    @Value("${副本}")
    String 副本选择;
    @Autowired
    副本 副本;

    @Test
    void contextLoads() {

        System.out.println(副本选择);
        switch (副本选择) {
            case "魂土司机":
                tools.start(副本.魂土司机());
                break;
            case "魂土打手":
                tools.start(副本.魂土打手());
                break;
            case "单人副本":
                tools.start(副本.单人副本());
                break;
            case "活动":
                tools.start(副本.活动());
                break;
        }

    }


}
