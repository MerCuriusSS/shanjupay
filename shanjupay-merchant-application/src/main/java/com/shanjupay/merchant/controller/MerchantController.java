package com.shanjupay.merchant.controller;

import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.util.PhoneUtil;
import com.shanjupay.common.util.StringUtil;
import com.shanjupay.merchant.api.MerchantService;
import com.shanjupay.merchant.dto.MerchantDTO;

import com.shanjupay.merchant.convert.MerchantDetailConvert;
import com.shanjupay.merchant.convert.MerchantRegisterConvert;
import com.shanjupay.merchant.service.FileService;
import com.shanjupay.merchant.service.SmsService;
import com.shanjupay.merchant.vo.MerchantDetailVO;
import com.shanjupay.merchant.vo.MerchantRegisterVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class MerchantController extends BasicController{

    @Reference
    MerchantService merchantService;//注入远程调用的接口

    @Autowired
    SmsService smsService;//注入本地bean的接口

    @Autowired
    FileService fileService;

    @ApiOperation("根据id查询商户信息")
    @GetMapping(value="/merchants/{id}")
    public MerchantDTO queryMerchantById(@PathVariable("id") long id){
        MerchantDTO merchantDTO=merchantService.queryMerchantById(id);
        return merchantDTO;
    }


    /**
     * 获取手机验证码
     * @param phone
     * @return
     */
    @ApiOperation("获取手机验证码")
    @GetMapping("/sms")
    @ApiImplicitParam(value="手机号",name="phone",required = true,dataType = "String",paramType = "query")
    public String getSMSCode(@RequestParam("phone") String phone){
        //向验证码服务请求发送验证码
        return smsService.sendMsg(phone);
    }

    /**
     * 商户注册
     * @param merchantRegisterVO
     * @return
     */
    @ApiOperation("商户注册")
    @ApiImplicitParam(value="商户注册信息",name="merchantRegisterVO",required = true,dataType = "MerchantRegisterVO",paramType = "body")
    @PostMapping("/merchants/register")
    public MerchantRegisterVO registerMerchant(@RequestBody MerchantRegisterVO merchantRegisterVO){
        //检验参数合法性
        if(merchantRegisterVO==null){
            throw new BusinessException(CommonErrorCode.E_100108);
        }

        if(StringUtil.isBlank(merchantRegisterVO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100112);
        }

        //手机号格式校验
        if(!PhoneUtil.isMatches(merchantRegisterVO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100109);
        }


        //校验验证码
        smsService.checkVerifyCode(merchantRegisterVO.getVerifyCode(),merchantRegisterVO.getVerifykey());
        MerchantDTO merchantDTO=new MerchantDTO();
//        //向dto写入商户注册信息
//        merchantDTO.setMobile(merchantRegisterVO.getMobile());
//        merchantDTO.setUsername(merchantRegisterVO.getUsername());
        //...
        //vo转换为dto
        merchantDTO=MerchantRegisterConvert.INSTANCE.vo2Dto(merchantRegisterVO);
        //调用dubbo服务接口
        merchantService.createMerchant(merchantDTO);
        return null;
    }

    /**
     * 上传证件照
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @ApiOperation("上传证件照")
    @PostMapping("/upload")
    public String upload(@ApiParam(value="证件照",required = true) @RequestParam("file") MultipartFile multipartFile) throws IOException {
        //调用fileService上传文件
        //生成的文件名称fileName，要保证他的唯一
        //获取扩展名
        String suffix=multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        //文件名称
        String fileName= UUID.randomUUID().toString()+suffix;
        //返回完整路径
        return fileService.upload(multipartFile.getBytes(),fileName);
    }

    /**
     * 资质申请
     * @param merchantDetailVO
     */
    @ApiOperation("资质申请")
    @PostMapping("/my/merchants/save")
    @ApiImplicitParam(value="商户认证资料",name="merchantDetailVO",required = true,dataType = "MerchantDetailVO",paramType = "body")
    public void saveMerchant(@RequestBody MerchantDetailVO merchantDetailVO){
        //取出当前登录商户的id（暂时定义为1）
        Long merchantId=getLoginMerchantId();
        MerchantDTO merchantDTO= MerchantDetailConvert.INSTANCE.vo2Tto(merchantDetailVO);
        merchantService.applyMerchant(merchantId,merchantDTO);
    }




}
