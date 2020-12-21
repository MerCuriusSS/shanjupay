package com.shanjupay.merchant.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "StaffDTO", description = "商户信息")
public class StaffDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 商户ID
     */
    @ApiModelProperty("商户id")
    private Long merchantId;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String fullName;

    /**
     * 职位
     */
    @ApiModelProperty("职位")
    private String position;

    /**
     * 用户名(关联统一用户)
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 手机号(关联统一用户)
     */
    @ApiModelProperty("手机号")
    private String mobile;

    /**
     * 员工所属门店
     */
    @ApiModelProperty("员工所属门店")
    private Long storeId;

    /**
     * 最后一次登录时间
     */
    @ApiModelProperty("最后一次登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 0表示禁用，1表示启用
     */
    @ApiModelProperty("0表示禁用，1表示启用")
    private Boolean staffStatus;


}
