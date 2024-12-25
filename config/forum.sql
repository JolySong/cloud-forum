/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 90100 (9.1.0)
 Source Host           : localhost:3306
 Source Schema         : forum

 Target Server Type    : MySQL
 Target Server Version : 90100 (9.1.0)
 File Encoding         : 65001

 Date: 25/12/2024 15:45:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for announcements
-- ----------------------------
DROP TABLE IF EXISTS `announcements`;
CREATE TABLE `announcements` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `user_id` bigint unsigned NOT NULL COMMENT '发布者ID',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0:下线 1:上线',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status_time` (`status`,`start_time`,`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告表';

-- ----------------------------
-- Records of announcements
-- ----------------------------
BEGIN;
INSERT INTO `announcements` (`id`, `title`, `content`, `user_id`, `status`, `start_time`, `end_time`, `created_at`, `updated_at`) VALUES (1, '社区规范更新公告', '为了给大家提供更好的交流环境，我们更新了社区规范...\n\n## 主要变更\n1. 发帖规则调整\n2. 评论要求更新\n3. 奖励机制优化', 1, 1, '2024-03-01 00:00:00', '2024-03-31 23:59:59', '2024-12-05 22:14:44', '2024-12-05 22:14:44');
INSERT INTO `announcements` (`id`, `title`, `content`, `user_id`, `status`, `start_time`, `end_time`, `created_at`, `updated_at`) VALUES (2, '新功能上线：话题订阅', '我们很高兴地宣布，话题订阅功能正式上线！\n\n现在您可以订阅感兴趣的话题，获取最新更新提醒。', 1, 1, '2024-03-15 00:00:00', NULL, '2024-12-05 22:14:44', '2024-12-05 22:14:44');
INSERT INTO `announcements` (`id`, `title`, `content`, `user_id`, `status`, `start_time`, `end_time`, `created_at`, `updated_at`) VALUES (3, '社区维护通知', '系统将于本周日凌晨2点-4点进行例行维护，期间可能出现访问缓慢的情况。', 1, 1, '2024-03-20 00:00:00', '2024-03-21 04:00:00', '2024-12-05 22:14:44', '2024-12-05 22:14:44');
COMMIT;

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `description` varchar(255) DEFAULT NULL COMMENT '分类描述',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0:禁用 1:正常',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类表';

-- ----------------------------
-- Records of categories
-- ----------------------------
BEGIN;
INSERT INTO `categories` (`id`, `name`, `description`, `sort`, `status`, `created_at`, `updated_at`) VALUES (1, '技术', '技术相关讨论', 1, 1, '2024-12-05 22:14:44', '2024-12-05 22:14:44');
INSERT INTO `categories` (`id`, `name`, `description`, `sort`, `status`, `created_at`, `updated_at`) VALUES (2, '生活', '生活分享交流', 2, 1, '2024-12-05 22:14:44', '2024-12-05 22:14:44');
INSERT INTO `categories` (`id`, `name`, `description`, `sort`, `status`, `created_at`, `updated_at`) VALUES (3, '问答', '技术问答解惑', 3, 1, '2024-12-05 22:14:44', '2024-12-09 01:42:22');
INSERT INTO `categories` (`id`, `name`, `description`, `sort`, `status`, `created_at`, `updated_at`) VALUES (4, '分享', '资源经验分享', 4, 1, '2024-12-05 22:14:44', '2024-12-05 22:14:44');
COMMIT;

-- ----------------------------
-- Table structure for collects
-- ----------------------------
DROP TABLE IF EXISTS `collects`;
CREATE TABLE `collects` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `topic_id` bigint DEFAULT NULL COMMENT '主题id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_topic_id` (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1867218969128132611 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收藏表';

-- ----------------------------
-- Records of collects
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `content` text NOT NULL COMMENT '评论内容',
  `user_id` bigint unsigned NOT NULL COMMENT '评论者ID',
  `topic_id` bigint unsigned NOT NULL COMMENT '话题ID',
  `parent_id` bigint unsigned DEFAULT NULL COMMENT '父评论ID',
  `like_count` bigint DEFAULT '0' COMMENT '评论点赞数',
  `is_top` int DEFAULT '0' COMMENT '是否置顶 1是 0否',
  `reply_to` bigint unsigned DEFAULT NULL COMMENT '回复用户ID',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0:删除 1:正常',
  `audit` tinyint NOT NULL DEFAULT '0' COMMENT '0: 待审核, 1: 已通过, 2: 已拒绝',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_topic_id` (`topic_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';

-- ----------------------------
-- Records of comments
-- ----------------------------
BEGIN;
INSERT INTO `comments` (`id`, `content`, `user_id`, `topic_id`, `parent_id`, `like_count`, `is_top`, `reply_to`, `status`, `audit`, `created_at`, `updated_at`) VALUES (1, '非常实用的文章，感谢分享！', 3, 1, NULL, -4, 0, NULL, 1, 1, '2024-12-05 22:14:44', '2024-12-12 19:00:09');
INSERT INTO `comments` (`id`, `content`, `user_id`, `topic_id`, `parent_id`, `like_count`, `is_top`, `reply_to`, `status`, `audit`, `created_at`, `updated_at`) VALUES (2, '这个观点很有意思，但是我觉得还可以补充...', 4, 1, NULL, -3, 0, NULL, 1, 1, '2024-12-05 22:14:44', '2024-12-12 19:00:09');
INSERT INTO `comments` (`id`, `content`, `user_id`, `topic_id`, `parent_id`, `like_count`, `is_top`, `reply_to`, `status`, `audit`, `created_at`, `updated_at`) VALUES (3, '完全同意作者的看法，确实是这样。', 5, 2, NULL, -1, 0, NULL, 1, 1, '2024-12-05 22:14:44', '2024-12-12 19:00:09');
INSERT INTO `comments` (`id`, `content`, `user_id`, `topic_id`, `parent_id`, `like_count`, `is_top`, `reply_to`, `status`, `audit`, `created_at`, `updated_at`) VALUES (4, '我也遇到过类似的问题，解决方案是...', 2, 3, NULL, 0, 0, NULL, 1, 1, '2024-12-05 22:14:44', '2024-12-12 19:00:09');
INSERT INTO `comments` (`id`, `content`, `user_id`, `topic_id`, `parent_id`, `like_count`, `is_top`, `reply_to`, `status`, `audit`, `created_at`, `updated_at`) VALUES (5, '文章写得很详细，收藏了！', 3, 4, NULL, 0, 0, NULL, 1, 1, '2024-12-05 22:14:44', '2024-12-12 19:00:09');
INSERT INTO `comments` (`id`, `content`, `user_id`, `topic_id`, `parent_id`, `like_count`, `is_top`, `reply_to`, `status`, `audit`, `created_at`, `updated_at`) VALUES (12, 'markdown', 6, 7, NULL, 61, 0, NULL, 1, 2, '2024-12-07 12:52:37', '2024-12-12 19:00:14');
INSERT INTO `comments` (`id`, `content`, `user_id`, `topic_id`, `parent_id`, `like_count`, `is_top`, `reply_to`, `status`, `audit`, `created_at`, `updated_at`) VALUES (18, '谢谢', 6, 3, NULL, 0, 0, NULL, 1, 0, '2024-12-12 22:55:31', '2024-12-12 22:55:31');
INSERT INTO `comments` (`id`, `content`, `user_id`, `topic_id`, `parent_id`, `like_count`, `is_top`, `reply_to`, `status`, `audit`, `created_at`, `updated_at`) VALUES (20, '我不知道这个啥情况', 6, 3, NULL, 0, 0, NULL, 1, 0, '2024-12-12 23:02:42', '2024-12-12 23:02:42');
COMMIT;

-- ----------------------------
-- Table structure for email_send_records
-- ----------------------------
DROP TABLE IF EXISTS `email_send_records`;
CREATE TABLE `email_send_records` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一标识符',
  `recipient_email` varchar(255) NOT NULL COMMENT '收件人邮箱',
  `subject` varchar(100) NOT NULL COMMENT '邮件主题',
  `body` text NOT NULL COMMENT '邮件内容',
  `email_type` tinyint NOT NULL DEFAULT '1' COMMENT '邮件类型（0安全验证 1系统通知）',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '发送状态（如：0失败 1成功）',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  `error_message` text COMMENT '发送失败时的错误信息（可选）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='邮件记录表';

-- ----------------------------
-- Records of email_send_records
-- ----------------------------
BEGIN;
INSERT INTO `email_send_records` (`id`, `recipient_email`, `subject`, `body`, `email_type`, `status`, `created_at`, `updated_at`, `error_message`) VALUES (1, '854236339@qq.com', '验证码', '【验证码】您的验证码为：256291 。 验证码五分钟内有效，逾期作废。\n\n\n------------------------------\n\n\n', 0, 1, '2024-12-11 23:08:34', '2024-12-11 23:08:34', NULL);
INSERT INTO `email_send_records` (`id`, `recipient_email`, `subject`, `body`, `email_type`, `status`, `created_at`, `updated_at`, `error_message`) VALUES (2, '854236339@qq.com', '验证码', '【验证码】您的验证码为：830949 。 验证码五分钟内有效，逾期作废。\n\n\n------------------------------\n\n\n', 0, 1, '2024-12-11 23:09:23', '2024-12-11 23:09:23', NULL);
INSERT INTO `email_send_records` (`id`, `recipient_email`, `subject`, `body`, `email_type`, `status`, `created_at`, `updated_at`, `error_message`) VALUES (3, '854236339@qq.com', '验证码', '【验证码】您的验证码为：350661 。 验证码五分钟内有效，逾期作废。\n\n\n------------------------------\n\n\n', 0, 1, '2024-12-11 23:14:48', '2024-12-11 23:14:48', NULL);
INSERT INTO `email_send_records` (`id`, `recipient_email`, `subject`, `body`, `email_type`, `status`, `created_at`, `updated_at`, `error_message`) VALUES (4, '854236339@qq.com', '验证码', '【验证码】您的验证码为：669837 。 验证码五分钟内有效，逾期作废。\n\n\n------------------------------\n\n\n', 0, 1, '2024-12-11 23:15:28', '2024-12-11 23:15:28', NULL);
INSERT INTO `email_send_records` (`id`, `recipient_email`, `subject`, `body`, `email_type`, `status`, `created_at`, `updated_at`, `error_message`) VALUES (5, '854236339@qq.com', '验证码', '【验证码】您的验证码为：219461 。 验证码五分钟内有效，逾期作废。\n\n\n------------------------------\n\n\n', 0, 1, '2024-12-11 23:19:14', '2024-12-11 23:19:14', NULL);
INSERT INTO `email_send_records` (`id`, `recipient_email`, `subject`, `body`, `email_type`, `status`, `created_at`, `updated_at`, `error_message`) VALUES (6, '854236339@qq.com', '验证码', '【验证码】您的验证码为：643691 。 验证码五分钟内有效，逾期作废。\n\n\n------------------------------\n\n\n', 0, 1, '2024-12-11 23:27:22', '2024-12-11 23:27:22', NULL);
COMMIT;

