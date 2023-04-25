package com.visual.face.search.unit;

import com.visual.face.search.FaceSearch;
import com.visual.face.search.base.BaseTest;
import com.visual.face.search.model.*;
import com.visual.face.search.utils.Base64Util;

import java.io.File;
import java.util.*;

public class FaceSearchTest extends BaseTest {

    public static String serverHost = "http://127.0.0.1:8080";
    public static String namespace = "n1";
    public static String collectionName = "c000002";
    public static FaceSearch faceSearch = FaceSearch.build(serverHost, namespace, collectionName);

    public static void collect(){
        List<FiledColumn> sampleColumns = new ArrayList<>();
        sampleColumns.add(FiledColumn.build().setName("name").setDataType(FiledDataType.STRING).setComment("姓名"));
        sampleColumns.add(FiledColumn.build().setName("age").setDataType(FiledDataType.INT).setComment("年龄"));
        List<FiledColumn> faceColumns = new ArrayList<>();
        faceColumns.add(FiledColumn.build().setName("label_1").setDataType(FiledDataType.STRING).setComment("标签1"));
        faceColumns.add(FiledColumn.build().setName("label_2").setDataType(FiledDataType.STRING).setComment("标签1"));
        Collect collect = Collect.build().setCollectionComment("人脸库").setSampleColumns(sampleColumns).setFaceColumns(faceColumns);

        Response<Boolean> deleteCollect =  faceSearch.collect().deleteCollect();
        System.out.println(deleteCollect);

        Response<Boolean> createCollect =  faceSearch.collect().createCollect(collect);
        System.out.println(createCollect);

        Response<CollectRep> getCollect =  faceSearch.collect().getCollect();
        System.out.println(getCollect);

        Response<List<CollectRep>> collectList =  faceSearch.collect().collectList();
        System.out.println(collectList);
    }

    public static List<String> sample(){
        Set<String> allIds = new HashSet<>();
        List<String> updateIds = new ArrayList<>();
        List<String> deleteIds = new ArrayList<>();
        for(int i=0; i< 20; i++){
            Sample sample = Sample.build(UUID.randomUUID().toString().toLowerCase().replace("-",""));
            KeyValues sampleData = KeyValues.build().add(KeyValue.build("name", "姓名"+i), KeyValue.build("age", new Random().nextInt(80)));
            sample.setSampleData(sampleData);
            Response<Boolean> createSample = faceSearch.sample().createSample(sample);
            System.out.println("createSample:"+i+":"+createSample);
            if(new Random().nextInt(10) <= 1){
                deleteIds.add(sample.getSampleId());
            }
            if(new Random().nextInt(10) <= 1){
                updateIds.add(sample.getSampleId());
            }
            allIds.add(sample.getSampleId());
        }
        for(String id : updateIds){
            Sample sample = Sample.build(id);
            KeyValues sampleData = KeyValues.build().add(KeyValue.build("name", "姓名"+ new Random().nextInt(10000)), KeyValue.build("age", new Random().nextInt(80)));
            sample.setSampleData(sampleData);
            Response<Boolean> updateSample = faceSearch.sample().updateSample(sample);
            System.out.println("updateSample:"+id+":"+updateSample);
        }

        for(String id : deleteIds){
            Response<Boolean> deleteSample = faceSearch.sample().deleteSample(id);
            System.out.println("deleteSample:"+id+":"+deleteSample);

        }

        for(String id : updateIds){
            Response<SampleRep> getSample = faceSearch.sample().getSample(id);
            System.out.println("getSample:"+id+":"+getSample);
        }

        Response<List<SampleRep>> getSample = faceSearch.sample().sampleList(0, 10, Order.asc);
        System.out.println("getSample:"+getSample);
        for(SampleRep item : getSample.getData()){
            System.out.println("getSample:item:"+item);
        }

        //删除不存在的样本
        allIds.removeAll(deleteIds);
        return new ArrayList<>(allIds);
    }



    public static void main(String[] args) {
        collect();
        sample();
    }


}
