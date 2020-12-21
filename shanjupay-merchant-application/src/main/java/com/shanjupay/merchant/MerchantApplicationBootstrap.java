package com.shanjupay.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootApplication
public class MerchantApplicationBootstrap {
    public static void main(String[] args){
        SpringApplication.run(MerchantApplicationBootstrap.class,args);
    }


    @Bean
    RestTemplate restTemplate(){
        RestTemplate restTemplate=new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        //【不是必要的步骤】
        //得到消息装换器
        List<HttpMessageConverter<?>> messageConverters=restTemplate.getMessageConverters();
        //指定StringHttpMessageConverter消息装换器的字符集为utf8（默认是iso-8859-1）
        messageConverters.set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;

    }
}
