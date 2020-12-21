package com.shanjupay.merchant.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.StringUtil;
import com.shanjupay.merchant.api.AppService;
import com.shanjupay.merchant.dto.AppDTO;

import com.shanjupay.merchant.convert.AppConvert;
import com.shanjupay.merchant.entity.App;
import com.shanjupay.merchant.entity.Merchant;
import com.shanjupay.merchant.mapper.AppMapper;
import com.shanjupay.merchant.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/24 14:08
 */
@org.apache.dubbo.config.annotation.Service
public class AppServiceImpl implements AppService {
    @Autowired
    AppMapper appMapper;

    @Autowired
    MerchantMapper merchantMapper;

    /**
     * 创建应用
     * @param merchantId 商户id
     * @param appDTO 应用信息
     * @return
     * @throws BusinessException
     */
    @Override
    public AppDTO createApp(Long merchantId, AppDTO appDTO) throws BusinessException {

        if(merchantId==null||appDTO==null|| StringUtil.isBlank(appDTO.getAppName())){
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //校验商户是否通过资质审核
        Merchant merchant=merchantMapper.selectById(merchantId);
        if(merchant==null){
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        //取出商户资质申请状态
        String auditStatus=merchant.getAuditStatus();
        if(!"2".equals(auditStatus)){
            throw new BusinessException(CommonErrorCode.E_200003);
        }
        //校验应用名称
        String appName=appDTO.getAppName();
        Boolean existAppName=isExistAppName(appName);
        if(existAppName){
            throw new BusinessException(CommonErrorCode.E_200004);
        }

        //生成应用id
        String appId= UUID.randomUUID().toString();
        //调用appMapper向App表插入数据
        App entity=AppConvert.INSTANCE.dto2Entity(appDTO);
        entity.setAppId(appId);
        entity.setMerchantId(merchantId);
        appMapper.insert(entity);
        AppDTO resultDto=AppConvert.INSTANCE.entity2Dto(entity);

        return resultDto;
    }

    /**
     *
     * @param merchantId
     * @return
     * @throws BusinessException
     */
    @Override
    public List<AppDTO> queryAppByMerchant(Long merchantId) throws BusinessException {
        List<App> apps=appMapper.selectList(new LambdaQueryWrapper<App>().eq(App::getMerchantId,merchantId));
        List<AppDTO> appDTOS=AppConvert.INSTANCE.entities2Dtos(apps);
        return appDTOS;
    }

    /**
     *
     * @param appId 应用id
     * @return
     * @throws BusinessException
     */
    @Override
    public AppDTO getAppById(String appId) throws BusinessException {
        App app=appMapper.selectOne(new LambdaQueryWrapper<App>().eq(App::getAppId,appId));
        return AppConvert.INSTANCE.entity2Dto(app);
    }


    private boolean isExistAppName(String appName){
        Integer count=appMapper.selectCount(new LambdaQueryWrapper<App>().eq(App::getAppName,appName));
        return count>0;
    }
}
