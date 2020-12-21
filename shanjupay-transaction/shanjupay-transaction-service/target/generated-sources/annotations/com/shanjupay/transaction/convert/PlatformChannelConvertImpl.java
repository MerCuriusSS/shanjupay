package com.shanjupay.transaction.convert;

import com.shanjupay.transaction.api.dto.PlatformChannelDTO;
import com.shanjupay.transaction.entity.PlatformChannel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-09T14:40:21+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class PlatformChannelConvertImpl implements PlatformChannelConvert {

    @Override
    public PlatformChannel dto2Entity(PlatformChannelDTO platformChannelDTO) {
        if ( platformChannelDTO == null ) {
            return null;
        }

        PlatformChannel platformChannel = new PlatformChannel();

        platformChannel.setId( platformChannelDTO.getId() );
        platformChannel.setChannelName( platformChannelDTO.getChannelName() );
        platformChannel.setChannelCode( platformChannelDTO.getChannelCode() );

        return platformChannel;
    }

    @Override
    public PlatformChannelDTO entity2Dto(PlatformChannel platformChannel) {
        if ( platformChannel == null ) {
            return null;
        }

        PlatformChannelDTO platformChannelDTO = new PlatformChannelDTO();

        platformChannelDTO.setId( platformChannel.getId() );
        platformChannelDTO.setChannelName( platformChannel.getChannelName() );
        platformChannelDTO.setChannelCode( platformChannel.getChannelCode() );

        return platformChannelDTO;
    }

    @Override
    public List<PlatformChannelDTO> entitys2Dtos(List<PlatformChannel> platformChannels) {
        if ( platformChannels == null ) {
            return null;
        }

        List<PlatformChannelDTO> list = new ArrayList<PlatformChannelDTO>( platformChannels.size() );
        for ( PlatformChannel platformChannel : platformChannels ) {
            list.add( entity2Dto( platformChannel ) );
        }

        return list;
    }
}
