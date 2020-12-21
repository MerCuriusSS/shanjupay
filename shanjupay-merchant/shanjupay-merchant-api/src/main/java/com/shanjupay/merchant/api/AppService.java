package com.shanjupay.merchant.api;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.merchant.dto.AppDTO;

import java.util.List;

/**
 * 应用管理相关的接口
 * @author mercurius
 * @version 1.0
 * @date 2020/11/24 14:06
 */
public interface AppService {

    /**
     * 创建应用
     * @param merchantId 商户id
     * @param appDTO 应用信息
     * @return 创建成功的应用信息
     * @throws BusinessException
     */
    AppDTO createApp(Long merchantId,AppDTO appDTO) throws BusinessException;

    /**
     * 根据商户查询应用
     * @param merchantId 商户id
     * @return 应用
     * @throws BusinessException
     */
    List<AppDTO> queryAppByMerchant(Long merchantId) throws BusinessException;

    /**
     * 根据应用id插叙应用信息
     * @param appId 应用id
     * @return 应用信息
     * @throws BusinessException
     */
    AppDTO getAppById(String appId) throws BusinessException;
}