-- ----------------------------
-- Table structure for follows
-- ----------------------------
DROP TABLE IF EXISTS `follows`;
CREATE TABLE `follows` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '关注ID',
  `user_id` bigint unsigned NOT NULL COMMENT '关注者ID',
  `followed_id` bigint unsigned NOT NULL COMMENT '被关注者ID',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_followed` (`user_id`,`followed_id`),
  KEY `idx_followed_id` (`followed_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1866488433740890115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='关注表';

-- ----------------------------
-- Records of follows
-- ----------------------------
BEGIN;
INSERT INTO `follows` (`id`, `user_id`, `followed_id`, `created_at`) VALUES (1, 2, 3, '2024-12-05 22:14:44');
INSERT INTO `follows` (`id`, `user_id`, `followed_id`, `created_at`) VALUES (2, 2, 4, '2024-12-05 22:14:44');
INSERT INTO `follows` (`id`, `user_id`, `followed_id`, `created_at`) VALUES (3, 3, 2, '2024-12-05 22:14:44');
INSERT INTO `follows` (`id`, `user_id`, `followed_id`, `created_at`) VALUES (4, 4, 2, '2024-12-05 22:14:44');
INSERT INTO `follows` (`id`, `user_id`, `followed_id`, `created_at`) VALUES (5, 5, 2, '2024-12-05 22:14:44');
COMMIT;

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
  `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
  `target_id` bigint unsigned NOT NULL COMMENT '目标ID',
  `target_type` tinyint NOT NULL COMMENT '目标类型 0:话题, 1:评论\n',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_target` (`user_id`,`target_id`,`target_type`),
  KEY `idx_target` (`target_id`,`target_type`)
) ENGINE=InnoDB AUTO_INCREMENT=1867221687737552899 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点赞表';

