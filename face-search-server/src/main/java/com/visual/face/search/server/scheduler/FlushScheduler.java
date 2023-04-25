//package com.visual.face.search.server.scheduler;
//
//import io.milvus.param.R;
//import io.milvus.grpc.FlushResponse;
//import io.milvus.client.MilvusServiceClient;
//import io.milvus.param.collection.FlushParam;
//import io.milvus.param.collection.HasCollectionParam;
//import com.visual.face.search.server.utils.VTableCache;
//import com.alibaba.proxima.be.client.ProximaSearchClient;
//import com.visual.face.search.server.engine.api.SearchEngine;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import javax.annotation.Resource;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.scheduling.annotation.Scheduled;
//
///**
// * 用于刷新数据，Milvus不刷新数据可能导致数据丢失
// */
//@Component
//public class FlushScheduler {
//
//    private static final Integer SUCCESS_STATUE = 0;
//    public Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Resource
//    private SearchEngine searchEngine;
//    @Value("${visual.scheduler.flush.enable:true}")
//    private boolean enable;
//
//    @Scheduled(fixedDelayString = "${visual.scheduler.flush.interval:300000}")
//    public void flush(){
//        if(enable){
//            Object client = searchEngine.getEngine();
//            if(client instanceof MilvusServiceClient){
//                this.flushMilvus((MilvusServiceClient) client);
//            }else if(client instanceof ProximaSearchClient){
//                this.flushProxima((ProximaSearchClient) client);
//            }
//        }
//    }
//
//    /**
//     * 刷新数据到数据库中
//     * @param client
//     */
//    private void flushMilvus(MilvusServiceClient client){
//        VTableCache.getVectorTables().forEach((vectorTable) -> {
//            try {
//                HasCollectionParam requestParam = HasCollectionParam.newBuilder().withCollectionName(vectorTable).build();
//                boolean exist = client.hasCollection(requestParam).getData();
//                if(exist){
//                    FlushParam flushParam = FlushParam.newBuilder().addCollectionName(vectorTable).build();
//                    R<FlushResponse> response = client.flush(flushParam);
//                    if(SUCCESS_STATUE.equals(response.getStatus())){
//                        VTableCache.remove(vectorTable);
//                        logger.info("flushMilvus success: table is {}", vectorTable);
//                    }else{
//                        throw new RuntimeException("FlushResponse Error");
//                    }
//                }
//            }catch (Exception e){
//                logger.error("flushMilvus error:", e);
//            }
//        });
//    }
//
//    /**
//     * 刷新数据到数据库中
//     * @param client
//     */
//    private void flushProxima(ProximaSearchClient client){
//
//    }
//
//}
