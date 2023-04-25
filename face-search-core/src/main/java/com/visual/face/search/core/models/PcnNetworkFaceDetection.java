package com.visual.face.search.core.models;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import com.visual.face.search.core.base.BaseOnnxInfer;
import com.visual.face.search.core.base.FaceDetection;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.domain.Mats;
import com.visual.face.search.core.utils.ReleaseUtil;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 人脸识别-PCN
 * git:
 * https://github.com/Rock-100/FaceKit/tree/master/PCN
 * https://github.com/siriusdemon/pytorch-PCN
 */
public class PcnNetworkFaceDetection extends BaseOnnxInfer implements FaceDetection {

    public final static int ValueOfPcnMaxSide = 512;
    public final static String KeyOfPcnMaxSide = "pcn-max-side";
    //常量参数
    private final static float stride_ = 8;
    private final static float minFace_ = 28;
    private final static float scale_ = 1.414f;
    private final static float angleRange_ = 45f;
    private final static double EPS = 1e-5;
    //人脸预测分数阈值
    public final static float defScoreTh = 0.8f;
    //人脸重叠iou阈值
    public final static float defIouTh = 0.6f;
    //人脸预测分数阈值
    public static float[] defScoreThs = new float[]{0.3f, 0.4f, defScoreTh};
    //人脸重叠iou阈值
    public static float[] defIouThs = new float[]{defIouTh, defIouTh, 0.3f};

    /**
     * 构造函数
     * @param modelPaths    模型路径
     * @param threads       线程数
     */
    public PcnNetworkFaceDetection(String[] modelPaths, int threads) {
        super(modelPaths, threads);
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
        Mat mat = null;
        Mat imgPad = null;
        Mat resizeMat = null;
        ImageMat imageMat = image.clone();
        try {
            //防止分辨率过高，导致内存持续增加
            int maxSide = getMaxSide(params);
            mat = imageMat.toCvMat();
            Size size = mat.size();
            float scale = 1.0f;
            if(size.height > maxSide && size.width > maxSide){
                scale = (float) ((size.height > size.width) ? size.width/maxSide: size.height/maxSide);
            }
            //推理
            resizeMat = resize_img_release_mat(mat.clone(), scale);
            imgPad = pad_img_release_mat(resizeMat.clone());
            float[] iouThs = iouTh <= 0 ? defIouThs : new float[]{iouTh, iouTh, 0.3f};
            float[] scoreThs = scoreTh <= 0 ? defScoreThs : new float[]{0.375f * scoreTh, 0.5f * scoreTh, scoreTh};
            List<Window2> willis = detect_release_mat(this.getSessions(), resizeMat.clone(), imgPad.clone(), scoreThs, iouThs);
            return trans_window(resizeMat, imgPad, willis, scale);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            ReleaseUtil.release(imgPad, resizeMat, mat);
            ReleaseUtil.release(imageMat);
        }
    }

    /********************************分割线*************************************/

