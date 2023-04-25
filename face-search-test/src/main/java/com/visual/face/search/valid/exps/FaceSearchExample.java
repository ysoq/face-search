package com.visual.face.search.valid.exps;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.nio.file.Paths;
import org.opencv.highgui.HighGui;
import com.visual.face.search.model.*;
import com.visual.face.search.FaceSearch;
import com.visual.face.search.utils.JsonUtil;
import com.visual.face.search.utils.Base64Util;
import com.visual.face.search.valid.utils.DrawImage;
import org.apache.commons.codec.digest.DigestUtils;

public class FaceSearchExample {

    static{ nu.pattern.OpenCV.loadShared(); }
    //本地开发模式
    public static String serverHost = "http://127.0.0.1:8080";
    //docker部署模式
    //public static String serverHost = "http://127.0.0.1:56789";
    //远程测试服务
    //public static String serverHost = "http://face-search.diven.nat300.top";
    public static String namespace = "namespace_1";
    public static String collectionName = "collect_20211201_v11";
    public static FaceSearch faceSearch = FaceSearch.build(serverHost, namespace, collectionName);

    /**集合创建*/
    public static void collect(){
        //样本属性字段
        List<FiledColumn> sampleColumns = new ArrayList<>();
        sampleColumns.add(FiledColumn.build().setName("name").setDataType(FiledDataType.STRING).setComment("姓名"));
        //人脸属性字段
        List<FiledColumn> faceColumns = new ArrayList<>();
        faceColumns.add(FiledColumn.build().setName("label").setDataType(FiledDataType.STRING).setComment("标签1"));
        //待创建的人脸库信息
        Collect collect = Collect.build().setCollectionComment("人脸库").setSampleColumns(sampleColumns).setFaceColumns(faceColumns);
        //删除集合
        Response<Boolean> deleteCollect =  faceSearch.collect().deleteCollect();
        System.out.println(deleteCollect);
        //创建结合
        Response<Boolean> createCollect =  faceSearch.collect().createCollect(collect);
        System.out.println(createCollect);
        //获取集合信息
        Response<CollectRep> getCollect =  faceSearch.collect().getCollect();
        System.out.println(getCollect);
    }

    /**添加样本数据*/
    public static void index(){
        String indexPath = "face-search-test/src/main/resources/image/validate/index";
        for(String name : Objects.requireNonNull(new File(indexPath).list())){
            System.out.println(name);
            File files[] = Paths.get(indexPath, name).toFile().listFiles();
            if(files.length > 0){
                //创建样本数据
                String sampleId = DigestUtils.md5Hex(name);
                Sample sample = Sample.build(sampleId);
                KeyValues sampleData = KeyValues.build();
                sampleData.add(KeyValue.build("name", name));
                sample.setSampleData(sampleData);
                Response<Boolean> createSample = faceSearch.sample().createSample(sample);
                if(createSample.ok()){
                    for(File image : files){
                        KeyValues faceData = KeyValues.build();
                        faceData.add(KeyValue.build("label", "标签-" + name));
                        String imageBase64 = Base64Util.encode(image.getAbsolutePath());
                        Face face = Face.build(sampleId).setFaceData(faceData).setImageBase64(imageBase64)
                                .setMinConfidenceThresholdWithThisSample(50f)
                                .setMaxConfidenceThresholdWithOtherSample(50f);
                        Response<FaceRep> createFace = faceSearch.face().createFace(face);
                        System.out.println("createFace:" + createFace);
                    }
                }
            }
        }
    }

    /**搜索*/
    public static void search() {
        String searchPath = "face-search-test/src/main/resources/image/validate/search";
        for(File image : Objects.requireNonNull(new File(searchPath).listFiles())){
            String imageBase64 = Base64Util.encode(image.getAbsolutePath());
            Long s = System.currentTimeMillis();
            Response<List<SearchRep>> listResponse = faceSearch.search()
                .search(Search.build(imageBase64)
                .setConfidenceThreshold(50f)        //最小置信分：50
                .setMaxFaceNum(10).setLimit(1)
            );
            Long e = System.currentTimeMillis();
            System.out.println("search cost:" + (e-s)+"ms");
            System.out.println(JsonUtil.toString(listResponse, true, false));

            DrawImage drawImage = DrawImage.build(image.getAbsolutePath());
            if(listResponse.ok()){
                List<SearchRep> reps = listResponse.getData();
                for(SearchRep rep : reps){
                    FaceLocation location = rep.getLocation();
                    drawImage.drawRect(new DrawImage.Rect(location.getX(), location.getY(), location.getW(), location.getH()), 2, Color.RED);
                    List<SampleFace> faces = rep.getMatch();
                    if(faces.size() > 0){
                        for(SampleFace face : faces){
                            int confidence = face.getConfidence().intValue();
                            String name = face.getSampleData().getString("name");
                            drawImage.drawText("姓名：" + name, new DrawImage.Point(location.getX() + 5, location.getY()+5), 14, Color.RED);
                            drawImage.drawText("分数：" + confidence, new DrawImage.Point(location.getX()+5, location.getY()+25), 14, Color.RED);
                        }
                    }else{
                        drawImage.drawText("未识别", new DrawImage.Point(location.getX() + 5, location.getY()+5), 14, Color.GREEN);
                    }
                }
            }

            HighGui.imshow("image", drawImage.toMat());
            HighGui.waitKey();
        }
        //退出程序
        System.exit(1);
    }

    /**main**/
    public static void main(String[] args) {
        collect();
        index();
        search();
    }

}
