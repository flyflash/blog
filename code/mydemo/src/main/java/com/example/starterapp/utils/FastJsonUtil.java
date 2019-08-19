package com.example.starterapp.utils;

import com.alibaba.fastjson.JSON;

public class FastJsonUtil {

    public static String bean2Json(Object object){
        return JSON.toJSONString(object);
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass){
        return JSON.parseObject(jsonStr, objClass);
    }
	
}
