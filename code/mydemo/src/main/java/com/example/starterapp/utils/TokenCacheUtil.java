package com.example.starterapp.utils;

import com.example.starterapp.POJO.User;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TokenCacheUtil {

    public static LoadingCache<String, User> localCache =
            CacheBuilder.newBuilder()
                    .initialCapacity(1000).maximumSize(10000) //缓存回收策略， 数量接近10000时自动清理缓存
                    .expireAfterAccess(2, TimeUnit.HOURS)
                    .build(new CacheLoader<String, User>() {
                        @Override
                        public User load(String key) throws Exception {
                            User user = new User();
                            user.setId(null);
                            return user;
                        }
                    });

    /**
     * 存放token
     * @param key
     * @param value
     */
    public static void setToken(String key, User value){
        localCache.put(key, value);
        log.info("【Guava缓存】token存放key：[{}]value:[{}]成功", key, value);
    }

    /**
     * 获取token
     * @param key
     * @return
     */
    public static User getToken(String key){
        try {
            User value = localCache.get(key);
            if (value == null){
                return null;
            }
            log.info("【Guava缓存】获取key:[{}]的token:[{}]成功", key, value);
        } catch (ExecutionException e) {
            log.info("【Guava缓存】获取key:[{}]的token:[{}]失败", key, e);
        }
        return null;
    }

    /**
     * 清除token
     * @param key
     */
    public static void delToken(String key){
        localCache.invalidate(key);
        log.info("【Guava缓存】清除token:[{}]成功", key);
    }
}
