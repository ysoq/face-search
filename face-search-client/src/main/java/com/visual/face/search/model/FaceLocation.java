package com.visual.face.search.model;

import java.io.Serializable;

public class FaceLocation implements Serializable {
    /**左上角x坐标**/
    private int x;
    /**左上角y坐标**/
    private int y;
    /**宽度**/
    private int w;
    /**高度**/
    private int h;

    /**
     * 构建坐标
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    public static FaceLocation build(int x, int y, int w, int h){
        return new FaceLocation().setX(x).setY(y).setW(w).setH(h);
    }

    public static FaceLocation build(float x, float y, float w, float h){
        return new FaceLocation().setX((int) x).setY((int) y).setW((int) w).setH((int) h);
    }

    public int getX() {
        return x;
    }

    public FaceLocation setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public FaceLocation setY(int y) {
        this.y = y;
        return this;
    }

    public int getW() {
        return w;
    }

    public FaceLocation setW(int w) {
        this.w = w;
        return this;
    }

    public int getH() {
        return h;
    }

    public FaceLocation setH(int h) {
        this.h = h;
        return this;
    }
}
