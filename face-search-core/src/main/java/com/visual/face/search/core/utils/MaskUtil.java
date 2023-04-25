package com.visual.face.search.core.utils;

import com.visual.face.search.core.domain.FaceInfo;
import com.visual.face.search.core.domain.ImageMat;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class MaskUtil {

    /**添加遮罩层所需要的索引号:InsightCoordFaceKeyPoint**/
    private static int [] MASK_106_IST_ROUND_INDEX = new int[]{
            1,9,10,11,12,13,14,15,16,2,3,4,5,6,7,8,0,
            24,23,22,21,20,19,18,32,31,30,29,28,27,26,25,17,
            101,105,104,103,102,50,51,49,48,43
    };

    /**
     * 添加遮罩层
     * @param image         原始图像
     * @param pts           指定不不需要填充的区域
     * @param release   是否释放参数image
     * @return
     */
    public static Mat mask(Mat image, List<MatOfPoint> pts, boolean release){
        Mat pattern = null;
        try {
            pattern = MatOfPoint.zeros(image.size(), CvType.CV_8U);
            Imgproc.fillPoly(pattern, pts, new Scalar(1,1,1));
            Mat dst = new Mat();
            image.copyTo(dst, pattern);
            return dst;
        }finally {
            if(null != pattern){
                pattern.release();
            }
            if(release && null != pts){
                for(MatOfPoint pt : pts){
                    pt.release();
                }
            }
            if(release && null != image){
                image.release();
            }
        }
    }

    /**
     * 添加遮罩层
     * @param image         原始图像
     * @param fillPoints    指定不不需要填充的区域的点
     * @param release   是否释放参数image
     * @return
     */
    public static Mat mask(Mat image, Point[] fillPoints, boolean release){
        List<MatOfPoint> pts = null;
        try {
            pts = new ArrayList<>();
            pts.add(new MatOfPoint(fillPoints));
            return mask(image, pts, false);
        }finally {
            if(null != pts){
                for(MatOfPoint pt : pts){
                    pt.release();
                }
            }
            if(release && null != image){
                image.release();
            }
        }
    }

    /**
     * 添加遮罩层:InsightCoordFaceKeyPoint
     * @param image     原始图像
     * @param points    人脸标记点
     * @param release   是否释放参数image
     * @return
     */
    public static Mat maskFor106InsightCoordModel(Mat image, FaceInfo.Points points, boolean release){
        try {
            Point[] fillPoints = PointUtil.convert(points.select(MASK_106_IST_ROUND_INDEX));
            return mask(image, fillPoints, false);
        }finally {
            if(release && null != image){
                image.release();
            }
        }
    }

    /**
     * 添加遮罩层:InsightCoordFaceKeyPoint
     * @param image     原始图像
     * @param points    人脸标记点
     * @param release       是否释放参数image
     * @return
     */
    public static ImageMat maskFor106InsightCoordModel(ImageMat image, FaceInfo.Points points, boolean release){
        try {
            Mat mat = maskFor106InsightCoordModel(image.toCvMat(), points, false);
            return ImageMat.fromCVMat(mat);
        }finally {
            if(release && null != image){
                image.release();
            }
        }
    }

}
