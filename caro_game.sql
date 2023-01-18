-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: caro_game
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `PlayerId` int NOT NULL,
  `FriendId` int NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `PlayerId` (`PlayerId`),
  KEY `FriendId` (`FriendId`),
  CONSTRAINT `friend_ibfk_1` FOREIGN KEY (`PlayerId`) REFERENCES `player` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
INSERT INTO `friend` VALUES (3,1,4),(4,4,1),(5,1,2),(6,2,1),(7,1,5),(8,5,1),(9,1,6),(10,6,1),(11,7,1),(12,1,7),(13,7,4),(14,4,7),(15,4,2),(16,2,4),(17,1,12),(18,12,1),(19,12,2),(20,2,12);
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match`
--

DROP TABLE IF EXISTS `match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `match` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `StartAt` datetime NOT NULL,
  `EndAt` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match`
--

LOCK TABLES `match` WRITE;
/*!40000 ALTER TABLE `match` DISABLE KEYS */;
INSERT INTO `match` VALUES (11,'2021-12-13 11:30:28','2021-12-13 11:30:40'),(12,'2021-12-13 15:11:04','2021-12-13 15:11:26'),(13,'2021-12-13 15:11:30','2021-12-13 15:11:40'),(14,'2021-12-13 15:11:43','2021-12-13 15:11:53'),(15,'2021-12-13 15:11:56','2021-12-13 15:12:10'),(16,'2021-12-13 15:12:30','2021-12-13 15:12:40'),(17,'2021-12-13 15:12:45','2021-12-13 15:12:52'),(18,'2021-12-13 15:13:03','2021-12-13 15:13:16'),(19,'2021-12-13 15:13:21','2021-12-13 15:13:32'),(20,'2021-12-13 15:13:35','2021-12-13 15:14:35'),(21,'2021-12-13 15:14:39','2021-12-13 15:14:53'),(22,'2021-12-13 15:25:00','2021-12-13 15:25:13'),(23,'2021-12-13 15:25:17','2021-12-13 15:25:27'),(24,'2021-12-13 16:17:48','2021-12-13 16:18:51'),(25,'2021-12-13 16:35:49','2021-12-13 16:36:15'),(26,'2021-12-13 16:42:26','2021-12-13 16:42:37'),(27,'2021-12-13 18:23:55','2021-12-13 18:24:10'),(28,'2021-12-13 18:28:55','2021-12-13 18:29:07'),(29,'2021-12-13 18:31:55','2021-12-13 18:32:11'),(30,'2021-12-14 01:27:57','2021-12-14 01:28:12'),(31,'2021-12-14 11:13:28','2021-12-14 11:14:10'),(32,'2021-12-14 12:20:59','2021-12-14 12:21:12'),(33,'2021-12-14 12:28:50','2021-12-14 12:29:03'),(34,'2021-12-14 12:29:07','2021-12-14 12:29:17'),(35,'2021-12-14 12:34:30','2021-12-14 12:34:46'),(36,'2021-12-14 13:42:14','2021-12-14 13:42:28'),(37,'2021-12-14 14:59:27','2021-12-14 14:59:38'),(38,'2021-12-14 15:17:56','2021-12-14 15:18:10'),(39,'2021-12-14 15:19:04','2021-12-14 15:19:19'),(40,'2021-12-14 15:25:38','2021-12-14 15:25:51'),(41,'2021-12-14 15:29:58','2021-12-14 15:30:10'),(42,'2021-12-14 15:30:29','2021-12-14 15:30:59'),(43,'2021-12-14 15:31:22','2021-12-14 15:34:30'),(44,'2021-12-14 15:36:34','2021-12-14 15:37:00'),(45,'2021-12-14 15:37:47','2021-12-14 15:38:04'),(46,'2021-12-14 16:17:47','2021-12-14 16:17:57'),(47,'2021-12-14 16:27:19','2021-12-14 16:27:30'),(48,'2021-12-14 16:28:32','2021-12-14 16:28:47'),(49,'2021-12-14 16:33:49','2021-12-14 16:34:35'),(50,'2021-12-14 16:35:10','2021-12-14 16:35:21'),(51,'2021-12-14 16:45:11','2021-12-14 16:45:22'),(52,'2021-12-14 16:49:22','2021-12-14 16:49:33'),(53,'2021-12-14 20:01:40','2021-12-14 20:03:11'),(54,'2021-12-14 20:13:13','2021-12-14 20:15:43'),(55,'2021-12-14 20:14:48','2021-12-14 20:16:48'),(56,'2021-12-14 20:36:25','2021-12-14 20:36:59'),(57,'2021-12-14 20:37:24','2021-12-14 20:37:59'),(58,'2021-12-14 20:43:32','2021-12-14 20:43:46'),(59,'2021-12-14 20:53:17','2021-12-14 20:53:32'),(60,'2021-12-14 21:08:35','2021-12-14 21:09:12'),(61,'2021-12-14 21:14:55','2021-12-14 21:15:11'),(62,'2021-12-14 21:14:33','2021-12-14 21:15:33'),(63,'2021-12-14 21:16:16','2021-12-14 21:16:24'),(64,'2021-12-14 21:21:32','2021-12-14 21:21:46');
/*!40000 ALTER TABLE `match` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Img` varchar(255) NOT NULL,
  `Username` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `FullName` varchar(255) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'icons8_magneto_96px.png','cuong','123456','Cuong Pham'),(2,'icons8_iron_man_96px.png','nam','123456','Nam'),(4,'icons8_pikachu_pokemon_96px.png','thuha','123456','Thu Hà'),(5,'icons8_deadpool_96px.png','player1','1','Player 1'),(6,'icons8_groot_96px.png','player2','1','Player 2'),(7,'icons8_circled_user_male_skin_type_7_96px.png','tuyen','1','1'),(8,'icons8_avatar_96px.png','tuyen2','1','tuyen'),(11,'icons8_circled_user_male_skin_type_7_96px.png','tuyen3','1','tuyen tuyen'),(12,'icons8_logan_x_men_96px.png','khanh','123456','Gia Khánh');
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playerinmatch`
--

DROP TABLE IF EXISTS `playerinmatch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playerinmatch` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Result` varchar(255) NOT NULL,
  `MatchId` int NOT NULL,
  `PlayerId` int NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FKPlayerInMa932343` (`PlayerId`),
  KEY `MatchId` (`MatchId`),
  CONSTRAINT `FKPlayerInMa932343` FOREIGN KEY (`PlayerId`) REFERENCES `player` (`Id`),
  CONSTRAINT `playerinmatch_ibfk_1` FOREIGN KEY (`MatchId`) REFERENCES `match` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playerinmatch`
