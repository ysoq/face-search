package com.visual.face.search.core.test.models;

import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.models.PcnNetworkFaceDetection;
import com.visual.face.search.core.test.base.BaseTest;
import com.visual.face.search.core.utils.CropUtil;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.Map;

public class PcnNetworkFaceDetectionTest extends BaseTest {

    private static String model1Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn1_sd.onnx";
    private static String model2Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn2_sd.onnx";
    private static String model3Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn3_sd.onnx";

    private static String imagePath = "face-search-core/src/test/resources/images/faces";
//    private static String imagePath = "face-search-core/src/test/resources/images/faces/rotate";
//    private static String imagePath = "face-search-core/src/test/resources/images/faces/debug";


    public static void main(String[] args) {
        Map<String, String> map = getImagePathMap(imagePath);
        PcnNetworkFaceDetection infer = new PcnNetworkFaceDetection(new String[]{model1Path, model2Path, model3Path}, 2);
        for(String fileName : map.keySet()){
            String imageFilePath = map.get(fileName);
            System.out.println(imageFilePath);
            Mat image = Imgcodecs.imread(imageFilePath);
            long s = System.currentTimeMillis();
            List<FaceInfo> faceInfos = infer.inference(ImageMat.fromCVMat(image), PcnNetworkFaceDetection.defScoreTh, PcnNetworkFaceDetection.defIouTh, null);
            long e = System.currentTimeMillis();
            if(faceInfos.size() > 0){
                System.out.println("fileName="+fileName+",\tcost="+(e-s)+",\t"+faceInfos.get(0).score);
            }else{
                System.out.println("fileName="+fileName+",\tcost="+(e-s)+",\t"+faceInfos);
            }


            for(FaceInfo faceInfo : faceInfos){
                FaceInfo.FaceBox box = faceInfo.rotateFaceBox().scaling(1.0f);
                Imgproc.line(image, new Point(box.leftTop.x, box.leftTop.y), new Point(box.rightTop.x, box.rightTop.y), new Scalar(0,0,255), 1);
                Imgproc.line(image, new Point(box.rightTop.x, box.rightTop.y), new Point(box.rightBottom.x, box.rightBottom.y), new Scalar(255,0,0), 1);
                Imgproc.line(image, new Point(box.rightBottom.x, box.rightBottom.y), new Point(box.leftBottom.x, box.leftBottom.y), new Scalar(255,0,0), 1);
                Imgproc.line(image, new Point(box.leftBottom.x, box.leftBottom.y), new Point(box.leftTop.x, box.leftTop.y), new Scalar(255,0,0), 1);
                Imgproc.putText(image, String.valueOf(faceInfo.angle), new Point(box.leftTop.x, box.leftTop.y), Imgproc.FONT_HERSHEY_PLAIN, 1, new Scalar(0,0,255));
//                Imgproc.rectangle(image, new Point(faceInfo.box.x1(), faceInfo.box.y1()), new Point(faceInfo.box.x2(), faceInfo.box.y2()), new Scalar(255,0,255));

                FaceInfo.FaceBox box1 = faceInfo.rotateFaceBox();
                Imgproc.circle(image, new Point(box1.leftTop.x, box1.leftTop.y), 3, new Scalar(0,0,255), -1);
                Imgproc.circle(image, new Point(box1.rightTop.x, box1.rightTop.y), 3, new Scalar(0,0,255), -1);
                Imgproc.circle(image, new Point(box1.rightBottom.x, box1.rightBottom.y), 3, new Scalar(0,0,255), -1);
                Imgproc.circle(image, new Point(box1.leftBottom.x, box1.leftBottom.y), 3, new Scalar(0,0,255), -1);

                int pointNum = 1;
                for(FaceInfo.Point keyPoint : faceInfo.points){
                    Imgproc.circle(image, new Point(keyPoint.x, keyPoint.y), 3, new Scalar(0,0,255), -1);
                    Imgproc.putText(image, String.valueOf(pointNum), new Point(keyPoint.x+1, keyPoint.y), Imgproc.FONT_HERSHEY_PLAIN, 1, new Scalar(255,0,0));
                    pointNum ++ ;
                }

//                Mat crop = CropUtil.crop(image, box);
//                HighGui.imshow(fileName, crop);
//                HighGui.waitKey();
            }
            HighGui.imshow(fileName, image);
            HighGui.waitKey();
        }
        System.exit(1);
    }
}
