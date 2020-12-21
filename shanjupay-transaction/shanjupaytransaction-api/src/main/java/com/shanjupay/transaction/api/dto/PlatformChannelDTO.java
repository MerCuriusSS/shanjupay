package com.shanjupay.transaction.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/24 17:09
 */
@Data
@ApiModel(value = "PlatformChannelDTO",description="服务类型信息")
public class PlatformChannelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("服务类型名称")
    private String channelName;

    @ApiModelProperty("服务类型代码")
    private String channelCode;
}
