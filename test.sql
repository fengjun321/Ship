/*
Navicat MySQL Data Transfer

Source Server         : docker-win
Source Server Version : 80027
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 80027
File Encoding         : 65001

Date: 2021-12-18 18:42:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for RecordCost
-- ----------------------------
DROP TABLE IF EXISTS `RecordCost`;
CREATE TABLE `RecordCost` (
  `ShipId` int DEFAULT NULL,
  `ShipName` varchar(255) DEFAULT NULL,
  `RoomId` int DEFAULT NULL,
  `UserId` int DEFAULT NULL,
  `UserName` varchar(255) DEFAULT NULL,
  `GoodId` int DEFAULT NULL,
  `GoodName` varchar(255) DEFAULT NULL,
  `GoodPrice` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of RecordCost
-- ----------------------------
INSERT INTO `RecordCost` VALUES ('1101', '泰坦立刻', '-1', '7', 'xiaodan', '1', '风筒', '43');
INSERT INTO `RecordCost` VALUES ('1101', '泰坦立刻', '-1', '7', 'xiaodan', '2', '牙刷', '34');
INSERT INTO `RecordCost` VALUES ('1101', '泰坦立刻', '-1', '7', 'xiaodan', '2', '牙刷', '34');

-- ----------------------------
-- Table structure for RoomManage
-- ----------------------------
DROP TABLE IF EXISTS `RoomManage`;
CREATE TABLE `RoomManage` (
  `ShipId` int NOT NULL,
  `RoomRange` int DEFAULT NULL,
  `Reserve` varchar(255) DEFAULT NULL,
  `PriceLev` varchar(255) DEFAULT NULL,
  `CashPledge` int DEFAULT NULL,
  PRIMARY KEY (`ShipId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of RoomManage
-- ----------------------------
INSERT INTO `RoomManage` VALUES ('1101', '30', '3,4,23,24,1,2,25,30,29,28,18,19,7,27', '20,30,40', '100');

-- ----------------------------
-- Table structure for Ship
-- ----------------------------
DROP TABLE IF EXISTS `Ship`;
CREATE TABLE `Ship` (
  `Id` int NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Price` int DEFAULT NULL,
  `Discout` int DEFAULT NULL,
  `Date` datetime DEFAULT NULL,
  `Des` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of Ship
-- ----------------------------
INSERT INTO `Ship` VALUES ('1101', '泰坦立刻', '300', '5', '2021-12-09 17:59:29', '北京-上海');
INSERT INTO `Ship` VALUES ('1201', '马可勃咯', '400', '0', '2021-12-29 17:59:34', '中国-美国');
INSERT INTO `Ship` VALUES ('1202', '猎人', '200', '6', '2022-01-14 17:59:37', '广州-杭州');

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `income` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('1', 'xiaoming', '3344');
INSERT INTO `staff` VALUES ('2', 'zhu', '444');

-- ----------------------------
-- Table structure for User
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `UserId` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Authority` int DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of User
-- ----------------------------
INSERT INTO `User` VALUES ('6', 'huoying', '2', 'dd');
INSERT INTO `User` VALUES ('7', 'xiaodan', '2', 'dd');
INSERT INTO `User` VALUES ('8', 'xiaodaen', '2', '4545');
INSERT INTO `User` VALUES ('9', 'longen', '4545', '2');
INSERT INTO `User` VALUES ('10', 'chenlong', '4545', '2');
INSERT INTO `User` VALUES ('11', 'chenlon33g', '4545', '2');
INSERT INTO `User` VALUES ('12', 'xxx', '4545', '2');
INSERT INTO `User` VALUES ('13', 'chunqi', '4545', '2');
INSERT INTO `User` VALUES ('14', 'huonan', '2', '4545');
INSERT INTO `User` VALUES ('15', 'zhutgu', '2', '4545');
INSERT INTO `User` VALUES ('16', 'shiluodi', '2', 'ttt');

-- ----------------------------
-- Table structure for UserTicket
-- ----------------------------
DROP TABLE IF EXISTS `UserTicket`;
CREATE TABLE `UserTicket` (
  `UserId` int DEFAULT NULL,
  `ShipId` int DEFAULT NULL,
  `RoomId` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of UserTicket
-- ----------------------------
INSERT INTO `UserTicket` VALUES ('1', '2', '8');
INSERT INTO `UserTicket` VALUES ('6', '1101', '24');
INSERT INTO `UserTicket` VALUES ('7', '1101', '1');
INSERT INTO `UserTicket` VALUES ('7', '1101', '25');
INSERT INTO `UserTicket` VALUES ('7', '1101', '30');
INSERT INTO `UserTicket` VALUES ('7', '1101', '29');
INSERT INTO `UserTicket` VALUES ('7', '1101', '28');
INSERT INTO `UserTicket` VALUES ('6', '1101', '18');
INSERT INTO `UserTicket` VALUES ('6', '1101', '19');
INSERT INTO `UserTicket` VALUES ('6', '1101', '7');
INSERT INTO `UserTicket` VALUES ('6', '1101', '27');

-- ----------------------------
-- Procedure structure for Register
-- ----------------------------
DROP PROCEDURE IF EXISTS `Register`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `Register`(in in_name varchar(50), in in_authority int, in_password varchar(50))
BEGIN
	#Routine body goes here...
	declare cnt int default 0;
	declare error_code int default 0;    
  declare continue handler for sqlexception set error_code = -1;    
	

  start transaction;    
	
	select count(1) into cnt from User where name = in_name; 
	if cnt = 0 then
		insert into User (Name, Authority, Password) values (in_name, in_authority, in_password);
	else
		set error_code = -2;
	end if;

	if error_code = -1 then 
		rollback;
	else
		commit;
	end if;

	if error_code < 0 then
		select error_code, -1 as UserId, "ername" as Name, -1 as Authority, "err" as Password; 
	else
		select error_code, UserId, Name, Authority, Password from User where name = in_name;
  end if;
END
;;
DELIMITER ;
