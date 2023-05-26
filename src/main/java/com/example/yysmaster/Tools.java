package com.example.yysmaster;

import com.example.yysmaster.Cv.Cv;
import com.example.yysmaster.Dm.DmSoft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;

@Component
@SuppressWarnings("all")
public class Tools {
    @Autowired
    Cv cv;
    @Autowired
    Random random;
    @Autowired
    DmSoft dmSoft;


//    public void start() {
//
//        Action 点击挑战 = new Action("点击挑战", "tiaozhan.png", 972, 522, 80, 80);
////        Action 结算步骤一 = new Action("结算", "5.png", 6, 122, 200, 400);
//        Action 结算步骤二 = new Action("结算", "5.png", 951, 123, 170, 320);
//        Action 魂 = new Action("魂", "hun.png", 951, 123, 170, 320);
//
//        int i = 0;
//        while (true) {
//            i += 1;
//            System.out.println("**************");
//            System.out.println("执行第" + i + "次");
//
//
//            cv.WaitLive(点击挑战.path);
//            坐标随机偏移(点击挑战);
//            随机点击();
//            while (true) {
//                if (cv.WaitDie(点击挑战.path)) {
//                    System.out.println(点击挑战.name + "----执行成功");
//                    break;
//                } else {
//                    随机点击();
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//
//            cv.WaitLive(结算步骤二.path);
//            坐标随机偏移(结算步骤二);
//            随机点击();
//            while (true) {
//                if (cv.Temp_Match(魂.path)!=null) {
//                    System.out.println(结算步骤二.name + "----执行成功");
//                    dmSoft.CopyFile("jietu.png","result.png",0);
//                    随机点击();
//                    break;
//                } else {
//                    随机点击();
//                }
//            }
//
//        }
//    }
    public void start() {

        Action 点击挑战 = new Action("点击挑战", "tiaozhan.png", 972, 522, 80, 80);
//        Action 结算步骤一 = new Action("结算", "5.png", 6, 122, 200, 400);
        Action 结算步骤二 = new Action("结算", "5.png", 951, 123, 170, 320);
        Action 魂 = new Action("魂", "hun.png", 951, 123, 170, 320);
        ArrayList<Action> actions = new ArrayList<>();
//        actions.add(点击挑战);
//        actions.add(结算步骤一);
        actions.add(结算步骤二);
        actions.add(魂);
        int i = 0;
        while (true) {
            i += 1;
            System.out.println("**************");
            System.out.println("执行第" + i + "次");

            for (Action action : actions) {
                cv.WaitLive(action.path);
                坐标随机偏移(action);
                随机点击();
                while (true) {
                    if (cv.WaitDie(action.path)) {
                        System.out.println(action.name + "----执行成功");
                        break;
                    } else {
                        随机点击();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        }
    }

    public void 坐标随机偏移(Action action) {
        dmSoft.MoveToEx(action.click_x, action.click_y, action.click_w, action.click_h);
    }

    public void 随机点击() {
        int num = random.getNum(1, 2);
        for (int i = 0; i < num; i++) {
            dmSoft.LeftClick();
            try {
                Thread.sleep(random.getNum(150, 250));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
