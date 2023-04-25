package com.visual.face.search.engine.test;

import com.visual.face.search.engine.impl.OpenSearchEngine;
import com.visual.face.search.engine.model.MapParam;
import com.visual.face.search.engine.model.SearchResponse;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public class SearchEngineTest {

    public static RestHighLevelClient getOpenSearchClientImpl(){
//        final String hostName = "192.168.10.201";
        final String hostName = "172.16.36.229";
        final Integer hostPort = 9200;
        final String hostScheme = "https";
        final String userName = "admin";
        final String password = "admin";

        //认证参数
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

        //ssl设置
        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }}, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }

        //构建请求
        RestClientBuilder builder = RestClient.builder(new HttpHost(hostName, hostPort, hostScheme))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setSSLHostnameVerifier((hostname, session) -> true)
                        .setSSLContext(sslContext)
                        .setMaxConnTotal(10)
                        .setMaxConnPerRoute(10)
                );
        //构建client
        return new RestHighLevelClient(builder);
    }


    public static void main(String[] args) throws InterruptedException {
//        String index_name = "app-opensearch-index-001";
//        String index_name = "app-opensearch-index-002";
//        String index_name = "python-test-index2";
        String index_name = "visual_search_namespace_1_collect_20211201_v05_ohr6_vector";

        OpenSearchEngine engine = new OpenSearchEngine(getOpenSearchClientImpl());

        boolean exist = engine.exist(index_name);
        System.out.println(exist);
        if(exist){
//            boolean drop = engine.dropCollection(index_name);
//            System.out.println(drop);
        }

//        boolean create = engine.createCollection(index_name, MapParam.build());
//        System.out.println(create);
//
//        for(int i=0; i<10; i++){
//            float[] vectors = new float[512];
//            vectors[i] = 0.23333333f;
//            boolean insert = engine.insertVector(index_name, "simple-0001", String.valueOf(i), vectors);
//            System.out.println("insert="+insert);
//        }
//
//        Thread.sleep(2000);

//        boolean delete = engine.deleteVectorByKey(index_name, "0");
//        System.out.println(delete);

//        boolean delete1 = engine.deleteVectorByKey(index_name, Arrays.asList("1", "5", "9"));
//        System.out.println(delete1);


//        float[][] a = new float[2][];
//
        float[] vectors = new float[512];
        vectors[0] = 0.768888f;
        vectors[1] = 20000.768888f;
        vectors[162] = 33333f;
//        a[0] = vectors;
//
//        float[] vectors1 = new float[512];
//        vectors1[2] = 10000.54444f;
//        a[1] = vectors1;
//        SearchResponse searchResponse = engine.search(index_name, a, "cosinesimil",1);
//        System.out.println(searchResponse);

//        engine.searchCount(index_name, vectors, "", 1);
        float minScore = engine.searchMinScoreBySampleId(index_name, "d4395b36984926a1934a0f9b916b32d21", vectors, "cosinesimil");
        float maxScore = engine.searchMaxScoreBySampleId(index_name, "d4395b36984926a1934a0f9b916b32d21", vectors, "cosinesimil");

        System.out.println(minScore);
        System.out.println(maxScore);
        System.exit(1);

//        Object y = vectors;
//        Object[] y1 = (Object[]) y;
//        System.out.println(y1.length);
//
//        System.out.println(y.getClass().getComponentType());
//        System.out.println(y.getClass().isArray());
//        System.out.println(cc instanceof Float);
//        System.exit(1);
    }
}
