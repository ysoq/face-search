package com.visual.face.search.core.extract;

import java.util.List;
import java.util.Map;
import org.opencv.core.Mat;
import com.visual.face.search.core.base.*;
import com.visual.face.search.core.domain.ExtParam;
import com.visual.face.search.core.domain.FaceImage;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.utils.CropUtil;
import com.visual.face.search.core.utils.MaskUtil;
import com.visual.face.search.core.models.InsightCoordFaceKeyPoint;

/**
 * 人脸特征提取器实现
 */
public class FaceFeatureExtractorImpl implements FaceFeatureExtractor {

    public static final float defScaling = 1.5f;

    private FaceKeyPoint faceKeyPoint;
    private FaceDetection faceDetection;
    private FaceAlignment faceAlignment;
    private FaceRecognition faceRecognition;
    private FaceDetection backupFaceDetection;
    private FaceAttribute faceAttribute;

    /**
     * 构造函数
     * @param faceDetection         人脸识别模型
     * @param backupFaceDetection   备用人脸识别模型
     * @param faceKeyPoint          人脸关键点模型
     * @param faceAlignment         人脸对齐模型
     * @param faceRecognition       人脸特征提取模型
     */
    public FaceFeatureExtractorImpl(
            FaceDetection faceDetection, FaceDetection backupFaceDetection,
            FaceKeyPoint faceKeyPoint, FaceAlignment faceAlignment,
            FaceRecognition faceRecognition, FaceAttribute faceAttribute) {
        this.faceKeyPoint = faceKeyPoint;
        this.faceDetection = faceDetection;
        this.faceAlignment = faceAlignment;
        this.faceAttribute = faceAttribute;
        this.faceRecognition = faceRecognition;
        this.backupFaceDetection = backupFaceDetection;
    }

    /**
     * 人脸特征提取
     * @param image
     * @param extParam
     * @param params
     * @return
     */
    @Override
    public FaceImage extract(ImageMat image, ExtParam extParam, Map<String, Object> params) {
        //人脸识别
        List<FaceInfo> faceInfos =  this.faceDetection.inference(image, extParam.getScoreTh(), extParam.getIouTh(), params);
        //启用备用的人脸识别
        if(faceInfos.isEmpty() && null != backupFaceDetection){
            faceInfos = this.backupFaceDetection.inference(image, extParam.getScoreTh(), extParam.getIouTh(), params);
        }
        //取人脸topK
        int topK = (extParam.getTopK()  > 0) ? extParam.getTopK() : 5;
        if(faceInfos.size() > topK){
            faceInfos = faceInfos.subList(0, topK);
        }
        //处理数据
        for(FaceInfo faceInfo : faceInfos) {
            Mat cropFace = null;
            ImageMat cropImageMat = null;
            ImageMat alignmentImage = null;
            try {
                //通过旋转角度获取正脸坐标，并进行图像裁剪
                FaceInfo.FaceBox rotateFaceBox = faceInfo.rotateFaceBox();
                cropFace = CropUtil.crop(image.toCvMat(), rotateFaceBox);
                cropImageMat = ImageMat.fromCVMat(cropFace);
                //人脸属性检测
                FaceInfo.Attribute attribute = this.faceAttribute.inference(cropImageMat, params);
                faceInfo.attribute = attribute;
                //进行缩放人脸区域，并裁剪图片
                float scaling = extParam.getScaling() <= 0 ? defScaling : extParam.getScaling();
                FaceInfo.FaceBox box = rotateFaceBox.scaling(scaling);
                cropFace = CropUtil.crop(image.toCvMat(), box);
                cropImageMat = ImageMat.fromCVMat(cropFace);
                //人脸标记关键点
                FaceInfo.Points corpPoints = this.faceKeyPoint.inference(cropImageMat, params);
                //还原原始图片中的关键点
                FaceInfo.Point corpImageCenter = FaceInfo.Point.build((float)cropImageMat.center().x, (float)cropImageMat.center().y);
                FaceInfo.Points imagePoints = corpPoints.rotation(corpImageCenter, faceInfo.angle).operateSubtract(corpImageCenter);
                faceInfo.points = imagePoints.operateAdd(box.center());
                //人脸对齐
                alignmentImage = this.faceAlignment.inference(cropImageMat, corpPoints, params);
                //判断是否需要遮罩人脸以外的区域
                if(extParam.isMask()){
                    if(faceKeyPoint instanceof InsightCoordFaceKeyPoint){
                        FaceInfo.Points alignmentPoints = this.faceKeyPoint.inference(alignmentImage, params);
                        alignmentImage = MaskUtil.maskFor106InsightCoordModel(alignmentImage, alignmentPoints, true);
                    }
                }
                //人脸特征提取
                FaceInfo.Embedding embedding = this.faceRecognition.inference(alignmentImage, params);
                faceInfo.embedding = embedding;
            }finally {
                if(null != alignmentImage){
                    alignmentImage.release();
                }
                if(null != cropImageMat){
                    cropImageMat.release();
                }
                if(null != cropFace){
                    cropFace.release();
                }
            }

        }
        return FaceImage.build(image.toBase64AndNoReleaseMat(), faceInfos);
    }

}
