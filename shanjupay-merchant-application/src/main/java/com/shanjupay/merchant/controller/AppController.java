package com.shanjupay.merchant.controller;

import com.shanjupay.merchant.api.AppService;
import com.shanjupay.merchant.dto.AppDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/24 14:25
 */
@RestController
public class AppController extends BasicController{

    @Reference
    AppService appService;

    /**
     * 商户创建应用
     * @param appDTO
     * @return
     */
    @ApiOperation("商户创建应用")
    @PostMapping("/my/createApp")
    public AppDTO createApp(@RequestBody AppDTO appDTO){
        //得到商户id
        Long merchantId=getLoginMerchantId();
        return appService.createApp(merchantId,appDTO);
    }

    /**
     * 查询商户下的应用列表
     * @return
     */
    @ApiOperation("查询商户下的应用列表")
    @GetMapping("/my/apps")
    public List<AppDTO> queryMyApps(){
        //商户id
        Long merchantId=getLoginMerchantId();
        return appService.queryAppByMerchant(merchantId);
    }

    /**
     * 根据应用id查询应用信息
     * @param appID
     * @return
     */
    @ApiOperation("根据应用id查询应用信息")
    @GetMapping("/my/apps/{appId}")
    public AppDTO getApp(@PathVariable("appId") String appID){
        return appService.getAppById(appID);
    }

}
