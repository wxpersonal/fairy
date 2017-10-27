/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : whatever

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2017-10-27 11:00:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '上级菜单',
  `name` varchar(50) NOT NULL DEFAULT '""' COMMENT '排序级别',
  `kind` int(11) NOT NULL DEFAULT '0' COMMENT '菜单类型  0菜单实例(默认),1菜单分组',
  `url` varchar(500) DEFAULT NULL COMMENT 'url',
  `icon` varchar(500) DEFAULT NULL COMMENT 'icon',
  `create_by` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '0无效 1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) DEFAULT '0',
  `system_id` int(11) DEFAULT '0',
  `code` varchar(50) NOT NULL,
  `desc` varchar(1000) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `orders` int(11) DEFAULT '0',
  `create_by` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '0', '1', 'whatever', '系统管理', 'whatever', '1', '0', '2017-06-20 00:00:00', '0', '2017-06-20 00:00:00', '1');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '权限编码',
  `name` varchar(50) DEFAULT NULL,
  `desc` varchar(1000) DEFAULT NULL COMMENT '权限说明',
  `create_by` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'ROLE_ADMIN', '管理员', '拥有除权限管理系统外的所有权限', null, '2017-06-20 00:00:00', null, '2017-06-20 00:00:00', '1');
INSERT INTO `t_role` VALUES ('2', 'ROLE_SADMIN', '超级管理员', '拥有所有权限', null, '2017-06-20 00:00:00', null, '2017-06-20 00:00:00', '1');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `create_by` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('1', '1', '0', '2017-06-20 00:00:00', '0', '2017-06-20 00:00:00', '1');

-- ----------------------------
-- Table structure for t_sys_code
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_code`;
CREATE TABLE `t_sys_code` (
  `code` varchar(50) NOT NULL,
  `p_code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `desc` varchar(1000) DEFAULT NULL,
  `create_by` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `updateBy` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_code
-- ----------------------------
INSERT INTO `t_sys_code` VALUES ('_PERMISSiON', null, '权限', null, '0', '2017-06-20 00:00:00', '0', '2017-06-20 00:00:00', '1');
INSERT INTO `t_sys_code` VALUES ('_ROLE', null, '角色', null, '0', '2017-06-20 00:00:00', '0', '2017-06-20 00:00:00', '1');

-- ----------------------------
-- Table structure for t_sys_file
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_file`;
CREATE TABLE `t_sys_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '附件id',
  `objectid` int(11) DEFAULT '0' COMMENT '所属对象id',
  `objecttype` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0' COMMENT '附件类型',
  `oldname` varchar(255) DEFAULT NULL COMMENT '附件原名称',
  `newname` varchar(255) DEFAULT NULL COMMENT '附件新名称',
  `size` int(11) DEFAULT '0' COMMENT '附件大小',
  `path` varchar(255) DEFAULT NULL COMMENT '存放路径',
  `relativepath` varchar(255) DEFAULT NULL COMMENT '相对路径',
  `create_by` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Records of t_sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(20) NOT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `gender` int(20) DEFAULT '0' COMMENT '性别',
  `mobile` varchar(50) DEFAULT NULL COMMENT '电话',
  `birthday` datetime DEFAULT NULL COMMENT '出生时间',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `address_id` int(11) DEFAULT '0',
  `photo_id` int(20) DEFAULT '0' COMMENT '照片附件',
  `create_by` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `status` int(20) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', 'abc123', '0', '18205256689', '2017-06-16 00:00:00', 'wxpersonal@163.com', '0', '0', '0', '2017-06-16 00:00:00', '0', '2017-06-16 00:00:00', '1');
INSERT INTO `t_user` VALUES ('2', 'weix', 'xiang123', '0', '18205256689', '2017-06-20 00:00:00', 'wxpersonal@163.com', '0', '0', '0', '2017-06-20 00:00:00', '0', '2017-06-20 00:00:00', '1');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色code',
  `create_by` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
