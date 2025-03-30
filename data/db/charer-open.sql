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

 Date: 30/03/2025 16:47:59
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of ads
-- ----------------------------
BEGIN;
INSERT INTO `ads` (`id`, `name`, `type`, `content`, `link`, `img`, `note`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('634017344553029', '{\"zh_CN\":\"ddd\",\"en_US\":null,\"ru_RU\":\"\"}', 'image', '{\"zh_CN\":\"\",\"en_US\":null,\"ru_RU\":\"\"}', NULL, 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_24_00b43e9472ff469b8a981d3523ddb18b.jpeg', 'ddd', 1, '10000', '1', NULL, '2025-01-15 07:23:13', '1', '2025-02-24 13:37:21');
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of alert_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for camera
-- ----------------------------
DROP TABLE IF EXISTS `camera`;
CREATE TABLE `camera` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `station_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '场站id',
  `product_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备名称',
  `dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备dn',
  `status` int DEFAULT NULL COMMENT '启用/停用',
  `secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备密钥',
  `online` int DEFAULT NULL COMMENT '设备状态',
  `online_time` bigint DEFAULT NULL COMMENT '设备在线时间',
  `offline_time` bigint DEFAULT NULL COMMENT '设备离线时间',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of camera
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of channel_config
-- ----------------------------
BEGIN;
INSERT INTO `channel_config` (`id`, `name`, `identifier`, `properties`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `note`) VALUES ('1', '短信', 'Sms', NULL, '10000', '1', NULL, '2024-12-15 21:27:46', NULL, NULL, NULL);
INSERT INTO `channel_config` (`id`, `name`, `identifier`, `properties`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `note`) VALUES ('2', 'App', 'App', NULL, '10000', '1', NULL, '2024-12-15 21:27:46', NULL, NULL, NULL);
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
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_update_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `dn` (`dn`) USING BTREE,
  KEY `product_key` (`product_key`),
  KEY `station_id` (`station_id`),
  KEY `price_id` (`price_id`),
  KEY `online` (`online`),
  KEY `status` (`status`),
  KEY `tenant_id` (`tenant_id`),
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of charger
-- ----------------------------
BEGIN;
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('612064437694533', '04', '594065961017413', '586997419884613', '05', '37708231609452', 'XpeciGeRs6Wke46Z', 2.00, 0, 1742270850685, 1743320790962, 1, NULL, '649213992497221', '10000', '1', NULL, '2024-11-14 06:36:36', '1', '2025-03-30 07:46:31', 1742272497595);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('612064492138565', '04', '594065961017413', '586997419884613', '01', '35346369478732', 'DzjGnnps6B2tQ4iW', 2.00, 0, 1742270852229, 1743320791025, 1, NULL, '649213992497221', '10000', '1', NULL, '2024-11-14 06:36:50', '1', '2025-03-30 07:46:31', 1742272492556);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('612064611700805', '04', '594065961017413', '586997419884613', '03', '17022249889259', 'JwdTsztQn5eEfH25', 2.00, 0, 1742270851597, 1743320791036, 1, NULL, '649213992497221', '10000', '1', NULL, '2024-11-14 06:37:19', '1', '2025-03-30 07:46:31', 1742272491675);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('612065649598533', '03', '594065961017413', '586997419884613', '测试设备', '48000000000000', 'JwdTsztQn45eEfH25', 2.00, 0, 1742270853324, 1743320791046, 1, NULL, '649213992497221', '10000', '1', NULL, '2024-11-14 06:41:32', '1', '2025-03-30 07:46:31', 1742272491187);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('613759149195333', '04', '594065961017413', '586997419884613', '02', '35356369478481', '6KAr6YhepwKFJJmP', 2.00, 0, 1742270851284, 1743320791056, 1, NULL, '649213992497221', '10000', '1', NULL, '2024-11-19 01:32:24', '1', '2025-03-30 07:46:31', 1742272499581);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('613865011867717', '04', '594065961017413', '586997419884613', '04', '65425588531925', 'EmhsbzWDWkdSFNQs', 2.00, 0, 1742270851766, 1743320791066, 1, NULL, '649213992497221', '10000', '1', NULL, '2024-11-19 08:43:10', '1', '2025-03-30 07:46:31', 1742272499096);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('617008233738309', '03', '617007724576837', '586997419884613', '03', '95064006312206', 'ZQtZ8WxWsbjy4AxP', 2.00, 0, 1742270860879, 1743320791076, 1, NULL, '649213992497221', '10000', '1', NULL, '2024-11-28 05:52:58', '1', '2025-03-30 07:46:31', 1742272494031);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('617008369291333', '03', '617007724576837', '586997419884613', '04', '10357611144727', 'h2XGrfPpcaC3nkY4', 2.00, 0, 1742270859551, 1743320791083, 1, NULL, '649213992497221', '10000', '1', NULL, '2024-11-28 05:53:31', '1', '2025-03-30 07:46:31', 1742272492615);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651348640223301', '05', '651348387233861', '654127040671813', '01', '36058143934159', 'mPNprAPedkXcMi6a', 5.00, 0, 1742270841798, 1743320791090, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:44:26', '1', '2025-03-30 07:46:31', 1742272494486);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651348773916741', '03', '651348387233861', '586997419884613', '02', '27315585897184', 'FxNfdKx2nMjkjPjH', 2.00, 0, 1742270850358, 1743320791097, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:44:59', '1', '2025-03-30 07:46:31', 1742272497904);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651348859646021', '03', '651348387233861', '586997419884613', '03', '72247778682044', 'QdaDN5PkxmA7C4x7', 2.00, 0, 1742270848357, 1743320791102, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:45:20', '1', '2025-03-30 07:46:31', 1742272497006);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651348948627525', '03', '651348387233861', '586997419884613', '04', '34716812710660', 'cf6wkRdjCn4mm5NM', 2.00, 0, 1742270849475, 1743320791108, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:45:41', '1', '2025-03-30 07:46:31', 1742272498300);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349035991109', '03', '651348387233861', '586997419884613', '05', '13855615589794', 'ANswpXkspnbZmpNx', 2.00, 0, 1742270848721, 1743320791113, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:46:03', '1', '2025-03-30 07:46:31', 1742272497056);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349130403909', '03', '651348387233861', '586997419884613', '06', '49841749392063', 'J6rndcWn5EF6H5cx', 2.00, 0, 1742270850229, 1743320791118, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:46:26', '1', '2025-03-30 07:46:31', 1742272498313);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349217067077', '03', '651348387233861', '586997419884613', '07', '84420170589055', 'EN3mjycpjdn3cyh2', 2.00, 0, 1742270850001, 1743320791122, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:46:47', '1', '2025-03-30 07:46:31', 1742272497153);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349294174277', '03', '651348387233861', '586997419884613', '08', '91102188205597', 'f8WhyPZrbkRBaaa6', 2.00, 0, 1742270848859, 1743320791127, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:47:06', '1', '2025-03-30 07:46:31', 1742272497244);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349364060229', '03', '651348387233861', '586997419884613', '09', '39302806840648', 'ffaTwTBsEF7MWprk', 2.00, 0, 1742111711579, 1743320791132, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:47:23', '1', '2025-03-30 07:46:31', 1742204084562);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349446111301', '03', '651348387233861', '586997419884613', '10', '85919106127720', 'DCjStYMRdJ4Hrez5', 2.00, 0, 1742270849603, 1743320791136, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:47:43', '1', '2025-03-30 07:46:31', 1742272498267);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349519933509', '03', '651348387233861', '586997419884613', '11', '53491656691040', 'Ypk5SD5he7hB6yBe', 2.00, 0, 1742002865522, 1743320791141, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:48:01', '1', '2025-03-30 07:46:31', 1742005940300);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349597933637', '03', '651348387233861', '586997419884613', '12', '72808569760502', 'nMRKJiENh5ZKjeGm', 2.00, 0, 1742270849824, 1743320791145, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:48:20', '1', '2025-03-30 07:46:31', 1742272497067);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349686448197', '03', '651348387233861', '586997419884613', '13', '34007864969850', 'ERky2xpBRmZdmWSW', 2.00, 0, 1742004746836, 1743320791150, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:48:42', '1', '2025-03-30 07:46:31', 1742008280365);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349766484037', '03', '651348387233861', '586997419884613', '14', '15497303127384', 'PDe2z34K2BNwN3iR', 2.00, 0, 1742270848991, 1743320791154, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:49:01', '1', '2025-03-30 07:46:31', 1742272497584);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349844291653', '03', '651348387233861', '586997419884613', '15', '32324342289284', 'Jx6DCpahznGyeBJG', 2.00, 0, 1742270852099, 1743320791159, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:49:20', '1', '2025-03-30 07:46:31', 1742272490505);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651349942534213', '03', '651348387233861', '586997419884613', '16', '27440875537048', 'daYxG2ydpmtmKFtN', 2.00, 0, 1742270860721, 1743320791163, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:49:44', '1', '2025-03-30 07:46:31', 1742272496339);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651350040875077', '03', '651348387233861', '586997419884613', '17', '45403527104516', 'PeHnHc4YMkTakMeE', 2.00, 0, 1742270850490, 1743320791167, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:50:08', '1', '2025-03-30 07:46:31', 1742272499196);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('651350126686277', '03', '651348387233861', '586997419884613', '18', '44151785795882', '7srsxpYPGJ5REDGE', 2.00, 0, 1742270850943, 1743320791172, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-05 06:50:29', '1', '2025-03-30 07:46:31', 1742272490104);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('654838048682053', '03', '617007724576837', '586997419884613', '02', '22130030363863', 'csFzi5REJNraAmFa', 2.00, 0, 1742271433115, 1743320791178, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-15 03:22:52', '1', '2025-03-30 07:46:31', 1742272496057);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('654838425395269', '03', '617007724576837', '586997419884613', '01', '50725245930304', 'AGnwGd2bA44pnE8e', 2.00, 0, 1742271767958, 1743320791183, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-15 03:24:24', '1', '2025-03-30 07:46:31', 1742272489291);
INSERT INTO `charger` (`id`, `product_key`, `station_id`, `price_id`, `name`, `dn`, `secret`, `low_balance`, `online`, `online_time`, `offline_time`, `status`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `last_update_time`) VALUES ('654838586552389', '03', '617007724576837', '586997419884613', '05', '32010600281014', 'fTeskcpmm5YFbNKZ', 2.00, 0, NULL, 1743320791189, 1, NULL, '649213992497221', '10000', '1', NULL, '2025-03-15 03:25:04', '1', '2025-03-30 07:46:31', NULL);
COMMIT;

-- ----------------------------
-- Table structure for charger_directive
-- ----------------------------
DROP TABLE IF EXISTS `charger_directive`;
CREATE TABLE `charger_directive` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备dn',
  `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '数据',
  `directive` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '指令名',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `relate_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联对象id',
  `result` int DEFAULT NULL COMMENT '执行结果',
  `serial` int DEFAULT NULL COMMENT '指令序列号',
  `state` int DEFAULT NULL COMMENT '执行状态',
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `type` int DEFAULT NULL COMMENT '关联类型',
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
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `charger_id` (`charger_id`,`no`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE,
  KEY `state` (`state`) USING BTREE,
  KEY `parking_id` (`parking_id`),
  KEY `no` (`no`),
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of charger_gun
-- ----------------------------
BEGIN;
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('612064437772357', '612064437694533', NULL, '01', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-14 06:36:36', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('612064437776453', '612064437694533', NULL, '02', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-14 06:36:36', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('612064492159045', '612064492138565', NULL, '01', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-14 06:36:50', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('612064492171333', '612064492138565', NULL, '02', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-14 06:36:50', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('612064611713093', '612064611700805', NULL, '01', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-14 06:37:19', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('612064611721285', '612064611700805', NULL, '02', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-14 06:37:19', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('612065649623109', '612065649598533', NULL, '01', 1, 2, 7, 2, 1, '-1', NULL, '10000', '1', NULL, '2024-11-14 06:41:32', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613759149219909', '613759149195333', NULL, '01', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-19 01:32:24', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613759149224005', '613759149195333', NULL, '02', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-19 01:32:24', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613865011900485', '613865011867717', NULL, '01', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-19 08:43:10', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613865011925061', '613865011867717', NULL, '02', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-19 08:43:10', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('617008233750597', '617008233738309', NULL, '01', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-28 05:52:58', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('617008369311813', '617008369291333', NULL, '01', 1, 2, 7, 2, 0, '-1', NULL, '10000', '1', NULL, '2024-11-28 05:53:31', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651348640247877', '651348640223301', NULL, '01', 2, 1, 30, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:44:26', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651348774015045', '651348773916741', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:44:59', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651348859670597', '651348859646021', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:45:20', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651348948648005', '651348948627525', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:45:41', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349036015685', '651349035991109', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:46:03', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349130428485', '651349130403909', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:46:26', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349217087557', '651349217067077', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:46:47', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349294194757', '651349294174277', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:47:06', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349364076613', '651349364060229', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:47:23', '1', '2025-03-17 09:35:42');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349446131781', '651349446111301', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:47:43', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349520019525', '651349519933509', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:48:01', '1', '2025-03-15 06:14:20');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349597970501', '651349597933637', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:48:20', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349686485061', '651349686448197', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:48:42', '1', '2025-03-15 06:14:20');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349766541381', '651349766484037', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:49:01', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349844312133', '651349844291653', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:49:20', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651349942562885', '651349942534213', NULL, '01', 1, 2, 7, 2, 0, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:49:44', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651350040891461', '651350040875077', NULL, '01', 1, 2, 7, 2, 1, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:50:08', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651350126710853', '651350126686277', NULL, '01', 1, 2, 7, 2, 1, '-1', '649213992497221', '10000', '1', NULL, '2025-03-05 06:50:29', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('654838048714821', '654838048682053', NULL, '01', 1, 2, 7, 1, 0, '-1', NULL, '10000', '1', NULL, '2025-03-15 03:22:52', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('654838425428037', '654838425395269', NULL, '01', 1, 2, 7, 1, 0, '-1', NULL, '10000', '1', NULL, '2025-03-15 03:24:24', '1', '2025-03-18 04:36:29');
INSERT INTO `charger_gun` (`id`, `charger_id`, `parking_id`, `no`, `speed`, `current`, `power`, `back`, `slot`, `state`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('654838586576965', '654838586552389', NULL, '01', 1, 2, 7, NULL, NULL, '-1', NULL, '10000', '1', NULL, '2025-03-15 03:25:04', '1', '2025-03-15 06:14:10');
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `scope` (`scope`) USING BTREE,
  KEY `apply` (`apply`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
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
  KEY `state` (`state`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE,
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of customer
-- ----------------------------
BEGIN;
INSERT INTO `customer` (`id`, `user_name`, `password`, `nick_name`, `type`, `mobile`, `avatar`, `sex`, `balance_amount`, `give_amount`, `quota_amount`, `status`, `login_ip`, `login_date`, `physical_card_no`, `logical_card_no`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1', 'admin', '2222', '测试用户', 'Wechat', NULL, NULL, '3', 0.00, 0.00, NULL, 0, '127.0.0.1', '2024-09-25 11:04:23', '10000', '20000', NULL, '10000', NULL, NULL, '2024-09-25 11:04:43', '1', '2024-10-10 00:57:06');
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
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
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `entity_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `scope` int DEFAULT NULL,
  `state` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of customer_notify
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for dcam
-- ----------------------------
DROP TABLE IF EXISTS `dcam`;
CREATE TABLE `dcam` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `station_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '场站id',
  `product_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备名称',
  `dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备dn',
  `status` int DEFAULT NULL COMMENT '启用/停用',
  `secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备密钥',
  `online` int DEFAULT NULL COMMENT '设备状态',
  `online_time` bigint DEFAULT NULL COMMENT '设备在线时间',
  `offline_time` bigint DEFAULT NULL COMMENT '设备离线时间',
  `price_park_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '占位计费规则',
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `park_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of dcam
-- ----------------------------
BEGIN;
INSERT INTO `dcam` (`id`, `station_id`, `product_key`, `name`, `dn`, `status`, `secret`, `online`, `online_time`, `offline_time`, `price_park_id`, `agent_id`, `tenant_id`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('1', '651348387233861', 'rf', 'RF2-104', '417151c4-8a4d6079', 1, '123456', -1, 1739961607728, 1736762931697, '586997419884613', '649213992497221', '10000', NULL, '1', NULL, '2025-01-28 09:39:53', '1', '2025-03-15 06:14:20', NULL);
INSERT INTO `dcam` (`id`, `station_id`, `product_key`, `name`, `dn`, `status`, `secret`, `online`, `online_time`, `offline_time`, `price_park_id`, `agent_id`, `tenant_id`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('2', '651348387233861', 'rf', 'RF2-101', 'ef5b7f4a-a149da78', 1, '123456', -1, 1739961607736, 1736762937397, '586997419884613', '649213992497221', '10000', NULL, '1', NULL, '2025-01-17 09:39:57', '1', '2025-03-15 06:14:20', NULL);
INSERT INTO `dcam` (`id`, `station_id`, `product_key`, `name`, `dn`, `status`, `secret`, `online`, `online_time`, `offline_time`, `price_park_id`, `agent_id`, `tenant_id`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('633302705143877', '651348387233861', 'rf', 'RF2-108', 'ee30ef2d-0252b8e3', 1, '123456', -1, 1739961607773, 1736762931647, '586997419884613', '649213992497221', '10000', NULL, '1', NULL, '2025-01-13 06:55:20', '1', '2025-03-15 06:14:20', NULL);
INSERT INTO `dcam` (`id`, `station_id`, `product_key`, `name`, `dn`, `status`, `secret`, `online`, `online_time`, `offline_time`, `price_park_id`, `agent_id`, `tenant_id`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('633303631740997', '651348387233861', 'rf', 'RF2-105', '49f6788f-aaae2325', 1, '123456', -1, 1739961607751, 1736762931719, '586997419884613', '649213992497221', '10000', NULL, '1', NULL, '2025-01-13 06:59:06', '1', '2025-03-15 06:14:20', NULL);
INSERT INTO `dcam` (`id`, `station_id`, `product_key`, `name`, `dn`, `status`, `secret`, `online`, `online_time`, `offline_time`, `price_park_id`, `agent_id`, `tenant_id`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('633305288577093', '651348387233861', 'rf', 'RF2-109', 'df983aff-518921f1', 1, '123456', -1, 1739961607744, 1736762931681, '586997419884613', '649213992497221', '10000', NULL, '1', NULL, '2025-01-13 07:05:51', '1', '2025-03-15 06:14:20', NULL);
INSERT INTO `dcam` (`id`, `station_id`, `product_key`, `name`, `dn`, `status`, `secret`, `online`, `online_time`, `offline_time`, `price_park_id`, `agent_id`, `tenant_id`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('633305705484357', '651348387233861', 'rf', 'RF2-106', 'c6f6446b-f5f5373d', 1, '123456', -1, 1739961607759, 1736762935294, '586997419884613', '649213992497221', '10000', NULL, '1', NULL, '2025-01-13 07:07:33', '1', '2025-03-15 06:14:20', NULL);
INSERT INTO `dcam` (`id`, `station_id`, `product_key`, `name`, `dn`, `status`, `secret`, `online`, `online_time`, `offline_time`, `price_park_id`, `agent_id`, `tenant_id`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('633306538430533', '651348387233861', 'rf', 'RF2-107', 'c1ea6750-ad8a5657', 1, '123456', -1, 1739961607395, 1736762933304, '586997419884613', '649213992497221', '10000', NULL, '1', NULL, '2025-01-13 07:10:56', '1', '2025-03-15 06:14:20', NULL);
INSERT INTO `dcam` (`id`, `station_id`, `product_key`, `name`, `dn`, `status`, `secret`, `online`, `online_time`, `offline_time`, `price_park_id`, `agent_id`, `tenant_id`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('633307585851461', '651348387233861', 'rf', 'RF2-102', '88474118-389f1f8b', 1, '123456', -1, 1739961607779, 1736762935302, '586997419884613', '649213992497221', '10000', NULL, '1', NULL, '2025-01-13 07:15:12', '1', '2025-03-15 06:14:20', NULL);
INSERT INTO `dcam` (`id`, `station_id`, `product_key`, `name`, `dn`, `status`, `secret`, `online`, `online_time`, `offline_time`, `price_park_id`, `agent_id`, `tenant_id`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('633308275351621', '651348387233861', 'rf', 'RF2-103', '87afae74-e7d646d1', 1, '123456', -1, 1739961607766, 1736762935406, '586997419884613', '649213992497221', '10000', NULL, '1', NULL, '2025-01-13 07:18:00', '1', '2025-03-15 06:14:20', NULL);
COMMIT;

-- ----------------------------
-- Table structure for dcam_parking
-- ----------------------------
DROP TABLE IF EXISTS `dcam_parking`;
CREATE TABLE `dcam_parking` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `dcam_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '车位相机id',
  `parking_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '车位id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of dcam_parking
-- ----------------------------
BEGIN;
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632279251185733', '1', '632278813716549', '07车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-10 09:30:54', '1', '2025-01-10 09:30:54');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632279284576325', '1', '632278857273413', '08车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-10 09:31:02', '1', '2025-01-10 09:31:02');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632279411417157', '2', '632278567116869', '01车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-10 09:31:33', '1', '2025-01-10 09:31:33');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632279440048197', '2', '632278605807685', '02车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-10 09:31:40', '1', '2025-01-10 09:31:40');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632279570423877', '632275576586309', '630763194732613', '02车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-10 09:32:11', '1', '2025-01-10 09:32:11');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('632502340902981', '632275576586309', '630763194732613', '01车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-11 00:38:39', '1', '2025-01-11 00:38:39');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633303767097413', '633303631740997', '632278902816837', '09车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 06:59:40', '1', '2025-01-13 06:59:40');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633303801987141', '633303631740997', '632278931173445', '10车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 06:59:48', '1', '2025-01-13 06:59:48');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633304465186885', '633302705143877', '633304347041861', '15车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 07:02:30', '1', '2025-01-13 07:02:30');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633304500768837', '633302705143877', '633304369848389', '16车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 07:02:39', '1', '2025-01-13 07:02:39');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633305808650309', '633305705484357', '633304251904069', '11车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 07:07:58', '1', '2025-01-13 07:07:58');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633305839583301', '633305705484357', '633304276906053', '12车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 07:08:05', '1', '2025-01-13 07:08:05');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633307394199621', '633306538430533', '633304301199429', '13车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 07:14:25', '1', '2025-01-13 07:14:25');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633307426734149', '633306538430533', '633304324767813', '14车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 07:14:33', '1', '2025-01-13 07:14:33');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633307778920517', '633307585851461', '632278664446021', '03车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 07:15:59', '1', '2025-01-13 07:15:59');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633307812003909', '633307585851461', '632278711525445', '04车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 07:16:07', '1', '2025-01-13 07:16:07');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633308402425925', '633308275351621', '632278753599557', '05车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 07:18:31', '1', '2025-01-13 07:18:31');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633308433883205', '633308275351621', '632278785519685', '06车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-13 07:18:39', '1', '2025-01-13 07:18:39');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633968363077701', '633305288577093', '633966004551749', '17车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-15 04:03:54', '1', '2025-01-15 04:03:54');
INSERT INTO `dcam_parking` (`id`, `dcam_id`, `parking_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('633968395313221', '633305288577093', '633966040547397', '18车位', NULL, NULL, NULL, '10000', '1', NULL, '2025-01-15 04:04:02', '1', '2025-01-15 04:04:02');
COMMIT;

-- ----------------------------
-- Table structure for instant
-- ----------------------------
DROP TABLE IF EXISTS `instant`;
CREATE TABLE `instant` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `arrival_amount` decimal(19,2) DEFAULT NULL,
  `bank_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `paid_amount` decimal(19,2) DEFAULT NULL,
  `pay_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `payment_identifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payment_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `recharge_type` int DEFAULT NULL,
  `refund_locked` int DEFAULT NULL,
  `refunded_amount` decimal(19,2) DEFAULT NULL,
  `state` int DEFAULT NULL,
  `topup_amount` decimal(19,2) DEFAULT NULL,
  `topup_time` datetime DEFAULT NULL,
  `trade_state_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trade_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tran_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of instant
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ledger
-- ----------------------------
DROP TABLE IF EXISTS `ledger`;
CREATE TABLE `ledger` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分帐类型',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户id',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `dealer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tran_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流水id',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单号',
  `order_tran_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备id',
  `charger_dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '充电桩号',
  `gun_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '充电枪号',
  `station_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `station_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `station_address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `start_time` datetime DEFAULT NULL COMMENT '开始充电时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束充电时间',
  `total_quantity` decimal(10,4) DEFAULT NULL,
  `total_amount` decimal(10,4) DEFAULT NULL,
  `charge_minute` int DEFAULT NULL,
  `settled_amount` decimal(19,2) DEFAULT NULL COMMENT '结算的金额',
  `settled_elec_amount` decimal(19,2) DEFAULT NULL COMMENT '结算的电费金额',
  `settled_service_amount` decimal(10,2) DEFAULT NULL COMMENT '服务费结算总额',
  `settled_park_amount` decimal(19,2) DEFAULT NULL COMMENT '结算的占位费金额',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '分成金额',
  `percent` decimal(19,2) DEFAULT NULL COMMENT '结算的分成占比',
  `ledger_settle_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结算id',
  `ledger_settle_dealer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结算id',
  `state` int DEFAULT NULL COMMENT '1启用/0停用',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `dealer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `agent_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `tran_id` (`tran_id`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充电订单表';

-- ----------------------------
-- Records of ledger
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ledger_settle
-- ----------------------------
DROP TABLE IF EXISTS `ledger_settle`;
CREATE TABLE `ledger_settle` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `tran_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流水id',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '分成结算金额',
  `state` int DEFAULT NULL COMMENT '状态',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `dealer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `tran_id` (`tran_id`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充电订单表';

-- ----------------------------
-- Records of ledger_settle
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for ledger_settle_dealer
-- ----------------------------
DROP TABLE IF EXISTS `ledger_settle_dealer`;
CREATE TABLE `ledger_settle_dealer` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分帐类型',
  `ledger_settle_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tran_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流水id',
  `dealer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `agent_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL COMMENT '分成结算金额',
  `settle_time` datetime DEFAULT NULL,
  `paid_time` datetime DEFAULT NULL,
  `state` int DEFAULT NULL COMMENT '1启用/0停用',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `dealer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `tran_id` (`tran_id`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充电订单表';

-- ----------------------------
-- Records of ledger_settle_dealer
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of map_config
-- ----------------------------
BEGIN;
INSERT INTO `map_config` (`id`, `name`, `identifier`, `properties`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1', '高德地图', 'Amap', '{\"apiKey\":\"a6347f91e48a9830a5005eefad6fbff1\",\"securityJsCode\":\"df23e76ff4be248ce583def2a848cc63\"}', 1, '10000', 1, 1, '2024-12-13 17:37:14', 1, '2025-01-18 03:21:03');
COMMIT;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告ID',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `notice_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告内容',
  `notice_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告标题',
  `notice_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告类型（1通知 2公告）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告状态（0正常 1关闭）',
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告内容',
  `state` int DEFAULT NULL COMMENT '公告状态',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告标题',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告类型（1通知 2公告）',
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
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
INSERT INTO `notify_config` (`id`, `name`, `identifier`, `properties`, `status`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1', '充电完成通知', 'ChargeFinish', '{\"isSendSms\":\"N\",\"smsConfigId\":\"1\",\"smsTemplateId\":\"\",\"smsContent\":\"eeefffff\",\"isSendPush\":\"Y\",\"pushWechatTemplateId\":\"ssWCdtlHZJ9a2HLpUiWHk5H-fjvVY8WBwx50LBUaNtQ\",\"pushAppTitle\":\"eeeeeee\",\"pushAppContent\":\"eeeeeefff\"}', 1, NULL, '10000', '1', NULL, '2025-02-12 21:37:28', '1', '2025-02-25 10:36:50');
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
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (31, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'0', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (5756, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'1', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (9789, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'0', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (56856, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'1', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (67567, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'1', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (90890, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'1', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (151515, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'0', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (1231312, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'1', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (4324234, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'1', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (4534346, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'0', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (4534636, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'1', 1683816661690);
INSERT INTO `notify_message` (`id`, `content`, `create_at`, `message_type`, `status`, `update_at`) VALUES (64432342, '你的设备【热水器】温度过高', 1683816661690, 'alert', b'1', 1683816661690);
COMMIT;

-- ----------------------------
-- Table structure for oper_log
-- ----------------------------
DROP TABLE IF EXISTS `oper_log`;
CREATE TABLE `oper_log` (
  `id` bigint NOT NULL COMMENT '日志主键',
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作模块',
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
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `notify` int DEFAULT NULL,
  `charge_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `charge_pay_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `charge_start_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `charge_stop_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `tran_id` (`tran_id`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE,
  KEY `price_id` (`price_id`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE,
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
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `discount_quota_amount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
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
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `tran_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流水号',
  `station_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '场站id',
  `station_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '场站名称',
  `station_address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `parking_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '车位id',
  `parking_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parking_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `car_plate` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '车牌',
  `car_plate_type` int DEFAULT NULL COMMENT '车牌类型',
  `in_time` datetime DEFAULT NULL COMMENT '入场时间',
  `in_bg_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'inBgImage',
  `in_feture_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'inFetureImage',
  `out_time` datetime DEFAULT NULL COMMENT '出场时间',
  `out_bg_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'outBgImage',
  `out_feture_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'outFetureImage',
  `state` int DEFAULT NULL COMMENT '状态',
  `deal` int DEFAULT NULL COMMENT '分成',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `dealer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dcam_dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dcam_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `settle` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `tran_id` (`tran_id`) USING BTREE,
  KEY `user_name` (`parking_id`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='车位停放记录表';

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
  `station_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '充电场ID',
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '场站名称',
  `no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编号',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `parking_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '车位id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `station_id` (`station_id`,`no`) USING BTREE,
  KEY `station_id_2` (`station_id`) USING BTREE,
  KEY `no` (`no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of parking
-- ----------------------------
BEGIN;
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('632278567116869', '632275846434885', '{\"zh_CN\":\"01车位\",\"en_US\":null,\"ru_RU\":\"\"}', '01', '01车位', NULL, '10000', '1', NULL, '2025-01-10 09:28:07', '1', '2025-01-10 09:28:07', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('632278605807685', '632275846434885', '{\"zh_CN\":\"02车位\",\"en_US\":null,\"ru_RU\":\"\"}', '02', '01车位', NULL, '10000', '1', NULL, '2025-01-10 09:28:16', '1', '2025-01-10 09:28:16', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('632278664446021', '632275846434885', '{\"zh_CN\":\"03车位\",\"en_US\":null,\"ru_RU\":\"\"}', '03', '03车位', NULL, '10000', '1', NULL, '2025-01-10 09:28:30', '1', '2025-01-10 09:28:30', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('632278711525445', '632275846434885', '{\"zh_CN\":\"04车位\",\"en_US\":null,\"ru_RU\":\"\"}', '04', '', NULL, '10000', '1', NULL, '2025-01-10 09:28:42', '1', '2025-01-10 09:28:42', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('632278753599557', '632275846434885', '{\"zh_CN\":\"05车位\",\"en_US\":null,\"ru_RU\":\"\"}', '05', NULL, NULL, '10000', '1', NULL, '2025-01-10 09:28:52', '1', '2025-01-10 09:28:52', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('632278785519685', '632275846434885', '{\"zh_CN\":\"06车位\",\"en_US\":null,\"ru_RU\":\"\"}', '06', NULL, NULL, '10000', '1', NULL, '2025-01-10 09:29:00', '1', '2025-01-10 09:29:00', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('632278813716549', '632275846434885', '{\"zh_CN\":\"07车位\",\"en_US\":null,\"ru_RU\":\"\"}', '07', NULL, NULL, '10000', '1', NULL, '2025-01-10 09:29:07', '1', '2025-01-10 09:29:07', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('632278857273413', '632275846434885', '{\"zh_CN\":\"08车位\",\"en_US\":null,\"ru_RU\":\"\"}', '08', '08车位', NULL, '10000', '1', NULL, '2025-01-10 09:29:17', '1', '2025-01-10 09:29:17', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('632278902816837', '632275846434885', '{\"zh_CN\":\"09车位\",\"en_US\":null,\"ru_RU\":\"\"}', '09', NULL, NULL, '10000', '1', NULL, '2025-01-10 09:29:29', '1', '2025-01-10 09:29:29', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('632278931173445', '632275846434885', '{\"zh_CN\":\"10车位\",\"en_US\":null,\"ru_RU\":\"\"}', '10', NULL, NULL, '10000', '1', NULL, '2025-01-10 09:29:35', '1', '2025-01-10 09:29:35', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('633304251904069', '632275846434885', '{\"zh_CN\":\"11车位\",\"en_US\":null,\"ru_RU\":\"\"}', '11', NULL, NULL, '10000', '1', NULL, '2025-01-13 07:01:38', '1', '2025-01-13 07:01:38', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('633304276906053', '632275846434885', '{\"zh_CN\":\"12车位\",\"en_US\":null,\"ru_RU\":\"\"}', '12', NULL, NULL, '10000', '1', NULL, '2025-01-13 07:01:44', '1', '2025-01-13 07:01:44', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('633304301199429', '632275846434885', '{\"zh_CN\":\"13车位\",\"en_US\":null,\"ru_RU\":\"\"}', '13', NULL, NULL, '10000', '1', NULL, '2025-01-13 07:01:50', '1', '2025-01-13 07:01:50', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('633304324767813', '632275846434885', '{\"zh_CN\":\"14车位\",\"en_US\":null,\"ru_RU\":\"\"}', '14', NULL, NULL, '10000', '1', NULL, '2025-01-13 07:01:56', '1', '2025-01-13 07:01:56', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('633304347041861', '632275846434885', '{\"zh_CN\":\"15车位\",\"en_US\":null,\"ru_RU\":\"\"}', '15', NULL, NULL, '10000', '1', NULL, '2025-01-13 07:02:01', '1', '2025-01-13 07:02:01', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('633304369848389', '632275846434885', '{\"zh_CN\":\"16车位\",\"en_US\":null,\"ru_RU\":\"\"}', '16', NULL, NULL, '10000', '1', NULL, '2025-01-13 07:02:07', '1', '2025-01-13 07:02:07', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('633966004551749', '632275846434885', '{\"zh_CN\":\"17车位\",\"en_US\":null,\"ru_RU\":\"\"}', '17', NULL, NULL, '10000', '1', NULL, '2025-01-15 03:54:19', '1', '2025-01-15 03:54:19', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('633966040547397', '632275846434885', '{\"zh_CN\":\"18车位\",\"en_US\":null,\"ru_RU\":\"\"}', '18', NULL, NULL, '10000', '1', NULL, '2025-01-15 03:54:27', '1', '2025-01-15 03:54:27', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643220999901253', '594065961017413', '{\"zh_CN\":\"01车位\",\"en_US\":null,\"ru_RU\":\"\"}', '01', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:32:59', '1', '2025-02-10 07:32:59', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643221038501957', '594065961017413', '{\"zh_CN\":\"02车位\",\"en_US\":null,\"ru_RU\":\"\"}', '02', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:33:08', '1', '2025-02-10 07:33:08', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643221073670213', '594065961017413', '{\"zh_CN\":\"03车位\",\"en_US\":null,\"ru_RU\":\"\"}', '03', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:33:17', '1', '2025-02-10 07:33:17', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643221098655813', '594065961017413', '{\"zh_CN\":\"04车位\",\"en_US\":null,\"ru_RU\":\"\"}', '04', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:33:23', '1', '2025-02-10 07:33:23', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643221129187397', '594065961017413', '{\"zh_CN\":\"05车位\",\"en_US\":null,\"ru_RU\":\"\"}', '05', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:33:30', '1', '2025-02-10 07:33:30', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643221155786821', '594065961017413', '{\"zh_CN\":\"06车位\",\"en_US\":null,\"ru_RU\":\"\"}', '06', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:33:37', '1', '2025-02-10 07:33:37', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643221180158021', '594065961017413', '{\"zh_CN\":\"07车位\",\"en_US\":null,\"ru_RU\":\"\"}', '07', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:33:43', '1', '2025-02-10 07:33:43', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643221207101509', '594065961017413', '{\"zh_CN\":\"08车位\",\"en_US\":null,\"ru_RU\":\"\"}', '08', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:33:50', '1', '2025-02-10 07:33:50', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643221249716293', '594065961017413', '{\"zh_CN\":\"09车位\",\"en_US\":null,\"ru_RU\":\"\"}', '09', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:34:00', '1', '2025-02-10 07:34:00', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643221277282373', '594065961017413', '{\"zh_CN\":\"10车位\",\"en_US\":null,\"ru_RU\":\"\"}', '10', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:34:07', '1', '2025-02-10 07:34:07', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643222095056965', '594065961017413', '{\"zh_CN\":\"测试车位\",\"en_US\":null,\"ru_RU\":\"\"}', 'test', NULL, NULL, '10000', '1', NULL, '2025-02-10 07:37:26', '1', '2025-02-10 07:37:26', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643288816558149', '617007724576837', '{\"zh_CN\":\"01车位\",\"en_US\":null,\"ru_RU\":\"\"}', '01', NULL, NULL, '10000', '1', NULL, '2025-02-10 12:08:56', '1', '2025-02-10 12:08:56', NULL);
INSERT INTO `parking` (`id`, `station_id`, `name`, `no`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `parking_id`) VALUES ('643288850960453', '617007724576837', '{\"zh_CN\":\"02车位\",\"en_US\":null,\"ru_RU\":\"\"}', '02', NULL, NULL, '10000', '1', NULL, '2025-02-10 12:09:04', '1', '2025-02-10 12:09:04', NULL);
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `identifier` (`identifier`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of payment
-- ----------------------------
BEGIN;
INSERT INTO `payment` (`id`, `name`, `identifier`, `description`, `no`, `status`, `is_default`, `img`, `properties`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('586157946454081', '{\"zh_CN\":\"微信支付\",\"en_US\":\"Test\",\"ru_RU\":\"test\"}', 'Wechat', '{\"zh_CN\":\"推荐的支付方式\",\"en_US\":\"Test\",\"ru_RU\":\"test\"}', '1', 1, 1, NULL, '{\"appId\":\"wx7fd1602b884b4f31\",\"merchantId\":\"1643766678\",\"merchantSerialNumber\":\"6933BC1446D8356339E3941F7AD394B72D0B892F\",\"apiV3Key\":\"mkv9x4ltjkikc8zdmr6qif10uzv6u6eo\",\"privateKey\":\"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDkBkZ8NL3YOwzj\\nG4NYdpzXeWZkivdcDft3QQhJYkvOZAB6KHSZUHRdhsH/BrYbhIr/yif5gLpNxN61\\nQ02n+JG70Fe6JMCC9eetxXv9TGi7ZmYwxgVxSYF0MJ/L8VnlLW83MemtIOrD0+sC\\nlKo8mE1MCh4v0SmDfOu/bqLUvyXZP4CC/yub0Yk3hGlh6zajVDgA5nOXrlIt7o2Z\\ngewfZu8jZ15vlJqGJSSTpyuVBJcGNyB7JMACd5jKDhmiYvUx0pPx6WvzseS/0BbX\\n6kWKEHF3xvsZ3+twXzvIqvgNSG7lF4wZDNpn1keyWZMeB0HHSY0rW37ot8HA7YOe\\nCjSIgRTzAgMBAAECggEBAIgONoBl9+DNWyMnh3eBTpRDG5ViVbXXofPGGeuvewvr\\nH13ERc408zna7mX82ywQKd/1/2QgKwm8LiZ5whw3dL1b0vx+cV+y2koVS6YXsWYf\\n81mmzhiKg3OQj8hgegME2RdV8Ngci/fMuq0Ph4QgW/7DCr/9TYfnQcM3gHDwnOTC\\nnte5eO17U+fHA/t7hR7jq5rTxhbWa7deEhSVk/yfQJZsqjW/DQe3xcAk28Ap+WP9\\nnD8ZsbG0OBicMINEQJIrLm5FI4KjTY8ARjBV0t8DgyF/Kf3T6yqtuWP433tCrvRA\\nQyVi5cMf+Yj1DLaKgAixsf0VxkPTOtCx5wSgdRgykAECgYEA8qOA1OZkz5ST/qlh\\nsWB9SDJYL0vrboIQw1Xtoqhb0dCXnA51onBZomHvMqbnL5PCHra99DJtJbuP4iO6\\nsm3DcI//9pTsPnteLj2bqVcxUie3UJbjMsTrv/fc6ZKIZ+VttxlrjRe65f3OQFpy\\nyZpjvPqB2hCVCQtxPlwlXvGEl2ECgYEA8JTBRG/zNdhkm7nMIqBs3WGfpg89oqNs\\nLuhmWhrzQCq2MnEw958w0Jjt1r/9LzYunaa4LcfU9vP20ya+cSBeUo+pqAQ6BXd4\\neUDhO+e6BzA0CuXho0+Hxx389dZIwlgt3mOEvO7dZMoGyDamIOaaAwAycmYBNitY\\ntgbEnRMqUNMCgYB/zNzzxnJ8355f+Slquw7h8xp9tvhHmZxotp7XGORcDAl++iK9\\nr32WyQyrWjvU1Hffo5NDxze0MymjC/yvNJ9Hb6F7aI9SjbpakrqlpDZnNmIYwZDj\\n0dL2pwAlyresOazjTzFV/JTFxTxYqwtg4r/77tpsWGWXgP5hwTkx2/ZhoQKBgDU+\\nNqeWvxSjl2B/dhYTuwQjntl5dgKQeFjJRh2a6UrCKSBwvI2QxZTkw35LbxULwLVT\\nOF+pYhBlXbLNWEhhCX7sHHf6t3yOPDNZkH9ZgWol2N6wkYc7CqmekWU2z1NzgHM/\\nQxktJAIyU93reki9wEf5uzBmMs5yOWfPh6PbofnRAoGAJOQgOBQ0A6I4+brVxRj9\\nSGnyr+ICho9qyoesHSaOVauYBoMQIRPBEJUZx0nUKfTae3qxQc1ONDDlV/qNsOia\\nmWSOxZsNT11q/ihAWj8L+q+gAw8u3KoWxYZgfSFpfYMRCT7murNJGBlx+62bbGW7\\nSbpvU3VX+Tqg5VGNf0ZZOxc=\\n-----END PRIVATE KEY-----\"}', '2222', '10000', '1', NULL, '2024-09-02 01:42:49', '1', '2025-02-28 15:44:04');
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
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
INSERT INTO `plugin_info` (`id`, `plugin_id`, `protocol_key`, `type`, `name`, `protocol`, `config`, `config_schema`, `version`, `file`, `script`, `deploy_type`, `description`, `state`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2, 'ykc-tcp-plugin', 'ykc-tcp', NULL, '云快充', NULL, '{\"host\": \"0.0.0.0\", \"port\": \"6166\"}', '[{\"id\":\"host\",\"name\":\"绑定ip\",\"type\":\"text\",\"value\":\"0.0.0.0\",\"desc\":\"tcp绑定ip，默认为0.0.0.0\"},{\"id\":\"port\",\"name\":\"端口\",\"type\":\"number\",\"value\":6767,\"desc\":\"tcp端口，默认为6166\"}]', '1.0.4', 'ykc-tcp-plugin-1.0.4-repackage.jar', NULL, 'upload', '云快充插件', 'running', '10000', 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-07 16:41:07');
INSERT INTO `plugin_info` (`id`, `plugin_id`, `protocol_key`, `type`, `name`, `protocol`, `config`, `config_schema`, `version`, `file`, `script`, `deploy_type`, `description`, `state`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (3, 'vzi-mqtt-client-plugin', 'vzi-mqtt', NULL, '臻识mqtt客户端', NULL, '{\"baseUrl\":\"https://open.vzicloud.com\",\"accesskeyId\":\"KNZlPZc862kb54QQIWkTVW8WwGI0jRgR\",\"accesskeySecret\":\"nBiV2Ljbc0CVdVAplgOmgmMwlSfaNDHN\"}', '[{\"id\":\"host\",\"name\":\"绑定ip\",\"type\":\"text\",\"value\":\"0.0.0.0\",\"desc\":\"tcp绑定ip，默认为0.0.0.0\"},{\"id\":\"port\",\"name\":\"端口\",\"type\":\"number\",\"value\":6767,\"desc\":\"tcp端口，默认为6166\"}]', '1.0.4', 'vzi-mqtt-client-plugin-1.0.4-repackage.jar', NULL, 'upload', '臻识插件', 'running', '10000', 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-06 16:06:52');
COMMIT;

-- ----------------------------
-- Table structure for plugin_instance
-- ----------------------------
DROP TABLE IF EXISTS `plugin_instance`;
CREATE TABLE `plugin_instance` (
  `id` bigint NOT NULL COMMENT 'id',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `heartbeat_at` bigint DEFAULT NULL COMMENT '心跳时间',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '插件主程序所在ip',
  `main_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '插件主程序id',
  `plugin_id` bigint DEFAULT NULL COMMENT '插件id',
  `port` int NOT NULL COMMENT '插件主程序端口',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of plugin_instance
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pmsc
-- ----------------------------
DROP TABLE IF EXISTS `pmsc`;
CREATE TABLE `pmsc` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `station_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '场站id',
  `direct` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '方向',
  `product_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备名称',
  `dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备dn',
  `status` int DEFAULT NULL COMMENT '启用/停用',
  `secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备密钥',
  `online` int DEFAULT NULL COMMENT '设备状态',
  `online_time` bigint DEFAULT NULL COMMENT '设备在线时间',
  `offline_time` bigint DEFAULT NULL COMMENT '设备离线时间',
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of pmsc
-- ----------------------------
BEGIN;
INSERT INTO `pmsc` (`id`, `station_id`, `direct`, `product_key`, `name`, `dn`, `status`, `secret`, `online`, `online_time`, `offline_time`, `agent_id`, `tenant_id`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('634404583202885', '623472357883973', 'In', 'rf', '33ss', '33sss', 1, 'dBTDXfyixR32zAdn', -1, NULL, NULL, '644609434873925', '10000', NULL, '1', NULL, '2025-01-16 09:38:53', '1', '2025-02-14 07:22:00');
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `no` (`no`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of price
-- ----------------------------
BEGIN;
INSERT INTO `price` (`id`, `name`, `no`, `type`, `status`, `note`, `properties`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('586997419884613', '默认计价规则', '1', 2, 1, '', '{\"fee\": [{\"name\": \"尖\", \"amount\": \"1.6145\", \"elecFee\": \"1.1145\", \"serviceFee\": \"0.5000\"}, {\"name\": \"峰\", \"amount\": \"1.6145\", \"elecFee\": \"1.1145\", \"serviceFee\": \"0.5000\"}, {\"name\": \"平\", \"amount\": \"1.2701\", \"elecFee\": \"0.7701\", \"serviceFee\": \"0.5000\"}, {\"name\": \"谷\", \"amount\": \"0.9257\", \"elecFee\": \"0.4257\", \"serviceFee\": \"0.5000\"}], \"period\": [{\"index\": 3, \"label\": \"00:00-00:30\"}, {\"index\": 3, \"label\": \"00:30-01:00\"}, {\"index\": 3, \"label\": \"01:00-01:30\"}, {\"index\": 3, \"label\": \"01:30-02:00\"}, {\"index\": 3, \"label\": \"02:00-02:30\"}, {\"index\": 3, \"label\": \"02:30-03:00\"}, {\"index\": 3, \"label\": \"03:00-03:30\"}, {\"index\": 3, \"label\": \"03:30-04:00\"}, {\"index\": 3, \"label\": \"04:00-04:30\"}, {\"index\": 3, \"label\": \"04:30-05:00\"}, {\"index\": 3, \"label\": \"05:00-05:30\"}, {\"index\": 3, \"label\": \"05:30-06:00\"}, {\"index\": 3, \"label\": \"06:00-06:30\"}, {\"index\": 3, \"label\": \"06:30-07:00\"}, {\"index\": 2, \"label\": \"07:00-07:30\"}, {\"index\": 2, \"label\": \"07:30-08:00\"}, {\"index\": 0, \"label\": \"08:00-08:30\"}, {\"index\": 0, \"label\": \"08:30-09:00\"}, {\"index\": 0, \"label\": \"09:00-09:30\"}, {\"index\": 0, \"label\": \"09:30-10:00\"}, {\"index\": 0, \"label\": \"10:00-10:30\"}, {\"index\": 0, \"label\": \"10:30-11:00\"}, {\"index\": 0, \"label\": \"11:00-11:30\"}, {\"index\": 2, \"label\": \"11:30-12:00\"}, {\"index\": 2, \"label\": \"12:00-12:30\"}, {\"index\": 2, \"label\": \"12:30-13:00\"}, {\"index\": 2, \"label\": \"13:00-13:30\"}, {\"index\": 2, \"label\": \"13:30-14:00\"}, {\"index\": 2, \"label\": \"14:00-14:30\"}, {\"index\": 2, \"label\": \"14:30-15:00\"}, {\"index\": 2, \"label\": \"15:00-15:30\"}, {\"index\": 2, \"label\": \"15:30-16:00\"}, {\"index\": 2, \"label\": \"16:00-16:30\"}, {\"index\": 2, \"label\": \"16:30-17:00\"}, {\"index\": 2, \"label\": \"17:00-17:30\"}, {\"index\": 2, \"label\": \"17:30-18:00\"}, {\"index\": 2, \"label\": \"18:00-18:30\"}, {\"index\": 1, \"label\": \"18:30-19:00\"}, {\"index\": 1, \"label\": \"19:00-19:30\"}, {\"index\": 1, \"label\": \"19:30-20:00\"}, {\"index\": 1, \"label\": \"20:00-20:30\"}, {\"index\": 1, \"label\": \"20:30-21:00\"}, {\"index\": 1, \"label\": \"21:00-21:30\"}, {\"index\": 1, \"label\": \"21:30-22:00\"}, {\"index\": 1, \"label\": \"22:00-22:30\"}, {\"index\": 1, \"label\": \"22:30-23:00\"}, {\"index\": 3, \"label\": \"23:00-23:30\"}, {\"index\": 3, \"label\": \"23:30-24:00\"}]}', '10000', '1', NULL, '2024-09-04 10:38:39', '1', '2024-11-21 07:01:04');
INSERT INTO `price` (`id`, `name`, `no`, `type`, `status`, `note`, `properties`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('654127040671813', '30千瓦快充计费规则', '2', 2, 1, '', '{\"fee\": [{\"name\": \"尖\", \"amount\": \"1.4945\", \"elecFee\": \"1.1145\", \"serviceFee\": \"0.3800\"}, {\"name\": \"峰\", \"amount\": \"1.4945\", \"elecFee\": \"1.1145\", \"serviceFee\": \"0.3800\"}, {\"name\": \"平\", \"amount\": \"1.1501\", \"elecFee\": \"0.7701\", \"serviceFee\": \"0.3800\"}, {\"name\": \"谷\", \"amount\": \"0.8057\", \"elecFee\": \"0.4257\", \"serviceFee\": \"0.3800\"}], \"period\": [{\"index\": 3, \"label\": \"00:00-00:30\"}, {\"index\": 3, \"label\": \"00:30-01:00\"}, {\"index\": 3, \"label\": \"01:00-01:30\"}, {\"index\": 3, \"label\": \"01:30-02:00\"}, {\"index\": 3, \"label\": \"02:00-02:30\"}, {\"index\": 3, \"label\": \"02:30-03:00\"}, {\"index\": 3, \"label\": \"03:00-03:30\"}, {\"index\": 3, \"label\": \"03:30-04:00\"}, {\"index\": 3, \"label\": \"04:00-04:30\"}, {\"index\": 3, \"label\": \"04:30-05:00\"}, {\"index\": 3, \"label\": \"05:00-05:30\"}, {\"index\": 3, \"label\": \"05:30-06:00\"}, {\"index\": 3, \"label\": \"06:00-06:30\"}, {\"index\": 3, \"label\": \"06:30-07:00\"}, {\"index\": 2, \"label\": \"07:00-07:30\"}, {\"index\": 2, \"label\": \"07:30-08:00\"}, {\"index\": 0, \"label\": \"08:00-08:30\"}, {\"index\": 0, \"label\": \"08:30-09:00\"}, {\"index\": 0, \"label\": \"09:00-09:30\"}, {\"index\": 0, \"label\": \"09:30-10:00\"}, {\"index\": 0, \"label\": \"10:00-10:30\"}, {\"index\": 0, \"label\": \"10:30-11:00\"}, {\"index\": 0, \"label\": \"11:00-11:30\"}, {\"index\": 2, \"label\": \"11:30-12:00\"}, {\"index\": 2, \"label\": \"12:00-12:30\"}, {\"index\": 2, \"label\": \"12:30-13:00\"}, {\"index\": 2, \"label\": \"13:00-13:30\"}, {\"index\": 2, \"label\": \"13:30-14:00\"}, {\"index\": 2, \"label\": \"14:00-14:30\"}, {\"index\": 2, \"label\": \"14:30-15:00\"}, {\"index\": 2, \"label\": \"15:00-15:30\"}, {\"index\": 2, \"label\": \"15:30-16:00\"}, {\"index\": 2, \"label\": \"16:00-16:30\"}, {\"index\": 2, \"label\": \"16:30-17:00\"}, {\"index\": 2, \"label\": \"17:00-17:30\"}, {\"index\": 2, \"label\": \"17:30-18:00\"}, {\"index\": 2, \"label\": \"18:00-18:30\"}, {\"index\": 1, \"label\": \"18:30-19:00\"}, {\"index\": 1, \"label\": \"19:00-19:30\"}, {\"index\": 1, \"label\": \"19:30-20:00\"}, {\"index\": 1, \"label\": \"20:00-20:30\"}, {\"index\": 1, \"label\": \"20:30-21:00\"}, {\"index\": 1, \"label\": \"21:00-21:30\"}, {\"index\": 1, \"label\": \"21:30-22:00\"}, {\"index\": 1, \"label\": \"22:00-22:30\"}, {\"index\": 1, \"label\": \"22:30-23:00\"}, {\"index\": 3, \"label\": \"23:00-23:30\"}, {\"index\": 3, \"label\": \"23:30-24:00\"}]}', '10000', '1', NULL, '2025-03-13 03:09:47', '1', '2025-03-13 03:09:47');
COMMIT;

-- ----------------------------
-- Table structure for price_park
-- ----------------------------
DROP TABLE IF EXISTS `price_park`;
CREATE TABLE `price_park` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编号',
  `before_free_minute` int DEFAULT NULL COMMENT '充电前免费时长',
  `after_free_minute` int DEFAULT NULL COMMENT '充电后免费时长',
  `period` int DEFAULT NULL COMMENT '计费周期',
  `max_amount` decimal(19,2) DEFAULT NULL COMMENT '每天封顶金额',
  `price` decimal(19,2) DEFAULT NULL COMMENT '每周期单价',
  `status` int DEFAULT NULL COMMENT '1启用/0停用',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `no` (`no`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of price_park
-- ----------------------------
BEGIN;
INSERT INTO `price_park` (`id`, `name`, `no`, `before_free_minute`, `after_free_minute`, `period`, `max_amount`, `price`, `status`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('586997419884613', '默认占位计价规则', '1', 10, 30, 1, 20.00, 2.00, 1, '', '10000', '1', NULL, '2024-09-04 10:38:39', '1', '2025-01-14 12:41:35');
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
INSERT INTO `product` (`id`, `name`, `type`, `product_key`, `product_secret`, `protocol_key`, `category`, `manufacturer`, `img`, `keep_alive_time`, `properties`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('516578994860105', '臻识RF系列车位相机', 'Dcam', 'rf', '454444', 'vzi', 'dcam', '臻识', NULL, 30, '{\"qty\":2,\"speed\":\"Slow\",\"current\":\"AC\",\"power\":7,\"protocol\":\"ykc\",\"lowBalance\":2.00}', 1, '1', NULL, '2024-11-06 07:57:35', '1', '2024-11-06 07:57:35');
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `scope` (`scope`) USING BTREE,
  KEY `apply` (`type`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
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
INSERT INTO `push_config` (`id`, `name`, `identifier`, `properties`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1', '微信小程序推送', 'Wechat', '{\"appId\":\"wx7fd1602b884b4f31\",\"appSecret\":\"7269bdb79b5691afd6e9b8dbeb401d55\"}', 1, '10000', '1', NULL, '2025-02-18 11:06:17', '1', '2025-02-18 13:39:15');
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `status` int DEFAULT NULL COMMENT '启用/停用',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `type` (`type`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值方案';

-- ----------------------------
-- Records of recharge
-- ----------------------------
BEGIN;
INSERT INTO `recharge` (`id`, `type`, `name`, `note`, `tenant_id`, `status`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('594715916664901', 4, '充值', '提示: 充值成功后系统将赠送您充值金额所对应的服务费打折额度，服务费打折额度在您的订单结算后自动扣除。服务费打折额度扣除以订单总金额为基础扣除。例如: 充值500元赠送服务费打折额度500元(5折)。充电完成后订单总金额为100元(其中服务费为40元)，将从账户中扣除100元的服务费打折额度，订单总优惠金额为20元（40元x5折=20元）。', '10000', 1, '1', NULL, '2024-09-26 06:05:18', '1', '2025-03-13 14:20:10');
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `recharge_id` (`recharge_id`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值方案';

-- ----------------------------
-- Records of recharge_item
-- ----------------------------
BEGIN;
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613292156031045', '594715916664901', NULL, 50.00, NULL, NULL, 1.00, NULL, NULL, '10000', '1', NULL, '2024-11-17 17:52:12', '1', '2024-12-04 16:08:19');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613292175417413', '594715916664901', NULL, 100.00, NULL, NULL, 0.90, NULL, NULL, '10000', '1', NULL, '2024-11-17 17:52:17', '1', '2024-11-27 12:11:55');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613292197429317', '594715916664901', NULL, 200.00, NULL, NULL, 0.85, NULL, NULL, '10000', '1', NULL, '2024-11-17 17:52:22', '654124202426437', '2025-03-14 00:55:40');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613292219453509', '594715916664901', NULL, 300.00, NULL, NULL, 0.75, NULL, NULL, '10000', '1', NULL, '2024-11-17 17:52:28', '654124202426437', '2025-03-14 00:55:58');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('613292239388741', '594715916664901', NULL, 500.00, NULL, NULL, 0.65, NULL, NULL, '10000', '1', NULL, '2024-11-17 17:52:33', '654124202426437', '2025-03-14 00:56:14');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('630755621290053', '623472357883973', 'ddd', NULL, '2222', NULL, NULL, NULL, NULL, '10000', '1', NULL, '2025-01-06 02:11:14', '1', '2025-01-06 02:11:14');
INSERT INTO `recharge_item` (`id`, `recharge_id`, `name`, `amount`, `note`, `give`, `discount`, `minus`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('654447411122245', '594715916664901', NULL, 1000.00, NULL, NULL, 0.40, NULL, NULL, '10000', '654124202426437', NULL, '2025-03-14 00:53:22', '654124202426437', '2025-03-14 00:53:53');
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
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
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236806339', '167655236814421', '167655236523866', '18791735601', 30.00, '1970-01-01 00:00:00', 10, '36', '10000', '1', NULL, '1970-01-01 00:00:00', NULL, '1970-01-01 00:00:00');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236841649', '167655236851697', '167655236523866', '18791735601', 10.00, '1970-01-01 00:00:00', 10, '38', '10000', '1', NULL, '1970-01-01 00:00:00', NULL, '1970-01-01 00:00:00');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236882385', '167655236894128', '167655236523866', '18791735601', 1.00, '1970-01-01 00:00:00', 10, '39', '10000', '1', NULL, '1970-01-01 00:00:00', NULL, '1970-01-01 00:00:00');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236924755', '167655236938651', '167655236523866', '18791735601', 300.00, '1970-01-01 00:33:44', 10, '47', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236968007', '167655236973052', '167655236523866', '18791735601', 50.00, '1970-01-01 00:33:44', 10, '61', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237001471', '167655237016245', '167655236523866', '18791735601', 50.00, '1970-01-01 00:33:44', 10, '65', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237046202', '167655237055026', '167655236523866', '18791735601', 50.00, '1970-01-01 00:33:44', 10, '95', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237088185', '167655237098269', '167655236523866', '18791735601', 200.00, '1970-01-01 00:33:44', 10, '96', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237125363', '167655237139374', '167655236523866', '18791735601', 100.00, '1970-01-01 00:33:44', 10, '97', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237166625', '167655237175739', '167655236523866', '18791735601', 100.00, '1970-01-01 00:33:44', 10, '98', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237208023', '167655237219421', '167655236523866', '18791735601', 50.00, '1970-01-01 00:33:44', 10, '99', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237245490', '167655237256690', '167655236523866', '18791735601', 300.00, '1970-01-01 00:33:44', 10, '101', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237283201', '167655237291522', '167655236523866', '18791735601', 550.00, '1970-01-01 00:33:44', 10, '102', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237323313', '16765523733168', '167655236523866', '18791735601', 100.00, '1970-01-01 00:33:45', 10, '106', '10000', '1', NULL, '1970-01-01 00:33:45', NULL, '1970-01-01 00:33:45');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655238867937', '167655238873302', '167655238646141', '15592636988', 300.00, '1970-01-01 00:33:44', 10, '48', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655244861168', '167655244878299', '167655244746869', '13992751118', 100.00, '1970-01-01 00:33:44', 10, '40', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250603056', '167655250618225', '167655250483579', '13619273115', 5000.00, '1970-01-01 00:33:44', 10, '46', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250649738', '167655250653322', '167655250483579', '13619273115', 1400.00, '1970-01-01 00:33:44', 10, '84', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250682910', '167655250693491', '167655250483579', '13619273115', 600.00, '1970-01-01 00:33:44', 10, '89', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250729611', '167655250738580', '167655250483579', '13619273115', 600.00, '1970-01-01 00:33:44', 10, '90', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655251261484', '167655251278549', '167655251182704', '18681956305', 49.14, '1970-01-01 00:33:44', 10, '41', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655251788797', '167655251799132', '167655251746711', '18691796773', 15.97, '1970-01-01 00:33:44', 10, '43', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1676552537421', '167655253755679', '167655252927533', '15384670061', 1000.00, '1970-01-01 00:33:44', 10, '45', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655254102012', '167655254115494', '16765525406367', '18809179997', 13.92, '1970-01-01 00:33:44', 10, '94', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655254202058', '167655254218479', '167655254168611', '13109171213', 49.94, '1970-01-01 00:33:44', 10, '51', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655255982412', '167655255991962', '167655255949827', '13891776694', 43.17, '1970-01-01 00:33:44', 10, '49', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655256482002', '167655256492465', '167655256448996', NULL, 35.00, '1970-01-01 00:33:44', 10, '67', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655256561756', '167655256576069', '167655256522234', '13992713513', 48.14, '1970-01-01 00:33:44', 10, '50', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('16765525748517', '167655257492934', '167655257448608', '15934232562', 33.00, '1970-01-01 00:33:44', 10, '93', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258244716', '167655258256627', '167655258163161', '15091639276', 50.00, '1970-01-01 00:33:44', 10, '72', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258367769', '167655258371629', '16765525830696', '13639570999', 50.00, '1970-01-01 00:33:44', 10, '70', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258401981', '167655258412012', '16765525830696', '13639570999', 26.36, '1970-01-01 00:33:44', 10, '71', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258501067', '167655258519926', '167655258462926', '13772644187', 46.57, '1970-01-01 00:33:44', 10, '86', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258581010', '167655258597813', '167655258542830', '17703793066', 28.94, '1970-01-01 00:33:44', 10, '87', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('16765525902843', '167655259038888', '167655258989428', '15332273395', 50.00, '1970-01-01 00:33:44', 10, '79', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655259549491', '167655259555083', '167655259503856', '15029217375', 50.00, '1970-01-01 00:33:44', 10, '81', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655261084681', '167655261091675', '167655261043390', '18291733999', 47.00, '1970-01-01 00:33:44', 10, '88', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('16765526374172', '167655263757117', '167655263709304', '13892704144', 50.00, '1970-01-01 00:33:44', 10, '100', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655264025841', '167655264031768', '167655263983901', '13892471843', 40.00, '1970-01-01 00:33:44', 10, '104', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655264547061', '167655264555150', '167655264504515', '15609255681', 36.78, '1970-01-01 00:33:44', 10, '105', '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655267426448', '167655267438329', '16765526738846', NULL, 50.00, '1970-01-01 00:33:45', 10, '110', '10000', '1', NULL, '1970-01-01 00:33:45', NULL, '1970-01-01 00:33:45');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('650780253065285', '650780253032517', '650625723420741', '18240876818', 0.72, NULL, 10, '', '10000', '1', NULL, '2025-03-03 16:11:40', '1', '2025-03-03 16:12:15');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651025096155205', '651025096142917', '611759831654469', '18992720878', 0.01, NULL, 10, '', '10000', '1', NULL, '2025-03-04 08:47:56', '1', '2025-03-04 08:48:19');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651047741689925', '651047741632581', '611759831654469', '18992720878', 0.01, NULL, 10, '', '10000', '1', NULL, '2025-03-04 10:20:05', '1', '2025-03-04 10:24:02');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651052939247685', '651052939145285', '611759831654469', '18992720878', 0.01, NULL, 10, '', '10000', '1', NULL, '2025-03-04 10:41:13', '1', '2025-03-04 10:41:28');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651059184672837', '651059184590917', '611759831654469', '18992720878', 0.03, NULL, 10, '', '10000', '1', NULL, '2025-03-04 11:06:38', '1', '2025-03-04 11:07:01');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651059622826053', '651059622809669', '611759831654469', '18992720878', 0.02, NULL, 10, '', '10000', '1', NULL, '2025-03-04 11:08:25', '1', '2025-03-04 11:08:40');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651611714555973', '651611714465861', '611759831654469', '18992720878', 0.01, NULL, 10, '', '10000', '1', NULL, '2025-03-06 00:34:53', '1', '2025-03-06 00:35:28');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('654258827157573', '654258827055173', '654258355834949', '18992733229', 0.01, NULL, 10, '', '10000', '1', NULL, '2025-03-13 12:06:01', '1', '2025-03-13 12:06:28');
INSERT INTO `refund` (`id`, `tran_id`, `customer_id`, `user_name`, `amount`, `refund_time`, `state`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('655007896281157', '655007896268869', '650625723420741', '18240876818', 33.00, NULL, 10, '余额退款', '10000', '1', NULL, '2025-03-15 14:53:59', '1', '2025-03-15 14:55:26');
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
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
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236823089', '167655236806339', NULL, '167655236832183', 30.00, 10, '17194748095197930437', '1970-01-01 00:00:00', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:00:00', NULL, '1970-01-01 00:00:00');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236863538', '167655236841649', NULL, '167655236872600', 10.00, 10, '17194748449357681584', '1970-01-01 00:00:00', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:00:00', NULL, '1970-01-01 00:00:00');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236903485', '167655236882385', NULL, '167655236915042', 1.00, 10, '17194750416428675305', '1970-01-01 00:00:00', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:00:00', NULL, '1970-01-01 00:00:00');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236948990', '167655236924755', NULL, '167655236958995', 300.00, 10, '17241444828723282921', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655236981239', '167655236968007', NULL, '167655236997037', 50.00, 10, '17265457964208266261', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237026813', '167655237001471', NULL, '167655237035328', 50.00, 10, '17265459136088779414', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237066526', '167655237046202', NULL, '167655237077553', 50.00, 10, '17341443979631714592', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237106791', '167655237088185', NULL, '167655237119147', 200.00, 10, '17341448714895634479', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('16765523714784', '167655237125363', NULL, '167655237155795', 100.00, 10, '17341719651577013508', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237188823', '167655237166625', NULL, '167655237196898', 100.00, 10, '17342543069609878894', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237223037', '167655237208023', NULL, '167655237236920', 50.00, 10, '17342548607308814588', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237266979', '167655237245490', NULL, '167655237274828', 300.00, 10, '17344334922351392173', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655237308006', '167655237283201', NULL, '167655237315466', 550.00, 10, '17346803334390227576', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('16765523734900', '167655237323313', NULL, '167655237353997', 100.00, 10, '17378151918838441529', '1970-01-01 00:33:45', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:45', NULL, '1970-01-01 00:33:45');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655238882699', '167655238867937', NULL, '167655238893590', 300.00, 10, '17241507294025335348', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655244887991', '167655244861168', NULL, '167655244895061', 100.00, 10, '17197112459096171933', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250621960', '167655250603056', NULL, '167655250635123', 5000.00, 10, '17236288601993779081', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250667397', '167655250649738', NULL, '167655250677020', 1400.00, 10, '17290798841610893516', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250708726', '167655250682910', NULL, '167655250713158', 600.00, 10, '17315869614981611887', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655250744068', '167655250729611', NULL, '167655250754601', 600.00, 10, '17315869627231821068', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655251288294', '167655251261484', NULL, '167655251295822', 49.14, 10, '17208550168535059209', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655251809272', '167655251788797', NULL, '167655251818962', 15.97, 10, '17220666503595617531', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655253768334', '1676552537421', NULL, '167655253774631', 1000.00, 10, '17235474512776737824', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655254121437', '167655254102012', NULL, '16765525413704', 13.92, 10, '17327645489705557830', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655254226219', '167655254202058', NULL, '167655254235659', 49.94, 10, '17264829736041697731', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655256002575', '167655255982412', NULL, '167655256016987', 43.17, 10, '17256088687382876056', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655256506318', '167655256482002', NULL, '167655256514197', 35.00, 10, '17265463344112563202', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655256585077', '167655256561756', NULL, '167655256597180', 48.14, 10, '17263994926653118167', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655257503121', '16765525748517', NULL, '167655257516801', 33.00, 10, '17323280612141340469', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258268987', '167655258244716', NULL, '167655258275053', 50.00, 10, '17279377121685190145', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258384837', '167655258367769', NULL, '167655258399298', 50.00, 10, '17279365859444038400', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258424119', '167655258401981', NULL, '167655258434557', 26.36, 10, '17279366035914870320', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258526430', '167655258501067', NULL, '167655258532372', 46.57, 10, '17300886143840452079', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655258606037', '167655258581010', NULL, '167655258616745', 28.94, 10, '17300888129651445766', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655259041911', '16765525902843', NULL, '167655259052892', 50.00, 10, '17284689355721255684', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655259566944', '167655259549491', NULL, '167655259579469', 50.00, 10, '17289780172542562195', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655261104335', '167655261084681', NULL, '167655261116650', 47.00, 10, '17300888768912716261', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655263765072', '16765526374172', NULL, '167655263774008', 50.00, 10, '17343110137999796444', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655264041317', '167655264025841', NULL, '167655264051283', 40.00, 10, '17350397605153856081', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655264564568', '167655264547061', NULL, '167655264577389', 36.78, 10, '17353506052011381263', '1970-01-01 00:33:44', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:44', NULL, '1970-01-01 00:33:44');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('167655267442302', '167655267426448', NULL, '167655267456524', 50.00, 10, '17400226776907470660', '1970-01-01 00:33:45', NULL, NULL, '10000', '1', NULL, '1970-01-01 00:33:45', NULL, '1970-01-01 00:33:45');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('650780255182917', '650780253065285', '650626292383813', '650780255166533', 0.72, 10, '50301702302025030479477026619', '2025-03-03 16:12:15', '支付用户零钱', NULL, '10000', '1', NULL, '2025-03-03 16:11:40', '1', '2025-03-03 16:11:40');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651025098235973', '651025096155205', '651024760651845', '651025098227781', 0.01, 10, '50302202702025030430483952588', '2025-03-04 08:48:19', '支付用户零钱', NULL, '10000', '1', NULL, '2025-03-04 08:47:56', '1', '2025-03-04 08:48:19');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651047743897669', '651047741689925', '651029544775749', '651047743860805', 0.01, 10, '50302602602025030425069600024', '2025-03-04 10:24:01', '支付用户零钱', NULL, '10000', '1', NULL, '2025-03-04 10:20:05', '1', '2025-03-04 10:24:02');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651052941529157', '651052939247685', '651052788150341', '651052941467717', 0.01, 10, '50300302572025030422489607929', '2025-03-04 10:41:28', '支付用户零钱', NULL, '10000', '1', NULL, '2025-03-04 10:41:14', '1', '2025-03-04 10:41:28');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651059186876485', '651059184672837', '651053160251461', '651059186810949', 0.03, 10, '50302402862025030431086289501', '2025-03-04 11:07:01', '工商银行借记卡9829', NULL, '10000', '1', NULL, '2025-03-04 11:06:39', '1', '2025-03-04 11:07:01');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651059624902725', '651059622826053', '651053160251461', '651059624890437', 0.02, 10, '50302402862025030400997861161', '2025-03-04 11:08:40', '工商银行借记卡9829', NULL, '10000', '1', NULL, '2025-03-04 11:08:26', '1', '2025-03-04 11:08:40');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('651611716771909', '651611714555973', '651059986182213', '651611716706373', 0.01, 10, '50301402852025030648264855560', '2025-03-06 00:35:28', '工商银行借记卡9829', NULL, '10000', '1', NULL, '2025-03-06 00:34:54', '1', '2025-03-06 00:35:28');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('654258829516869', '654258827157573', '654258527670341', '654258829406277', 0.01, 10, '50300802642025031306253989569', '2025-03-13 12:06:28', '支付用户零钱', NULL, '10000', '1', NULL, '2025-03-13 12:06:02', '1', '2025-03-13 12:06:28');
INSERT INTO `refund_balance` (`id`, `refund_id`, `topup_id`, `tran_id`, `amount`, `state`, `pay_id`, `success_time`, `user_received_account`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('655007898382405', '655007896281157', '650626292383813', '655007898370117', 33.00, 10, '50301702302025031566512462487', '2025-03-15 14:55:25', '支付用户零钱', NULL, '10000', '1', NULL, '2025-03-15 14:54:00', '1', '2025-03-15 14:55:26');
COMMIT;

-- ----------------------------
-- Table structure for rule_info
-- ----------------------------
DROP TABLE IF EXISTS `rule_info`;
CREATE TABLE `rule_info` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '规则id',
  `actions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '动作',
  `create_at` bigint DEFAULT NULL COMMENT '创建时间',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `filters` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '过滤器',
  `listeners` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '监听器',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '规则名称',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '规则类型',
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of rule_info
-- ----------------------------
BEGIN;
INSERT INTO `rule_info` (`id`, `actions`, `create_at`, `desc`, `filters`, `listeners`, `name`, `state`, `type`, `uid`) VALUES ('2820c218-660e-48ff-a234-c7b6793a5bb8', '[]', 1649167998895, 'test', '[]', '[]', '测试场景1', 'stopped', 'scene', '1');
INSERT INTO `rule_info` (`id`, `actions`, `create_at`, `desc`, `filters`, `listeners`, `name`, `state`, `type`, `uid`) VALUES ('2c10229b-dcb2-439e-b411-5425b49657a1', '[]', 1652515471242, '开关插座开关状态推送', '[]', '[]', '小度设备属性更新推送', 'running', 'flow', '1');
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
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
INSERT INTO `sms_config` (`id`, `name`, `identifier`, `tpl_type`, `properties`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1', 'Nikita', 'Nikita', 'Content', '{\"login\":\"redrocketcharge\",\"password\":\"Shenshu03\",\"sender\":\"SMSPRO.KG\",\"test\":true}', 1, '10000', 1, NULL, NULL, 1, '2025-02-18 03:28:28');
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
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sms_record
-- ----------------------------
BEGIN;
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('634479315968069', 'Nikita', 'Login', '18992720878', '{\"code\":7614}', 1, '10000', 1, NULL, '2025-01-16 14:42:59', 1, '2025-01-16 14:43:00');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('634834466828357', 'Nikita', 'Login', '18992720878', '{\"code\":9452}', 1, '10000', 1, NULL, '2025-01-17 14:48:06', 1, '2025-01-17 14:48:09');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('634836570771525', 'Nikita', 'Login', '18992720878', '{\"code\":1751}', 1, '10000', 1, NULL, '2025-01-17 14:56:39', 1, '2025-01-17 14:56:40');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644027953406021', 'Nikita', 'Login', '18992720878', '{\"code\":8533}', 1, NULL, 1, NULL, '2025-02-12 14:16:29', 1, '2025-02-12 14:16:34');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644027993444421', 'Nikita', 'Login', '18992720878', '{\"code\":3232}', 1, NULL, 1, NULL, '2025-02-12 14:16:39', 1, '2025-02-12 14:16:40');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644028695674949', 'Nikita', 'Login', '18992720878', '{\"code\":6959}', 1, NULL, 1, NULL, '2025-02-12 14:19:30', 1, '2025-02-12 14:19:31');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644028752891973', 'Nikita', 'Login', '18992720878', '{\"code\":8282}', 1, NULL, 1, NULL, '2025-02-12 14:19:44', 1, '2025-02-12 14:19:45');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644031082729541', 'Nikita', 'Login', '18992720878', '{\"code\":5431}', 1, NULL, 1, NULL, '2025-02-12 14:29:13', 1, '2025-02-12 14:29:14');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644033050878021', 'Nikita', 'Login', '18992720878', '{\"code\":1826}', 1, NULL, 1, NULL, '2025-02-12 14:37:14', 1, '2025-02-12 14:37:15');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644033971408965', 'Nikita', 'Login', '18992720878', '{\"code\":4712}', 1, NULL, 1, NULL, '2025-02-12 14:40:58', 1, '2025-02-12 14:40:59');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644035298119749', 'Nikita', 'Login', '18992720878', '{\"code\":9873}', 1, NULL, 1, NULL, '2025-02-12 14:46:22', 1, '2025-02-12 14:46:23');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644036295913541', 'Nikita', 'Login', 'undefined', '{\"code\":4454}', 1, NULL, 1, NULL, '2025-02-12 14:50:26', 1, '2025-02-12 14:50:27');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644036885385285', 'Nikita', 'Login', 'undefined', '{\"code\":2321}', 1, NULL, 1, NULL, '2025-02-12 14:52:50', 1, '2025-02-12 14:52:51');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644037701238853', 'Nikita', 'Login', '18992720878', '{\"code\":4740}', 1, NULL, 1, NULL, '2025-02-12 14:56:09', 1, '2025-02-12 14:56:10');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644038604202053', 'Nikita', 'Login', '18992720878', '{\"code\":7441}', 1, NULL, 1, NULL, '2025-02-12 14:59:49', 1, '2025-02-12 14:59:50');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644038700572741', 'Nikita', 'Login', '18992720878', '{\"code\":6741}', 1, NULL, 1, NULL, '2025-02-12 15:00:13', 1, '2025-02-12 15:00:14');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644054629621829', 'Nikita', 'Login', '18992720878', '{\"code\":9033}', 1, NULL, 1, NULL, '2025-02-12 16:05:02', 1, '2025-02-12 16:05:03');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644054756032581', 'Nikita', 'Login', '18992720878', '{\"code\":5304}', 1, NULL, 1, NULL, '2025-02-12 16:05:33', 1, '2025-02-12 16:05:34');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644058089873477', 'Nikita', 'Login', '18992720878', '{\"code\":1628}', 1, NULL, 1, NULL, '2025-02-12 16:19:07', 1, '2025-02-12 16:19:07');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644058171162693', 'Nikita', 'Login', '18992720878', '{\"code\":4353}', 1, NULL, 1, NULL, '2025-02-12 16:19:26', 1, '2025-02-12 16:19:27');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644179327189061', 'Nikita', 'Login', '18992720878', '{\"code\":5542}', 1, NULL, 1, NULL, '2025-02-13 00:32:26', 1, '2025-02-13 00:32:27');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644179498717253', 'Nikita', 'Login', '18992720878', '{\"code\":8722}', 1, NULL, 1, NULL, '2025-02-13 00:33:07', 1, '2025-02-13 00:33:08');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644255828611141', 'Nikita', 'Login', '18992720878', '{\"code\":2087}', 1, NULL, 1, NULL, '2025-02-13 05:43:43', 1, '2025-02-13 05:43:44');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644255921877061', 'Nikita', 'Login', '18992720878', '{\"code\":9411}', 1, NULL, 1, NULL, '2025-02-13 05:44:05', 1, '2025-02-13 05:44:06');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644268727697477', 'Nikita', 'Login', '18992720878', '{\"code\":1889}', 1, NULL, 1, NULL, '2025-02-13 06:36:12', 1, '2025-02-13 06:36:13');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('644268836507717', 'Nikita', 'Login', '18992720878', '{\"code\":4136}', 1, NULL, 1, NULL, '2025-02-13 06:36:38', 1, '2025-02-13 06:36:39');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('645734938349637', 'Nikita', 'Login', '18992720878', '{\"code\":4755}', 1, NULL, 1, NULL, '2025-02-17 10:02:13', 1, '2025-02-17 10:02:14');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('645735011074117', 'Nikita', 'Login', '18992720878', '{\"code\":5206}', 1, NULL, 1, NULL, '2025-02-17 10:02:31', 1, '2025-02-17 10:02:32');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('645741774090309', 'Nikita', 'Login', '18992720878', '{\"code\":\"7757\"}', 1, NULL, 1, NULL, '2025-02-17 10:30:02', 1, '2025-02-17 10:30:04');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('645743734194245', 'Nikita', 'Login', '18992720878', '{\"code\":\"2670\"}', 1, NULL, 1, NULL, '2025-02-17 10:38:01', 1, '2025-02-17 10:38:02');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('645777549500485', 'Nikita', 'Login', '18992720878', '{\"code\":\"9401\"}', 1, NULL, 1, NULL, '2025-02-17 12:55:37', 1, '2025-02-17 12:55:38');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646296088428613', 'Nikita', 'Notify', '18992720878', NULL, 1, '10000', 1, NULL, '2025-02-19 00:05:33', 1, '2025-02-19 00:05:34');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646297234722885', 'Nikita', 'Notify', '18992720878', NULL, 1, '10000', 1, NULL, '2025-02-19 00:10:13', 1, '2025-02-19 00:10:13');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646334349963333', 'Nikita', 'Notify', '18992720878', NULL, 1, '10000', 1, NULL, '2025-02-19 02:41:14', 1, '2025-02-19 02:41:16');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646334588796997', 'Nikita', 'Login', '18992720878', '{\"code\":\"6714\"}', 1, NULL, 1, NULL, '2025-02-19 02:42:12', 1, '2025-02-19 02:42:13');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646335148998725', 'Nikita', 'Notify', '18992720878', NULL, 1, '10000', 1, NULL, '2025-02-19 02:44:29', 1, '2025-02-19 02:44:30');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646409788305477', 'Nikita', 'Notify', '18992720878', NULL, 1, '10000', 1, NULL, '2025-02-19 07:48:12', 1, '2025-02-19 07:48:13');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646413526483013', 'Nikita', 'Notify', '18992720878', NULL, 1, '10000', 1, NULL, '2025-02-19 08:03:24', 1, '2025-02-19 08:03:25');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646413804675141', 'Nikita', 'Notify', '18992720878', NULL, 1, '10000', 1, NULL, '2025-02-19 08:04:32', 1, '2025-02-19 08:04:33');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646419901177925', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-19 08:29:21', 1, '2025-02-19 08:29:23');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646421688832069', 'Nikita', 'Notify', '18992720878', NULL, 1, '10000', 1, NULL, '2025-02-19 08:36:37', 1, '2025-02-19 08:36:38');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646423897878597', 'Nikita', 'Notify', '18992720878', NULL, 1, '10000', 1, NULL, '2025-02-19 08:45:36', 1, '2025-02-19 08:45:37');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646424185856069', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-19 08:46:47', 1, '2025-02-19 08:46:47');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646424568152133', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-19 08:48:20', 1, '2025-02-19 08:48:21');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646425483817029', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-19 08:52:04', 1, '2025-02-19 08:52:04');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646427352404037', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-19 08:59:40', 1, '2025-02-19 08:59:41');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646428396642373', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-19 09:03:55', 1, '2025-02-19 09:03:56');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('646429445378117', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-19 09:08:11', 1, '2025-02-19 09:08:12');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648201305509957', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-24 09:17:54', 1, '2025-02-24 09:17:56');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648210560970821', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-24 09:55:33', 1, '2025-02-24 09:55:35');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648225973997637', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-24 10:58:16', 1, '2025-02-24 10:58:18');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648226878013509', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-24 11:01:57', 1, '2025-02-24 11:01:59');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648294299271237', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-24 15:36:17', 1, '2025-02-24 15:36:19');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648295218876485', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-24 15:40:02', 1, '2025-02-24 15:40:03');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648457728004165', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-25 02:41:17', 1, '2025-02-25 02:41:19');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648527990313029', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-25 07:27:11', 1, '2025-02-25 07:27:12');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648529305694277', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-25 07:32:32', 1, '2025-02-25 07:32:33');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648530604232773', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-25 07:37:49', 1, '2025-02-25 07:37:50');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648532327829573', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-25 07:44:50', 1, '2025-02-25 07:44:51');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648533879746629', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-25 07:51:09', 1, '2025-02-25 07:51:09');
INSERT INTO `sms_record` (`id`, `identifier`, `type`, `mobile`, `message`, `result`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('648573835964485', 'Nikita', 'Notify', '18992720878', NULL, 1, NULL, 1, NULL, '2025-02-25 10:33:44', 1, '2025-02-25 10:33:46');
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
  `agent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `park_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of station
-- ----------------------------
BEGIN;
INSERT INTO `station` (`id`, `name`, `latitude`, `longitude`, `address`, `status`, `description`, `img`, `note`, `tags`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('594065961017413', '{\"zh_CN\":\"百合花城充电站\",\"en_US\":\"Default Station\",\"ru_RU\":null}', '34.362281', '107.11277', '{\"zh_CN\":\"陕西省宝鸡市渭滨区姜谭路69号\",\"en_US\":\"West Section of Xinjian Road\",\"ru_RU\":null}', 1, '{\"zh_CN\":\"工作时间:00:00 - 24:00\",\"en_US\":\"\",\"ru_RU\":\"\"}', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_25_bbe7540c4cb446cd8a2a823830470f8e.jpeg', 'fffff', '{\"zh_CN\":\"24小时营业\",\"en_US\":\"\",\"ru_RU\":\"\"}', '649213992497221', '10000', '1', NULL, '2024-10-08 13:05:40', '1', '2025-03-10 04:20:35', 'Off');
INSERT INTO `station` (`id`, `name`, `latitude`, `longitude`, `address`, `status`, `description`, `img`, `note`, `tags`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('617007724576837', '{\"zh_CN\":\"明德沁园充电站\",\"en_US\":\"\",\"ru_RU\":null}', '34.346433', '107.208834', '{\"zh_CN\":\"宝鸡市渭滨区高新大道8号(宝鸡文理学院高新校区对面)\",\"en_US\":\"\",\"ru_RU\":null}', 1, '{\"zh_CN\":\"工作时间:00:00 - 24:00\",\"en_US\":\"\",\"ru_RU\":\"\"}', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_25_6c67e38efe3d411e9fe4c4bf08b30800.jpeg', NULL, '{\"zh_CN\":\"24小时营业\",\"en_US\":\"\",\"ru_RU\":\"\"}', '649213992497221', '10000', '1', NULL, '2024-11-28 05:50:54', '1', '2025-03-15 06:02:58', 'Off');
INSERT INTO `station` (`id`, `name`, `latitude`, `longitude`, `address`, `status`, `description`, `img`, `note`, `tags`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('623472357883973', '{\"zh_CN\":\"金水湾充电站\",\"en_US\":\"\",\"ru_RU\":null}', '34.372631', '107.113744', '{\"zh_CN\":\"宝鸡市金台区新福路乐活58街区停车场\",\"en_US\":\"\",\"ru_RU\":null}', 1, '{\"zh_CN\":\"工作时间:00:00 - 24:00\",\"en_US\":\"\",\"ru_RU\":\"\"}', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_25_4e9e24d902374dcead24b2ff16fb97d9.jpeg', NULL, '{\"zh_CN\":\"24小时营业\",\"en_US\":\"\",\"ru_RU\":\"\"}', NULL, '10000', '1', NULL, '2024-12-16 12:15:33', '1', '2025-03-10 04:20:27', 'Off');
INSERT INTO `station` (`id`, `name`, `latitude`, `longitude`, `address`, `status`, `description`, `img`, `note`, `tags`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `park_state`) VALUES ('651348387233861', '{\"zh_CN\":\"石油东厂区\",\"en_US\":\"\",\"ru_RU\":\"\"}', '34.363799', '107.165657', '{\"zh_CN\":\"东风路中国石油东厂区\",\"en_US\":\"\",\"ru_RU\":\"\"}', 1, '{\"zh_CN\":\"东风路中国石油东厂区\",\"en_US\":\"\",\"ru_RU\":\"\"}', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_06_7aec32a384c1444db3e37021102a30bc.jpeg', NULL, '{\"zh_CN\":\"\",\"en_US\":\"\",\"ru_RU\":\"\"}', '649213992497221', '10000', '1', NULL, '2025-03-05 06:43:24', '1', '2025-03-15 06:14:21', 'Off');
COMMIT;

-- ----------------------------
-- Table structure for station_favorite
-- ----------------------------
DROP TABLE IF EXISTS `station_favorite`;
CREATE TABLE `station_favorite` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '客户id',
  `station_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '场站id',
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='场站收藏';

-- ----------------------------
-- Records of station_favorite
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备dn',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备名称',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `product_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品key',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `dn` (`dn`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of storage
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_agent
-- ----------------------------
DROP TABLE IF EXISTS `sys_agent`;
CREATE TABLE `sys_agent` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `contact_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
  `contact_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `menu_ids` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '菜单ids',
  `tenant_profit_percent` decimal(19,2) DEFAULT NULL COMMENT '运营商分成比例',
  `status` int DEFAULT NULL COMMENT '启用/停用',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_agent
-- ----------------------------
BEGIN;
INSERT INTO `sys_agent` (`id`, `name`, `contact_person`, `contact_mobile`, `menu_ids`, `tenant_profit_percent`, `status`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('649213992497221', 'agent', 'agent', 'agent', '[\"580697072078917\",\"632588972994629\",\"580958514028613\",\"636556871516229\",\"581008017731653\",\"636115075387461\",\"636118310428741\",\"636115458879557\",\"636118718099525\",\"636115734843461\",\"636119104938053\",\"636115950932037\",\"636119497736261\",\"632591069773893\",\"636128797368389\",\"636129844842565\",\"636131066740805\",\"636131236679749\",\"587680307204165\",\"636775185645637\",\"632891081621573\",\"636515476852805\",\"636773452767301\",\"636515557359685\",\"637277467107397\",\"1\",\"100\",\"1001\",\"1002\",\"1003\",\"1004\",\"1005\",\"1007\",\"101\",\"1008\",\"1009\",\"1010\",\"1011\",\"1012\",\"103\",\"1017\",\"1018\",\"1019\",\"1020\",\"104\",\"1021\",\"1022\",\"1023\",\"1024\",\"1025\",\"502\",\"442129\",\"44212332\",\"423\",\"442123323123\",\"4234553537\",\"635476799909957\",\"636762894495813\",\"636762974990405\",\"636763050074181\",\"636763119214661\"]', 0.10, 1, NULL, '10000', '649212264292421', NULL, '2025-02-27 05:58:32', '649212264292421', '2025-02-27 05:58:32');
COMMIT;

-- ----------------------------
-- Table structure for sys_agent_station
-- ----------------------------
DROP TABLE IF EXISTS `sys_agent_station`;
CREATE TABLE `sys_agent_station` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `agent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '代理商id',
  `station_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '场站id',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_agent_station
-- ----------------------------
BEGIN;
INSERT INTO `sys_agent_station` (`id`, `agent_id`, `station_id`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `status`) VALUES ('654880187547717', '649213992497221', '651348387233861', NULL, '10000', '1', NULL, '2025-03-15 06:14:20', '1', '2025-03-15 06:14:20', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_agent_station_dealer
-- ----------------------------
DROP TABLE IF EXISTS `sys_agent_station_dealer`;
CREATE TABLE `sys_agent_station_dealer` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `agent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '代理商id',
  `agent_station_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '代理商场站id',
  `dealer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '合作商id',
  `percent` decimal(10,2) DEFAULT NULL COMMENT '分成比例',
  `status` int DEFAULT NULL COMMENT '启用/停用',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_agent_station_dealer
-- ----------------------------
BEGIN;
INSERT INTO `sys_agent_station_dealer` (`id`, `agent_id`, `agent_station_id`, `dealer_id`, `percent`, `status`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('654880261685317', '649213992497221', '654880187547717', '649214094676037', 0.10, 1, NULL, '10000', '1', NULL, '2025-03-15 06:14:38', '1', '2025-03-15 06:14:38');
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
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `config` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_app
-- ----------------------------
BEGIN;
INSERT INTO `sys_app` (`id`, `app_id`, `app_name`, `app_secret`, `app_type`, `status`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `config`) VALUES ('600133534576709', '12323432', 'test', '111111', 'Wechat', 1, NULL, '10000', '1', NULL, '2024-10-11 13:29:38', '1', '2025-03-06 07:36:56', '{\"appId\":\"wx7fd1602b884b4f31\",\"appSecret\":\"7269bdb79b5691afd6e9b8dbeb401d55\"}');
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
  `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
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
INSERT INTO `sys_config` (`id`, `config_key`, `config_name`, `config_type`, `config_value`, `data_type`, `remark`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (585177022468146, 'app.user.privacy', '移动端-隐私协议', 'Y', '{\"zh_CN\":\"<h1 class=\\\"ql-align-center\\\">久福云充小程序隐私保护说明</h1><p>本指引是久福云充小程序开发者（以下简称“开发者”）为处理你的个人信息而制定。</p><h2>开发者处理的信息</h2><p>根据法律规定，开发者仅处理实现小程序功能所必要的信息。</p><p>为了注册、登录小程序，开发者收集你的用户信息（微信昵称、头像、性别、地区）。</p><p>开发者收集你的手机号码，用于方便用户登陆，并在发生突发情况时与你取得联系。</p><h2>第三方插件信息/SDK信息</h2><p>为实现特定功能，开发者可能会接入由第三方提供的插件/SDK。第三方插件/SDK的个人信息处理规则，请以其公示的官方说明为准。久福云充小程序接入的第三方插件信息/SDK信息如下：</p><h3>第三方插件信息</h3><p>插件名称：高德地图 插件提供方名称: 高德软件有限公司为您提供最近的充电柱，高德地图需要收集您的定位信息。</p><h2>你的权益</h2><p>开发者承诺，除法律法规另有规定外，开发者对你的信息的保存期限应当为实现处理目的所必要的最短时间。关于久福云充，你可以通过以下路径：小程序主页右上角“…”—“设置”—点击特定信息—点击“不允许”，撤回对开发者的授权。关于你的个人信息，你可以通过以下方式与开发者联系，行使查阅、复制、更正、删除等法定权利。 邮箱： jiufuyunchong@163.com</p><h2>开发者对信息的存储</h2><p>开发者承诺，除法律法规另有规定外，开发者对你的信息的保存期限应当为实现处理目的所必要的最短时间。开发者将会在本指引所明示的用途内使用收集的信息。如开发者使用你的信息超出本指引目的或合理范围，开发者必须在变更使用目的或范围前，再次以xxx方式告知并征得你的明示同意。</p><h2>信息对外提供</h2><p>开发者承诺，不会主动共享或转让你的信息至任何第三方，如存在确需共享或转让时，开发者应当直接征得或确认第三方征得你的单独同意。开发者承诺，不会对外公开披露你的信息，如必须公开披露时，开发者应当向你告知公开披露的目的、披露信息的类型及可能涉及的信息，并征得你的单独同意。你认为开发者未遵守上述约定，或有其他的投诉建议、或未成年人个人信息保护相关问题，可通过以下方式与开发者联系；或者向微信进行投诉。 </p><p><br></p><p>邮箱： jiufuyunchong@163.com</p><p>更新日期：2024-11-18生效日期：2024-11-18</p>\",\"en_US\":\"\",\"ru_RU\":\"\"}', 7, NULL, 10000, 1, NULL, '2024-08-30 07:11:26', 1, '2025-03-14 09:25:29');
INSERT INTO `sys_config` (`id`, `config_key`, `config_name`, `config_type`, `config_value`, `data_type`, `remark`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (585177022468166, 'app.user.agreement', '移动端-用户协议', 'Y', '{\"zh_CN\":\"<h2>一、总则</h2><p>1、用户在注册及使用前请认真阅读本协议，确保充分理解本协议中所有条款。除非您接受本协议所有条款，否则您无权注册、登录或使用本协议所涉服务。您的注册、登录、使用等行为将视为无条件接受本协议所有条款的约束。2、除非另有明确规定，本产品所推出的新功能、新服务，均无条件的使用本协议。3、我公司保留在任何时候修改本协议条款的权利，且无需另行通知。在我公司修改协议条款后，如果您不接受修改后的条款，请立即停止使用本产品提供的服务，继续使用本产品提供的服务将被视为接受修改后的协议。</p><h2>二、用户注册</h2><p>1、用户应当同意本协议的全部条款并按照页面提示完成全部注册程序(未成年人应与法定监护人共同完成)。用户在注册过程中点击“下一步”按钮即表示完全接受本协议全部条款。2、用户在使用本服务前需要注册一个本产品账号。本产品账号应当使用手机号码绑定注册，请用户使用尚未与本产品账号绑定且未被本产品根据本协议封禁的手机号码注册账号。本产品可以根据用户需求或产品需求对账号注册和绑定的方式进行更改，而无须事先通知用户。3、用户在使用本产品服务过程中应保证各项服务业务所需信息的真实性，如果因信息不真实而引起的问题，以及问题发生所带来的后果，本公司不负任何责任。4、在用户注册及使用本产品时，要搜集能识别用户身份的个人信息以便系统可以在必要时联系用户，或为用户提供更好的使用体验。系统搜集的信息包括但不限于用户的性别、年龄、出生日期、所在城市；系统同意对这些信息的使用将受限于用户个人隐私信息保护的约束。</p><h2>三、服务内容</h2><p>1、本服务的具体内容由本产品根据实际情况提供，包括但不限于用户使用本产品等。本产品可以对提供的服务予以变更，且本产品提供的服务内容可能随时变更，用户将会收到关于服务变更的通知。2、除非本协议另有其他明示规定，本公司所推出的新产品、新功能、新服务，均受到本协议条款之规范。</p><h2>四、服务变更、中断或终止</h2><p>1、鉴于网络服务的特殊性(包括但不限于服务器的稳定性问题、恶意的网络攻击等行为的存在及其他无法控制的情形)，用户同意我公司有权随时中断或终止部分或全部的服务。2、我公司需要定期或不定期地对提供服务的系统或相关设备进行检修或维护，如因此类情况而造成服务在合理时间内的中断，我公司无需为此承担任何责任。3、如发生下列任何一种情形，我公司有权随时变更、中断或终止向用户提供本协议项下的服务而无需对用户或任何第三方承担任何责任：(1)根据法律规定用户应提交真实信息，而用户提供的个人资料不真实、或与注册时信息不一致又未能提供合理证明；(2)用户违反相关法律法规或本协议的约定；(3)按照法律规定或有权机关的要求；(4)出于安全的原因或其他必要的情形。</p><h2>五、用户个人隐私信息保护</h2><p>1、依据法律的规定，我们将在特定情形下收集、使用和披露您的个人信息。以下条款描述了我们如何收集、使用和披露您的个人信息。2、信息收集我们会对您直接提供的信息进行保存。比如：我们会记录您的注册信息、寻求客服或者其他和我们沟通的记录。记录信息的种类包括：头像、昵称、性别、出生日期、所在地区以及其他您选择提供的信息。我们收集、使用和披露个人信息是为了通过创建账户、识别用户、回应查询和邮件等方式来为您提供服务。当你接触或者使用我们的服务时，我们将自动收集您的信息包括：Log信息(我们记录所有您使用服务时的log信息，包括浏览器信息、使用时间、浏览的网页、IP地址及来源)。我们使用多种技术记录信息，包括但不限于：向您的移动设备种Cookies。Cookies是一些存在您的硬件上的小数据包，用以帮助我们提高服务的质量及您的使用体验，了解在哪些区域和功能上受欢迎，以及统计流量等。3、信息使用(1)除本隐私政策未载明或本隐私政策的更新未能首先通知您的情况下，您的个人信息将不会用于其他目的。(2)匿名汇总统计数据不是我公司所定义的个人用户信息，我们将为多种目的，包括但不限于分析和使用模式的报告等，来保存和使用此类信息。我公司保留以任何目的或单方面许可第三方使用和披露匿名汇总统计数据的权利。(3)您在本产品中上传的信息，有可能会损坏您或他人的合法权益，您必须充分意识此类风险的存在。您明确同意，自行承担因上传信息所存在的一切风险及后果，我公司无需承担任何责任。4、法定披露 虽然我们会尽最大努力保护用户隐私，但当我们有理由相信只有公开个人信息才能遵循现行司法程序、 法院指令或其他法律程序或者保护我公司、我公司用户或第三方的权利、财产或安全时，我们可能披露个人信息。5、信息安全 我们会采取合理的实际及电子手段以及规程保障措施来保护您的个人信息。 虽然通过因特网信息传输数据并非100% 安全，但我们已经采取并将继续采取商业范畴内合理的努力来确保您的个人信息得到保护。6、未成年人隐私保护我公司重视对未成年人个人隐私信息的保护。我公司将依赖用户提供的个人信息判断用户是否为未成年人。任何18岁以下的未成年人注册账号或使用本服务应事先取得家长或其法定监护人(以下简称“监护人”)的书面同意。除根据法律法规的规定及有权机关的指示披露外，我公司不会使用向任何第三方透露未成年人的个人隐私信息。</p><h2>六、内容规范</h2><p>1、本项规范所述内容是指用户使用本服务过程中所制作、上载、复制、发布、传播的任何内容，包括但不限于账号头像、名称、个性签名等注册信息及认证资料，或文字、语音、图片、图文等发送、回复消息和相关链接页面，以及其他使用本产品账号或本服务所产生的内容。2、用户承诺使用本产品的服务时必须符合中华人民共和国有关法律法规，不得利用本产品的服务制作、上载、复制、发布、传播以下内容：(1)反对宪法所确定的基本原则的；(2)危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；(3)损害国家荣誉和利益的；(4)煽动民族仇恨、民族歧视，破坏民族团结的；(5)破坏国家宗教政策，宣扬邪教和封建迷信的；(6)散布谣言，扰乱社会秩序，破坏社会稳定的；(7)散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；(8)侮辱或者诽谤他人，侵害他人合法权益的；(9)含有法律、行政法规禁止的其他内容的。3、用户不得利用本产品账号或本服务制作、上载、复制、发布、传播下干扰本产品正常运营，以及侵犯其他用户或第三方合作权益的内容：(1)含有任何性暗示的；(2)含有辱骂、恐吓、威胁内容的；(3)含有骚扰、垃圾广告、恶意信息、诱骗信息的；(4)涉及他人隐私、个人信息或资料的；(5)含有其他干扰本服务正常运营和侵犯其他用户或第三方合法权益的。</p><h2>七、使用规则</h2><p>1、用户在本服务中或通过本服务所传送、发布的任何内容并不反映或代表，也不得被视为反映或代表我公司的观点、立场或政策，我公司对此不承担任何责任。2、用户在使用本产品时，必须遵守中华人民共和国相关法律法规的规定，同意将不会利用本产品进行任何违法或不正当的活动，包括但不限于下列行为：(1)干扰或破坏有关服务，或与有关服务连接的任何服务器或网络，或与有关服务相关的任何政策、要求或规定；(2)采集并存储涉及任何其他用户的个人信息，以用于任何被禁止的活动；(3)故意或非故意违反任何相关的中国法律、法规、规章、条例等其他具有法律效力的规范。3、用户须对利用本产品账号或本服务传送信息的真实性、合法性、无害性、准确性、有效性等全权负责，与用户所传播信息相关的任何法律责任由用户自行承担，与我公司无关。如因此给我公司或第三方造成损害的，用户应当依法予以赔偿。4、本产品提供的服务中可能包括广告，用户同意在使用过程中显示本产品和第三方供应商、合作伙伴提供的广告。除法律法规明确规定外，用户应自行对该广告信息进行的交易负责，对用户因该广告信息进行的交易或前述广告商提供的内容或遭受的损失或损害，我公司不承担任何责任。5、用户为使用本产品，须自行配备进入国际互联网所必需的设备，包括电脑、手机及其他与接入国际互联网有关的装置，并自行支付与此服务有关的费用。</p><h2>八、免责声明</h2><p>1、对于经由本产品服务而传送的内容，我公司不保证前述内容的正确性、完整性或品质。用户在接受有关服务时，有可能会接触到令人不快、不适当或令人厌恶的内容。在任何情况下，我公司均不对任何内容负责，包括但不限于任何内容发生任何错误或纰漏以及衍生的任何损失或损害。用户使用上述内容，应自行承担风险。2、用户明确同意其使用本产品所存在的风险及其后果将完全由其自己承担，我公司对用户不承担任何责任。如因用户违反有关法律、法规或本协议项下的任何条款而给任何其他第三人造成损失，用户同意承担由此造成的损害赔偿责任。3、我公司尊重并保护用户的个人隐私权。但因恶意的网络攻击等行为及其他无法控制的情形，导致用户隐私信息泄露的，用户同意我公司不承担任何责任。4、对于因电信系统或互联网网络故障、计算机故障、计算机系统问题或其它任何不可抗力原因而产生损失，我公司不承担任何责任，但将尽力减少因此给用户造成的损失和影响。</p><h2>九、知识产权声明</h2><p>1、本产品服务中包含的任何文字、图表、音频、视频和软件(包括但不限于软件中包含的图表、动画、音频、视频、界面实际、数据和程序、代码、文档)等信息或材料均受著作权法、商标法和其它法律法规保护，未经相关权利人书面同意，用户不得以任何方式使用该信息或材料。2、本协议未授予用户使用本产品任何商标、服务标记、标识、域名和其他显著品牌特征的权利，任何人不得擅自(包括但不限于：以非法的方式复制、传播、展示、镜像、上载、下载)使用，否则我公司将依法追究法律责任。3、除本协议明确允许以外，用户不得以任何形式或任何方式对本产品部分或全部内容进行修改、出租、租赁、出借、出售、分发、复制、创作衍生品或用于任何商业用途。</p><h2>十、法律适用</h2><p>1、本协议的订立、执行和解释及争议的解决均应适用中国法律并受中国法院管辖。如服务条款任何一部分与中华人民共和国法律相抵触，则该部分条款应按法律规定重新解释，部分条款无效或重新解释不影响其余条款法律效力。2、用户和我公司一致同意本协议。在执行本协议过程中如发生纠纷，双方应友好协商解决；协商不成时，任何一方可直接向所在地的人民法院提起诉讼。</p><h2>十一、其他规定</h2><p>1、本协议中的标题仅为方便而设，在解释本协议时应被忽略。2、本协议及其修改权、最终解释权归我公司所有。</p>\",\"en_US\":\"\",\"ru_RU\":\"\"}', 7, '', 10000, 1, NULL, '2024-08-30 07:11:26', 1, '2024-11-28 16:29:51');
INSERT INTO `sys_config` (`id`, `config_key`, `config_name`, `config_type`, `config_value`, `data_type`, `remark`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (585177022468167, 'app.charge.balance.limit', '用户充电最低余额', 'Y', '10', 5, '', 10000, 1, NULL, '2024-08-30 07:11:26', 1, '2024-11-28 16:55:23');
INSERT INTO `sys_config` (`id`, `config_key`, `config_name`, `config_type`, `config_value`, `data_type`, `remark`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (585177022468168, 'app.user.service', '移动端-客户服务', 'Y', '{\"zh_CN\":\"<h2>客服电话: <a href=\\\"tel:18791735601\\\">18791735601</a></h2>\",\"en_US\":\"\",\"ru_RU\":\"\"}', 7, NULL, 10000, 1, NULL, '2024-08-30 07:11:26', 1, '2024-11-29 13:47:38');
COMMIT;

-- ----------------------------
-- Table structure for sys_country
-- ----------------------------
DROP TABLE IF EXISTS `sys_country`;
CREATE TABLE `sys_country` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
  `iso2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Iso2',
  `iso3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ISO3',
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '国际区号',
  `tel_rule` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '国际区号正则',
  `status` int DEFAULT NULL COMMENT '状态',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_country
-- ----------------------------
BEGIN;
INSERT INTO `sys_country` (`id`, `name`, `icon`, `iso2`, `iso3`, `tel`, `tel_rule`, `status`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('634476376969285', '{\"zh_CN\":\"中国\",\"en_US\":null,\"ru_RU\":\"\"}', 'http://192.168.3.61:8086/iot-oss/iot/2025_01_16_c51e4b51e7de44cfafe528054ca335f5.png', 'cn', 'chn', '86', '^[0-9]{11,11}$', 1, NULL, '10000', '1', NULL, '2025-01-16 14:31:01', '1', '2025-01-16 14:39:16');
COMMIT;

-- ----------------------------
-- Table structure for sys_dealer
-- ----------------------------
DROP TABLE IF EXISTS `sys_dealer`;
CREATE TABLE `sys_dealer` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `contact_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
  `contact_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `status` int DEFAULT NULL COMMENT '启用/停用',
  `menu_ids` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '菜单ids',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `agent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_dealer
-- ----------------------------
BEGIN;
INSERT INTO `sys_dealer` (`id`, `name`, `contact_person`, `contact_mobile`, `status`, `menu_ids`, `note`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('649214094676037', 'dealer', '11', '11', 1, '[\"580697072078917\",\"632588972994629\",\"580958514028613\",\"636556871516229\",\"581008017731653\",\"636115075387461\",\"636118310428741\",\"636115458879557\",\"636118718099525\",\"636115734843461\",\"636119104938053\",\"636115950932037\",\"636119497736261\",\"632591069773893\",\"636128797368389\",\"636129844842565\",\"636131066740805\",\"636131236679749\",\"587680307204165\",\"636775185645637\",\"632891081621573\",\"636515476852805\",\"636773452767301\",\"636515557359685\",\"637277467107397\"]', NULL, '649213992497221', '10000', '649212264292421', NULL, '2025-02-27 05:58:57', '649212264292421', '2025-02-27 05:58:57');
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
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` bigint DEFAULT NULL COMMENT '租户ID',
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
INSERT INTO `sys_dept` (`id`, `parent_id`, `dept_name`, `leader`, `email`, `phone`, `order_num`, `ancestors`, `status`, `agent_id`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (649213992607813, 0, 'agent', 'agent', NULL, 'agent', NULL, '0', '1', '649213992497221', 10000, 649212264292421, NULL, '2025-02-27 05:58:32', 649212264292421, '2025-02-27 05:58:32');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `id` bigint NOT NULL COMMENT '字典编码',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `css_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `dict_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典标签',
  `dict_sort` int DEFAULT NULL COMMENT '字典排序',
  `dict_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典类型',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典键值',
  `is_default` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否默认（Y是 N否）',
  `list_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表格字典样式',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态（0正常 1停用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (1, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '男', 1, 'sys_user_sex', '0', 'Y', '', '性别男', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (2, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '女', 2, 'sys_user_sex', '1', 'N', '', '性别女', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (3, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '未知', 3, 'sys_user_sex', '2', 'N', '', '性别未知', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (4, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '显示', 1, 'sys_show_hide', '0', 'Y', 'primary', '显示菜单', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (5, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '隐藏', 2, 'sys_show_hide', '1', 'N', 'danger', '隐藏菜单', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (6, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '正常', 1, 'sys_normal_disable', '0', 'Y', 'primary', '正常状态', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (7, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '停用', 2, 'sys_normal_disable', '1', 'N', 'danger', '停用状态', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (12, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '是', 1, 'sys_yes_no', 'Y', 'Y', 'primary', '系统默认是', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (13, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '否', 2, 'sys_yes_no', 'N', 'N', 'danger', '系统默认否', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (14, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '通知', 1, 'sys_notice_type', '1', 'Y', 'warning', '通知', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (15, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '公告', 2, 'sys_notice_type', '2', 'N', 'success', '公告', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (16, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '正常', 1, 'sys_notice_status', '0', 'Y', 'primary', '正常状态', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (17, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '关闭', NULL, 'sys_notice_status', '1', 'N', 'danger', '关闭状态', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (18, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '新增', NULL, 'sys_oper_type', '1', 'N', 'info', '新增操作', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (19, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '修改', NULL, 'sys_oper_type', '2', 'N', 'info', '修改操作', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (20, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '删除', NULL, 'sys_oper_type', '3', 'N', 'danger', '删除操作', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (21, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '授权', NULL, 'sys_oper_type', '4', 'N', 'primary', '授权操作', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (22, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '导出', NULL, 'sys_oper_type', '5', 'N', 'warning', '导出操作', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (23, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '导入', NULL, 'sys_oper_type', '6', 'N', 'warning', '导入操作', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (24, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '强退', NULL, 'sys_oper_type', '7', 'N', 'danger', '强退操作', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (25, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '生成代码', NULL, 'sys_oper_type', '8', 'N', 'warning', '生成操作', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (26, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '清空数据', NULL, 'sys_oper_type', '9', 'N', 'danger', '清空操作', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (27, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '成功', NULL, 'sys_common_status', '0', 'N', 'primary', '正常状态', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (28, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '失败', NULL, 'sys_common_status', '1', 'N', 'danger', '停用状态', '0');
INSERT INTO `sys_dict_data` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `css_class`, `dict_label`, `dict_sort`, `dict_type`, `dict_value`, `is_default`, `list_class`, `remark`, `status`) VALUES (29, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '', '其他', NULL, 'sys_oper_type', '0', 'N', 'info', '其他操作', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` bigint NOT NULL COMMENT '字典主键',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典名称',
  `dict_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '字典类型',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态（0正常 1停用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `dict_name`, `dict_type`, `remark`, `status`) VALUES (1, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '用户性别', 'sys_user_sex', '用户性别列表', '0');
INSERT INTO `sys_dict_type` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `dict_name`, `dict_type`, `remark`, `status`) VALUES (2, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '菜单状态', 'sys_show_hide', '菜单状态列表', '0');
INSERT INTO `sys_dict_type` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `dict_name`, `dict_type`, `remark`, `status`) VALUES (3, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '系统开关', 'sys_normal_disable', '系统开关列表', '0');
INSERT INTO `sys_dict_type` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `dict_name`, `dict_type`, `remark`, `status`) VALUES (6, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '系统是否', 'sys_yes_no', '系统是否列表', '0');
INSERT INTO `sys_dict_type` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `dict_name`, `dict_type`, `remark`, `status`) VALUES (7, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '通知类型', 'sys_notice_type', '通知类型列表', '0');
INSERT INTO `sys_dict_type` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `dict_name`, `dict_type`, `remark`, `status`) VALUES (8, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '通知状态', 'sys_notice_status', '通知状态列表', '0');
INSERT INTO `sys_dict_type` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `dict_name`, `dict_type`, `remark`, `status`) VALUES (9, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '操作类型', 'sys_oper_type', '操作类型列表', '0');
INSERT INTO `sys_dict_type` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `dict_name`, `dict_type`, `remark`, `status`) VALUES (10, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '系统状态', 'sys_common_status', '登录状态列表', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_logininfo
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfo`;
CREATE TABLE `sys_logininfo` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dealer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '浏览器类型',
  `ipaddr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录地点',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提示消息',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作系统',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录状态 0成功 1失败',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
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
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (6, '{\"zh_CN\":\"平台管理\",\"en_US\":\"Platform\",\"ru_RU\":null}', 'M', 0, 'chart', NULL, '', '', 'platform', 90, '0', '1', 0, 0, 0, 1, '1', '0', '0', '0', '0', '1', '租户管理目录', 1, 103, '2024-08-15 10:34:57', 1, '2024-10-05 17:20:29');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (100, '{\"zh_CN\":\"用户管理\",\"en_US\":\"User\",\"ru_RU\":null}', 'C', 1, 'user', 'system/user/list', '', 'system:user:list', 'user', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '用户管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 17:56:02');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (101, '{\"zh_CN\":\"角色管理\",\"en_US\":\"Role\",\"ru_RU\":null}', 'C', 1, 'peoples', 'system/role/list', '', 'system:role:list', 'role', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '角色管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 17:56:12');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (102, '{\"zh_CN\":\"菜单管理\",\"en_US\":\"Menu\",\"ru_RU\":null}', 'C', 6, 'tree-table', 'platform/menu/list', '', 'platform:menu:list', 'menu', 3, '0', '1', 0, 0, 0, 1, '0', '1', '1', '1', '0', '1', '菜单管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:47:38');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (103, '{\"zh_CN\":\"部门管理\",\"en_US\":\"Department\",\"ru_RU\":null}', 'C', 1, 'tree', 'system/dept/index', '', 'system:dept:list', 'dept', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '部门管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 17:56:19');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (104, '{\"zh_CN\":\"岗位管理\",\"en_US\":\"Position\",\"ru_RU\":null}', 'C', 1, 'post', 'system/post/index', '', 'system:post:list', 'post', 5, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '1', '1', '岗位管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 06:22:50');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (105, '{\"zh_CN\":\"字典管理\",\"en_US\":\"Dict\",\"ru_RU\":null}', 'C', 6, 'dict', 'platform/dict/list', '', 'platform:dict:list', 'dict', 6, '0', '1', 0, 0, 0, 1, '0', '1', '1', '1', '1', '1', '字典管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-10 05:20:14');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (106, '{\"zh_CN\":\"参数设置\",\"en_US\":\"Config\",\"ru_RU\":null}', 'C', 1, 'edit', 'system/config/index', '', 'system:config:list', 'sysconfig', 7, '0', '1', 0, 0, 0, 0, '1', '1', '0', '1', '0', '1', '参数设置菜单', 1, 103, '2024-08-15 10:34:57', 1, '2024-10-05 17:24:36');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (107, '{\"zh_CN\":\"通知公告\",\"en_US\":\"Announcement\",\"ru_RU\":null}', 'C', 580697072078917, 'message', 'business/notice/list', '', 'business:notice:list', 'notice', 800, '0', '1', 0, 0, 0, 0, '1', '0', '0', '1', '0', '1', '通知公告菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:13:48');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (109, '{\"zh_CN\":\"在线用户\",\"en_US\":\"Online User\",\"ru_RU\":null}', 'C', 2, 'online', 'monitor/online/index', '', 'monitor:online:list', 'online', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '在线用户菜单', 1, 103, '2024-08-15 10:34:57', 1, '2024-10-05 17:26:26');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (118, '{\"zh_CN\":\"文件管理\",\"en_US\":\"Attachment\",\"ru_RU\":null}', 'C', 6, 'upload', 'platform/oss/list', '', 'platform:oss:list', 'oss', 10, '0', '1', 0, 0, 0, 1, '0', '1', '1', '1', '0', '1', '文件管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:55:08');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (423, '{\"zh_CN\":\"应用修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 502, '', NULL, NULL, 'system:app:edit', '', 4, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:42');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (501, '{\"zh_CN\":\"登录日志\",\"en_US\":\"Login Log\",\"ru_RU\":null}', 'C', 2, 'logininfor', 'monitor/loginlog/index', '', 'monitor:loginlog:list', 'logininfor', 2, '0', '1', 0, 0, 0, 0, NULL, '1', '1', '1', '0', '1', '登录日志菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:42:26');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (502, '{\"zh_CN\":\"应用管理\",\"en_US\":\"Application\",\"ru_RU\":null}', 'C', 1, 'phone', 'system/app/list', '', 'system:app:list', 'app', 11, '1', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', '应用管理菜单', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:12');
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
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1013, '{\"zh_CN\":\"菜单查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 102, '#', '', '', 'platform:menu:query', '', 1, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:47:47');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1014, '{\"zh_CN\":\"菜单新增\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 102, '#', '', '', 'platform:menu:add', '', 2, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:47:55');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1015, '{\"zh_CN\":\"菜单修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 102, '#', '', '', 'platform:menu:edit', '', 3, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:48:03');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1016, '{\"zh_CN\":\"菜单删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 102, '#', '', '', 'platform:menu:delete', '', 4, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:48:13');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1017, '{\"zh_CN\": \"部门查询\", \"en_US\":\"\"}', 'F', 103, '#', '', '', 'system:dept:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1018, '{\"zh_CN\": \"部门新增\", \"en_US\":\"\"}', 'F', 103, '#', '', '', 'system:dept:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1019, '{\"zh_CN\": \"部门修改\", \"en_US\":\"\"}', 'F', 103, '#', '', '', 'system:dept:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1020, '{\"zh_CN\":\"部门删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 103, '#', '', '', 'system:dept:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:06:19');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1021, '{\"zh_CN\": \"岗位查询\", \"en_US\":\"\"}', 'F', 104, '#', '', '', 'system:post:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1022, '{\"zh_CN\": \"岗位新增\", \"en_US\":\"\"}', 'F', 104, '#', '', '', 'system:post:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1023, '{\"zh_CN\": \"岗位修改\", \"en_US\":\"\"}', 'F', 104, '#', '', '', 'system:post:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1024, '{\"zh_CN\":\"岗位删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 104, '#', '', '', 'system:post:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-23 02:06:07');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1025, '{\"zh_CN\": \"岗位导出\", \"en_US\":\"\"}', 'F', 104, '#', '', '', 'system:post:export', '', 5, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1026, '{\"zh_CN\": \"字典查询\", \"en_US\":\"\"}', 'F', 105, '#', '', '', 'system:dict:query', '#', 1, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1027, '{\"zh_CN\": \"字典新增\", \"en_US\":\"\"}', 'F', 105, '#', '', '', 'system:dict:add', '#', 2, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1028, '{\"zh_CN\": \"字典修改\", \"en_US\":\"\"}', 'F', 105, '#', '', '', 'system:dict:edit', '#', 3, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1029, '{\"zh_CN\": \"字典删除\", \"en_US\":\"\"}', 'F', 105, '#', '', '', 'system:dict:remove', '#', 4, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1030, '{\"zh_CN\": \"字典导出\", \"en_US\":\"\"}', 'F', 105, '#', '', '', 'system:dict:export', '#', 5, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
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
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1600, '{\"zh_CN\": \"文件查询\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:query', '#', 1, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1601, '{\"zh_CN\": \"文件上传\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:upload', '#', 2, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1602, '{\"zh_CN\": \"文件下载\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:download', '#', 3, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1603, '{\"zh_CN\": \"文件删除\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:remove', '#', 4, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1604, '{\"zh_CN\": \"配置添加\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:add', '#', 5, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (1605, '{\"zh_CN\": \"配置编辑\", \"en_US\":\"\"}', 'F', 118, '#', '', '', 'system:oss:edit', '#', 6, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2205, '{\"zh_CN\":\"产品管理\",\"en_US\":\"Product\",\"ru_RU\":null}', 'C', 6, 'product-management', 'platform/product/list', NULL, 'platform:product:list', 'product', 401, '1', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:09:13');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2210, '{\"zh_CN\":\"插件管理\",\"en_US\":\"Plugins\",\"ru_RU\":null}', 'C', 6, 'component', 'platform/plugins/index', NULL, 'platform:plugin:list', 'plugins', 400, '1', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:11:20');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2213, '{\"zh_CN\":\"定时任务\",\"en_US\":\"\",\"ru_RU\":null}', 'C', 6, 'component', 'platform/task/list', NULL, 'platform:task:list', 'task', 100, '0', '1', 0, 0, 0, 1, '0', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-01-22 17:30:49');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (2222, '{\"zh_CN\":\"告警日志\",\"en_US\":\"\",\"ru_RU\":null}', 'C', 2, 'message', 'monitor/alarm/list', NULL, 'monitor:alert:list', 'list', 10, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', '', 1, 103, '2024-08-15 10:34:57', 1, '2025-02-26 13:10:45');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442129, '{\"zh_CN\":\"应用查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 502, '', NULL, NULL, 'system:app:query', '', 1, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:25');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (44212332, '{\"zh_CN\":\"应用添加\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 502, '', NULL, NULL, 'system:app:add', '', 2, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:31');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (4234553537, '{\"zh_CN\":\"应用导出\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 502, '', NULL, NULL, 'system:app:export', '', 5, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:52');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442123323123, '{\"zh_CN\":\"应用删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 502, '', NULL, NULL, 'system:app:delete', '', 4, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:57:47');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (441853056651333, '{\"zh_CN\":\"产品查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2205, '', NULL, NULL, 'platform:product:query', '', 1, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:09:22');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (441853220675653, '{\"zh_CN\":\"产品添加\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2205, '', NULL, NULL, 'platform:product:add', '', 1, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:09:28');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (441853285179461, '{\"zh_CN\":\"产品修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2205, '', NULL, NULL, 'platform:product:edit', '', 1, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:09:32');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (441862726639685, '{\"zh_CN\":\"产品删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2205, '', NULL, NULL, 'platform:product:delete', '', 1, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:09:42');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442127357415493, '{\"zh_CN\":\"插件添加\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2210, '', NULL, NULL, 'platform:plugin:add', '', 1, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:11:28');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442127532781637, '{\"zh_CN\":\"插件修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2210, '', NULL, NULL, 'platform:plugin:edit', '', 2, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:11:33');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442127596064837, '{\"zh_CN\":\"插件查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2210, '', NULL, NULL, 'platform:plugin:query', '', 0, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:11:53');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442127705182277, '{\"zh_CN\":\"插件删除\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2210, '', NULL, NULL, 'platform:plugin:delete', '', 3, '0', '1', 0, 0, 0, 1, NULL, '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-02-26 13:11:42');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442129175347269, '{\"zh_CN\":\"定时任务查询\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2213, '', NULL, NULL, 'platform:task:query', '', 1, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-01-23 02:48:44');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (442129320091717, '{\"zh_CN\":\"定时任务修改\",\"en_US\":\"\",\"ru_RU\":null}', 'F', 2213, '', NULL, NULL, 'platform:task:edit', '', 1, '0', '1', 0, 0, 0, 1, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2024-08-15 10:34:57', 1, '2025-01-23 02:48:53');
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
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (634475502964805, '{\"zh_CN\":\"国家管理\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 1, 'chart', 'system/country/list', NULL, 'system:country:list', 'country', 1000, '1', '1', 0, 0, 0, 0, '1', '1', '0', '1', '0', '1', NULL, 1, NULL, '2025-01-16 14:27:28', 1, '2025-01-23 01:41:17');
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
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636764744593477, '{\"zh_CN\":\"国家查询\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 634475502964805, '', NULL, NULL, 'system:country:query', '', 1, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:42:25', 1, '2025-01-23 01:42:25');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636764813963333, '{\"zh_CN\":\"国家添加\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 634475502964805, '', NULL, NULL, 'system:country:add', '', 2, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:42:42', 1, '2025-01-23 01:42:42');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636764885725253, '{\"zh_CN\":\"国家修改\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 634475502964805, '', NULL, NULL, 'system:country:edit', '', 3, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:42:59', 1, '2025-01-23 01:42:59');
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (636764962709573, '{\"zh_CN\":\"国家删除\",\"en_US\":null,\"ru_RU\":\"\"}', 'F', 634475502964805, '', NULL, NULL, 'system:country:delete', '', 4, '0', '1', 0, 0, 0, 0, '1', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-01-23 01:43:18', 1, '2025-01-23 01:43:18');
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
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `menu_type`, `parent_id`, `icon`, `component`, `query_param`, `perms`, `path`, `order_num`, `is_cache`, `is_frame`, `is_profit`, `is_dealer`, `is_agent`, `is_tenant`, `is_platform_apply`, `is_dealer_apply`, `is_agent_apply`, `is_tenant_apply`, `visible`, `status`, `remark`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (643188978524229, '{\"zh_CN\":\"平台设置\",\"en_US\":null,\"ru_RU\":\"\"}', 'C', 6, 'dashboard', 'platform/setting/list', NULL, 'platform:setting:list', 'setting', 1000, '1', '1', 0, 0, 0, 1, '0', '1', '1', '1', '0', '1', NULL, 1, NULL, '2025-02-10 05:22:41', 1, '2025-02-10 05:23:00');
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
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `notice_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告内容',
  `notice_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告标题',
  `notice_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告类型（1通知 2公告）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告状态（0正常 1关闭）',
  `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
BEGIN;
INSERT INTO `sys_notice` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `notice_content`, `notice_title`, `notice_type`, `remark`, `status`, `tenant_id`) VALUES (1, 1, 103, '2024-08-15 10:34:57', 1, '2024-11-27 16:32:56', '<p>5paw54mI5pys5YaF5a65墨迹</p>', '温馨提醒：2018-07-01 新版本发布啦', '1', '管理员', '0', 10000);
INSERT INTO `sys_notice` (`id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`, `notice_content`, `notice_title`, `notice_type`, `remark`, `status`, `tenant_id`) VALUES (2, 1, 103, '2024-08-15 10:34:57', 1, '2024-08-15 10:34:57', '57u05oqk5YaF5a65', '维护通知：2018-07-01 系统凌晨维护', '1', '管理员', '0', 0);
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
  `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
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
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648265062391877, '2222_2025_02_24_00b43e9472ff469b8a981d3523ddb18b.jpeg', '.jpeg', '2222_2025_02_10_b29afd4484b54eb7b48a1681b0f97523.jpeg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_24_00b43e9472ff469b8a981d3523ddb18b.jpeg', 10000, 1, NULL, '2025-02-24 13:37:19', 1, '2025-02-24 13:37:19');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648265205420101, '2222_2025_02_24_311c050e96b1483d96c381c710859b19.jpeg', '.jpeg', '2222_2025_02_10_b29afd4484b54eb7b48a1681b0f97523.jpeg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_24_311c050e96b1483d96c381c710859b19.jpeg', 10000, 1, NULL, '2025-02-24 13:37:54', 1, '2025-02-24 13:37:54');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648265259102277, '2222_2025_02_24_d84172ed008f49cf8265008aae4581d7.jpeg', '.jpeg', '2222_2025_02_10_b29afd4484b54eb7b48a1681b0f97523.jpeg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_24_d84172ed008f49cf8265008aae4581d7.jpeg', 10000, 1, NULL, '2025-02-24 13:38:08', 1, '2025-02-24 13:38:08');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648265290317893, '2222_2025_02_24_7ead1ebbdb0c4badb4242b1a2d2c542e.jpeg', '.jpeg', '2222_2025_02_10_b29afd4484b54eb7b48a1681b0f97523.jpeg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_24_7ead1ebbdb0c4badb4242b1a2d2c542e.jpeg', 10000, 1, NULL, '2025-02-24 13:38:15', 1, '2025-02-24 13:38:15');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648265430155333, '2222_2025_02_24_a33a2ca998d24cca8499cbb29b403a09.jpeg', '.jpeg', '2222_2025_02_10_b29afd4484b54eb7b48a1681b0f97523.jpeg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_24_a33a2ca998d24cca8499cbb29b403a09.jpeg', 10000, 1, NULL, '2025-02-24 13:38:49', 1, '2025-02-24 13:38:49');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648277726568517, '2222_2025_02_24_f401a7436e1a4606aa6735618b921063jpg', 'jpg', '1740407318_-1802568391_ef5b7f4a-a149da78_1_169127419_T133_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_24_f401a7436e1a4606aa6735618b921063jpg', NULL, 1, NULL, '2025-02-24 14:28:51', 1, '2025-02-24 14:28:51');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648397909561413, '2222_2025_02_25_936cf74bfb3a476ebe876075d4c8516fjpg', 'jpg', '1740436655_11260591_49f6788f-aaae2325_0_39490672_T0_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_25_936cf74bfb3a476ebe876075d4c8516fjpg', NULL, 1, NULL, '2025-02-24 22:37:53', 1, '2025-02-24 22:37:53');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648417783328837, '2222_2025_02_25_ba97fa8ad5b24980b998687c36531680jpg', 'jpg', '1740441480_1631402941_ee30ef2d-0252b8e3_0_78304140_T92_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_25_ba97fa8ad5b24980b998687c36531680jpg', NULL, 1, NULL, '2025-02-24 23:58:45', 1, '2025-02-24 23:58:45');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648418244104261, '2222_2025_02_25_4351dee83ac849ffb10ff80b5aa2b34ajpg', 'jpg', '1740441590_1631516335_ee30ef2d-0252b8e3_1_78306941_T94_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_25_4351dee83ac849ffb10ff80b5aa2b34ajpg', NULL, 1, NULL, '2025-02-25 00:00:37', 1, '2025-02-25 00:00:37');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648418360987717, '2222_2025_02_25_7ed4f67286664ed69aee3783a2733aa4jpg', 'jpg', '1740441656_-1768291144_c6f6446b-f5f5373d_0_111352057_T75_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_25_7ed4f67286664ed69aee3783a2733aa4jpg', NULL, 1, NULL, '2025-02-25 00:01:06', 1, '2025-02-25 00:01:06');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648435822420037, '2222_2025_02_25_4e9e24d902374dcead24b2ff16fb97d9.jpeg', '.jpeg', '2222_2025_02_10_b29afd4484b54eb7b48a1681b0f97523.jpeg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_25_4e9e24d902374dcead24b2ff16fb97d9.jpeg', 10000, 1, NULL, '2025-02-25 01:12:09', 1, '2025-02-25 01:12:09');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648435882430533, '2222_2025_02_25_6c67e38efe3d411e9fe4c4bf08b30800.jpeg', '.jpeg', '2222_2025_02_10_b29afd4484b54eb7b48a1681b0f97523.jpeg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_25_6c67e38efe3d411e9fe4c4bf08b30800.jpeg', 10000, 1, NULL, '2025-02-25 01:12:24', 1, '2025-02-25 01:12:24');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (648435949678661, '2222_2025_02_25_bbe7540c4cb446cd8a2a823830470f8e.jpeg', '.jpeg', '2222_2025_02_10_b29afd4484b54eb7b48a1681b0f97523.jpeg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_02_25_bbe7540c4cb446cd8a2a823830470f8e.jpeg', 10000, 1, NULL, '2025-02-25 01:12:40', 1, '2025-02-25 01:12:40');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (651614171045957, '2222_2025_03_06_7aec32a384c1444db3e37021102a30bc.jpeg', '.jpeg', '2222_2025_02_25_4e9e24d902374dcead24b2ff16fb97d9.jpeg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_06_7aec32a384c1444db3e37021102a30bc.jpeg', 10000, 1, NULL, '2025-03-06 00:44:53', 1, '2025-03-06 00:44:53');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654392659095621, '2222_2025_03_14_de1b0a4f09db454a8bed735786f24e6cjpg', 'jpg', '1741900223_122326046_df983aff-518921f1_0_124499282_T19_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_de1b0a4f09db454a8bed735786f24e6cjpg', NULL, 1, NULL, '2025-03-13 21:10:35', 1, '2025-03-13 21:10:35');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654393772032069, '2222_2025_03_14_7769c6491b134d5ea051960189c43dadjpg', 'jpg', '1741900475_131093080_417151c4-8a4d6079_1_24939155_T2_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_7769c6491b134d5ea051960189c43dadjpg', NULL, 1, NULL, '2025-03-13 21:15:07', 1, '2025-03-13 21:15:07');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654393833132101, '2222_2025_03_14_5ecdab82bbdd417098842e84383f7771jpg', 'jpg', '1741900475_131108078_417151c4-8a4d6079_0_24939154_T4_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_5ecdab82bbdd417098842e84383f7771jpg', NULL, 1, NULL, '2025-03-13 21:15:21', 1, '2025-03-13 21:15:21');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654393910177861, '2222_2025_03_14_505d30ec517e48fd9123898918ea3526jpg', 'jpg', '1741900519_131126809_417151c4-8a4d6079_0_24939154_T7_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_505d30ec517e48fd9123898918ea3526jpg', NULL, 1, NULL, '2025-03-13 21:15:40', 1, '2025-03-13 21:15:40');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654393911865413, '2222_2025_03_14_c571fbb5d1f3471bbcf020861a7c5ebajpg', 'jpg', '1741900504_131126979_417151c4-8a4d6079_1_24939155_T9_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_c571fbb5d1f3471bbcf020861a7c5ebajpg', NULL, 1, NULL, '2025-03-13 21:15:41', 1, '2025-03-13 21:15:41');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654410877321285, '2222_2025_03_14_ebc5ce3327e742e0abc9eebfdf1f9cb1jpg', 'jpg', '1741904675_127523764_ee30ef2d-0252b8e3_1_57053656_T3_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_ebc5ce3327e742e0abc9eebfdf1f9cb1jpg', NULL, 1, NULL, '2025-03-13 22:24:43', 1, '2025-03-13 22:24:43');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654426473611333, '2222_2025_03_14_a252cfa2b53046b5b5b932f2341c1bbdjpg', 'jpg', '1741909718_129706244_87afae74-e7d646d1_1_116322143_T3_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_a252cfa2b53046b5b5b932f2341c1bbdjpg', NULL, 1, NULL, '2025-03-13 23:28:10', 1, '2025-03-13 23:28:10');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654432042496069, '2222_2025_03_14_4b60b913a93e4b0283fa33d0ab5887e6jpg', 'jpg', '1741909818_141599368_88474118-389f1f8b_1_113426913_T4_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_4b60b913a93e4b0283fa33d0ab5887e6jpg', NULL, 1, NULL, '2025-03-13 23:50:50', 1, '2025-03-13 23:50:50');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654432046358597, '2222_2025_03_14_99726b431798430bb22db65873bed794jpg', 'jpg', '1741909833_142972109_ef5b7f4a-a149da78_1_43521191_T4_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_99726b431798430bb22db65873bed794jpg', NULL, 1, NULL, '2025-03-13 23:50:51', 1, '2025-03-13 23:50:51');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654432627064901, '2222_2025_03_14_c617c501e8f74a60ba85e222442a45bdjpg', 'jpg', '1741911182_131207425_87afae74-e7d646d1_0_118115442_T5_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_c617c501e8f74a60ba85e222442a45bdjpg', NULL, 1, NULL, '2025-03-13 23:53:13', 1, '2025-03-13 23:53:13');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654433763786821, '2222_2025_03_14_091fa64d5e8f4d3ca7d6297b474c270bjpg', 'jpg', '1741910235_133109910_ee30ef2d-0252b8e3_0_58067255_T5_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_091fa64d5e8f4d3ca7d6297b474c270bjpg', NULL, 1, NULL, '2025-03-13 23:57:50', 1, '2025-03-13 23:57:50');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654435188224069, '2222_2025_03_14_ce7afd40f6a44e9699f0758af99349edjpg', 'jpg', '1741910583_16405995_49f6788f-aaae2325_0_50074677_T0_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_ce7afd40f6a44e9699f0758af99349edjpg', NULL, 1, NULL, '2025-03-14 00:03:38', 1, '2025-03-14 00:03:38');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654436036673605, '2222_2025_03_14_a6db82750fb84f57a460aa999cd724fcjpg', 'jpg', '1741910783_141411129_417151c4-8a4d6079_1_25142855_T11_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_a6db82750fb84f57a460aa999cd724fcjpg', NULL, 1, NULL, '2025-03-14 00:07:05', 1, '2025-03-14 00:07:05');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654439687176261, '2222_2025_03_14_50ac9489b9e8430e83b149dc5470f13fjpg', 'jpg', '1741911671_133809281_df983aff-518921f1_0_125843782_T29_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_50ac9489b9e8430e83b149dc5470f13fjpg', NULL, 1, NULL, '2025-03-14 00:21:56', 1, '2025-03-14 00:21:56');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654440694149189, '2222_2025_03_14_61d26c8421694765b448108f6d3064c9jpg', 'jpg', '1741911949_134055279_df983aff-518921f1_0_125843782_T31_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_61d26c8421694765b448108f6d3064c9jpg', NULL, 1, NULL, '2025-03-14 00:26:02', 1, '2025-03-14 00:26:02');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654441016512581, '2222_2025_03_14_79a50f5744cd4221958a94fc83dd6be0jpg', 'jpg', '1741912030_134133530_df983aff-518921f1_1_124741383_T33_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_79a50f5744cd4221958a94fc83dd6be0jpg', NULL, 1, NULL, '2025-03-14 00:27:21', 1, '2025-03-14 00:27:21');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654441430040645, '2222_2025_03_14_0e5886de5aa04d4c8986d34847830874jpg', 'jpg', '1741912104_134980912_ee30ef2d-0252b8e3_1_58111456_T7_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_0e5886de5aa04d4c8986d34847830874jpg', NULL, 1, NULL, '2025-03-14 00:29:02', 1, '2025-03-14 00:29:02');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654442987679813, '2222_2025_03_14_d9df3d5b5f0b4bddafb406c1e2ffac56jpg', 'jpg', '1741912488_145645551_ef5b7f4a-a149da78_0_44431090_T6_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_d9df3d5b5f0b4bddafb406c1e2ffac56jpg', NULL, 1, NULL, '2025-03-14 00:35:22', 1, '2025-03-14 00:35:22');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654445117308997, '2222_2025_03_14_08115090949e4d52bdc4d1423f77eedfjpg', 'jpg', '1741914241_134257806_87afae74-e7d646d1_1_118376843_T7_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_08115090949e4d52bdc4d1423f77eedfjpg', NULL, 1, NULL, '2025-03-14 00:44:02', 1, '2025-03-14 00:44:02');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654446033731653, '2222_2025_03_14_e1b4041259dc49ca9bc92d3b2cb0a5a1jpg', 'jpg', '1741913234_135358670_df983aff-518921f1_0_125932482_T35_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_e1b4041259dc49ca9bc92d3b2cb0a5a1jpg', NULL, 1, NULL, '2025-03-14 00:47:46', 1, '2025-03-14 00:47:46');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654447432511557, '2222_2025_03_14_5f87005e37a24c49b39fc58ca4553b52jpg', 'jpg', '1741913598_145357661_88474118-389f1f8b_0_110861012_T6_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_5f87005e37a24c49b39fc58ca4553b52jpg', NULL, 1, NULL, '2025-03-14 00:53:27', 1, '2025-03-14 00:53:27');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654459189583941, '2222_2025_03_14_2018648536704507ad94a40706163321jpg', 'jpg', '1741916464_77605042_c6f6446b-f5f5373d_1_88067147_T17_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_2018648536704507ad94a40706163321jpg', NULL, 1, NULL, '2025-03-14 01:41:18', 1, '2025-03-14 01:41:18');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654480645681221, '2222_2025_03_14_60f250ffb2fa428e9f2d91170bf36359jpg', 'jpg', '1741921680_143808138_df983aff-518921f1_1_126108483_T37_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_60f250ffb2fa428e9f2d91170bf36359jpg', NULL, 1, NULL, '2025-03-14 03:08:36', 1, '2025-03-14 03:08:36');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654484994969669, '2222_2025_03_14_deec7357477c4d889a61a979f5270c1cjpg', 'jpg', '1741922767_155900682_ef5b7f4a-a149da78_0_44431090_T8_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_deec7357477c4d889a61a979f5270c1cjpg', NULL, 1, NULL, '2025-03-14 03:26:18', 1, '2025-03-14 03:26:18');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654487464603717, '2222_2025_03_14_b9318077c7a443f98a8369fa43750d6ejpg', 'jpg', '1741923368_155130550_88474118-389f1f8b_1_113426913_T8_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_b9318077c7a443f98a8369fa43750d6ejpg', NULL, 1, NULL, '2025-03-14 03:36:21', 1, '2025-03-14 03:36:21');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654489144172613, '2222_2025_03_14_f7c48498d3b44fa7adddb540e7932e51jpg', 'jpg', '1741923779_145884712_df983aff-518921f1_0_125932482_T39_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_f7c48498d3b44fa7adddb540e7932e51jpg', NULL, 1, NULL, '2025-03-14 03:43:11', 1, '2025-03-14 03:43:11');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654493457788997, '2222_2025_03_14_3308f04ab0244a268f98c3116a24a54bjpg', 'jpg', '1741924836_30632106_49f6788f-aaae2325_0_50074677_T2_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_3308f04ab0244a268f98c3116a24a54bjpg', NULL, 1, NULL, '2025-03-14 04:00:44', 1, '2025-03-14 04:00:44');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654494545498181, '2222_2025_03_14_efc196cb0d7c4b7c9e74f560c5b6438ejpg', 'jpg', '1741926338_146325474_87afae74-e7d646d1_1_118376843_T9_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_efc196cb0d7c4b7c9e74f560c5b6438ejpg', NULL, 1, NULL, '2025-03-14 04:05:09', 1, '2025-03-14 04:05:09');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654495222734917, '2222_2025_03_14_c1d71fa4290b4ad4ba5ce50012c8278ajpg', 'jpg', '1741925265_147367346_df983aff-518921f1_1_126108483_T41_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_c1d71fa4290b4ad4ba5ce50012c8278ajpg', NULL, 1, NULL, '2025-03-14 04:07:55', 1, '2025-03-14 04:07:55');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654496178249797, '2222_2025_03_14_c6fa1367f5de4f8f8a0a5c94ff32a417jpg', 'jpg', '1741925499_148347902_ee30ef2d-0252b8e3_0_58067255_T9_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_c6fa1367f5de4f8f8a0a5c94ff32a417jpg', NULL, 1, NULL, '2025-03-14 04:11:48', 1, '2025-03-14 04:11:48');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654496520544325, '2222_2025_03_14_59853a413a07472eb6ec0fba1c3d1b1ajpg', 'jpg', '1741925550_158714897_ef5b7f4a-a149da78_1_44795891_T10_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_59853a413a07472eb6ec0fba1c3d1b1ajpg', NULL, 1, NULL, '2025-03-14 04:13:12', 1, '2025-03-14 04:13:12');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654498067247173, '2222_2025_03_14_e6cfe51a88b940a98238ce92b7845c4fjpg', 'jpg', '1741925933_87097015_c6f6446b-f5f5373d_1_89331247_T19_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_e6cfe51a88b940a98238ce92b7845c4fjpg', NULL, 1, NULL, '2025-03-14 04:19:29', 1, '2025-03-14 04:19:29');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654502798200901, '2222_2025_03_14_8138f91f800940a5825b067e08099d2cjpg', 'jpg', '1741927083_158875500_88474118-389f1f8b_1_113702213_T10_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_8138f91f800940a5825b067e08099d2cjpg', NULL, 1, NULL, '2025-03-14 04:38:44', 1, '2025-03-14 04:38:44');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654518722682949, '2222_2025_03_14_fd661f1db2674c9890074c8762ed8e7djpg', 'jpg', '1741930981_153105993_df983aff-518921f1_1_126686383_T43_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_fd661f1db2674c9890074c8762ed8e7djpg', NULL, 1, NULL, '2025-03-14 05:43:32', 1, '2025-03-14 05:43:32');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654519621288005, '2222_2025_03_14_4768d4a14f7f4b8c9d751c34bce42ce3jpg', 'jpg', '1741931205_37019646_49f6788f-aaae2325_0_50544677_T4_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_4768d4a14f7f4b8c9d751c34bce42ce3jpg', NULL, 1, NULL, '2025-03-14 05:47:11', 1, '2025-03-14 05:47:11');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654520018731077, '2222_2025_03_14_cc37ce0c850c4fd89714b5e74f196115jpg', 'jpg', '1741931315_163079781_88474118-389f1f8b_1_113702213_T12_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_cc37ce0c850c4fd89714b5e74f196115jpg', NULL, 1, NULL, '2025-03-14 05:48:48', 1, '2025-03-14 05:48:48');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654527876337733, '2222_2025_03_14_16f9c89c0c7542cc8c7cf2b9d94e3e35jpg', 'jpg', '1741933237_166369960_ef5b7f4a-a149da78_1_44795891_T12_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_16f9c89c0c7542cc8c7cf2b9d94e3e35jpg', NULL, 1, NULL, '2025-03-14 06:20:47', 1, '2025-03-14 06:20:47');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654531855843397, '2222_2025_03_14_78a7830d307f47dc8da5f7a0402077a5jpg', 'jpg', '1741934174_167343200_ef5b7f4a-a149da78_1_45396791_T14_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_78a7830d307f47dc8da5f7a0402077a5jpg', NULL, 1, NULL, '2025-03-14 06:36:58', 1, '2025-03-14 06:36:58');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654543522119749, '2222_2025_03_14_c4466022ef314c6d9781c96f5e7c36cbjpg', 'jpg', '1741937059_159906994_ee30ef2d-0252b8e3_1_58111456_T11_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_c4466022ef314c6d9781c96f5e7c36cbjpg', NULL, 1, NULL, '2025-03-14 07:24:27', 1, '2025-03-14 07:24:27');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654555483435077, '2222_2025_03_14_70d405cd7fdc44a7ac3a859a7f838765jpg', 'jpg', '1741939978_162079712_df983aff-518921f1_1_126686383_T45_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_70d405cd7fdc44a7ac3a859a7f838765jpg', NULL, 1, NULL, '2025-03-14 08:13:07', 1, '2025-03-14 08:13:07');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654558124040261, '2222_2025_03_14_c1f58b08ef124ab69404d3a35e90d7fdjpg', 'jpg', '1741940622_173755218_ef5b7f4a-a149da78_1_45396791_T16_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_c1f58b08ef124ab69404d3a35e90d7fdjpg', NULL, 1, NULL, '2025-03-14 08:23:52', 1, '2025-03-14 08:23:52');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654562917294149, '2222_2025_03_14_ebd368d954b74be690cf012a1365521cjpg', 'jpg', '1741941766_173551779_88474118-389f1f8b_0_113918012_T14_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_ebd368d954b74be690cf012a1365521cjpg', NULL, 1, NULL, '2025-03-14 08:43:22', 1, '2025-03-14 08:43:22');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654566515892293, '2222_2025_03_14_c802243d47b64de4a5795aae8bb23f60jpg', 'jpg', '1741942644_175803808_ef5b7f4a-a149da78_1_45517091_T18_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_c802243d47b64de4a5795aae8bb23f60jpg', NULL, 1, NULL, '2025-03-14 08:58:00', 1, '2025-03-14 08:58:00');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654581182955589, '2222_2025_03_14_0d78f0542cf544f3940c69b6c9e1ba95jpg', 'jpg', '1741946226_178011545_88474118-389f1f8b_1_114088113_T16_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_0d78f0542cf544f3940c69b6c9e1ba95jpg', NULL, 1, NULL, '2025-03-14 09:57:41', 1, '2025-03-14 09:57:41');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654583015723077, '2222_2025_03_14_41e806c9b25f463baeea24a981b103d8jpg', 'jpg', '1741946698_52497101_49f6788f-aaae2325_0_50544677_T10_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_41e806c9b25f463baeea24a981b103d8jpg', NULL, 1, NULL, '2025-03-14 10:05:09', 1, '2025-03-14 10:05:09');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654586404012101, '2222_2025_03_14_9898564813784496b87b8f7e7cf8aadbjpg', 'jpg', '1741947528_179287530_88474118-389f1f8b_0_113918012_T18_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_9898564813784496b87b8f7e7cf8aadbjpg', NULL, 1, NULL, '2025-03-14 10:18:56', 1, '2025-03-14 10:18:56');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654586475286597, '2222_2025_03_14_2d6a79cfd65e4e30b801bb442c7eec94jpg', 'jpg', '1741947517_111032272_c1ea6750-ad8a5657_0_93878787_T1_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_2d6a79cfd65e4e30b801bb442c7eec94jpg', NULL, 1, NULL, '2025-03-14 10:19:13', 1, '2025-03-14 10:19:13');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654587245985861, '2222_2025_03_14_1fef678972a54bf598e8129178f78b53jpg', 'jpg', '1741947735_108870374_c6f6446b-f5f5373d_1_89331247_T21_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_1fef678972a54bf598e8129178f78b53jpg', NULL, 1, NULL, '2025-03-14 10:22:21', 1, '2025-03-14 10:22:21');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654587501240389, '2222_2025_03_14_a525aee48fb347de8aeb8902fd2d397fjpg', 'jpg', '1741947793_111284204_c1ea6750-ad8a5657_0_93878787_T3_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_a525aee48fb347de8aeb8902fd2d397fjpg', NULL, 1, NULL, '2025-03-14 10:23:24', 1, '2025-03-14 10:23:24');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654587769663557, '2222_2025_03_14_b060bf1aa7ad41d094a70cc51748576ejpg', 'jpg', '1741947834_170708423_ee30ef2d-0252b8e3_1_59084256_T13_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_b060bf1aa7ad41d094a70cc51748576ejpg', NULL, 1, NULL, '2025-03-14 10:24:29', 1, '2025-03-14 10:24:29');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654598501511237, '2222_2025_03_14_b0c6df7adf7942d5be5357535b91e74bjpg', 'jpg', '1741950483_183614626_ef5b7f4a-a149da78_1_45517091_T20_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_b0c6df7adf7942d5be5357535b91e74bjpg', NULL, 1, NULL, '2025-03-14 11:08:09', 1, '2025-03-14 11:08:09');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654600204705861, '2222_2025_03_14_46b2c79f18f34168a67770cffa77775ejpg', 'jpg', '1741950892_181492106_417151c4-8a4d6079_1_25142855_T13_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_46b2c79f18f34168a67770cffa77775ejpg', NULL, 1, NULL, '2025-03-14 11:15:05', 1, '2025-03-14 11:15:05');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654606117519429, '2222_2025_03_14_259fd52f45614eb8ad49e1bf962440f4jpg', 'jpg', '1741952316_58136864_49f6788f-aaae2325_0_51473777_T12_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_259fd52f45614eb8ad49e1bf962440f4jpg', NULL, 1, NULL, '2025-03-14 11:39:09', 1, '2025-03-14 11:39:09');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654606981603397, '2222_2025_03_14_bf5aff334e1f40c79b9ea503cb28bd38jpg', 'jpg', '1741952527_185684928_ef5b7f4a-a149da78_0_45696990_T22_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_bf5aff334e1f40c79b9ea503cb28bd38jpg', NULL, 1, NULL, '2025-03-14 11:42:40', 1, '2025-03-14 11:42:40');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654611971108933, '2222_2025_03_14_b83613ea3d4b44ad8916fff8c40d2aeajpg', 'jpg', '1741954986_174995528_87afae74-e7d646d1_1_120781043_T11_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_b83613ea3d4b44ad8916fff8c40d2aeajpg', NULL, 1, NULL, '2025-03-14 12:02:58', 1, '2025-03-14 12:02:58');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654629265244229, '2222_2025_03_14_ec906cdd56684d5f93f9d531acd07d23jpg', 'jpg', '1741957986_189751257_88474118-389f1f8b_1_114088113_T20_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_ec906cdd56684d5f93f9d531acd07d23jpg', NULL, 1, NULL, '2025-03-14 13:13:20', 1, '2025-03-14 13:13:20');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654629908455493, '2222_2025_03_14_73e81e54a30a431396c3d2173aa1be7bjpg', 'jpg', '1741959386_179374828_87afae74-e7d646d1_0_118115442_T13_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_73e81e54a30a431396c3d2173aa1be7bjpg', NULL, 1, NULL, '2025-03-14 13:15:57', 1, '2025-03-14 13:15:57');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654635808120901, '2222_2025_03_14_ea5fc54377b444b49d7942abc9c76e01jpg', 'jpg', '1741959591_192722361_ef5b7f4a-a149da78_0_45696990_T24_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_ea5fc54377b444b49d7942abc9c76e01jpg', NULL, 1, NULL, '2025-03-14 13:39:57', 1, '2025-03-14 13:39:57');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654636621717573, '2222_2025_03_14_e2b52aacd1c0423eb6dd54e26c95dbedjpg', 'jpg', '1741959768_181891063_df983aff-518921f1_0_127703282_T47_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_e2b52aacd1c0423eb6dd54e26c95dbedjpg', NULL, 1, NULL, '2025-03-14 13:43:16', 1, '2025-03-14 13:43:16');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654642751254597, '2222_2025_03_14_4200cf66136a43b48a06252946b8d9a3jpg', 'jpg', '1741961261_191879599_417151c4-8a4d6079_1_26336555_T15_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_4200cf66136a43b48a06252946b8d9a3jpg', NULL, 1, NULL, '2025-03-14 14:08:12', 1, '2025-03-14 14:08:12');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654643362922565, '2222_2025_03_14_08f8ea9c10994d5081087a8db613c77ejpg', 'jpg', '1741961411_193193705_88474118-389f1f8b_1_114520913_T22_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_08f8ea9c10994d5081087a8db613c77ejpg', NULL, 1, NULL, '2025-03-14 14:10:42', 1, '2025-03-14 14:10:42');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654644303536197, '2222_2025_03_14_2b84974d13ef4316a1d6f18f111767cfjpg', 'jpg', '1741962880_182889106_87afae74-e7d646d1_0_120959642_T15_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_2b84974d13ef4316a1d6f18f111767cfjpg', NULL, 1, NULL, '2025-03-14 14:14:31', 1, '2025-03-14 14:14:31');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654647780622405, '2222_2025_03_14_146007079c0747fc980c697f381fa0c7jpg', 'jpg', '1741962491_184615532_df983aff-518921f1_1_127774883_T49_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_14_146007079c0747fc980c697f381fa0c7jpg', NULL, 1, NULL, '2025-03-14 14:28:40', 1, '2025-03-14 14:28:40');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654841887817797, '2222_2025_03_15_5828bc9980e146789268676af8cdb954jpg', 'jpg', '1742011107_231123582_87afae74-e7d646d1_1_121901343_T18_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_5828bc9980e146789268676af8cdb954jpg', NULL, 1, NULL, '2025-03-15 03:38:30', 1, '2025-03-15 03:38:30');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654847315308613, '2222_2025_03_15_989eccbab47a49e6bef41ca24a972a52jpg', 'jpg', '1742011222_234074568_ee30ef2d-0252b8e3_0_61135855_T16_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_989eccbab47a49e6bef41ca24a972a52jpg', NULL, 1, NULL, '2025-03-15 04:00:35', 1, '2025-03-15 04:00:35');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654853890748485, '2222_2025_03_15_de76341a51e1480c8c539925013b8971jpg', 'jpg', '1742012825_235678239_ee30ef2d-0252b8e3_1_61290356_T18_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_de76341a51e1480c8c539925013b8971jpg', NULL, 1, NULL, '2025-03-15 04:27:20', 1, '2025-03-15 04:27:20');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654854728634437, '2222_2025_03_15_d2e599c5bbc84e3d808f9f363055582ajpg', 'jpg', '1742013003_235884375_ee30ef2d-0252b8e3_1_61536556_T20_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_d2e599c5bbc84e3d808f9f363055582ajpg', NULL, 1, NULL, '2025-03-15 04:30:45', 1, '2025-03-15 04:30:45');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654855298756677, '2222_2025_03_15_4b600c0ed5ca440382307089f71b5d05jpg', 'jpg', '1742013151_246307551_ef5b7f4a-a149da78_0_47333790_T27_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_4b600c0ed5ca440382307089f71b5d05jpg', NULL, 1, NULL, '2025-03-15 04:33:04', 1, '2025-03-15 04:33:04');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654858310234181, '2222_2025_03_15_984bb792c5cf441f94061815c6d253ebjpg', 'jpg', '1742015152_235135019_87afae74-e7d646d1_1_121901343_T20_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_984bb792c5cf441f94061815c6d253ebjpg', NULL, 1, NULL, '2025-03-15 04:45:19', 1, '2025-03-15 04:45:19');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654870242283589, '2222_2025_03_15_f333e1250bb7452d9c3d54652c6df817jpg', 'jpg', '1742016788_239672381_ee30ef2d-0252b8e3_0_61545855_T22_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_f333e1250bb7452d9c3d54652c6df817jpg', NULL, 1, NULL, '2025-03-15 05:33:52', 1, '2025-03-15 05:33:52');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654876486361157, '2222_2025_03_15_5a29e4ab977f4ab5aee6869f04b1b291jpg', 'jpg', '1742018326_37744377_49f6788f-aaae2325_0_25052441_T1_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_5a29e4ab977f4ab5aee6869f04b1b291jpg', NULL, 1, NULL, '2025-03-15 05:59:17', 1, '2025-03-15 05:59:17');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654878129156165, '2222_2025_03_15_71880a61bb8242cebfc5b10aa62e7697jpg', 'jpg', '1742018750_38145570_49f6788f-aaae2325_0_25052441_T3_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_71880a61bb8242cebfc5b10aa62e7697jpg', NULL, 1, NULL, '2025-03-15 06:05:58', 1, '2025-03-15 06:05:58');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654878495977541, '2222_2025_03_15_619507b7a6b749d1ab5e50c546525a84jpg', 'jpg', '1742018811_251970800_ef5b7f4a-a149da78_1_47628791_T29_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_619507b7a6b749d1ab5e50c546525a84jpg', NULL, 1, NULL, '2025-03-15 06:07:27', 1, '2025-03-15 06:07:27');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654882916368453, '2222_2025_03_15_66b3037578e34b18b509b0da5a0a8b7djpg', 'jpg', '1742019895_242019332_df983aff-518921f1_1_128926083_T52_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_66b3037578e34b18b509b0da5a0a8b7djpg', NULL, 1, NULL, '2025-03-15 06:25:27', 1, '2025-03-15 06:25:27');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654883488161861, '2222_2025_03_15_b18169fe055342039d5bb449e59d25d8jpg', 'jpg', '1742020054_250652406_417151c4-8a4d6079_1_26336555_T18_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_b18169fe055342039d5bb449e59d25d8jpg', NULL, 1, NULL, '2025-03-15 06:27:46', 1, '2025-03-15 06:27:46');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654884133326917, '2222_2025_03_15_3239682f6630472a967798aa178fa8a9jpg', 'jpg', '1742021431_241439474_87afae74-e7d646d1_0_122351642_T22_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_3239682f6630472a967798aa178fa8a9jpg', NULL, 1, NULL, '2025-03-15 06:30:24', 1, '2025-03-15 06:30:24');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654890379305029, '2222_2025_03_15_ae359f01c9b14d709fc4993d966dbe42jpg', 'jpg', '1742021708_182876571_c6f6446b-f5f5373d_0_92963246_T24_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_ae359f01c9b14d709fc4993d966dbe42jpg', NULL, 1, NULL, '2025-03-15 06:55:49', 1, '2025-03-15 06:55:49');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654895394410565, '2222_2025_03_15_af0682bbd6884acba662988a3727c8aajpg', 'jpg', '1742022956_256089347_ef5b7f4a-a149da78_1_47628791_T31_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_af0682bbd6884acba662988a3727c8aajpg', NULL, 1, NULL, '2025-03-15 07:16:13', 1, '2025-03-15 07:16:13');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654900151234629, '2222_2025_03_15_74377ca98b38459ea067f5a155abb040jpg', 'jpg', '1742024123_246971459_ee30ef2d-0252b8e3_0_61545855_T24_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_74377ca98b38459ea067f5a155abb040jpg', NULL, 1, NULL, '2025-03-15 07:35:34', 1, '2025-03-15 07:35:34');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654900640907333, '2222_2025_03_15_51359e212b864179bdab2ce779d1c3c3jpg', 'jpg', '1742024210_185383173_c6f6446b-f5f5373d_1_93045347_T26_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_51359e212b864179bdab2ce779d1c3c3jpg', NULL, 1, NULL, '2025-03-15 07:37:34', 1, '2025-03-15 07:37:34');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654906819776581, '2222_2025_03_15_68c0ee948a694046b3fb4ba441d1e09fjpg', 'jpg', '1742025748_248602581_ee30ef2d-0252b8e3_1_61536556_T26_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_68c0ee948a694046b3fb4ba441d1e09fjpg', NULL, 1, NULL, '2025-03-15 08:02:42', 1, '2025-03-15 08:02:42');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654910604742725, '2222_2025_03_15_f379b0f0adb94c03bbb111fff63572c7jpg', 'jpg', '1742026659_46073154_49f6788f-aaae2325_0_25252441_T5_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_f379b0f0adb94c03bbb111fff63572c7jpg', NULL, 1, NULL, '2025-03-15 08:18:06', 1, '2025-03-15 08:18:06');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654933629689925, '2222_2025_03_15_c3e43a67e90040efaa4a5f096ebd63eejpg', 'jpg', '1742033544_253523781_87afae74-e7d646d1_0_122351642_T24_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_c3e43a67e90040efaa4a5f096ebd63eejpg', NULL, 1, NULL, '2025-03-15 09:51:48', 1, '2025-03-15 09:51:48');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654936758333509, '2222_2025_03_15_cdcf581ac55b416f84015a04abe5a64ejpg', 'jpg', '1742033061_194200157_c6f6446b-f5f5373d_1_93045347_T28_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_cdcf581ac55b416f84015a04abe5a64ejpg', NULL, 1, NULL, '2025-03-15 10:04:32', 1, '2025-03-15 10:04:32');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654941135716421, '2222_2025_03_15_0ac52d7195ed481fa54832bcf01ef9fbjpg', 'jpg', '1742034132_267264713_ef5b7f4a-a149da78_0_47333790_T35_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_0ac52d7195ed481fa54832bcf01ef9fbjpg', NULL, 1, NULL, '2025-03-15 10:22:20', 1, '2025-03-15 10:22:20');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654947056525381, '2222_2025_03_15_a0798916f66846cf94ce113be2c29c39jpg', 'jpg', '1742035570_196713515_c6f6446b-f5f5373d_0_92963246_T30_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_a0798916f66846cf94ce113be2c29c39jpg', NULL, 1, NULL, '2025-03-15 10:46:26', 1, '2025-03-15 10:46:26');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654959219560517, '2222_2025_03_15_7d13a09d02bd4416b5234521b0e07df8jpg', 'jpg', '1742039768_259772784_87afae74-e7d646d1_1_123009443_T26_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_7d13a09d02bd4416b5234521b0e07df8jpg', NULL, 1, NULL, '2025-03-15 11:35:55', 1, '2025-03-15 11:35:55');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654976985641029, '2222_2025_03_15_1153cdd1ec8b46358c3b0876775b3852jpg', 'jpg', '1742042870_62280640_49f6788f-aaae2325_0_25252441_T7_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_1153cdd1ec8b46358c3b0876775b3852jpg', NULL, 1, NULL, '2025-03-15 12:48:13', 1, '2025-03-15 12:48:13');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654979799294021, '2222_2025_03_15_bab8389adfa64787858da902a49d41e3jpg', 'jpg', '1742043547_204709074_c6f6446b-f5f5373d_0_93837846_T32_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_bab8389adfa64787858da902a49d41e3jpg', NULL, 1, NULL, '2025-03-15 12:59:40', 1, '2025-03-15 12:59:40');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (654995946295365, '2222_2025_03_15_d1f2cde2a28b4617a1aa90a6f301461djpg', 'jpg', '1742047478_269616517_df983aff-518921f1_0_129351982_T54_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_d1f2cde2a28b4617a1aa90a6f301461djpg', NULL, 1, NULL, '2025-03-15 14:05:22', 1, '2025-03-15 14:05:22');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655000900821061, '2222_2025_03_15_9f2a7d43eff44515b099a2d046db1670jpg', 'jpg', '1742048706_279318358_417151c4-8a4d6079_0_29485454_T20_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_9f2a7d43eff44515b099a2d046db1670jpg', NULL, 1, NULL, '2025-03-15 14:25:31', 1, '2025-03-15 14:25:31');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655004689252421, '2222_2025_03_15_70fe89b97c78489382930768cfb1a938jpg', 'jpg', '1742049617_69044268_49f6788f-aaae2325_0_26579241_T9_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_70fe89b97c78489382930768cfb1a938jpg', NULL, 1, NULL, '2025-03-15 14:40:56', 1, '2025-03-15 14:40:56');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655005100224581, '2222_2025_03_15_6f954e3e4670489ab2620e39c113b8e4jpg', 'jpg', '1742049743_69144596_49f6788f-aaae2325_0_26589741_T11_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_6f954e3e4670489ab2620e39c113b8e4jpg', NULL, 1, NULL, '2025-03-15 14:42:37', 1, '2025-03-15 14:42:37');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655005297537093, '2222_2025_03_15_a0723218ac8549bb817fe10ad581f4dcjpg', 'jpg', '1742049755_69193036_49f6788f-aaae2325_0_26589741_T13_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_a0723218ac8549bb817fe10ad581f4dcjpg', NULL, 1, NULL, '2025-03-15 14:43:25', 1, '2025-03-15 14:43:25');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655008565506117, '2222_2025_03_15_411472ea489e42bcb9a3b626e9a04ca2jpg', 'jpg', '1742050593_272697364_df983aff-518921f1_0_129351982_T56_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_411472ea489e42bcb9a3b626e9a04ca2jpg', NULL, 1, NULL, '2025-03-15 14:56:43', 1, '2025-03-15 14:56:43');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655011042029637, '2222_2025_03_15_23bec0249af94376bd8a5b1398a84a92jpg', 'jpg', '1742051167_47292909_c1ea6750-ad8a5657_0_40011398_T0_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_23bec0249af94376bd8a5b1398a84a92jpg', NULL, 1, NULL, '2025-03-15 15:06:47', 1, '2025-03-15 15:06:47');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655011727163461, '2222_2025_03_15_d2940a675590418081a09b24dadff746jpg', 'jpg', '1742051366_47461065_c1ea6750-ad8a5657_0_40011398_T2_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_d2940a675590418081a09b24dadff746jpg', NULL, 1, NULL, '2025-03-15 15:09:34', 1, '2025-03-15 15:09:34');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655011909754949, '2222_2025_03_15_3f4f45e81eea459cb71871fe4718a203jpg', 'jpg', '1742051384_274259748_ee30ef2d-0252b8e3_1_62346556_T28_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_3f4f45e81eea459cb71871fe4718a203jpg', NULL, 1, NULL, '2025-03-15 15:10:19', 1, '2025-03-15 15:10:19');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655013563699269, '2222_2025_03_15_536c4c90aee4414ea18fe14d1bb26f10jpg', 'jpg', '1742051774_282409753_417151c4-8a4d6079_1_29618655_T24_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_536c4c90aee4414ea18fe14d1bb26f10jpg', NULL, 1, NULL, '2025-03-15 15:17:03', 1, '2025-03-15 15:17:03');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655013769695301, '2222_2025_03_15_b25ac3d59cc74b0bacc2afe6a4332b95jpg', 'jpg', '1742051859_282460777_417151c4-8a4d6079_1_29618655_T26_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_b25ac3d59cc74b0bacc2afe6a4332b95jpg', NULL, 1, NULL, '2025-03-15 15:17:53', 1, '2025-03-15 15:17:53');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655014009749573, '2222_2025_03_15_c739fe92dda1471a8f577428cd52a94ejpg', 'jpg', '1742051890_282518887_417151c4-8a4d6079_1_29618655_T28_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_15_c739fe92dda1471a8f577428cd52a94ejpg', NULL, 1, NULL, '2025-03-15 15:18:52', 1, '2025-03-15 15:18:52');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655028353056837, '2222_2025_03_16_d12167b20c744c19b21c626736c033b1jpg', 'jpg', '1742055425_278273944_ee30ef2d-0252b8e3_1_62346556_T30_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_d12167b20c744c19b21c626736c033b1jpg', NULL, 1, NULL, '2025-03-15 16:17:14', 1, '2025-03-15 16:17:14');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655031384092741, '2222_2025_03_16_4433c2a1b82f4062acbafbafc754cd13jpg', 'jpg', '1742056139_52260360_c1ea6750-ad8a5657_0_40412398_T4_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_4433c2a1b82f4062acbafbafc754cd13jpg', NULL, 1, NULL, '2025-03-15 16:29:34', 1, '2025-03-15 16:29:34');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655076088619077, '2222_2025_03_16_6c29ddb464d9429a8c39648eb25c0825jpg', 'jpg', '1742067070_75752_49f6788f-aaae2325_0_103530149_T0_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_6c29ddb464d9429a8c39648eb25c0825jpg', NULL, 1, NULL, '2025-03-15 19:31:28', 1, '2025-03-15 19:31:28');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655120481685573, '2222_2025_03_16_b322c379c4d7482486e50b5fb1cff6a5jpg', 'jpg', '1742077912_308512687_417151c4-8a4d6079_1_29618655_T30_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_b322c379c4d7482486e50b5fb1cff6a5jpg', NULL, 1, NULL, '2025-03-15 22:32:06', 1, '2025-03-15 22:32:06');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655144125501509, '2222_2025_03_16_b6e5eccc332f42a191b9ecd545bf415bjpg', 'jpg', '1742083682_314283208_417151c4-8a4d6079_0_29485454_T32_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_b6e5eccc332f42a191b9ecd545bf415bjpg', NULL, 1, NULL, '2025-03-16 00:08:18', 1, '2025-03-16 00:08:18');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655157341704261, '2222_2025_03_16_541f8774ebf94458bef9e9aeb68b08d2jpg', 'jpg', '1742088162_308140417_87afae74-e7d646d1_1_123009443_T28_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_541f8774ebf94458bef9e9aeb68b08d2jpg', NULL, 1, NULL, '2025-03-16 01:02:05', 1, '2025-03-16 01:02:05');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655161575235653, '2222_2025_03_16_06daa42fcf7646ee8f36024d1c201872jpg', 'jpg', '1742087949_20946520_49f6788f-aaae2325_0_103530149_T2_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_06daa42fcf7646ee8f36024d1c201872jpg', NULL, 1, NULL, '2025-03-16 01:19:18', 1, '2025-03-16 01:19:18');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655166726479941, '2222_2025_03_16_b6605cf2f1bc4b04991795dd48daa1b0jpg', 'jpg', '1742089179_322340473_ef5b7f4a-a149da78_0_50222590_T37_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_b6605cf2f1bc4b04991795dd48daa1b0jpg', NULL, 1, NULL, '2025-03-16 01:40:16', 1, '2025-03-16 01:40:16');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655172163330117, '2222_2025_03_16_e453795f8d244ae2a80d47b720221f16jpg', 'jpg', '1742090532_323664369_ef5b7f4a-a149da78_0_50222590_T39_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_e453795f8d244ae2a80d47b720221f16jpg', NULL, 1, NULL, '2025-03-16 02:02:23', 1, '2025-03-16 02:02:23');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655173874663493, '2222_2025_03_16_30385dbe49684226bb53c424d3b28b4fjpg', 'jpg', '1742090952_87046075_c1ea6750-ad8a5657_0_40412398_T6_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_30385dbe49684226bb53c424d3b28b4fjpg', NULL, 1, NULL, '2025-03-16 02:09:21', 1, '2025-03-16 02:09:21');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655195568615493, '2222_2025_03_16_de2be04707ed4ddeab1712aff523e34ejpg', 'jpg', '1742097471_317473620_87afae74-e7d646d1_0_124219942_T30_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_de2be04707ed4ddeab1712aff523e34ejpg', NULL, 1, NULL, '2025-03-16 03:37:38', 1, '2025-03-16 03:37:38');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655195644936261, '2222_2025_03_16_b266fb1e47ac406b9708166ec6606b90jpg', 'jpg', '1742096240_326861875_417151c4-8a4d6079_0_30872754_T34_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_b266fb1e47ac406b9708166ec6606b90jpg', NULL, 1, NULL, '2025-03-16 03:37:56', 1, '2025-03-16 03:37:56');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655209671622725, '2222_2025_03_16_a4238aaa2a2e4bbba98bda23316dd6c8jpg', 'jpg', '1742100942_320918025_87afae74-e7d646d1_0_124219942_T32_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_a4238aaa2a2e4bbba98bda23316dd6c8jpg', NULL, 1, NULL, '2025-03-16 04:35:01', 1, '2025-03-16 04:35:01');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655213536407621, '2222_2025_03_16_4a17c9a3b9b444d59f11297b6d78d801jpg', 'jpg', '1742101852_321860160_87afae74-e7d646d1_0_124351942_T34_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_4a17c9a3b9b444d59f11297b6d78d801jpg', NULL, 1, NULL, '2025-03-16 04:50:44', 1, '2025-03-16 04:50:44');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655215864057925, '2222_2025_03_16_2a63b7e38cb74806b3e78fec7b05e273jpg', 'jpg', '1742101176_332962673_88474118-389f1f8b_0_120617512_T25_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_2a63b7e38cb74806b3e78fec7b05e273jpg', NULL, 1, NULL, '2025-03-16 05:00:13', 1, '2025-03-16 05:00:13');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655216138096709, '2222_2025_03_16_cb2f88f388434354af63dd720b79c628jpg', 'jpg', '1742101273_331865675_417151c4-8a4d6079_0_30872754_T36_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_cb2f88f388434354af63dd720b79c628jpg', NULL, 1, NULL, '2025-03-16 05:01:19', 1, '2025-03-16 05:01:19');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655223937986629, '2222_2025_03_16_b9cba292d68443a99999e60c370800e4jpg', 'jpg', '1742103140_336303388_ef5b7f4a-a149da78_1_50743691_T41_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_b9cba292d68443a99999e60c370800e4jpg', NULL, 1, NULL, '2025-03-16 05:33:04', 1, '2025-03-16 05:33:04');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655228549840965, '2222_2025_03_16_4c021d9e84f4433b8e5c6f00e03ad248jpg', 'jpg', '1742104298_337431639_ef5b7f4a-a149da78_1_50743691_T43_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_4c021d9e84f4433b8e5c6f00e03ad248jpg', NULL, 1, NULL, '2025-03-16 05:51:50', 1, '2025-03-16 05:51:50');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655233809940549, '2222_2025_03_16_2079b007149e4faeb45e144d616fe1aajpg', 'jpg', '1742105551_338717599_ef5b7f4a-a149da78_1_50832991_T45_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_2079b007149e4faeb45e144d616fe1aajpg', NULL, 1, NULL, '2025-03-16 06:13:14', 1, '2025-03-16 06:13:14');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655235429695557, '2222_2025_03_16_7932cd594ad34313ac0f759c257c98d8jpg', 'jpg', '1742105981_267117009_c6f6446b-f5f5373d_0_93837846_T38_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_7932cd594ad34313ac0f759c257c98d8jpg', NULL, 1, NULL, '2025-03-16 06:19:49', 1, '2025-03-16 06:19:49');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655237213659205, '2222_2025_03_16_dfb37f7ec62b420eaad417f7ca764195jpg', 'jpg', '1742106392_329263346_ee30ef2d-0252b8e3_1_62857156_T32_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_dfb37f7ec62b420eaad417f7ca764195jpg', NULL, 1, NULL, '2025-03-16 06:27:05', 1, '2025-03-16 06:27:05');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655237987119173, '2222_2025_03_16_4a4aa13d29834c2e8a785ec631e55c93jpg', 'jpg', '1742106604_338363345_88474118-389f1f8b_1_114520913_T27_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_4a4aa13d29834c2e8a785ec631e55c93jpg', NULL, 1, NULL, '2025-03-16 06:30:14', 1, '2025-03-16 06:30:14');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655255011823685, '2222_2025_03_16_b803e853e94d4aff9cfffaa293446fcejpg', 'jpg', '1742110761_342520284_88474118-389f1f8b_0_120617512_T30_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_b803e853e94d4aff9cfffaa293446fcejpg', NULL, 1, NULL, '2025-03-16 07:39:30', 1, '2025-03-16 07:39:30');
INSERT INTO `sys_oss` (`id`, `file_name`, `file_suffix`, `original_name`, `service`, `url`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (655256534646853, '2222_2025_03_16_02d56162f5364626b3578b81f5fb3f5cjpg', 'jpg', '1742111129_344261027_ef5b7f4a-a149da78_1_50832991_T48_full.jpg', 'oss-embed', 'https://cdz.haocheting.com:8085/iot-oss/iot/2222_2025_03_16_02d56162f5364626b3578b81f5fb3f5cjpg', NULL, 1, NULL, '2025-03-16 07:45:42', 1, '2025-03-16 07:45:42');
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
  `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
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
INSERT INTO `sys_oss_config` (`id`, `access_key`, `access_policy`, `bucket_name`, `config_key`, `domain`, `endpoint`, `ext1`, `is_https`, `prefix`, `region`, `remark`, `secret_key`, `status`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1', 'admin', '1', 'iot', 'oss-embed', '', 'cdz.haocheting.com:8085/iot-oss', '', 'Y', '2222', 'local', NULL, '123', '1', 10000, '1', '103', '2024-08-15 10:34:57', '1', '2025-02-24 13:21:12');
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint NOT NULL COMMENT '岗位序号',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `post_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位编码',
  `post_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位名称',
  `post_sort` int DEFAULT NULL COMMENT '岗位排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态（0正常 1停用）',
  `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
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
  `dealer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
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
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
  `id` bigint NOT NULL COMMENT 'id',
  `account_count` bigint DEFAULT NULL COMMENT '用户数量（-1不限制）',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '企业名称',
  `contact_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `contact_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
  `domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '域名',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '企业简介',
  `license_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '统一社会信用代码',
  `bill_type` int DEFAULT NULL COMMENT '结算方式(1统一，2独立)',
  `is_ledger` int DEFAULT NULL COMMENT '是否分帐',
  `package_id` bigint DEFAULT NULL COMMENT '租户套餐编号',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户状态（0正常 1停用）',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `display_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '平台显示名称',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `platform_profit_percent` decimal(19,2) DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_dept` bigint DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
BEGIN;
INSERT INTO `sys_tenant` (`id`, `account_count`, `address`, `company_name`, `contact_phone`, `contact_user_name`, `domain`, `intro`, `license_number`, `bill_type`, `is_ledger`, `package_id`, `status`, `avatar`, `display_name`, `expire_time`, `platform_profit_percent`, `note`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES (10000, -1, NULL, '充电桩运营平台', '15888888888', '管理组', 'localhost', '多租户通用后台管理管理系统', NULL, NULL, NULL, NULL, '1', '', 'ChargeOn运营平台', '2029-12-31 00:00:00', 0.00, NULL, 10000, 1, 103, '2024-08-15 10:34:58', 1, '2025-03-30 07:38:13');
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_package`;
CREATE TABLE `sys_tenant_package` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户套餐id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '套餐名称',
  `menu_ids` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '关联菜单id',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态（0正常 1停用）',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `menu_check_strictly` bit(1) DEFAULT NULL COMMENT '菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）',
  `package_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '套餐名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_tenant_package
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
  `dealer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '合作商编号',
  `is_agent_admin` tinyint(1) DEFAULT NULL COMMENT '是否是代理商管理员',
  `agent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '代理商编号',
  `is_tenant_admin` tinyint(1) DEFAULT '0' COMMENT '是否是租户管理员1是,0否',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `user_name`, `user_type`, `nick_name`, `phone`, `avatar`, `dept_id`, `email`, `login_date`, `login_ip`, `password`, `remark`, `sex`, `status`, `dealer_id`, `is_agent_admin`, `agent_id`, `is_tenant_admin`, `tenant_id`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('1', 'admin', 'SuperAdmin', 'admin', NULL, NULL, '100', 'xw2sy@163.com', '2025-03-30 07:38:29', '127.0.0.1', '$2a$10$Mz4n4UNgMQsRXIlngPrTReslgEvZaFVJIcQ9FzS6wRHhjzBjSUND.', '管理员', '1', '1', NULL, NULL, NULL, 1, '10000', '1', '103', '2024-08-29 16:53:58', '1', '2025-03-30 07:38:29');
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
INSERT INTO `task_info` (`id`, `actions`, `expression`, `name`, `reason`, `state`, `type`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('667bbfa1-a7ed-4ce8-9ce0-cfa8cac90e6c', '[]', '22', '2222', 'stop by 1', 'stopped', 'delay', '2222', '1', NULL, '2025-02-09 22:41:56', '1', '2025-02-10 06:58:01');
INSERT INTO `task_info` (`id`, `actions`, `expression`, `name`, `reason`, `state`, `type`, `note`, `create_by`, `create_dept`, `create_time`, `update_by`, `update_time`) VALUES ('97f79dde-bf3c-4d5b-bfd8-8102539002ad', '[]', '*/25 * * * * ? *', '测试111', 'stop by 1', 'stopped', 'timer', 'sss', '1', NULL, '2025-02-28 22:42:00', '1', '2025-02-10 07:00:01');
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
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '租户编号',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者',
  `create_dept` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `charge_pay_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `recharge_type` (`recharge_type`) USING BTREE,
  KEY `source` (`source`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE,
  KEY `tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值方案';

-- ----------------------------
-- Records of topup
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
