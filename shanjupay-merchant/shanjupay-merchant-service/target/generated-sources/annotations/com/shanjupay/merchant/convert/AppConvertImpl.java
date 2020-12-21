package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.dto.AppDTO;
import com.shanjupay.merchant.entity.App;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-11T16:05:00+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class AppConvertImpl implements AppConvert {

    @Override
    public AppDTO entity2Dto(App app) {
        if ( app == null ) {
            return null;
        }

        AppDTO appDTO = new AppDTO();

        appDTO.setAppId( app.getAppId() );
        appDTO.setAppName( app.getAppName() );
        appDTO.setMerchantId( app.getMerchantId() );
        appDTO.setPublicKey( app.getPublicKey() );
        appDTO.setNotifyUrl( app.getNotifyUrl() );

        return appDTO;
    }

    @Override
    public App dto2Entity(AppDTO appDTO) {
        if ( appDTO == null ) {
            return null;
        }

        App app = new App();

        app.setAppId( appDTO.getAppId() );
        app.setAppName( appDTO.getAppName() );
        app.setMerchantId( appDTO.getMerchantId() );
        app.setPublicKey( appDTO.getPublicKey() );
        app.setNotifyUrl( appDTO.getNotifyUrl() );

        return app;
    }

    @Override
    public List<AppDTO> entities2Dtos(List<App> apps) {
        if ( apps == null ) {
            return null;
        }

        List<AppDTO> list = new ArrayList<AppDTO>( apps.size() );
        for ( App app : apps ) {
            list.add( entity2Dto( app ) );
        }

        return list;
    }

    @Override
    public List<App> dtos2Entities(List<AppDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<App> list = new ArrayList<App>( dtos.size() );
        for ( AppDTO appDTO : dtos ) {
            list.add( dto2Entity( appDTO ) );
        }

        return list;
    }
}
