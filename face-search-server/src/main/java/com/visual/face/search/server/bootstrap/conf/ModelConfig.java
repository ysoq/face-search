package com.visual.face.search.server.bootstrap.conf;

import com.visual.face.search.core.base.*;
import com.visual.face.search.core.extract.FaceFeatureExtractor;
import com.visual.face.search.core.extract.FaceFeatureExtractorImpl;
import com.visual.face.search.core.models.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("visualModelConfig")
public class ModelConfig {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${visual.model.faceDetection.name}")
    private String faceDetectionName;
    @Value("${visual.model.faceDetection.modelPath}")
    private String[] faceDetectionModel;
    @Value("${visual.model.faceDetection.thread:4}")
    private Integer faceDetectionThread;

    @Value("${visual.model.faceDetection.backup.name}")
    private String backupFaceDetectionName;
    @Value("${visual.model.faceDetection.backup.modelPath}")
    private String[] backupFaceDetectionModel;
    @Value("${visual.model.faceDetection.backup.thread:4}")
    private Integer backupFaceDetectionThread;

    @Value("${visual.model.faceKeyPoint.name:InsightCoordFaceKeyPoint}")
    private String faceKeyPointName;
    @Value("${visual.model.faceKeyPoint.modelPath}")
    private String[] faceKeyPointModel;
    @Value("${visual.model.faceKeyPoint.thread:4}")
    private Integer faceKeyPointThread;

    @Value("${visual.model.faceAlignment.name:Simple005pFaceAlignment}")
    private String faceAlignmentName;

    @Value("${visual.model.faceRecognition.name:InsightArcFaceRecognition}")
    private String faceRecognitionName;
    @Value("${visual.model.faceRecognition.modelPath}")
    private String[] faceRecognitionNameModel;
    @Value("${visual.model.faceRecognition.thread:4}")
    private Integer faceRecognitionNameThread;


    @Value("${visual.model.faceAttribute.name:InsightAttributeDetection}")
    private String faceAttributeDetectionName;
    @Value("${visual.model.faceAttribute.modelPath}")
    private String[] faceAttributeDetectionNameModel;
    @Value("${visual.model.faceAttribute.thread:4}")
    private Integer faceAttributeDetectionNameThread;


    /**
     * 获取人脸识别模型
     * @return
     */
    @Bean(name = "visualFaceDetection")
    public FaceDetection getFaceDetection(){
        if(faceDetectionName.equalsIgnoreCase("PcnNetworkFaceDetection")){
            return new PcnNetworkFaceDetection(getModelPath(faceDetectionName, faceDetectionModel), faceDetectionThread);
        }else if(faceDetectionName.equalsIgnoreCase("InsightScrfdFaceDetection")){
            return new InsightScrfdFaceDetection(getModelPath(faceDetectionName, faceDetectionModel)[0], faceDetectionThread);
        }else{
            return new PcnNetworkFaceDetection(getModelPath(faceDetectionName, faceDetectionModel), faceDetectionThread);
        }
    }

    /**
     * 获取人脸识别模型
     * @return
     */
    @Bean(name = "visualBackupFaceDetection")
    public FaceDetection getBackupFaceDetection(){
        if(backupFaceDetectionName.equalsIgnoreCase("PcnNetworkFaceDetection")){
            return new PcnNetworkFaceDetection(getModelPath(backupFaceDetectionName, backupFaceDetectionModel), backupFaceDetectionThread);
        }else if(backupFaceDetectionName.equalsIgnoreCase("InsightScrfdFaceDetection")){
            return new InsightScrfdFaceDetection(getModelPath(backupFaceDetectionName, backupFaceDetectionModel)[0], backupFaceDetectionThread);
        }else{
            return this.getFaceDetection();
        }
    }

    /**
     * 关键点标记服务
     * @return
     */
    @Bean(name = "visualFaceKeyPoint")
    public FaceKeyPoint getFaceKeyPoint(){
        if(faceKeyPointName.equalsIgnoreCase("InsightCoordFaceKeyPoint")){
            return new InsightCoordFaceKeyPoint(getModelPath(faceKeyPointName, faceKeyPointModel)[0], faceKeyPointThread);
        }else{
            return new InsightCoordFaceKeyPoint(getModelPath(faceKeyPointName, faceKeyPointModel)[0], faceKeyPointThread);
        }
    }

    /**
     * 人脸对齐服务
     * @return
     */
    @Bean(name = "visualFaceAlignment")
    public FaceAlignment getFaceAlignment(){
        if(faceAlignmentName.equalsIgnoreCase("Simple005pFaceAlignment")){
            return new Simple005pFaceAlignment();
        }else if(faceAlignmentName.equalsIgnoreCase("Simple106pFaceAlignment")){
            return new Simple106pFaceAlignment();
        }else{
            return new Simple005pFaceAlignment();
        }
    }

