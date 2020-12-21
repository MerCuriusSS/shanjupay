package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.dto.MerchantDTO;
import com.shanjupay.merchant.vo.MerchantDetailVO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-11T16:04:13+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class MerchantDetailConvertImpl implements MerchantDetailConvert {

    @Override
    public MerchantDTO vo2Tto(MerchantDetailVO merchantDetailVO) {
        if ( merchantDetailVO == null ) {
            return null;
        }

        MerchantDTO merchantDTO = new MerchantDTO();

        merchantDTO.setMerchantName( merchantDetailVO.getMerchantName() );
        merchantDTO.setMerchantNo( merchantDetailVO.getMerchantNo() );
        merchantDTO.setMerchantAddress( merchantDetailVO.getMerchantAddress() );
        merchantDTO.setMerchantType( merchantDetailVO.getMerchantType() );
        merchantDTO.setBusinessLicensesImg( merchantDetailVO.getBusinessLicensesImg() );
        merchantDTO.setIdCardFrontImg( merchantDetailVO.getIdCardFrontImg() );
        merchantDTO.setIdCardAfterImg( merchantDetailVO.getIdCardAfterImg() );
        merchantDTO.setUsername( merchantDetailVO.getUsername() );
        merchantDTO.setContactsAddress( merchantDetailVO.getContactsAddress() );

        return merchantDTO;
    }
}
