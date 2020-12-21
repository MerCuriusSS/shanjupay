package com.shanjupay.transaction.convert;

import com.shanjupay.transaction.api.dto.PayChannelParamDTO;
import com.shanjupay.transaction.entity.PayChannelParam;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-09T14:40:21+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class PayChannelParamConvertImpl implements PayChannelParamConvert {

    @Override
    public PayChannelParam dto2Entity(PayChannelParamDTO payChannelParamDto) {
        if ( payChannelParamDto == null ) {
            return null;
        }

        PayChannelParam payChannelParam = new PayChannelParam();

        payChannelParam.setId( payChannelParamDto.getId() );
        payChannelParam.setChannelName( payChannelParamDto.getChannelName() );
        payChannelParam.setMerchantId( payChannelParamDto.getMerchantId() );
        payChannelParam.setPayChannel( payChannelParamDto.getPayChannel() );
        payChannelParam.setParam( payChannelParamDto.getParam() );
        payChannelParam.setAppPlatformChannelId( payChannelParamDto.getAppPlatformChannelId() );

        return payChannelParam;
    }

    @Override
    public PayChannelParamDTO entity2Dto(PayChannelParam payChannelParam) {
        if ( payChannelParam == null ) {
            return null;
        }

        PayChannelParamDTO payChannelParamDTO = new PayChannelParamDTO();

        payChannelParamDTO.setId( payChannelParam.getId() );
        payChannelParamDTO.setChannelName( payChannelParam.getChannelName() );
        payChannelParamDTO.setMerchantId( payChannelParam.getMerchantId() );
        payChannelParamDTO.setPayChannel( payChannelParam.getPayChannel() );
        payChannelParamDTO.setParam( payChannelParam.getParam() );
        payChannelParamDTO.setAppPlatformChannelId( payChannelParam.getAppPlatformChannelId() );

        return payChannelParamDTO;
    }

    @Override
    public List<PayChannelParamDTO> entitys2Dtos(List<PayChannelParam> payChannelParams) {
        if ( payChannelParams == null ) {
            return null;
        }

        List<PayChannelParamDTO> list = new ArrayList<PayChannelParamDTO>( payChannelParams.size() );
        for ( PayChannelParam payChannelParam : payChannelParams ) {
            list.add( entity2Dto( payChannelParam ) );
        }

        return list;
    }
}
