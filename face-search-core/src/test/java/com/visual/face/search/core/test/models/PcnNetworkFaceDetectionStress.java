package com.visual.face.search.core.test.models;

import ai.onnxruntime.OrtEnvironment;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.models.PcnNetworkFaceDetection;
import com.visual.face.search.core.test.base.BaseTest;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.List;
import java.util.Map;

public class PcnNetworkFaceDetectionStress extends BaseTest {

    static{ nu.pattern.OpenCV.loadShared(); }
    private OrtEnvironment env = OrtEnvironment.getEnvironment();

    private static String model1Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn1_sd.onnx";
    private static String model2Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn2_sd.onnx";
    private static String model3Path = "face-search-core/src/main/resources/model/onnx/detection_face_pcn/pcn3_sd.onnx";

    private static String imagePath = "face-search-test/src/main/resources/image/validate/noface";


    public static void main(String[] args) {
        Map<String, String> map = getImagePathMap(imagePath);
        PcnNetworkFaceDetection infer = new PcnNetworkFaceDetection(new String[]{model1Path, model2Path, model3Path}, 1);

        int num = 0;
        for (int i = 0; i < 10000; i++) {
            for (String fileName : map.keySet()) {
                num = num + 1;
                String imageFilePath = map.get(fileName);
                System.out.println(num+":"+imageFilePath);
                Mat image = Imgcodecs.imread(imageFilePath);
                ImageMat imageMat = ImageMat.fromCVMat(image);

                List<FaceInfo> faceInfos = infer.inference(imageMat, PcnNetworkFaceDetection.defScoreTh, PcnNetworkFaceDetection.defIouTh, null);
                faceInfos.clear();

                imageMat.release();
                image.release();
            }
        }
    }
}
