/*
Navicat MySQL Data Transfer

Source Server         : 立兴
Source Server Version : 50731
Source Host           : 119.45.238.208:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50731
File Encoding         : 65001

Date: 2020-09-20 14:40:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base
-- ----------------------------
DROP TABLE IF EXISTS `base`;
CREATE TABLE `base` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础表';

-- ----------------------------
-- Table structure for f_company_bill
-- ----------------------------
DROP TABLE IF EXISTS `f_company_bill`;
CREATE TABLE `f_company_bill` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `fee_category_name` varchar(32) NOT NULL DEFAULT '' COMMENT '费用类别名称',
  `fee_subject_name` varchar(32) NOT NULL DEFAULT '' COMMENT '费用科目名称',
  `area_code` int(6) DEFAULT NULL COMMENT '进货产地编码',
  `area_name` varchar(16) NOT NULL DEFAULT '' COMMENT '进货产地名称',
  `adress` varchar(128) NOT NULL DEFAULT '' COMMENT '详细地址',
  `bill_date` bigint(8) unsigned DEFAULT NULL COMMENT '账单日期',
  `price` decimal(14,4) DEFAULT NULL COMMENT '单价',
  `num` decimal(14,0) unsigned DEFAULT NULL COMMENT '数量',
  `weight` decimal(14,4) DEFAULT NULL COMMENT '重量',
  `total_price` decimal(14,4) DEFAULT NULL COMMENT '总金额',
  `entered_user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '录入人',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='公司账单表';

-- ----------------------------
-- Table structure for m_customer_distribution_company_rel
-- ----------------------------
DROP TABLE IF EXISTS `m_customer_distribution_company_rel`;
CREATE TABLE `m_customer_distribution_company_rel` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(11) NOT NULL COMMENT '客户id',
  `distribution_company_id` bigint(11) NOT NULL COMMENT '配送公司id',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='客户和配送公司关联表';

-- ----------------------------
-- Table structure for m_customer_info
-- ----------------------------
DROP TABLE IF EXISTS `m_customer_info`;
CREATE TABLE `m_customer_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '客户名称',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='客户表';

-- ----------------------------
-- Table structure for m_customer_price_category_rel
-- ----------------------------
DROP TABLE IF EXISTS `m_customer_price_category_rel`;
CREATE TABLE `m_customer_price_category_rel` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(11) NOT NULL COMMENT '客户id',
  `price_category_id` bigint(11) NOT NULL COMMENT '价目id',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='客户和价目关联表';

-- ----------------------------
-- Table structure for m_distribution_company
-- ----------------------------
DROP TABLE IF EXISTS `m_distribution_company`;
CREATE TABLE `m_distribution_company` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '配送单位名称',
  `need_invoice` tinyint(1) DEFAULT NULL COMMENT '是否需要开机打发票（0：不需要，1：需要）',
  `settlement_deduction_rate` decimal(14,4) DEFAULT NULL COMMENT '结算扣率',
  `area_code` int(6) DEFAULT NULL COMMENT '地区编码',
  `area_name` varchar(32) NOT NULL DEFAULT '' COMMENT '地区名称',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '详细地址',
  `contact_user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '联系人姓名',
  `contact_user_mobile` varchar(16) NOT NULL DEFAULT '' COMMENT '联系人手机号',
  `order_manager_name` varchar(32) NOT NULL DEFAULT '' COMMENT '订单管理人姓名',
  `order_manager_mobile` varchar(16) NOT NULL DEFAULT '' COMMENT '订单管理人手机号',
  `financial_contact_name` varchar(32) NOT NULL DEFAULT '' COMMENT '财务联系人姓名',
  `financial_contact_mobile` varchar(16) NOT NULL DEFAULT '' COMMENT '财务联系人手机号',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='配送公司信息表';

-- ----------------------------
-- Table structure for m_order_check_detail
-- ----------------------------
DROP TABLE IF EXISTS `m_order_check_detail`;
CREATE TABLE `m_order_check_detail` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_date` bigint(8) NOT NULL COMMENT '订单日期',
  `distribution_company_id` bigint(11) unsigned NOT NULL COMMENT '配送单位id',
  `delivery_user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '送货人姓名',
  `check_user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '验收人姓名',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_date` (`order_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='订单送货验收信息表';

-- ----------------------------
-- Table structure for m_order_info
-- ----------------------------
DROP TABLE IF EXISTS `m_order_info`;
CREATE TABLE `m_order_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(128) NOT NULL DEFAULT '' COMMENT '订单编号',
  `order_date` bigint(8) unsigned DEFAULT NULL COMMENT '订单日期',
  `distribution_company_id` bigint(11) DEFAULT NULL COMMENT '配送单位id',
  `distribution_company_name` varchar(64) NOT NULL DEFAULT '' COMMENT '配送单位名称',
  `customer_id` bigint(11) DEFAULT NULL COMMENT '客户id',
  `customer_name` varchar(32) NOT NULL DEFAULT '' COMMENT '客户名称',
  `price_category_id` bigint(11) unsigned DEFAULT NULL COMMENT '价目id',
  `price_category_name` varchar(32) NOT NULL DEFAULT '' COMMENT '价目名称',
  `varieties_price_id` bigint(11) unsigned DEFAULT NULL COMMENT '品种价格id',
  `varieties_name` varchar(32) NOT NULL DEFAULT '' COMMENT '品种名称',
  `unit` varchar(16) NOT NULL DEFAULT '' COMMENT '单位',
  `num` decimal(14,0) unsigned DEFAULT NULL COMMENT '数量',
  `price` decimal(14,4) DEFAULT NULL COMMENT '单价',
  `total_price` decimal(14,4) DEFAULT NULL COMMENT '总价',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `source` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '来源（1：后台添加，2：复制，3：导入）',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_sn` (`order_sn`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Table structure for m_price_category
-- ----------------------------
DROP TABLE IF EXISTS `m_price_category`;
CREATE TABLE `m_price_category` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '价目名称',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='价目表';

-- ----------------------------
-- Table structure for m_varieties_price_info
-- ----------------------------
DROP TABLE IF EXISTS `m_varieties_price_info`;
CREATE TABLE `m_varieties_price_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `price_category_id` bigint(11) unsigned NOT NULL COMMENT '价目id',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '食材品种名称',
  `unit` varchar(16) NOT NULL DEFAULT '' COMMENT '单位',
  `price` decimal(14,4) DEFAULT NULL COMMENT '单价',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_price_category_id` (`price_category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='品种价格信息表';

-- ----------------------------
-- Table structure for sys_city
-- ----------------------------
DROP TABLE IF EXISTS `sys_city`;
CREATE TABLE `sys_city` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` int(6) unsigned NOT NULL COMMENT '区域编码',
  `parent_code` int(6) unsigned NOT NULL COMMENT '上级区域编码',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '地区名称',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_code` (`code`) USING BTREE,
  KEY `idx_parent_code` (`parent_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3568 DEFAULT CHARSET=utf8mb4 COMMENT='中国省市区地名表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单编号',
  `pcode` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单父编号',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `icon` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT 'url地址',
  `sort_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '菜单排序号',
  `level` int(1) unsigned DEFAULT NULL COMMENT '菜单层级（一级：1，二级：2...）',
  `is_menu` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是菜单（0：不是  1：是）',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `is_enable` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否启用（0：不启用，1：启用）',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `mobile` varchar(16) NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1：正常，2：冻结，3：注销）',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';
