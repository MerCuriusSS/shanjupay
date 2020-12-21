package com.shanjupay.transaction.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shanjupay.common.cache.Cache;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.RedisUtil;
import com.shanjupay.transaction.api.PayChannelService;
import com.shanjupay.transaction.api.dto.PayChannelDTO;
import com.shanjupay.transaction.api.dto.PayChannelParamDTO;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import com.shanjupay.transaction.convert.PayChannelParamConvert;
import com.shanjupay.transaction.convert.PlatformChannelConvert;
import com.shanjupay.transaction.entity.AppPlatformChannel;
import com.shanjupay.transaction.entity.PayChannelParam;
import com.shanjupay.transaction.entity.PlatformChannel;
import com.shanjupay.transaction.mapper.AppPlatformChannelMapper;
import com.shanjupay.transaction.mapper.PayChannelParamMapper;
import com.shanjupay.transaction.mapper.PlatformChannelMapper;
import com.shanjupay.transaction.mapper.PlatformPayChannelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/24 17:16
 */
@org.apache.dubbo.config.annotation.Service
public class PayChannelServiceImpl implements PayChannelService {
   @Autowired
   private PlatformChannelMapper platformChannelMapper;
   @Autowired
   private AppPlatformChannelMapper appPlatformChannelMapper;
   @Autowired
   private PlatformPayChannelMapper platformPayChannelMapper;
   @Autowired
   private PayChannelParamMapper payChannelParamMapper;
   @Autowired
   private Cache cache;

