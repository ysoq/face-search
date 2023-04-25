package com.visual.face.search.core.models;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import com.visual.face.search.core.base.BaseOnnxInfer;
import com.visual.face.search.core.base.FaceDetection;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import org.opencv.core.Scalar;

import java.util.*;

/**
 * 人脸识别-SCRFD
 * git:https://github.com/deepinsight/insightface/tree/master/detection/scrfd
 */
public class InsightScrfdFaceDetection extends BaseOnnxInfer implements FaceDetection {
    //图像的最大尺寸
    private final static int maxSizeLength = 640;
    //模型人脸检测的步长
    private final static int[] strides = new int[]{8, 16, 32};
    //人脸预测分数阈值
    public final static float defScoreTh = 0.5f;
    //人脸重叠iou阈值
    public final static float defIouTh = 0.7f;

    /**
     * 构造函数
     * @param modelPath     模型路径
     * @param threads       线程数
     */
    public InsightScrfdFaceDetection(String modelPath, int threads) {
        super(modelPath, threads);
    }

    /**
     *获取人脸信息
     * @param image     图像信息
     * @param scoreTh   人脸人数阈值
     * @param iouTh     人脸iou阈值
     * @return  人脸模型
     */
    @Override
    public List<FaceInfo> inference(ImageMat image, float scoreTh, float iouTh, Map<String, Object> params) {
        OnnxTensor tensor = null;
        OrtSession.Result output = null;
        ImageMat imageMat = image.clone();
        try {
            float imgScale = 1.0f;
            iouTh = iouTh <= 0 ? defIouTh : iouTh;
            scoreTh = scoreTh <= 0 ? defScoreTh : scoreTh;
            int imageWidth = imageMat.getWidth(), imageHeight = imageMat.getHeight();
            int modelWidth = imageWidth, modelHeight = imageHeight;
            if(imageWidth > maxSizeLength || imageHeight > maxSizeLength){
                if(imageWidth > imageHeight){
                    modelWidth = maxSizeLength;
                    imgScale = 1.0f * imageWidth / maxSizeLength;
                    modelHeight = imageHeight * maxSizeLength / imageWidth;
                }else {
                    modelHeight = maxSizeLength ;
                    imgScale = 1.0f * imageHeight / maxSizeLength;
                    modelWidth = modelWidth * maxSizeLength / imageHeight;
                }
                imageMat = imageMat.resizeAndDoReleaseMat(modelWidth, modelHeight);
            }
            tensor = imageMat
                    .blobFromImageAndDoReleaseMat(1.0/128, new Scalar(127.5, 127.5, 127.5), true)
                    .to4dFloatOnnxTensorAndDoReleaseMat(true);
            output = getSession().run(Collections.singletonMap(getInputName(), tensor));
            return fitterBoxes(output, scoreTh, iouTh, tensor.getInfo().getShape()[3], imgScale);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(null != tensor){
                tensor.close();
            }
            if(null != output){
                output.close();
            }
            if(null != imageMat){
                imageMat.release();
            }
        }
    }

    /**
     * 过滤人脸框
     * @param output    数据输出
     * @param scoreTh   人脸分数阈值
     * @param iouTh     人脸重叠阈值
     * @param tensorWidth   输出层的宽度
     * @param imgScale  图像的缩放比例
     * @return
     * @throws OrtException
     */
    private List<FaceInfo> fitterBoxes(OrtSession.Result output, float scoreTh, float iouTh, long tensorWidth, float imgScale) throws OrtException {
        //分数过滤及计算正确的人脸框值
        List<FaceInfo> faceInfos = new ArrayList<>();
        for(int index=0; index< 3; index++) {
            float[][] scores = (float[][]) output.get(index).getValue();
            float[][] boxes = (float[][]) output.get(index + 3).getValue();
            float[][] points = (float[][]) output.get(index + 6).getValue();
            int ws = (int) Math.ceil(1.0f * tensorWidth / strides[index]);
            for(int i=0; i< scores.length; i++){
                if(scores[i][0] >= scoreTh){
                    int anchorIndex = i / 2;
                    int rowNum = anchorIndex / ws;
                    int colNum = anchorIndex % ws;
                    //计算人脸框
                    float anchorX = colNum * strides[index];
                    float anchorY = rowNum * strides[index];
                    float x1 = (anchorX - boxes[i][0] * strides[index]) * imgScale;
                    float y1 = (anchorY - boxes[i][1] * strides[index]) * imgScale;
                    float x2 = (anchorX + boxes[i][2] * strides[index]) * imgScale;
                    float y2 = (anchorY + boxes[i][3] * strides[index]) * imgScale;
                    //计算关键点
                    float [] point = points[i];
                    FaceInfo.Points keyPoints = FaceInfo.Points.build();
                    for(int pointIndex=0; pointIndex<(point.length/2); pointIndex++){
                        float pointX = (point[2*pointIndex]   * strides[index] + anchorX) * imgScale;
                        float pointY = (point[2*pointIndex+1] * strides[index] + anchorY) * imgScale;
                        keyPoints.add(FaceInfo.Point.build(pointX, pointY));
                    }
                    faceInfos.add(FaceInfo.build(scores[i][0], 0, FaceInfo.FaceBox.build(x1,y1,x2,y2), keyPoints));
                }
            }
        }
        //对人脸框进行iou过滤
        Collections.sort(faceInfos);
        List<FaceInfo> faces = new ArrayList<>();
        while(!faceInfos.isEmpty()){
            Iterator<FaceInfo> iterator = faceInfos.iterator();
            //获取第一个元素，并删除元素
            FaceInfo firstFace = iterator.next();
            iterator.remove();
            //对比后面元素与第一个元素之间的iou
            while (iterator.hasNext()) {
                FaceInfo nextFace = iterator.next();
                if(firstFace.iou(nextFace) >= iouTh){
                    iterator.remove();
                }
            }
            faces.add(firstFace);
        }
        //返回
        return faces;
    }

}
