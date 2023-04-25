package com.visual.face.search.core.models;

import com.visual.face.search.core.base.FaceAlignment;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.utils.AlignUtil;
import org.opencv.core.Mat;

import java.util.Map;

/**
 * 五点对齐法
 */
public class Simple005pFaceAlignment implements FaceAlignment {

    /**对齐矩阵**/
    private final static double[][] dst_points = new double[][]{
            {30.2946f + 8.0000f, 51.6963f},
            {65.5318f + 8.0000f, 51.6963f},
            {48.0252f + 8.0000f, 71.7366f},
            {33.5493f + 8.0000f, 92.3655f},
            {62.7299f + 8.0000f, 92.3655f}
    };

    /**
     * 对图像进行对齐
     * @param imageMat  图像信息
     * @imagePoint      图像的关键点
     * @param params    参数信息
     * @return
     */
    @Override
    public ImageMat inference(ImageMat imageMat, FaceInfo.Points imagePoint, Map<String, Object> params) {
        double [][] image_points;
        if(imagePoint.size() == 5){
            image_points = imagePoint.toDoubleArray();
        }else if(imagePoint.size() == 106){
            image_points = imagePoint.select(38, 88, 80, 52, 61).toDoubleArray();
        }else{
            throw new RuntimeException("need 5 point, but get "+ imagePoint.size());
        }
        Mat alignMat = AlignUtil.alignedImage(imageMat.toCvMat(), image_points, 112, 112, dst_points);
        return ImageMat.fromCVMat(alignMat);
    }

}
