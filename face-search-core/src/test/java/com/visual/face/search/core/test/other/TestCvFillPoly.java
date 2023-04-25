package com.visual.face.search.core.test.other;

import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import com.visual.face.search.core.utils.MaskUtil;
import com.visual.face.search.core.utils.PointUtil;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class TestCvFillPoly {

    //静态加载动态链接库
    static{ nu.pattern.OpenCV.loadShared(); }

    public static void main(String[] args) {
        String imagePath = "face-search-core/src/test/resources/images/faces/debug/debug_0007.jpg";
        Mat image = Imgcodecs.imread(imagePath);
        Point[] src_points = new Point[106];

        FaceInfo.Points points = FaceInfo.Points.build();
        for(int i=0; i< dst_points.length; i++){
            points.add(PointUtil.convert(new Point(dst_points[i][0] * 439 / 112, dst_points[i][1] * 439 / 112)));
        }

        Mat d = MaskUtil.maskFor106InsightCoordModel(image, points, false);


//        int [] indexes = new int[]{1,9,10,11,12,13,14,15,16,2,3,4,5,6,7,8,0,24,23,22,21,20,19,18,32,31,30,29,28,27,26,25,17,101,105,104,103,102,50,51,49,48,43};
//        Point[] fill_points = new Point[indexes.length];
//        for(int i=0; i< indexes.length; i++){
//            fill_points[i] = src_points[indexes[i]];
//        }
//
//        //        MatOfPoint.zeros(image.size(), CvType.CV_8U);
////        Mat pattern = new Mat(image.size(), CvType.CV_8U);
//
//        Mat pattern = MatOfPoint.zeros(image.size(), CvType.CV_8U);;
//        List<MatOfPoint> pts = new ArrayList<>();;
//        pts.add(new MatOfPoint(fill_points));
//        Imgproc.fillPoly(pattern, pts, new Scalar(1,1,1));
//
//        HighGui.imshow("Drawing a polygon", pattern);
//        HighGui.waitKey();
//
//        for (Point src_point : src_points) {
//            Imgproc.circle(image, src_point, 1, new Scalar(0, 0, 255), -1);
//        }
//        Mat d = new Mat();
//        image.copyTo(d, pattern);


        HighGui.imshow("fileName", d);
        HighGui.waitKey();
        System.exit(1);

//        Imgproc.fil
    }



    /**对齐矩阵**/
    private final static double[][] dst_points = new double[][]{
            {56.9405, 104.8443},
            {18.5795, 41.9579},
            {29.7909, 83.8938},
            {32.4892, 87.9299},
            {35.5363, 91.7113},
            {38.8522, 95.2713},
            {42.4044, 98.8245},
            {46.4592, 101.8917},
            {51.2277, 104.0554},
            {18.6211, 47.0351},
            {19.1112, 51.9579},
            {19.9591, 56.7764},
            {21.0883, 61.467},
            {22.4328, 66.0933},
            {23.9187, 70.6575},
            {25.5052, 75.2115},
            {27.4518, 79.6344},
            {94.3557, 40.9158},
            {84.5255, 83.7362},
            {81.8184, 87.8399},
            {78.7297, 91.68},
            {75.3339, 95.2649},
            {71.7154, 98.8306},
            {67.5886, 101.8988},
            {62.7335, 104.062},
            {94.4373, 46.0395},
            {94.149, 51.0301},
            {93.5035, 55.9094},
            {92.5847, 60.6884},
            {91.4355, 65.4221},
            {90.1689, 70.1432},
            {88.7174, 74.8307},
            {86.8375, 79.3739},
            {37.6054, 49.8439},
            {38.3254, 46.2809},
            {30.5494, 46.5533},
            {33.5341, 48.6793},
            {42.097, 49.5744},
            {38.3255, 46.2799},
            {46.1797, 48.9365},
            {38.3534, 43.7203},
            {33.9532, 44.3446},
            {42.8563, 45.3288},
            {23.6783, 36.8122},
            {29.0158, 35.7892},
            {34.6376, 36.2009},
            {46.5079, 40.1862},
            {40.5936, 37.8703},
            {28.5353, 32.615},
            {35.1155, 32.4559},
            {47.2983, 37.4058},
            {41.5737, 34.4037},
            {44.3619, 84.8402},
            {56.3115, 91.5205},
            {50.3433, 85.5404},
            {47.3686, 88.0585},
            {51.1102, 90.5446},
            {62.4342, 85.3069},
            {65.6106, 87.7116},
            {61.6732, 90.382},
            {56.2631, 86.0649},
            {68.7674, 84.2605},
            {56.1979, 85.0424},
            {53.0131, 81.1738},
            {48.3541, 82.7133},
            {46.1528, 84.9518},
            {50.31, 84.8129},
            {59.1781, 81.046},
            {64.1735, 82.3522},
            {66.8877, 84.454},
            {62.3321, 84.5486},
            {56.1101, 81.9197},
            {55.4506, 47.1509},
            {55.5035, 55.1007},
            {55.5601, 63.0179},
            {50.1072, 49.3489},
            {47.9679, 65.6062},
            {45.988, 71.6565},
            {48.8313, 73.9777},
            {52.0659, 74.8593},
            {55.7886, 76.272},
            {60.909, 49.2251},
            {63.4678, 65.3933},
            {65.718, 71.3635},
            {62.8521, 73.7614},
            {59.5402, 74.8053},
            {55.5967, 70.9364},
            {73.4902, 49.5798},
            {72.4674, 46.0168},
            {64.9145, 48.9078},
            {68.9848, 49.4388},
            {77.5852, 48.3019},
            {72.4663, 46.0169},
            {80.5914, 46.1361},
            {72.5983, 43.4159},
            {68.0961, 45.1217},
            {77.0717, 43.964},
            {63.9449, 40.2127},
            {69.945, 37.6809},
            {76.0014, 35.8078},
            {81.9096, 35.2657},
            {87.7462, 36.15},
            {63.0412, 37.4403},
            {68.8145, 34.2255},
            {75.4285, 32.0634},
            {82.3109, 32.0248}
    };
}
