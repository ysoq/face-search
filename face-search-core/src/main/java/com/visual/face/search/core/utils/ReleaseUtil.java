package com.visual.face.search.core.utils;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtSession;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.domain.Mats;
import org.opencv.core.Mat;

public class ReleaseUtil {

    public static void release(Mat ...mats){
        for(Mat mat : mats){
            if(null != mat){
                try {
                    mat.release();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    mat = null;
                }
            }
        }
    }

    public static void release(Mats mats){
        if(null == mats || mats.isEmpty()){
            return;
        }
        try {
            mats.release();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mats = null;
        }
    }

    public static void release(ImageMat ...imageMats){
        for(ImageMat imageMat : imageMats){
            if(null != imageMat){
                try {
                    imageMat.release();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    imageMat = null;
                }
            }
        }
    }

    public static void release(OnnxTensor ...tensors){
        if(null == tensors || tensors.length == 0){
            return;
        }
        try {
            for(OnnxTensor tensor : tensors){
                try {
                    if(null != tensor){
                        tensor.close();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    tensor = null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            tensors = null;
        }
    }

    public static void release(OrtSession.Result ...results){
        if(null == results || results.length == 0){
            return;
        }
        try {
            for(OrtSession.Result result : results){
                try {
                    if(null != result){
                        result.close();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    result = null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            results = null;
        }
    }

}
