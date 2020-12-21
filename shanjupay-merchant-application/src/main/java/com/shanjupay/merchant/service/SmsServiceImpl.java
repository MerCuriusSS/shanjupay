package com.shanjupay.merchant.service;

import com.alibaba.fastjson.JSON;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/23 10:38
 */
@org.springframework.stereotype.Service
@Slf4j
public class SmsServiceImpl implements SmsService{

    @Value("${sms.url}")
    String url;

    @Value("${sms.effectiveTime}")
    String effectiveTime;//有效期

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送手机验证码
     * @param phone
     * @return 验证码对应的key
     *
     */
    @Override
    public String sendMsg(String phone) {
        String sms_url=url+"/generate?name=sms&effectiveTime="+effectiveTime;
        //请求信息,传入body，header
        //请求体
        Map<String,Object> body=new HashMap<>();
        body.put("mobile",phone);
        //请求头
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //请求信息，传入body，header
        HttpEntity httpEntity=new HttpEntity(body,httpHeaders);
        //向url请求
        ResponseEntity<Map> exchange= null;
        Map bodyMap=null;
        try {
            exchange = restTemplate.exchange(sms_url, HttpMethod.POST,httpEntity, Map.class);
            log.info("请求验证码服务，得到响应:{}", JSON.toJSONString(exchange));
            bodyMap=exchange.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new RuntimeException("发送验证码失败");
        }

        if(body==null||bodyMap.get("result")==null){
            throw new RuntimeException("发送验证码失败");
        }

        Map result= (Map) bodyMap.get("result");
        String key= (String) result.get("key");
        log.info("得到发送验证码对应的key：{}",key);
        return key;
    }

    /**
     *
     * @param verifyCode 验证码
     * @param verifyKey  验证码key
     */
    @Override
    public void checkVerifyCode(String verifyCode, String verifyKey) throws BusinessException {
        //检验验证码的url
        String url="http://localhost:56085/sailing/verify?name=sms&verificationCode="+verifyCode+"&verificationKey="+verifyKey;

        Map bodyMap=null;

        try {
            //使用restTmplate请求http服务
           ResponseEntity<Map> exchange= restTemplate.exchange(url,HttpMethod.POST,HttpEntity.EMPTY,Map.class);
            log.info("请求验证码服务，得到响应:{}", JSON.toJSONString(exchange));
           bodyMap=exchange.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
//            throw new RuntimeException("校验验证码失败");
            throw new BusinessException(CommonErrorCode.E_100102);
        }

        if(bodyMap==null||bodyMap.get("result")==null||!(Boolean)bodyMap.get("result")){
            throw new BusinessException(CommonErrorCode.E_100102);
        }
    }
}
