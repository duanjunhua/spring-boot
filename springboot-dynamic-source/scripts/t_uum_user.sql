/*
 Navicat Premium Dump SQL

 Source Server         : 本地链接
 Source Server Type    : MySQL
 Source Server Version : 50729 (5.7.29)
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50729 (5.7.29)
 File Encoding         : 65001

 Date: 19/08/2025 15:39:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_uum_user
-- ----------------------------
DROP TABLE IF EXISTS `t_uum_user`;
CREATE TABLE `t_uum_user`  (
  `USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `USER_LOGIN_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录名',
  PRIMARY KEY (`USER_ID`) USING BTREE,
  INDEX `IDX_USER_ID`(`PERSON_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
