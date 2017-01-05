-- --------------------------------------------------------
-- Värd:                         46.101.184.184
-- Server version:               5.5.53-0ubuntu0.14.04.1 - (Ubuntu)
-- Server OS:                    debian-linux-gnu
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for testdb
CREATE DATABASE IF NOT EXISTS avatar_webchat /*!40100 DEFAULT CHARACTER SET latin1 */;
GRANT ALL ON avatar_webchat.* to 'avatar_admin' identified by 'avatarTeam!2';
USE avatar_webchat;

-- Dumping structure for tabell testdb.chat_blacklist
CREATE TABLE IF NOT EXISTS `chat_blacklist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL DEFAULT '0',
  `time_of_ban` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK__chat_user_banned` (`userId`),
  CONSTRAINT `FK__chat_user_banned` FOREIGN KEY (`userId`) REFERENCES `chat_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell testdb.chat_blacklist: ~0 rows (approximately)
/*!40000 ALTER TABLE `chat_blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_blacklist` ENABLE KEYS */;

-- Dumping structure for tabell testdb.chat_line
CREATE TABLE IF NOT EXISTS `chat_line` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `line_text` text,
  `posted_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `chat_room_id` int(11) NOT NULL,
  `isFile` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_chat_line_chat_user` (`user_id`),
  KEY `FK_chat_line_chat_room` (`chat_room_id`),
  CONSTRAINT `FK_chat_line_chat_room` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`id`),
  CONSTRAINT `FK_chat_line_chat_user` FOREIGN KEY (`user_id`) REFERENCES `chat_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=708 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell testdb.chat_line: ~0 rows (approximately)
/*!40000 ALTER TABLE `chat_line` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_line` ENABLE KEYS */;

-- Dumping structure for tabell testdb.chat_reports
CREATE TABLE IF NOT EXISTS `chat_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `senderId` int(11) NOT NULL,
  `reportedUserId` int(11) NOT NULL,
  `reason` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK__chat_user_reported` (`reportedUserId`),
  KEY `FK_chat_user_sent_report` (`senderId`),
  CONSTRAINT `FK_chat_user_sent_report` FOREIGN KEY (`senderId`) REFERENCES `chat_user` (`id`),
  CONSTRAINT `FK__chat_user_reported` FOREIGN KEY (`reportedUserId`) REFERENCES `chat_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell testdb.chat_reports: ~0 rows (approximately)
/*!40000 ALTER TABLE `chat_reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_reports` ENABLE KEYS */;

-- Dumping structure for tabell testdb.chat_room
CREATE TABLE IF NOT EXISTS `chat_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chat_room_name` varchar(50) NOT NULL,
  `isGroup` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell testdb.chat_room: ~0 rows (approximately)
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;

-- Dumping structure for tabell testdb.chat_room_members
CREATE TABLE IF NOT EXISTS `chat_room_members` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chat_room_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK__chat_room_rel` (`chat_room_id`),
  KEY `FK__chat_user_rel` (`user_id`),
  CONSTRAINT `FK__chat_room_rel` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`id`),
  CONSTRAINT `FK__chat_user_rel` FOREIGN KEY (`user_id`) REFERENCES `chat_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=239 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell testdb.chat_room_members: ~0 rows (approximately)
/*!40000 ALTER TABLE `chat_room_members` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_room_members` ENABLE KEYS */;

-- Dumping structure for tabell testdb.chat_user
CREATE TABLE IF NOT EXISTS `chat_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(45) NOT NULL,
  `salt` blob NOT NULL,
  `info_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FK__chat_user_info` (`info_id`),
  CONSTRAINT `FK__chat_user_info` FOREIGN KEY (`info_id`) REFERENCES `chat_user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell testdb.chat_user: ~1 rows (approximately)
/*!40000 ALTER TABLE `chat_user` DISABLE KEYS */;
INSERT INTO `chat_user` (`id`, `username`, `password`, `salt`, `info_id`) VALUES
	(75, 'superuseradmin', 'UP4nrMbcvvc=', _binary 0x7F9DBF551D8DFE69495C56F67A2A01EA12C8DE5451F6216081971E9431880B91, 1);
/*!40000 ALTER TABLE `chat_user` ENABLE KEYS */;

