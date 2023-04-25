package com.visual.face.search.server.service.impl;

import com.visual.face.search.core.domain.ExtParam;
import com.visual.face.search.core.domain.FaceImage;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.extract.FaceFeatureExtractor;
import com.visual.face.search.core.utils.Similarity;
import com.visual.face.search.server.domain.extend.CompareFace;
import com.visual.face.search.server.domain.extend.FaceLocation;
import com.visual.face.search.server.domain.request.FaceCompareReqVo;
import com.visual.face.search.server.domain.response.FaceCompareRepVo;
import com.visual.face.search.server.service.api.FaceCompareService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("visualFaceCompareServiceImpl")
public class FaceCompareServiceImpl implements FaceCompareService {

    @Value("${visual.face-mask.face-compare:false}")
    private boolean faceMask;

    @Resource
    private FaceFeatureExtractor faceFeatureExtractor;


    @Override
    public FaceCompareRepVo faceCompare(FaceCompareReqVo compareReq) {
        FaceInfo faceInfoA = getFaceInfo(compareReq.getFaceScoreThreshold(), compareReq.getImageBase64A());
        if(null == faceInfoA){
            throw new RuntimeException("Image A is not face");
        }
        FaceInfo faceInfoB = getFaceInfo(compareReq.getFaceScoreThreshold(), compareReq.getImageBase64B());
        if(null == faceInfoB){
            throw new RuntimeException("Image B is not face");
        }
        //计算余弦相似度
        float simVal = Similarity.cosineSimilarityNorm(faceInfoA.embedding.embeds, faceInfoB.embedding.embeds);
        float confidence = (float) Math.floor(simVal * 1000000)/10000;
        //欧式距离
        float euclideanDistance = Similarity.euclideanDistance(faceInfoA.embedding.embeds, faceInfoB.embedding.embeds);
        float distance = (float) Math.floor(euclideanDistance * 10000)/10000;
        //构建返回值
        FaceCompareRepVo faceCompareRep = new FaceCompareRepVo();
        faceCompareRep.setDistance(distance);
        faceCompareRep.setConfidence(confidence);
        if(compareReq.getNeedFaceInfo()){
            CompareFace compareFace = new CompareFace();
            compareFace.setFaceScoreA((float) Math.floor(faceInfoA.score * 1000000)/10000);
            compareFace.setFaceScoreB((float) Math.floor(faceInfoA.score * 1000000)/10000);
            FaceInfo.FaceBox boxA = faceInfoA.box;
            compareFace.setLocationA(FaceLocation.build(boxA.leftTop.x, boxA.leftTop.y, boxA.width(), boxA.height()));
            FaceInfo.FaceBox boxB = faceInfoB.box;
            compareFace.setLocationB(FaceLocation.build(boxB.leftTop.x, boxB.leftTop.y, boxB.width(), boxB.height()));
            faceCompareRep.setFaceInfo(compareFace);
        }
        //返回对象
        return faceCompareRep;
    }


    /**
     * 图片检测并提取人脸特征
     * @param faceScoreThreshold
     * @param imageBase64
     * @return
     */
    private FaceInfo getFaceInfo(float faceScoreThreshold, String imageBase64){
        faceScoreThreshold = faceScoreThreshold < 0 ? 0 : faceScoreThreshold;
        faceScoreThreshold = faceScoreThreshold > 100 ? 100 : faceScoreThreshold;
        faceScoreThreshold = faceScoreThreshold > 1 ? faceScoreThreshold / 100 : faceScoreThreshold;

        ExtParam extParam = ExtParam.build().setMask(faceMask).setScoreTh(faceScoreThreshold).setIouTh(0).setTopK(1);
        ImageMat imageMat = null;
        FaceImage faceImage = null;
        try {
            imageMat = ImageMat.fromBase64(imageBase64);
            faceImage = faceFeatureExtractor.extract(imageMat, extParam, new HashMap<>());
        }finally {
            if(null != imageMat){
                imageMat.release();
            }
        }
        if(null == faceImage){
            throw new RuntimeException("FeatureExtractor extract error");
        }

        List<FaceInfo> faceInfos = faceImage.faceInfos();
        if(faceInfos.size() > 0){
            return faceInfos.get(0);
        }
        return null;
    }
}
