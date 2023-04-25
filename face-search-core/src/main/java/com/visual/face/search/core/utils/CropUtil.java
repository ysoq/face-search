package com.visual.face.search.core.utils;

import java.util.List;
import java.util.ArrayList;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;
import com.visual.face.search.core.domain.FaceInfo;

/**
 * 图像裁剪工具
 */
public class CropUtil {

    /**
     * 根据4个点裁剪图像
     * @param image
     * @param faceBox
     * @return
     */
    public static Mat crop(Mat image, FaceInfo.FaceBox faceBox){
        Mat endM = null;
        Mat startM = null;
        Mat perspectiveTransform = null;
        try {
            List<Point> dest = new ArrayList<>();
            dest.add(new Point(faceBox.leftTop.x, faceBox.leftTop.y));
            dest.add(new Point(faceBox.rightTop.x, faceBox.rightTop.y));
            dest.add(new Point(faceBox.rightBottom.x, faceBox.rightBottom.y));
            dest.add(new Point(faceBox.leftBottom.x, faceBox.leftBottom.y));
            startM = Converters.vector_Point2f_to_Mat(dest);
            List<Point> ends = new ArrayList<>();
            ends.add(new Point(0, 0));
            ends.add(new Point(faceBox.width(), 0));
            ends.add(new Point(faceBox.width(), faceBox.height()));
            ends.add(new Point(0, faceBox.height()));
            endM = Converters.vector_Point2f_to_Mat(ends);
            perspectiveTransform = Imgproc.getPerspectiveTransform(startM, endM);
            Mat outputMat = new Mat((int)faceBox.height() , (int)faceBox.width(), CvType.CV_8UC4);
            Imgproc.warpPerspective(image, outputMat, perspectiveTransform, new Size((int)faceBox.width(), (int)faceBox.height()), Imgproc.INTER_CUBIC);
            return outputMat;
        }finally {
            if(null != endM){
                endM.release();
            }
            if(null != startM){
                startM.release();
            }
            if(null != perspectiveTransform){
                perspectiveTransform.release();
            }
        }
    }

}
