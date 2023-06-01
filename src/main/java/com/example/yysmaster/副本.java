package com.example.yysmaster;

import com.example.yysmaster.Dm.DmSoft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@SuppressWarnings("all")
public class 副本 {
    @Autowired
    DmSoft dmSoft;
    public ArrayList<Action> 魂土司机(){
        ArrayList<Action> actions = new ArrayList<>();
        Action 点击挑战 = new Action("点击挑战", dmSoft.GetPath()+ "tiaozhan-2.png", 1060, 550, 45, 45);
        Action 结算 = new Action("结算", dmSoft.GetPath()+ "5.png", 951, 123, 170, 320);
        actions.add(点击挑战);
        actions.add(结算);
        return actions;
    }
    public ArrayList<Action> 魂土打手(){
        ArrayList<Action> actions = new ArrayList<>();

        Action 结算 = new Action("结算", dmSoft.GetPath()+ "5.png", 951, 123, 170, 320);
        actions.add(结算);
        return actions;
    }

    public ArrayList<Action> 单人副本(){
        ArrayList<Action> actions = new ArrayList<>();
        Action 点击挑战 = new Action("点击挑战", dmSoft.GetPath()+ "tiaozhan.png", 980, 530, 65, 65);
        Action 结算 = new Action("结算", dmSoft.GetPath()+ "5.png", 951, 123, 170, 320);
       actions.add(点击挑战);
        actions.add(结算);
        return actions;
    }
    public ArrayList<Action> 活动(){
        ArrayList<Action> actions = new ArrayList<>();
        Action 点击挑战 = new Action("点击挑战", dmSoft.GetPath()+ "huodongtiaozhan.png", 1028, 539, 50, 50);
        Action 结算 = new Action("结算", dmSoft.GetPath()+ "5.png", 960, 60, 130, 420);
       actions.add(点击挑战);
        actions.add(结算);
        return actions;
    }
}
