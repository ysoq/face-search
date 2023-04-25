package com.visual.face.search.core.base;

import java.util.Map;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.domain.FaceInfo.Points;

/**
 * 人脸关键点检测
 */
public interface FaceKeyPoint {

    /**
     * 人脸关键点检测
     * @param imageMat  图像数据
     * @param params    参数信息
     * @return
     */
    Points inference(ImageMat imageMat, Map<String, Object> params);

}
