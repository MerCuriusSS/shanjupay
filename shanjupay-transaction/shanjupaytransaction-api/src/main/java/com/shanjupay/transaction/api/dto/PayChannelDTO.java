package com.shanjupay.transaction.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/27 16:59
 */
@ApiModel("支付渠道")
@Data
public class PayChannelDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @ApiModelProperty("支付渠道名称")
    private String channelName;
    @ApiModelProperty("支付渠道编码")
    private String channelCode;
}
