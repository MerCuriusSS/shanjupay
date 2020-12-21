package com.shanjupay.merchant.convert;


import com.shanjupay.merchant.dto.MerchantDTO;
import com.shanjupay.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/23 14:16
 */
@Mapper //对象属性的映射
public interface MerchantConvert {
    //转换类实例
    MerchantConvert INSTANCE= Mappers.getMapper(MerchantConvert.class);

    //把DTO转换成entity
    Merchant dto2entity(MerchantDTO merchantDTO);

    //entity转换为DTO
    MerchantDTO entity2dto(Merchant merchant);

    public static void main(String[] args){
        MerchantDTO merchantDTO=new MerchantDTO();
        merchantDTO.setUsername("aaaa");
        merchantDTO.setMobile("1234213421");
        Merchant merchant=MerchantConvert.INSTANCE.dto2entity(merchantDTO);
        System.out.println(merchant.getUsername());
        System.out.println(merchant.getMobile());



    }
}
