package com.shanjupay.common.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;


/**
 * @author mercurius
 * @version 1.0
 * @date 2020/11/23 16:17
 */

public class QiniuUtils {
    private static final Logger logger= LoggerFactory.getLogger(QiniuUtils.class);
    /**
     * 文件上传工具方法
     * @param accessKey
     * @param secretKey
     * @param bucket
     * @param bytes
     * @param fileName 外部传进来，七牛云上的文件名称和此保持一致
     */
    public static void upload2qiniu(String accessKey,String secretKey,String bucket,byte[] bytes,String fileName) throws RuntimeException {
        //构造一个带指定 Region 对象的配置类,指定存储区域和存储空间选择的区域一致
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);

        //认证
        Auth auth = Auth.create(accessKey, secretKey);
        //认证通过后得到的token
        String upToken = auth.uploadToken(bucket);

        try {
            try {
                //上传文件,参数：字节数组，key，token
                Response response = uploadManager.put(bytes, fileName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                logger.error("上传文件到七牛：{}",ex.getMessage());
                try {
                    logger.error(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
                throw new RuntimeException(r.bodyString());
            }
        } catch (Exception e) {
            logger.error("上传文件到七牛：{}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    //测试文件上传
    private static void testUpload()  {
        //构造一个带指定 Region 对象的配置类,指定存储区域和存储空间选择的区域一致
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "ySgV69TMadY8fkgsFb81Av6OQsNtkXykrdf5O-oh";
        String secretKey = "kAO1e3gAGhUc4hX30GTUftmIM7sXhLdENdFUTlEz";
        String bucket = "mer-shanjupay";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = UUID.randomUUID().toString();

        try {
            //得到本地文件的字节数据
            String filePath="C:\\Users\\12345678\\Desktop\\MecuriusWorkSpace\\laptop\\01-bilibili\\icons\\storm.png";
            String suffix=filePath.substring(filePath.lastIndexOf("."));
            FileInputStream fileInputStream=new FileInputStream(new File(filePath));
            byte[] bytes= IOUtils.toByteArray(fileInputStream);
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            //认证
            Auth auth = Auth.create(accessKey, secretKey);
            //认证通过后得到的token
            String upToken = auth.uploadToken(bucket);

            try {
                //上传文件,参数：字节数组，key，token
                //key：建议自己生成一个不重复的名称
                Response response = uploadManager.put(bytes, key+suffix, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                logger.error("上传文件到七牛：{}",ex.getMessage());
                try {
                    logger.error(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 私有下载外链
     */
    public static void getPrivateDownloadUrl() throws UnsupportedEncodingException {
        String fileName = "bdf1ccb7-1d9c-48f3-940b-50a228b4f39b.png";
        String domainOfBucket = "http://qk8p0z4lq.hn-bkt.clouddn.com/";
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        String accessKey = "ySgV69TMadY8fkgsFb81Av6OQsNtkXykrdf5O-oh";
        String secretKey = "kAO1e3gAGhUc4hX30GTUftmIM7sXhLdENdFUTlEz";
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        System.out.println(finalUrl);
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        //上传测试
//        QiniuUtils.testUpload();

        QiniuUtils.getPrivateDownloadUrl();
    }
}
