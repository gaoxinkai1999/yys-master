package com.example.yysmaster.Cv;

import com.example.yysmaster.Dm.DmSoft;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.SIFT;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Cv {

    @Autowired
    DmSoft dmSoft;


    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        new Cv().demo();
    }

    public java.awt.Point Temp_Match(String temp) {
//        Mat src = Imgcodecs.imread("src/main/resources/pic/111.png");//待匹配图片
//        Mat template = Imgcodecs.imread("src/main/resources/pic/" + temp);// 获取匹配模板
//        String property = System.getProperty("user.dir");
        Mat src = Imgcodecs.imread(dmSoft.getConfig().getPath() + "jietu.png");//待匹配图片
        Mat template = Imgcodecs.imread(dmSoft.getConfig().getPath() + temp);// 获取匹配模板

        /*
          TM_SQDIFF = 0, 平方差匹配法，最好的匹配为0，值越大匹配越差
          TM_SQDIFF_NORMED = 1,归一化平方差匹配法
          TM_CCORR = 2,相关匹配法，采用乘法操作，数值越大表明匹配越好
          TM_CCORR_NORMED = 3,归一化相关匹配法
          TM_CCOEFF = 4,相关系数匹配法，最好的匹配为1，-1表示最差的匹配
          TM_CCOEFF_NORMED = 5;归一化相关系数匹配法
         */
        int method = Imgproc.TM_CCOEFF_NORMED;

        int width = src.cols() - template.cols() + 1;
        int height = src.rows() - template.rows() + 1;
        // 创建32位模板匹配结果Mat
        Mat result = new Mat(width, height, CvType.CV_32FC1);

        /*
         * 将模板与重叠的图像区域进行比较。
         * @param image运行搜索的图像。 它必须是8位或32位浮点。
         * @param templ搜索的模板。 它必须不大于源图像并且具有相同的数据类型。
         * @param result比较结果图。 它必须是单通道32位浮点。 如果image是（W * H）并且templ是（w * h），则结果是（（W-w + 1）*（H-h + 1））。
         * @param方法用于指定比较方法的参数，请参阅默认情况下未设置的#TemplateMatchModes。
         * 当前，仅支持#TM_SQDIFF和#TM_CCORR_NORMED方法。
         */
        Imgproc.matchTemplate(src, template, result, method);

        // 归一化 详见https://blog.csdn.net/ren365880/article/details/103923813
//        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        // 获取模板匹配结果 minMaxLoc寻找矩阵(一维数组当作向量,用Mat定义) 中最小值和最大值的位置.
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

        // 绘制匹配到的结果 不同的参数对结果的定义不同
        double x, y;


        //最佳匹配度
        double good_score = mmr.maxVal;

//        //最佳匹配坐标
        x = mmr.maxLoc.x;
        y = mmr.maxLoc.y;
        int center_x = (int) (x + template.cols() * 0.5);
        int center_y = (int) (y + template.rows() * 0.5);

        src.release();
        template.release();
        result.release();
        if (good_score < 0.8) {
            return null;
        }

        return new java.awt.Point(center_x, center_y);
    }

    public java.awt.Point Sift_Match(String temp) {
        Mat resT = new Mat();
        Mat resO = new Mat();


        //即当detector 又当Detector
        SIFT sift = SIFT.create();

        Mat templateImage = Imgcodecs.imread(dmSoft.getConfig().getPath() + temp);

        Mat originalImage = Imgcodecs.imread(dmSoft.getConfig().getPath() + "jietu.png");

        MatOfKeyPoint templateKeyPoints = new MatOfKeyPoint();
        MatOfKeyPoint originalKeyPoints = new MatOfKeyPoint();

        //获取模板图的特征点
        sift.detect(templateImage, templateKeyPoints);
        sift.detect(originalImage, originalKeyPoints);


        sift.compute(templateImage, templateKeyPoints, resT);
        sift.compute(originalImage, originalKeyPoints, resO);

        List<MatOfDMatch> matches = new LinkedList();
        DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
//        System.out.println("寻找最佳匹配");

//        printPic("ptest", templateImage);
//        printPic("ptesO", originalImage);
//
//        printPic("test", resT);
//        printPic("tesO", resO);

        /**
         * knnMatch方法的作用就是在给定特征描述集合中寻找最佳匹配
         * 使用KNN-matching算法，令K=2，则每个match得到两个最接近的descriptor，然后计算最接近距离和次接近距离之间的比值，当比值大于既定值时，才作为最终match。
         */
        descriptorMatcher.knnMatch(resT, resO, matches, 2);
//        System.out.println("计算匹配结果");
        LinkedList<DMatch> goodMatchesList = new LinkedList();
        //对匹配结果进行筛选，依据distance进行筛选
        matches.forEach(match -> {
            DMatch[] dmatcharray = match.toArray();
            DMatch m1 = dmatcharray[0];
            DMatch m2 = dmatcharray[1];
            double nndrRatio = 0.7;
            if (m1.distance <= m2.distance * nndrRatio) {
                goodMatchesList.addLast(m1);
            }
        });

        int matchesPointCount = goodMatchesList.size();
        //当匹配后的特征点大于等于 4 个，则认为模板图在原图中，该值可以自行调整
        if (matchesPointCount >= 4) {
//            System.out.println("模板图在原图匹配成功！");

            List<KeyPoint> templateKeyPointList = templateKeyPoints.toList();
            List<KeyPoint> originalKeyPointList = originalKeyPoints.toList();
            LinkedList<org.opencv.core.Point> objectPoints = new LinkedList();
            LinkedList<org.opencv.core.Point> scenePoints = new LinkedList();
            goodMatchesList.forEach(goodMatch -> {
                objectPoints.addLast(templateKeyPointList.get(goodMatch.queryIdx).pt);
                scenePoints.addLast(originalKeyPointList.get(goodMatch.trainIdx).pt);
            });
            MatOfPoint2f objMatOfPoint2f = new MatOfPoint2f();
            objMatOfPoint2f.fromList(objectPoints);
            MatOfPoint2f scnMatOfPoint2f = new MatOfPoint2f();
            scnMatOfPoint2f.fromList(scenePoints);
            //使用 findHomography 寻找匹配上的关键点的变换
            Mat homography = Calib3d.findHomography(objMatOfPoint2f, scnMatOfPoint2f, Calib3d.RANSAC, 3);

            /**
             * 透视变换(Perspective Transformation)是将图片投影到一个新的视平面(Viewing Plane)，也称作投影映射(Projective Mapping)。
             */
            Mat templateCorners = new Mat(4, 1, CvType.CV_32FC2);
            Mat templateTransformResult = new Mat(4, 1, CvType.CV_32FC2);
            templateCorners.put(0, 0, new double[]{0, 0});
            templateCorners.put(1, 0, new double[]{templateImage.cols(), 0});
            templateCorners.put(2, 0, new double[]{templateImage.cols(), templateImage.rows()});
            templateCorners.put(3, 0, new double[]{0, templateImage.rows()});
            //使用 perspectiveTransform 将模板图进行透视变以矫正图象得到标准图片
            Core.perspectiveTransform(templateCorners, templateTransformResult, homography);

            //矩形四个顶点  匹配的图片经过旋转之后就这个矩形的四个点的位置就不是正常的abcd了
            double[] pointA = templateTransformResult.get(0, 0);
//            double[] pointB = templateTransformResult.get(1, 0);
            double[] pointC = templateTransformResult.get(2, 0);
//            double[] pointD = templateTransformResult.get(3, 0);

            //将匹配的图像用用四条线框出来
//            Imgproc.rectangle(originalImage, new org.opencv.core.Point(pointA), new org.opencv.core.Point(pointC), new Scalar(0, 255, 0));
//            Imgcodecs.imwrite("sift.png",originalImage);
            //该坐标为截图区域坐标
            java.awt.Point mid = new java.awt.Point((int) ((pointA[0] + pointC[0]) / 2), (int) ((pointA[1] + pointC[1]) / 2));
            //转化为窗口坐标
//            内存释放(new Mat[]{resO, resT, templateImage, originalImage, homography, templateCorners, templateTransformResult});
            templateKeyPoints.release();
            originalKeyPoints.release();
            templateCorners.release();
            templateTransformResult.release();
            resT.release();
            resO.release();
            templateImage.release();
            originalImage.release();
            homography.release();

            System.gc();

            return mid;
        } else {
            return null;
        }
    }


    public java.awt.Point WaitLive(String temp) {
        while (true) {
            dmSoft.CapturePng(0, 0, dmSoft.getConfig().getWidth(), dmSoft.getConfig().getHeight(), dmSoft.getConfig().getPath() + "jietu.png");
            java.awt.Point point = Temp_Match(temp);
            if (point != null) {
                return point;
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * 是否消失
     *
     * @param temp
     * @return
     */
    public boolean WaitDie(String temp) {

        dmSoft.CapturePng(0, 0, dmSoft.getConfig().getWidth(), dmSoft.getConfig().getHeight(), dmSoft.getConfig().getPath() + "jietu.png");
        java.awt.Point point = Temp_Match(temp);
        return point == null;


    }



    public void demo() {
        // 待匹配图片
        Mat templete = Imgcodecs.imread("C:\\Users\\g\\Desktop\\ceshi.png");
        // 模板
        Mat demo = Imgcodecs.imread("C:\\Users\\g\\Desktop\\shepi.png");

        int width = templete.cols() - demo.cols() + 1;
        int height = templete.rows() - demo.rows() + 1;

        // 模板匹配结果Mat要是32位的。
        Mat result = new Mat(width, height, CvType.CV_32FC1);
        // 模板匹配函数
        Imgproc.matchTemplate(templete, demo, result, Imgproc.TM_CCOEFF_NORMED);
        //  归一化
//        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        //遍历result结果,找相似结果通过Limit筛选。
        //因为我们用的是TM_CCOEFF_NORMED，所以结果越接近1标识匹配值越大，所以我们将匹配值大于0.9的区域框起来实现多目标匹配
        double Limit = 0.9;
        for (int i = 0; i < result.rows(); i++) {

            for (int j = 0; j < result.cols(); j++) {
                double matchValue = result.get(i, j)[0];
                if (matchValue > Limit) {    //绘制匹配到的结果
                    Imgproc.rectangle(templete, new Point(j, i), new Point(j + demo.cols(), i + demo.rows()), new Scalar(0, 0, 255), 2, Imgproc.LINE_AA);

                }
            }

        }


        /*以下寻找最佳匹配
         *MinMaxLocResult mmr = Core.minMaxLoc(result);
         * double x,y; if (method==Imgproc.TM_SQDIFF_NORMED ||
         * method==Imgproc.TM_SQDIFF) { x = mmr.minLoc.x; y = mmr.minLoc.y; } else { x =
         * mmr.maxLoc.x; y = mmr.maxLoc.y; }
         *绘制匹配到的结果
         *Imgproc.putText(templete,"Match Success",new Point(x,y),Imgproc.FONT_HERSHEY_SCRIPT_COMPLEX, 1.0, new Scalar(0, 255, 0),1,Imgproc.LINE_AA);
         */


        // 显示结果
        HighGui.imshow("模板匹配", templete);
        HighGui.waitKey(0);

    }

}


