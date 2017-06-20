/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mybook

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-06-20 12:55:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `bookNum` varchar(255) NOT NULL,
  `bookName` varchar(255) NOT NULL,
  `bookType` varchar(255) NOT NULL,
  `bookAuthor` varchar(255) NOT NULL,
  `bookFactory` varchar(255) NOT NULL,
  PRIMARY KEY (`bid`),
  UNIQUE KEY `bookNum` (`bookNum`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '2', '三国演义', '教育', '罗贯中', '人民教育出版社');
INSERT INTO `book` VALUES ('2', '1', '1', '1', '1', '1');
INSERT INTO `book` VALUES ('5', '5', '5', '5', '5', '5');
INSERT INTO `book` VALUES ('6', '4', '三国演义', '励志', '我', '逗逼出版社');
INSERT INTO `book` VALUES ('8', '6', '逗逼升仙秘籍', '人生宝典', '我', '都比出版社');
INSERT INTO `book` VALUES ('9', '3', '三国群英传', '游戏', '游戏迷', '游戏出版社');
INSERT INTO `book` VALUES ('10', '8', 'java基础', '工具', 'java之父', '图灵出版社');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zzx', '123');
