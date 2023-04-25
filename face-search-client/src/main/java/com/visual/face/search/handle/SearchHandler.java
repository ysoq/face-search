package com.visual.face.search.handle;

import com.alibaba.fastjson.TypeReference;
import com.visual.face.search.common.Api;
import com.visual.face.search.http.HttpClient;
import com.visual.face.search.model.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SearchHandler extends BaseHandler<SearchHandler>{

    /**实例对象**/
    private final static Map<String, SearchHandler> ins = new ConcurrentHashMap<>();

    /**
     * 构建样本对象
     * @param serverHost        服务地址
     * @param namespace         命名空间
     * @param collectionName    集合名称
     * @return
     */
    public static SearchHandler build(String serverHost, String namespace, String collectionName){
        String key = serverHost+"|_|"+namespace + "|_|" + collectionName;
        if(!ins.containsKey(key)){
            synchronized (SearchHandler.class){
                if(!ins.containsKey(key)){
                    ins.put(key, new SearchHandler().setServerHost(serverHost)
                            .setNamespace(namespace).setCollectionName(collectionName));
                }
            }
        }
        return ins.get(key);
    }

    /**
     * 人脸搜索
     * @param search 搜索条件
     * @return  获取当前匹配的列表
     */
    public Response<List<SearchRep>> search(Search search){
        SearchReq searchReq = SearchReq.build(namespace, collectionName)
                .setImageBase64(search.getImageBase64())
                .setMaxFaceNum(search.getMaxFaceNum())
                .setLimit(search.getLimit())
                .setConfidenceThreshold(search.getConfidenceThreshold())
                .setFaceScoreThreshold(search.getFaceScoreThreshold());
        return HttpClient.post(Api.getUrl(this.serverHost, Api.visual_search), searchReq, new TypeReference<Response<List<SearchRep>>>(){});
    }
}
