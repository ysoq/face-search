package com.visual.face.search.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片人脸信息
 */
public class FaceImage implements Serializable {

    /**图像数据**/
    public  String imageBase64;
    /**人脸解析数据**/
    public List<FaceInfo> faceInfos;

    /**
     * 构建函数
     * @param imageBase64   图像数据
     * @param faceInfos     人脸解析数据
     * @return
     */
    private FaceImage(String imageBase64, List<FaceInfo> faceInfos) {
        this.imageBase64 = imageBase64;
        this.faceInfos = faceInfos;
    }

    /**
     * 构建对象
     * @param imageBase64      图像数据
     * @param faceInfos     人脸解析数据
     * @return
     */
    public static FaceImage build(String imageBase64, List<FaceInfo> faceInfos){
        if(faceInfos == null){
            faceInfos = new ArrayList<>();
        }
        return new FaceImage(imageBase64, faceInfos);
    }

    /**
     * 图像数据
     * @return
     */
    public String imageBase64(){
        return this.imageBase64;
    }

    /**
     * 获取图像数据
     * @return
     */
    public ImageMat imageMat(){
        return ImageMat.fromBase64(this.imageBase64);
    }

    /**
     * 获取人脸解析数据
     * @return
     */
    public List<FaceInfo> faceInfos(){
        return this.faceInfos;
    }
}
