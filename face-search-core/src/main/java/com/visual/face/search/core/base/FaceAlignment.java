package com.visual.face.search.core.base;

import java.util.Map;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.domain.FaceInfo.Points;

/**
 * 对图像进行对齐
 */
public interface FaceAlignment {

    /**
     * 对图像进行对齐
     * @param imageMat  图像信息
     * @imagePoint
     * @param params    参数信息
     * @return
     */
    ImageMat inference(ImageMat imageMat, Points imagePoint, Map<String, Object> params);

}
