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

 Date: 19/08/2025 15:38:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_data_source
-- ----------------------------
DROP TABLE IF EXISTS `t_data_source`;
CREATE TABLE `t_data_source`  (
  `id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库地址',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库密码',
  `driver_class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库驱动',
  `datasource_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_data_source
-- ----------------------------
INSERT INTO `t_data_source` VALUES ('1', 'jdbc:mysql://127.0.0.1:3306/typt?defaultFetchSize=50&useCursorFetch=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false', 'root', 'root', 'com.mysql.jdbc.Driver', 'typt');
INSERT INTO `t_data_source` VALUES ('2', 'jdbc:mysql://127.0.0.1:3306/yhzs?defaultFetchSize=50&useCursorFetch=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false', 'root', 'root', 'com.mysql.jdbc.Driver', 'yhzs');

SET FOREIGN_KEY_CHECKS = 1;
