/*
 Navicat Premium Data Transfer

 Source Server         : 10.10.102.106
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : 10.10.102.106:3306
 Source Schema         : chensino

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 26/09/2022 16:26:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父菜单ID',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT 1 COMMENT '排序值',
  `keep_alive` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10455 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (10451, '用户管理', NULL, '/admin/user/index', -1, NULL, 1, '0', '0', '2022-09-05 16:29:47', NULL, '0');
INSERT INTO `t_menu` VALUES (10452, '用户查看', 'user_query', '', 10451, NULL, 1, '0', '0', '2022-09-05 16:29:47', NULL, '0');
INSERT INTO `t_menu` VALUES (10453, '用户新增', 'user_add', '', 10451, NULL, 1, '0', '0', '2022-09-05 16:29:47', NULL, '0');
INSERT INTO `t_menu` VALUES (10454, '用户删除', 'user_del', '', 10451, NULL, 1, '0', '0', '2022-09-05 16:29:47', NULL, '0');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `role_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色代码',
  `role_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `ds_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据权限类型，全部、本级、本级和子级、自定义',
  `ds_scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据权限范围描述',
  `del_flag` tinyint(4) NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '管理员', 'ADMIN', '管理员', '0', '2', 0, '2022-09-05 16:26:14', NULL);
INSERT INTO `t_role` VALUES (2, '访客', 'GUEST', ' 访客权限', '0', '2', 0, '2022-09-05 17:52:31', NULL);

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE,
  INDEX `t_role_menu__menu_id_fk`(`menu_id`) USING BTREE,
  CONSTRAINT `t_role_menu__menu_id_fk` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`menu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_role_menu__role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (1, 10451);
INSERT INTO `t_role_menu` VALUES (1, 10452);
INSERT INTO `t_role_menu` VALUES (1, 10453);
INSERT INTO `t_role_menu` VALUES (1, 10454);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `nick_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `employ_id` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `dept_id` int(11) NOT NULL COMMENT '部门id',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `creator_id` bigint(20) NOT NULL COMMENT '创建人id',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志，0：未删除，1：删除',
  `lock_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否禁用',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'chensino', 'chensino', '101234', '$2a$10$qjl3G9aELnYIyITfiGRraerkDICiDIGvW./faw.vuCBIoQW9c4JSy', NULL, NULL, 3, '462488588@qq.com', '2022-09-01 10:01:50', NULL, -1, 0, 0);
INSERT INTO `t_user` VALUES (2, 'guest', 'guest', '102222', '$2a$10$qjl3G9aELnYIyITfiGRraerkDICiDIGvW./faw.vuCBIoQW9c4JSy', NULL, NULL, 3, '462488588@qq.com', '2022-09-01 10:01:50', NULL, -1, 0, 0);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `t_user_role__role_id_fk`(`role_id`) USING BTREE,
  CONSTRAINT `t_user_role__role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_user_role__user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1);
INSERT INTO `t_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