    /**
     * 人脸特征提取服务
     * @return
     */
    @Bean(name = "visualFaceRecognition")
    public FaceRecognition getFaceRecognition(){
        if(faceRecognitionName.equalsIgnoreCase("InsightArcFaceRecognition")){
            return new InsightArcFaceRecognition(getModelPath(faceRecognitionName, faceRecognitionNameModel)[0], faceRecognitionNameThread);
        }else{
            return new InsightArcFaceRecognition(getModelPath(faceRecognitionName, faceRecognitionNameModel)[0], faceRecognitionNameThread);
        }
    }

    /**
     * 人脸属性检测
     * @return
     */
    @Bean(name = "visualAttributeDetection")
    public InsightAttributeDetection getAttributeDetection(){
        if(faceAttributeDetectionName.equalsIgnoreCase("InsightAttributeDetection")){
            return new InsightAttributeDetection(getModelPath(faceAttributeDetectionName, faceAttributeDetectionNameModel)[0], faceAttributeDetectionNameThread);
        }else{
            return new InsightAttributeDetection(getModelPath(faceAttributeDetectionName, faceAttributeDetectionNameModel)[0], faceAttributeDetectionNameThread);
        }
    }

    /**
     * 构建特征提取器
     * @param faceDetection     人脸识别模型
     * @param faceKeyPoint      人脸关键点模型
     * @param faceAlignment     人脸对齐模型
     * @param faceRecognition   人脸特征提取模型
     */
    @Bean(name = "visualFaceFeatureExtractor")
    public FaceFeatureExtractor getFaceFeatureExtractor(
            @Qualifier("visualFaceDetection")FaceDetection faceDetection,
            @Qualifier("visualBackupFaceDetection")FaceDetection backupFaceDetection,
            @Qualifier("visualFaceKeyPoint")FaceKeyPoint faceKeyPoint,
            @Qualifier("visualFaceAlignment")FaceAlignment faceAlignment,
            @Qualifier("visualFaceRecognition")FaceRecognition faceRecognition,
            @Qualifier("visualAttributeDetection") FaceAttribute faceAttribute
    ){
            if(faceDetection.getClass().isAssignableFrom(backupFaceDetection.getClass())){
                return new FaceFeatureExtractorImpl(
                    faceDetection, null, faceKeyPoint,
                    faceAlignment, faceRecognition, faceAttribute
                );
            }else{
                return new FaceFeatureExtractorImpl(
                    faceDetection, backupFaceDetection, faceKeyPoint,
                    faceAlignment, faceRecognition, faceAttribute
                );
            }
    }

    /**
     * 获取模型路径
     * @param modelName 模型名称
     * @return
     */
    private String[] getModelPath(String modelName, String modelPath[]){

        String basePath = "face-search-core/src/main/resources/";
        if("docker".equalsIgnoreCase(profile)){
            basePath = "/app/face-search/";
        }

        if((null == modelPath || modelPath.length != 3) && "PcnNetworkFaceDetection".equalsIgnoreCase(modelName)){
            return new String[]{
                    basePath + "model/onnx/detection_face_pcn/pcn1_sd.onnx",
                    basePath + "model/onnx/detection_face_pcn/pcn2_sd.onnx",
                    basePath + "model/onnx/detection_face_pcn/pcn3_sd.onnx"
            };
        }

        if((null == modelPath || modelPath.length != 1) && "InsightScrfdFaceDetection".equalsIgnoreCase(modelName)){
            return new String[]{basePath + "model/onnx/detection_face_scrfd/scrfd_500m_bnkps.onnx"};
        }

        if((null == modelPath || modelPath.length != 1) && "InsightCoordFaceKeyPoint".equalsIgnoreCase(modelName)){
            return new String[]{basePath + "model/onnx/keypoint_coordinate/coordinate_106_mobilenet_05.onnx"};
        }

        if((null == modelPath || modelPath.length != 1) && "InsightArcFaceRecognition".equalsIgnoreCase(modelName)){
            return new String[]{basePath + "model/onnx/recognition_face_arc/glint360k_cosface_r18_fp16_0.1.onnx"};
        }

        if((null == modelPath || modelPath.length != 1) && "InsightAttributeDetection".equalsIgnoreCase(modelName)){
            return new String[]{basePath + "model/onnx/attribute_gender_age/insight_gender_age.onnx"};
        }

        return modelPath;
    }
}
