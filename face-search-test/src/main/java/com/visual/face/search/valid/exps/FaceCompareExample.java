package com.visual.face.search.valid.exps;

import com.visual.face.search.FaceCompare;
import com.visual.face.search.model.Compare;
import com.visual.face.search.model.CompareRep;
import com.visual.face.search.model.FaceLocation;
import com.visual.face.search.model.Response;
import com.visual.face.search.utils.Base64Util;
import com.visual.face.search.valid.utils.DrawImage;
import com.visual.face.search.valid.utils.MatUtil;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import java.awt.*;
import java.io.File;
import java.util.Objects;

public class FaceCompareExample {

    static{ nu.pattern.OpenCV.loadShared(); }
    //本地开发模式
    public static String serverHost = "http://127.0.0.1:8080";
    //docker部署模式
    //public static String serverHost = "http://127.0.0.1:56789";
    //远程测试服务
    //public static String serverHost = "http://face-search.diven.nat300.top";

    public static FaceCompare faceSearch = FaceCompare.build(serverHost);

    /**搜索*/
    public static void compare() {
        String searchPath = "face-search-test/src/main/resources/image/compare";
        File [] files = Objects.requireNonNull(new File(searchPath).listFiles());
        for(int i=0; i< files.length-1; i++){
            for(int j=i+1; j< files.length; j++){
                File imageA = files[i];
                File imageB = files[j];
                String imageABase64 = Base64Util.encode(imageA.getAbsolutePath());
                String imageBBase64 = Base64Util.encode(imageB.getAbsolutePath());

                Response<CompareRep> compareRepResponse = faceSearch.compare().faceCompare(Compare.build()
                        .setImageBase64A(imageABase64)
                        .setImageBase64B(imageBBase64)
                        .setFaceScoreThreshold(0f)
                        .setNeedFaceInfo(true)
                );

                int distance = compareRepResponse.getData().getDistance().intValue();
                int confidence = compareRepResponse.getData().getConfidence().intValue();

                FaceLocation locationA = compareRepResponse.getData().getFaceInfo().getLocationA();
                FaceLocation locationB = compareRepResponse.getData().getFaceInfo().getLocationB();
                Mat image1 = DrawImage.build(imageA.getAbsolutePath()).drawRect(new DrawImage.Rect(locationA.getX(), locationA.getY(), locationA.getW(), locationA.getH()), 2, Color.RED).toMat();
                Mat image2 = DrawImage.build(imageB.getAbsolutePath()).drawRect(new DrawImage.Rect(locationB.getX(), locationB.getY(), locationB.getW(), locationB.getH()), 2, Color.RED).toMat();
                DrawImage drawImage = DrawImage.build(MatUtil.matToBufferedImage(MatUtil.concat(image1, image2)));

                int cx = (locationA.getX() + locationB.getX() + image1.width()) / 2 ;
                int cy = (locationA.getY() + locationB.getY()) / 2;
                drawImage.drawText("人脸比对结果", new DrawImage.Point(cx+5, cy+25), 14, Color.RED);
                drawImage.drawText("分数：" + confidence, new DrawImage.Point(cx+5, cy+50), 14, Color.RED);
                drawImage.drawText("距离：" + distance, new DrawImage.Point(cx+5, cy+75), 14, Color.RED);
                HighGui.imshow("image", drawImage.toMat());
                HighGui.waitKey();
            }
        }
        //退出程序
        System.exit(1);
    }

    /**main**/
    public static void main(String[] args) {
        compare();
    }

}
