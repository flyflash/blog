package com.example.starterapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartResolverConfig {

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setResolveLazily(true);
        commonsMultipartResolver.setMaxInMemorySize(40960);
        commonsMultipartResolver.setMaxUploadSize(10 * 1024 * 1024); // 上传文件大小 5M 5 * 1024 * 1024
        commonsMultipartResolver.setMaxUploadSizePerFile(1 * 1024 * 1024);
        return commonsMultipartResolver;
    }
}