-- ----------------------------
-- Records of likes
-- ----------------------------
BEGIN;
INSERT INTO `likes` (`id`, `user_id`, `target_id`, `target_type`, `created_at`) VALUES (1, 2, 1, 0, '2024-12-05 22:14:44');
INSERT INTO `likes` (`id`, `user_id`, `target_id`, `target_type`, `created_at`) VALUES (2, 3, 1, 0, '2024-12-05 22:14:44');
INSERT INTO `likes` (`id`, `user_id`, `target_id`, `target_type`, `created_at`) VALUES (3, 4, 1, 0, '2024-12-05 22:14:44');
INSERT INTO `likes` (`id`, `user_id`, `target_id`, `target_type`, `created_at`) VALUES (4, 5, 2, 0, '2024-12-05 22:14:44');
INSERT INTO `likes` (`id`, `user_id`, `target_id`, `target_type`, `created_at`) VALUES (5, 2, 1, 1, '2024-12-05 22:14:44');
INSERT INTO `likes` (`id`, `user_id`, `target_id`, `target_type`, `created_at`) VALUES (1865673795147202563, 2, 6, 1, '2024-12-09 11:35:01');
INSERT INTO `likes` (`id`, `user_id`, `target_id`, `target_type`, `created_at`) VALUES (1865673795147202564, 3, 6, 1, '2024-12-09 11:35:10');
INSERT INTO `likes` (`id`, `user_id`, `target_id`, `target_type`, `created_at`) VALUES (1865673795147202565, 4, 6, 1, '2024-12-09 11:35:15');
COMMIT;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `type` tinyint NOT NULL COMMENT '消息类型(0:点赞, 1:评论, 2:关注， 3系统)',
  `content` text NOT NULL COMMENT '消息内容',
  `from_user_id` bigint DEFAULT NULL COMMENT '发送者用户ID',
  `to_user_id` bigint NOT NULL COMMENT '接收者用户ID',
  `target_type` tinyint DEFAULT NULL COMMENT '目标类型(0:话题,1:评论)',
  `target_id` bigint DEFAULT NULL COMMENT '目标ID',
  `topic_id` bigint DEFAULT NULL COMMENT '话题ID(评论类消息)',
  `is_read` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读(0:未读,1:已读)',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '消息状态(0:已失效,1:有效)',
  PRIMARY KEY (`id`),
  KEY `idx_to_user_id` (`to_user_id`),
  KEY `idx_from_user_id` (`from_user_id`),
  KEY `idx_target` (`target_type`,`target_id`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息表';

-- ----------------------------
-- Records of message
-- ----------------------------
BEGIN;
INSERT INTO `message` (`id`, `type`, `content`, `from_user_id`, `to_user_id`, `target_type`, `target_id`, `topic_id`, `is_read`, `created_at`, `updated_at`, `status`) VALUES (1, 0, '你的话题已发起审核，请等待审核结果通知！', 1000, 6, 1, 17, 17, 0, '2024-12-12 22:43:55', '2024-12-12 22:43:55', 1);
INSERT INTO `message` (`id`, `type`, `content`, `from_user_id`, `to_user_id`, `target_type`, `target_id`, `topic_id`, `is_read`, `created_at`, `updated_at`, `status`) VALUES (2, 0, '赞了我的话题！', 6, 2, 0, NULL, 1, 0, '2024-12-12 22:44:19', '2024-12-12 22:44:19', 1);
INSERT INTO `message` (`id`, `type`, `content`, `from_user_id`, `to_user_id`, `target_type`, `target_id`, `topic_id`, `is_read`, `created_at`, `updated_at`, `status`) VALUES (3, 0, '赞了我的话题！', 6, 4, 0, NULL, 3, 0, '2024-12-12 22:56:37', '2024-12-12 22:56:37', 1);
INSERT INTO `message` (`id`, `type`, `content`, `from_user_id`, `to_user_id`, `target_type`, `target_id`, `topic_id`, `is_read`, `created_at`, `updated_at`, `status`) VALUES (4, 0, '你的话题已发起审核，请等待审核结果通知！', 1000, 6, 1, 22, 22, 0, '2024-12-12 22:56:37', '2024-12-12 22:56:37', 1);
INSERT INTO `message` (`id`, `type`, `content`, `from_user_id`, `to_user_id`, `target_type`, `target_id`, `topic_id`, `is_read`, `created_at`, `updated_at`, `status`) VALUES (5, 1, '说清楚啊', 6, 2, 1, 19, 3, 0, '2024-12-12 23:01:23', '2024-12-12 23:01:23', 1);
INSERT INTO `message` (`id`, `type`, `content`, `from_user_id`, `to_user_id`, `target_type`, `target_id`, `topic_id`, `is_read`, `created_at`, `updated_at`, `status`) VALUES (6, 1, '我不知道这个啥情况', 6, 4, 0, 20, 3, 0, '2024-12-12 23:02:43', '2024-12-12 23:02:43', 1);
COMMIT;

-- ----------------------------
-- Table structure for message_count
-- ----------------------------
DROP TABLE IF EXISTS `message_count`;
CREATE TABLE `message_count` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `type` tinyint NOT NULL COMMENT '消息类型(0:点赞, 1:评论, 2:系统通知)',
  `unread_count` int NOT NULL DEFAULT '0' COMMENT '未读数量',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_type` (`user_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息计数表';

-- ----------------------------
-- Records of message_count
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for message_quote
-- ----------------------------
DROP TABLE IF EXISTS `message_quote`;
CREATE TABLE `message_quote` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '引用ID',
  `message_id` bigint NOT NULL COMMENT '消息ID',
  `title` varchar(255) DEFAULT NULL COMMENT '引用标题',
  `content` text COMMENT '引用内容',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_message_id` (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息引用内容表';

-- ----------------------------
-- Records of message_quote
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `user_id` bigint unsigned NOT NULL COMMENT '接收用户ID',
  `sender_id` bigint unsigned NOT NULL COMMENT '发送用户ID',
  `type` varchar(20) NOT NULL COMMENT '类型 comment:评论 like:点赞 follow:关注',
  `target_id` bigint unsigned NOT NULL COMMENT '目标ID',
  `content` varchar(255) DEFAULT NULL COMMENT '通知内容',
  `is_read` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读 0:未读 1:已读',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知表';

-- ----------------------------
-- Records of notifications
-- ----------------------------
BEGIN;
INSERT INTO `notifications` (`id`, `user_id`, `sender_id`, `type`, `target_id`, `content`, `is_read`, `created_at`) VALUES (1, 2, 3, 'comment', 1, '评论了你的话题', 0, '2024-12-05 22:14:44');
INSERT INTO `notifications` (`id`, `user_id`, `sender_id`, `type`, `target_id`, `content`, `is_read`, `created_at`) VALUES (2, 2, 4, 'like', 1, '赞了你的话题', 0, '2024-12-05 22:14:44');
INSERT INTO `notifications` (`id`, `user_id`, `sender_id`, `type`, `target_id`, `content`, `is_read`, `created_at`) VALUES (3, 3, 2, 'follow', 3, '关注了你', 0, '2024-12-05 22:14:44');
INSERT INTO `notifications` (`id`, `user_id`, `sender_id`, `type`, `target_id`, `content`, `is_read`, `created_at`) VALUES (4, 4, 5, 'comment', 3, '回复了你的评论', 0, '2024-12-05 22:14:44');
INSERT INTO `notifications` (`id`, `user_id`, `sender_id`, `type`, `target_id`, `content`, `is_read`, `created_at`) VALUES (5, 5, 2, 'like', 4, '赞了你的评论', 0, '2024-12-05 22:14:44');
COMMIT;

-- ----------------------------
-- Table structure for sys_app
-- ----------------------------
DROP TABLE IF EXISTS `sys_app`;
CREATE TABLE `sys_app` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '应用ID',
  `app_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用编码',
  `app_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用名称',
  `frontend_url` varchar(255) NOT NULL COMMENT '前端地址',
  `backend_url` varchar(255) DEFAULT NULL COMMENT '后端地址',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序值',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` tinyint DEFAULT '1' COMMENT '0禁用 1启用',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_app_code` (`app_code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统应用表';

-- ----------------------------
-- Records of sys_app
-- ----------------------------
BEGIN;
INSERT INTO `sys_app` (`id`, `app_code`, `app_name`, `frontend_url`, `backend_url`, `sort`, `remark`, `status`, `created_at`, `updated_at`, `deleted`) VALUES (1, 'forum', '论坛前台系统', 'http://localhost:3000/forum', NULL, 0, '论坛系统', 1, '2024-12-13 18:38:39', '2024-12-15 23:11:22', 0);
INSERT INTO `sys_app` (`id`, `app_code`, `app_name`, `frontend_url`, `backend_url`, `sort`, `remark`, `status`, `created_at`, `updated_at`, `deleted`) VALUES (8, 'forum-ui', '论坛后台管理', '', NULL, 0, '论坛后台管理系统', 0, '2024-12-13 18:38:39', '2024-12-15 20:21:04', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `config_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置中文名',
  `config_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `config_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_system` tinyint DEFAULT '0' COMMENT '系统内置 0否 1是',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` (`id`, `config_name`, `config_key`, `config_value`, `is_system`, `remark`, `created_at`, `updated_at`, `deleted`) VALUES (1, '仅单设备登陆', 'singleDeviceLogin', 'true', 1, 'single', '2024-12-10 20:05:51', '2024-12-14 17:41:39', 0);
INSERT INTO `sys_config` (`id`, `config_name`, `config_key`, `config_value`, `is_system`, `remark`, `created_at`, `updated_at`, `deleted`) VALUES (4, '默认密码', 'default_password', 'Qwer1234.', 1, '默认密码', '2024-12-10 20:05:51', '2024-12-14 17:41:39', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `app_id` bigint NOT NULL COMMENT '应用id',
  `parent_id` bigint DEFAULT '0' COMMENT '父资源ID',
  `resource_name` varchar(50) NOT NULL COMMENT '资源名称',
  `resource_type` char(1) NOT NULL COMMENT '资源类型（M目录 C菜单 F按钮 A接口）',
  `resource_key` varchar(100) NOT NULL COMMENT '资源权限标识',
  `resource_url` varchar(200) DEFAULT '#' COMMENT '前端路由地址',
  `api_url` varchar(200) DEFAULT NULL COMMENT '接口地址',
  `method` varchar(10) DEFAULT NULL COMMENT '请求方法（GET POST PUT DELETE）',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `sort` int DEFAULT '0' COMMENT '显示顺序',
  `icon` varchar(100) DEFAULT '#' COMMENT '图标',
  `status` tinyint DEFAULT '0' COMMENT '资源状态（0显示 1隐藏）',
  `is_public` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否公开资源(不需要权限认证) 0否 1是',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`),
  KEY `idx_api_url` (`api_url`),
  KEY `idx_app_id` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资源权限表';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (1, 1, 0, '主页', 'M', 'home', '/home', NULL, NULL, NULL, 1, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-14 12:05:44', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (2, 1, 0, '话题管理', 'M', 'topic', '/topic', NULL, NULL, NULL, 2, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (3, 1, 0, '用户中心', 'M', 'user', '/user', NULL, NULL, NULL, 3, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (4, 1, 0, '系统管理', 'M', 'system', '/system', NULL, NULL, NULL, 4, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (5, 1, 1, '首页', 'C', 'home:index', '/home', NULL, NULL, 'views/home/index', 1, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (6, 1, 2, '话题列表', 'C', 'topic:list', '/topic', NULL, NULL, 'views/topic/index', 1, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (7, 1, 2, '话题详情', 'C', 'topic:detail', '/topic/:id', NULL, NULL, 'views/topic/detail', 2, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (8, 1, 2, '发布话题', 'C', 'topic:add', '/topic/add', NULL, NULL, 'views/topic/add', 3, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (9, 1, 2, '编辑话题', 'C', 'topic:edit', '/topic/edit/:id', NULL, NULL, 'views/topic/edit', 4, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (10, 1, 3, '个人中心', 'C', 'user:profile', '/user', NULL, NULL, 'views/user/index', 1, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (11, 1, 3, '我的话题', 'C', 'user:topics', '/user/topics', NULL, NULL, 'views/user/components/TopicList', 2, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (12, 1, 3, '我的评论', 'C', 'user:comments', '/user/comments', NULL, NULL, 'views/user/components/CommentList', 3, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (13, 1, 3, '我的收藏', 'C', 'user:favorites', '/user/favorites', NULL, NULL, 'views/user/components/TopicList', 4, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (14, 1, 3, '我的点赞', 'C', 'user:likes', '/user/likes', NULL, NULL, 'views/user/components/LikeList', 5, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (15, 1, 8, '新增话题', 'F', 'topic:create', '#', NULL, NULL, NULL, 0, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-14 12:10:30', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (16, 1, 9, '编辑话题', 'F', 'topic:update', '#', NULL, NULL, NULL, 0, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-14 12:09:24', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (17, 1, 2, '删除话题', 'F', 'topic:delete', '#', NULL, NULL, NULL, 0, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (18, 1, 7, '点赞话题', 'F', 'topic:like', '#', NULL, NULL, NULL, 3, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-14 12:17:57', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (19, 1, 7, '收藏话题', 'F', 'topic:favorite', '#', NULL, NULL, NULL, 3, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-14 12:17:47', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (20, 1, 7, '评论话题', 'F', 'topic:comment', '#', NULL, NULL, NULL, 2, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-14 12:17:20', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (21, 1, 7, '删除评论', 'F', 'comment:delete', '#', NULL, NULL, NULL, 7, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-14 12:19:36', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (22, 1, 7, '点赞评论', 'F', 'comment:like', '#', NULL, NULL, NULL, 5, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-14 12:19:12', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (23, 1, 7, '回复评论', 'F', 'comment:reply', '#', NULL, NULL, NULL, 6, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-14 12:19:22', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (24, 1, 10, '编辑资料', 'F', 'user:update', '#', NULL, NULL, NULL, 3, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 23:03:32', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (25, 1, 3, '修改密码', 'F', 'user:password', '#', NULL, NULL, NULL, 0, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 19:02:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (26, 1, 10, '更换头像', 'F', 'user:avatar', '#', NULL, NULL, NULL, 1, '#', 0, 0, '2024-12-10 14:02:19', '2024-12-13 23:03:10', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (65, 1, 0, '用户登录', 'A', 'auth:login', '#', '/api/auth/login', 'POST', NULL, 0, '#', 1, 1, '2024-12-10 14:04:32', '2024-12-16 00:09:50', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (66, 1, 0, '用户登出', 'A', 'auth:logout', '#', '/api/auth/logout', 'GET', NULL, 0, '#', 1, 1, '2024-12-10 14:04:32', '2024-12-16 00:09:56', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (67, 1, 10, '获取用户信息', 'A', 'user:info', '#', '/api/user/info', 'GET', NULL, 0, '#', 0, 1, '2024-12-10 14:04:32', '2024-12-13 23:04:40', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (68, 1, 3, '获取用户统计', 'A', 'user:stat', '#', '/api/user/stat/{id}', 'GET', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (69, 1, 24, '更新用户信息', 'A', 'user:info:update', '#', '/api/user/info', 'PUT', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 23:03:50', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (70, 1, 25, '修改用户密码', 'A', 'user:password:update', '#', '/api/user/update/password', 'PUT', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 23:05:06', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (71, 1, 11, '获取用户话题', 'A', 'user:topics:list', '#', '/api/user/topics', 'GET', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 23:06:07', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (72, 1, 13, '获取用户收藏', 'A', 'user:favorites:list', '#', '/api/user/collects', 'GET', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 23:06:14', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (73, 1, 12, '获取用户评论', 'A', 'user:comments:list', '#', '/api/user/comments', 'GET', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 23:06:23', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (74, 1, 14, '获取用户点赞', 'A', 'user:likes:list', '#', '/api/user/likes', 'GET', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 23:06:30', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (75, 1, 6, '获取话题列表', 'A', 'topic:list', '#', '/api/topic', 'GET', NULL, 1, '#', 0, 1, '2024-12-10 14:04:32', '2024-12-14 12:16:01', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (76, 1, 7, '获取话题详情', 'A', 'topic:detail', '#', '/api/topic/{id}', 'GET', NULL, 0, '#', 0, 1, '2024-12-10 14:04:32', '2024-12-14 12:20:46', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (77, 1, 15, '创建话题', 'A', 'topic:create', '#', '/api/topic', 'POST', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:11:00', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (78, 1, 16, '更新话题', 'A', 'topic:update', '#', '/api/topic/{id}', 'PUT', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:10:51', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (79, 1, 17, '删除话题', 'A', 'topic:delete', '#', '/api/topic/{id}', 'DELETE', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:13:57', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (80, 1, 18, '点赞话题', 'A', 'topic:like', '#', '/api/topic/{id}/like', 'POST', NULL, 1, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:07:19', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (81, 1, 18, '取消点赞', 'A', 'topic:unlike', '#', '/api/topic/{id}/like', 'DELETE', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:07:04', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (82, 1, 19, '收藏话题', 'A', 'topic:favorite', '#', '/api/topic/{id}/collect', 'POST', NULL, 1, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:07:29', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (83, 1, 19, '取消收藏', 'A', 'topic:unfavorite', '#', '/api/topic/{id}/collect', 'DELETE', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:06:18', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (84, 1, 7, '获取评论列表', 'A', 'comment:list', '#', '/api/topic/comments/{topicId}', 'GET', NULL, 1, '#', 0, 1, '2024-12-10 14:04:32', '2024-12-14 12:20:13', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (85, 1, 20, '发表评论', 'A', 'comment:create', '#', '/api/topic/comments', 'POST', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:14:14', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (86, 1, 21, '删除评论', 'A', 'comment:delete', '#', '/api/topic/comments/{id}', 'DELETE', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:15:02', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (87, 1, 22, '点赞评论', 'A', 'comment:like', '#', '/api/topic/comments/{id}/like', 'POST', NULL, 1, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:06:48', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (88, 1, 22, '取消点赞评论', 'A', 'comment:unlike', '#', '/api/topic/comments/{id}/like', 'DELETE', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:06:37', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (89, 1, 23, '回复评论', 'A', 'comment:reply', '#', '/api/topic/comments/{id}/reply', 'POST', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:14:45', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (90, 1, 6, '获取分类列表', 'A', 'category:list', '#', '/api/topic/categories', 'GET', NULL, 0, '#', 0, 1, '2024-12-10 14:04:32', '2024-12-14 12:15:40', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (91, 1, 2, '获取分类详情', 'A', 'category:detail', '#', '/api/topic/categories/{id}', 'GET', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (92, 1, 2, '创建分类', 'A', 'category:create', '#', '/api/topic/categories', 'POST', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (93, 1, 2, '更新分类', 'A', 'category:update', '#', '/api/topic/categories', 'PUT', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (94, 1, 2, '删除分类', 'A', 'category:delete', '#', '/api/topic/categories/{id}', 'DELETE', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (95, 1, 2, '分类排序', 'A', 'category:sort', '#', '/api/topic/categories/sort', 'POST', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (96, 1, 6, '获取轮播图', 'A', 'banner:list', '#', '/api/banner/list', 'GET', NULL, 4, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-14 12:16:32', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (97, 1, 1, '搜索话题', 'A', 'search:topics', '#', '/api/search/topics', 'GET', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (98, 1, 5, '获取热门分类', 'A', 'hot:categories', '#', '/api/topic/hot/categories', 'GET', NULL, 3, '#', 0, 1, '2024-12-10 14:04:32', '2024-12-13 22:27:21', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (99, 1, 5, '获取热门标签', 'A', 'hot:tags', '#', '/api/topic/hot/tags', 'GET', NULL, 4, '#', 0, 1, '2024-12-10 14:04:32', '2024-12-13 22:33:45', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (100, 1, 5, '获取热门话题', 'A', 'hot:topics', '#', '/api/topic/hot/topics', 'GET', NULL, 1, '#', 0, 1, '2024-12-10 14:04:32', '2024-12-13 22:27:06', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (101, 1, 5, '获取热门评论', 'A', 'hot:comments', '#', '/api/topic/hot/comments', 'GET', NULL, 2, '#', 0, 1, '2024-12-10 14:04:32', '2024-12-13 22:33:33', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (102, 1, 5, '获取统计数据', 'A', 'stats:forum', '#', '/api/analysis/siteCount', 'GET', NULL, 0, '#', 0, 1, '2024-12-10 14:04:32', '2024-12-13 22:26:41', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (103, 1, 2, '获取活跃用户', 'A', 'hot:activeUser', '#', '/api/topic/activeUser', 'GET', NULL, 0, '#', 0, 1, '2024-12-10 14:23:49', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (104, 1, 6, '获取推荐话题', 'A', 'topic:recommend', '#', '/api/topic/recommend', 'GET', NULL, 2, '#', 0, 1, '2024-12-10 14:26:11', '2024-12-14 12:16:14', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (105, 1, 2, '获取作者信息', 'A', 'topic:author', '#', '/api/topic/author/{id}', 'GET', NULL, 0, '#', 0, 1, '2024-12-10 15:00:54', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (107, 1, 2, '关注作者', 'A', 'topic:follow', '#', '/api/topic/follow/{id}', 'POST', NULL, 0, '#', 0, 0, '2024-12-10 18:39:00', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (108, 1, 2, '取消关注', 'A', 'topic:unfollow', '#', '/api/topic/follow/{id}', 'DELETE', NULL, 0, '#', 0, 0, '2024-12-10 18:40:54', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (109, 1, 26, '上传用户头像', 'A', 'user:info:avatar', '#', '/api/user/avatar', 'POST', NULL, 0, '#', 0, 0, '2024-12-10 14:04:32', '2024-12-13 23:04:27', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (110, 1, 2, '获取文件地址', 'A', 'file:get', '#', '/api/file/getUrl', 'GET', NULL, 0, '#', 0, 1, '2024-12-10 14:26:11', '2024-12-13 19:02:42', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (200, 8, 0, '话题管理', 'C', 'topic', '/topic', NULL, NULL, NULL, 2, '#', 0, 0, '2024-12-14 13:11:06', '2024-12-14 17:54:31', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (204, 8, 242, '获取分类详情', 'A', 'category:detail', '#', '/api/topic/categories/{id}', 'GET', NULL, 0, '#', 0, 0, NULL, '2024-12-14 13:42:51', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (205, 8, 242, '创建分类', 'A', 'category:create', '#', '/api/topic/categories', 'POST', NULL, 0, '#', 0, 0, NULL, '2024-12-14 13:19:24', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (206, 8, 242, '更新分类', 'A', 'category:update', '#', '/api/topic/categories', 'PUT', NULL, 0, '#', 0, 0, NULL, '2024-12-14 13:18:53', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (207, 8, 242, '删除分类', 'A', 'category:delete', '#', '/api/topic/categories/{id}', 'DELETE', NULL, 0, '#', 0, 0, NULL, '2024-12-14 13:19:00', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (208, 8, 242, '分类排序', 'A', 'category:sort', '#', '/api/topic/categories/sort', 'POST', NULL, 0, '#', 0, 0, NULL, '2024-12-14 13:19:16', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (216, 8, 242, '获取分类列表', 'A', 'category:list', '#', '/api/topic/categories', 'GET', NULL, 0, '#', 0, 1, NULL, '2024-12-14 13:18:43', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (217, 8, 200, '获取话题列表', 'A', 'topic:list', '#', '/api/topic', 'GET', NULL, 1, '#', 0, 1, NULL, NULL, 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (219, 8, 200, '获取轮播图', 'A', 'banner:list', '#', '/api/banner/list', 'GET', NULL, 4, '#', 0, 0, NULL, NULL, 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (221, 8, 243, '获取评论列表', 'A', 'comment:list', '#', '/api/topic/comments/{topicId}', 'GET', NULL, 1, '#', 0, 1, NULL, '2024-12-14 13:38:06', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (230, 8, 200, '创建话题', 'A', 'topic:create', '#', '/api/topic', 'POST', NULL, 0, '#', 0, 0, NULL, NULL, 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (231, 8, 200, '更新话题', 'A', 'topic:update', '#', '/api/topic/{id}', 'PUT', NULL, 0, '#', 0, 0, NULL, NULL, 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (232, 8, 200, '删除话题', 'A', 'topic:delete', '#', '/api/topic/{id}', 'DELETE', NULL, 0, '#', 0, 0, NULL, NULL, 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (236, 8, 200, '收藏话题', 'A', 'topic:favorite', '#', '/api/topic/{id}/collect', 'POST', NULL, 1, '#', 0, 0, NULL, NULL, 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (237, 8, 243, '发表评论', 'A', 'comment:create', '#', '/api/topic/comments', 'POST', NULL, 0, '#', 0, 0, NULL, '2024-12-14 13:37:33', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (238, 8, 243, '删除评论', 'A', 'comment:delete', '#', '/api/topic/comments/{id}', 'DELETE', NULL, 0, '#', 0, 0, NULL, '2024-12-14 13:37:33', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (242, 8, 0, '分类管理', 'C', 'comment', '/comment', '', 'GET', NULL, 1, '', 0, 0, '2024-12-14 13:17:56', '2024-12-14 13:43:24', 0);
INSERT INTO `sys_resource` (`id`, `app_id`, `parent_id`, `resource_name`, `resource_type`, `resource_key`, `resource_url`, `api_url`, `method`, `component`, `sort`, `icon`, `status`, `is_public`, `created_at`, `updated_at`, `deleted`) VALUES (243, 8, 0, '评论管理', 'C', 'comment', '/comment', '', 'GET', NULL, 3, '', 0, 0, '2024-12-14 13:23:49', '2024-12-14 13:43:39', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `app_id` bigint NOT NULL COMMENT '应用id',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '角色状态（0停用 1正常）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_key` (`role_key`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `app_id`, `role_name`, `role_key`, `role_sort`, `status`, `remark`, `created_at`, `updated_at`, `deleted`) VALUES (2, 1, '普通用户', 'user', 2, 1, '普通用户', '2024-12-09 19:36:43', '2024-12-16 01:02:54', 0);
INSERT INTO `sys_role` (`id`, `app_id`, `role_name`, `role_key`, `role_sort`, `status`, `remark`, `created_at`, `updated_at`, `deleted`) VALUES (3, 1, '演示', 'demo', NULL, 1, '演示角色', '2024-12-14 13:57:30', '2024-12-16 01:02:45', 0);
INSERT INTO `sys_role` (`id`, `app_id`, `role_name`, `role_key`, `role_sort`, `status`, `remark`, `created_at`, `updated_at`, `deleted`) VALUES (5, 8, 'demo', '123', NULL, 1, 't', '2024-12-16 00:15:03', '2024-12-16 01:08:29', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `app_id` bigint NOT NULL COMMENT 'appid',
  `resource_id` bigint NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`role_id`,`resource_id`,`app_id`) USING BTREE,
  KEY `idx_resource_id` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和资源关联表';

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 1);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 1);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 2);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 2);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 3);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 5);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 5);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 6);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 6);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 7);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 7);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 8);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 8);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 9);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 9);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 10);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 11);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 12);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 13);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 14);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 15);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 15);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 16);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 16);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 17);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 17);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 18);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 18);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 19);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 19);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 20);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 20);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 21);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 21);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 22);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 22);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 23);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 23);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 24);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 25);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 26);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 67);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 68);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 69);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 70);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 71);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 72);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 73);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 74);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 75);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 75);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 76);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 76);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 77);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 77);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 78);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 78);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 79);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 79);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 80);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 80);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 81);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 81);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 82);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 82);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 83);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 83);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 84);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 84);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 85);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 85);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 86);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 86);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 87);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 87);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 88);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 88);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 89);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 89);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 90);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 90);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 91);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 91);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 92);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 92);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 93);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 93);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 94);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 94);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 95);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 95);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 96);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 96);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 97);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 97);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 98);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 98);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 99);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 99);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 100);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 100);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 101);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 101);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 102);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 102);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 103);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 103);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 104);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 104);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 105);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 105);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 107);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 107);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 108);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 108);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 109);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (2, 1, 110);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (3, 1, 110);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 200);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 204);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 205);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 206);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 207);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 208);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 216);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 217);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 219);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 221);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 230);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 231);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 232);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 236);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 237);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 238);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 242);
INSERT INTO `sys_role_resource` (`role_id`, `app_id`, `resource_id`) VALUES (5, 8, 243);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nickname` varchar(50) NOT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `gender` tinyint NOT NULL DEFAULT '2' COMMENT '性别 0男 1女 2未知',
  `bio` varchar(255) DEFAULT NULL COMMENT '个人简介',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0:禁用 1:正常',
  `last_login` datetime DEFAULT NULL COMMENT '最后登录时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (1, 'superAdmin', '123456', '超级管理员', 'https://api.dicebear.com/7.x/avataaars/svg?seed=1', 'admin@example.com', NULL, 0, '系统管理员', 1, '2024-12-11 13:07:41', '2024-12-05 22:14:44', '2024-12-16 18:41:50');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (2, 'john_doe', '123456', 'John Doe', 'https://api.dicebear.com/7.x/avataaars/svg?seed=2', 'john@example.com', NULL, 1, '前端开发工程师，Vue.js 爱好者', 1, '2024-12-11 13:07:39', '2024-12-05 22:14:44', '2024-12-14 17:19:22');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (3, 'alice', '123456', 'Alice', 'https://api.dicebear.com/7.x/avataaars/svg?seed=3', 'alice@example.com', NULL, 1, '全栈开发者，热爱技术分享', 1, '2024-12-11 13:07:39', '2024-12-05 22:14:44', '2024-12-14 17:19:31');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (4, 'bob', '$2a$10$X/hX5qrs.NPpVqsX9Qx0K.Cl.7nxJ1hxVxm2.vvZuJL1uZozqvz6W', 'Bob', 'https://api.dicebear.com/7.x/avataaars/svg?seed=4', 'bob@example.com', NULL, 0, '后端工程师，Java专家', 1, '2024-12-11 13:07:39', '2024-12-05 22:14:44', '2024-12-14 14:54:36');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (5, 'emma', 'Qwer1234.', 'Emma', 'https://api.dicebear.com/7.x/avataaars/svg?seed=5', 'emma@example.com', NULL, 0, 'UI设计师，追求极致体验', 1, '2024-12-11 13:07:39', '2024-12-05 22:14:44', '2024-12-14 17:50:09');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (6, 'misan', '123456', 'Mi San', 'U5qlZtozKlqWejwc.png', '854236339@qq.com', NULL, 0, '后端工程师，Java专家123', 1, '2024-12-12 17:10:11', '2024-12-06 14:40:18', '2024-12-14 14:54:36');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (6, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_resource`;
