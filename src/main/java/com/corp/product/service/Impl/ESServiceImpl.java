package com.corp.product.service.Impl;

import com.alibaba.fastjson.JSONObject;

import com.corp.product.service.ESService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ESServiceImpl implements ESService {
    private static final Logger logger = LoggerFactory.getLogger(ESServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    @Override
    public JSONObject queryAll() {
        SearchRequest searchRequest = new SearchRequest("db");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        try {
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse =
                    client.search(searchRequest, RequestOptions.DEFAULT);
            return JSONObject.parseObject(searchResponse.toString());

        }catch (IOException e){
            return null;
        }
    }

    @Override
    public JSONObject queryByKey(String key){
        SearchRequest searchRequest = new SearchRequest("db");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder matchQueryBuilder =
                QueryBuilders.matchQuery("content", key);
        SearchSourceBuilder query = searchSourceBuilder.query(matchQueryBuilder);

        searchRequest.source(query);

        try {
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return JSONObject.parseObject(searchResponse.toString());
        }catch (IOException e){
            return null;
        }
    }
}