    /**
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public List<PlatformChannelDTO> queryPlatformChannel() throws BusinessException {
        //查询所有记录
        List<PlatformChannel> platformChannels=platformChannelMapper.selectList(null);
        //类型转换
        return PlatformChannelConvert.INSTANCE.entitys2Dtos(platformChannels);
    }

    /**
     * 为某个应用绑定一个服务类型
     * @param appID 应用id
     * @param platformChannelCodes 服务类型code
     * @throws BusinessException
     */
    @Override
    @Transactional
    public void bindPlatformChannelForApp(String appID, String platformChannelCodes) throws BusinessException {
        //根据应用id和服务类型code查询，如果已经绑定，则不再绑定，否则插入记录
        AppPlatformChannel appPlatformChannel=appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId,appID)
        .eq(AppPlatformChannel::getPlatformChannel,platformChannelCodes));

        if(appPlatformChannel==null){
            //向app_platForm_channel插入
            AppPlatformChannel entity=new AppPlatformChannel();
            entity.setAppId(appID);//应用id
            entity.setPlatformChannel(platformChannelCodes);//服务类型code
            appPlatformChannelMapper.insert(entity);
        }

    }

    /**
     * 应用绑定服务类型状态
     * @param appId
     * @param platformChannelCodes
     * @return
     * @throws BusinessException
     */
    @Override
    public int queryAppBindPlatformChannel(String appId, String platformChannelCodes) throws BusinessException {
        AppPlatformChannel appPlatformChannel=appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId,appId)
                .eq(AppPlatformChannel::getPlatformChannel,platformChannelCodes));

        if(appPlatformChannel!=null){
            return 1;
        }
        return 0;
    }

    /**
     * 根据服务类型查询支付渠道
     * @param platformChannelCode 服务类型代码
     * @return
     * @throws BusinessException
     */
    @Override
    public List<PayChannelDTO> queryPayChannelByPlatformChannel(String platformChannelCode) throws BusinessException {
        //调用mapper查询数据库platform_pay_channel,pay_channel,platform_channel
        return platformPayChannelMapper.selectPayChannelByPlatformChannel(platformChannelCode);
    }

    /**
     * 支付渠道参数配置
     * @param payChannelParam 配置支付渠道参数：包括：商户id，应用id，服务类型code，支付渠道code，配置名称，配置参数（json）
     * @throws BusinessException
     */
    @Override
    public void savePayChannelParam(PayChannelParamDTO payChannelParam) throws BusinessException {
        //根据应用、服务类型、支付渠道查询一条记录
        //根据应用与服务类型的绑定id
        Long appPlatformChannelId=selectIdByAppPlatformChannel(payChannelParam.getAppId(),payChannelParam.getPlatformChannelCode());
        if(appPlatformChannelId==null){
            throw new BusinessException(CommonErrorCode.E_300010);
        }

        //根据应用与服务类型的绑定id和支付渠道查询paychannelParam的一条记录
        PayChannelParam param=payChannelParamMapper.selectOne(new LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId,appPlatformChannelId)
        .eq(PayChannelParam::getPayChannel,payChannelParam.getPayChannel()));
        //如果存在配置则更新
        if(param!=null){
            param.setChannelName(payChannelParam.getChannelName());//配置名称
            param.setParam(payChannelParam.getParam());//json格式参数
            payChannelParamMapper.updateById(param);
        }
        else{//否则添加配置
            payChannelParam.setAppPlatformChannelId(appPlatformChannelId);
            PayChannelParam payChannelParamEntity=PayChannelParamConvert.INSTANCE.dto2Entity(payChannelParam);
            payChannelParamEntity.setId(null);
            payChannelParamMapper.insert(payChannelParamEntity);
        }


        //保存到redis
        updateCache(payChannelParam.getAppId(),payChannelParam.getPlatformChannelCode());

    }

    /**
     * 根据应用，服务类型和支付渠道的代码查询该支付渠道的参数配置信息
     * @param appID 应用ID
     * @param platformChannel 服务类型code
     * @return
     * @throws BusinessException
     */
    @Override
    public List<PayChannelParamDTO> queryPayChannelParamByAppAndPlatform(String appID, String platformChannel) throws BusinessException {

        //先从redis查询，如果查询到则返回，否则从数据库查询，从数据库查询完毕后将数据保存到redis中
       String redisKey=RedisUtil.keyBuilder(appID,platformChannel);
       Boolean exists=cache.exists(redisKey);
       if(exists){
           //从redis获取支付渠道参数列表（json传）
           String PayChannelParamDTO_String=cache.get(redisKey);
           //json转List
           List<PayChannelParamDTO> payChannelParamDTOS = JSON.parseArray(PayChannelParamDTO_String, PayChannelParamDTO.class);
           return payChannelParamDTOS;
       }

        //根据应用和服务类型找到他们的绑定id
        Long appPlatformChannelId=selectIdByAppPlatformChannel(appID,platformChannel);
        if(appPlatformChannelId==null){
            return null;
        }

        //应用和服务类型绑定id查询支付渠道参数记录
        List<PayChannelParam> list=payChannelParamMapper.selectList(new LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId,appPlatformChannelId));

        List<PayChannelParamDTO> payChannelParamDTOS = PayChannelParamConvert.INSTANCE.entitys2Dtos(list);
        //保存到redis
        updateCache(appID,platformChannel);

        return payChannelParamDTOS;
    }

    /**
     * 根据应用和服务类型查询支持渠道参数列表
     * @param appId 应用id
     * @param platformChannel 服务类型code
     * @param payChannel 支付渠道代码
     * @return
     * @throws BusinessException
     */
    @Override
    public PayChannelParamDTO queryParamByAppPlatformAndPayChannel(String appId, String platformChannel, String payChannel) throws BusinessException {
        //根据应用和服务类型查询支付渠道参数列表
        List<PayChannelParamDTO> payChannelParamDTOS=queryPayChannelParamByAppAndPlatform(appId,platformChannel);
        for(PayChannelParamDTO payChannelParamDTO:payChannelParamDTOS){
            if(payChannelParamDTO.getPayChannel().equals(payChannel)){
                return payChannelParamDTO;
            }
        }
        return null;
    }

    /**
     * 根据应用、服务类型查询应用与服务类型的绑定id
     * @param appId
     * @param platformChannelCode
     * @return
     */
    private Long selectIdByAppPlatformChannel(String appId,String platformChannelCode){
        AppPlatformChannel appplatformchannel=appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId,appId)
        .eq(AppPlatformChannel::getPlatformChannel,platformChannelCode));

        if(appplatformchannel!=null){
            return appplatformchannel.getId();
        }

        return null;
    }

    /**
     * 根据应用和服务类型，将查询到的支付渠道参数配置列表写入redis
     * @param appId 应用id
     * @param platformChannelCode 服务类型code
     */
    private void updateCache(String appId,String platformChannelCode){
        //得到redis中key（支付渠道参数配置列表的key）
        //格式：SJ_PAY_PARAM:应用id：服务类型code，例如：SJ_PAY_PARAM：xjlasjd7a78da8da：shanju_c2b：
        String redisKey=RedisUtil.keyBuilder(appId,platformChannelCode);
        //根据key查询redis
        Boolean exists = cache.exists(redisKey);
        if(exists){
            cache.del(redisKey);
        }

        //根据应用id和服务类型code查询支付渠道参数列表，将支付渠道参数写入redis
        //根据应用和服务类型找到他们的绑定id
        Long appPlatformChannelId=selectIdByAppPlatformChannel(appId,platformChannelCode);
        if(appPlatformChannelId!=null){
            //应用和服务类型绑定id查询支付渠道参数记录
            List<PayChannelParam> list=payChannelParamMapper.selectList(new LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId,appPlatformChannelId));

            List<PayChannelParamDTO> payChannelParamDTOS = PayChannelParamConvert.INSTANCE.entitys2Dtos(list);
            cache.set(redisKey, JSON.toJSON(payChannelParamDTOS).toString());
        }

    }
}
