/*
Navicat MySQL Data Transfer

Source Server         : Xue
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-11 19:42:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `content` mediumtext,
  `title` varchar(255) DEFAULT NULL,
  `likenum` int(10) DEFAULT '0' COMMENT '点赞数',
  `looknum` int(12) DEFAULT '0' COMMENT '浏览量',
  `time` varchar(50) DEFAULT NULL COMMENT '发布时间',
  `tag_id` int(10) DEFAULT NULL COMMENT '标签主键',
  `img_url` mediumtext COMMENT '文章内容图片',
  `commentnum` int(12) DEFAULT '0',
  `state` int(2) DEFAULT NULL COMMENT '0 草稿状态 1发表状态  默认为发表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '博客主键',
  `blog_name` varchar(20) DEFAULT NULL COMMENT '博客名',
  `blog_intro` varchar(50) DEFAULT NULL COMMENT '博客简介',
  `blog_url` varchar(255) DEFAULT NULL COMMENT '博客地址',
  `user_id` int(10) NOT NULL COMMENT '用户主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogroll
-- ----------------------------
DROP TABLE IF EXISTS `blogroll`;
CREATE TABLE `blogroll` (
  `blogroll_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL COMMENT '用户主键',
  `describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `url` varchar(50) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`blogroll_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(100) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `article_id` int(50) DEFAULT NULL,
  `comment_content` varchar(255) DEFAULT NULL,
  `commont_time` varchar(50) DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for keyword
-- ----------------------------
DROP TABLE IF EXISTS `keyword`;
CREATE TABLE `keyword` (
  `keyword` varchar(20) DEFAULT NULL,
  `article_id` int(50) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `tag_id` int(10) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(20) DEFAULT NULL COMMENT '标签名',
  `user_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(50) NOT NULL COMMENT '用户名（登录）',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `sex` varchar(10) DEFAULT '男' COMMENT '性别',
  `head_img` varchar(255) DEFAULT NULL COMMENT '头像',
  `user_intro` varchar(255) DEFAULT NULL COMMENT '个人简介',
  `birthday` varchar(30) DEFAULT NULL,
  `looknum` int(12) DEFAULT '0' COMMENT '浏览量',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
