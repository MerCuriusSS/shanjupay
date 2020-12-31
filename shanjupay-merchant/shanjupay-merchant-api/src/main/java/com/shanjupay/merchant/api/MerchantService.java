package com.shanjupay.merchant.api;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.merchant.dto.MerchantDTO;
import com.shanjupay.merchant.dto.StaffDTO;
import com.shanjupay.merchant.dto.StoreDTO;

public interface MerchantService {
    //根据id查询详细信息
    public MerchantDTO queryMerchantById(Long id);


    /**
     *注册商户服务接口，接收账号，密码，手机号，为可拓展性试用DTO接收参数
     * @param merchantDTO 商户注册信息
     * @return 注册成功的商户信息
     */
    MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException;

    /**
     * 资质申请保存接口
     * @param merchantId 商户id
     * @param merchantDTO 资质申请的信息
     * @throws BusinessException
     */
    void applyMerchant(Long merchantId,MerchantDTO merchantDTO) throws BusinessException;



    /**
     * 新增门店
     * @param storeDTO 门店信息
     * @return 新增成功的门店信息
     * @throws BusinessException
     */
    StoreDTO createStore(StoreDTO storeDTO) throws BusinessException;

    /**
     * 新增员工
     * @param staffDTO 员工信息
     * @return 新增成功的员工信息
     * @throws BusinessException
     */
    StaffDTO createStaff(StaffDTO staffDTO) throws BusinessException;


    /**
     * 员工和门店绑定(设置为管理员)
     * @param storeId 门店id
     * @param staffId 员工id
     * @throws BusinessException
     */
    void bindStaffToStore(Long storeId,Long staffId) throws BusinessException;

    /**
     * 根据租户id查询商户信息
     * @param tenantId
     * @return
     */
    MerchantDTO queryMerchantByTenantId(Long tenantId);
}
