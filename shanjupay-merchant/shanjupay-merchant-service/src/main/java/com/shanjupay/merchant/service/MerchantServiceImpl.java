package com.shanjupay.merchant.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.PhoneUtil;
import com.shanjupay.common.util.StringUtil;
import com.shanjupay.merchant.api.MerchantService;
import com.shanjupay.merchant.convert.StaffConvert;
import com.shanjupay.merchant.convert.StoreConvert;
import com.shanjupay.merchant.dto.MerchantDTO;
import com.shanjupay.merchant.convert.MerchantConvert;
import com.shanjupay.merchant.dto.StaffDTO;
import com.shanjupay.merchant.dto.StoreDTO;
import com.shanjupay.merchant.entity.Merchant;
import com.shanjupay.merchant.entity.Staff;
import com.shanjupay.merchant.entity.Store;
import com.shanjupay.merchant.entity.StoreStaff;
import com.shanjupay.merchant.mapper.MerchantMapper;
import com.shanjupay.merchant.mapper.StaffMapper;
import com.shanjupay.merchant.mapper.StoreMapper;
import com.shanjupay.merchant.mapper.StoreStaffMapper;
import com.shanjupay.user.api.TenantService;
import com.shanjupay.user.api.dto.tenant.CreateTenantRequestDTO;
import com.shanjupay.user.api.dto.tenant.TenantDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantMapper merchantMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    StaffMapper staffMapper;
    @Autowired
    StoreStaffMapper storeStaffMapper;
    @Reference
    TenantService tenantService;

    @Override
    public MerchantDTO queryMerchantById(Long id) {
        Merchant merchant=merchantMapper.selectById(id);
        MerchantDTO merchantDTO=new MerchantDTO();
        merchantDTO.setId(merchant.getId());
        merchantDTO.setMerchantName(merchant.getMerchantName());
        return merchantDTO;
    }

    /**
     *注册商户服务接口，接收账号，密码，手机号，为可拓展性使用DTO接收参数
     * 调用SaaS接口新增租户，用户，绑定用户和租户的关系，初始化权限
     * @param merchantDTO 商户注册信息
     * @return 注册成功的商户信息
     */
    @Override
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException {
        //检验参数合法性
        if(merchantDTO==null){
            throw new BusinessException(CommonErrorCode.E_100108);
        }

        //手机号是否为空
        if(StringUtil.isBlank(merchantDTO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100112);
        }

        if(StringUtil.isBlank(merchantDTO.getPassword())){
            throw new BusinessException(CommonErrorCode.E_100111);
        }

        //手机号格式校验
        if(!PhoneUtil.isMatches(merchantDTO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100109);
        }

        //校验手机号的唯一性
        //根据手机号查询商户表,如果存在记录则说明手机号已存在
        Integer count=merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getMobile,merchantDTO.getMobile()));
        if(count>0){
            throw new BusinessException(CommonErrorCode.E_100113);
        }

        //调用SaaS接口
        //构造调用参数
        CreateTenantRequestDTO createTenantRequestDTO=new CreateTenantRequestDTO();
        createTenantRequestDTO.setMobile(merchantDTO.getMobile());//手机号
        createTenantRequestDTO.setUsername(merchantDTO.getUsername());//用户名
        createTenantRequestDTO.setPassword(merchantDTO.getPassword());//密码
        createTenantRequestDTO.setTenantTypeCode("shanju-merchant");//租户类型
        createTenantRequestDTO.setBundleCode("shanju-merchant");//套餐，根据套餐分配权限
        createTenantRequestDTO.setName(merchantDTO.getUsername());//租户名称，和账号名一样

        //如果租户在SaaS已经存在，SaaS直接返回此租户的信息，否则进行添加
        TenantDTO tenantAndAccount = tenantService.createTenantAndAccount(createTenantRequestDTO);
        //获取租户id
        if(tenantAndAccount==null||tenantAndAccount.getId()==null){
            throw new BusinessException(CommonErrorCode.E_200012);
        }
        Long tenantId=tenantAndAccount.getId();

        //租户id在商户表唯一[租户和商户是一一对应]
        //根据租户id从商户表查询，如果存在记录则不允许添加商户
        Integer count1 = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getTenantId, tenantId));
        if(count1>0){
            throw new BusinessException(CommonErrorCode.E_200017);//商户租户重复注册
        }

        //调用mapper向数据库写入记录
        Merchant merchant=new Merchant();
        //使用mapstruct进行对象转换
        merchant=MerchantConvert.INSTANCE.dto2entity(merchantDTO);

        //设置所对应的租户的id
        merchant.setTenantId(tenantId);

