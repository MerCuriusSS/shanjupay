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
 * @date 2020/11/24 17:21
 */
@Data
@TableName("platform_channel")
public class PlatformChannel implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "ID", type = IdType.ID_WORKER)
    private Long id;
    @TableField("CHANNEL_NAME")
    private String channelName;
    @TableField("CHANNEL_CODE")
    private String channelCode;
}
