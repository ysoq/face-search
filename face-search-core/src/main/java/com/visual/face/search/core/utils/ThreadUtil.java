package com.visual.face.search.core.utils;

public class ThreadUtil {

    public static void run(Runnable runnable){
        new Thread(runnable).start();
    }

}
