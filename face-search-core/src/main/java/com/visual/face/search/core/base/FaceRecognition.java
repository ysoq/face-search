package com.visual.face.search.core.base;

import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.domain.FaceInfo.Embedding;

import java.util.Map;

/**
 * 人脸识别模型
 */
public interface FaceRecognition {

    /**
     * 人脸识别，人脸特征向量
     * @param image 图像信息
     * @param params    参数信息
     * @return
     */
    Embedding inference(ImageMat image, Map<String, Object> params);

}
