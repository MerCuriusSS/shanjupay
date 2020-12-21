package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.dto.AppDTO;
import com.shanjupay.merchant.entity.App;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/24 14:10
 */
@Mapper
public interface AppConvert {
    AppConvert INSTANCE= Mappers.getMapper(AppConvert.class);

    AppDTO entity2Dto(App app);

    App dto2Entity(AppDTO appDTO);

    List<AppDTO> entities2Dtos(List<App> apps);

    List<App> dtos2Entities(List<AppDTO> dtos);
}
