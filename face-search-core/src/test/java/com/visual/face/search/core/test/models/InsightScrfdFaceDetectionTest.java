package com.visual.face.search.core.test.models;

import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.models.InsightScrfdFaceDetection;
import com.visual.face.search.core.test.base.BaseTest;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.Map;

public class InsightScrfdFaceDetectionTest extends BaseTest {
    private static String modelPath = "face-search-core/src/main/resources/model/onnx/detection_face_scrfd/scrfd_500m_bnkps.onnx";

    private static String imagePath = "face-search-core/src/test/resources/images/faces";
//    private static String imagePath = "face-search-core/src/test/resources/images/faces/rotate";
//    private static String imagePath = "face-search-core/src/test/resources/images/faces/debug";


    public static void main(String[] args) {
        Map<String, String> map = getImagePathMap(imagePath);
        InsightScrfdFaceDetection infer = new InsightScrfdFaceDetection(modelPath, 2);

        for(String fileName : map.keySet()){
            String imageFilePath = map.get(fileName);
            System.out.println(imageFilePath);
            Mat image = Imgcodecs.imread(imageFilePath);
            long s = System.currentTimeMillis();
            List<FaceInfo> faceInfos = infer.inference(ImageMat.fromCVMat(image), 0.5f, 0.7f, null);
            long e = System.currentTimeMillis();
            if(faceInfos.size() > 0){
                System.out.println("fileName="+fileName+",\tcost="+(e-s)+",\t"+faceInfos.get(0).score);
            }else{
                System.out.println("fileName="+fileName+",\tcost="+(e-s)+",\t"+faceInfos);
            }

            for(FaceInfo faceInfo : faceInfos){
                Imgproc.rectangle(image, new Point(faceInfo.box.x1(), faceInfo.box.y1()), new Point(faceInfo.box.x2(), faceInfo.box.y2()), new Scalar(0,0,255));
                int pointNum = 1;
                for(FaceInfo.Point keyPoint : faceInfo.points){
                    Imgproc.circle(image, new Point(keyPoint.x, keyPoint.y), 3, new Scalar(0,0,255), -1);
                    Imgproc.putText(image, String.valueOf(pointNum), new Point(keyPoint.x+1, keyPoint.y), Imgproc.FONT_HERSHEY_PLAIN, 1, new Scalar(255,0,0));
                    pointNum ++ ;
                }
            }
            HighGui.imshow(fileName, image);
            HighGui.waitKey();
        }
        System.exit(1);
    }

}
