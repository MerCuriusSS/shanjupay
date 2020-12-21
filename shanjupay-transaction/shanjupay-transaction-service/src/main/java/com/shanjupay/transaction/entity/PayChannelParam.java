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
 * @date 2020/11/28 15:36
 */
@Data
@TableName("pay_channel_param")
public class PayChannelParam implements Serializable {
    private static final Long serialVersionUID=1L;
    @TableId(value = "ID", type = IdType.ID_WORKER)
    private Long id;
    @TableField(value = "CHANNEL_NAME")
    private String channelName;
    @TableField(value = "MERCHANT_ID")
    private Long merchantId;
    @TableField(value = "PAY_CHANNEL")
    private String payChannel;
    @TableField(value = "PARAM")
    private String param;
    @TableField(value = "APP_PLATFORM_CHANNEL_ID")
    private Long appPlatformChannelId;

}
