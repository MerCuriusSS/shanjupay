package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.dto.MerchantDTO;
import com.shanjupay.merchant.vo.MerchantRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 将商户注册vo和dto的相互转换
 */
@Mapper
public interface MerchantRegisterConvert {
    MerchantRegisterConvert INSTANCE= Mappers.getMapper(MerchantRegisterConvert.class);

    //将dto转换vo
    MerchantRegisterVO dto2Vo(MerchantDTO merchantDTO);
    //将vo转换为dto
    MerchantDTO vo2Dto(MerchantRegisterVO merchantRegisterVO);

}
