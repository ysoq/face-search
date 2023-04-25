package com.visual.face.search.core.test.extract;

import com.visual.face.search.core.base.*;
import com.visual.face.search.core.domain.ExtParam;
import com.visual.face.search.core.domain.FaceImage;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.extract.FaceFeatureExtractor;
import com.visual.face.search.core.extract.FaceFeatureExtractorImpl;
import com.visual.face.search.core.models.*;
import com.visual.face.search.core.test.base.BaseTest;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.Map;

public class FaceFeatureExtractTest extends BaseTest {

    private static String modelPcn1Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn1_sd.onnx";
    private static String modelPcn2Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn2_sd.onnx";
    private static String modelPcn3Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn3_sd.onnx";
    private static String modelScrfdPath = "face-search-core/src/main/resources/model/onnx/detection_face_scrfd/scrfd_500m_bnkps.onnx";
    private static String modelCoordPath = "face-search-core/src/main/resources/model/onnx/keypoint_coordinate/coordinate_106_mobilenet_05.onnx";
    private static String modelArcPath = "face-search-core/src/main/resources/model/onnx/recognition_face_arc/glint360k_cosface_r18_fp16_0.1.onnx";
    private static String modelArrPath = "face-search-core/src/main/resources/model/onnx/attribute_gender_age/insight_gender_age.onnx";

//    private static String imagePath = "face-search-core/src/test/resources/images/faces";
    private static String imagePath = "face-search-core/src/test/resources/images/faces/debug/debug_0001.jpg";


    public static void main(String[] args) {
        Map<String, String> map = getImagePathMap(imagePath);
        FaceDetection insightScrfdFaceDetection = new InsightScrfdFaceDetection(modelScrfdPath, 1);
        FaceKeyPoint insightCoordFaceKeyPoint = new InsightCoordFaceKeyPoint(modelCoordPath, 1);
        FaceRecognition insightArcFaceRecognition = new InsightArcFaceRecognition(modelArcPath, 1);
        FaceAlignment simple005pFaceAlignment = new Simple005pFaceAlignment();
        FaceAlignment simple106pFaceAlignment = new Simple106pFaceAlignment();
        FaceDetection pcnNetworkFaceDetection = new PcnNetworkFaceDetection(new String[]{modelPcn1Path, modelPcn2Path, modelPcn3Path}, 1);
        FaceAttribute insightFaceAttribute = new InsightAttributeDetection(modelArrPath, 1);

        FaceFeatureExtractor extractor = new FaceFeatureExtractorImpl(
                pcnNetworkFaceDetection, insightScrfdFaceDetection, insightCoordFaceKeyPoint,
                simple005pFaceAlignment, insightArcFaceRecognition, insightFaceAttribute);
        for(String fileName : map.keySet()){
            String imageFilePath = map.get(fileName);
            System.out.println(imageFilePath);
            Mat image = Imgcodecs.imread(imageFilePath);
            long s = System.currentTimeMillis();
            ExtParam extParam = ExtParam.build()
                    .setMask(true)
                    .setTopK(20)
                    .setScoreTh(0)
                    .setIouTh(0);
            FaceImage faceImage = extractor.extract(ImageMat.fromCVMat(image), extParam, null);
            List<FaceInfo> faceInfos = faceImage.faceInfos();
            long e = System.currentTimeMillis();
            System.out.println("fileName="+fileName+",\tcost="+(e-s)+",\t"+faceInfos);

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

                FaceInfo.Attribute attribute = faceInfo.attribute;
                Imgproc.putText(image, attribute.valueOfGender().name(), new Point(box.center().x-10, box.center().y), Imgproc.FONT_HERSHEY_PLAIN, 1, new Scalar(255,0,0));
                Imgproc.putText(image, ""+attribute.age, new Point(box.center().x-10, box.center().y+20), Imgproc.FONT_HERSHEY_PLAIN, 1, new Scalar(255,0,0));


                int pointNum = 1;
                for(FaceInfo.Point keyPoint : faceInfo.points){
                    Imgproc.circle(image, new Point(keyPoint.x, keyPoint.y), 1, new Scalar(0,0,255), -1);
//                    Imgproc.putText(image, String.valueOf(pointNum), new Point(keyPoint.x+1, keyPoint.y), Imgproc.FONT_HERSHEY_PLAIN, 1, new Scalar(255,0,0));
                    pointNum ++ ;
                }


//                Mat crop = CropUtil.crop(image, box);
//                HighGui.imshow(fileName, crop);
//                HighGui.waitKey();

            }
            HighGui.imshow(fileName, image);
            HighGui.waitKey();
            image.release();
        }
        System.exit(1);
    }
}
