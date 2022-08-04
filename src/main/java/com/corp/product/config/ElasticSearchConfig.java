package com.corp.product.config;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义连接ES，区别于spring-data-elasticsearch方式，
 * 1.spring-data-elasticsearch方式：框架封装好了对ES操作的CRUD方法，很方便，但是部分功能可能实现起来比较麻烦。
 * 2.自定义这种：是自己实现CRUD功能，更麻烦，但是更灵活。
 */
@Configuration
public class ElasticSearchConfig {

    /**
     * ES地址,多个用逗号隔开
     */
    @Value("${elasticsearch.host}")
    private List<String> host;

    /**
     * ES端口
     */
    @Value("${elasticsearch.port}")
    private List<String> port;

    /**
     * 使用协议 http/https
     */
    @Value("${elasticsearch.schema}")
    private String schema;

    /**
     * ES用户名
     */
    @Value("${elasticsearch.username}")
    private String username;

    /**
     * ES密码
     */
    @Value("${elasticsearch.password}")
    private String password;

    /**
     * 连接超时时间
     */
    @Value("${elasticsearch.connectTimeOut}")
    private int connectTimeOut;

    /**
     * 连接超时时间
     */
    @Value("${elasticsearch.socketTimeOut}")
    private int socketTimeOut;

    /**
     * 获取连接的超时时间
     */
    @Value("${elasticsearch.connectionRequestTimeOut}")
    private int connectionRequestTimeOut;

    /**
     * 最大连接数
     */
    @Value("${elasticsearch.maxConnectNum}")
    private int maxConnectNum;

    /**
     * 最大路由连接数
     */
    @Value("${elasticsearch.maxConnectPerRoute}")
    private int maxConnectPerRoute;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        ArrayList<HttpHost> hostList = new ArrayList<>(host.size());
        for (int i = 0; i < host.size(); i++) {
            hostList.add(new HttpHost(host.get(i), Integer.valueOf(port.get(i)), schema));
        }
        RestClientBuilder client = RestClient.builder(hostList.toArray(new HttpHost[0]));
        // 异步httpclient连接延时配置
        client.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeOut);
            requestConfigBuilder.setSocketTimeout(socketTimeOut);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
            return requestConfigBuilder;
        });
        // 异步httpclient连接数配置
        client.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(maxConnectNum);
            httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
            //是否添加鉴权
            if (null != username && !"".equals(username) ) {
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            return httpClientBuilder;
        });
        return new RestHighLevelClient(client);
    }

}