    private static int getMaxSide(Map<String, Object> params){
        try {
            if(null != params && params.containsKey(KeyOfPcnMaxSide)){
                Object value = params.get(KeyOfPcnMaxSide);
                if(null != value && !value.toString().trim().isEmpty()){
                    return Integer.parseInt(value.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ValueOfPcnMaxSide;
    }

    private static Mat pad_img_release_mat(Mat mat){
        try {
            int row = Math.min((int)(mat.size().height * 0.2), 100);
            int col = Math.min((int)(mat.size().width * 0.2), 100);
            Mat dst = new Mat();
            Core.copyMakeBorder(mat, dst, row, row, col, col, Core.BORDER_CONSTANT);
            return dst;
        }finally {
            ReleaseUtil.release(mat);
        }
    }

    private static Mat resize_img_release_mat(Mat mat, float scale){
        Mat matF32 = null;
        try{
            double h = mat.size().height;
            double w = mat.size().width;
            int h_ = (int) (h / scale);
            int w_ = (int) (w / scale);
            matF32 = new Mat();
            if(mat.type() != CvType.CV_32FC3){
                mat.convertTo(matF32, CvType.CV_32FC3);
            }else{
                mat.copyTo(matF32);
            }
            Mat dst = new Mat();
            Imgproc.resize(matF32, dst, new Size(w_, h_), 0,0, Imgproc.INTER_NEAREST);
            return dst;
        }finally {
            ReleaseUtil.release(matF32, mat);
        }
    }

    private static Mat preprocess_img_release_mat(Mat mat, int dim){
        Mat matTmp = null;
        Mat matF32 = null;
        try {
            //resize
            matTmp = new Mat();
            if(dim > 0){
                Imgproc.resize(mat, matTmp, new Size(dim, dim), 0, 0, Imgproc.INTER_NEAREST);
            }else{
                mat.copyTo(matTmp);
            }
            //格式转化
            matF32 = new Mat();
            if(mat.type() != CvType.CV_32FC3){
                matTmp.convertTo(matF32, CvType.CV_32FC3);
            }else{
                matTmp.copyTo(matF32);
            }
            //减法运算
            Mat dst = new Mat();
            Core.subtract(matF32, new Scalar(104, 117, 123), dst);
            return dst;
        }finally {
            ReleaseUtil.release(matF32, matTmp, mat);
        }

    }

    private static OnnxTensor set_input_release_mat(Mat mat){
        Mat dst = null;
        try {
            dst = new Mat();
            mat.copyTo(dst);
            return ImageMat.fromCVMat(dst).to4dFloatOnnxTensorAndDoReleaseMat(true);
        }finally {
            ReleaseUtil.release(dst, mat);
        }
    }

    private static OnnxTensor set_input_release_mat(Mats mats){
        try {
            float[][][][] arrays = new float[mats.size()][][][];
            for(int i=0; i< mats.size(); i++){
                float[][][][] array = ImageMat.fromCVMat(mats.get(i)).to4dFloatArrayAndDoReleaseMat(true);
                arrays[i] = array[0];
            }
            return OnnxTensor.createTensor(OrtEnvironment.getEnvironment(), arrays);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            ReleaseUtil.release(mats);
        }
    }

    private static boolean legal(int x, int y, Size size){
        if(0 <= x && x < size.width && 0 <= y && y< size.height){
            return true;
        }else{
            return false;
        }
    }

    private static boolean inside(int x, int y, Window2 rect){
        if(rect.x <= x && x < (rect.x + rect.w) && rect.y <= y && y< (rect.y + rect.h)){
            return true;
        }else{
            return false;
        }
    }

    private static float IoU(Window2 w1, Window2 w2){
        float xOverlap = Math.max(0, Math.min(w1.x + w1.w - 1, w2.x + w2.w - 1) - Math.max(w1.x, w2.x) + 1);
        float yOverlap = Math.max(0, Math.min(w1.y + w1.h - 1, w2.y + w2.h - 1) - Math.max(w1.y, w2.y) + 1);
        float intersection = xOverlap * yOverlap;
        float unio = w1.w * w1.h + w2.w * w2.h - intersection;
        return intersection / unio;
    }

    private static List<Window2> NMS(List<Window2> winlist, boolean local, float threshold){
        try{
            if(winlist==null || winlist.isEmpty()){
                return new ArrayList<>();
            }
            //排序
            Collections.sort(winlist);
            int [] flag = new int[winlist.size()];
            for(int i=0; i< winlist.size(); i++){
                if(flag[i] > 0){
                    continue;
                }
                for(int j=i+1; j<winlist.size(); j++){
                    if(local && Math.abs(winlist.get(i).scale - winlist.get(j).scale) > EPS){
                        continue;
                    }
                    if(IoU(winlist.get(i), winlist.get(j)) > threshold){
                        flag[j] = 1;
                    }
                }
            }
            List<Window2> list = new ArrayList<>();
            for(int i=0; i< flag.length; i++){
                if(flag[i] == 0){
                    list.add(winlist.get(i));
                }
            }
            return list;
        }finally {
            if(null != winlist && !winlist.isEmpty()){
                winlist.clear();
            }
        }
    }

    private static  List<Window2> deleteFP(List<Window2> winlist){
        try {
            if(null == winlist || winlist.isEmpty()) {
                return new ArrayList<>();
            }
            //排序
            Collections.sort(winlist);
            int [] flag = new int[winlist.size()];
            for(int i=0; i< winlist.size(); i++){
                if(flag[i] > 0){
                    continue;
                }
                for(int j=i+1; j<winlist.size(); j++){
                    Window2 win = winlist.get(j);
                    if(inside(win.x, win.y, winlist.get(i)) && inside(win.x + win.w - 1, win.y + win.h - 1, winlist.get(i))){
                        flag[j] = 1;
                    }
                }
            }
            List<Window2> list = new ArrayList<>();
            for(int i=0; i< flag.length; i++){
                if(flag[i] == 0){
                    list.add(winlist.get(i));
                }
            }
            return list;
        }finally {
            if(null != winlist && !winlist.isEmpty()){
                winlist.clear();
            }
        }
    }

    private static List<FaceInfo> trans_window(Mat img, Mat imgPad, List<Window2> winlist, float scale){
        List<FaceInfo> ret = new ArrayList<>();
        try {
            int row = (imgPad.size(0) - img.size(0)) / 2;
            int col = (imgPad.size(1) - img.size(1)) / 2;
            for(Window2 win : winlist){
                if( win.w > 0 && win.h > 0){
                    int x1 = win.x - col;
                    int y1 = win.y - row;
                    int x2 = win.x - col + win.w;
                    int y2 = win.y - row + win.w;
                    int angle = (win.angle + 360) % 360;
                    //扩展人脸高度
                    float rw = 0f;
                    float rh = 0.1f;
                    int w = Math.abs(x2 - x1);
                    int h = Math.abs(y2 - y1);
                    x1 = Math.max(Float.valueOf((x1 - (int)(w * rw)) * scale).intValue(), 1);
                    y1 = Math.max(Float.valueOf((y1 - (int)(h * rh)) * scale).intValue(), 1);
                    x2 = Math.min(Float.valueOf((x2 + (int)(w * rw)) * scale).intValue(), Float.valueOf((img.size(1)) * scale).intValue()-1);
                    y2 = Math.min(Float.valueOf((y2 + (int)(h * rh)) * scale).intValue(), Float.valueOf((img.size(0)) * scale).intValue()-1);
                    //构建人脸信息
                    FaceInfo faceInfo = FaceInfo.build(win.conf, angle, FaceInfo.FaceBox.build(x1, y1, x2, y2), FaceInfo.Points.build());
                    ret.add(faceInfo);
                }
            }
            return ret;
        }finally {
            if(null != winlist && !winlist.isEmpty()){
                winlist.clear();
            }
        }

    }

    /**
     * 验证通过
     * @param img
     * @param imgPad
     * @param net
     * @param thres
     * @return
     * @throws OrtException
     * @throws IOException
     */
    private static List<Window2> stage1_release_mat(Mat img, Mat imgPad, OrtSession net, float thres) throws RuntimeException {
        Mat img_resized = null;
        OnnxTensor net_input1 = null;
        OrtSession.Result output = null;
        List<Window2> winlist = new ArrayList<>();
        try {
            //获取必要参数
            int netSize = 24;
            float curScale = minFace_ / netSize;
            int row = (int) ((imgPad.size().height - img.size().height) / 2);
            int col = (int) ((imgPad.size().width  - img.size().width)  / 2);
            img_resized = resize_img_release_mat(img.clone(), curScale);
            //循环处理
            while(Math.min(img_resized.size().height, img_resized.size().width) >= netSize){
                img_resized = preprocess_img_release_mat(img_resized, 0);
                net_input1 = set_input_release_mat(img_resized.clone());
                //推理网络
                output = net.run(Collections.singletonMap(net.getInputNames().iterator().next(), net_input1));
                float[][][][] cls_prob = (float[][][][]) output.get(0).getValue();
                float[][][][] rotate = (float[][][][]) output.get(1).getValue();
                float[][][][] bbox =   (float[][][][]) output.get(2).getValue();
                //关闭对象
                ReleaseUtil.release(output); output = null;
                ReleaseUtil.release(net_input1); net_input1 = null;
                //计算业务逻辑
                float w = netSize * curScale;
                for(int i=0; i< cls_prob[0][0].length; i++){
                    for(int j=0; j< cls_prob[0][0][0].length; j++){
                        if(cls_prob[0][1][i][j] > thres){
                            float sn = bbox[0][0][i][j];
                            float xn = bbox[0][1][i][j];
                            float yn = bbox[0][2][i][j];
                            int rx = (int)(j * curScale * stride_ - 0.5 * sn * w + sn * xn * w + 0.5 * w) + col;
                            int ry = (int)(i * curScale * stride_ - 0.5 * sn * w + sn * yn * w + 0.5 * w) + row;
                            int rw = (int)(w * sn);
                            if (legal(rx, ry, imgPad.size()) && legal(rx + rw - 1, ry + rw - 1, imgPad.size())){
                                if (rotate[0][1][i][j] > 0.5){
                                    winlist.add(new Window2(rx, ry, rw, rw, 0, curScale, cls_prob[0][1][i][j]));
                                }else{
                                    winlist.add(new Window2(rx, ry, rw, rw, 180, curScale, cls_prob[0][1][i][j]));
                                }
                            }
                        }
                    }
                }
                img_resized = resize_img_release_mat(img_resized, scale_);
                curScale = (float) (img.size().height / img_resized.size().height);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            ReleaseUtil.release(output);
            ReleaseUtil.release(net_input1);
            ReleaseUtil.release(img_resized, imgPad, img);
        }
        //返回
        return winlist;
    }

    /**
     * 验证通过
     * @param img
     * @param img180
     * @param net
     * @param thres
     * @param dim
     * @param winlist
     * @return
     * @throws OrtException
     */
    private static List<Window2> stage2_release_mat(Mat img, Mat img180, OrtSession net, float thres, int dim, List<Window2> winlist) throws OrtException {
        if(winlist==null || winlist.isEmpty()){
            return new ArrayList<>();
        }
        //逻辑处理
        float[][] bbox;
        float[][] rotate;
        float[][] cls_prob;
        Mats datalist = null;
        OnnxTensor input = null;
        OrtSession.Result output = null;
        Size size = img.size();
        int height = img.size(0);
        try {
            datalist = Mats.build();
            for(Window2 win : winlist){
                if(Math.abs(win.angle) < EPS){
                    Mat corp = null;
                    try {
                        corp = new Mat(img, new Rect(win.x, win.y, win.w, win.h));
                        Mat preprocess = preprocess_img_release_mat(corp.clone(), dim);
                        datalist.add(preprocess);
                    }finally {
                        ReleaseUtil.release(corp);
                    }
                }else{
                    Mat corp = null;
                    try {
                        int y2 = win.y + win.h - 1;
                        int y = height - 1 - y2;
                        corp = new Mat(img180, new Rect(win.x, y, win.w, win.h));
                        Mat preprocess = preprocess_img_release_mat(corp.clone(), dim);
                        datalist.add(preprocess);
                    }finally {
                        ReleaseUtil.release(corp);
                    }
                }
            }
            //模型推理
            input    = set_input_release_mat(datalist.clone());
            output   = net.run(Collections.singletonMap(net.getInputNames().iterator().next(), input));
            cls_prob = (float[][]) output.get(0).getValue();
            rotate   = (float[][]) output.get(1).getValue();
            bbox     = (float[][]) output.get(2).getValue();
        }finally {
            ReleaseUtil.release(datalist);
            ReleaseUtil.release(output);
            ReleaseUtil.release(input);
            ReleaseUtil.release(img180, img);
            output = null; input = null;
        }
        //再次后处理数据
        List<Window2> ret = new ArrayList<>();
        for(int i=0; i<winlist.size(); i++){
            if(cls_prob[i][1] > thres){
                float sn = bbox[i][0];
                float xn = bbox[i][1];
                float yn = bbox[i][2];
                float cropX = winlist.get(i).x;
                float cropY = winlist.get(i).y;
                float cropW = winlist.get(i).w;
                if(Math.abs(winlist.get(i).angle) > EPS){
                    cropY = height - 1 - (cropY + cropW - 1);
                }
                int w = (int)(sn * cropW);
                int x = (int)(cropX - 0.5 * sn * cropW + cropW * sn * xn + 0.5 * cropW);
                int y = (int)(cropY - 0.5 * sn * cropW + cropW * sn * yn + 0.5 * cropW);
                float maxRotateScore = 0;
                int maxRotateIndex = 0;

                for(int j=0; j<3; j++){
                    if(rotate[i][j] > maxRotateScore){
                        maxRotateScore = rotate[i][j];
                        maxRotateIndex = j;
                    }
                }

                if(legal(x, y, size) && legal(x + w - 1, y + w - 1, size)){
                    int angle = 0;
                    if(Math.abs(winlist.get(i).angle) < EPS){
                        if(maxRotateIndex == 0){
                            angle = 90;
                        }else if(maxRotateIndex == 1){
                            angle = 0;
                        }else{
                            angle = -90;
                        }
                        ret.add(new Window2(x, y, w, w, angle, winlist.get(i).scale, cls_prob[i][1]));
                    }else{
                        if(maxRotateIndex == 0){
                            angle = 90;
                        }else if(maxRotateIndex == 1){
                            angle = 180;
                        }else{
                            angle = -90;
                        }
                        ret.add(new Window2(x, height - 1 - (y + w - 1), w, w, angle, winlist.get(i).scale, cls_prob[i][1]));
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 验证通过
     * @param imgPad
     * @param img180
     * @param img90
     * @param imgNeg90
     * @param net
     * @param thres
     * @param dim
     * @param winlist
     * @return
     * @throws OrtException
     */
    private static List<Window2> stage3_release_mat(Mat imgPad, Mat img180, Mat img90, Mat imgNeg90, OrtSession net, float thres, int dim, List<Window2> winlist) throws OrtException {
        if (winlist == null || winlist.isEmpty()) {
            return new ArrayList<>();
        }
        //逻辑处理
        float[][] bbox;
        float[][] rotate;
        float[][] cls_prob;
        Mats datalist = null;
        OnnxTensor input = null;
        OrtSession.Result output = null;

        int height = imgPad.size(0);
        int width  = imgPad.size(1);
        Size imgPadSize = imgPad.size();
        Size img180Size = img180.size();
        Size img90Size = img90.size();
        Size imgNeg90Size = imgNeg90.size();

        try {
            datalist = Mats.build();
            for(Window2 win : winlist){
                if(Math.abs(win.angle) < EPS){
                    Mat corp = null;
                    try {
                        corp = new Mat(imgPad, new Rect(win.x, win.y, win.w, win.h));
                        datalist.add(preprocess_img_release_mat(corp.clone(), dim));
                    }finally {
                        ReleaseUtil.release(corp);
                    }
                }else if(Math.abs(win.angle - 90) < EPS){
                    Mat corp = null;
                    try {
                        corp = new Mat(img90, new Rect(win.y, win.x, win.h, win.w));
                        datalist.add(preprocess_img_release_mat(corp.clone(), dim));
                    }finally {
                        ReleaseUtil.release(corp);
                    }
                }else if(Math.abs(win.angle + 90) < EPS){
                    Mat corp = null;
                    try {
                        int x = win.y;
                        int y = width - 1 - (win.x + win.w - 1);
                        corp = new Mat(imgNeg90, new Rect(x, y, win.w, win.h));
                        datalist.add(preprocess_img_release_mat(corp.clone(), dim));
                    }finally {
                        ReleaseUtil.release(corp);
                    }
                }else{
                    Mat corp = null;
                    try {
                        int y2 = win.y + win.h - 1;
                        int y = height - 1 - y2;
                        corp = new Mat(img180, new Rect(win.x, y, win.w, win.h));
                        datalist.add(preprocess_img_release_mat(corp.clone(), dim));
                    }finally {
                        ReleaseUtil.release(corp);
                    }
                }
            }
            input    = set_input_release_mat(datalist);
            output   = net.run(Collections.singletonMap(net.getInputNames().iterator().next(), input));
            cls_prob = (float[][]) output.get(0).getValue();
            rotate   = (float[][]) output.get(1).getValue();
            bbox     = (float[][]) output.get(2).getValue();
        }finally {
            ReleaseUtil.release(datalist);
            ReleaseUtil.release(output);
            ReleaseUtil.release(input);
            ReleaseUtil.release(imgPad, img180, img90, imgNeg90);
            output = null; input = null;
        }
        //模型后处理
        List<Window2> ret = new ArrayList<>();
        for(int i=0; i<winlist.size(); i++) {
            if (cls_prob[i][1] > thres) {
                float sn = bbox[i][0];
                float xn = bbox[i][1];
                float yn = bbox[i][2];
                float cropX = winlist.get(i).x;
                float cropY = winlist.get(i).y;
                float cropW = winlist.get(i).w;
                Size img_tmp_size = imgPadSize;
                if (Math.abs(winlist.get(i).angle - 180) < EPS) {
                    cropY = height - 1 - (cropY + cropW - 1);
                    img_tmp_size = img180Size;
                }else if (Math.abs(winlist.get(i).angle - 90) < EPS) {
                    cropX = winlist.get(i).y;
                    cropY = winlist.get(i).x;
                    img_tmp_size = img90Size;
                }else if (Math.abs(winlist.get(i).angle + 90) < EPS) {
                    cropX = winlist.get(i).y;
                    cropY = width - 1 - (winlist.get(i).x + winlist.get(i).w - 1);
                    img_tmp_size = imgNeg90Size;
                }
                int w = (int) (sn * cropW);
                int x = (int) (cropX - 0.5 * sn * cropW + cropW * sn * xn + 0.5 * cropW);
                int y = (int) (cropY - 0.5 * sn * cropW + cropW * sn * yn + 0.5 * cropW);
                int angle = (int)(angleRange_ * rotate[i][0]);
                if(legal(x, y, img_tmp_size) && legal(x + w - 1, y + w - 1, img_tmp_size)){
                    if(Math.abs(winlist.get(i).angle) < EPS){
                        ret.add(new Window2(x, y, w, w, angle, winlist.get(i).scale, cls_prob[i][1]));
                    }else if(Math.abs(winlist.get(i).angle - 180) < EPS){
                        ret.add(new Window2(x, height - 1 - (y + w - 1), w, w, 180 - angle, winlist.get(i).scale, cls_prob[i][1]));
                    }else if(Math.abs(winlist.get(i).angle - 90) < EPS){
                        ret.add(new Window2(y, x, w, w, 90 - angle, winlist.get(i).scale, cls_prob[i][1]));
                    }else{
                        ret.add(new Window2(width - y - w, x, w, w, -90 + angle, winlist.get(i).scale, cls_prob[i][1]));
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 验证通过
     * @param sessions
     * @param img
     * @param imgPad
     * @return
     * @throws OrtException
     * @throws IOException
     */
    private static List<Window2> detect_release_mat(OrtSession[] sessions, Mat img, Mat imgPad, float[] scoreThs, float iouThs[]) throws OrtException, IOException {
        Mat img180 = null;
        Mat img90 = null;
        Mat imgNeg90 = null;
        try {
            //前置处理
            img180 = new Mat();
            Core.flip(imgPad, img180, 0);
            img90 = new Mat();
            Core.transpose(imgPad, img90);
            imgNeg90 = new Mat();
            Core.flip(img90, imgNeg90, 0);
            //第一步
            List<Window2> winlist = stage1_release_mat(img.clone(), imgPad.clone(), sessions[0], scoreThs[0]);
            winlist = NMS(winlist, true, iouThs[0]);
            //第二步
            winlist = stage2_release_mat(imgPad.clone(), img180.clone(), sessions[1], scoreThs[1], 24, winlist);
            winlist = NMS(winlist, true, iouThs[1]);
            //第三步
            winlist = stage3_release_mat(imgPad.clone(), img180.clone(), img90.clone(), imgNeg90.clone(), sessions[2], scoreThs[2], 48, winlist);
            winlist = NMS(winlist, false, iouThs[2]);
            //后处理
            winlist = deleteFP(winlist);
            return winlist;
        }finally {
            ReleaseUtil.release(imgNeg90, img90, img180, imgPad, img);
        }
    }

    /**
     * 临时的人脸框
     */
    private static class Window2 implements Comparable<Window2>{
        public int x;
        public int y;
        public int w;
        public int h;
        public int angle;
        public float scale;
        public float conf;

        public Window2(int x, int y, int w, int h, int angle, float scale, float conf) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.angle = angle;
            this.scale = scale;
            this.conf = conf;
        }

        @Override
        public int compareTo(Window2 o) {
            if(o.conf == this.conf){
                return new Integer(this.y).compareTo(o.y);
            }else{
                return new Float(o.conf).compareTo(this.conf);
            }
        }

        @Override
        public String toString() {
            return "Window2{"
                    + "x=" + x +
                    ", y=" + y +
                    ", w=" + w +
                    ", h=" + h +
                    ", angle=" + angle +
                    ", scale=" + scale +
                    ", conf=" + conf +
                    "}" +"\n";
        }
    }
}
