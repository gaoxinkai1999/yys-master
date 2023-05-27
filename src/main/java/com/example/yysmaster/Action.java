package com.example.yysmaster;

import com.example.yysmaster.Dm.DmConfig;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

public class Action {
    public String name;
    public Mat pic;
    public int click_x;
    public int click_y;
    public int click_w;
    public int click_h;
    public Point center;



    public Action(String name, String pic_path, int click_x, int click_y, int click_w, int click_h) {
        this.name = name;
        this.pic = Imgcodecs.imread(pic_path);
        this.click_x = click_x;
        this.click_y = click_y;
        this.click_w = click_w;
        this.click_h = click_h;
    }
}
