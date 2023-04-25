package com.visual.face.search.core.base;

import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.domain.FaceInfo.Attribute;

import java.util.Map;

public interface FaceAttribute {

    /**
     * 人脸属性信息
     * @param imageMat  图像数据
     * @param params    参数信息
     * @return
     */
    Attribute inference(ImageMat imageMat, Map<String, Object> params);

}
