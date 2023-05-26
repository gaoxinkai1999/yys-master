package com.example.yysmaster;

import com.example.yysmaster.Cv.Cv;
import com.example.yysmaster.Dm.DmConfig;
import com.example.yysmaster.Dm.DmSoft;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Test
    void contextLoads() {

//        Action 点击挑战 = new Action("点击挑战", "tiaozhan.png", 972, 522, 80, 80);
//        Action 点击挑战二 = new Action("点击挑战", "tiaozhan-2.png", 1059, 548, 45, 45);
//        Action 结算步骤一 = new Action("结算", "5.png", 6, 122, 200, 400);
//        Action 结算步骤二 = new Action("结算", "5.png", 951, 123, 170, 320);
//
//        ArrayList<Action> actions = new ArrayList<>();
//        actions.add(点击挑战);
//        actions.add(点击挑战二);
//        actions.add(结算步骤一);
//        actions.add(结算步骤二);
        tools.start();

        //左 6  122   200  400
        //右 951 123  170   320

        //突破 14  体力145235


    }


}
