/*
 Navicat Premium Data Transfer

 Source Server         : mysql-localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 127.0.0.1:3306
 Source Schema         : test_bpm_cg

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 09/02/2021 15:24:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_data_source_def
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_source_def`;
CREATE TABLE `sys_data_source_def`  (
  `id_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
  `class_path_` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源全路径',
  `attributes_json_` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '属性配置',
  PRIMARY KEY (`id_`) USING BTREE,
  INDEX `class_path_unique`(`class_path_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据源模板' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
