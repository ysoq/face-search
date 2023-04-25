package com.visual.face.search.engine.model;

import java.io.Serializable;
import com.visual.face.search.engine.utils.NumberUtils;

public class SearchDocument implements Serializable {
    private float score;
    private String faceId;
    private String sampleId;
    private Float[] vectors;

    public SearchDocument(){}

    public SearchDocument(String sampleId, String faceId, float score) {
        this(sampleId, faceId, score, null);
    }

    public SearchDocument(String sampleId, String faceId, float score, Float[] vectors) {
        this.score = score;
        this.faceId = faceId;
        this.sampleId = sampleId;
        this.vectors = vectors;
    }

    public static SearchDocument build(String sampleId, String faceId, float score){
        return build(sampleId, faceId, score, null);
    }

    public static SearchDocument build(String sampleId, String faceId, float score, Float[] vectors){
        return new SearchDocument(sampleId, faceId, score, vectors);
    }

    public float getScore() {
        return score;
    }

    public SearchDocument setScore(float score) {
        this.score = score;
        return this;
    }

    public String getFaceId() {
        return faceId;
    }

    public SearchDocument setFaceId(String faceId) {
        this.faceId = faceId;
        return this;
    }

    public String getSampleId() {
        return sampleId;
    }

    public SearchDocument setSampleId(String sampleId) {
        this.sampleId = sampleId;
        return this;
    }

    public Float[] getVectors() {
        return vectors;
    }

    public SearchDocument setVectors(Object vectors) {
        if(vectors == null){
            return this;
        }else{
            this.vectors = NumberUtils.getFloatArray(vectors);
        }
        return this;
    }
}
