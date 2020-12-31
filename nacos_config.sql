/*
 Navicat Premium Data Transfer

 Source Server         : mer
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : nacos_config

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 22/12/2020 17:27:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (1, 'spring-boot-http.yaml', 'COMMON_GROUP', 'spring:\r\n  http:\r\n    encoding:\r\n      charset: UTF-8\r\n      force: true\r\n      enabled: true\r\n  messages:\r\n    encoding: UTF-8\r\n\r\nserver:\r\n  tomcat:\r\n    remote_ip_header: x-forwarded-for\r\n    protocol_header: x-forwarded-proto\r\n  servlet:\r\n    context-path: /\r\n  use-forward-headers: true\r\n\r\nmanagement:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: refresh,health,info,env\r\n                ', '305c71f8be0ad1066b6982ab5b4ece03', '2020-11-20 17:54:28', '2020-11-20 17:54:28', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (2, 'merchant-application.yaml', 'SHANJUPAY_GROUP', 'server:\r\n  servlet:\r\n    context-path: /merchant\r\n\r\nswagger:\r\n  enabled: true\r\n\r\nsms:\r\n  url: \"http://localhost:56085/sailing\"\r\n  effectiveTime: 600\r\n\r\noss:\r\n  qiniu:\r\n    url: \"qk8p0z4lq.hn-bkt.clouddn.com\"\r\n    accessKey: \"ySgV69TMadY8fkgsFb81Av6OQsNtkXykrdf5O-oh\"\r\n    secretKey: \"kAO1e3gAGhUc4hX30GTUftmIM7sXhLdENdFUTlEz\"\r\n    bucket: \"mer-shanjupay\"', 'bceece7e9db3f4abcd513c733bf1cfa0', '2020-11-20 17:54:28', '2020-11-24 09:42:04', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (3, 'merchant-service.yaml', 'SHANJUPAY_GROUP', 'server:\r\n  servlet:\r\n    context-path: /merchant-service\r\n\r\n\r\nspring:\r\n  datasource:\r\n    druid:\r\n      url: jdbc:mysql://127.0.0.1:3306/shanjupay_merchant_service?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\r\n      username: root\r\n      password: lwb123\r\n\r\nmybatis-plus:\r\n  typeAliasesPackage: com.shanjupay.merchant.entity\r\n  mapper-locations: classpath:com/shanjupay/*/mapper/*.xml', '17b91a26839eed2b43d4325ebca7ba45', '2020-11-20 17:54:28', '2020-11-20 17:54:28', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (4, 'spring-boot-starter-druid.yaml', 'COMMON_GROUP', 'spring:\r\n  datascource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/oauth?useUnicode=true\r\n    druid:\r\n      initial-size: 5\r\n      min-idle: 5\r\n      max-active: 20\r\n      max-wait: 60000\r\n      time-between-eviction-runs-millis: 60000\r\n      min-evictable-idle-time-millis: 300000\r\n      validation-query: SELECT 1 FROM DUAL\r\n      test-while-idle: true\r\n      test-on-borrow: true\r\n      test-on-return: false\r\n      pool-prepared-statements: true\r\n      max-pool-prepared-statement-per-conncetion-size: 20\r\n      filter:\r\n        stat:\r\n          slow-sql-millis: 1\r\n          log-slow-sql: true\r\n      filters: config,stat,wall,log4j2\r\n      web-stat-filter:\r\n        enabled: true\r\n        url-pattern: /*\r\n        exclusions: \"*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*\"\r\n        session-stat-enable: false\r\n        session-stat-max-count: 1000\r\n        principal-cookie-name: admin\r\n        principal-session-name: admin\r\n        profile-enable: true\r\n      stat-view-servlet:\r\n        enabled: true\r\n        url-pattern: /druid/*\r\n        allow: 127.0.0.1,192.168.163.1\r\n        deny: 192.168.1.73\r\n        reset-enable: false\r\n        login-password: admin\r\n        login-username: admin\r\n      aop-patterns: com.shanjupay.*.service.*', 'a0387a9e81111568ac47e4d4592e8d3c', '2020-11-20 17:54:28', '2020-11-20 17:54:28', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (5, 'spring-boot-mybatis-plus.yaml', 'COMMON_GROUP', 'mybatis-plus:\r\n  configuration:\r\n    cache-enabled: false\r\n    map-underscore-to-camel: true\r\n  global-config:\r\n    id-type: 0\r\n    field-strategy: 0\r\n    db-column-underline: true\r\n    refresh-mapper: true\r\n  typeAliasesPackage: com.shanjupay.user.entity\r\n  mapper-locations: classpath:com/shanjupay/*/mapper/*.xml', '75adb4fbf791e971e5ffec54994e8f95', '2020-11-20 17:54:28', '2020-11-20 17:54:28', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (8, 'transaction-service.yaml', 'SHANJUPAY_GROUP', '#覆盖spring-boot-http-yaml项目\r\nserver:\r\n  servlet:\r\n    context-path: /transaction\r\n\r\n#覆盖spring-boot-starter-druid.yaml项目\r\nspring:\r\n  datasource:\r\n    druid:\r\n      url: jdbc:mysql://127.0.0.1:3306/shanjupay_transaction?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\r\n      username: root\r\n      password: lwb123\r\n\r\n#覆盖spring-boot-mybatis-plus.yaml\r\nmybatis-plus:\r\n  typeAliasesPackage: com.shanjupay.transaction.entity\r\n  mapper-locations: claspath:com/shanjupay/*/mapper/*.xml', '8ecf26edda4fdb8091d918f80710a69e', '2020-11-24 16:26:45', '2020-11-24 16:26:45', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (9, 'spring-boot-redis.yaml', 'COMMON_GROUP', 'spring:\r\n  redis:\r\n    #Redis数据库索引\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 1000ms\r\n    lettuce:\r\n      pool:\r\n        max-idle: 8\r\n        min-idle: 0\r\n        max-active: 8\r\n        max-wait: 1000ms\r\n      shutdown-timeout: 1000ms', '15bbf0898be3c371f4ff06696a34f2dd', '2020-12-09 13:41:57', '2020-12-09 13:41:57', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (10, 'jwt.yaml', 'COMMON_GROUP', 'siging-key: shanju123', 'dbf1ac8e8c13511ae324487b3de124b8', '2020-12-11 13:50:57', '2020-12-11 14:11:38', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info` VALUES (11, 'gateway-service.yaml', 'SHANJUPAY_GROUP', '#路由规则\r\nzuul:\r\n  retryable: true\r\n  add-host-header: true\r\n  ignoredServices: \"*\"\r\n  sensitiveHeaders: \"*\"\r\n  routes:\r\n    operation-application:\r\n      path: /operation/**\r\n      stripPrefix: false\r\n    merchant-application:\r\n      path: /merchant/**\r\n      stripPrefix: false\r\n    uaa-service: \r\n      path: /uaa/**\r\n      stripPrefix: false\r\n\r\nfeign:\r\n  hystrix:\r\n    enabled: true\r\n  compression:\r\n    request:\r\n      enabled: true # 配置请求GZIP压缩\r\n      mime-types: [\"text/xml\",\"application/xml\",\"application/json\"] # 配置压缩支持的MIME TYPE\r\n      min-request-size: 2048 # 配置压缩数据大小的下限\r\n    response:\r\n      enabled: true # 配置响应GZIP压缩\r\n\r\nhystrix:\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 93000  # 设置熔断超时时间  default 1000\r\n        timeout:\r\n          enabled: true # 打开超时熔断功能 default true\r\n\r\nribbon:\r\n  nacos:\r\n    enabled: true # 不知道是否生效\r\n  ConnectTimeout: 3000 # 设置连接超时时间 default 2000\r\n  ReadTimeout: 20000    # 设置读取超时时间  default 5000\r\n  OkToRetryOnAllOperations: false # 对所有操作请求都进行重试  default false\r\n  MaxAutoRetriesNextServer: 1    # 切换实例的重试次数  default 1\r\n  MaxAutoRetries: 1     # 对当前实例的重试次数 default 0', '36becc60dcc0d752986a63525eb61fce', '2020-12-11 13:52:53', '2020-12-11 13:52:53', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (12, 'uaa-service.yaml', 'SHANJUPAY_GROUP', '#覆盖spring-boot-http.yaml的项目\r\nserver:\r\n  servlet:\r\n    context-path: /uaa\r\n\r\n#覆盖spring-boot-starter-druid.yaml项目\r\nspring:\r\n  dataSource:\r\n    druid:\r\n      url: jdbc:mysql://127.0.0.1:3306/shanjupay_uaa?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\r\n      username: root\r\n      password: lwb123', 'dd274b6f887c3c491e1e5ab2b25337db', '2020-12-11 14:01:05', '2020-12-11 14:01:05', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (13, 'user-service.yaml', 'SHANJUPAY_GROUP', '#覆盖spring-boot-http.yaml的项目\r\nserver:\r\n  servlet:\r\n    context-path: /user\r\n\r\n#覆盖spring-boot-starter-druid.yaml项目\r\nspring:\r\n  dataSource:\r\n    druid:\r\n      url: jdbc:mysql://127.0.0.1:3306/shanjupay_user?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\r\n      username: root\r\n      password: lwb123\r\n\r\n#覆盖spring-boot-mybatis-plus.yaml项目\r\nmybatis-plus:\r\n  typeAliasesPackage: com.shanjupay.user.entity\r\n  mapper-locations: classpath:com/shanjupay/*/mapper/xml/*.xml\r\n\r\nsms:\r\n  url: \"http://localhost:56085/sailing\"\r\n  effectiveTime: 6000', '518e24e56d1de87c18daa48362b90420', '2020-12-11 14:06:21', '2020-12-11 16:46:58', NULL, '0:0:0:0:0:0:0:1', '', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', 'null', 'null', 'null', 'yaml', 'null');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(64) UNSIGNED NOT NULL,
  `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (2, 6, 'merchant-application.yaml', 'SHANJUPAY_GROUP', '', 'server:\r\n  servlet:\r\n    context-path: /merchant\r\n\r\n\r\nswagger:\r\n  enabled: true', 'b177265051014361c20f3b54db494203', '2020-11-23 10:47:30', '2020-11-23 10:47:30', NULL, '0:0:0:0:0:0:0:1', 'U', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357');
INSERT INTO `his_config_info` VALUES (2, 7, 'merchant-application.yaml', 'SHANJUPAY_GROUP', '', 'server:\r\n  servlet:\r\n    context-path: /merchant\r\n\r\nswagger:\r\n  enabled: true\r\n\r\nsms:\r\n  url: \"http://localhost:56085/sailing\"\r\n  effectiveTime: 600', 'efa978c4c798fe310011401c311a974b', '2020-11-24 09:42:03', '2020-11-24 09:42:04', NULL, '0:0:0:0:0:0:0:1', 'U', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357');
INSERT INTO `his_config_info` VALUES (0, 8, 'transaction-service.yaml', 'SHANJUPAY_GROUP', '', '#覆盖spring-boot-http-yaml项目\r\nserver:\r\n  servlet:\r\n    context-path: /transaction\r\n\r\n#覆盖spring-boot-starter-druid.yaml项目\r\nspring:\r\n  datasource:\r\n    druid:\r\n      url: jdbc:mysql://127.0.0.1:3306/shanjupay_transaction?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\r\n      username: root\r\n      password: lwb123\r\n\r\n#覆盖spring-boot-mybatis-plus.yaml\r\nmybatis-plus:\r\n  typeAliasesPackage: com.shanjupay.transaction.entity\r\n  mapper-locations: claspath:com/shanjupay/*/mapper/*.xml', '8ecf26edda4fdb8091d918f80710a69e', '2020-11-24 16:26:45', '2020-11-24 16:26:45', NULL, '0:0:0:0:0:0:0:1', 'I', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357');
INSERT INTO `his_config_info` VALUES (0, 9, 'spring-boot-redis.yaml', 'COMMON_GROUP', '', 'spring:\r\n  redis:\r\n    #Redis数据库索引\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 1000ms\r\n    lettuce:\r\n      pool:\r\n        max-idle: 8\r\n        min-idle: 0\r\n        max-active: 8\r\n        max-wait: 1000ms\r\n      shutdown-timeout: 1000ms', '15bbf0898be3c371f4ff06696a34f2dd', '2020-12-09 13:41:57', '2020-12-09 13:41:57', NULL, '0:0:0:0:0:0:0:1', 'I', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357');
INSERT INTO `his_config_info` VALUES (0, 10, 'jwt.yaml', 'COMMON_GROUP', '', 'sign-key: shanju123', '7ac7b6be54f7ea6aa4e648b058e86b7f', '2020-12-11 13:50:57', '2020-12-11 13:50:57', NULL, '0:0:0:0:0:0:0:1', 'I', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357');
INSERT INTO `his_config_info` VALUES (0, 11, 'gateway-service.yaml', 'SHANJUPAY_GROUP', '', '#路由规则\r\nzuul:\r\n  retryable: true\r\n  add-host-header: true\r\n  ignoredServices: \"*\"\r\n  sensitiveHeaders: \"*\"\r\n  routes:\r\n    operation-application:\r\n      path: /operation/**\r\n      stripPrefix: false\r\n    merchant-application:\r\n      path: /merchant/**\r\n      stripPrefix: false\r\n    uaa-service: \r\n      path: /uaa/**\r\n      stripPrefix: false\r\n\r\nfeign:\r\n  hystrix:\r\n    enabled: true\r\n  compression:\r\n    request:\r\n      enabled: true # 配置请求GZIP压缩\r\n      mime-types: [\"text/xml\",\"application/xml\",\"application/json\"] # 配置压缩支持的MIME TYPE\r\n      min-request-size: 2048 # 配置压缩数据大小的下限\r\n    response:\r\n      enabled: true # 配置响应GZIP压缩\r\n\r\nhystrix:\r\n  command:\r\n    default:\r\n      execution:\r\n        isolation:\r\n          thread:\r\n            timeoutInMilliseconds: 93000  # 设置熔断超时时间  default 1000\r\n        timeout:\r\n          enabled: true # 打开超时熔断功能 default true\r\n\r\nribbon:\r\n  nacos:\r\n    enabled: true # 不知道是否生效\r\n  ConnectTimeout: 3000 # 设置连接超时时间 default 2000\r\n  ReadTimeout: 20000    # 设置读取超时时间  default 5000\r\n  OkToRetryOnAllOperations: false # 对所有操作请求都进行重试  default false\r\n  MaxAutoRetriesNextServer: 1    # 切换实例的重试次数  default 1\r\n  MaxAutoRetries: 1     # 对当前实例的重试次数 default 0', '36becc60dcc0d752986a63525eb61fce', '2020-12-11 13:52:53', '2020-12-11 13:52:53', NULL, '0:0:0:0:0:0:0:1', 'I', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357');
INSERT INTO `his_config_info` VALUES (0, 12, 'uaa-service.yaml', 'SHANJUPAY_GROUP', '', '#覆盖spring-boot-http.yaml的项目\r\nserver:\r\n  servlet:\r\n    context-path: /uaa\r\n\r\n#覆盖spring-boot-starter-druid.yaml项目\r\nspring:\r\n  dataSource:\r\n    druid:\r\n      url: jdbc:mysql://127.0.0.1:3306/shanjupay_uaa?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\r\n      username: root\r\n      password: lwb123', 'dd274b6f887c3c491e1e5ab2b25337db', '2020-12-11 14:01:04', '2020-12-11 14:01:05', NULL, '0:0:0:0:0:0:0:1', 'I', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357');
INSERT INTO `his_config_info` VALUES (0, 13, 'user-service.yaml', 'SHANJUPAY_GROUP', '', '#覆盖spring-boot-http.yaml的项目\r\nserver:\r\n  servlet:\r\n    context-path: /user\r\n\r\n#覆盖spring-boot-starter-druid.yaml项目\r\nspring:\r\n  dataSource:\r\n    druid:\r\n      url: jdbc:mysql://127.0.0.1:3306/shanjupay_uaa?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\r\n      username: root\r\n      password: lwb123\r\n\r\n#覆盖spring-boot-mybatis-plus.yaml项目\r\nmybatis-plus:\r\n  typeAliasesPackage: com.shanjupay.user.entity\r\n  mapper-locations: classpath:com/shanjupay/*/mapper/xml/*.xml\r\n\r\nsms:\r\n  url: \"http://localhost:56085/sailing\"\r\n  effectiveTime: 6000', 'dbb6b5ce156ac855e49d121beb9c1ada', '2020-12-11 14:06:20', '2020-12-11 14:06:21', NULL, '0:0:0:0:0:0:0:1', 'I', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357');
INSERT INTO `his_config_info` VALUES (10, 14, 'jwt.yaml', 'COMMON_GROUP', '', 'sign-key: shanju123', '7ac7b6be54f7ea6aa4e648b058e86b7f', '2020-12-11 14:11:38', '2020-12-11 14:11:38', NULL, '0:0:0:0:0:0:0:1', 'U', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357');
INSERT INTO `his_config_info` VALUES (13, 15, 'user-service.yaml', 'SHANJUPAY_GROUP', '', '#覆盖spring-boot-http.yaml的项目\r\nserver:\r\n  servlet:\r\n    context-path: /user\r\n\r\n#覆盖spring-boot-starter-druid.yaml项目\r\nspring:\r\n  dataSource:\r\n    druid:\r\n      url: jdbc:mysql://127.0.0.1:3306/shanjupay_uaa?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\r\n      username: root\r\n      password: lwb123\r\n\r\n#覆盖spring-boot-mybatis-plus.yaml项目\r\nmybatis-plus:\r\n  typeAliasesPackage: com.shanjupay.user.entity\r\n  mapper-locations: classpath:com/shanjupay/*/mapper/xml/*.xml\r\n\r\nsms:\r\n  url: \"http://localhost:56085/sailing\"\r\n  effectiveTime: 6000', 'dbb6b5ce156ac855e49d121beb9c1ada', '2020-12-11 16:46:58', '2020-12-11 16:46:58', NULL, '0:0:0:0:0:0:0:1', 'U', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (1, '1', '9fd8a6b2-8155-44bd-9dc7-8d0aa297f357', 'dev', '开发', 'nacos', 1605843119422, 1605843119422);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
