package com.visual.face.search.server.bootstrap.conf;

import com.visual.face.search.engine.api.SearchEngine;
import com.visual.face.search.engine.impl.OpenSearchEngine;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Configuration("visualEngineConfig")
public class EngineConfig {
    //日志
    public Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${visual.engine.open-search.host:localhost}")
    private String openSearchHost;
    @Value("${visual.engine.open-search.port:9200}")
    private Integer openSearchPort;
    @Value("${visual.engine.open-search.scheme:https}")
    private String openSearchScheme;
    @Value("${visual.engine.open-search.username:admin}")
    private String openSearchUserName;
    @Value("${visual.engine.open-search.password:admin}")
    private String openSearchPassword;

    @Bean(name = "visualSearchEngine")
    public SearchEngine simpleSearchEngine(){
        //认证参数
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(openSearchUserName, openSearchPassword));
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
            logger.error("create SearchEngine error:", e);
            throw new RuntimeException(e);
        }
        //构建请求
        RestClientBuilder builder = RestClient.builder(new HttpHost(openSearchHost, openSearchPort, openSearchScheme))
            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setDefaultCredentialsProvider(credentialsProvider)
                .setSSLHostnameVerifier((hostname, session) -> true)
                .setSSLContext(sslContext)
                .setMaxConnTotal(10)
                .setMaxConnPerRoute(10)
            );
        //构建client
        return  new OpenSearchEngine(new RestHighLevelClient(builder));
    }

}