//        //审核状态为0-未进行资质申请
        merchant.setAuditStatus("0");
        merchantMapper.insert(merchant);

        //新增门店
        StoreDTO storeDTO=new StoreDTO();
        storeDTO.setStoreName("根门店");
        storeDTO.setMerchantId(merchant.getId());//商户id
        StoreDTO store = createStore(storeDTO);

        //新增员工
        StaffDTO staffDTO=new StaffDTO();
        staffDTO.setMobile(merchantDTO.getMobile());//手机号
        staffDTO.setUsername(merchantDTO.getUsername());//账号
        staffDTO.setStoreId(store.getId());//员工所属门店id
        staffDTO.setMerchantId(merchant.getId());//商户id
        StaffDTO staff = createStaff(staffDTO);

        //为门店设置管理员
        bindStaffToStore(store.getId(),staff.getId());

        //将dto中写入新增商户的id
//        merchantDTO.setId(merchant.getId());
        //将entity转换为dto
        MerchantDTO merchantdto1=MerchantConvert.INSTANCE.entity2dto(merchant);
        return merchantdto1;
    }

    /**
     * 资质申请保存接口
     *
     * @param merchantId 商户id
     * @param merchantDTO 资质申请的信息
     * @throws BusinessException
     */
    @Override
    @Transactional
    public void applyMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException {
        if(merchantId==null||merchantDTO==null){
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //校验商户id合法性,查询商户表，如果查询不到记录，认为非法
       Merchant merchant= merchantMapper.selectById(merchantId);
        if(merchant==null){
            throw new BusinessException(CommonErrorCode.E_200002);
        }



        //dto转成entity
        Merchant entity=MerchantConvert.INSTANCE.dto2entity(merchantDTO);
        //将必要的参数设置到entity
        entity.setId(merchantId);
        entity.setMobile(merchant.getMobile());//资质申请手机号不让改，还使用数据库手机号
        entity.setAuditStatus("1");//已申请待审核
        entity.setTenantId(merchant.getTenantId());

        //maper更新商户表
        merchantMapper.updateById(entity);
    }

    /**
     * 新增门店
     * @param storeDTO 门店信息
     * @return 新增成功的门店信息
     * @throws BusinessException
     */
    @Override
    public StoreDTO createStore(StoreDTO storeDTO) throws BusinessException {
        Store entity= StoreConvert.INSTANCE.dto2entity(storeDTO);
        log.info("新增门店：{}", JSON.toJSONString(entity));
        //新增门店
        storeMapper.insert(entity);
        return StoreConvert.INSTANCE.entity2dto(entity);
    }

    /**
     *  新增员工信息
     * @param staffDTO 员工信息
     * @return 新增成功的员工信息
     * @throws BusinessException
     */
    @Override
    public StaffDTO createStaff(StaffDTO staffDTO) throws BusinessException {
        //参数合法性校验
        if(staffDTO==null||StringUtils.isBlank(staffDTO.getMobile())
                || StringUtils.isBlank(staffDTO.getUsername())
                ||staffDTO.getStoreId()==null){
            throw new BusinessException(CommonErrorCode.E_300009);
        }


        //在同一个商户下员工的账号和手机号唯一
        Boolean existStaffByMobile = isExistStaffByMobile(staffDTO.getMobile(), staffDTO.getMerchantId());
        Boolean existStaffByUsername = isExistStaffByUsername(staffDTO.getUsername(), staffDTO.getMerchantId());
        if(existStaffByMobile){
            throw new BusinessException(CommonErrorCode.E_100113);
        }

        if(existStaffByUsername){
            throw new BusinessException(CommonErrorCode.E_100114);
        }

        Staff staff = StaffConvert.INSTANCE.dto2entity(staffDTO);
        log.info("商户下新增员工：{}",JSON.toJSONString(staff));
        staffMapper.insert(staff);
        return StaffConvert.INSTANCE.entity2dto(staff);
    }

    /**
     *  绑定门店和员工（设置guanliyuan）
     * @param storeId 门店id
     * @param staffId 员工id
     * @throws BusinessException
     */
    @Override
    public void bindStaffToStore(Long storeId, Long staffId) throws BusinessException {
        StoreStaff storeStaff=new StoreStaff();
        storeStaff.setStaffId(staffId);
        storeStaff.setStoreId(storeId);
        storeStaffMapper.insert(storeStaff);
    }


    /**
     * 校验手机号
     * @param mobile
     * @param merchantId
     * @return
     */
    private Boolean isExistStaffByMobile(String mobile,Long merchantId){
        Integer count = staffMapper.selectCount(new LambdaQueryWrapper<Staff>().eq(Staff::getMobile, mobile).eq(Staff::getMerchantId, merchantId));
        return count>0;
    }

    /**
     *  校验用户名
     * @param userName
     * @param merchantId
     * @return
     */
    private Boolean isExistStaffByUsername(String userName,Long merchantId){
        Integer count = staffMapper.selectCount(new LambdaQueryWrapper<Staff>().eq(Staff::getUsername, userName).eq(Staff::getMerchantId, merchantId));
        return count>0;
    }
}
