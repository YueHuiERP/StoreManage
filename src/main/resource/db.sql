/*
 Navicat Premium Data Transfer

 Source Server         : 靠谱数据库
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 47.101.181.138:3306
 Source Schema         : storemanage

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 01/11/2019 13:03:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `type` int(9) NULL DEFAULT NULL,
  `specsUnit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `specsValue` decimal(9, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('00b72ee07a7b471ca2ef22a5e36a5b6c', '../../upload/images/eb3990a7-7d4e-4fd9-8d75-35601806a009.jpg', '2', '芒果', 0, '斤', 2.00);
INSERT INTO `goods` VALUES ('4de2b2428dc9469989b9446a64db2ae9', '../../upload/images/dbef3dd5-0000-4bad-85c6-7c6a593005b8.jpg', '1', '纸箱', 1, '斤', 1.00);
INSERT INTO `goods` VALUES ('5377d847cd1b4a3eb7de8fb3883b5504', '../../upload/images/16a9f059-3456-40d7-8543-823e8f120050.jpg', '1', '纸箱', 1, '斤', 1.00);
INSERT INTO `goods` VALUES ('c837bffb79de4c498979c837e0d7b50a', '../../upload/images/53562ca6-1d38-4588-8e99-48cc9e078fb4.jpg', '10', '纸箱1', 1, '斤', 10.00);
INSERT INTO `goods` VALUES ('f1534d6dcecb443e8ec8b693acebec21', '../../upload/images/867f08c0-c078-43cb-ad2f-0ee4cf2872a9.jpg', '1', '玉芒', 0, '斤', 1.00);

-- ----------------------------
-- Table structure for manifest
-- ----------------------------
DROP TABLE IF EXISTS `manifest`;
CREATE TABLE `manifest`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `pid` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `amount` int(9) NULL DEFAULT NULL,
  `status` int(1) NULL DEFAULT NULL,
  `parentId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `gid` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `visible` bit(1) NULL DEFAULT b'1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of manifest
-- ----------------------------
INSERT INTO `manifest` VALUES ('119d869cc7a04a358a46ed21cbd26d13', '芒果', 'd804969dfafd4f568d6a6f7233b4761e', 11, 1, 'a84c7bd704654e6dac2f864fca11278b', '00b72ee07a7b471ca2ef22a5e36a5b6c', '2019-10-28 13:53:06', b'1');
INSERT INTO `manifest` VALUES ('2267bf775bf84bff902d367b7869e4d8', 'bug果', NULL, 10, 0, NULL, '00b72ee07a7b471ca2ef22a5e36a5b6c', '2019-10-18 21:14:59', b'1');
INSERT INTO `manifest` VALUES ('3590f69571d140619524d0c6620e6773', '百香果30枚（本地）', NULL, 10, 0, NULL, '880360cb130d4ea3bdd8b27d660a9bc0', '2019-10-14 11:05:00', b'1');
INSERT INTO `manifest` VALUES ('40c040fee7de4e32b885ddf787f1b553', '芒果', NULL, 10, 0, NULL, '00b72ee07a7b471ca2ef22a5e36a5b6c', '2019-10-28 13:50:31', b'1');
INSERT INTO `manifest` VALUES ('51c3deb21d2a491b85bd166f4d5424c4', 'bug果', NULL, 11, 0, NULL, '00b72ee07a7b471ca2ef22a5e36a5b6c', '2019-10-18 15:26:33', b'1');
INSERT INTO `manifest` VALUES ('6731438d966d4eed896463ebf8ab420d', 'bug果', '5d0bdef2b8e04f9e9c969d3af742c6a0', 11, 1, '96d0f33507d54398b61f55e1a7d1b9ff', '00b72ee07a7b471ca2ef22a5e36a5b6c', '2019-10-28 13:50:50', b'1');
INSERT INTO `manifest` VALUES ('96d0f33507d54398b61f55e1a7d1b9ff', 'bug果', NULL, 11, 0, NULL, '00b72ee07a7b471ca2ef22a5e36a5b6c', '2019-10-18 15:26:33', b'1');
INSERT INTO `manifest` VALUES ('a10ce54c03684073a9614e907a8a94be', 'bug果', '5d0bdef2b8e04f9e9c969d3af742c6a0', 10, 1, '2267bf775bf84bff902d367b7869e4d8', '00b72ee07a7b471ca2ef22a5e36a5b6c', '2019-10-28 13:50:50', b'1');
INSERT INTO `manifest` VALUES ('a84c7bd704654e6dac2f864fca11278b', '芒果', NULL, 11, 0, NULL, '00b72ee07a7b471ca2ef22a5e36a5b6c', '2019-10-28 13:52:36', b'1');
INSERT INTO `manifest` VALUES ('a906870ef624463da892834b59d88963', '百香果30枚（本地）', NULL, 11, 0, NULL, '880360cb130d4ea3bdd8b27d660a9bc0', '2019-10-08 00:00:00', b'1');
INSERT INTO `manifest` VALUES ('dfa34892d1f3499e99fdb5821d9324a1', '百香果30枚（本地）', NULL, 10, 0, NULL, '880360cb130d4ea3bdd8b27d660a9bc0', '2019-10-14 11:06:30', b'1');
INSERT INTO `manifest` VALUES ('e44b053ed4a741189bee4027da178dfb', 'bug果', '5d0bdef2b8e04f9e9c969d3af742c6a0', 11, 1, '51c3deb21d2a491b85bd166f4d5424c4', '00b72ee07a7b471ca2ef22a5e36a5b6c', '2019-10-28 13:50:50', b'1');
INSERT INTO `manifest` VALUES ('e84d8be77412450892f0ac980cdcb9c3', '芒果', '5d0bdef2b8e04f9e9c969d3af742c6a0', 10, 1, '40c040fee7de4e32b885ddf787f1b553', '00b72ee07a7b471ca2ef22a5e36a5b6c', '2019-10-28 13:50:50', b'1');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `serial` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `pId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `amount` int(9) NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `status` int(3) NULL DEFAULT NULL,
  `parentId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `sId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('57f3408870334525b720abc71eac726f', '百香果30枚（本地）', NULL, NULL, 0, '2019-09-06 18:15:43', 1, 'da532eaf00af4b7b997920bf8d46c05a', NULL);
INSERT INTO `order` VALUES ('8180512c8e1d42478fe9679c6c9d1c2e', NULL, NULL, '7dc2e9c7e1eb49809d9c31454d229b3f', 3, '2019-09-07 11:11:57', 0, 'aa2aafa93e2a49c88d1a9a3fa61ef16d', NULL);
INSERT INTO `order` VALUES ('8977509300c04739b1878296c09ac613', '百香果30枚（本地）', NULL, NULL, 0, '2019-09-06 23:48:32', 1, 'da532eaf00af4b7b997920bf8d46c05a', NULL);
INSERT INTO `order` VALUES ('aa2aafa93e2a49c88d1a9a3fa61ef16d', '百香果30枚（本地）', NULL, NULL, 0, '2019-09-07 11:11:57', 1, 'da532eaf00af4b7b997920bf8d46c05a', NULL);
INSERT INTO `order` VALUES ('b661f75b39de4309861a04509a0c3651', NULL, NULL, '15f1728221e04f7a85634d1dec27b1c1', 1, '2019-09-06 23:48:32', 0, '8977509300c04739b1878296c09ac613', NULL);
INSERT INTO `order` VALUES ('da532eaf00af4b7b997920bf8d46c05a', '百香果30枚（本地） ', NULL, NULL, 0, '2019-09-06 18:15:38', 0, NULL, NULL);
INSERT INTO `order` VALUES ('fffc739016f046cc98e1f432fcc3b4a5', NULL, NULL, '15f1728221e04f7a85634d1dec27b1c1', 1, '2019-09-06 18:15:43', 0, '57f3408870334525b720abc71eac726f', NULL);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `specs` decimal(9, 2) NULL DEFAULT NULL,
  `packFee` decimal(9, 2) NULL DEFAULT NULL,
  `amount` int(9) NULL DEFAULT NULL,
  `gid` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `creatorId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `updaterId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('2201582e97d946b3988bc9d35264add8', '芒果', '2019-10-28 16:58:21', NULL, 10.00, 10, NULL, NULL, NULL);
INSERT INTO `product` VALUES ('5d0bdef2b8e04f9e9c969d3af742c6a0', '芒果', '2019-10-28 12:30:18', NULL, 10.00, 10, NULL, NULL, NULL);
INSERT INTO `product` VALUES ('c4882b17bd39438c805314719320d404', '芒果', '2019-10-28 17:40:36', NULL, 10.00, 10, NULL, NULL, NULL);
INSERT INTO `product` VALUES ('ce3ce59eefe74d4f9777232d849c6988', '芒果', '2019-10-28 17:40:35', NULL, 10.00, 10, NULL, NULL, NULL);
INSERT INTO `product` VALUES ('d804969dfafd4f568d6a6f7233b4761e', '芒果', '2019-10-28 09:48:14', NULL, 10.00, 10, NULL, NULL, NULL);
INSERT INTO `product` VALUES ('d9a49399564a4484ad979669ecc20673', '芒果', '2019-10-28 09:48:01', NULL, 10.00, 10, NULL, NULL, NULL);
INSERT INTO `product` VALUES ('e337c7c1bc1e4a889df11cee7dd67c57', '芒果', '2019-10-28 17:40:33', NULL, 10.00, 10, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for productcops
-- ----------------------------
DROP TABLE IF EXISTS `productcops`;
CREATE TABLE `productcops`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `sId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `amount` int(9) NULL DEFAULT NULL,
  `pId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `batch` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `pAmount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `specs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `specsUnit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `specsValue` decimal(9, 2) NULL DEFAULT NULL,
  `gId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `oId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `creatorId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `updaterId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of productcops
-- ----------------------------
INSERT INTO `productcops` VALUES ('434ebf94880f4d099c8412f59e7c64db', '1188741694968311810', 1, 'e337c7c1bc1e4a889df11cee7dd67c57', 'YM20191028004', '10', '1', '斤', 1.00, 'f1534d6dcecb443e8ec8b693acebec21', NULL, NULL, NULL);
INSERT INTO `productcops` VALUES ('49548be44b3e4b4c81078b9a90e35156', '1188630119924240385', 1, 'd9a49399564a4484ad979669ecc20673', 'MG20191028001', '10', '2', '斤', 2.00, '00b72ee07a7b471ca2ef22a5e36a5b6c', NULL, NULL, NULL);
INSERT INTO `productcops` VALUES ('4cb2f6b7204442859f0e789c2606426b', '1188741694968311810', 1, 'c4882b17bd39438c805314719320d404', 'YM20191028004', '10', '1', '斤', 1.00, 'f1534d6dcecb443e8ec8b693acebec21', NULL, NULL, NULL);
INSERT INTO `productcops` VALUES ('700a3c63aa324e1f822eae1d57297628', '1188741694968311810', 1, '2201582e97d946b3988bc9d35264add8', 'YM20191028004', '10', '1', '斤', 1.00, 'f1534d6dcecb443e8ec8b693acebec21', NULL, NULL, NULL);
INSERT INTO `productcops` VALUES ('a114703c4c204477b70e9a18e6e5cf0e', '1188630119924240385', 1, 'd804969dfafd4f568d6a6f7233b4761e', 'MG20191028001', '10', '2', '斤', 2.00, '00b72ee07a7b471ca2ef22a5e36a5b6c', NULL, NULL, NULL);
INSERT INTO `productcops` VALUES ('afa40246256d4b5cac28cf377fc9ec0c', '1188674138035802114', 1, '5d0bdef2b8e04f9e9c969d3af742c6a0', 'ZX20191028003', '10', '1', '斤', 1.00, '5377d847cd1b4a3eb7de8fb3883b5504', NULL, NULL, NULL);
INSERT INTO `productcops` VALUES ('afc881a506a945aab77b6c02b4ac3e4b', NULL, 1, NULL, NULL, NULL, '4.3', '斤', 4.30, '880360cb130d4ea3bdd8b27d660a9bc0', 'da532eaf00af4b7b997920bf8d46c05a', NULL, NULL);
INSERT INTO `productcops` VALUES ('c735fa86a72b47a3b36ca6ea03661390', '1188673250252640257', 1, '5d0bdef2b8e04f9e9c969d3af742c6a0', 'MG20191028002', '10', '2', '斤', 2.00, '00b72ee07a7b471ca2ef22a5e36a5b6c', NULL, NULL, NULL);
INSERT INTO `productcops` VALUES ('d9da751321d34522af42f1786df9a9f3', '1188674138035802114', 1, '2201582e97d946b3988bc9d35264add8', 'ZX20191028003', '10', '1', '斤', 1.00, '5377d847cd1b4a3eb7de8fb3883b5504', NULL, NULL, NULL);
INSERT INTO `productcops` VALUES ('f0f1a165cbb54aaab53ae7749b37d9b2', '1188741694968311810', 1, 'ce3ce59eefe74d4f9777232d849c6988', 'YM20191028004', '10', '1', '斤', 1.00, 'f1534d6dcecb443e8ec8b693acebec21', NULL, NULL, NULL);
INSERT INTO `productcops` VALUES ('fae0a8cfd5fd4125a3f6bfc80d620063', NULL, 1, NULL, NULL, NULL, '1', '斤', 1.00, '217aa7e77b894d64a96b854b0bd3e687', 'da532eaf00af4b7b997920bf8d46c05a', NULL, NULL);

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `gId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `amount` int(9) NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `batch` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `provider` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `createTime` datetime(3) NULL DEFAULT NULL,
  `offsetAmount` decimal(10, 2) NULL DEFAULT NULL,
  `specsValue` decimal(10, 2) NULL DEFAULT NULL,
  `supplierId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `creatorId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `parentId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `updaterId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES ('1188630119924240385', '芒果', '00b72ee07a7b471ca2ef22a5e36a5b6c', 20, 11.00, 'MG20191028001', NULL, NULL, '2019-10-28 09:34:38.502', 0.00, 2.00, NULL, 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', NULL, NULL);
INSERT INTO `stock` VALUES ('1188630123850108930', '芒果', '00b72ee07a7b471ca2ef22a5e36a5b6c', 10, 11.00, 'MG20191028001', '测试供应商', '11', '2019-10-28 09:34:39.764', 0.00, 2.00, '3072b689cd554f679c7e0f920b57f489', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', '1188630119924240385', NULL);
INSERT INTO `stock` VALUES ('1188630123858497538', '芒果', '00b72ee07a7b471ca2ef22a5e36a5b6c', 10, 11.00, 'MG20191028001', '供应商test', '11', '2019-10-28 09:34:39.765', 0.00, 2.00, '3410c67026a748c59f1555d72ef6e49b', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', '1188630119924240385', NULL);
INSERT INTO `stock` VALUES ('1188673250252640257', '芒果', '00b72ee07a7b471ca2ef22a5e36a5b6c', 20, 100.00, 'MG20191028002', NULL, NULL, '2019-10-28 12:26:01.935', 0.00, 2.00, NULL, 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', NULL, NULL);
INSERT INTO `stock` VALUES ('1188673250416218114', '芒果', '00b72ee07a7b471ca2ef22a5e36a5b6c', 10, 100.00, 'MG20191028002', '供应商test', '', '2019-10-28 12:26:02.014', 0.00, 2.00, '3410c67026a748c59f1555d72ef6e49b', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', '1188673250252640257', NULL);
INSERT INTO `stock` VALUES ('1188673250424606721', '芒果', '00b72ee07a7b471ca2ef22a5e36a5b6c', 10, 100.00, 'MG20191028002', '测试供应商', '', '2019-10-28 12:26:02.015', 0.00, 2.00, '3072b689cd554f679c7e0f920b57f489', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', '1188673250252640257', NULL);
INSERT INTO `stock` VALUES ('1188674138035802114', '纸箱', '5377d847cd1b4a3eb7de8fb3883b5504', 100, 1.00, 'ZX20191028003', NULL, NULL, '2019-10-28 12:29:33.662', 0.00, 1.00, NULL, 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', NULL, NULL);
INSERT INTO `stock` VALUES ('1188674138136465410', '纸箱', '5377d847cd1b4a3eb7de8fb3883b5504', 100, 1.00, 'ZX20191028003', '供应商test', '', '2019-10-28 12:29:33.675', 0.00, 1.00, '3410c67026a748c59f1555d72ef6e49b', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', '1188674138035802114', NULL);
INSERT INTO `stock` VALUES ('1188741694968311810', '玉芒', 'f1534d6dcecb443e8ec8b693acebec21', 186, 1.00, 'YM20191028004', NULL, NULL, '2019-10-28 16:58:00.128', 0.00, 1.00, NULL, 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', NULL, NULL);
INSERT INTO `stock` VALUES ('1188741695542931458', '玉芒', 'f1534d6dcecb443e8ec8b693acebec21', 99, 1.00, 'YM20191028004', '供应商test', '11', '2019-10-28 16:58:00.547', 0.00, 1.00, '3410c67026a748c59f1555d72ef6e49b', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', '1188741694968311810', NULL);
INSERT INTO `stock` VALUES ('1188741695551320066', '玉芒', 'f1534d6dcecb443e8ec8b693acebec21', 55, 1.00, 'YM20191028004', '测试供应商', '11', '2019-10-28 16:58:00.548', 0.00, 1.00, '3072b689cd554f679c7e0f920b57f489', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', '1188741694968311810', NULL);
INSERT INTO `stock` VALUES ('1188741695559708674', '玉芒', 'f1534d6dcecb443e8ec8b693acebec21', 32, 1.00, 'YM20191028004', '供应商test', '11', '2019-10-28 16:58:00.549', 0.00, 1.00, '3410c67026a748c59f1555d72ef6e49b', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', '1188741694968311810', NULL);
INSERT INTO `stock` VALUES ('1188789233100259329', '芒果', '00b72ee07a7b471ca2ef22a5e36a5b6c', 22, 11.00, 'MG20191028005', '供应商test,供应商test', '11', '2019-10-28 20:06:53.874', -10.00, 2.00, '', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', NULL, 'd7f04aa8-3ca5-436a-8463-73e14f2228dd');
INSERT INTO `stock` VALUES ('1188789233309974529', '芒果', '00b72ee07a7b471ca2ef22a5e36a5b6c', 11, 11.00, 'MG20191028005', '供应商test', '11', '2019-10-28 20:06:54.485', 0.00, 2.00, '3410c67026a748c59f1555d72ef6e49b', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', '1188789233100259329', NULL);
INSERT INTO `stock` VALUES ('1188789233330946050', '芒果', '00b72ee07a7b471ca2ef22a5e36a5b6c', 11, 11.00, 'MG20191028005', '供应商test', '11', '2019-10-28 20:06:54.486', 0.00, 2.00, '3410c67026a748c59f1555d72ef6e49b', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd', '1188789233100259329', NULL);

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES ('287f1f51912e4a83b9a4a4510159dc50', '供应商', NULL, NULL);
INSERT INTO `supplier` VALUES ('3072b689cd554f679c7e0f920b57f489', '测试供应商', '测试供应商', '2019-10-08 10:55:33');
INSERT INTO `supplier` VALUES ('3410c67026a748c59f1555d72ef6e49b', '供应商test', 'kahdfk', '2019-10-08 11:31:17');
INSERT INTO `supplier` VALUES ('3480f6b5846c41038364ed5564a1203d', 'fdsf', NULL, NULL);
INSERT INTO `supplier` VALUES ('99eacc099c18478e96ef0d654c4c6832', '测试供应商', NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `role` int(3) NULL DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('65b31fa72f544b858d3829d074b1631c', 'T', '谭凯艺', 'tky...93', 0, NULL, '2019-08-22 21:27:42');
INSERT INTO `user` VALUES ('844b7d98ad174562bf1d80a38e92822b', '邓恩荣', '邓恩荣', 'dengenrong', 0, NULL, '2019-09-05 20:59:54');
INSERT INTO `user` VALUES ('b6068c06b5be4d2ab251f5521d62e70d', '123456', '测试用户', '123456', 0, NULL, '2019-08-22 22:13:03');
INSERT INTO `user` VALUES ('d7f04aa8-3ca5-436a-8463-73e14f2228dd', 'admin', '管理员2', 'admin123', 2, 'null', '2019-07-15 15:29:25');

-- ----------------------------
-- Table structure for waste
-- ----------------------------
DROP TABLE IF EXISTS `waste`;
CREATE TABLE `waste`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `sId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `weight` double(9, 2) NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `creatorId` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of waste
-- ----------------------------
INSERT INTO `waste` VALUES ('06037e567b014cab9ab378e1f6bfdbe1', '1188674138035802114', 30.00, '2019-10-28 12:35:00', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd');
INSERT INTO `waste` VALUES ('1a7a42b062734114b6c4502d15ead5d4', '1188674138035802114', 10.00, '2019-10-28 12:31:05', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd');
INSERT INTO `waste` VALUES ('5a27cf3653c94345ae819bfdca8658ec', 'bc76fec02dfb4d2d9b8bba8c3cf1ce36', 1.00, '2019-10-27 14:30:00', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd');
INSERT INTO `waste` VALUES ('c0fdca7daaff4bee89813e158c95b1fa', '689544edf2fb40faafdefed925fd8fa1', 100.00, '2019-10-08 16:25:23', 'd7f04aa8-3ca5-436a-8463-73e14f2228dd');

SET FOREIGN_KEY_CHECKS = 1;
