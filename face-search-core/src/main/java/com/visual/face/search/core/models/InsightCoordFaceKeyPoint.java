package com.visual.face.search.core.models;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtSession;
import com.visual.face.search.core.base.BaseOnnxInfer;
import com.visual.face.search.core.base.FaceKeyPoint;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.utils.MathUtil;
import org.apache.commons.math3.linear.RealMatrix;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.Collections;
import java.util.Map;

public class InsightCoordFaceKeyPoint extends BaseOnnxInfer implements FaceKeyPoint {

    private static final int[] outputSize = new int[]{192, 192};

    /**
     * 构造函数
     * @param modelPath     模型路径
     * @param threads       线程数
     */
    public InsightCoordFaceKeyPoint(String modelPath, int threads) {
        super(modelPath, threads);
    }

    /**
     * 人脸关键点检测
     * @param imageMat  图像数据
     * @param params    参数信息
     * @return
     */
    @Override
    public FaceInfo.Points inference(ImageMat imageMat, Map<String, Object> params) {
        Mat M =null;
        Mat IM = null;
        Mat img = null;
        OnnxTensor tensor = null;
        OrtSession.Result output = null;
        try {
            Mat image = imageMat.toCvMat();
            int w = image.size(1);
            int h = image.size(0);
            float cx = 1.0f * w / 2;
            float cy = 1.0f * h / 2;
            float[]center = new float[]{cx, cy};
            float rotate = 0;
            float _scale = (float) (1.0f * outputSize[0]  / (Math.max(w, h)*1.5));
            Mat[] transform = transform(image, center, outputSize, _scale, rotate);
            img = transform[0];
            M = transform[1];
            tensor = ImageMat.fromCVMat(img)
                    .blobFromImageAndDoReleaseMat(1.0, new Scalar(0, 0, 0), true)
                    .to4dFloatOnnxTensorAndDoReleaseMat(true);
            output = this.getSession().run(Collections.singletonMap(this.getInputName(), tensor));
            float[] value = ((float[][]) output.get(0).getValue())[0];
            float points[][] = new float[106][2];
            for(int i=0; i< 106; i++){
                points[i][0] = (value[2*i] + 1) * 96;
                points[i][1] = (value[2*i + 1] + 1) * 96;
            }
            IM = new Mat();
            Imgproc.invertAffineTransform(M, IM);
            return transPoints(points, IM);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(null != tensor){
                tensor.close();
            }
            if(null != output){
                output.close();
            }
            if(null != M){
                M.release();
            }
            if(null != IM){
                IM.release();
            }
            if(null != img){
                img.release();
            }
        }
    }

    /**
     * 获取人脸数据和仿射矩阵
     * @param image
     * @param center
     * @param outputSize
     * @param scale
     * @param rotation
     * @return
     */
    private static Mat[] transform(Mat image, float[]center, int[]outputSize, float scale, float rotation){
        double scale_ratio = scale;
        double rot = rotation * Math.PI / 180.0;
        double cx = center[0] * scale_ratio;
        double cy = center[1] * scale_ratio;
        //矩阵构造
        RealMatrix t1 = MathUtil.similarityTransform((Double[][]) null, scale_ratio, null, null);
        RealMatrix t2 = MathUtil.similarityTransform((Double[][]) null, null, null, new Double[]{- cx, - cy});
        RealMatrix t3 = MathUtil.similarityTransform((Double[][]) null, null, rot, null);
        RealMatrix t4 = MathUtil.similarityTransform((Double[][]) null, null, null, new Double[]{1.0*outputSize[0]/2, 1.0*outputSize[1]/2});
        RealMatrix tx = MathUtil.dotProduct(t4, MathUtil.dotProduct(t3, MathUtil.dotProduct(t2, t1)));
        RealMatrix tm = tx.getSubMatrix(0, 1, 0, 2);
        //仿射矩阵
        Mat matMTemp = new MatOfDouble(MathUtil.flatMatrix(tm, 1).toArray());
        Mat matM = new Mat(2, 3, CvType.CV_32FC3);
        matMTemp.reshape(1,2).copyTo(matM);
        matMTemp.release();
        //使用open cv做仿射变换
        Mat dst = new Mat();
        Imgproc.warpAffine(image, dst, matM, new Size(outputSize[0], outputSize[1]));
        return new Mat[]{dst, matM};
    }

    /**
     * 关键点转换
     * @param points    预测的点
     * @param mat       仿射矩阵
     * @return          标记的关键点
     */
    private static FaceInfo.Points transPoints(float points[][], Mat mat){
        int length = points.length;
        FaceInfo.Points pointList = FaceInfo.Points.build();
        double M[][] = ImageMat.fromCVMat(mat).to4dDoubleArrayAndNoReleaseMat(true)[0][0];
        for(int i=0; i<length; i++){
            float pt[] = points[i];
            double new_pt [] = new double[]{pt[0], pt[1], 1.0f};
            double x = MathUtil.dotProduct(MathUtil.createVector(M[0]), MathUtil.createVector(new_pt));
            double y = MathUtil.dotProduct(MathUtil.createVector(M[1]), MathUtil.createVector(new_pt));
            pointList.add(FaceInfo.Point.build((float)x, (float)y));
        }
        return pointList;
    }
}
