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

import java.util.List;
import java.util.Map;

public class FaceFeatureExtractOOMTest extends BaseTest {

    private static String modelPcn1Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn1_sd.onnx";
    private static String modelPcn2Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn2_sd.onnx";
    private static String modelPcn3Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn3_sd.onnx";
    private static String modelScrfdPath = "face-search-core/src/main/resources/model/onnx/detection_face_scrfd/scrfd_500m_bnkps.onnx";
    private static String modelCoordPath = "face-search-core/src/main/resources/model/onnx/keypoint_coordinate/coordinate_106_mobilenet_05.onnx";
    private static String modelArcPath = "face-search-core/src/main/resources/model/onnx/recognition_face_arc/glint360k_cosface_r18_fp16_0.1.onnx";
    private static String modelArrPath = "face-search-core/src/main/resources/model/onnx/attribute_gender_age/insight_gender_age.onnx";

//    private static String imagePath = "face-search-core/src/test/resources/images/faces";
    private static String imagePath = "face-search-core/src/test/resources/images/faces/debug/debug_0001.jpg";
    private static Map<String, String> map = getImagePathMap(imagePath);


    public static void main(String[] args) {
        testAll();

//        testPcnNetworkFaceDetection();
//        testInsightScrfdFaceDetection();  //无异常，内存稳定
//        testInsightCoordFaceKeyPoint();   //无异常，内存稳定
//        testInsightArcFaceRecognition();  //无异常，内存稳定,389-351
    }


    public static void testAll() {
        FaceDetection insightScrfdFaceDetection = new InsightScrfdFaceDetection(modelScrfdPath, 1);
        FaceKeyPoint insightCoordFaceKeyPoint = new InsightCoordFaceKeyPoint(modelCoordPath, 1);
        FaceRecognition insightArcFaceRecognition = new InsightArcFaceRecognition(modelArcPath, 1);
        FaceAlignment simple005pFaceAlignment = new Simple005pFaceAlignment();
        FaceAlignment simple106pFaceAlignment = new Simple106pFaceAlignment();
        FaceDetection pcnNetworkFaceDetection = new PcnNetworkFaceDetection(new String[]{modelPcn1Path, modelPcn2Path, modelPcn3Path}, 1);
        FaceAttribute insightFaceAttribute = new InsightAttributeDetection(modelArrPath, 1);

        FaceFeatureExtractor extractor = new FaceFeatureExtractorImpl(
                insightScrfdFaceDetection, pcnNetworkFaceDetection, insightCoordFaceKeyPoint,
                simple106pFaceAlignment, insightArcFaceRecognition, insightFaceAttribute);
//        FaceFeatureExtractor extractor = new FaceFeatureExtractorImpl(insightScrfdFaceDetection, insightCoordFaceKeyPoint, simple106pFaceAlignment, insightArcFaceRecognition);
        for (int i = 0; i < 100000; i++) {
            for (String fileName : map.keySet()) {
                long s = System.currentTimeMillis();
                ImageMat imageMat = ImageMat.fromImage(map.get(fileName));
                ExtParam extParam = ExtParam.build().setMask(true).setScoreTh(InsightScrfdFaceDetection.defScoreTh).setIouTh(InsightScrfdFaceDetection.defIouTh);
                FaceImage faceImage = extractor.extract(imageMat, extParam, null);
                long e = System.currentTimeMillis();
                System.out.println(i + ",cost=" +(e-s)+"ms:"+ fileName + ":" + faceImage);
                imageMat.release();
            }
        }
    }

    public static void testPcnNetworkFaceDetection(){
        FaceDetection pcnNetworkFaceDetection = new PcnNetworkFaceDetection(new String[]{modelPcn1Path, modelPcn2Path, modelPcn3Path}, 4);
        for (int i = 0; i < 100000; i++) {
            for (String fileName : map.keySet()) {
                ImageMat imageMat = ImageMat.fromImage(map.get(fileName));
                List<FaceInfo> list = pcnNetworkFaceDetection.inference(imageMat, PcnNetworkFaceDetection.defScoreTh, PcnNetworkFaceDetection.defIouTh, null);
                System.out.println(i + "," + fileName + ":" + list.size());
                imageMat.release();
            }
        }
    }

    //验证无内存泄露
    public static void testInsightScrfdFaceDetection(){
        FaceDetection insightScrfdFaceDetection = new InsightScrfdFaceDetection(modelScrfdPath, 4);
        for (int i = 0; i < 100000; i++) {
            for (String fileName : map.keySet()) {
                ImageMat imageMat = ImageMat.fromImage(map.get(fileName));
                List<FaceInfo> list = insightScrfdFaceDetection.inference(imageMat, InsightScrfdFaceDetection.defScoreTh, InsightScrfdFaceDetection.defIouTh, null);
                System.out.println(i + "," + fileName + ":" + list.size());
                imageMat.release();
            }
        }
    }

    //验证无内存泄露
    public static void testInsightCoordFaceKeyPoint(){
        FaceKeyPoint insightCoordFaceKeyPoint = new InsightCoordFaceKeyPoint(modelCoordPath, 1);
        for (int i = 0; i < 100000; i++) {
            for (String fileName : map.keySet()) {
                ImageMat imageMat = ImageMat.fromImage(map.get(fileName));
                FaceInfo.Points list = insightCoordFaceKeyPoint.inference(imageMat, null);
                System.out.println(i + "," + fileName + ":" + list.size());
                imageMat.release();
            }
        }
    }


    //验证无内存泄露
    public static void testInsightArcFaceRecognition(){
        FaceRecognition insightArcFaceRecognition = new InsightArcFaceRecognition(modelArcPath, 1);
        for (int i = 0; i < 100000; i++) {
            for (String fileName : map.keySet()) {
                ImageMat imageMat = ImageMat.fromImage(map.get(fileName));
                FaceInfo.Embedding embedding = insightArcFaceRecognition.inference(imageMat, null);
                System.out.println(i + "," + fileName + ":" + embedding);
                imageMat.release();
            }
        }
    }
}