CREATE TABLE `sys_user_role_resource` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `app_id` bigint NOT NULL COMMENT '应用id',
  `resource_id` bigint NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`user_id`,`role_id`,`app_id`,`resource_id`),
  KEY `idx_resource_id` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户和资源关联表';

-- ----------------------------
-- Records of sys_user_role_resource
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 1);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 1);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 1);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 2);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 2);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 2);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 3);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 5);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 5);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 5);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 6);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 6);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 6);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 7);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 7);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 7);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 8);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 8);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 8);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 9);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 9);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 9);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 10);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 11);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 12);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 13);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 14);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 15);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 15);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 15);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 16);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 16);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 16);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 17);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 17);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 17);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 18);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 18);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 18);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 19);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 19);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 19);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 20);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 20);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 20);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 21);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 21);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 21);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 22);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 22);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 22);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 23);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 23);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 23);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 24);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 25);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 26);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 67);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 68);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 69);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 70);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 71);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 72);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 73);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 74);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 75);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 75);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 75);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 76);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 76);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 76);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 77);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 77);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 77);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 78);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 78);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 78);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 79);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 79);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 79);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 80);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 80);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 80);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 81);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 81);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 81);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 82);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 82);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 82);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 83);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 83);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 83);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 84);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 84);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 84);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 85);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 85);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 85);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 86);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 86);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 86);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 87);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 87);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 87);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 88);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 88);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 88);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 89);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 89);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 89);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 90);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 90);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 90);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 91);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 91);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 91);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 92);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 92);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 92);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 93);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 93);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 93);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 94);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 94);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 94);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 95);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 95);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 95);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 96);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 96);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 96);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 97);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 97);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 97);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 98);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 98);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 98);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 99);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 99);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 99);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 100);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 100);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 100);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 101);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 101);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 101);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 102);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 102);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 102);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 103);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 103);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 103);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 104);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 104);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 104);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 105);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 105);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 105);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 107);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 107);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 107);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 108);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 108);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 108);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 109);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (6, 2, 1, 110);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 2, 1, 110);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 3, 1, 110);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 200);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 204);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 205);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 206);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 207);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 208);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 216);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 217);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 219);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 221);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 230);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 231);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 232);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 236);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 237);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 238);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 242);
INSERT INTO `sys_user_role_resource` (`user_id`, `role_id`, `app_id`, `resource_id`) VALUES (7, 5, 8, 243);
COMMIT;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` varchar(50) NOT NULL COMMENT '标签名称',
  `color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签颜色',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签表';

