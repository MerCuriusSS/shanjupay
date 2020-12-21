package com.shanjupay.merchant.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "AppDTO", description = "商店信息")
public class AppDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商店id")
    private String appId;

    /**
     * 商店名称
     */
    @ApiModelProperty("商店名称")
    private String appName;

    /**
     * 所属商户
     */
    @ApiModelProperty("所属商户")
    private Long merchantId;

    /**
     * 应用公钥(RSAWithSHA256)
     */
    @ApiModelProperty("应用公钥")
    private String publicKey;

    /**
     * 授权回调地址
     */
    @ApiModelProperty("授权回调地址")
    private String notifyUrl;


}
