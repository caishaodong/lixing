/*
 Navicat Premium Data Transfer

 Source Server         : 119.45.238.208
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 119.45.238.208:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 13/10/2020 11:56:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单编号',
  `pcode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单父编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单图标',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'url地址',
  `sort_num` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '菜单排序号',
  `level` int(1) UNSIGNED NULL DEFAULT NULL COMMENT '菜单层级（一级：1，二级：2...）',
  `is_menu` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否是菜单（0：不是  1：是）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `is_enable` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否启用（0：不启用，1：启用）',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 'index', '', '首页', '', '', 0, 1, 0, '', 1, 0, '2020-09-10 15:15:09', '2020-09-10 15:15:09');
INSERT INTO `sys_menu` VALUES (2, 'orderManagement', '', '订单管理', '', '', 1, 1, 0, '', 1, 0, '2020-09-10 15:16:14', '2020-09-10 15:16:14');
INSERT INTO `sys_menu` VALUES (3, 'todayOrder', 'orderManagement', '今日订单', '', '', 1, 2, 0, '', 1, 0, '2020-09-10 15:16:52', '2020-09-10 15:16:52');
INSERT INTO `sys_menu` VALUES (4, 'pastOrder', 'orderManagement', '历史订单', '', '', 2, 2, 0, '', 1, 0, '2020-09-10 15:17:12', '2020-09-10 15:17:12');
INSERT INTO `sys_menu` VALUES (5, 'futureOrder', 'orderManagement', '暂存订单', '', '', 3, 2, 0, '', 1, 0, '2020-09-10 15:17:38', '2020-09-10 15:17:38');
INSERT INTO `sys_menu` VALUES (6, 'varietyManagement', '', '品种管理', '', '', 2, 1, 0, '', 1, 0, '2020-09-10 15:18:58', '2020-09-10 15:18:58');
INSERT INTO `sys_menu` VALUES (7, 'distributeManagement', '', '配送管理', '', '', 3, 1, 0, '', 1, 0, '2020-09-10 15:20:12', '2020-09-10 15:20:12');
INSERT INTO `sys_menu` VALUES (8, 'billManagement', '', '账单管理', '', '', 4, 1, 0, '', 1, 0, '2020-09-10 15:20:47', '2020-09-10 15:20:47');

SET FOREIGN_KEY_CHECKS = 1;
