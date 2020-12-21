package com.shanjupay.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/24 17:49
 */
@Data
@TableName("platform_pay_channel")
public class PlatformPayChannel implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value="ID",type = IdType.ID_WORKER)
    private Long id;

    @TableField("PLATFORM_CHANNEL")
    private String platformChannel;

    @TableField("PAY_CHANNEL")
    private String payChannel;
}
