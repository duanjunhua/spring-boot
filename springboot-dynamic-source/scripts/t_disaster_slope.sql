/*
 Navicat Premium Dump SQL

 Source Server         : 本地链接
 Source Server Type    : MySQL
 Source Server Version : 50729 (5.7.29)
 Source Host           : localhost:3306
 Source Schema         : yhzsglzxt

 Target Server Type    : MySQL
 Target Server Version : 50729 (5.7.29)
 File Encoding         : 65001

 Date: 19/08/2025 15:40:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_disaster_slope
-- ----------------------------
DROP TABLE IF EXISTS `t_disaster_slope`;
CREATE TABLE `t_disaster_slope`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键（唯一）',
  `start_pile_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '起点桩号',
  `end_pile_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '止点桩号',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '灾害风险隐患描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '边坡' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
