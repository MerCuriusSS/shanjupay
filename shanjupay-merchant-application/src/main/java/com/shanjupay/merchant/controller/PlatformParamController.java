package com.shanjupay.merchant.controller;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.transaction.api.PayChannelService;
import com.shanjupay.transaction.api.dto.PayChannelDTO;
import com.shanjupay.transaction.api.dto.PayChannelParamDTO;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平台支付参数配置相关的controller
 * @author mercurius
 * @version 1.0
 * @date 2020/11/24 17:32
 */
@RestController
@Slf4j
public class PlatformParamController extends BasicController{
    @Reference
    private PayChannelService payChannelService;

    /**
     * 获取平台服务类型
     * @return
     */
    @ApiOperation("获取平台服务类型")
    @GetMapping("/my/platform-channels")
    public List<PlatformChannelDTO> queryPlatformChannels(){
        return payChannelService.queryPlatformChannel();
    }


    /**
     * 绑定服务类型
     * @param appId
     * @param platformChannelCodes
     */
    @ApiOperation("绑定服务类型")
    @PostMapping(value="/my/apps/{appId}/platform-channels")
    public void bindPlatformForApp(@PathVariable("appId") String appId, @RequestParam("platformChannelCodes") String platformChannelCodes){

        payChannelService.bindPlatformChannelForApp(appId,platformChannelCodes);
    }

    /**
     * 查询应用是否绑定了某个服务类型
     * @param appId
     * @param platformChannelCodes
     * @return
     */
    @ApiOperation("查询应用是否绑定了某个服务类型")
    @PostMapping("/my/merchants/apps/platformchannels")
    public int queryAppBindPlatformChannel(@RequestParam("appId") String appId,@RequestParam("platformChannelCodes") String platformChannelCodes){

        return payChannelService.queryAppBindPlatformChannel(appId,platformChannelCodes);
    }

    /**
     * 根据服务类型查询支付渠道
     * @param platformChannelCode
     * @return
     * @throws BusinessException
     */
    @ApiOperation("根据服务类型查询支付渠道")
    @PostMapping("/my/pay-channels/platform-channel/{platformchannelCode}")
    List<PayChannelDTO> queryPayChannelByPlatformChannel(@PathVariable("platformchannelCode") String platformChannelCode) throws BusinessException {

        return payChannelService.queryPayChannelByPlatformChannel(platformChannelCode);
    }

    /**
     * 商户配置支付渠道参数
     * @param payChannelParamDTO
     */
    @ApiOperation("商户配置支付渠道参数")
    @RequestMapping(value = "/my/pay-channel-params",method={RequestMethod.POST,RequestMethod.PUT})
    void createPayChannelParam(@RequestBody PayChannelParamDTO payChannelParamDTO){

        if(payChannelParamDTO==null||payChannelParamDTO.getChannelName()==null){
            throw new BusinessException(CommonErrorCode.E_300009);
        }

        //取出当前登录商户的id（暂时定义为1）
        Long merchantId=getLoginMerchantId();
        payChannelParamDTO.setMerchantId(merchantId);
        payChannelService.savePayChannelParam(payChannelParamDTO);

    }

    /**
     * 根据应用和服务类型获取支付渠道参数列表
     * @param appId
     * @param platformChannel
     * @return
     */
    @ApiOperation("根据应用和服务类型获取支付渠道参数列表")
    @GetMapping(value="/my/pay-channel-params/apps/{appId}/platform-channels/{platformChannel}")
    public List<PayChannelParamDTO> queryPayChannelParams(@PathVariable("appId") String appId,@PathVariable("platformChannel") String platformChannel){
        return payChannelService.queryPayChannelParamByAppAndPlatform(appId,platformChannel);
    }

    /**
     * 获取指定应用指定服务类型下所包含的原始支付渠道参数列表
     * @param appId
     * @param platformChannel
     * @return
     */
    @ApiOperation("根据应用和服务类型和支付渠道获取单个支付渠道参数列表")
    @GetMapping(value="/my/pay-channel-params/apps/{appId}/platform-channels/{platformChannel}/pay-channels/{payChannel}")
    public PayChannelParamDTO queryPayChannelParam(@PathVariable("appId") String appId,@PathVariable("platformChannel") String platformChannel,@PathVariable("payChannel") String payChannel){
        return payChannelService.queryParamByAppPlatformAndPayChannel(appId,platformChannel,payChannel);
    }


}
