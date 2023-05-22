-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: chensino
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_menu`
--

DROP TABLE IF EXISTS `t_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_menu` (
                          `menu_id` int NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
                          `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                          `permission` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                          `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                          `parent_id` int DEFAULT NULL COMMENT '父菜单ID',
                          `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                          `sort` int DEFAULT '1' COMMENT '排序值',
                          `keep_alive` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0',
                          `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0',
                          PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10455 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_menu`
--

LOCK TABLES `t_menu` WRITE;
/*!40000 ALTER TABLE `t_menu` DISABLE KEYS */;
INSERT INTO `t_menu` VALUES (10451,'用户管理',NULL,'/admin/user/index',-1,NULL,1,'0','0','2022-09-05 16:29:47',NULL,'0'),(10452,'用户查看','user_query','',10451,NULL,1,'0','0','2022-09-05 16:29:47',NULL,'0'),(10453,'用户新增','user_add','',10451,NULL,1,'0','0','2022-09-05 16:29:47',NULL,'0'),(10454,'用户删除','user_del','',10451,NULL,1,'0','0','2022-09-05 16:29:47',NULL,'0');
/*!40000 ALTER TABLE `t_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_operate_log`
--

DROP TABLE IF EXISTS `t_operate_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_operate_log` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                 `type` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '日志类型',
                                 `title` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
                                 `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
                                 `time` bigint DEFAULT NULL,
                                 `create_time` datetime DEFAULT NULL,
                                 `update_time` datetime DEFAULT NULL,
                                 `remote_addr` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'ip',
                                 `user_agent` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户使用的客户端',
                                 `request_uri` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求路径',
                                 `class_name` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL,
                                 `method` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
                                 `params` text COLLATE utf8mb4_bin,
                                 `exception` text COLLATE utf8mb4_bin,
                                 `service_id` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
                                 `del_flag` tinyint DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1659493944012464131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统操作日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_operate_log`
--

LOCK TABLES `t_operate_log` WRITE;
/*!40000 ALTER TABLE `t_operate_log` DISABLE KEYS */;
INSERT INTO `t_operate_log` VALUES (1,'success','测试log','zhangsan',NULL,'2023-05-06 17:39:17','2023-05-06 17:39:22','127.0.0.1','chrome','/usr/local/','xx','xx','xx',NULL,'2',0),(1654790790712840194,NULL,NULL,'anonymousUser',56,NULL,NULL,NULL,NULL,NULL,'com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,NULL),(1654791768799428610,NULL,NULL,'anonymousUser',57,'2023-05-06 18:14:36',NULL,NULL,NULL,NULL,'com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,0),(1654791890224529410,NULL,NULL,'anonymousUser',4757,'2023-05-06 18:15:06',NULL,NULL,NULL,NULL,'com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1654796330222399490,'0','查询文件列表','anonymousUser',8,'2023-05-06 18:32:44',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/minio/files/chensino','com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,0),(1654796376946946049,'0','查询文件列表','anonymousUser',5,'2023-05-06 18:32:56',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/minio/files/chensino','com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,0),(1654796575266222081,'0','查询文件列表','anonymousUser',4,'2023-05-06 18:33:43',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/minio/files/chensino','com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,0),(1654796797899878402,'0','查询文件列表','anonymousUser',4,'2023-05-06 18:34:34',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/minio/files/chensino','com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,0),(1654796965311328258,'0','查询文件列表','anonymousUser',5,'2023-05-06 18:34:50',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/minio/files/chensino','com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,0),(1654797132668276738,'0','查询文件列表','anonymousUser',55,'2023-05-06 18:35:23',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/minio/files/chensino','com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,0),(1654797166394605569,'0','查询文件列表','anonymousUser',53,'2023-05-06 18:36:04',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/minio/files/chensino','com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,0),(1655761393229991937,'0','登录接口','anonymousUser',198,'2023-05-09 10:27:33',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655764411358883841,'9','登录接口','anonymousUser',0,'2023-05-09 10:39:33',NULL,'0:0:0:0:0:0:0:1','Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"string\",\"password\":\"string\",\"phone\":\"string\",\"phoneCode\":\"string\",\"username\":\"string\"}\n','No enum constant com.chensino.core.system.factory.LoginTypeEnum.string',NULL,0),(1655764446498762754,'9','登录接口','anonymousUser',0,'2023-05-09 10:39:41',NULL,'0:0:0:0:0:0:0:1','Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"string\",\"password\":\"string\",\"phone\":\"string\",\"phoneCode\":\"string\",\"username\":\"string\"}\n','No enum constant com.chensino.core.system.factory.LoginTypeEnum.string',NULL,0),(1655764682549997570,'0','登录接口','anonymousUser',174,'2023-05-09 10:40:37',NULL,'0:0:0:0:0:0:0:1','Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655764760022986754,'0','登录接口','anonymousUser',65,'2023-05-09 10:40:56',NULL,'0:0:0:0:0:0:0:1','Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655764787546009602,'0','登录接口','anonymousUser',66,'2023-05-09 10:41:03',NULL,'0:0:0:0:0:0:0:1','Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655764953028079617,'0','查询文件列表','anonymousUser',53,'2023-05-09 10:41:42',NULL,'0:0:0:0:0:0:0:1','Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36','/minio/files/chensino','com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,0),(1655833989287915521,'0','登录接口','anonymousUser',197,'2023-05-09 15:16:01',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655833995281575937,'0','登录接口','anonymousUser',68,'2023-05-09 15:16:03',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655833998192422914,'0','登录接口','anonymousUser',66,'2023-05-09 15:16:04',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655834001199738881,'0','登录接口','anonymousUser',69,'2023-05-09 15:16:04',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655834004496461825,'0','登录接口','anonymousUser',66,'2023-05-09 15:16:05',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655834013723930626,'0','登录接口','anonymousUser',67,'2023-05-09 15:16:07',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655834018773872642,'0','登录接口','anonymousUser',66,'2023-05-09 15:16:09',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655834021370146817,'0','登录接口','anonymousUser',66,'2023-05-09 15:16:09',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1655834023731539970,'0','登录接口','anonymousUser',66,'2023-05-09 15:16:10',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1659136010548047873,'0','登录接口','anonymousUser',220,'2023-05-18 17:57:05',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1659459665287159810,'0','登录接口','anonymousUser',203,'2023-05-19 15:23:10',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/login','com.chensino.core.system.controller.LoginController','login','参数1:{\"loginType\":\"USERNAME\",\"password\":\"123456\",\"username\":\"chensino\"}\n',NULL,NULL,0),(1659493944012464130,'0','查询文件列表','anonymousUser',60,'2023-05-19 17:39:23',NULL,'0:0:0:0:0:0:0:1','PostmanRuntime/7.32.2','/minio/files/chensino','com.chensino.core.system.controller.MinioController','listFilesByBucket','参数1:\"chensino\"\n',NULL,NULL,0);
/*!40000 ALTER TABLE `t_operate_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role` (
                          `role_id` int NOT NULL AUTO_INCREMENT COMMENT '角色id',
                          `role_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色名',
                          `role_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色代码',
                          `role_desc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色描述',
                          `ds_type` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '数据权限类型，全部、本级、本级和子级、自定义',
                          `ds_scope` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '数据权限范围描述',
                          `del_flag` tinyint DEFAULT '0',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
                          `update_time` datetime DEFAULT NULL,
                          PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES (1,'管理员','ADMIN','管理员','0','2',0,'2022-09-05 16:26:14',NULL),(2,'访客','GUEST',' 访客权限','0','2',0,'2022-09-05 17:52:31',NULL);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role_menu`
--

DROP TABLE IF EXISTS `t_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_role_menu` (
                               `role_id` int NOT NULL COMMENT '角色ID',
                               `menu_id` int NOT NULL COMMENT '菜单ID',
                               PRIMARY KEY (`role_id`,`menu_id`) USING BTREE,
                               KEY `t_role_menu__menu_id_fk` (`menu_id`) USING BTREE,
                               CONSTRAINT `t_role_menu__menu_id_fk` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`menu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                               CONSTRAINT `t_role_menu__role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='角色菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role_menu`
--

LOCK TABLES `t_role_menu` WRITE;
/*!40000 ALTER TABLE `t_role_menu` DISABLE KEYS */;
INSERT INTO `t_role_menu` VALUES (1,10451),(1,10452),(1,10453),(1,10454);
/*!40000 ALTER TABLE `t_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
                          `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
                          `username` varchar(16) NOT NULL COMMENT '用户名',
                          `nick_name` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '昵称',
                          `employ_id` varchar(6) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '工号',
                          `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                          `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '手机号',
                          `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '头像地址',
                          `dept_id` int NOT NULL COMMENT '部门id',
                          `email` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '邮箱',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                          `creator_id` bigint NOT NULL COMMENT '创建人id',
                          `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标志，0：未删除，1：删除',
                          `lock_flag` tinyint DEFAULT '0' COMMENT '是否禁用',
                          PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'chensino','chensino','101234','$2a$10$qjl3G9aELnYIyITfiGRraerkDICiDIGvW./faw.vuCBIoQW9c4JSy','17786390126',NULL,3,'462488588@qq.com','2022-09-01 10:01:50',NULL,-1,0,0),(2,'guest','guest','102222','$2a$10$qjl3G9aELnYIyITfiGRraerkDICiDIGvW./faw.vuCBIoQW9c4JSy',NULL,NULL,3,'462488588@qq.com','2022-09-01 10:01:50',NULL,-1,0,0);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_role`
--

DROP TABLE IF EXISTS `t_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_role` (
                               `user_id` bigint NOT NULL COMMENT '用户id',
                               `role_id` int NOT NULL COMMENT '角色id',
                               PRIMARY KEY (`user_id`,`role_id`) USING BTREE,
                               KEY `t_user_role__role_id_fk` (`role_id`) USING BTREE,
                               CONSTRAINT `t_user_role__role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                               CONSTRAINT `t_user_role__user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户-角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_role`
--

LOCK TABLES `t_user_role` WRITE;
/*!40000 ALTER TABLE `t_user_role` DISABLE KEYS */;
INSERT INTO `t_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `t_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_third`
--

DROP TABLE IF EXISTS `t_user_third`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_third` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '本系统对应用户username',
                                `project` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '其它系统名',
                                `username_third` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '第三方系统用户',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='本系统用户和其它系统（包括第三方oauth系统)对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_third`
--

LOCK TABLES `t_user_third` WRITE;
/*!40000 ALTER TABLE `t_user_third` DISABLE KEYS */;
INSERT INTO `t_user_third` VALUES (1,'chensino','github','ChenSino'),(2,'zs','wechat','wechat123');
/*!40000 ALTER TABLE `t_user_third` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-24  9:06:14
