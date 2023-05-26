package com.example.yysmaster;

import java.awt.*;

public class Action {
    public String name;
    public String path;
    public int click_x;
    public int click_y;
    public int click_w;
    public int click_h;
    public Point center;

    public Action(String name, String path, int click_x, int click_y, int click_w, int click_h) {
        this.name = name;
        this.path = path;
        this.click_x = click_x;
        this.click_y = click_y;
        this.click_w = click_w;
        this.click_h = click_h;
    }
}
