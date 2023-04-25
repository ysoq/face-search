package com.visual.face.search.core.utils;

public class ArrayUtil {

    public static double [] floatToDouble(float[] input){
        if (input == null){
            return null;
        }
        double[] output = new double[input.length];
        for (int i = 0; i < input.length; i++){
            output[i] = input[i];
        }
        return output;
    }

    public static float [] doubleToFloat(double[] input){
        if (input == null){
            return null;
        }
        float[] output = new float[input.length];
        for (int i = 0; i < input.length; i++){
            output[i] = Double.valueOf(input[i]).floatValue();
        }
        return output;
    }

    public static float[] division(float[] input, float division){
        float[] output = new float[input.length];
        for(int i=0; i< input.length; i++){
            output[i] = input[i] / division;
        }
        return output;
    }

    public static double matrixNorm(float[] matrix){
        return matrixNorm(new double[][]{floatToDouble(matrix)});
    }

    public static double matrixNorm(double[] matrix){
        return matrixNorm(new double[][]{matrix});
    }

    public static double matrixNorm(double[][] matrix){
        double sum=0.0;
        for(double[] temp1:matrix){
            for(double temp2:temp1){
                sum+=Math.pow(temp2,2);
            }
        }
        return Math.sqrt(sum);
    }

}
