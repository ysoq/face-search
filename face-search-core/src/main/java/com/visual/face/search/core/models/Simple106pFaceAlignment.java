package com.visual.face.search.core.models;

import com.visual.face.search.core.base.FaceAlignment;
import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.utils.AlignUtil;
import org.opencv.core.Mat;

import java.util.Map;

public class Simple106pFaceAlignment implements FaceAlignment {

    /**矫正的偏移**/
    private final static double x_offset = 0;
    private final static double y_offset = -8;

    /**对齐矩阵**/
    private final static double[][] dst_points = new double[][]{
            {x_offset + 55.9033,   y_offset + 109.5666},
            {x_offset + 11.1944,   y_offset + 34.8529},
            {x_offset + 23.4634,   y_offset + 84.9067},
            {x_offset + 26.6072,   y_offset + 89.7229},
            {x_offset + 30.1978,   y_offset + 94.2239},
            {x_offset + 34.1534,   y_offset + 98.4295},
            {x_offset + 38.4549,   y_offset + 102.5791},
            {x_offset + 43.3488,   y_offset + 106.149},
            {x_offset + 49.0901,   y_offset + 108.6559},
            {x_offset + 11.1362,   y_offset + 40.8701},
            {x_offset + 11.5769,   y_offset + 46.7289},
            {x_offset + 12.4184,   y_offset + 52.4678},
            {x_offset + 13.5954,   y_offset + 58.0757},
            {x_offset + 15.0485,   y_offset + 63.6245},
            {x_offset + 16.6973,   y_offset + 69.0877},
            {x_offset + 18.5132,   y_offset + 74.5257},
            {x_offset + 20.7569,   y_offset + 79.817},
            {x_offset + 99.9696,   y_offset + 33.7004},
            {x_offset + 88.8002,   y_offset + 84.668},
            {x_offset + 85.5828,   y_offset + 89.5583},
            {x_offset + 81.9019,   y_offset + 94.1172},
            {x_offset + 77.8414,   y_offset + 98.3508},
            {x_offset + 73.5045,   y_offset + 102.5337},
            {x_offset + 68.579 ,   y_offset + 106.1167},
            {x_offset + 62.8033,   y_offset + 108.6437},
            {x_offset + 100.1725,  y_offset + 39.7505},
            {x_offset + 99.9274,   y_offset + 45.6823},
            {x_offset + 99.2375,   y_offset + 51.4997},
            {x_offset + 98.2293,   y_offset + 57.1924},
            {x_offset + 96.9149,   y_offset + 62.8431},
            {x_offset + 95.4353,   y_offset + 68.4604},
            {x_offset + 93.744,    y_offset + 74.0441},
            {x_offset + 91.5361,   y_offset + 79.4612},
            {x_offset + 32.9994,   y_offset + 44.2254},
            {x_offset + 34.125,    y_offset + 40.0104},
            {x_offset + 24.6554,   y_offset + 40.2778},
            {x_offset + 28.1858,   y_offset + 42.7984},
            {x_offset + 38.3286,   y_offset + 43.9908},
            {x_offset + 34.1243,   y_offset + 40.0099},
            {x_offset + 43.1474,   y_offset + 43.3184},
            {x_offset + 34.0167,   y_offset + 36.9718},
            {x_offset + 28.7573,   y_offset + 37.653},
            {x_offset + 39.3212,   y_offset + 38.9433},
            {x_offset + 16.2452,   y_offset + 28.4257},
            {x_offset + 22.6223,   y_offset + 27.7422},
            {x_offset + 29.3128,   y_offset + 28.5235},
            {x_offset + 43.2792,   y_offset + 33.2877},
            {x_offset + 36.3115,   y_offset + 30.5346},
            {x_offset + 22.1746,   y_offset + 23.7718},
            {x_offset + 29.9789,   y_offset + 23.9014},
            {x_offset + 44.2766,   y_offset + 29.9646},
            {x_offset + 37.5711,   y_offset + 26.3851},
            {x_offset + 41.0353,   y_offset + 85.7782},
            {x_offset + 55.2768,   y_offset + 93.9337},
            {x_offset + 48.1265,   y_offset + 86.7238},
            {x_offset + 44.5563,   y_offset + 89.7497},
            {x_offset + 49.021,    y_offset + 92.7902},
            {x_offset + 62.5841,   y_offset + 86.3203},
            {x_offset + 66.422,    y_offset + 89.1865},
            {x_offset + 61.7114,   y_offset + 92.4944},
            {x_offset + 55.186,    y_offset + 87.3286},
            {x_offset + 70.197,    y_offset + 84.9727},
            {x_offset + 55.1775,   y_offset + 85.9714},
            {x_offset + 51.3597,   y_offset + 81.3182},
            {x_offset + 45.7722,   y_offset + 83.1928},
            {x_offset + 43.166,    y_offset + 85.9001},
            {x_offset + 48.134,    y_offset + 85.7435},
            {x_offset + 58.739,    y_offset + 81.1461},
            {x_offset + 64.7266,   y_offset + 82.6822},
            {x_offset + 67.9371,   y_offset + 85.2036},
            {x_offset + 62.5068,   y_offset + 85.3484},
            {x_offset + 55.0752,   y_offset + 82.203},
            {x_offset + 54.1078,   y_offset + 41.0726},
            {x_offset + 54.1622,   y_offset + 50.449},
            {x_offset + 54.2472,   y_offset + 59.7894},
            {x_offset + 47.8631,   y_offset + 43.7787},
            {x_offset + 45.3862,   y_offset + 63.1931},
            {x_offset + 43.1486,   y_offset + 70.3748},
            {x_offset + 46.5561,   y_offset + 72.9832},
            {x_offset + 50.3907,   y_offset + 74.013},
            {x_offset + 54.7346,   y_offset + 75.5877},
            {x_offset + 60.6909,   y_offset + 43.5412},
            {x_offset + 63.7283,   y_offset + 62.8403},
            {x_offset + 66.4094,   y_offset + 69.925},
            {x_offset + 63.0125,   y_offset + 72.6655},
            {x_offset + 59.1424,   y_offset + 73.8402},
            {x_offset + 54.3016,   y_offset + 69.1335},
            {x_offset + 75.6758,   y_offset + 44.0704},
            {x_offset + 74.6523,   y_offset + 39.8236},
            {x_offset + 65.424,    y_offset + 43.1928},
            {x_offset + 70.2963,   y_offset + 43.8439},
            {x_offset + 80.5739,   y_offset + 42.5855},
            {x_offset + 74.6507,   y_offset + 39.8237},
            {x_offset + 84.2044,   y_offset + 40.0282},
            {x_offset + 74.6292,   y_offset + 36.7377},
            {x_offset + 69.2614,   y_offset + 38.7215},
            {x_offset + 79.9957,   y_offset + 37.425},
            {x_offset + 63.7995,   y_offset + 32.7549},
            {x_offset + 71.0128,   y_offset + 29.9861},
            {x_offset + 78.3723,   y_offset + 27.9521},
            {x_offset + 85.5398,   y_offset + 27.2929},
            {x_offset + 92.6151,   y_offset + 28.1619},
            {x_offset + 62.768,    y_offset + 29.3678},
            {x_offset + 69.733,    y_offset + 25.7456},
            {x_offset + 77.7338,   y_offset + 23.3563},
            {x_offset + 86.0268,   y_offset + 23.3427}};

    @Override
    public ImageMat inference(ImageMat imageMat, FaceInfo.Points imagePoint, Map<String, Object> params) {
        double [][] image_points;
        if(imagePoint.size() == 106){
            image_points = imagePoint.toDoubleArray();
        }else{
            throw new RuntimeException("need 106 point, but get "+ imagePoint.size());
        }
        Mat alignMat = AlignUtil.alignedImage(imageMat.toCvMat(), image_points, 112, 112, dst_points);
        return ImageMat.fromCVMat(alignMat);
    }

}
