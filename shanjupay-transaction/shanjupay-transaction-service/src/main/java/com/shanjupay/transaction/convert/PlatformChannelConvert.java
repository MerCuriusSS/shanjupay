package com.shanjupay.transaction.convert;

import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import com.shanjupay.transaction.entity.PlatformChannel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/24 17:26
 */
@Mapper
public interface PlatformChannelConvert {
    PlatformChannelConvert INSTANCE= Mappers.getMapper(PlatformChannelConvert.class);

    PlatformChannel dto2Entity(PlatformChannelDTO platformChannelDTO);

    PlatformChannelDTO entity2Dto(PlatformChannel platformChannel);

    List<PlatformChannelDTO> entitys2Dtos(List<PlatformChannel> platformChannels);
}