--

LOCK TABLES `playerinmatch` WRITE;
/*!40000 ALTER TABLE `playerinmatch` DISABLE KEYS */;
INSERT INTO `playerinmatch` VALUES (21,'THẮNG',11,4),(22,'THUA',11,1),(23,'THUA',12,1),(24,'THẮNG',12,4),(25,'THUA',13,1),(26,'THẮNG',13,4),(27,'THUA',14,1),(28,'THẮNG',14,4),(29,'THUA',15,1),(30,'THẮNG',15,4),(31,'THẮNG',16,1),(32,'THUA',16,4),(33,'THẮNG',17,1),(34,'THUA',17,4),(35,'THẮNG',18,4),(36,'THUA',18,1),(37,'THUA',19,4),(38,'THẮNG',19,1),(39,'THẮNG',20,4),(40,'THUA',20,1),(41,'THẮNG',21,4),(42,'THUA',21,1),(43,'THẮNG',22,1),(44,'THUA',22,4),(45,'THUA',23,1),(46,'THẮNG',23,4),(47,'THUA',24,1),(48,'THẮNG',24,7),(49,'THẮNG',25,1),(50,'THUA',25,7),(51,'THUA',26,1),(52,'THẮNG',26,7),(53,'THẮNG',27,1),(54,'THUA',27,4),(55,'THUA',28,1),(56,'THẮNG',28,4),(57,'THẮNG',29,1),(58,'THUA',29,4),(59,'THẮNG',30,1),(60,'THUA',30,4),(61,'THẮNG',31,1),(62,'THUA',31,4),(63,'THẮNG',32,4),(64,'THUA',32,1),(65,'THẮNG',33,1),(66,'THUA',33,4),(67,'THUA',34,1),(68,'THẮNG',34,4),(69,'THUA',35,1),(70,'THẮNG',35,4),(71,'THUA',36,4),(72,'THẮNG',36,1),(73,'THẮNG',37,1),(74,'THUA',37,4),(75,'THẮNG',38,1),(76,'THUA',38,4),(77,'THẮNG',39,4),(78,'THUA',39,1),(79,'THẮNG',40,1),(80,'THUA',40,4),(81,'THẮNG',41,1),(82,'THUA',41,4),(83,'THẮNG',42,4),(84,'THUA',42,1),(85,'THẮNG',43,1),(86,'THUA',43,4),(87,'THUA',44,1),(88,'THẮNG',44,4),(89,'THẮNG',45,1),(90,'THUA',45,4),(91,'THẮNG',46,1),(92,'THUA',46,4),(93,'THẮNG',47,1),(94,'THUA',47,4),(95,'THẮNG',48,4),(96,'THUA',48,1),(97,'THẮNG',49,4),(98,'THUA',49,1),(99,'THẮNG',50,4),(100,'THUA',50,1),(101,'THẮNG',51,1),(102,'THUA',51,4),(103,'THẮNG',52,4),(104,'THUA',52,2),(105,'THUA',53,1),(106,'THẮNG',53,12),(107,'THẮNG',54,1),(108,'THUA',54,4),(109,'THẮNG',55,12),(110,'THUA',55,2),(111,'THẮNG',56,4),(112,'THUA',56,1),(113,'THẮNG',57,2),(114,'THUA',57,4),(115,'THẮNG',58,1),(116,'THUA',58,2),(117,'THẮNG',59,1),(118,'THUA',59,4),(119,'THẮNG',60,12),(120,'THUA',60,1),(121,'THUA',61,1),(122,'THẮNG',61,12),(123,'THUA',62,4),(124,'THẮNG',62,2),(125,'THẮNG',63,1),(126,'THUA',63,4),(127,'THUA',64,1),(128,'THẮNG',64,4);
/*!40000 ALTER TABLE `playerinmatch` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-14 21:24:51
