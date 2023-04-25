package com.visual.face.search.core.test.models;

import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.models.InsightCoordFaceKeyPoint;
import com.visual.face.search.core.test.base.BaseTest;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.util.Map;


public class InsightCoordFaceKeyPointTest extends BaseTest {

    private static String modelPath = "face-search-core/src/main/resources/model/onnx/keypoint_coordinate/coordinate_106_mobilenet_05.onnx";

    private static String imagePath = "face-search-core/src/test/resources/images/faces";
//    private static String imagePath = "face-search-core/src/test/resources/images/faces/debug";
//    private static String imagePath = "face-search-core/src/test/resources/images/faces/rotate";


    public static void main(String[] args) {
        Map<String, String> map = getImagePathMap(imagePath);
        InsightCoordFaceKeyPoint infer = new InsightCoordFaceKeyPoint(modelPath, 2);

        for(String fileName : map.keySet()){
            String imageFilePath = map.get(fileName);
            System.out.println(imageFilePath);
            Mat image = Imgcodecs.imread(imageFilePath);
            long s = System.currentTimeMillis();
            FaceInfo.Points points = infer.inference(ImageMat.fromCVMat(image), null);
            long e = System.currentTimeMillis();
            System.out.println("fileName="+fileName+",\tcost="+(e-s)+",\t"+points);
            int pointNum = 1;
            for(FaceInfo.Point keyPoint : points){
                Imgproc.circle(image, new Point(keyPoint.x, keyPoint.y), 1, new Scalar(0,0,255), -1);
                Imgproc.putText(image, String.valueOf(pointNum), new Point(keyPoint.x+1, keyPoint.y), Imgproc.FONT_HERSHEY_PLAIN, 1, new Scalar(255,0,0));
                pointNum ++ ;
                System.out.println("["+keyPoint.x+"," +keyPoint.y+"],");
            }
            HighGui.imshow(fileName, image);
            HighGui.waitKey();
        }
        System.exit(1);
    }
}