-- ----------------------------
-- Records of tags
-- ----------------------------
BEGIN;
INSERT INTO `tags` (`id`, `name`, `color`, `created_at`, `updated_at`) VALUES (1, 'Vue.js', '#F4A4C0', '2024-12-05 22:14:44', '2024-12-06 10:51:15');
INSERT INTO `tags` (`id`, `name`, `color`, `created_at`, `updated_at`) VALUES (2, 'JavaScript', '#B18CFE', '2024-12-05 22:14:44', '2024-12-06 10:51:42');
INSERT INTO `tags` (`id`, `name`, `color`, `created_at`, `updated_at`) VALUES (3, 'CSS', '#FF6251', '2024-12-05 22:14:44', '2024-12-06 10:52:17');
INSERT INTO `tags` (`id`, `name`, `color`, `created_at`, `updated_at`) VALUES (4, 'React', '#F4A4C0', '2024-12-05 22:14:44', '2024-12-06 10:52:30');
INSERT INTO `tags` (`id`, `name`, `color`, `created_at`, `updated_at`) VALUES (5, 'Node.js', '#B18CFE', '2024-12-05 22:14:44', '2024-12-06 10:52:30');
INSERT INTO `tags` (`id`, `name`, `color`, `created_at`, `updated_at`) VALUES (6, 'TypeScript', '#FF6251', '2024-12-05 22:14:44', '2024-12-06 10:52:30');
INSERT INTO `tags` (`id`, `name`, `color`, `created_at`, `updated_at`) VALUES (7, '前端开发', '#F4A4C0', '2024-12-05 22:14:44', '2024-12-06 10:52:33');
INSERT INTO `tags` (`id`, `name`, `color`, `created_at`, `updated_at`) VALUES (8, '后端开发', '#B18CFE', '2024-12-05 22:14:44', '2024-12-06 10:52:33');
INSERT INTO `tags` (`id`, `name`, `color`, `created_at`, `updated_at`) VALUES (9, '面试', '#FF6251', '2024-12-05 22:14:44', '2024-12-06 10:52:33');
INSERT INTO `tags` (`id`, `name`, `color`, `created_at`, `updated_at`) VALUES (10, '职场', '#FF6251', '2024-12-05 22:14:44', '2024-12-06 10:52:38');
COMMIT;

