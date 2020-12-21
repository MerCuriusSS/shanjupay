package com.shanjupay.common.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/23 15:11
 */
@Data
@ApiModel(value="RestErrorResponse",description = "错误响应参数包装")
public class RestErrorResponse {

    private String errCode;
    private String errMessage;

    public RestErrorResponse(String errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }
}
