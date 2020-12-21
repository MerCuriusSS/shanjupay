package com.shanjupay.merchant.convert;

import com.shanjupay.merchant.dto.StoreDTO;
import com.shanjupay.merchant.entity.Store;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-11T16:05:00+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class StoreConvertImpl implements StoreConvert {

    @Override
    public StoreDTO entity2dto(Store entity) {
        if ( entity == null ) {
            return null;
        }

        StoreDTO storeDTO = new StoreDTO();

        storeDTO.setId( entity.getId() );
        storeDTO.setStoreName( entity.getStoreName() );
        storeDTO.setStoreNumber( entity.getStoreNumber() );
        storeDTO.setMerchantId( entity.getMerchantId() );
        storeDTO.setParentId( entity.getParentId() );
        storeDTO.setStoreStatus( entity.getStoreStatus() );
        storeDTO.setStoreAddress( entity.getStoreAddress() );

        return storeDTO;
    }

    @Override
    public Store dto2entity(StoreDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Store store = new Store();

        store.setId( dto.getId() );
        store.setStoreName( dto.getStoreName() );
        store.setStoreNumber( dto.getStoreNumber() );
        store.setMerchantId( dto.getMerchantId() );
        store.setParentId( dto.getParentId() );
        store.setStoreStatus( dto.getStoreStatus() );
        store.setStoreAddress( dto.getStoreAddress() );

        return store;
    }

    @Override
    public List<StoreDTO> listentity2dto(List<Store> staff) {
        if ( staff == null ) {
            return null;
        }

        List<StoreDTO> list = new ArrayList<StoreDTO>( staff.size() );
        for ( Store store : staff ) {
            list.add( entity2dto( store ) );
        }

        return list;
    }
}
