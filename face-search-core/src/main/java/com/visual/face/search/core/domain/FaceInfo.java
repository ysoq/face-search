package com.visual.face.search.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class FaceInfo implements Comparable<FaceInfo>, Serializable {
    /**人脸分数**/
    public float score;
    /**人脸旋转角度**/
    public float angle;
    /**人脸框**/
    public FaceBox box;
    /**人脸关键点**/
    public Points points;
    /**人脸特征向量**/
    public Embedding embedding;
    /**人脸属性信息**/
    public Attribute attribute;

    /**
     * 构造函数
     * @param score     人脸分数
     * @param box       人脸框
     * @param points    人脸关键点
     * @param angle     人脸旋转角度
     * @param embedding 人脸特征向量
     */
    private FaceInfo(float score, FaceBox box, Points points, float angle, Embedding embedding) {
        this.score = score;
        this.angle = angle;
        this.box = box;
        this.points = points;
        this.embedding = embedding;
    }

    /**
     * 构造一个人脸信息
     * @param score     人脸分数
     * @param box       人脸框
     * @param points    人脸关键点
     * @param angle     人脸旋转角度
     */
    public static FaceInfo build(float score, float angle, FaceBox box, Points points){
        return new FaceInfo(score, box, points, angle, null);
    }

    /**
     * 构造一个人脸信息
     * @param score     人脸分数
     * @param box       人脸框
     * @param points    人脸关键点
     * @param angle     人脸旋转角度
     * @param embedding 人脸特征向量
     */
    public static FaceInfo build(float score, float angle, FaceBox box, Points points, Embedding embedding){
        return new FaceInfo(score, box, points, angle, embedding);
    }

    /**
     * 判断两个框的重叠率
     * @param that
     * @return
     */
    public double iou(FaceInfo that){
        float areaA = this.box.area();
        float areaB = that.box.area();
        float wTotal = Math.max(this.box.x2(), that.box.x2()) - Math.min(this.box.x1(), that.box.x1());
        float hTotal = Math.max(this.box.y2(), that.box.y2()) - Math.min(this.box.y1(), that.box.y1());
        float wOverlap = wTotal - this.box.width() - that.box.width();
        float hOverlap = hTotal - this.box.height() - that.box.height();
        float areaOverlap = (wOverlap >= 0 || hOverlap >= 0) ? 0 : wOverlap * hOverlap;
        return 1.0 * areaOverlap / (areaA + areaB - areaOverlap);
    }

    /**
     * 对人脸框进行旋转对应的角度
     * @return
     */
    public FaceBox rotateFaceBox(){
        return this.box.rotate(this.angle);
    }

    @Override
    public int compareTo(FaceInfo that) {
        return Float.compare(that.score, this.score);
    }

    /**
     * 关键点
     */
    public static class Point implements Serializable {
        /**坐标X的值**/
        public float x;
        /**坐标Y的值**/
        public float y;

        /**
         * 构造函数
         * @param x 坐标X的值
         * @param y 坐标Y的值
         */
        private Point(float x, float y){
            this.x = x;
            this.y = y;
        }

        /**
         * 构造一个点
         * @param x 坐标X的值
         * @param y 坐标Y的值
         * @return
         */
        public static Point build(float x, float y){
            return new Point(x, y);
        }

        /**
         * 对点进行中心旋转
         * @param center    中心点
         * @param angle     旋转角度
         * @return  旋转后的角
         */
        public Point rotation(Point center, float angle){
            double k = Math.toRadians(angle);
            float nx1 = (float) ((this.x - center.x) * Math.cos(k) + (this.y - center.y) * Math.sin(k) + center.x);
            float ny1 = (float) (-(this.x - center.x) * Math.sin(k) + (this.y - center.y) * Math.cos(k) + center.y);
            return new Point(nx1, ny1);
        }

        /**
         * 计算两点之间的距离
         * @param that  点
         * @return  距离
         */
        public float distance(Point that){
            return (float) Math.sqrt(Math.pow((this.x-that.x), 2)+Math.pow((this.y-that.y), 2));
        }
    }

    /**
     * 关键点集合
     */
    public static class Points extends ArrayList<Point>{

        /**
         * 构建一个集合
         * @return
         */
        public static Points build(){
            return new Points();
        }

        /**
         * 添加点
         * @param point
         * @return
         */
        public Points add(Point ...point){
            super.addAll(Arrays.asList(point));
            return this;
        }

        /**
         * 转换为double数组
         * @return
         */
        public double[][] toDoubleArray(){
            double[][] arr = new double[this.size()][2];
            for(int i=0; i< this.size(); i++){
                arr[i][0] = this.get(i).x;
                arr[i][1] = this.get(i).y;
            }
            return arr;
        }

        /**
         * 对点进行中心旋转
         * @param center    中心点
         * @param angle     旋转角度
         * @return  旋转后的角
         */
        public Points rotation(Point center, float angle){
            Points points = build();
            for(Point item : this){
                points.add(item.rotation(center, angle));
            }
            return points;
        }

        /**
         * 加法操作，对所有的点都加上point的值
         * @param point
         * @return
         */
        public Points operateAdd(Point point){
            Points points = build();
            for(Point item : this){
                float x = item.x + point.x;
                float y = item.y + point.y;
                points.add(Point.build(x, y));
            }
            return points;
        }

        /**
         * 减法操作，对所有的点都加上point的值
         * @param point
         * @return
         */
        public Points operateSubtract(Point point){
            Points points = build();
            for(Point item : this){
                float x = item.x - point.x;
                float y = item.y - point.y;
                points.add(Point.build(x, y));
            }
            return points;
        }

        /**
         * 选择关键点
         * @param indexes   关键点索引号
         * @return  关键点集合
         */
        public Points select(int ...indexes){
            Points points = build();
            for(int index : indexes){
                points.add(this.get(index));
            }
            return points;
        }

        /**
         * 乘法操作，对所有的点都乘法scale的值
         * @param scale
         * @return
         */
        public Points operateMultiply(float scale){
            Points points = build();
            for(Point item : this){
                float x = item.x * scale;
                float y = item.y * scale;
                points.add(Point.build(x, y));
            }
            return points;
        }
    }

    /**
     * 标准坐标系下的人脸框
     */
    public static class FaceBox implements Serializable {
        /**左上角坐标值**/
        public Point leftTop;
        /**右上角坐标**/
        public Point rightTop;
        /**右下角坐标**/
        public Point rightBottom;
        /**左下角坐标**/
        public Point leftBottom;

        /**
         * 构造函数
         * @param leftTop       左上角坐标值
         * @param rightTop      右上角坐标
         * @param rightBottom   右下角坐标
         * @param leftBottom    左下角坐标
         */
        public FaceBox(Point leftTop, Point rightTop, Point rightBottom, Point leftBottom) {
            this.leftTop = leftTop;
            this.rightTop = rightTop;
            this.rightBottom = rightBottom;
            this.leftBottom = leftBottom;
        }

        /**
         * 构造函数
         * @param x1  左上角坐标X的值
         * @param y1  左上角坐标Y的值
         * @param x2  右下角坐标X的值
         * @param y2  右下角坐标Y的值
         */
        private FaceBox(float x1, float y1, float x2, float y2){
            this.leftTop = Point.build(x1, y1);
            this.rightTop = Point.build(x2, y1);
            this.rightBottom = Point.build(x2, y2);
            this.leftBottom = Point.build(x1, y2);
        }

        /**
         * 构造一个人脸框
         * @param x1  左上角坐标X的值
         * @param y1  左上角坐标Y的值
         * @param x2  右下角坐标X的值
         * @param y2  右下角坐标Y的值
         */
        public static FaceBox build(float x1, float y1, float x2, float y2){
            return new FaceBox((int)x1,(int)y1,(int)x2,(int)y2);
        }

        /**
         * x的最小坐标
         * @return
         */
        public float x1(){
            return Math.min(Math.min(Math.min(leftTop.x, rightTop.x), rightBottom.x), leftBottom.x);
        }

        /**
         * y的最小坐标
         * @return
         */
        public float y1(){
            return Math.min(Math.min(Math.min(leftTop.y, rightTop.y), rightBottom.y), leftBottom.y);
        }

        /**
         * x的最大坐标
         * @return
         */
        public float x2(){
            return Math.max(Math.max(Math.max(leftTop.x, rightTop.x), rightBottom.x), leftBottom.x);
        }

        /**
         * y的最大坐标
         * @return
         */
        public float y2(){
            return Math.max(Math.max(Math.max(leftTop.y, rightTop.y), rightBottom.y), leftBottom.y);
        }

        /**
         * 判断当前的人脸框是否是标准的人脸框，即非旋转后的人脸框。
         * @return 否是标准的人脸框
         */
        public boolean normal(){
            if((int)leftTop.x == (int)leftBottom.x && (int)leftTop.y == (int)rightTop.y){
                if((int)rightBottom.x == (int)rightTop.x && (int)rightBottom.y == (int)leftBottom.y){
                    return true;
                }
            }
            return false;
        }

        /**
         * 获取宽度
         * @return
         */
        public float width(){
            return (float) Math.sqrt(Math.pow((rightTop.x-leftTop.x), 2)+Math.pow((rightTop.y-leftTop.y), 2));
        }

        /**
         * 获取高度
         * @return
         */
        public float height(){
            return (float) Math.sqrt(Math.pow((rightTop.x-rightBottom.x), 2)+Math.pow((rightTop.y-rightBottom.y), 2));
        }

        /**
         * 获取面积
         * @return
         */
        public float area(){
            return this.width() * this.height();
        }

        /**
         * 中心点坐标
         * @return
         */
        public Point center(){
            return Point.build((rightTop.x + leftBottom.x) / 2, (rightTop.y + leftBottom.y) / 2);
        }

        /**
         * 对人脸框进行旋转对应的角度
         * @param angle 旋转角
         * @return
         */
        public FaceBox rotate(float angle){
            Point center = this.center();
            Point rPoint1 = this.leftTop.rotation(center, angle);
            Point rPoint2 = this.rightTop.rotation(center, angle);
            Point rPoint3 = this.rightBottom.rotation(center, angle);
            Point rPoint4 = this.leftBottom.rotation(center, angle);
            return new FaceBox(rPoint1, rPoint2, rPoint3, rPoint4);
        }

        /**
         * 中心缩放
         * @param scale
         * @return
         */
        public FaceBox scaling(float scale){
            //p1-p3
            float length_p1_p3 = leftTop.distance(rightBottom);
            float x_diff_p1_p3 = leftTop.x-rightBottom.x;
            float y_diff_p1_p3 = leftTop.y-rightBottom.y;
            float change_p1_p3 = length_p1_p3 * (1-scale);
            float change_x_p1_p3 = change_p1_p3 * x_diff_p1_p3 / length_p1_p3 / 2;
            float change_y_p1_p3 = change_p1_p3 * y_diff_p1_p3 / length_p1_p3 / 2;
            //p2-p4
            float length_p2_p4 = rightTop.distance(leftBottom);
            float x_diff_p2_p4 = rightTop.x-leftBottom.x;
            float y_diff_p2_p4 = rightTop.y-leftBottom.y;
            float change_p2_p4 = length_p2_p4 * (1-scale);
            float change_x_p2_p4 = change_p2_p4 * x_diff_p2_p4 / length_p2_p4 / 2;
            float change_y_p2_p4 = change_p2_p4 * y_diff_p2_p4 / length_p2_p4 / 2;
            //构造人脸框
            return new FaceBox(
                    new Point(leftTop.x - change_x_p1_p3,  leftTop.y - change_y_p1_p3),
                    new Point(rightTop.x - change_x_p2_p4,  rightTop.y - change_y_p2_p4),
                    new Point(rightBottom.x + change_x_p1_p3,  rightBottom.y + change_y_p1_p3),
                    new Point(leftBottom.x + change_x_p2_p4,  leftBottom.y + change_y_p2_p4)
            );
        }
    }

    /**
     * 人脸特征向量
     */
    public static class Embedding implements Serializable {
        /**当前图片的base64编码值**/
        public String image;
        /**当前图片的人脸向量信息**/
        public float[] embeds;

        /**
         * 构造函数
         * @param image     前图片的base64编码值
         * @param embeds    当前图片的人脸向量信息
         */
        private Embedding(String image, float[] embeds){
            this.image = image;
            this.embeds = embeds;
        }

        /**
         * 构建人脸特征向量
         * @param image     前图片的base64编码值
         * @param embeds    当前图片的人脸向量信息
         */
        public static Embedding build(String image, float[] embeds){
            return new Embedding(image, embeds);
        }
    }

    /**
     * 人脸属性信息
     */
    public static class Attribute implements Serializable {
        public Integer age;
        public Integer gender;

        /**
         * 构造函数
         * @param gender     前图片的base64编码值
         * @param age        当前图片的人脸向量信息
         */
        private Attribute(Gender gender, Integer age) {
            this.age = age;
            this.gender = null == gender ? -1 : gender.getCode();
        }

        /**
         * 获取枚举值
         * @return
         */
        public Gender valueOfGender(){
            return Gender.valueOf(this.gender);
        }

        /**
         * 构建人脸属性信息
         * @param gender     前图片的base64编码值
         * @param age        当前图片的人脸向量信息
         */
        public static Attribute build(Gender gender, Integer age){
            return new Attribute(gender, age);
        }
    }

    public static enum  Gender {
        MALE(0),        //男性
        FEMALE(1),      //女性
        UNKNOWN(-1);    //未知

        private int code;

        Gender(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public static Gender valueOf(Integer code) {
            code = null == code ? -1 : code;
            if(code == 0){
                return MALE;
            }
            if(code == 1){
                return FEMALE;
            }
            return UNKNOWN;
        }
    }
}
