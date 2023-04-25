package com.visual.face.search.core.extract;

import com.visual.face.search.core.domain.ExtParam;
import com.visual.face.search.core.domain.FaceImage;
import com.visual.face.search.core.domain.ImageMat;

import java.util.Map;

/**
 * 人脸特征提取器
 */
public interface FaceFeatureExtractor {

    /**
     * 人脸特征提取
     * @param image
     * @param extParam
     * @param params
     * @return
     */
    public FaceImage extract(ImageMat image, ExtParam extParam, Map<String, Object> params);

}
