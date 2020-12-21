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
 * @date 2020/11/24 17:59
 */
@Data
@TableName("app_platform_channel")
public class AppPlatformChannel implements Serializable {
    private static final Long serialVersionUID=1L;
    @TableId(value = "ID", type = IdType.ID_WORKER)
    private Long id;

    @TableField("APP_ID")
    private String appId;

    @TableField("PLATFORM_CHANNEL")
    private String platformChannel;
}
