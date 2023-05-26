package com.example.yysmaster.Dm;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "game")
@Data

public class DmConfig {


    private String license;
    private int height;
    private int width;
    private String title;
    private int hwnd;
    //全局资源路径
    private String path;
    /**
     * 大漠插件执行组件
     */
    private ActiveXComponent dm = new ActiveXComponent("dm.dmsoft");



//
//    public void version() {
//        System.out.println("大漠插件版本：" + dm.invoke("Ver").getString());
//    }


    public void register() {
        int success = Dispatch.call(dm, "Reg", license, "").getInt();
        System.out.println("注册状态码:" + success);
        System.out.println((success == 1 ? "注册成功" : "注册失败"));

    }


    public ActiveXComponent getDm() {
        return dm;
    }


}
