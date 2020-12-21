package com.shanjupay.merchant.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "StoreStaffDTO", description = "门店员工信息")
public class StoreStaffDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long id;

    /**
     * 门店标识
     */
    @ApiModelProperty("门店标识")
    private Long storeId;

    /**
     * 员工标识
     */
    @ApiModelProperty("员工标识")
    private Long staffId;


}
