package com.example.yysmaster.Dm;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@Data
public class DmSoft {


    public DmSoft(@Autowired DmConfig config) {
        this.config = config;
        //注册
        config.register();
        //获取句柄
        config.setHwnd(FindWindow("", config.getTitle()));
        //绑定窗口
//        BindWindow(config.getHwnd(), "gdi", "normal", "windows", 0);
        BindWindow(config.getHwnd(), "gdi", "dx2", "windows", 0);
//        SetWindowState(config.getHwnd(), 11);
        SetPath(config.getPath());
        //设置分辨率
        SetClientSize(config.getHwnd(), config.getWidth(), config.getHeight());
//            开启模拟鼠标真实点击
//        EnableMouseSync();
        EnableRealMouse();
    }

    private DmConfig config;

    //左
    private final Point left = new Point(91, 382);
    //右
    private final Point right = new Point(1000, 382);
    //上
    private final Point up = new Point(564, 80);
    //下
    Point down = new Point(564, 500);

    public int BindWindow(int hwnd, String display, String mouse, String keypad, int mode) {

        return send("BindWindow", hwnd, display, mouse, keypad, mode).getInt();
    }

    public int FindWindow(String classname, String title) {
        return send("FindWindow", classname, title).getInt();
    }

    public int FindWindowByProcessId(int process_id, String class_, String title) {

        return send("FindWindowByProcessId", process_id, class_, title).getInt();
    }


    public void CopyFile(String src_file,String dst_file,int over){
         send("CopyFile", src_file, dst_file, over);
    }
    public int[] GetClientSize(int hwnd) {

        Variant width = new Variant(0, true);
        Variant hight = new Variant(0, true);

        send("GetClientSize", hwnd, width, hight);
        return new int[]{width.getInt(), hight.getInt()};

    }

    /**
     * 把鼠标移动到目的点(x,y)
     *
     * @return
     */
    public int MoveTo(int x, int y) {
        return send("MoveTo", x, y).getInt();
    }

    public int SetClientSize(int hwnd, int width, int height) {
        return send("SetClientSize", hwnd, width, height).getInt();
    }

    /**
     * 按下鼠标左键
     *
     * @return
     */
    public int LeftClick() {
        return send("LeftClick").getInt();
    }

    /**
     * 按住鼠标左键
     *
     * @return
     */
    public int LeftDown() {
        return send("LeftDown").getInt();
    }

    /**
     * 弹起鼠标左键
     */
    public int LeftUp() {
        return send("LeftUp").getInt();
    }

    /**
     * 鼠标拖动
     */
    public void Drag(Point start, Point end) {
        EnableRealMouse();
        MoveTo(start.x, start.y);
        LeftDown();
        MoveTo(end.x, end.y);
        LeftUp();
    }

    public void Drag_left() {
        Drag(left, right);
    }

    public void Drag_right() {
        Drag(right, left);
    }

    public void Drag_up() {
        Drag(up, down);
    }

    public void Drag_down() {
        Drag(down, up);
    }

    public int EnableRealMouse() {
        return send("EnableRealMouse", 2, 10, 30).getInt();
    }

    public int Delay(int mis) {
        return send("Delay", mis).getInt();
    }

    public int SetWindowState(int hwnd, int flag) {
        return send("SetWindowState", hwnd, flag).getInt();
    }

    /**
     * 鼠标消息采用同步发送模式.默认异步
     *
     * 注: 此接口必须在绑定之后才能调用。
     *
     * 有些时候，如果是异步发送，如果发送动作太快,中间没有延时,有可能下个动作会影响前面的.
     *
     * 而用同步就没有这个担心.
     */
    public void EnableMouseSync(){
        send("EnableMouseSync", 1,500);
    }

    public String MoveToEx(int x,int y,int w,int h){
        return send("MoveToEx", x, y, w, h).getString();
    }
    public void MoveAndClick(int x, int y) {
        MoveTo(x, y);
        LeftClick();
        Delay(500);
    }

    /**
     * 按住指定的虚拟键码
     *
     * @param key_str
     * @return 示例:
     * <p>
     * dm.KeyDownChar "enter"
     */
    public int KeyDownChar(String key_str) {
        return send("KeyDownChar", key_str).getInt();
    }

    /**
     * 弹起来虚拟键key_str
     *
     * @param key_str
     * @return
     */
    public int KeyUpChar(String key_str) {
        return send("KeyUpChar", key_str).getInt();
    }

    /**
     * 按下指定的虚拟键码
     *
     * @param key_str
     * @return
     */
    public int KeyPressChar(String key_str) {
        return send("KeyPressChar", key_str).getInt();
    }

    /**
     * 根据指定的字符串序列，依次按顺序按下其中的字符.
     *
     * @param key_str 需要按下的字符串序列. 比如"1234","abcd","7389,1462"等.
     * @param delay   每按下一个按键，需要延时多久. 单位毫秒.这个值越大，按的速度越慢。
     */
    public int KeyPressStr(String key_str, int delay) {
        return send("KeyPressStr", key_str, delay).getInt();
    }


    public int WheelDown() {
        return send("WheelDown").getInt();
    }


    /**
     * 设置全局路径,设置了此路径后,所有接口调用中,相关的文件都相对于此路径. 比如图片,字库等.
     *
     * @param path
     * @return
     */
    public int SetPath(String path) {
        return send("SetPath", path).getInt();

    }

    public int CapturePng(int x1, int y1, int x2, int y2, String filename) {
        return send("CapturePng", x1, y1, x2, y2, filename).getInt();
    }

    public int CapturePng() {
        return send("CapturePng", 0, 0, 1560, 878, "jietu.png").getInt();
    }

    public int ImageToBmp(String pic_name, String bmp_name) {
        return send("ImageToBmp", pic_name, bmp_name).getInt();

    }

    /**
     * 抓取指定区域(x1, y1, x2, y2)的图像,保存为file(24位位图)
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param pic_name
     * @return
     */
    public int Capture(int x1, int y1, int x2, int y2, String pic_name) {
        return send("Capture", x1, y1, x2, y2, pic_name).getInt();


    }

    public int[] FindPicS(int x1, int y1, int x2, int y2, String pic_name) {
        Variant x = new Variant(0, true);
        Variant y = new Variant(0, true);
        send("FindPicS", x1, y1, x2, y2, pic_name, "000000", 0.5, 0, x, y);

        return new int[]{x.getInt(), y.getInt()};

    }


    /**
     * 获取全局路径.(可用于调试)
     */
    public String GetPath() {
        return send("GetPath").getString();
    }

    public String GetBasePath() {
        return send("GetBasePath").getString();
    }

    //调用dll，解决变参指针问题
    private Variant send(String method, Object... attributes) {
        int length = attributes.length;
        Variant[] variants = new Variant[length];
        for (int i = 0; i < length; i++) {
            if (attributes[i] instanceof Variant) {
                variants[i] = (Variant) attributes[i];
            } else {
                variants[i] = new Variant(attributes[i], true);
            }

        }
        return Dispatch.call(config.getDm(), method, variants);
    }
}