-- Dumping structure for tabell testdb.chat_user_info
CREATE TABLE IF NOT EXISTS `chat_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `isAdmin` int(11) NOT NULL DEFAULT '0',
  `created` datetime DEFAULT NULL,
  `isSubscriber` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell testdb.chat_user_info: ~1 rows (approximately)
/*!40000 ALTER TABLE `chat_user_info` DISABLE KEYS */;
INSERT INTO `chat_user_info` (`id`, `firstname`, `lastname`, `email`, `isAdmin`, `created`, `isSubscriber`) VALUES
	(1, 'default', 'default', 'default@mail.com', 1, '2017-01-03 13:28:51', 1);
/*!40000 ALTER TABLE `chat_user_info` ENABLE KEYS */;

-- Dumping structure for tabell testdb.friend
CREATE TABLE IF NOT EXISTS `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id1` int(11) NOT NULL DEFAULT '0',
  `id2` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_friend_bound_1` (`id1`),
  KEY `FK_friend_bound_2` (`id2`),
  CONSTRAINT `FK_friend_bound_2` FOREIGN KEY (`id2`) REFERENCES `chat_user` (`id`),
  CONSTRAINT `FK_friend_bound_1` FOREIGN KEY (`id1`) REFERENCES `chat_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell testdb.friend: ~0 rows (approximately)
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;

-- Dumping structure for tabell testdb.friend_requests
CREATE TABLE IF NOT EXISTS `friend_requests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender` int(11) NOT NULL,
  `reciever` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK__chat_user` (`sender`),
  KEY `FK__chat_user_2` (`reciever`),
  CONSTRAINT `FK__chat_user` FOREIGN KEY (`sender`) REFERENCES `chat_user` (`id`),
  CONSTRAINT `FK__chat_user_2` FOREIGN KEY (`reciever`) REFERENCES `chat_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=latin1;

-- Dumpar data för tabell testdb.friend_requests: ~0 rows (approximately)
/*!40000 ALTER TABLE `friend_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend_requests` ENABLE KEYS */;

-- Dumping structure for procedure testdb.proc_add_message_to_room
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_add_message_to_room`(
	IN `lineText` TEXT,
	IN `userId` INT,
	IN `chatRoomId` INT


,
	IN `isFile` INT
)
BEGIN
SET FOREIGN_KEY_CHECKS = 0;
INSERT INTO chat_line(line_text, user_id, chat_room_id, isFile)
VALUES(`lineText`, `userId`,`chatRoomId`,`isFile`);
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_check_if_group_room
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_check_if_group_room`(
	IN `chatRoomId` INT
)
BEGIN
SELECT chat_room.isGroup
FROM chat_room
WHERE chat_room.id = `chatRoomId`;
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_clear_chat_history
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_clear_chat_history`(
	IN `chatRoomId` INT
)
BEGIN
DELETE FROM chat_line
WHERE chat_line.chat_room_id = `chatRoomId`;
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_create_account
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_create_account`(
	IN `fName` VARCHAR(30),
	IN `lName` VARCHAR(30),
	IN `eMail` VARCHAR(50),
	IN `isAdminParam` INT,
	IN `userName` VARCHAR(25),
	IN `passWord` VARCHAR(45),
	IN `_salt` BLOB


)
    DETERMINISTIC
    COMMENT 'Creates new user account, adds information to chat_user_info and chat_user tables.'
BEGIN
	INSERT INTO chat_user_info(firstname, lastname, email, isAdmin, created)
	VALUES(`fName`, `lName`, `eMail`, `isAdminParam`,  NOW());
	INSERT INTO chat_user(username, chat_user.password, salt, info_id)
	VALUES(`userName`, `passWord`, `_salt`, LAST_INSERT_ID());
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_create_group_room
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_create_group_room`(
	IN `chatRoomName` VARCHAR(50),
	IN `newUserId` INT,
	IN `oldChatRoomId` INT


)
BEGIN
INSERT INTO chat_room(chat_room_name, isGroup)
VALUES(`chatRoomName`, 1);
SET @newRoom = LAST_INSERT_ID();
INSERT INTO chat_room_members(chat_room_id, user_id)
VALUES(@newRoom, `newUserId`);
INSERT INTO chat_room_members(chat_room_id, user_id)
	SELECT @newRoom, chat_room_members.user_id
		FROM chat_room_members
			WHERE chat_room_members.chat_room_id = `oldChatRoomId`;
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_create_new_group
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_create_new_group`(
	IN `chatRoomName` VARCHAR(50),
	IN `userId` INT


,
	OUT `newRoomId` INT
)
BEGIN
INSERT INTO chat_room(chat_room_name, isGroup)
VALUES(`chatRoomName`, 1);
SET @newRoom = LAST_INSERT_ID();
INSERT INTO chat_room_members(chat_room_id, user_id)
VALUES(@newRoom, `userId`);
SELECT id INTO `newRoomId` FROM chat_room
WHERE chat_room.id = @newRoom;
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_create_private_chat
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_create_private_chat`(
	IN `chatRoomName` VARCHAR(50),
	IN `userId1` INT,
	IN `userId2` INT
)
BEGIN
INSERT INTO chat_room(chat_room_name, isGroup)
VALUES(`chatRoomName`, 0);
INSERT INTO chat_room_members(chat_room_id, user_id)
VALUES
	(LAST_INSERT_ID(), `userId1`),
	(LAST_INSERT_ID(), `userId2`);
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_delete_account
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_delete_account`(
	IN `userID` INT

)
    COMMENT 'Completely wipes DB of User from provided ID'
BEGIN
SET FOREIGN_KEY_CHECKS = 0;
	DELETE avatar_webchat.chat_room_members, avatar_webchat.chat_line, avatar_webchat.friend, avatar_webchat.friend_requests, avatar_webchat.chat_user, avatar_webchat.chat_user_info, avatar_webchat.chat_room
	FROM avatar_webchat.chat_room_members 
	LEFT OUTER JOIN avatar_webchat.chat_line ON avatar_webchat.chat_line.user_id = avatar_webchat.chat_room_members.user_id
	LEFT OUTER JOIN avatar_webchat.friend ON (avatar_webchat.friend.id1 = avatar_webchat.chat_room_members.user_id) OR (avatar_webchat.friend.id2 = avatar_webchat.chat_room_members.user_id)
	LEFT OUTER JOIN avatar_webchat.friend_requests ON (avatar_webchat.friend_requests.sender = avatar_webchat.chat_room_members.user_id) OR (avatar_webchat.friend_requests.reciever = avatar_webchat.chat_room_members.user_id)
	LEFT OUTER JOIN avatar_webchat.chat_user ON avatar_webchat.chat_user.id = avatar_webchat.chat_room_members.user_id
	LEFT OUTER JOIN avatar_webchat.chat_user_info ON avatar_webchat.chat_user.info_id = avatar_webchat.chat_user_info.id
	LEFT OUTER JOIN avatar_webchat.chat_room ON avatar_webchat.chat_room.id = avatar_webchat.chat_room_members.chat_room_id
	WHERE avatar_webchat.chat_room_members.user_id = userID AND avatar_webchat.chat_room.isGroup = 0;
	DELETE avatar_webchat.chat_room_members
	FROM avatar_webchat.chat_room_members
	WHERE avatar_webchat.chat_room_members.user_id = userID;	
SET autocommit = 0;
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_delete_disabled_account
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_delete_disabled_account`(
	IN `report_id` INT
)
BEGIN
DELETE 
FROM chat_blacklist 
WHERE chat_blacklist.id = `report_id`;
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_delete_report_by_id
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_delete_report_by_id`(
	IN `report_id` INT
)
BEGIN
DELETE 
FROM chat_reports 
WHERE chat_reports.id = `report_id`;
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_disable_user
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_disable_user`(
	IN `userId` INT
)
BEGIN
DELETE FROM chat_reports 
WHERE chat_reports.reportedUserId = `userId`;
INSERT INTO chat_blacklist(userId)
VALUES(`userId`);
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_get_chat_history
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_get_chat_history`(
	IN `chatId` INT





)
BEGIN
SELECT user_id, username, line_text, posted_at, isFile
FROM chat_line
LEFT OUTER JOIN chat_user ON chat_user.id = chat_line.user_id
WHERE chat_line.chat_room_id = `chatId` AND posted_at BETWEEN NOW() - INTERVAL 30 DAY AND NOW();
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_remove_user_from_chat_room
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_remove_user_from_chat_room`(
	IN `chatRoomId` INT,
	IN `userId` INT
)
BEGIN
DELETE FROM chat_room_members
WHERE chat_room_id = `chatRoomId` AND user_id = `userId`;
END//
DELIMITER ;

-- Dumping structure for procedure testdb.proc_set_subscriber_status
DELIMITER //
CREATE DEFINER=`avatar_admin`@`%` PROCEDURE `proc_set_subscriber_status`(
	IN `userId` INT,
	IN `subscriberStatus` INT

)
BEGIN
UPDATE chat_user_info cui
	LEFT OUTER JOIN chat_user on chat_user.info_id = cui.id
	SET cui.isSubscriber = `subscriberStatus`
	WHERE chat_user.id = `userId`;
END//
DELIMITER ;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
