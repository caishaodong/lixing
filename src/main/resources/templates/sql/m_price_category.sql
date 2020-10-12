/*
 Navicat Premium Data Transfer

 Source Server         : 立兴
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 119.45.238.208:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 11/10/2020 22:22:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_price_category
-- ----------------------------
DROP TABLE IF EXISTS `m_price_category`;
CREATE TABLE `m_price_category`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '价目名称',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：删除）',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '价目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_price_category
-- ----------------------------
INSERT INTO `m_price_category` VALUES (1, '越城区', 0, '2020-09-07 17:42:37', '2020-09-07 17:42:37');
INSERT INTO `m_price_category` VALUES (2, '市直', 0, '2020-09-07 17:42:49', '2020-09-07 17:42:49');
INSERT INTO `m_price_category` VALUES (3, '柯桥区', 0, '2020-09-07 17:42:56', '2020-09-07 17:42:56');
INSERT INTO `m_price_category` VALUES (4, '文理学院', 0, '2020-09-07 17:43:04', '2020-09-07 17:43:04');
INSERT INTO `m_price_category` VALUES (5, '上虞', 0, '2020-10-12 19:37:04', '2020-10-12 19:37:04');
INSERT INTO `m_price_category` VALUES (6, '其他', 0, '2020-09-07 17:43:09', '2020-09-07 17:43:09');


SET FOREIGN_KEY_CHECKS = 1;
