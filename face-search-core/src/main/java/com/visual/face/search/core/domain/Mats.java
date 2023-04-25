package com.visual.face.search.core.domain;

import org.opencv.core.Mat;

import java.util.ArrayList;

public class Mats extends ArrayList<Mat> {

    public static Mats build(){
        return new Mats();
    }

    public Mats append(Mat mat){
        this.add(mat);
        return this;
    }

    public Mats clone(){
        Mats mats = new Mats();
        for(Mat mat : this){
            mats.add(mat.clone());
        }
        return mats;
    }

    public void release(){
        if(this.isEmpty()){
            return;
        }
        for(Mat mat : this){
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
        this.clear();
    }

}
