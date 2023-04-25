package com.visual.face.search.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.visual.face.search.model.Collect;
import com.visual.face.search.model.CollectRep;
import com.visual.face.search.model.MapParam;
import com.visual.face.search.model.Response;
import com.visual.face.search.utils.JsonUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpClient {

    /**编码方式*/
    private static final String ENCODING = "UTF-8";
    /**连接超时时间，10秒*/
    public static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
    /**socket连接超时时间，10秒*/
    public static final int DEFAULT_READ_TIMEOUT = 10 * 000;
    /**请求超时时间，60秒*/
    public static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 30 * 000;
    /**最大连接数,默认为2*/
    private static final int MAX_TOTAL = 8;
    /**设置指向特定路由的并发连接总数，默认为2*/
    private static final int MAX_PER_ROUTE = 4;

    private static RequestConfig requestConfig;
    private static PoolingHttpClientConnectionManager connectionManager;
    private static BasicCookieStore cookieStore;
    private static HttpClientBuilder httpBuilder;
    private static CloseableHttpClient httpClient;
    private static CloseableHttpClient httpsClient;
    private static SSLContext sslContext;

    /**
     * 创建SSLContext对象，用来绕过https证书认证实现访问。
     */
    static {
        try {
            sslContext = SSLContext.getInstance("TLS");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[] {tm}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化httpclient对象，以及在创建httpclient对象之前的一些自定义配置。
     */
    static {
        // 自定义配置信息
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(DEFAULT_READ_TIMEOUT)
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT)
                .build();
        //设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(sslContext))
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 设置cookie存储对像，在需要获取cookie信息时，可以使用这个对象。
        cookieStore = new BasicCookieStore();
        // 设置最大连接数
        connectionManager.setMaxTotal(MAX_TOTAL);
        // 设置路由并发数
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        httpBuilder = HttpClientBuilder.create();
        httpBuilder.setDefaultRequestConfig(requestConfig);
        httpBuilder.setConnectionManager(connectionManager);
        httpBuilder.setDefaultCookieStore(cookieStore);
        // 实例化http 和 https的对象。
        httpClient = httpBuilder.build();
        httpsClient = httpBuilder.build();
    }

    /**
     * post请求
     * @param url
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> post(String url, Object data) {
        return post(url, data, new TypeReference<Response<T>>() {});
    }

    /**
     * post请求
     * @param url
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> post(String url, Object data, TypeReference<Response<T>> type) {
        // 创建HTTP对象
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        // 设置请求头
        if(null != data){
            String json = data instanceof String ? String.valueOf(data) : JsonUtil.toString(data);
            StringEntity stringEntity = new StringEntity(json, ENCODING);
            stringEntity.setContentEncoding(ENCODING);
            httpPost.setEntity(stringEntity);
        }
        // 创建httpResponse对象
        CloseableHttpClient client = url.toLowerCase().startsWith("https") ? httpsClient : httpClient;
        try {
            CloseableHttpResponse httpResponse = client.execute(httpPost);
            String content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            return JsonUtil.toEntity(content, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * get
     * @param url
     * @param param
     * @param <T>
     * @return
     */
    public static <T> Response<T> get(String url, MapParam param) {
        return get(url, param, new TypeReference<Response<T>>() {});
    }

    /**
     * get
     * @param url
     * @param param
     * @param <T>
     * @return
     */
    public static <T> Response<T> get(String url, MapParam param, TypeReference<Response<T>> type) {
        try {
            //参数构建
            URIBuilder uriBuilder = new URIBuilder(url);
            if(null != param && !param.isEmpty()){
                List<NameValuePair> list = new LinkedList<>();
                for(String key : param.keySet()){
                    Object value = param.get(key);
                    if(null == value){
                        list.add(new BasicNameValuePair(key, null));
                    }else{
                        list.add(new BasicNameValuePair(key, String.valueOf(value)));
                    }
                }
                uriBuilder.setParameters(list);
            }
            //构建请求
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
            // 创建httpResponse对象
            CloseableHttpClient client = url.toLowerCase().startsWith("https") ? httpsClient : httpClient;
            CloseableHttpResponse httpResponse = client.execute(httpGet);
            String content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            return JsonUtil.toEntity(content, type);
        } catch (Exception e) {
        throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) {
        String url = "http://127.0.0.1:8080/visual/collect/create";
        CollectRep collectRep = CollectRep.build("n1", "c1003").setCollectionComment("xxxxxx");

        Response<Boolean> res = HttpClient.post(url, collectRep);
        System.out.println(res.getCode());
        System.out.println(res.getData());
        System.out.println(res.getMessage());
    }

}
