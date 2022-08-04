package com.corp.product.service;

import com.alibaba.fastjson.JSONObject;

public interface ESService {

    JSONObject queryAll();

    JSONObject queryByKey(String key);
}
