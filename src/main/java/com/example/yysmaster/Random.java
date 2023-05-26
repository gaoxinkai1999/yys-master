package com.example.yysmaster;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class Random {

    public int getNum(int min,int max){




        try {
            SecureRandom instanceStrong = SecureRandom.getInstanceStrong();
        return min+instanceStrong.nextInt(max-min+1);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
//        return     min + (int)(Math.random() * (max-min+1));
    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(random.getNum(1, 5));

        }
    }
}
