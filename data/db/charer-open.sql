/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3306
 Source Schema         : charer-open

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 31/03/2025 21:41:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ads
-- ----------------------------
DROP TABLE IF EXISTS `ads`;
CREATE TABLE `ads` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '图片',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `status` int NOT NULL COMMENT '状态 启用1, 停用0',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of ads
-- ----------------------------
BEGIN;
INSERT INTO `ads` (`id`, `name`, `type`, `content`, `link`, `img`, `note`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('634017344553029', '{\"zh_CN\":\"ddd\",\"en_US\":null,\"ru_RU\":\"\"}', 'image', '{\"zh_CN\":\"\",\"en_US\":null,\"ru_RU\":\"\"}', NULL, 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_24_00b43e9472ff469b8a981d3523ddb18b.jpeg', 'ddd', 1, '1', NULL, '2025-01-15 07:23:13', '1', '2025-02-24 13:37:21', NULL);
COMMIT;

-- ----------------------------
-- Table structure for alert_config
-- ----------------------------
DROP TABLE IF EXISTS `alert_config`;
CREATE TABLE `alert_config` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '告警配置id',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `enable` bit(1) DEFAULT NULL COMMENT '是否启用',
  `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '告警严重度',
  `message_template_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联消息转发模板ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '告警名称',
  `rule_info_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联规则引擎ID',
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置所属用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of alert_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for alert_record
-- ----------------------------
DROP TABLE IF EXISTS `alert_record`;
CREATE TABLE `alert_record` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '告警记录id',
  `alert_time` bigint DEFAULT NULL COMMENT '告警时间',
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '告警详情',
  `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '告警严重度（1-5）',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '告警名称',
  `read_flg` bit(1) DEFAULT NULL COMMENT '是否已读',
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置所属用户',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of alert_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类id',
  `create_at` bigint DEFAULT NULL COMMENT '分类描述',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类名称',
  `model` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of category
-- ----------------------------
BEGIN;
INSERT INTO `category` (`id`, `create_at`, `name`, `model`) VALUES ('charger', 1650173898298, '充电桩', '[{\"identifier\":\"gunQty\",\"description\":null,\"unit\":null,\"name\":\"充电枪数量\",\"dataType\":{\"type\":\"int\"}},{\"identifier\":\"vc\",\"description\":null,\"unit\":null,\"name\":\"电压类型\",\"dataType\":{\"type\":\"enum\",\"specs\":{\"1\":\"直流\",\"2\":\"交流\"}}},{\"identifier\":\"power\",\"description\":null,\"unit\":null,\"name\":\"功率\",\"dataType\":{\"type\":\"int\"}}]');
COMMIT;

-- ----------------------------
-- Table structure for channel_config
-- ----------------------------
DROP TABLE IF EXISTS `channel_config`;
CREATE TABLE `channel_config` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '通道配置id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '通道配置名称',
  `identifier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '通道id',
  `properties` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '通道配置参数',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of channel_config
-- ----------------------------
BEGIN;
INSERT INTO `channel_config` (`id`, `name`, `identifier`, `properties`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('1', '短信', 'Sms', NULL, NULL, '1', NULL, '2024-12-15 21:27:46', NULL, NULL, NULL);
INSERT INTO `channel_config` (`id`, `name`, `identifier`, `properties`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('2', 'App', 'App', NULL, NULL, '1', NULL, '2024-12-15 21:27:46', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for charger
-- ----------------------------
DROP TABLE IF EXISTS `charger`;
CREATE TABLE `charger` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品key',
  `station_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '场站id',
  `price_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '计价规则id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备名称',
  `dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备dn',
  `secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备密钥',
  `low_balance` decimal(19,2) DEFAULT NULL COMMENT '充电最低余额',
  `online` int DEFAULT NULL COMMENT '设备状态',
  `online_time` bigint DEFAULT NULL COMMENT '设备在线时间',
  `offline_time` bigint DEFAULT NULL COMMENT '设备离线时间',
  `status` int DEFAULT NULL COMMENT '启用/停用',
  `last_update_time` bigint DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `dn` (`dn`) USING BTREE,
  KEY `product_key` (`product_key`),
  KEY `station_id` (`station_id`),
  KEY `price_id` (`price_id`),
  KEY `online` (`online`),
  KEY `status` (`status`),
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of charger
-- ----------------------------
BEGIN;
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `last_update_time`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('660227661180997', '03', '651348387233861', '586997419884613', '测试设备', '48000000000000', 'mSY4h7mG3piGAPz5', 2.00, 0, NULL, 1743427708387, 1, NULL, NULL, '1', NULL, '2025-03-30 08:53:16', '1', '2025-03-31 13:28:28');
COMMIT;

-- ----------------------------
-- Table structure for charger_directive
-- ----------------------------
DROP TABLE IF EXISTS `charger_directive`;
CREATE TABLE `charger_directive` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` int DEFAULT NULL COMMENT '关联类型',
  `dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备dn',
  `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '数据',
  `directive` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '指令名',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `relate_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联对象id',
  `result` int DEFAULT NULL COMMENT '执行结果',
  `serial` int DEFAULT NULL COMMENT '指令序列号',
  `state` int DEFAULT NULL COMMENT '执行状态',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of charger_directive
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for charger_gun
-- ----------------------------
DROP TABLE IF EXISTS `charger_gun`;
CREATE TABLE `charger_gun` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `charger_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备id',
  `parking_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '车位id',
  `no` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '枪号',
  `speed` int DEFAULT NULL COMMENT '快充/慢充',
  `current` int DEFAULT NULL COMMENT '直交流',
  `power` int DEFAULT NULL COMMENT '功率',
  `back` int DEFAULT NULL COMMENT '是否归位',
  `slot` int DEFAULT NULL COMMENT '是否插枪',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `charger_id` (`charger_id`,`no`) USING BTREE,
  KEY `state` (`state`) USING BTREE,
  KEY `parking_id` (`parking_id`),
  KEY `no` (`no`),
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of charger_gun
-- ----------------------------
BEGIN;
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('660227661254725', '660227661180997', NULL, '01', 1, 2, 7, NULL, NULL, '-1', '1', NULL, '2025-03-30 08:53:16', '1', '2025-03-30 08:53:16');
COMMIT;

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `scope` int DEFAULT NULL COMMENT '适用场站',
  `apply` int DEFAULT NULL COMMENT '限制抵扣',
  `station_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '场站ids',
  `accept` int DEFAULT NULL COMMENT '接收用户',
  `accept_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '适用用户ids',
  `amount` decimal(19,2) DEFAULT NULL COMMENT '金额',
  `enable_time` datetime DEFAULT NULL COMMENT '启用时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '描述',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `scope` (`scope`) USING BTREE,
  KEY `apply` (`apply`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值方案';

-- ----------------------------
-- Records of coupon
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for coupon_code
-- ----------------------------
DROP TABLE IF EXISTS `coupon_code`;
CREATE TABLE `coupon_code` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `coupon_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '优惠券id',
  `tran_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '优惠券编号',
  `scope` int DEFAULT NULL COMMENT '适用场站',
  `apply` int DEFAULT NULL COMMENT '限制抵扣',
  `station_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '场站ids',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户id',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `amount` decimal(10,2) DEFAULT NULL,
  `remained_amount` decimal(19,2) DEFAULT NULL,
  `state` int DEFAULT NULL COMMENT '状态',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `enable_time` datetime DEFAULT NULL COMMENT '启用时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `coupon_id` (`coupon_id`) USING BTREE,
  KEY `tran_id` (`tran_id`) USING BTREE,
  KEY `scope` (`scope`) USING BTREE,
  KEY `apply` (`apply`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE,
  KEY `state` (`state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值方案';

-- ----------------------------
-- Records of coupon_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户类型（sys_user系统用户）',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '用户头像',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户性别',
  `balance_amount` decimal(10,2) DEFAULT NULL,
  `give_amount` decimal(10,2) DEFAULT NULL,
  `quota_amount` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `status` int DEFAULT NULL COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL,
  `physical_card_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `logical_card_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of customer
-- ----------------------------
BEGIN;
INSERT INTO `customer` (`id`, `user_name`, `password`, `nick_name`, `type`, `mobile`, `avatar`, `sex`, `balance_amount`, `give_amount`, `quota_amount`, `status`, `login_ip`, `login_date`, `physical_card_no`, `logical_card_no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1', 'admin', '2222', '测试用户', 'Wechat', NULL, NULL, '3', 0.00, 0.00, NULL, 0, '127.0.0.1', '2024-09-25 11:04:23', '10000', '20000', NULL, NULL, NULL, '2024-09-25 11:04:43', '1', '2024-10-10 00:57:06');
COMMIT;

-- ----------------------------
-- Table structure for customer_login
-- ----------------------------
DROP TABLE IF EXISTS `customer_login`;
CREATE TABLE `customer_login` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
  `login_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'appCid',
  `platform` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'app平台',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'App设备',
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'App操作系统版本',
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL,
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of customer_login
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customer_notify
-- ----------------------------
DROP TABLE IF EXISTS `customer_notify`;
CREATE TABLE `customer_notify` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` int DEFAULT NULL,
  `customer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `entity_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scope` int DEFAULT NULL,
  `state` int DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `tenant_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of customer_notify
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for map_config
-- ----------------------------
DROP TABLE IF EXISTS `map_config`;
CREATE TABLE `map_config` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `identifier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '识别符',
  `properties` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '配置参数',
  `status` int DEFAULT NULL,
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of map_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告标题',
  `notice_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告内容',
  `notice_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告标题',
  `notice_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告类型（1通知 2公告）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告状态（0正常 1关闭）',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告内容',
  `state` int DEFAULT NULL COMMENT '公告状态',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告类型（1通知 2公告）',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of notice
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for notify_config
-- ----------------------------
DROP TABLE IF EXISTS `notify_config`;
CREATE TABLE `notify_config` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `identifier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '识别符',
  `properties` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '配置参数',
  `status` int DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of notify_config
-- ----------------------------
BEGIN;
INSERT INTO `notify_config` (`id`, `name`, `identifier`, `properties`, `status`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1', '充电完成通知', 'ChargeFinish', '{\"isSendSms\":\"N\",\"smsConfigId\":\"1\",\"smsTemplateId\":\"\",\"smsContent\":\"eeefffff\",\"isSendPush\":\"Y\",\"pushWechatTemplateId\":\"111111\",\"pushAppTitle\":\"eeeeeee\",\"pushAppContent\":\"eeeeeefff\"}', 1, NULL, '1', NULL, '2025-02-12 21:37:28', '1', '2025-03-30 08:55:55');
COMMIT;

-- ----------------------------
-- Table structure for notify_message
-- ----------------------------
DROP TABLE IF EXISTS `notify_message`;
CREATE TABLE `notify_message` (
  `id` bigint NOT NULL COMMENT '通知消息id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_at` bigint DEFAULT NULL,
  `message_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `update_at` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of notify_message
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for oper_log
-- ----------------------------
DROP TABLE IF EXISTS `oper_log`;
CREATE TABLE `oper_log` (
  `id` bigint NOT NULL COMMENT '日志主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作模块',
  `business_type` int DEFAULT NULL COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `cost_time` bigint DEFAULT NULL COMMENT '消耗时间',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门名称',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '错误消息',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '返回参数',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求方法',
  `oper_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作地点',
  `oper_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人员',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求参数',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求url',
  `operator_type` int DEFAULT NULL COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `request_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求方式',
  `status` int DEFAULT NULL COMMENT '操作状态（0正常 1异常）',
  `tenant_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of oper_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `charge_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `charge_pay_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `charge_start_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `charge_stop_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tran_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单号',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户用户名',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户id',
  `customer_login_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '价格id',
  `price_properties` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '分时电价',
  `charger_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备名称',
  `charger_dn` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备dn',
  `gun_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '充电枪号',
  `station_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `station_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `station_address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `tran_type` int DEFAULT NULL,
  `tran_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL COMMENT '开始充电时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束充电时间',
  `sharp_price` decimal(10,5) DEFAULT NULL COMMENT '尖价格',
  `sharp_qty` decimal(10,4) DEFAULT NULL COMMENT '尖数量',
  `sharp_quantity` decimal(10,4) DEFAULT NULL COMMENT '尖数量(计损)',
  `sharp_amount` decimal(10,4) DEFAULT NULL,
  `peak_price` decimal(10,5) DEFAULT NULL,
  `peak_qty` decimal(10,4) DEFAULT NULL,
  `peak_quantity` decimal(10,4) DEFAULT NULL,
  `peak_amount` decimal(10,4) DEFAULT NULL,
  `flat_price` decimal(10,5) DEFAULT NULL,
  `flat_qty` decimal(10,4) DEFAULT NULL,
  `flat_quantity` decimal(10,4) DEFAULT NULL,
  `flat_amount` decimal(10,4) DEFAULT NULL,
  `valley_price` decimal(10,5) DEFAULT NULL,
  `valley_qty` decimal(10,4) DEFAULT NULL,
  `valley_quantity` decimal(10,4) DEFAULT NULL,
  `valley_amount` decimal(10,4) DEFAULT NULL,
  `quantity_start` decimal(10,4) DEFAULT NULL,
  `quantity_end` decimal(10,4) DEFAULT NULL,
  `total_qty` decimal(10,4) DEFAULT NULL,
  `voltage` float DEFAULT NULL,
  `current` float DEFAULT NULL,
  `power` float DEFAULT NULL,
  `vin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stop_reason` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `physical_card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `soc` int DEFAULT NULL,
  `charge_minute` int DEFAULT NULL,
  `remain_minute` int DEFAULT NULL,
  `total_quantity` decimal(10,4) DEFAULT NULL,
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '设备上报订单金额',
  `elec_amount` decimal(10,2) DEFAULT NULL COMMENT '设备上报电费金额',
  `service_amount` decimal(10,2) DEFAULT NULL COMMENT '设备上报服务费金额',
  `park_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '占位id',
  `state` int DEFAULT NULL COMMENT '1启用/0停用',
  `deal` int DEFAULT NULL COMMENT '分成',
  `notify` int DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `tran_id` (`tran_id`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE,
  KEY `price_id` (`price_id`) USING BTREE,
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充电订单表';

-- ----------------------------
-- Records of orders
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for orders_settle
-- ----------------------------
DROP TABLE IF EXISTS `orders_settle`;
CREATE TABLE `orders_settle` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `customer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户id',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单id',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL COMMENT '原始金额',
  `settled_amount` decimal(10,2) DEFAULT NULL COMMENT '结算金额',
  `discount_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠金额',
  `discount_type` int DEFAULT NULL,
  `discount_relate_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `discount_quota_amount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE,
  KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充电订单表';

-- ----------------------------
-- Records of orders_settle
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for park
-- ----------------------------
DROP TABLE IF EXISTS `park`;
CREATE TABLE `park` (
  `id` varchar(255) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_dept` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `car_plate` varchar(255) DEFAULT NULL,
  `car_plate_type` int DEFAULT NULL,
  `dcam_dn` varchar(255) DEFAULT NULL,
  `dcam_id` varchar(255) DEFAULT NULL,
  `in_bg_image` varchar(255) DEFAULT NULL,
  `in_feture_image` varchar(255) DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `out_bg_image` varchar(255) DEFAULT NULL,
  `out_feture_image` varchar(255) DEFAULT NULL,
  `out_time` datetime DEFAULT NULL,
  `parking_id` varchar(255) DEFAULT NULL,
  `parking_name` varchar(255) DEFAULT NULL,
  `parking_no` varchar(255) DEFAULT NULL,
  `settle` int DEFAULT NULL,
  `state` int DEFAULT NULL,
  `station_address` varchar(255) DEFAULT NULL,
  `station_id` varchar(255) DEFAULT NULL,
  `station_name` varchar(255) DEFAULT NULL,
  `tran_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of park
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for parking
-- ----------------------------
DROP TABLE IF EXISTS `parking`;
CREATE TABLE `parking` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `parking_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '车位id',
  `station_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '充电场ID',
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '场站名称',
  `no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编号',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `station_id` (`station_id`,`no`) USING BTREE,
  KEY `station_id_2` (`station_id`) USING BTREE,
  KEY `no` (`no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of parking
-- ----------------------------
BEGIN;
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632278567116869', NULL, '632275846434885', '{\"zh_CN\":\"01车位\",\"en_US\":null,\"ru_RU\":\"\"}', '01', '01车位', '1', NULL, '2025-01-10 09:28:07', '1', '2025-01-10 09:28:07');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632278605807685', NULL, '632275846434885', '{\"zh_CN\":\"02车位\",\"en_US\":null,\"ru_RU\":\"\"}', '02', '01车位', '1', NULL, '2025-01-10 09:28:16', '1', '2025-01-10 09:28:16');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632278664446021', NULL, '632275846434885', '{\"zh_CN\":\"03车位\",\"en_US\":null,\"ru_RU\":\"\"}', '03', '03车位', '1', NULL, '2025-01-10 09:28:30', '1', '2025-01-10 09:28:30');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632278711525445', NULL, '632275846434885', '{\"zh_CN\":\"04车位\",\"en_US\":null,\"ru_RU\":\"\"}', '04', '', '1', NULL, '2025-01-10 09:28:42', '1', '2025-01-10 09:28:42');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632278753599557', NULL, '632275846434885', '{\"zh_CN\":\"05车位\",\"en_US\":null,\"ru_RU\":\"\"}', '05', NULL, '1', NULL, '2025-01-10 09:28:52', '1', '2025-01-10 09:28:52');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632278785519685', NULL, '632275846434885', '{\"zh_CN\":\"06车位\",\"en_US\":null,\"ru_RU\":\"\"}', '06', NULL, '1', NULL, '2025-01-10 09:29:00', '1', '2025-01-10 09:29:00');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632278813716549', NULL, '632275846434885', '{\"zh_CN\":\"07车位\",\"en_US\":null,\"ru_RU\":\"\"}', '07', NULL, '1', NULL, '2025-01-10 09:29:07', '1', '2025-01-10 09:29:07');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632278857273413', NULL, '632275846434885', '{\"zh_CN\":\"08车位\",\"en_US\":null,\"ru_RU\":\"\"}', '08', '08车位', '1', NULL, '2025-01-10 09:29:17', '1', '2025-01-10 09:29:17');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632278902816837', NULL, '632275846434885', '{\"zh_CN\":\"09车位\",\"en_US\":null,\"ru_RU\":\"\"}', '09', NULL, '1', NULL, '2025-01-10 09:29:29', '1', '2025-01-10 09:29:29');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632278931173445', NULL, '632275846434885', '{\"zh_CN\":\"10车位\",\"en_US\":null,\"ru_RU\":\"\"}', '10', NULL, '1', NULL, '2025-01-10 09:29:35', '1', '2025-01-10 09:29:35');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633304251904069', NULL, '632275846434885', '{\"zh_CN\":\"11车位\",\"en_US\":null,\"ru_RU\":\"\"}', '11', NULL, '1', NULL, '2025-01-13 07:01:38', '1', '2025-01-13 07:01:38');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633304276906053', NULL, '632275846434885', '{\"zh_CN\":\"12车位\",\"en_US\":null,\"ru_RU\":\"\"}', '12', NULL, '1', NULL, '2025-01-13 07:01:44', '1', '2025-01-13 07:01:44');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633304301199429', NULL, '632275846434885', '{\"zh_CN\":\"13车位\",\"en_US\":null,\"ru_RU\":\"\"}', '13', NULL, '1', NULL, '2025-01-13 07:01:50', '1', '2025-01-13 07:01:50');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633304324767813', NULL, '632275846434885', '{\"zh_CN\":\"14车位\",\"en_US\":null,\"ru_RU\":\"\"}', '14', NULL, '1', NULL, '2025-01-13 07:01:56', '1', '2025-01-13 07:01:56');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633304347041861', NULL, '632275846434885', '{\"zh_CN\":\"15车位\",\"en_US\":null,\"ru_RU\":\"\"}', '15', NULL, '1', NULL, '2025-01-13 07:02:01', '1', '2025-01-13 07:02:01');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633304369848389', NULL, '632275846434885', '{\"zh_CN\":\"16车位\",\"en_US\":null,\"ru_RU\":\"\"}', '16', NULL, '1', NULL, '2025-01-13 07:02:07', '1', '2025-01-13 07:02:07');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633966004551749', NULL, '632275846434885', '{\"zh_CN\":\"17车位\",\"en_US\":null,\"ru_RU\":\"\"}', '17', NULL, '1', NULL, '2025-01-15 03:54:19', '1', '2025-01-15 03:54:19');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633966040547397', NULL, '632275846434885', '{\"zh_CN\":\"18车位\",\"en_US\":null,\"ru_RU\":\"\"}', '18', NULL, '1', NULL, '2025-01-15 03:54:27', '1', '2025-01-15 03:54:27');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643220999901253', NULL, '594065961017413', '{\"zh_CN\":\"01车位\",\"en_US\":null,\"ru_RU\":\"\"}', '01', NULL, '1', NULL, '2025-02-10 07:32:59', '1', '2025-02-10 07:32:59');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643221038501957', NULL, '594065961017413', '{\"zh_CN\":\"02车位\",\"en_US\":null,\"ru_RU\":\"\"}', '02', NULL, '1', NULL, '2025-02-10 07:33:08', '1', '2025-02-10 07:33:08');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643221073670213', NULL, '594065961017413', '{\"zh_CN\":\"03车位\",\"en_US\":null,\"ru_RU\":\"\"}', '03', NULL, '1', NULL, '2025-02-10 07:33:17', '1', '2025-02-10 07:33:17');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643221098655813', NULL, '594065961017413', '{\"zh_CN\":\"04车位\",\"en_US\":null,\"ru_RU\":\"\"}', '04', NULL, '1', NULL, '2025-02-10 07:33:23', '1', '2025-02-10 07:33:23');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643221129187397', NULL, '594065961017413', '{\"zh_CN\":\"05车位\",\"en_US\":null,\"ru_RU\":\"\"}', '05', NULL, '1', NULL, '2025-02-10 07:33:30', '1', '2025-02-10 07:33:30');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643221155786821', NULL, '594065961017413', '{\"zh_CN\":\"06车位\",\"en_US\":null,\"ru_RU\":\"\"}', '06', NULL, '1', NULL, '2025-02-10 07:33:37', '1', '2025-02-10 07:33:37');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643221180158021', NULL, '594065961017413', '{\"zh_CN\":\"07车位\",\"en_US\":null,\"ru_RU\":\"\"}', '07', NULL, '1', NULL, '2025-02-10 07:33:43', '1', '2025-02-10 07:33:43');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643221207101509', NULL, '594065961017413', '{\"zh_CN\":\"08车位\",\"en_US\":null,\"ru_RU\":\"\"}', '08', NULL, '1', NULL, '2025-02-10 07:33:50', '1', '2025-02-10 07:33:50');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643221249716293', NULL, '594065961017413', '{\"zh_CN\":\"09车位\",\"en_US\":null,\"ru_RU\":\"\"}', '09', NULL, '1', NULL, '2025-02-10 07:34:00', '1', '2025-02-10 07:34:00');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643221277282373', NULL, '594065961017413', '{\"zh_CN\":\"10车位\",\"en_US\":null,\"ru_RU\":\"\"}', '10', NULL, '1', NULL, '2025-02-10 07:34:07', '1', '2025-02-10 07:34:07');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643222095056965', NULL, '594065961017413', '{\"zh_CN\":\"测试车位\",\"en_US\":null,\"ru_RU\":\"\"}', 'test', NULL, '1', NULL, '2025-02-10 07:37:26', '1', '2025-02-10 07:37:26');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643288816558149', NULL, '617007724576837', '{\"zh_CN\":\"01车位\",\"en_US\":null,\"ru_RU\":\"\"}', '01', NULL, '1', NULL, '2025-02-10 12:08:56', '1', '2025-02-10 12:08:56');
INSERT INTO `parking` (`id`, `parking_id`, `station_id`, `name`, `no`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('643288850960453', NULL, '617007724576837', '{\"zh_CN\":\"02车位\",\"en_US\":null,\"ru_RU\":\"\"}', '02', NULL, '1', NULL, '2025-02-10 12:09:04', '1', '2025-02-10 12:09:04');
COMMIT;

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付名称',
  `identifier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `status` int NOT NULL COMMENT '1启用/0停用',
  `is_default` int DEFAULT NULL,
  `img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `properties` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '描述',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `identifier` (`identifier`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of payment
-- ----------------------------
BEGIN;
INSERT INTO `payment` (`id`, `name`, `identifier`, `description`, `no`, `status`, `is_default`, `img`, `properties`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('586157946454081', '{\"zh_CN\":\"微信支付\",\"en_US\":\"Test\",\"ru_RU\":\"test\"}', 'Wechat', '{\"zh_CN\":\"推荐的支付方式\",\"en_US\":\"Test\",\"ru_RU\":\"test\"}', '1', 1, 1, NULL, '{\"appId\":\"wx11111111111\",\"merchantId\":\"1111111111111\",\"merchantSerialNumber\":\"111111111111111111111111111\",\"apiV3Key\":\"test\",\"privateKey\":\"-----BEGIN PRIVATE KEY-----\\ntest\\n-----END PRIVATE KEY-----\"}', '2222', '1', NULL, '2024-09-02 01:42:49', '1', '2025-03-30 08:54:35');
COMMIT;

-- ----------------------------
-- Table structure for plugin_info
-- ----------------------------
DROP TABLE IF EXISTS `plugin_info`;
CREATE TABLE `plugin_info` (
  `id` bigint NOT NULL COMMENT 'id',
  `plugin_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '插件包id',
  `protocol_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '协议key',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '插件类型',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '插件名称',
  `protocol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备插件协议类型',
  `config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '插件配置信息',
  `config_schema` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '插件配置项描述信息',
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '插件版本',
  `file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '插件包地址',
  `script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '插件脚本',
  `deploy_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部署方式',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of plugin_info
-- ----------------------------
BEGIN;
INSERT INTO `plugin_info` (`id`, `plugin_id`, `protocol_key`, `type`, `name`, `protocol`, `config`, `config_schema`, `version`, `file`, `script`, `deploy_type`, `description`, `state`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2, 'ykc-tcp-plugin', 'ykc-tcp', NULL, '云快充', NULL, '{\"host\": \"0.0.0.0\", \"port\": \"6166\"}', '[{\"id\":\"host\",\"name\":\"绑定ip\",\"type\":\"text\",\"value\":\"0.0.0.0\",\"desc\":\"tcp绑定ip，默认为0.0.0.0\"},{\"id\":\"port\",\"name\":\"端口\",\"type\":\"number\",\"value\":6767,\"desc\":\"tcp端口，默认为6166\"}]', '1.0.4', 'ykc-tcp-plugin-1.0.4-repackage.jar', NULL, 'upload', '云快充插件', 'running', 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-07 16:41:07');
COMMIT;

-- ----------------------------
-- Table structure for plugin_instance
-- ----------------------------
DROP TABLE IF EXISTS `plugin_instance`;
CREATE TABLE `plugin_instance` (
  `id` bigint NOT NULL COMMENT 'id',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '插件主程序所在ip',
  `main_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '插件主程序id',
  `plugin_id` bigint DEFAULT NULL COMMENT '插件id',
  `port` int NOT NULL COMMENT '插件主程序端口',
  `heartbeat_at` bigint DEFAULT NULL COMMENT '心跳时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of plugin_instance
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for price
-- ----------------------------
DROP TABLE IF EXISTS `price`;
CREATE TABLE `price` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编号',
  `type` int DEFAULT NULL COMMENT '计费模式1标准,2分时',
  `status` int DEFAULT NULL COMMENT '1启用/0停用',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `properties` json DEFAULT NULL COMMENT '分时电价',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `no` (`no`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of price
-- ----------------------------
BEGIN;
INSERT INTO `price` (`id`, `name`, `no`, `type`, `status`, `note`, `properties`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('586997419884613', '默认计价规则', '1', 2, 1, '', '{\"fee\": [{\"name\": \"尖\", \"amount\": \"1.6145\", \"elecFee\": \"1.1145\", \"serviceFee\": \"0.5000\"}, {\"name\": \"峰\", \"amount\": \"1.6145\", \"elecFee\": \"1.1145\", \"serviceFee\": \"0.5000\"}, {\"name\": \"平\", \"amount\": \"1.2701\", \"elecFee\": \"0.7701\", \"serviceFee\": \"0.5000\"}, {\"name\": \"谷\", \"amount\": \"0.9257\", \"elecFee\": \"0.4257\", \"serviceFee\": \"0.5000\"}], \"period\": [{\"index\": 3, \"label\": \"00:00-00:30\"}, {\"index\": 3, \"label\": \"00:30-01:00\"}, {\"index\": 3, \"label\": \"01:00-01:30\"}, {\"index\": 3, \"label\": \"01:30-02:00\"}, {\"index\": 3, \"label\": \"02:00-02:30\"}, {\"index\": 3, \"label\": \"02:30-03:00\"}, {\"index\": 3, \"label\": \"03:00-03:30\"}, {\"index\": 3, \"label\": \"03:30-04:00\"}, {\"index\": 3, \"label\": \"04:00-04:30\"}, {\"index\": 3, \"label\": \"04:30-05:00\"}, {\"index\": 3, \"label\": \"05:00-05:30\"}, {\"index\": 3, \"label\": \"05:30-06:00\"}, {\"index\": 3, \"label\": \"06:00-06:30\"}, {\"index\": 3, \"label\": \"06:30-07:00\"}, {\"index\": 2, \"label\": \"07:00-07:30\"}, {\"index\": 2, \"label\": \"07:30-08:00\"}, {\"index\": 0, \"label\": \"08:00-08:30\"}, {\"index\": 0, \"label\": \"08:30-09:00\"}, {\"index\": 0, \"label\": \"09:00-09:30\"}, {\"index\": 0, \"label\": \"09:30-10:00\"}, {\"index\": 0, \"label\": \"10:00-10:30\"}, {\"index\": 0, \"label\": \"10:30-11:00\"}, {\"index\": 0, \"label\": \"11:00-11:30\"}, {\"index\": 2, \"label\": \"11:30-12:00\"}, {\"index\": 2, \"label\": \"12:00-12:30\"}, {\"index\": 2, \"label\": \"12:30-13:00\"}, {\"index\": 2, \"label\": \"13:00-13:30\"}, {\"index\": 2, \"label\": \"13:30-14:00\"}, {\"index\": 2, \"label\": \"14:00-14:30\"}, {\"index\": 2, \"label\": \"14:30-15:00\"}, {\"index\": 2, \"label\": \"15:00-15:30\"}, {\"index\": 2, \"label\": \"15:30-16:00\"}, {\"index\": 2, \"label\": \"16:00-16:30\"}, {\"index\": 2, \"label\": \"16:30-17:00\"}, {\"index\": 2, \"label\": \"17:00-17:30\"}, {\"index\": 2, \"label\": \"17:30-18:00\"}, {\"index\": 2, \"label\": \"18:00-18:30\"}, {\"index\": 1, \"label\": \"18:30-19:00\"}, {\"index\": 1, \"label\": \"19:00-19:30\"}, {\"index\": 1, \"label\": \"19:30-20:00\"}, {\"index\": 1, \"label\": \"20:00-20:30\"}, {\"index\": 1, \"label\": \"20:30-21:00\"}, {\"index\": 1, \"label\": \"21:00-21:30\"}, {\"index\": 1, \"label\": \"21:30-22:00\"}, {\"index\": 1, \"label\": \"22:00-22:30\"}, {\"index\": 1, \"label\": \"22:30-23:00\"}, {\"index\": 3, \"label\": \"23:00-23:30\"}, {\"index\": 3, \"label\": \"23:30-24:00\"}]}', '1', NULL, '2024-09-04 10:38:39', '1', '2024-11-21 07:01:04');
COMMIT;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品名称',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品类型,1充电桩',
  `product_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品key',
  `product_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品密钥',
  `protocol_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '协议key',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '品类',
  `manufacturer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生产商',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片',
  `keep_alive_time` bigint DEFAULT NULL COMMENT '保活时长（秒）',
  `properties` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `status` int DEFAULT NULL,
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `product_key` (`product_key`) USING BTREE,
  KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of product
-- ----------------------------
BEGIN;
INSERT INTO `product` (`id`, `name`, `type`, `product_key`, `product_secret`, `protocol_key`, `category`, `manufacturer`, `img`, `keep_alive_time`, `properties`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('516578994860103', '交流-单枪(7KW)', 'Charger', '03', 'ff009f4d2b57401e888b26cf064ddff4', 'ykc', 'charger', '道尔', NULL, 30, '{\"qty\":1,\"speed\":\"Slow\",\"current\":\"AC\",\"power\":7,\"protocol\":\"ykc\",\"lowBalance\":2.00}', 1, '1', NULL, '2024-11-06 07:57:59', '1', '2024-11-06 07:57:59');
INSERT INTO `product` (`id`, `name`, `type`, `product_key`, `product_secret`, `protocol_key`, `category`, `manufacturer`, `img`, `keep_alive_time`, `properties`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('516578994860104', '交流-双枪(7KW)', 'Charger', '04', '699373445d93449486644rgedgfdas', 'ykc', 'charger', '道尔', NULL, 30, '{\"qty\":2,\"speed\":\"Slow\",\"current\":\"AC\",\"power\":7,\"protocol\":\"ykc\",\"lowBalance\":2.00}', 1, '1', NULL, '2024-11-06 07:57:35', '1', '2024-11-06 07:57:35');
INSERT INTO `product` (`id`, `name`, `type`, `product_key`, `product_secret`, `protocol_key`, `category`, `manufacturer`, `img`, `keep_alive_time`, `properties`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('516578994860106', '直流-单枪(30KW)', 'Charger', '05', '699373445d93449487a065addba24333', 'ykc', 'charger', '道尔', NULL, 30, '{\"qty\":1,\"speed\":\"Quick\",\"current\":\"DC\",\"power\":30,\"protocol\":\"ykc\",\"lowBalance\":5.00}', 1, '1', NULL, '2024-11-06 07:57:35', '1', '2024-11-06 07:57:35');
COMMIT;

-- ----------------------------
-- Table structure for promotion
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `scope` int DEFAULT NULL COMMENT '适用场站',
  `type` int DEFAULT NULL COMMENT '活动类型',
  `station_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '场站ids',
  `properties` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '属性',
  `start_time` datetime DEFAULT NULL COMMENT '启用时间',
  `end_time` datetime DEFAULT NULL COMMENT '过期时间',
  `status` int DEFAULT NULL,
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '描述',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `scope` (`scope`) USING BTREE,
  KEY `apply` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值方案';

-- ----------------------------
-- Records of promotion
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for protocol
-- ----------------------------
DROP TABLE IF EXISTS `protocol`;
CREATE TABLE `protocol` (
  `id` bigint NOT NULL COMMENT 'id',
  `protocol_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '协议key',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品类型,1充电桩',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '协议名称',
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版本',
  `manufacturer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生产商',
  `config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '配置信息',
  `status` int DEFAULT NULL COMMENT '状态',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `protocol_key` (`protocol_key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of protocol
-- ----------------------------
BEGIN;
INSERT INTO `protocol` (`id`, `protocol_key`, `type`, `name`, `version`, `manufacturer`, `config`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2, 'ykc', 'Charger', '云快充', '1.0.1', NULL, '{\"dnLength\": 14}', 1, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-07 16:41:07');
INSERT INTO `protocol` (`id`, `protocol_key`, `type`, `name`, `version`, `manufacturer`, `config`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (3, 'vzi', 'Dcam', '臻识mqtt', '1.0.1', NULL, NULL, 1, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-06 16:06:52');
COMMIT;

-- ----------------------------
-- Table structure for push_config
-- ----------------------------
DROP TABLE IF EXISTS `push_config`;
CREATE TABLE `push_config` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推送名称',
  `identifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '识别符',
  `properties` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '属性',
  `status` int DEFAULT NULL COMMENT '状态',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of push_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for recharge
-- ----------------------------
DROP TABLE IF EXISTS `recharge`;
CREATE TABLE `recharge` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `type` int DEFAULT NULL COMMENT '类型',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '描述',
  `status` int DEFAULT NULL COMMENT '启用/停用',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值方案';

-- ----------------------------
-- Records of recharge
-- ----------------------------
BEGIN;
INSERT INTO `recharge` (`id`, `type`, `name`, `note`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('594715916664901', 4, '充值', '提示: 充值成功后系统将赠送您充值金额所对应的服务费打折额度，服务费打折额度在您的订单结算后自动扣除。服务费打折额度扣除以订单总金额为基础扣除。例如: 充值500元赠送服务费打折额度500元(5折)。充电完成后订单总金额为100元(其中服务费为40元)，将从账户中扣除100元的服务费打折额度，订单总优惠金额为20元（40元x5折=20元）。', 1, '1', NULL, '2024-09-26 06:05:18', '1', '2025-03-13 14:20:10', NULL);
COMMIT;

-- ----------------------------
-- Table structure for recharge_item
-- ----------------------------
DROP TABLE IF EXISTS `recharge_item`;
CREATE TABLE `recharge_item` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `recharge_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '描述',
  `give` decimal(10,2) DEFAULT NULL COMMENT '赠送金额',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '折扣',
  `minus` decimal(10,2) DEFAULT NULL COMMENT '满减金额',
  `status` int DEFAULT NULL COMMENT '状态',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `recharge_id` (`recharge_id`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值方案';

-- ----------------------------
-- Records of recharge_item
-- ----------------------------
BEGIN;
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613292156031045', '594715916664901', NULL, 50.00, NULL, NULL, 1.00, NULL, NULL, '1', NULL, '2024-11-17 17:52:12', '1', '2024-12-04 16:08:19');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613292175417413', '594715916664901', NULL, 100.00, NULL, NULL, 0.90, NULL, NULL, '1', NULL, '2024-11-17 17:52:17', '1', '2024-11-27 12:11:55');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613292197429317', '594715916664901', NULL, 200.00, NULL, NULL, 0.85, NULL, NULL, '1', NULL, '2024-11-17 17:52:22', '654124202426437', '2025-03-14 00:55:40');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613292219453509', '594715916664901', NULL, 300.00, NULL, NULL, 0.75, NULL, NULL, '1', NULL, '2024-11-17 17:52:28', '654124202426437', '2025-03-14 00:55:58');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613292239388741', '594715916664901', NULL, 500.00, NULL, NULL, 0.65, NULL, NULL, '1', NULL, '2024-11-17 17:52:33', '654124202426437', '2025-03-14 00:56:14');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('630755621290053', '623472357883973', 'ddd', NULL, '2222', NULL, NULL, NULL, NULL, '1', NULL, '2025-01-06 02:11:14', '1', '2025-01-06 02:11:14');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('654447411122245', '594715916664901', NULL, 1000.00, NULL, NULL, 0.40, NULL, NULL, '654124202426437', NULL, '2025-03-14 00:53:22', '654124202426437', '2025-03-14 00:53:53');
COMMIT;

-- ----------------------------
-- Table structure for refund
-- ----------------------------
DROP TABLE IF EXISTS `refund`;
CREATE TABLE `refund` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `tran_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流水号',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `state` int DEFAULT NULL COMMENT '启用/停用',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值方案';

-- ----------------------------
-- Records of refund
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for refund_balance
-- ----------------------------
DROP TABLE IF EXISTS `refund_balance`;
CREATE TABLE `refund_balance` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `refund_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `topup_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tran_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `state` int DEFAULT NULL,
  `pay_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `success_time` datetime DEFAULT NULL,
  `user_received_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of refund_balance
-- ----------------------------
BEGIN;
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236823089', '167655236806339', NULL, '167655236832183', 30.00, 10, '17194748095197930437', '1970-01-01 00:00:00', NULL, NULL, '1', NULL, '1970-01-01 00:00:00', NULL, '1970-01-01 00:00:00');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236863538', '167655236841649', NULL, '167655236872600', 10.00, 10, '17194748449357681584', '1970-01-01 00:00:00', NULL, NULL, '1', NULL, '1970-01-01 00:00:00', NULL, '1970-01-01 00:00:00');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236903485', '167655236882385', NULL, '167655236915042', 1.00, 10, '17194750416428675305', '1970-01-01 00:00:00', NULL, NULL, '1', NULL, '1970-01-01 00:00:00', NULL, '1970-01-01 00:00:00');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236948990', '167655236924755', NULL, '167655236958995', 300.00, 10, '17241444828723282921', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236981239', '167655236968007', NULL, '167655236997037', 50.00, 10, '17265457964208266261', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237026813', '167655237001471', NULL, '167655237035328', 50.00, 10, '17265459136088779414', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237066526', '167655237046202', NULL, '167655237077553', 50.00, 10, '17341443979631714592', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237106791', '167655237088185', NULL, '167655237119147', 200.00, 10, '17341448714895634479', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('16765523714784', '167655237125363', NULL, '167655237155795', 100.00, 10, '17341719651577013508', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237188823', '167655237166625', NULL, '167655237196898', 100.00, 10, '17342543069609878894', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237223037', '167655237208023', NULL, '167655237236920', 50.00, 10, '17342548607308814588', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237266979', '167655237245490', NULL, '167655237274828', 300.00, 10, '17344334922351392173', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237308006', '167655237283201', NULL, '167655237315466', 550.00, 10, '17346803334390227576', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('16765523734900', '167655237323313', NULL, '167655237353997', 100.00, 10, '17378151918838441529', '1970-01-01 00:33:45', NULL, NULL, '1', NULL, '1970-01-01 00:33:45', NULL, '1970-01-01 00:33:45');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655238882699', '167655238867937', NULL, '167655238893590', 300.00, 10, '17241507294025335348', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655244887991', '167655244861168', NULL, '167655244895061', 100.00, 10, '17197112459096171933', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250621960', '167655250603056', NULL, '167655250635123', 5000.00, 10, '17236288601993779081', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250667397', '167655250649738', NULL, '167655250677020', 1400.00, 10, '17290798841610893516', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250708726', '167655250682910', NULL, '167655250713158', 600.00, 10, '17315869614981611887', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250744068', '167655250729611', NULL, '167655250754601', 600.00, 10, '17315869627231821068', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655251288294', '167655251261484', NULL, '167655251295822', 49.14, 10, '17208550168535059209', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655251809272', '167655251788797', NULL, '167655251818962', 15.97, 10, '17220666503595617531', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655253768334', '1676552537421', NULL, '167655253774631', 1000.00, 10, '17235474512776737824', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655254121437', '167655254102012', NULL, '16765525413704', 13.92, 10, '17327645489705557830', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655254226219', '167655254202058', NULL, '167655254235659', 49.94, 10, '17264829736041697731', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655256002575', '167655255982412', NULL, '167655256016987', 43.17, 10, '17256088687382876056', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655256506318', '167655256482002', NULL, '167655256514197', 35.00, 10, '17265463344112563202', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655256585077', '167655256561756', NULL, '167655256597180', 48.14, 10, '17263994926653118167', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655257503121', '16765525748517', NULL, '167655257516801', 33.00, 10, '17323280612141340469', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258268987', '167655258244716', NULL, '167655258275053', 50.00, 10, '17279377121685190145', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258384837', '167655258367769', NULL, '167655258399298', 50.00, 10, '17279365859444038400', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258424119', '167655258401981', NULL, '167655258434557', 26.36, 10, '17279366035914870320', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258526430', '167655258501067', NULL, '167655258532372', 46.57, 10, '17300886143840452079', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258606037', '167655258581010', NULL, '167655258616745', 28.94, 10, '17300888129651445766', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655259041911', '16765525902843', NULL, '167655259052892', 50.00, 10, '17284689355721255684', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655259566944', '167655259549491', NULL, '167655259579469', 50.00, 10, '17289780172542562195', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655261104335', '167655261084681', NULL, '167655261116650', 47.00, 10, '17300888768912716261', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655263765072', '16765526374172', NULL, '167655263774008', 50.00, 10, '17343110137999796444', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655264041317', '167655264025841', NULL, '167655264051283', 40.00, 10, '17350397605153856081', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655264564568', '167655264547061', NULL, '167655264577389', 36.78, 10, '17353506052011381263', '1970-01-01 00:33:44', NULL, NULL, '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655267442302', '167655267426448', NULL, '167655267456524', 50.00, 10, '17400226776907470660', '1970-01-01 00:33:45', NULL, NULL, '1', NULL, '1970-01-01 00:33:45', NULL, '1970-01-01 00:33:45');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('650780255182917', '650780253065285', '650626292383813', '650780255166533', 0.72, 10, '50301702302025030479477026619', '2025-03-03 16:12:15', '支付用户零钱', NULL, '1', NULL, '2025-03-03 16:11:40', '1', '2025-03-03 16:11:40');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651025098235973', '651025096155205', '651024760651845', '651025098227781', 0.01, 10, '50302202702025030430483952588', '2025-03-04 08:48:19', '支付用户零钱', NULL, '1', NULL, '2025-03-04 08:47:56', '1', '2025-03-04 08:48:19');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651047743897669', '651047741689925', '651029544775749', '651047743860805', 0.01, 10, '50302602602025030425069600024', '2025-03-04 10:24:01', '支付用户零钱', NULL, '1', NULL, '2025-03-04 10:20:05', '1', '2025-03-04 10:24:02');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651052941529157', '651052939247685', '651052788150341', '651052941467717', 0.01, 10, '50300302572025030422489607929', '2025-03-04 10:41:28', '支付用户零钱', NULL, '1', NULL, '2025-03-04 10:41:14', '1', '2025-03-04 10:41:28');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651059186876485', '651059184672837', '651053160251461', '651059186810949', 0.03, 10, '50302402862025030431086289501', '2025-03-04 11:07:01', '工商银行借记卡9829', NULL, '1', NULL, '2025-03-04 11:06:39', '1', '2025-03-04 11:07:01');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651059624902725', '651059622826053', '651053160251461', '651059624890437', 0.02, 10, '50302402862025030400997861161', '2025-03-04 11:08:40', '工商银行借记卡9829', NULL, '1', NULL, '2025-03-04 11:08:26', '1', '2025-03-04 11:08:40');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651611716771909', '651611714555973', '651059986182213', '651611716706373', 0.01, 10, '50301402852025030648264855560', '2025-03-06 00:35:28', '工商银行借记卡9829', NULL, '1', NULL, '2025-03-06 00:34:54', '1', '2025-03-06 00:35:28');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('654258829516869', '654258827157573', '654258527670341', '654258829406277', 0.01, 10, '50300802642025031306253989569', '2025-03-13 12:06:28', '支付用户零钱', NULL, '1', NULL, '2025-03-13 12:06:02', '1', '2025-03-13 12:06:28');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('655007898382405', '655007896281157', '650626292383813', '655007898370117', 33.00, 10, '50301702302025031566512462487', '2025-03-15 14:55:25', '支付用户零钱', NULL, '1', NULL, '2025-03-15 14:54:00', '1', '2025-03-15 14:55:26');
COMMIT;

-- ----------------------------
-- Table structure for sms_config
-- ----------------------------
DROP TABLE IF EXISTS `sms_config`;
CREATE TABLE `sms_config` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `identifier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '识别符',
  `tpl_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `properties` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '配置参数',
  `status` int DEFAULT NULL,
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sms_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sms_record
-- ----------------------------
DROP TABLE IF EXISTS `sms_record`;
CREATE TABLE `sms_record` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `identifier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '短信服务提供商',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '短信消息',
  `result` int DEFAULT NULL COMMENT '发送结果',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sms_record
-- ----------------------------
BEGIN;
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('634479315968069', 'Nikita', 'Login', '18992720878', '{\"code\":7614}', 1, 1, NULL, '2025-01-16 14:42:59', 1, '2025-01-16 14:43:00', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('634834466828357', 'Nikita', 'Login', '18992720878', '{\"code\":9452}', 1, 1, NULL, '2025-01-17 14:48:06', 1, '2025-01-17 14:48:09', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('634836570771525', 'Nikita', 'Login', '18992720878', '{\"code\":1751}', 1, 1, NULL, '2025-01-17 14:56:39', 1, '2025-01-17 14:56:40', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644027953406021', 'Nikita', 'Login', '18992720878', '{\"code\":8533}', 1, 1, NULL, '2025-02-12 14:16:29', 1, '2025-02-12 14:16:34', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644027993444421', 'Nikita', 'Login', '18992720878', '{\"code\":3232}', 1, 1, NULL, '2025-02-12 14:16:39', 1, '2025-02-12 14:16:40', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644028695674949', 'Nikita', 'Login', '18992720878', '{\"code\":6959}', 1, 1, NULL, '2025-02-12 14:19:30', 1, '2025-02-12 14:19:31', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644028752891973', 'Nikita', 'Login', '18992720878', '{\"code\":8282}', 1, 1, NULL, '2025-02-12 14:19:44', 1, '2025-02-12 14:19:45', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644031082729541', 'Nikita', 'Login', '18992720878', '{\"code\":5431}', 1, 1, NULL, '2025-02-12 14:29:13', 1, '2025-02-12 14:29:14', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644033050878021', 'Nikita', 'Login', '18992720878', '{\"code\":1826}', 1, 1, NULL, '2025-02-12 14:37:14', 1, '2025-02-12 14:37:15', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644033971408965', 'Nikita', 'Login', '18992720878', '{\"code\":4712}', 1, 1, NULL, '2025-02-12 14:40:58', 1, '2025-02-12 14:40:59', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644035298119749', 'Nikita', 'Login', '18992720878', '{\"code\":9873}', 1, 1, NULL, '2025-02-12 14:46:22', 1, '2025-02-12 14:46:23', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644036295913541', 'Nikita', 'Login', 'undefined', '{\"code\":4454}', 1, 1, NULL, '2025-02-12 14:50:26', 1, '2025-02-12 14:50:27', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644036885385285', 'Nikita', 'Login', 'undefined', '{\"code\":2321}', 1, 1, NULL, '2025-02-12 14:52:50', 1, '2025-02-12 14:52:51', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644037701238853', 'Nikita', 'Login', '18992720878', '{\"code\":4740}', 1, 1, NULL, '2025-02-12 14:56:09', 1, '2025-02-12 14:56:10', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644038604202053', 'Nikita', 'Login', '18992720878', '{\"code\":7441}', 1, 1, NULL, '2025-02-12 14:59:49', 1, '2025-02-12 14:59:50', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644038700572741', 'Nikita', 'Login', '18992720878', '{\"code\":6741}', 1, 1, NULL, '2025-02-12 15:00:13', 1, '2025-02-12 15:00:14', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644054629621829', 'Nikita', 'Login', '18992720878', '{\"code\":9033}', 1, 1, NULL, '2025-02-12 16:05:02', 1, '2025-02-12 16:05:03', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644054756032581', 'Nikita', 'Login', '18992720878', '{\"code\":5304}', 1, 1, NULL, '2025-02-12 16:05:33', 1, '2025-02-12 16:05:34', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644058089873477', 'Nikita', 'Login', '18992720878', '{\"code\":1628}', 1, 1, NULL, '2025-02-12 16:19:07', 1, '2025-02-12 16:19:07', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644058171162693', 'Nikita', 'Login', '18992720878', '{\"code\":4353}', 1, 1, NULL, '2025-02-12 16:19:26', 1, '2025-02-12 16:19:27', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644179327189061', 'Nikita', 'Login', '18992720878', '{\"code\":5542}', 1, 1, NULL, '2025-02-13 00:32:26', 1, '2025-02-13 00:32:27', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644179498717253', 'Nikita', 'Login', '18992720878', '{\"code\":8722}', 1, 1, NULL, '2025-02-13 00:33:07', 1, '2025-02-13 00:33:08', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644255828611141', 'Nikita', 'Login', '18992720878', '{\"code\":2087}', 1, 1, NULL, '2025-02-13 05:43:43', 1, '2025-02-13 05:43:44', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644255921877061', 'Nikita', 'Login', '18992720878', '{\"code\":9411}', 1, 1, NULL, '2025-02-13 05:44:05', 1, '2025-02-13 05:44:06', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644268727697477', 'Nikita', 'Login', '18992720878', '{\"code\":1889}', 1, 1, NULL, '2025-02-13 06:36:12', 1, '2025-02-13 06:36:13', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('644268836507717', 'Nikita', 'Login', '18992720878', '{\"code\":4136}', 1, 1, NULL, '2025-02-13 06:36:38', 1, '2025-02-13 06:36:39', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('645734938349637', 'Nikita', 'Login', '18992720878', '{\"code\":4755}', 1, 1, NULL, '2025-02-17 10:02:13', 1, '2025-02-17 10:02:14', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('645735011074117', 'Nikita', 'Login', '18992720878', '{\"code\":5206}', 1, 1, NULL, '2025-02-17 10:02:31', 1, '2025-02-17 10:02:32', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('645741774090309', 'Nikita', 'Login', '18992720878', '{\"code\":\"7757\"}', 1, 1, NULL, '2025-02-17 10:30:02', 1, '2025-02-17 10:30:04', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('645743734194245', 'Nikita', 'Login', '18992720878', '{\"code\":\"2670\"}', 1, 1, NULL, '2025-02-17 10:38:01', 1, '2025-02-17 10:38:02', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('645777549500485', 'Nikita', 'Login', '18992720878', '{\"code\":\"9401\"}', 1, 1, NULL, '2025-02-17 12:55:37', 1, '2025-02-17 12:55:38', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646296088428613', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 00:05:33', 1, '2025-02-19 00:05:34', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646297234722885', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 00:10:13', 1, '2025-02-19 00:10:13', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646334349963333', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 02:41:14', 1, '2025-02-19 02:41:16', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646334588796997', 'Nikita', 'Login', '18992720878', '{\"code\":\"6714\"}', 1, 1, NULL, '2025-02-19 02:42:12', 1, '2025-02-19 02:42:13', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646335148998725', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 02:44:29', 1, '2025-02-19 02:44:30', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646409788305477', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 07:48:12', 1, '2025-02-19 07:48:13', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646413526483013', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 08:03:24', 1, '2025-02-19 08:03:25', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646413804675141', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 08:04:32', 1, '2025-02-19 08:04:33', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646419901177925', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 08:29:21', 1, '2025-02-19 08:29:23', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646421688832069', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 08:36:37', 1, '2025-02-19 08:36:38', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646423897878597', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 08:45:36', 1, '2025-02-19 08:45:37', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646424185856069', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 08:46:47', 1, '2025-02-19 08:46:47', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646424568152133', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 08:48:20', 1, '2025-02-19 08:48:21', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646425483817029', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 08:52:04', 1, '2025-02-19 08:52:04', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646427352404037', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 08:59:40', 1, '2025-02-19 08:59:41', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646428396642373', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 09:03:55', 1, '2025-02-19 09:03:56', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('646429445378117', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-19 09:08:11', 1, '2025-02-19 09:08:12', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648201305509957', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-24 09:17:54', 1, '2025-02-24 09:17:56', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648210560970821', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-24 09:55:33', 1, '2025-02-24 09:55:35', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648225973997637', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-24 10:58:16', 1, '2025-02-24 10:58:18', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648226878013509', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-24 11:01:57', 1, '2025-02-24 11:01:59', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648294299271237', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-24 15:36:17', 1, '2025-02-24 15:36:19', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648295218876485', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-24 15:40:02', 1, '2025-02-24 15:40:03', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648457728004165', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-25 02:41:17', 1, '2025-02-25 02:41:19', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648527990313029', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-25 07:27:11', 1, '2025-02-25 07:27:12', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648529305694277', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-25 07:32:32', 1, '2025-02-25 07:32:33', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648530604232773', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-25 07:37:49', 1, '2025-02-25 07:37:50', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648532327829573', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-25 07:44:50', 1, '2025-02-25 07:44:51', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648533879746629', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-25 07:51:09', 1, '2025-02-25 07:51:09', NULL);
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `tenant_id`) VALUES ('648573835964485', 'Nikita', 'Notify', '18992720878', NULL, 1, 1, NULL, '2025-02-25 10:33:44', 1, '2025-02-25 10:33:46', NULL);
COMMIT;

-- ----------------------------
-- Table structure for station
-- ----------------------------
DROP TABLE IF EXISTS `station`;
CREATE TABLE `station` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备名称',
  `latitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '纬度',
  `longitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '经度',
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '地址',
  `status` int NOT NULL COMMENT '状态 启用1, 停用0',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '图片',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `tags` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '标签',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of station
-- ----------------------------
BEGIN;
INSERT INTO `station` (`id`, `name`, `latitude`, `longitude`, `address`, `status`, `description`, `img`, `note`, `tags`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651348387233861', '{\"zh_CN\":\"云途测试充电站\",\"en_US\":\"\",\"ru_RU\":\"\"}', '34.363799', '107.165657', '{\"zh_CN\":\"中国西安长安南路58号\",\"en_US\":\"\",\"ru_RU\":\"\"}', 1, '{\"zh_CN\":\"云途测试充电站介绍\",\"en_US\":\"\",\"ru_RU\":\"\"}', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_06_7aec32a384c1444db3e37021102a30bc.jpeg', NULL, '{\"zh_CN\":\"\",\"en_US\":\"\",\"ru_RU\":\"\"}', '1', NULL, '2025-03-05 06:43:24', '1', '2025-03-30 08:52:29');
COMMIT;

-- ----------------------------
-- Table structure for sys_app
-- ----------------------------
DROP TABLE IF EXISTS `sys_app`;
CREATE TABLE `sys_app` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键id',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'appId',
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用名称',
  `app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'appSecret',
  `app_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用类型',
  `status` int DEFAULT NULL COMMENT '状态',
  `config` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_app
-- ----------------------------
BEGIN;
INSERT INTO `sys_app` (`id`, `app_id`, `app_name`, `app_secret`, `app_type`, `status`, `config`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('600133534576709', '1111111111', 'test', '111111', 'Wechat', 1, '{\"appId\":\"wx7fd1602b884b4f31\",\"appSecret\":\"7269bdb79b5691afd6e9b8dbeb401d55\"}', NULL, '1', NULL, '2024-10-11 13:29:38', '1', '2025-03-06 07:36:56');
COMMIT;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint NOT NULL COMMENT '参数主键',
  `config_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参数键名',
  `config_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参数名称',
  `config_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '系统内置（Y是 N否）',
  `config_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '参数键值',
  `data_type` int DEFAULT NULL COMMENT '数据类型',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` (`id`, `config_key`, `config_name`, `config_type`, `config_value`, `data_type`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (585177022468146, 'app.user.privacy', '移动端-隐私协议', 'Y', '{\"zh_CN\":\"<h1 class=\\\"ql-align-center\\\">小程序隐私保护说明</h1><p><br></p><h2><br></h2>\",\"en_US\":\"\",\"ru_RU\":\"\"}', 7, NULL, 1, NULL, '2024-08-30 07:11:26', 1, '2025-03-31 13:38:01');
INSERT INTO `sys_config` (`id`, `config_key`, `config_name`, `config_type`, `config_value`, `data_type`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (585177022468166, 'app.user.agreement', '移动端-用户协议', 'Y', '{\"zh_CN\":\"<h2><span style=\\\"color: rgb(48, 49, 51);\\\">用户协议</span></h2><p><br></p>\",\"en_US\":\"\",\"ru_RU\":\"\"}', 7, '', 1, NULL, '2024-08-30 07:11:26', 1, '2025-03-31 13:38:17');
INSERT INTO `sys_config` (`id`, `config_key`, `config_name`, `config_type`, `config_value`, `data_type`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (585177022468167, 'app.charge.balance.limit', '用户充电最低余额', 'Y', '10', 5, '', 1, NULL, '2024-08-30 07:11:26', 1, '2024-11-28 16:55:23');
INSERT INTO `sys_config` (`id`, `config_key`, `config_name`, `config_type`, `config_value`, `data_type`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (585177022468168, 'app.user.service', '移动端-客户服务', 'Y', '{\"zh_CN\":\"<h2>客服服务</h2>\",\"en_US\":\"\",\"ru_RU\":\"\"}', 7, NULL, 1, NULL, '2024-08-30 07:11:26', 1, '2025-03-31 13:38:32');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint NOT NULL COMMENT '部门ID',
  `parent_id` bigint DEFAULT NULL COMMENT '父部门ID',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门名称',
  `leader` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '负责人',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `order_num` int DEFAULT NULL COMMENT '显示顺序',
  `ancestors` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '祖级列表',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门状态:0正常,1停用',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `id` bigint NOT NULL COMMENT '字典编码',
  `css_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `dict_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典标签',
  `dict_sort` int DEFAULT NULL COMMENT '字典排序',
  `dict_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典类型',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典键值',
  `is_default` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否默认（Y是 N否）',
  `list_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表格字典样式',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态（0正常 1停用）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1, '', '男', 1, 'sys_user_sex', '0', 'Y', '', '性别男', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2, '', '女', 2, 'sys_user_sex', '1', 'N', '', '性别女', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (3, '', '未知', 3, 'sys_user_sex', '2', 'N', '', '性别未知', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (4, '', '显示', 1, 'sys_show_hide', '0', 'Y', 'primary', '显示菜单', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (5, '', '隐藏', 2, 'sys_show_hide', '1', 'N', 'danger', '隐藏菜单', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (6, '', '正常', 1, 'sys_normal_disable', '0', 'Y', 'primary', '正常状态', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (7, '', '停用', 2, 'sys_normal_disable', '1', 'N', 'danger', '停用状态', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (12, '', '是', 1, 'sys_yes_no', 'Y', 'Y', 'primary', '系统默认是', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (13, '', '否', 2, 'sys_yes_no', 'N', 'N', 'danger', '系统默认否', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (14, '', '通知', 1, 'sys_notice_type', '1', 'Y', 'warning', '通知', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (15, '', '公告', 2, 'sys_notice_type', '2', 'N', 'success', '公告', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (16, '', '正常', 1, 'sys_notice_status', '0', 'Y', 'primary', '正常状态', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (17, '', '关闭', NULL, 'sys_notice_status', '1', 'N', 'danger', '关闭状态', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (18, '', '新增', NULL, 'sys_oper_type', '1', 'N', 'info', '新增操作', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (19, '', '修改', NULL, 'sys_oper_type', '2', 'N', 'info', '修改操作', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (20, '', '删除', NULL, 'sys_oper_type', '3', 'N', 'danger', '删除操作', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (21, '', '授权', NULL, 'sys_oper_type', '4', 'N', 'primary', '授权操作', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (22, '', '导出', NULL, 'sys_oper_type', '5', 'N', 'warning', '导出操作', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (23, '', '导入', NULL, 'sys_oper_type', '6', 'N', 'warning', '导入操作', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (24, '', '强退', NULL, 'sys_oper_type', '7', 'N', 'danger', '强退操作', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (25, '', '生成代码', NULL, 'sys_oper_type', '8', 'N', 'warning', '生成操作', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (26, '', '清空数据', NULL, 'sys_oper_type', '9', 'N', 'danger', '清空操作', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (27, '', '成功', NULL, 'sys_common_status', '0', 'N', 'primary', '正常状态', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (28, '', '失败', NULL, 'sys_common_status', '1', 'N', 'danger', '停用状态', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_data` (`id`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (29, '', '其他', NULL, 'sys_oper_type', '0', 'N', 'info', '其他操作', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` bigint NOT NULL COMMENT '字典主键',
  `dict_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典类型',
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态（0正常 1停用）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1, 'sys_user_sex', '用户性别', '用户性别列表', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2, 'sys_show_hide', '菜单状态', '菜单状态列表', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (3, 'sys_normal_disable', '系统开关', '系统开关列表', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (6, 'sys_yes_no', '系统是否', '系统是否列表', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (7, 'sys_notice_type', '通知类型', '通知类型列表', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (8, 'sys_notice_status', '通知状态', '通知状态列表', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (9, 'sys_oper_type', '操作类型', '操作类型列表', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (10, 'sys_common_status', '系统状态', '登录状态列表', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
COMMIT;

-- ----------------------------
-- Table structure for sys_logininfo
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfo`;
CREATE TABLE `sys_logininfo` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '浏览器类型',
  `ipaddr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录地点',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提示消息',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作系统',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录状态 0成功 1失败',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_logininfo
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单名称',
  `menu_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型（M目录 C菜单 F按钮）',
  `parent_id` bigint DEFAULT NULL COMMENT '父菜单ID',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组件路径',
  `query_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '路由参数',
  `perms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限字符串',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '路由地址',
  `order_num` int DEFAULT NULL COMMENT '显示顺序',
  `is_cache` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否缓存（0缓存 1不缓存）',
  `is_frame` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否为外链（0是 1否）',
  `is_profit` tinyint(1) DEFAULT NULL,
  `is_dealer` tinyint(1) DEFAULT NULL COMMENT '是否合作商菜单（0否 1是）',
  `is_agent` tinyint(1) DEFAULT NULL,
  `is_tenant` tinyint(1) DEFAULT NULL,
  `is_platform_apply` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_dealer_apply` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_agent_apply` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_tenant_apply` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `visible` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '显示状态（0显示 1隐藏）',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单状态（0正常 1停用）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1, '{\"zh_CN\":\"系统管理\",\"en_US\":\"System\",\"ru_RU\":null}', 'M', 0, 'system', NULL, '', '', 'system', 80, '0', '1', 0, 0, 0, 0, '1', '0', '1', '1', '0', '1', '系统管理目录', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 17:54:18');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2, '{\"zh_CN\":\"系统监控\",\"en_US\":\"Monitor\",\"ru_RU\":null}', 'M', 0, 'monitor', NULL, '', '', 'monitor', 100, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', '系统监控目录', 1, 103, '2024-08-15 10:34:57', 1, '2024-10-05 17:20:39');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (6, '{\"zh_CN\":\"平台管理\",\"en_US\":\"Platform\",\"ru_RU\":null}', 'M', 0, 'chart', NULL, '', '', 'platform', 90, '0', '1', 0, 0, 0, 0, '1', '0', '0', '0', '0', '1', '租户管理目录', 1, 103, '2024-08-15 10:34:57', 1, '2024-10-05 17:20:29');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (100, '{\"zh_CN\":\"用户管理\",\"en_US\":\"User\",\"ru_RU\":null}', 'C', 1, 'user', 'system/user/list', '', 'system:user:list', 'user', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '用户管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 17:56:02');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (101, '{\"zh_CN\":\"角色管理\",\"en_US\":\"Role\",\"ru_RU\":null}', 'C', 1, 'peoples', 'system/role/list', '', 'system:role:list', 'role', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '角色管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 17:56:12');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (102, '{\"zh_CN\":\"菜单管理\",\"en_US\":\"Menu\",\"ru_RU\":null}', 'C', 6, 'tree-table', 'platform/menu/list', '', 'platform:menu:list', 'menu', 3, '0', '1', 0, 0, 0, 0, '0', '1', '1', '1', '0', '1', '菜单管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:47:38');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (103, '{\"zh_CN\":\"部门管理\",\"en_US\":\"Department\",\"ru_RU\":null}', 'C', 1, 'tree', 'system/dept/index', '', 'system:dept:list', 'dept', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '部门管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 17:56:19');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (104, '{\"zh_CN\":\"岗位管理\",\"en_US\":\"Position\",\"ru_RU\":null}', 'C', 1, 'post', 'system/post/index', '', 'system:post:list', 'post', 5, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '1', '1', '岗位管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 06:22:50');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (105, '{\"zh_CN\":\"字典管理\",\"en_US\":\"Dict\",\"ru_RU\":null}', 'C', 6, 'dict', 'platform/dict/list', '', 'platform:dict:list', 'dict', 6, '0', '1', 0, 0, 0, 0, '0', '1', '1', '1', '1', '1', '字典管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-10 05:20:14');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (106, '{\"zh_CN\":\"参数设置\",\"en_US\":\"Config\",\"ru_RU\":null}', 'C', 1, 'edit', 'system/config/index', '', 'system:config:list', 'sysconfig', 7, '0', '1', 0, 0, 0, 0, '1', '1', '0', '1', '0', '1', '参数设置菜单', 1, 103, '2024-08-15 10:34:57', 1, '2024-10-05 17:24:36');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (107, '{\"zh_CN\":\"通知公告\",\"en_US\":\"Announcement\",\"ru_RU\":null}', 'C', 580697072078917, 'message', 'business/notice/list', '', 'business:notice:list', 'notice', 800, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', '通知公告菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:13:48');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (109, '{\"zh_CN\":\"在线用户\",\"en_US\":\"Online User\",\"ru_RU\":null}', 'C', 2, 'online', 'monitor/online/index', '', 'monitor:online:list', 'online', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '在线用户菜单', 1, 103, '2024-08-15 10:34:57', 1, '2024-10-05 17:26:26');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (118, '{\"zh_CN\":\"文件管理\",\"en_US\":\"Attachment\",\"ru_RU\":null}', 'C', 6, 'upload', 'platform/oss/list', '', 'platform:oss:list', 'oss', 10, '0', '1', 0, 0, 0, 0, '0', '1', '1', '1', '0', '1', '文件管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:55:08');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (423, '{\"zh_CN\":\"应用修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 502, '', NULL, NULL, 'system:app:edit', '', 4, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:42');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (501, '{\"zh_CN\":\"登录日志\",\"en_US\":\"Login Log\",\"ru_RU\":null}', 'C', 2, 'logininfor', 'monitor/loginlog/index', '', 'monitor:loginlog:list', 'logininfor', 2, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', '登录日志菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:42:26');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (502, '{\"zh_CN\":\"应用管理\",\"en_US\":\"Application\",\"ru_RU\":null}', 'C', 1, 'phone', 'system/app/list', '', 'system:app:list', 'app', 11, '1', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', '应用管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:12');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1001, '{\"zh_CN\": \"用户查询\", \"en_US\":\"\"}', 'F', 100, '#', '', '', 'system:user:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1002, '{\"zh_CN\": \"用户新增\", \"en_US\":\"\"}', 'F', 100, '#', '', '', 'system:user:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1003, '{\"zh_CN\": \"用户修改\", \"en_US\":\"\"}', 'F', 100, '#', '', '', 'system:user:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1004, '{\"zh_CN\":\"用户删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 100, '#', '', '', 'system:user:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:08:00');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1005, '{\"zh_CN\": \"用户导出\", \"en_US\":\"\"}', 'F', 100, '#', '', '', 'system:user:export', '', 5, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1007, '{\"zh_CN\": \"重置密码\", \"en_US\":\"\"}', 'F', 100, '#', '', '', 'system:user:resetPwd', '', 7, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1008, '{\"zh_CN\": \"角色查询\", \"en_US\":\"\"}', 'F', 101, '#', '', '', 'system:role:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1009, '{\"zh_CN\": \"角色新增\", \"en_US\":\"\"}', 'F', 101, '#', '', '', 'system:role:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1010, '{\"zh_CN\": \"角色修改\", \"en_US\":\"\"}', 'F', 101, '#', '', '', 'system:role:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1011, '{\"zh_CN\":\"角色删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 101, '#', '', '', 'system:role:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:07:05');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1012, '{\"zh_CN\": \"角色导出\", \"en_US\":\"\"}', 'F', 101, '#', '', '', 'system:role:export', '', 5, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1013, '{\"zh_CN\":\"菜单查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 102, '#', '', '', 'platform:menu:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:47:47');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1014, '{\"zh_CN\":\"菜单新增\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 102, '#', '', '', 'platform:menu:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:47:55');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1015, '{\"zh_CN\":\"菜单修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 102, '#', '', '', 'platform:menu:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:48:03');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1016, '{\"zh_CN\":\"菜单删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 102, '#', '', '', 'platform:menu:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:48:13');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1017, '{\"zh_CN\": \"部门查询\", \"en_US\":\"\"}', 'F', 103, '#', '', '', 'system:dept:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1018, '{\"zh_CN\": \"部门新增\", \"en_US\":\"\"}', 'F', 103, '#', '', '', 'system:dept:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1019, '{\"zh_CN\": \"部门修改\", \"en_US\":\"\"}', 'F', 103, '#', '', '', 'system:dept:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1020, '{\"zh_CN\":\"部门删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 103, '#', '', '', 'system:dept:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:06:19');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1021, '{\"zh_CN\": \"岗位查询\", \"en_US\":\"\"}', 'F', 104, '#', '', '', 'system:post:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1022, '{\"zh_CN\": \"岗位新增\", \"en_US\":\"\"}', 'F', 104, '#', '', '', 'system:post:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1023, '{\"zh_CN\": \"岗位修改\", \"en_US\":\"\"}', 'F', 104, '#', '', '', 'system:post:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1024, '{\"zh_CN\":\"岗位删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 104, '#', '', '', 'system:post:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:06:07');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1025, '{\"zh_CN\": \"岗位导出\", \"en_US\":\"\"}', 'F', 104, '#', '', '', 'system:post:export', '', 5, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1026, '{\"zh_CN\": \"字典查询\", \"en_US\":\"\"}', 'F', 105, '#', '', '', 'system:dict:query', '#', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1027, '{\"zh_CN\": \"字典新增\", \"en_US\":\"\"}', 'F', 105, '#', '', '', 'system:dict:add', '#', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1028, '{\"zh_CN\": \"字典修改\", \"en_US\":\"\"}', 'F', 105, '#', '', '', 'system:dict:edit', '#', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1029, '{\"zh_CN\": \"字典删除\", \"en_US\":\"\"}', 'F', 105, '#', '', '', 'system:dict:remove', '#', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1030, '{\"zh_CN\": \"字典导出\", \"en_US\":\"\"}', 'F', 105, '#', '', '', 'system:dict:export', '#', 5, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1033, '{\"zh_CN\": \"参数修改\", \"en_US\":\"\"}', 'F', 106, '#', '', '', 'system:config:edit', '#', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1036, '{\"zh_CN\":\"公告查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 107, '#', '', '', 'system:notice:query', '#', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:13:55');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1037, '{\"zh_CN\":\"公告新增\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 107, '#', '', '', 'system:notice:add', '#', 2, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:14:02');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1038, '{\"zh_CN\":\"公告修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 107, '#', '', '', 'system:notice:edit', '#', 3, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:14:11');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1039, '{\"zh_CN\":\"公告删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 107, '#', '', '', 'system:notice:delete', '#', 4, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:14:15');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1043, '{\"zh_CN\":\"登录日志查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 501, '#', '', '', 'monitor:loginlog:query', '#', 1, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:43:21');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1044, '{\"zh_CN\":\"登录日志删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 501, '#', '', '', 'monitor:loginlog:delete', '#', 2, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:43:17');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1045, '{\"zh_CN\":\"登录日志导出\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 501, '#', '', '', 'monitor:loginlog:export', '#', 3, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:43:26');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1047, '{\"zh_CN\":\"强退\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 109, '#', '', '', 'monitor:online:forceLogout', '#', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-14 09:22:33');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1050, '{\"zh_CN\":\"登录账户解锁\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 501, '#', '', '', 'monitor:loginlog:unlock', '#', 4, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:43:33');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1600, '{\"zh_CN\": \"文件查询\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:query', '#', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1601, '{\"zh_CN\": \"文件上传\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:upload', '#', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1602, '{\"zh_CN\": \"文件下载\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:download', '#', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1603, '{\"zh_CN\": \"文件删除\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:remove', '#', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1604, '{\"zh_CN\": \"配置添加\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:add', '#', 5, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1605, '{\"zh_CN\": \"配置编辑\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:edit', '#', 6, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2205, '{\"zh_CN\":\"产品管理\",\"en_US\":\"Product\",\"ru_RU\":null}', 'C', 6, 'product-management', 'platform/product/list', NULL, 'platform:product:list', 'product', 401, '1', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:09:13');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2210, '{\"zh_CN\":\"插件管理\",\"en_US\":\"Plugins\",\"ru_RU\":null}', 'C', 6, 'component', 'platform/plugins/index', NULL, 'platform:plugin:list', 'plugins', 400, '1', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:11:20');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2213, '{\"zh_CN\":\"定时任务\",\"en_US\":\"\",\"ru_RU\":null}', 'C', 6, 'component', 'platform/task/list', NULL, 'platform:task:list', 'task', 100, '0', '1', 0, 0, 0, 0, '0', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 17:30:49');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2222, '{\"zh_CN\":\"告警日志\",\"en_US\":\"\",\"ru_RU\":null}', 'C', 2, 'message', 'monitor/alarm/list', NULL, 'monitor:alert:list', 'list', 10, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:10:45');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442129, '{\"zh_CN\":\"应用查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 502, '', NULL, NULL, 'system:app:query', '', 1, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:25');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (44212332, '{\"zh_CN\":\"应用添加\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 502, '', NULL, NULL, 'system:app:add', '', 2, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:31');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (4234553537, '{\"zh_CN\":\"应用导出\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 502, '', NULL, NULL, 'system:app:export', '', 5, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:52');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442123323123, '{\"zh_CN\":\"应用删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 502, '', NULL, NULL, 'system:app:delete', '', 4, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:47');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (441853056651333, '{\"zh_CN\":\"产品查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2205, '', NULL, NULL, 'platform:product:query', '', 1, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:09:22');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (441853220675653, '{\"zh_CN\":\"产品添加\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2205, '', NULL, NULL, 'platform:product:add', '', 1, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:09:28');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (441853285179461, '{\"zh_CN\":\"产品修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2205, '', NULL, NULL, 'platform:product:edit', '', 1, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:09:32');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (441862726639685, '{\"zh_CN\":\"产品删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2205, '', NULL, NULL, 'platform:product:delete', '', 1, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:09:42');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442127357415493, '{\"zh_CN\":\"插件添加\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2210, '', NULL, NULL, 'platform:plugin:add', '', 1, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:11:28');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442127532781637, '{\"zh_CN\":\"插件修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2210, '', NULL, NULL, 'platform:plugin:edit', '', 2, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:11:33');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442127596064837, '{\"zh_CN\":\"插件查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2210, '', NULL, NULL, 'platform:plugin:query', '', 0, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:11:53');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442127705182277, '{\"zh_CN\":\"插件删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2210, '', NULL, NULL, 'platform:plugin:delete', '', 3, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:11:42');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442129175347269, '{\"zh_CN\":\"定时任务查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2213, '', NULL, NULL, 'platform:task:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-01-23 02:48:44');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442129320091717, '{\"zh_CN\":\"定时任务修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2213, '', NULL, NULL, 'platform:task:edit', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-01-23 02:48:53');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442131294584901, '{\"zh_CN\":\"告警查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2222, '', NULL, NULL, 'monitor:alert:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:10:35');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (580697072078917, '{\"zh_CN\":\"运营管理\",\"en_US\":\"Business\",\"ru_RU\":null}', 'M', 0, 'cube', NULL, NULL, NULL, 'business', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-17 15:22:28', 1, '2025-01-21 16:35:26');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (580958514028613, '{\"zh_CN\":\"场站管理\",\"en_US\":\"Station\",\"ru_RU\":null}', 'C', 580697072078917, 'clipboard', 'business/station/list', NULL, 'business:station:list', 'station', 1, '1', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-18 09:06:17', 1, '2025-01-06 01:58:31');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (587680307204165, '{\"zh_CN\":\"客户管理\",\"en_US\":\"Customer\",\"ru_RU\":null}', 'C', 580697072078917, 'clipboard', 'business/customer/list', NULL, 'business:customer:list', 'customer', 100, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-09-06 08:57:19', 1, '2025-01-15 06:46:21');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (594752547803205, '{\"zh_CN\":\"操作日志\",\"en_US\":\"Operate Log\",\"ru_RU\":null}', 'C', 2, 'date', 'monitor/operlog/index', NULL, 'monitor:operlog:list', 'operlog', 1, '1', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-09-26 08:34:21', 1, '2024-10-05 17:26:36');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (594753237971013, '{\"zh_CN\":\"设备日志\",\"en_US\":\"Device Log\",\"ru_RU\":null}', 'C', 2, 'date', 'monitor/devlog/list', NULL, 'monitor:devlog:list', 'devlog', 1, '1', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-09-26 08:37:09', 1, '2025-01-15 01:13:54');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (612325417422917, '{\"zh_CN\":\"广告管理\",\"en_US\":\"\",\"ru_RU\":null}', 'C', 580697072078917, 'code', 'business/ads/list', NULL, 'business:ads:list', 'ads', 5000, '1', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2024-11-15 00:18:32', 1, '2025-01-23 02:13:00');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (612325942202437, '{\"zh_CN\":\"广告查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 612325417422917, '', NULL, NULL, 'business:ads:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2024-11-15 00:20:40', 1, '2025-01-23 02:13:09');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (612326029131845, '{\"zh_CN\":\"广告新增\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 612325417422917, '', NULL, NULL, 'business:ads:add', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2024-11-15 00:21:01', 1, '2025-01-23 02:13:15');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (612326096756805, '{\"zh_CN\":\"广告修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 612325417422917, '', NULL, NULL, 'business:ads:edit', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2024-11-15 00:21:18', 1, '2025-01-23 02:13:21');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (612326157504581, '{\"zh_CN\":\"广告删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 612325417422917, '', NULL, NULL, 'business:ads:delete', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2024-11-15 00:21:33', 1, '2025-01-23 02:13:27');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (632588972994629, '{\"zh_CN\":\"运营统计\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 580697072078917, 'dashboard', 'business/analyze/list', NULL, 'business:analyze:list', 'analyze', 0, '1', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-11 06:31:09', 1, '2025-01-11 06:31:09');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (633808640872517, '{\"zh_CN\":\"功能配置\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 1, 'chart', 'system/func/list', NULL, 'system:func:list', 'func', 10, '0', '1', 0, 0, 0, 0, '1', '1', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-14 17:14:00', 1, '2025-01-14 17:14:00');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636115075387461, '{\"zh_CN\":\"设备管理\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 580697072078917, 'table2', 'business/charger/list', NULL, 'business:charger:list', 'charger', 2, '1', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-21 05:38:54', 1, '2025-03-30 07:55:25');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636118310428741, '{\"zh_CN\":\"充电桩查询\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636115075387461, '', NULL, NULL, 'business:charger:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-21 05:52:04', 1, '2025-01-21 06:19:35');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636118371549253, '{\"zh_CN\":\"充电桩添加\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636115075387461, '', NULL, NULL, 'business:charger:add', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 05:52:19', 1, '2025-02-14 06:33:36');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636118428803141, '{\"zh_CN\":\"充电桩修改\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636115075387461, '', NULL, NULL, 'business:charger:edit', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 05:52:33', 1, '2025-01-21 06:19:45');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636118496186437, '{\"zh_CN\":\"充电桩删除\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636115075387461, '', NULL, NULL, 'business:charger:delete', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 05:52:49', 1, '2025-01-21 06:19:51');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636118569152581, '{\"zh_CN\":\"充电桩操作\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636115075387461, '', NULL, NULL, 'business:charger:operate', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 05:53:07', 1, '2025-01-21 06:19:56');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636127715053637, '{\"zh_CN\":\"计费规则\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 580697072078917, 'table2', 'business/priceCharge/list', NULL, 'business:price_charge:list', 'priceCharge', 3, '1', '1', 0, 0, 0, 0, NULL, '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:30:20', 1, '2025-03-30 07:50:13');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636127909134405, '{\"zh_CN\":\"充电计费规则查询\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636127715053637, '', NULL, NULL, 'business:price_charge:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:31:07', 1, '2025-01-21 06:32:22');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636127977586757, '{\"zh_CN\":\"充电计费规则添加\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636127715053637, '', NULL, NULL, 'business:price_charge:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:31:24', 1, '2025-01-21 06:32:28');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636128043327557, '{\"zh_CN\":\"充电计费规则修改\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636127715053637, '', NULL, NULL, 'business:price_charge:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:31:40', 1, '2025-01-21 06:32:33');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636128147722309, '{\"zh_CN\":\"充电计费规则删除\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636127715053637, '', NULL, NULL, 'business:price_charge:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:32:06', 1, '2025-01-21 06:32:38');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636128797368389, '{\"zh_CN\":\"订单管理\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 580697072078917, 'table2', 'business/order/list', NULL, 'business:order:list', 'order', 5, '1', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:34:44', 1, '2025-03-30 07:53:04');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636129844842565, '{\"zh_CN\":\"订单查询\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636128797368389, '', NULL, NULL, 'business:order:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:39:00', 1, '2025-01-21 06:39:00');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636130078470213, '{\"zh_CN\":\"订单结算\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636128797368389, '', NULL, NULL, 'business:order:settle', '', 2, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:39:57', 1, '2025-01-21 06:39:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636130198978629, '{\"zh_CN\":\"订单扣款\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636128797368389, '', NULL, NULL, 'business:order:debit', '', 3, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:40:26', 1, '2025-01-21 06:40:26');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636130340139077, '{\"zh_CN\":\"订单分润\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636128797368389, '', NULL, NULL, 'business:order:deal', '', 4, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:41:01', 1, '2025-01-21 06:41:01');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636130515345477, '{\"zh_CN\":\"订单导出\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636128797368389, '', NULL, NULL, 'business:order:export', '', 5, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:41:44', 1, '2025-01-21 06:41:44');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636130591506501, '{\"zh_CN\":\"订单操作\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636128797368389, '', NULL, NULL, 'business:order:operate', '', 6, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:42:02', 1, '2025-01-21 06:42:02');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636130717261893, '{\"zh_CN\":\"退款管理\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 580697072078917, 'table2', 'business/refund/list', NULL, 'business:refund:list', 'refund', 6, '1', '1', 0, 0, 0, 0, NULL, '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:42:33', 1, '2025-03-30 07:53:16');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636130799206469, '{\"zh_CN\":\"退款查询\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636130717261893, '', NULL, NULL, 'business:refund:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:42:53', 1, '2025-01-21 06:42:53');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636134258819141, '{\"zh_CN\":\"充值记录\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 580697072078917, 'table2', 'business/topup/list', NULL, 'business:topup:list', 'topup', 9, '1', '1', 0, 0, 0, 0, NULL, '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:56:58', 1, '2025-03-30 07:52:10');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636134381580357, '{\"zh_CN\":\"充值方案\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 580697072078917, 'table2', 'business/recharge/list', NULL, 'business:recharge:list', 'recharge', 10, '1', '1', 0, 0, 0, 0, NULL, '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-21 06:57:28', 1, '2025-03-30 07:52:31');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636135833206853, '{\"zh_CN\":\"支付设置\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 633808640872517, '', NULL, NULL, 'system:payment_config:list', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-21 07:03:22', 1, '2025-01-21 07:08:19');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636135915032645, '{\"zh_CN\":\"短信设置\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 633808640872517, '', NULL, NULL, 'system:sms_config:list', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-21 07:03:42', 1, '2025-01-21 07:08:30');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636136017424453, '{\"zh_CN\":\"地图设置\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 633808640872517, '', NULL, NULL, 'system:map_config:list', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-21 07:04:07', 1, '2025-01-21 07:08:37');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636136103194693, '{\"zh_CN\":\"通知设置\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 633808640872517, '', NULL, NULL, 'system:notify_config:list', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-21 07:04:28', 1, '2025-01-21 07:08:45');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636136209788997, '{\"zh_CN\":\"通知模板管理\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 633808640872517, '', NULL, NULL, 'system:notify_template:list', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-21 07:04:54', 1, '2025-01-23 02:00:59');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636556871516229, '{\"zh_CN\":\"场站查询\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 580958514028613, '', NULL, NULL, 'business:station:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-22 11:36:34', 1, '2025-01-22 11:36:34');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636556962840645, '{\"zh_CN\":\"场站添加\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 580958514028613, '', NULL, NULL, 'business:station:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-22 11:36:57', 1, '2025-01-22 11:36:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636557025083461, '{\"zh_CN\":\"场站修改\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 580958514028613, '', NULL, NULL, 'business:station:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-22 11:37:12', 1, '2025-01-22 11:37:12');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636557094232133, '{\"zh_CN\":\"场站删除\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 580958514028613, '', NULL, NULL, 'business:station:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-22 11:37:29', 1, '2025-01-22 11:37:29');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636561373106245, '{\"zh_CN\":\"车位管理\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 580958514028613, '', NULL, NULL, 'business:station:parking:list', '', 5, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-22 11:54:53', 1, '2025-01-22 11:54:53');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636561609973829, '{\"zh_CN\":\"车位添加\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636561373106245, '', NULL, NULL, 'business:station:parking:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-22 11:55:51', 1, '2025-01-22 11:56:46');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636561702686789, '{\"zh_CN\":\"车位修改\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636561373106245, '', NULL, NULL, 'business:station:parking:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-22 11:56:14', 1, '2025-01-22 11:56:50');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636561792860229, '{\"zh_CN\":\"车位删除\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636561373106245, '', NULL, NULL, 'business:station:parking:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-22 11:56:36', 1, '2025-01-22 11:56:53');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636561924309061, '{\"zh_CN\":\"车位查询\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636561373106245, '', NULL, NULL, 'business:station:parking:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-22 11:57:08', 1, '2025-01-22 11:57:08');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636766912712773, '{\"zh_CN\":\"设置参数\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636135833206853, '', NULL, NULL, 'system:payment_config:setting', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:51:14', 1, '2025-01-23 01:51:14');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636767065292869, '{\"zh_CN\":\"修改状态\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636135833206853, '', NULL, NULL, 'system:payment_config:change_status', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:51:51', 1, '2025-01-23 01:51:51');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636767470530629, '{\"zh_CN\":\"设置参数\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636135915032645, '', NULL, NULL, 'system:sms_config:setting', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:53:30', 1, '2025-01-23 01:53:30');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636767716552773, '{\"zh_CN\":\"修改状态\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636135915032645, '', NULL, NULL, 'system:sms_config:change_status', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:54:30', 1, '2025-01-23 01:54:30');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636768020324421, '{\"zh_CN\":\"设置参数\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636136017424453, '', NULL, NULL, 'system:map_config:setting', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:55:44', 1, '2025-01-23 01:55:44');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636768130306117, '{\"zh_CN\":\"修改状态\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636136017424453, '', NULL, NULL, 'system:map_config:change_status', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:56:11', 1, '2025-01-23 01:56:11');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636768269250629, '{\"zh_CN\":\"设置参数\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636136103194693, '', NULL, NULL, 'system:notify_config:setting', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:56:45', 1, '2025-01-23 01:56:45');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636768344064069, '{\"zh_CN\":\"修改状态\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636136103194693, '', NULL, NULL, 'system:notify_config:change_status', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:57:04', 1, '2025-01-23 01:57:04');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636768603623493, '{\"zh_CN\":\"通知模板查询\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636136209788997, '', NULL, NULL, 'system:notify_template:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:58:07', 1, '2025-01-23 02:01:19');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636769473003589, '{\"zh_CN\":\"通知模板添加\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636136209788997, '', NULL, NULL, 'system:notify_template:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 02:01:39', 1, '2025-01-23 02:02:02');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636769544523845, '{\"zh_CN\":\"通知模板修改\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636136209788997, '', NULL, NULL, 'system:notify_template:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 02:01:57', 1, '2025-01-23 02:01:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636769642655813, '{\"zh_CN\":\"通知模板删除\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636136209788997, '', NULL, NULL, 'system:notify_template:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 02:02:21', 1, '2025-01-23 02:02:21');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636775185645637, '{\"zh_CN\":\"客户查询\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 587680307204165, '', NULL, NULL, 'business:customer:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 02:24:54', 1, '2025-01-23 02:24:54');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636775930400837, '{\"zh_CN\":\"系统充值\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636134258819141, '', NULL, NULL, 'business:topup:add', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-23 02:27:56', 1, '2025-01-24 14:45:02');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636776077942853, '{\"zh_CN\":\"充值记录导出\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636134258819141, '', NULL, NULL, 'business:topup:export', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-23 02:28:32', 1, '2025-01-24 14:45:07');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636776806207557, '{\"zh_CN\":\"充值方案查询\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636134381580357, '', NULL, NULL, 'business:recharge:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-23 02:31:29', 1, '2025-01-24 14:45:22');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636776871784517, '{\"zh_CN\":\"充值方案添加\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636134381580357, '', NULL, NULL, 'business:recharge:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-23 02:31:45', 1, '2025-01-24 14:45:26');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636776933912645, '{\"zh_CN\":\"充值方案修改\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636134381580357, '', NULL, NULL, 'business:recharge:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-23 02:32:01', 1, '2025-01-24 14:45:31');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636777021833285, '{\"zh_CN\":\"充值方案删除\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 636134381580357, '', NULL, NULL, 'business:recharge:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-23 02:32:22', 1, '2025-01-24 14:45:35');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (643188978524229, '{\"zh_CN\":\"平台设置\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 6, 'dashboard', 'platform/setting/list', NULL, 'platform:setting:list', 'setting', 1000, '1', '1', 0, 0, 0, 0, '0', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-02-10 05:22:41', 1, '2025-02-10 05:23:00');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655149342990405, '{\"zh_CN\":\"新建退款\",\"en_US\":\"\",\"ru_RU\":\"\"}', 'F', 636130717261893, '', NULL, NULL, 'business:refund:add', '', 2, '0', '1', 0, 0, 0, 0, NULL, NULL, NULL, NULL, '0', '1', NULL, 1, NULL, '2025-03-16 00:29:32', 1, '2025-03-16 00:29:40');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655149682745413, '{\"zh_CN\":\"执行退款\",\"en_US\":\"\",\"ru_RU\":\"\"}', 'F', 636130717261893, '', NULL, NULL, 'business:refund:refund', '', 3, '0', '1', 0, 0, 0, 0, NULL, NULL, NULL, NULL, '0', '1', NULL, 1, NULL, '2025-03-16 00:30:55', 1, '2025-03-16 00:30:55');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655149897695301, '{\"zh_CN\":\"删除退款\",\"en_US\":\"\",\"ru_RU\":\"\"}', 'F', 636130717261893, '', NULL, NULL, 'business:refund:delete', '', 4, '0', '1', 0, 0, 0, 0, NULL, NULL, NULL, NULL, '0', '1', NULL, 1, NULL, '2025-03-16 00:31:47', 1, '2025-03-16 00:31:47');
COMMIT;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `id` bigint NOT NULL COMMENT '公告ID',
  `notice_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告内容',
  `notice_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告标题',
  `notice_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告类型（1通知 2公告）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告状态（0正常 1关闭）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
BEGIN;
INSERT INTO `sys_notice` (`id`, `notice_content`, `notice_title`, `notice_type`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1, '<p>5paw54mI5pys5YaF5a65墨迹</p>', '温馨提醒：2018-07-01 新版本发布啦', '1', '管理员', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-11-27 16:32:56');
INSERT INTO `sys_notice` (`id`, `notice_content`, `notice_title`, `notice_type`, `remark`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2, '57u05oqk5YaF5a65', '维护通知：2018-07-01 系统凌晨维护', '1', '管理员', '0', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
COMMIT;

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint NOT NULL COMMENT '对象存储主键',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件名',
  `file_suffix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件后缀名',
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原名',
  `service` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '服务商',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'URL地址',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_oss
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_oss_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_config`;
CREATE TABLE `sys_oss_config` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主建',
  `access_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'accessKey',
  `access_policy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '桶权限类型(0private 1public 2custom)',
  `bucket_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '桶名称',
  `config_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置key',
  `domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '自定义域名',
  `endpoint` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '访问站点',
  `ext1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段',
  `is_https` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否https（0否 1是）',
  `prefix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '前缀',
  `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '域',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '秘钥',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否默认（0=是,1=否）',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_oss_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_oss_config` (`id`, `access_key`, `access_policy`, `bucket_name`, `config_key`, `domain`, `endpoint`, `ext1`, `is_https`, `prefix`, `region`, `remark`, `secret_key`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1', 'admin', '1', 'iot', 'oss-embed', '', 'localhost:9085/iot-oss', '', 'Y', '2222', 'local', NULL, '123', '1', '1', '103', '2024-08-15 10:34:57', '1', '2025-03-30 08:58:18');
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint NOT NULL COMMENT '岗位序号',
  `post_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位编码',
  `post_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位名称',
  `post_sort` int DEFAULT NULL COMMENT '岗位排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态（0正常 1停用）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL COMMENT '角色ID',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `role_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色权限',
  `role_sort` int DEFAULT NULL COMMENT '角色排序',
  `data_scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）',
  `dept_check_strictly` bit(1) DEFAULT NULL COMMENT '部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）',
  `menu_check_strictly` bit(1) DEFAULT NULL COMMENT '菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）',
  `is_sys` tinyint(1) DEFAULT NULL COMMENT '是否是系统角色',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色状态（0正常 1停用）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '菜单ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `user_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户类型（sys_user系统用户）',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `dept_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门ID',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户邮箱',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最后登录IP',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户性别',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帐号状态（0正常 1停用）',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_agent_admin` int DEFAULT NULL,
  `is_tenant_admin` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `user_name`, `user_type`, `nick_name`, `phone`, `avatar`, `dept_id`, `email`, `login_date`, `login_ip`, `password`, `remark`, `sex`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `is_agent_admin`, `is_tenant_admin`) VALUES ('1', 'admin', 'SuperAdmin', 'admin', NULL, NULL, '100', 'xw2sy@163.com', '2025-03-31 13:28:20', '127.0.0.1', '$2a$10$Mz4n4UNgMQsRXIlngPrTReslgEvZaFVJIcQ9FzS6wRHhjzBjSUND.', '管理员', '1', '1', '1', '103', '2024-08-29 16:53:58', '1', '2025-03-31 13:28:20', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `post_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for task_info
-- ----------------------------
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE `task_info` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `actions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '任务输出',
  `expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表达式',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务名称',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作备注',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务状态',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务类型',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of task_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for topup
-- ----------------------------
DROP TABLE IF EXISTS `topup`;
CREATE TABLE `topup` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `payment_identifier` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付方式id',
  `payment_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `recharge_type` int DEFAULT NULL COMMENT '充值方案类型',
  `source` int DEFAULT NULL COMMENT '充值来源',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户用户名',
  `tran_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流水号',
  `topup_amount` decimal(10,2) DEFAULT NULL COMMENT '充值金额',
  `paid_amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `arrival_amount` decimal(10,2) DEFAULT NULL COMMENT '到账金额',
  `give` decimal(10,2) DEFAULT NULL COMMENT '赠送金额',
  `minus` decimal(10,2) DEFAULT NULL COMMENT '立减金额',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '服务费打折数量',
  `topup_time` datetime DEFAULT NULL COMMENT '充值时间',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `pay_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '付款id',
  `trade_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bank_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trade_state_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `refund_locked` int DEFAULT NULL,
  `refunded_amount` decimal(19,2) DEFAULT NULL,
  `state` int DEFAULT NULL COMMENT '启用/停用',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '描述',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `recharge_type` (`recharge_type`) USING BTREE,
  KEY `source` (`source`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值方案';

-- ----------------------------
-- Records of topup
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
