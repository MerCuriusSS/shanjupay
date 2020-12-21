package com.shanjupay.merchant.service;

import com.shanjupay.common.domain.BusinessException;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/23 10:35
 */
public interface SmsService {
    /**
     * 发送手机验证码
     * @param phone
     * @return 验证码对应的key
     *
     */
    String sendMsg(String phone);

    /**
     *
     * @param verifyCode 验证码
     * @param verifyKey  验证码key
     */
    void checkVerifyCode(String verifyCode,String verifyKey) throws BusinessException;
}
