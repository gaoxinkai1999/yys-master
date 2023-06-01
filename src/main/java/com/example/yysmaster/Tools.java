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


    public void startPlus() {


        ArrayList<掉落> 掉落s = new ArrayList<>();



        Action 点击挑战 = new Action("点击挑战", dmSoft.GetPath()+ "tiaozhan.png", 972, 522, 80, 80);
//        Action 结算步骤一 = new Action("结算", "5.png", 6, 122, 200, 400);
        Action 结算步骤二 = new Action("结算", dmSoft.GetPath()+ "5.png", 951, 123, 170, 320);
        Action 掉落统计 = new Action("掉落统计", dmSoft.GetPath()+ "jinbi.png", 951, 123, 170, 320);
        ArrayList<Action> actions = new ArrayList<>();
//        actions.add(点击挑战);
//        actions.add(结算步骤一);
        actions.add(结算步骤二);

        int i = 0;
        int end=50;
        while (i<end) {
            i += 1;
            System.out.println("**************");
            System.out.println("执行第" + i + "次");

            for (Action action : actions) {
                cv.WaitLive(action.pic);
                坐标随机偏移(action);
                随机点击();
                while (true) {
                    if (cv.Temp_Match(掉落统计.pic)!=null){
                        dmSoft.CopyFile("jietu.png","result.png",0);
                        System.out.println("检测到掉落");
                        随机点击();
                        break;
                    }
                    if (cv.WaitDie(action.pic)) {
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
    public void start(ArrayList<Action> actions) {


        int i = 0;
        int end=120;
        while (i<end) {
            i += 1;
            System.out.println("**************");
            System.out.println("执行第" + i + "次");

            for (Action action : actions) {
                cv.WaitLive(action.pic);
                坐标随机偏移(action);
                随机点击();
                while (true) {
                    if (cv.WaitDie(action.pic)) {
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
