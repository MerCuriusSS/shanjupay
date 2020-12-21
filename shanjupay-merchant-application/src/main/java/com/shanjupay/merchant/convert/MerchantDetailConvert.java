package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.dto.MerchantDTO;
import com.shanjupay.merchant.vo.MerchantDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/24 13:18
 */
@Mapper
public interface MerchantDetailConvert {
    MerchantDetailConvert INSTANCE= Mappers.getMapper(MerchantDetailConvert.class);
    //vo转dto
    MerchantDTO vo2Tto(MerchantDetailVO merchantDetailVO);
}
