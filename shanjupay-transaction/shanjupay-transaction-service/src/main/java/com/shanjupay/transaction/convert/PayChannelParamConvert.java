package com.shanjupay.transaction.convert;

import com.shanjupay.transaction.api.dto.PayChannelParamDTO;
import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import com.shanjupay.transaction.entity.PayChannelParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/12/5 13:21
 */
@Mapper
public interface PayChannelParamConvert {

    PayChannelParamConvert INSTANCE= Mappers.getMapper(PayChannelParamConvert.class);

    PayChannelParam dto2Entity(PayChannelParamDTO payChannelParamDto);

    PayChannelParamDTO entity2Dto(PayChannelParam payChannelParam);

    List<PayChannelParamDTO> entitys2Dtos(List<PayChannelParam> payChannelParams);
}
