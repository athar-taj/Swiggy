-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: swiggy
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `is_read` bit(1) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `notification_type` enum('EMAIL','INBOX_NOTIFICATION','PUSH_NOTIFICATION','SMS') DEFAULT NULL,
  `receiver_id` bigint NOT NULL,
  `receiver_type` enum('RESTAURANT','USER') DEFAULT NULL,
  `sent_at` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,'2025-05-29 15:53:52.964658',_binary '\0','Hello Kiran Reddy, New Restaurant added: Green Vally','EMAIL',39,'RESTAURANT','2025-05-29 15:54:39.308351','New Restaurant added: Green Vally','Send Notification of user nearest of the Restaurant'),(2,'2025-05-29 15:53:52.964658',_binary '\0','Hello Meera Iyer, New Restaurant added: Green Vally','EMAIL',39,'RESTAURANT','2025-05-29 15:54:39.392415','New Restaurant added: Green Vally','Send Notification of user nearest of the Restaurant'),(3,'2025-05-29 15:53:52.964658',_binary '\0','Hello Vikram Patil, New Restaurant added: Green Vally','EMAIL',39,'RESTAURANT','2025-05-29 15:54:39.428458','New Restaurant added: Green Vally','Send Notification of user nearest of the Restaurant'),(4,'2025-05-29 15:53:52.964658',_binary '\0','Hello Neha Sharma, New Restaurant added: Green Vally','EMAIL',39,'RESTAURANT','2025-05-29 15:54:39.494099','New Restaurant added: Green Vally','Send Notification of user nearest of the Restaurant'),(5,'2025-05-29 15:55:20.081597',_binary '\0','Hello Kiran Reddy, New Restaurant added: Green Vally','EMAIL',40,'RESTAURANT','2025-05-29 15:55:20.220226','New Restaurant added: Green Vally','Send Notification of user nearest of the Restaurant'),(6,'2025-05-29 15:55:20.081597',_binary '\0','Hello Meera Iyer, New Restaurant added: Green Vally','EMAIL',40,'RESTAURANT','2025-05-29 15:55:20.308991','New Restaurant added: Green Vally','Send Notification of user nearest of the Restaurant'),(7,'2025-05-29 15:55:20.081597',_binary '\0','Hello Vikram Patil, New Restaurant added: Green Vally','EMAIL',40,'RESTAURANT','2025-05-29 15:55:20.355865','New Restaurant added: Green Vally','Send Notification of user nearest of the Restaurant'),(8,'2025-05-29 15:55:20.081597',_binary '\0','Hello Neha Sharma, New Restaurant added: Green Vally','EMAIL',40,'RESTAURANT','2025-05-29 15:55:20.399157','New Restaurant added: Green Vally','Send Notification of user nearest of the Restaurant');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-30 14:58:12
