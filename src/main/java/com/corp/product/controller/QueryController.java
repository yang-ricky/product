package com.corp.product.controller;

import com.corp.product.service.ESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class QueryController {
    @Autowired
    private ESService esService;

    @RequestMapping("/all")
    public Object queryAll(){
        return esService.queryAll();
    }

    @RequestMapping("/query/{key}")
    public Object queryByKey(@PathVariable String key){
        return esService.queryByKey(key);
    }
}
