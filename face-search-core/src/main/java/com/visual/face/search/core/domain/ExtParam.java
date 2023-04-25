package com.visual.face.search.core.domain;

import java.io.Serializable;

public class ExtParam implements Serializable {

    private float scoreTh;

    private float iouTh;

    private float scaling;

    private boolean mask;

    private int topK = 5;

    private ExtParam(){}

    public static ExtParam build(){
        return new ExtParam();
    }

    public float getScoreTh() {
        return scoreTh;
    }

    public ExtParam setScoreTh(float scoreTh) {
        this.scoreTh = scoreTh;
        return this;
    }

    public float getIouTh() {
        return iouTh;
    }

    public ExtParam setIouTh(float iouTh) {
        this.iouTh = iouTh;
        return this;
    }

    public float getScaling() {
        return scaling;
    }

    public ExtParam setScaling(float scaling) {
        this.scaling = scaling;
        return this;
    }

    public boolean isMask() {
        return mask;
    }

    public ExtParam setMask(boolean mask) {
        this.mask = mask;
        return this;
    }

    public int getTopK() {
        return topK;
    }

    public ExtParam setTopK(int topK) {
        this.topK = topK;
        return this;
    }
}
