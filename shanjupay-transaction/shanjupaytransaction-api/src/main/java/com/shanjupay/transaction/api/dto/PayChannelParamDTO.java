package com.shanjupay.transaction.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/28 15:11
 */
@ApiModel(value="PayChannelParamDTO",description = "支付渠道参数DTO")
@Data
public class PayChannelParamDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @ApiModelProperty("支付渠道")
    private String channelName;
    @ApiModelProperty("商户id")
    private Long merchantId;
    @ApiModelProperty("支付渠道code")
    private String payChannel;
    @ApiModelProperty("配置参数")
    private String param;
    @ApiModelProperty("商户应用_支付渠道关联id")
    private Long appPlatformChannelId;
    @ApiModelProperty("商户应用id")
    private String appId;
    @ApiModelProperty("应用绑定的服务类型对应的code")
    private String platformChannelCode;

}