-- ----------------------------
-- Table structure for topic_tags
-- ----------------------------
DROP TABLE IF EXISTS `topic_tags`;
CREATE TABLE `topic_tags` (
  `topic_id` bigint unsigned NOT NULL COMMENT '话题ID',
  `tag_id` bigint unsigned NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`topic_id`,`tag_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='话题标签关联表';

-- ----------------------------
-- Records of topic_tags
-- ----------------------------
BEGIN;
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (1, 1);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (10, 1);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (11, 2);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (12, 2);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (17, 2);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (18, 2);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (4, 3);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (14, 3);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (22, 3);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (12, 4);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (16, 4);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (17, 4);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (11, 5);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (13, 5);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (17, 5);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (16, 6);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (1, 7);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (2, 7);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (4, 7);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (15, 7);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (17, 7);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (22, 7);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (2, 8);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (13, 8);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (3, 9);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (3, 10);
INSERT INTO `topic_tags` (`topic_id`, `tag_id`) VALUES (5, 10);
COMMIT;

-- ----------------------------
-- Table structure for topic_views
-- ----------------------------
DROP TABLE IF EXISTS `topic_views`;
CREATE TABLE `topic_views` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `topic_id` bigint NOT NULL COMMENT '主题ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `id_topic_id` (`topic_id`) USING BTREE,
  KEY `id_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=329 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户阅读记录表';

-- ----------------------------
-- Records of topic_views
-- ----------------------------
BEGIN;
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (11, 1, 6, '2024-12-07 21:47:40');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (19, 1, 6, '2024-12-07 23:08:11');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (20, 1, 6, '2024-12-07 23:08:21');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (21, 1, 6, '2024-12-07 23:08:21');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (22, 7, 6, '2024-12-08 00:24:20');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (23, 5, 6, '2024-12-08 00:36:18');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (24, 1, 6, '2024-12-08 00:37:04');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (25, 1, 6, '2024-12-08 00:37:04');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (26, 1, 6, '2024-12-08 00:45:12');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (27, 1, 6, '2024-12-08 01:02:53');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (28, 1, 6, '2024-12-08 01:29:39');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (29, 1, 6, '2024-12-08 15:46:52');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (30, 1, 6, '2024-12-08 15:46:53');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (31, 1, 6, '2024-12-08 15:47:09');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (32, 1, 6, '2024-12-08 16:21:21');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (33, 1, 6, '2024-12-08 16:21:21');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (34, 4, 6, '2024-12-08 16:21:28');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (35, 4, 6, '2024-12-08 16:21:28');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (36, 4, 6, '2024-12-08 16:24:22');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (37, 4, 6, '2024-12-08 16:24:22');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (38, 2, 6, '2024-12-08 16:24:27');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (39, 2, 6, '2024-12-08 16:24:27');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (40, 2, 6, '2024-12-09 11:33:07');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (41, 1, 6, '2024-12-09 12:31:23');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (42, 1, 6, '2024-12-09 12:35:59');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (43, 1, 6, '2024-12-09 16:08:59');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (44, 1, 6, '2024-12-09 16:09:05');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (45, 7, 6, '2024-12-09 16:09:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (46, 7, 6, '2024-12-09 16:09:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (47, 7, 6, '2024-12-09 16:11:54');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (48, 7, 6, '2024-12-09 16:12:15');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (49, 7, 6, '2024-12-09 16:12:16');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (50, 5, 6, '2024-12-09 16:13:16');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (51, 5, 6, '2024-12-09 16:13:17');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (52, 1, 6, '2024-12-09 16:13:21');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (53, 1, 6, '2024-12-09 16:13:21');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (54, 1, 6, '2024-12-09 16:24:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (55, 1, 6, '2024-12-09 16:24:27');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (56, 1, 6, '2024-12-09 16:24:32');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (57, 1, 6, '2024-12-09 16:24:48');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (58, 1, 6, '2024-12-09 16:24:53');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (59, 1, 6, '2024-12-09 16:26:28');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (60, 1, 6, '2024-12-09 16:27:05');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (61, 1, 6, '2024-12-09 16:27:08');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (62, 1, 6, '2024-12-09 16:27:33');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (63, 1, 6, '2024-12-09 16:27:57');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (64, 1, 6, '2024-12-09 16:28:05');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (65, 1, 6, '2024-12-09 16:28:21');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (66, 1, 6, '2024-12-09 16:28:26');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (67, 1, 6, '2024-12-09 16:30:31');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (68, 1, 6, '2024-12-09 16:31:35');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (69, 1, 6, '2024-12-09 16:33:20');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (70, 1, 6, '2024-12-09 16:34:57');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (71, 1, 6, '2024-12-09 16:35:08');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (72, 1, 6, '2024-12-09 16:36:21');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (73, 1, 6, '2024-12-09 16:37:53');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (74, 1, 6, '2024-12-09 16:37:54');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (75, 1, 6, '2024-12-09 16:39:55');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (76, 1, 6, '2024-12-09 16:40:20');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (77, 1, 6, '2024-12-09 16:41:16');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (78, 1, 6, '2024-12-09 16:41:57');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (79, 1, 6, '2024-12-09 16:42:12');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (80, 1, 6, '2024-12-09 16:42:30');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (81, 1, 6, '2024-12-09 16:42:50');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (82, 1, 6, '2024-12-09 16:42:55');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (83, 1, 6, '2024-12-09 16:52:23');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (84, 1, 6, '2024-12-09 17:03:47');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (85, 1, 6, '2024-12-09 17:04:35');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (86, 1, 6, '2024-12-09 17:05:17');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (87, 1, 6, '2024-12-09 17:06:36');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (88, 1, 6, '2024-12-09 17:09:03');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (89, 1, 6, '2024-12-09 17:09:43');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (90, 1, 6, '2024-12-09 17:17:21');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (91, 1, 6, '2024-12-09 17:17:34');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (92, 1, 6, '2024-12-09 17:18:02');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (93, 1, 6, '2024-12-09 17:19:23');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (94, 1, 6, '2024-12-09 17:20:17');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (95, 1, 6, '2024-12-09 17:20:17');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (96, 1, 6, '2024-12-09 17:21:43');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (97, 1, 6, '2024-12-09 17:22:00');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (98, 1, 6, '2024-12-09 17:23:35');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (99, 1, 6, '2024-12-09 17:24:38');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (100, 1, 6, '2024-12-09 17:26:10');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (101, 1, 6, '2024-12-09 17:26:17');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (102, 1, 6, '2024-12-09 17:27:24');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (103, 1, 6, '2024-12-09 17:28:27');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (104, 7, 6, '2024-12-09 17:29:14');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (105, 7, 6, '2024-12-09 17:30:06');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (106, 7, 6, '2024-12-09 17:33:56');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (107, 7, 6, '2024-12-09 17:33:57');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (108, 11, 6, '2024-12-09 17:34:37');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (109, 11, 6, '2024-12-09 17:34:37');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (110, 3, 6, '2024-12-09 18:17:39');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (111, 4, 6, '2024-12-09 18:27:56');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (112, 4, 6, '2024-12-09 18:27:56');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (113, 1, NULL, '2024-12-10 14:50:46');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (114, 1, 6, '2024-12-10 16:21:58');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (115, 1, 6, '2024-12-10 16:21:59');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (116, 16, 6, '2024-12-10 17:47:43');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (117, 5, 6, '2024-12-10 18:35:25');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (118, 5, 6, '2024-12-10 18:35:26');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (119, 4, 6, '2024-12-10 18:35:31');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (120, 4, 6, '2024-12-10 18:35:31');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (121, 1, 6, '2024-12-10 18:36:18');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (122, 4, 6, '2024-12-10 18:36:18');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (123, 1, 6, '2024-12-10 18:42:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (124, 1, 6, '2024-12-10 18:42:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (125, 1, 6, '2024-12-10 18:43:45');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (126, 1, 6, '2024-12-10 18:43:45');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (127, 1, 6, '2024-12-10 18:43:55');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (128, 1, 6, '2024-12-10 18:45:06');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (129, 1, 6, '2024-12-10 18:45:06');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (130, 1, 6, '2024-12-10 18:46:27');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (131, 1, 6, '2024-12-10 18:46:27');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (132, 1, 6, '2024-12-10 18:46:38');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (133, 1, 6, '2024-12-10 18:46:38');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (134, 1, 6, '2024-12-10 18:47:40');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (135, 1, 6, '2024-12-10 18:47:53');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (136, 1, 6, '2024-12-10 18:48:04');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (137, 1, 6, '2024-12-10 18:48:06');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (138, 1, 6, '2024-12-10 18:48:14');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (139, 1, 6, '2024-12-10 18:48:14');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (140, 1, 6, '2024-12-10 18:49:00');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (141, 1, 6, '2024-12-10 18:49:00');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (142, 1, 6, '2024-12-10 18:49:51');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (143, 1, 6, '2024-12-10 18:49:51');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (144, 1, 6, '2024-12-10 18:50:02');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (145, 1, 6, '2024-12-10 18:50:02');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (146, 1, 6, '2024-12-10 18:50:45');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (147, 1, 6, '2024-12-10 19:05:30');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (148, 1, 6, '2024-12-10 19:05:30');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (149, 1, 6, '2024-12-10 19:06:33');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (150, 1, 6, '2024-12-10 19:06:33');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (151, 1, 6, '2024-12-10 19:07:44');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (152, 1, 6, '2024-12-10 19:07:44');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (153, 1, 6, '2024-12-10 19:07:47');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (154, 5, 6, '2024-12-10 19:07:51');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (155, 5, 6, '2024-12-10 19:07:51');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (156, 1, 6, '2024-12-10 19:08:06');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (157, 5, 6, '2024-12-10 19:08:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (158, 5, 6, '2024-12-10 19:08:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (159, 5, 6, '2024-12-10 19:15:08');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (160, 5, 6, '2024-12-10 19:16:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (161, 5, 6, '2024-12-10 19:20:20');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (162, 5, 6, '2024-12-10 19:20:52');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (163, 5, 6, '2024-12-10 19:21:03');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (164, 5, 6, '2024-12-10 19:21:14');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (165, 5, 6, '2024-12-10 19:21:27');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (166, 5, 6, '2024-12-10 19:21:48');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (167, 5, 6, '2024-12-10 19:25:10');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (168, 5, 6, '2024-12-10 19:30:09');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (169, 5, 6, '2024-12-10 19:30:39');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (170, 5, 6, '2024-12-10 19:30:55');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (171, 5, 6, '2024-12-10 19:31:25');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (172, 5, 6, '2024-12-10 19:34:40');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (173, 5, 6, '2024-12-10 19:37:43');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (174, 5, 6, '2024-12-10 19:39:33');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (175, 5, 6, '2024-12-10 19:41:51');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (176, 5, 6, '2024-12-10 19:43:42');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (177, 5, 6, '2024-12-10 19:55:18');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (178, 5, 6, '2024-12-10 19:55:25');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (179, 5, 6, '2024-12-10 22:06:36');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (180, 1, 6, '2024-12-10 22:06:46');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (181, 1, 6, '2024-12-10 22:06:47');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (182, 1, 6, '2024-12-10 22:21:32');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (183, 1, 6, '2024-12-11 00:03:53');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (184, 1, 6, '2024-12-11 00:04:29');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (185, 1, 6, '2024-12-11 00:04:37');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (186, 1, 6, '2024-12-11 00:05:00');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (187, 1, 6, '2024-12-11 00:05:09');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (188, 1, 6, '2024-12-11 00:05:37');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (189, 1, 6, '2024-12-11 00:05:52');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (190, 1, 6, '2024-12-11 00:05:55');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (191, 1, 6, '2024-12-11 00:06:43');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (192, 1, 6, '2024-12-11 00:33:37');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (193, 1, 6, '2024-12-11 00:41:29');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (194, 1, 6, '2024-12-11 00:41:30');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (195, 1, 6, '2024-12-11 00:47:08');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (196, 1, 6, '2024-12-11 00:47:08');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (197, 5, 6, '2024-12-11 00:47:18');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (198, 5, 6, '2024-12-11 00:47:19');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (199, 1, 6, '2024-12-11 00:47:50');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (200, 1, 6, '2024-12-11 00:47:50');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (201, 5, 6, '2024-12-11 00:48:50');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (202, 5, 6, '2024-12-11 00:48:51');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (203, 1, 6, '2024-12-11 00:49:08');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (204, 1, 6, '2024-12-11 00:49:08');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (205, 1, 6, '2024-12-11 00:49:50');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (206, 1, 6, '2024-12-11 00:55:44');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (207, 7, 6, '2024-12-11 00:58:54');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (208, 1, 6, '2024-12-11 00:59:05');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (209, 3, 6, '2024-12-11 01:00:40');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (210, 1, 6, '2024-12-11 01:00:43');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (211, 5, 6, '2024-12-11 01:00:48');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (212, 7, 6, '2024-12-11 01:01:52');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (213, 1, 6, '2024-12-11 01:12:25');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (214, 7, 6, '2024-12-11 01:12:31');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (215, 2, 6, '2024-12-11 01:12:34');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (216, 16, 6, '2024-12-11 23:36:08');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (217, 1, 6, '2024-12-11 23:36:16');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (218, 13, 6, '2024-12-11 23:36:22');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (219, 17, 6, '2024-12-11 23:41:29');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (220, 17, 6, '2024-12-11 23:42:27');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (221, 17, 6, '2024-12-11 23:43:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (222, 17, 6, '2024-12-11 23:44:06');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (223, 17, 6, '2024-12-11 23:44:35');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (224, 17, 6, '2024-12-11 23:45:00');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (225, 17, 6, '2024-12-11 23:46:01');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (226, 17, 6, '2024-12-11 23:46:09');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (227, 17, 6, '2024-12-11 23:49:03');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (228, 5, 6, '2024-12-11 23:49:18');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (229, 5, 6, '2024-12-11 23:49:18');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (230, 17, 6, '2024-12-11 23:49:26');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (231, 17, 6, '2024-12-11 23:49:32');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (232, 17, 6, '2024-12-11 23:49:50');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (233, 17, 6, '2024-12-11 23:49:57');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (234, 17, 6, '2024-12-11 23:50:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (235, 17, 6, '2024-12-11 23:51:54');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (236, 17, 6, '2024-12-11 23:52:49');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (237, 17, 6, '2024-12-11 23:53:00');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (238, 17, 6, '2024-12-11 23:54:26');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (239, 17, 6, '2024-12-11 23:55:15');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (240, 17, 6, '2024-12-11 23:55:17');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (241, 17, 6, '2024-12-11 23:56:04');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (242, 1, 6, '2024-12-11 23:56:08');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (243, 17, 6, '2024-12-11 23:56:13');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (244, 17, 6, '2024-12-11 23:56:22');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (245, 17, 6, '2024-12-12 15:06:08');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (246, 17, 6, '2024-12-12 15:06:11');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (247, 16, 6, '2024-12-12 15:06:25');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (248, 17, 6, '2024-12-12 15:07:57');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (249, 17, 6, '2024-12-12 15:09:18');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (250, 16, 6, '2024-12-12 15:10:43');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (251, 17, 6, '2024-12-12 15:11:07');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (252, 17, 6, '2024-12-12 15:11:44');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (253, 17, 6, '2024-12-12 15:12:34');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (254, 16, 6, '2024-12-12 15:12:40');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (255, 17, 6, '2024-12-12 15:14:24');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (256, 16, 6, '2024-12-12 15:14:35');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (257, 17, 6, '2024-12-12 15:16:09');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (258, 17, 6, '2024-12-12 15:17:25');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (259, 17, 6, '2024-12-12 15:17:30');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (260, 17, 6, '2024-12-12 15:17:34');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (261, 17, 6, '2024-12-12 15:17:38');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (262, 17, 6, '2024-12-12 15:17:43');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (263, 17, 6, '2024-12-12 15:17:48');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (264, 5, 6, '2024-12-12 17:16:42');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (265, 5, 6, '2024-12-12 17:16:43');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (266, 7, 6, '2024-12-12 17:17:01');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (267, 7, 6, '2024-12-12 17:17:01');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (268, 7, 6, '2024-12-12 17:18:47');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (269, 7, 6, '2024-12-12 17:19:03');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (270, 1, 6, '2024-12-12 17:19:57');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (271, 1, 6, '2024-12-12 17:19:57');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (272, 1, 6, '2024-12-12 17:20:30');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (273, 5, 6, '2024-12-12 17:20:46');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (274, 5, 6, '2024-12-12 17:22:28');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (275, 5, 6, '2024-12-12 17:22:28');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (276, 5, 6, '2024-12-12 17:22:44');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (277, 5, 6, '2024-12-12 17:22:49');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (278, 5, 6, '2024-12-12 17:22:52');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (279, 5, 6, '2024-12-12 17:23:19');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (280, 1, 6, '2024-12-12 17:23:25');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (281, 1, 6, '2024-12-12 17:23:56');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (282, 1, 6, '2024-12-12 17:23:57');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (283, 1, 6, '2024-12-12 17:23:59');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (284, 1, 6, '2024-12-12 17:24:36');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (285, 1, 6, '2024-12-12 17:24:38');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (286, 1, 6, '2024-12-12 17:24:52');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (287, 1, 6, '2024-12-12 17:25:09');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (288, 1, 6, '2024-12-12 17:26:36');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (289, 1, 6, '2024-12-12 17:27:05');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (290, 1, 6, '2024-12-12 17:28:07');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (291, 1, 6, '2024-12-12 17:28:32');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (292, 1, 6, '2024-12-12 17:29:15');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (293, 1, 6, '2024-12-12 17:30:22');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (294, 1, 6, '2024-12-12 17:33:20');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (295, 1, 6, '2024-12-12 17:35:45');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (296, 1, 6, '2024-12-12 17:35:46');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (297, 1, 6, '2024-12-12 17:37:37');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (298, 1, 6, '2024-12-12 17:37:54');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (299, 1, 6, '2024-12-12 17:50:51');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (300, 1, 6, '2024-12-12 17:50:51');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (301, 7, 6, '2024-12-12 18:56:36');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (302, 1, 6, '2024-12-12 18:57:39');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (303, 1, 6, '2024-12-12 18:57:40');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (304, 7, 6, '2024-12-12 18:58:22');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (305, 17, 6, '2024-12-12 18:58:54');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (306, 7, 6, '2024-12-12 18:58:57');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (307, 4, 6, '2024-12-12 19:49:37');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (308, 4, 6, '2024-12-12 19:49:37');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (309, 2, 6, '2024-12-12 21:21:54');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (310, 2, 6, '2024-12-12 21:21:54');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (311, 18, 6, '2024-12-12 22:19:27');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (312, 22, 6, '2024-12-12 22:30:50');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (313, 22, 6, '2024-12-12 22:40:12');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (314, 17, 6, '2024-12-12 22:43:52');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (315, 1, 6, '2024-12-12 22:44:16');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (316, 1, 6, '2024-12-12 22:44:17');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (317, 3, 6, '2024-12-12 22:55:12');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (318, 3, 6, '2024-12-12 22:55:12');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (319, 1, 6, '2024-12-13 13:45:47');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (320, 1, 6, '2024-12-13 13:45:48');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (321, 1, 6, '2024-12-13 13:45:59');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (322, 1, 6, '2024-12-15 21:38:15');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (323, 1, 6, '2024-12-15 21:38:15');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (324, 1, 6, '2024-12-15 23:04:40');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (325, 1, 6, '2024-12-15 23:04:40');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (326, 1, 6, '2024-12-15 23:05:17');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (327, 1, 6, '2024-12-15 23:05:55');
INSERT INTO `topic_views` (`id`, `topic_id`, `user_id`, `created_at`) VALUES (328, 1, 6, '2024-12-15 23:05:55');
COMMIT;

-- ----------------------------
-- Table structure for topics
-- ----------------------------
DROP TABLE IF EXISTS `topics`;
CREATE TABLE `topics` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '话题ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `user_id` bigint unsigned NOT NULL COMMENT '作者ID',
  `category_id` bigint unsigned NOT NULL COMMENT '分类ID',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0:删除 1:正常',
  `audit` tinyint NOT NULL DEFAULT '0' COMMENT '审核状态 0正在审核 1通过 2不通过',
  `is_top` tinyint NOT NULL DEFAULT '0' COMMENT '是否置顶 0:普通 1:置顶',
  `is_featured` tinyint NOT NULL DEFAULT '0' COMMENT '是否精选 0:否 1:是',
  `view_count` int NOT NULL DEFAULT '0' COMMENT '浏览数',
  `collect_count` int NOT NULL DEFAULT '0' COMMENT '收藏数',
  `like_count` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `comment_count` int NOT NULL DEFAULT '0' COMMENT '评论数',
  `last_comment_at` datetime DEFAULT NULL COMMENT '最后评论时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='话题表';

-- ----------------------------
-- Records of topics
-- ----------------------------
BEGIN;
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (1, 'Vue 3 组合式 API 最佳实践', '# Vue 3 组合式 API 最佳实践\n\n## 1. 为什么使用组合式 API\n组合式 API 提供了更好的代码组织方式...\n\n## 2. 基本用法\n以下是一些常见的使用场景...', 2, 1, 1, 1, 1, 1, 1733, 4, 60, 21, NULL, '2024-12-05 22:14:44', '2024-12-15 23:05:55');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (2, '2024年前端开发趋势展望', '随着技术的不断发展，前端开发领域在2024年将迎来新的变革...\n\n1. 微前端架构的普及\n2. AI 辅助开发工具的崛起\n3. WebAssembly 的广泛应用', 3, 1, 1, 1, 0, 1, 903, 0, 67, 34, NULL, '2024-12-05 22:14:44', '2024-12-13 14:00:02');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (3, '如何准备前端面试？', '作为一名前端开发者，面试准备是求职过程中的重要环节...\n\n## 技术准备\n1. JavaScript 基础\n2. 框架原理\n3. 性能优化', 4, 3, 1, 1, 0, 0, 573, 0, 34, 47, NULL, '2024-12-05 22:14:44', '2024-12-13 14:00:04');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (4, '分享一个实用的 CSS 技巧', '今天给大家分享一个在实际项目中经常用到的 CSS 技巧...\n\n```css\n.container {\n  display: grid;\n  place-items: center;\n}\n```', 5, 4, 1, 1, 1, 0, 370, 0, 25, 12, NULL, '2024-12-05 22:14:44', '2024-12-13 13:45:56');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (5, '远程工作一年的心得体会', '去年开始尝试远程工作，这一年来有很多感悟和经验想要分享...\n\n## 优点\n1. 时间自由\n2. 省去通勤\n\n## 挑战\n1. 自律要求高\n2. 沟通成本增加', 6, 2, 1, 1, 1, 0, 868, 0, 56, 28, NULL, '2024-12-05 22:14:44', '2024-12-12 18:59:43');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (7, '测试markdown', '<p><br></p><p><br></p><h1>简介</h1><p style=\"text-align: start;\">让我们用不到5分钟的时候，阅读 Markdown 概述，了解其工作原理以及你可以用它做什么。</p><h2 style=\"text-align: start;\">什么是 Markdown？</h2><p style=\"text-align: start;\">Markdown 是一种轻量级标记语言，你可以使用它向纯文本文档添加格式元素。Markdown 由 <a href=\"https://daringfireball.net/projects/markdown/\" target=\"_blank\">John Gruber</a> 于 2004 年创建，现在是世界上最流行的标记语言之一。</p><p style=\"text-align: start;\">使用 Markdown 与使用 <a href=\"https://en.wikipedia.org/wiki/WYSIWYG\" target=\"_blank\">WYSIWYG</a> 编辑器不同。在 Microsoft Word 等应用程序中，你可以单击按钮来设置单词和短语的格式，并且更改会立即显示。Markdown 并非如此。当你创建一个 Markdown 格式的文件时，你向文本添加 Markdown 语法来指示哪些单词和短语应显示为不同格式。</p><p style=\"text-align: start;\">例如，要表示标题，你可以在标题前添加一个井号（例如，# 标题一）。或者要使短语变为粗体，你可以在短语前后添加两个星号（例如，<strong>此文本为粗体</strong>）。在文本中看到 Markdown 语法可能需要一段时间才能适应，特别是如果你习惯于 WYSIWYG 应用程序。下面的屏幕截图显示了在 <a href=\"https://markdownguide.cn/tools/vscode/\" target=\"_blank\">Visual Studio Code</a> 文本编辑器 中显示的 Markdown 文件。</p><p style=\"text-align: start;\"><img src=\"https://www.markdown.cn/assets/images/vscode-bd3b2a22c4c4fd3cb35e8384855b2622.png\" alt=\"vscode.png\" data-href=\"\" style=\"height: auto;\"></p><p style=\"text-align: start;\">你可以使用文本编辑器应用程序向纯文本文件添加 Markdown 格式元素。或者，你可以使用 macOS、Windows、Linux、iOS 和 Android 操作系统的众多 Markdown 应用程序之一。还有专门用于编写 Markdown 的几个基于 Web 的应用程序。</p><p style=\"text-align: start;\">根据你使用的应用程序，你可能无法实时预览格式化的文档。但这没关系。据 <a href=\"https://daringfireball.net/projects/markdown/\" target=\"_blank\">Gruber</a> 所说，Markdown 语法旨在具有可读性和不显眼性，因此即使没有呈现 Markdown 文件中的文本，也可以读取该文本。</p><p style=\"text-align: start;\">NOTE</p><p style=\"text-align: start;\"><br></p><p>Markdown 格式语法的主要设计目标是使其尽可能具有可读性。其理念是 Markdown 格式的文档应可按原样发布为纯文本，而不会看起来像是用标记或格式化指令标记的。</p>', 6, 2, 1, 1, 0, 0, 44, 0, 0, 1, NULL, '2024-12-07 12:43:38', '2024-12-13 14:00:03');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (13, '分页2', '<p>分页1分页1分页1</p>', 6, 2, 1, 1, 0, 0, 1, 0, 0, 0, NULL, '2024-12-09 18:31:44', '2024-12-12 18:59:43');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (14, '分页1分页1', '<p>分页1分页1</p>', 6, 1, 1, 1, 0, 0, 0, 0, 0, 0, NULL, '2024-12-09 18:31:51', '2024-12-12 18:59:43');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (15, '分页1分页1分页1分页1分页1', '<p>分页1分页1分页1分页1分页1</p>', 6, 2, 1, 1, 0, 0, 0, 0, 0, 0, NULL, '2024-12-09 18:31:58', '2024-12-12 18:59:43');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (16, '分页3333', '<p>分页1分页1分页1</p>', 6, 4, 1, 1, 0, 0, 6, 0, 0, 0, NULL, '2024-12-09 18:32:09', '2024-12-12 18:59:43');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (17, '测试编辑的', '<p>123123123123123123</p>', 6, 3, 1, 0, 0, 0, 40, 0, 0, 0, NULL, '2024-12-11 23:36:50', '2024-12-12 22:43:54');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (18, '测试审核', '<p>测试审核1</p>', 6, 2, 1, 0, 0, 0, 1, 0, 0, 0, NULL, '2024-12-12 19:01:25', '2024-12-12 22:19:30');
INSERT INTO `topics` (`id`, `title`, `content`, `user_id`, `category_id`, `status`, `audit`, `is_top`, `is_featured`, `view_count`, `collect_count`, `like_count`, `comment_count`, `last_comment_at`, `created_at`, `updated_at`) VALUES (22, '测试消息通知', '<p>1测试消息通知测试消息通知111111</p>', 6, 3, 1, 0, 0, 0, 2, 0, 0, 0, NULL, '2024-12-12 22:14:15', '2024-12-12 22:40:14');
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nickname` varchar(50) NOT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `gender` tinyint NOT NULL DEFAULT '2' COMMENT '性别 0男 1女 2未知',
  `bio` varchar(255) DEFAULT NULL COMMENT '个人简介',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0:禁用 1:正常',
  `last_login` datetime DEFAULT NULL COMMENT '最后登录时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (1, 'admin', '$2a$10$X/hX5qrs.NPpVqsX9Qx0K.Cl.7nxJ1hxVxm2.vvZuJL1uZozqvz6W', '管理员', 'https://api.dicebear.com/7.x/avataaars/svg?seed=1', 'admin@example.com', NULL, 2, '系统管理员', 1, NULL, '2024-12-05 22:14:44', '2024-12-11 13:07:41');
INSERT INTO `users` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (2, 'john_doe', '$2a$10$X/hX5qrs.NPpVqsX9Qx0K.Cl.7nxJ1hxVxm2.vvZuJL1uZozqvz6W', 'John Doe', 'https://api.dicebear.com/7.x/avataaars/svg?seed=2', 'john@example.com', NULL, 1, '前端开发工程师，Vue.js 爱好者', 1, NULL, '2024-12-05 22:14:44', '2024-12-11 13:07:39');
INSERT INTO `users` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (3, 'alice', '$2a$10$X/hX5qrs.NPpVqsX9Qx0K.Cl.7nxJ1hxVxm2.vvZuJL1uZozqvz6W', 'Alice', 'https://api.dicebear.com/7.x/avataaars/svg?seed=3', 'alice@example.com', NULL, 1, '全栈开发者，热爱技术分享', 1, NULL, '2024-12-05 22:14:44', '2024-12-11 13:07:39');
INSERT INTO `users` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (4, 'bob', '$2a$10$X/hX5qrs.NPpVqsX9Qx0K.Cl.7nxJ1hxVxm2.vvZuJL1uZozqvz6W', 'Bob', 'https://api.dicebear.com/7.x/avataaars/svg?seed=4', 'bob@example.com', NULL, 0, '后端工程师，Java专家', 1, NULL, '2024-12-05 22:14:44', '2024-12-11 13:07:39');
INSERT INTO `users` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (5, 'emma', '$2a$10$X/hX5qrs.NPpVqsX9Qx0K.Cl.7nxJ1hxVxm2.vvZuJL1uZozqvz6W', 'Emma', 'https://api.dicebear.com/7.x/avataaars/svg?seed=5', 'emma@example.com', NULL, 0, 'UI设计师，追求极致体验', 1, NULL, '2024-12-05 22:14:44', '2024-12-11 13:07:39');
INSERT INTO `users` (`id`, `username`, `password`, `nickname`, `avatar`, `email`, `phone`, `gender`, `bio`, `status`, `last_login`, `created_at`, `updated_at`) VALUES (6, 'misan', '123456', 'Mi San', 'U5qlZtozKlqWejwc.png', '854236339@qq.com', NULL, 0, '后端工程师，Java专家123', 1, NULL, '2024-12-06 14:40:18', '2024-12-12 17:10:11');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
