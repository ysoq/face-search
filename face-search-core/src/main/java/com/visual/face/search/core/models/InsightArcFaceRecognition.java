package com.visual.face.search.core.models;

import java.util.Collections;
import java.util.Map;

import org.opencv.core.Scalar;
import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtSession;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.base.BaseOnnxInfer;
import com.visual.face.search.core.base.FaceRecognition;
import com.visual.face.search.core.domain.FaceInfo.Embedding;
import com.visual.face.search.core.domain.ImageMat;

/**
 * 人脸识别-人脸特征提取-512维
 * git:https://github.com/deepinsight/insightface/tree/master/recognition/arcface_torch
 */
public class InsightArcFaceRecognition  extends BaseOnnxInfer implements FaceRecognition {

    /**
     * 构造函数
     * @param modelPath     模型路径
     * @param threads       线程数
     */
    public InsightArcFaceRecognition(String modelPath, int threads) {
        super(modelPath, threads);
    }

    /**
     * 人脸识别，人脸特征向量
     * @param image 图像信息
     * @return
     */
    @Override
    public Embedding inference(ImageMat image, Map<String, Object> params) {
        OnnxTensor tensor = null;
        OrtSession.Result output = null;
        try {
            tensor = image.resizeAndNoReleaseMat(112,112)
                    .blobFromImageAndDoReleaseMat(1.0/127.5, new Scalar(127.5, 127.5, 127.5), true)
                    .to4dFloatOnnxTensorAndDoReleaseMat(true);
            output = getSession().run(Collections.singletonMap(getInputName(), tensor));
            float[][] embeds = (float[][]) output.get(0).getValue();
            return Embedding.build(image.toBase64AndNoReleaseMat(), embeds[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(null != tensor){
                tensor.close();
            }
            if(null != output){
                output.close();
            }
        }
    }

}
