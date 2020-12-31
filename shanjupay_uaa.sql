/*
 Navicat Premium Data Transfer

 Source Server         : mer
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : shanjupay_uaa

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 22/12/2020 17:28:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端标识',
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接入资源列表',
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端秘钥',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `archived` tinyint(4) NULL DEFAULT NULL,
  `trusted` tinyint(4) NULL DEFAULT NULL,
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '接入客户端信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('507f38136d4443f3a07f07d3ce5f9a05', 'shanju-resource', '', 'read', 'client_credentials,password,authorization_code,implicit,refresh_token', NULL, 'ROLE_API', 7200, 259200, NULL, '2019-11-13 08:18:32', NULL, 0, NULL);
INSERT INTO `oauth_client_details` VALUES ('5fe423628cd14143a94d05575323555d', 'shanju-resource', '', 'read', 'client_credentials,password,authorization_code,implicit,refresh_token', NULL, 'ROLE_API', 7200, 259200, NULL, '2019-11-13 11:00:29', NULL, 0, NULL);
INSERT INTO `oauth_client_details` VALUES ('8016770e53ab495cb88bacb626930ceb', 'shanju-resource', '', 'read', 'client_credentials,password,authorization_code,implicit,refresh_token', NULL, 'ROLE_API', 7200, 259200, NULL, '2019-11-13 08:16:27', NULL, 0, NULL);
INSERT INTO `oauth_client_details` VALUES ('e366cfc9373e440abbdbe5818aa6c91b', 'shanju-resource', '', 'read', 'client_credentials,password,authorization_code,implicit,refresh_token', NULL, 'ROLE_API', 7200, 259200, NULL, '2019-11-13 10:02:01', NULL, 0, NULL);
INSERT INTO `oauth_client_details` VALUES ('merchant-platform', 'shanju-resource', '123456', 'read', 'client_credentials,password,authorization_code,implicit,refresh_token', NULL, 'ROLE_MERCHANT,ROLE_USER', 31536000, 259200, NULL, '2019-11-22 04:49:31', 0, 0, 'false');
INSERT INTO `oauth_client_details` VALUES ('operation-platform', 'shanju-resource', '123456', 'read', 'client_credentials,password,authorization_code,implicit,refresh_token', NULL, 'ROLE_OPERATION', 7200, 259200, NULL, '2019-09-26 10:10:07', 0, 0, 'false');
INSERT INTO `oauth_client_details` VALUES ('portal-site', 'shanju-resource', '123456', 'read', 'client_credentials,password,authorization_code,implicit,refresh_token', NULL, 'ROLE_PORTAL', 7200, 259200, NULL, '2019-09-26 10:10:18', 0, 0, 'false');
INSERT INTO `oauth_client_details` VALUES ('shanju-resource', 'shanju-resource', 'suiyixie', 'read', 'client_credentials,password,authorization_code,implicit,refresh_token', NULL, 'ROLE_API', 7200, 259200, NULL, '2019-10-24 02:29:38', NULL, 0, NULL);

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权码',
  `authentication` longblob NULL COMMENT '认证信息',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '授权码' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
