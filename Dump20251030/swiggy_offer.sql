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
-- Table structure for table `offer`
--

DROP TABLE IF EXISTS `offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `applicable_days` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `max_discount_amount` double DEFAULT NULL,
  `min_order_value` double DEFAULT NULL,
  `offer_code` varchar(255) DEFAULT NULL,
  `offer_description` varchar(255) NOT NULL,
  `offer_discount` double NOT NULL,
  `offer_name` varchar(255) NOT NULL,
  `offer_type` enum('BULK_ORDER','COMBO_DEAL','FIRST_TIME_USER','ONLINE_ORDER','SEASONAL','SPECIAL_OCCASION','STUDENT_DISCOUNT','WALK_IN') NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `terms_and_conditions` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `restaurant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKa7t4b6wy3kqwpwa5pu920vung` (`offer_code`),
  KEY `FKbpynrsf3jy8kmm3bavigb7gns` (`restaurant_id`),
  CONSTRAINT `FKbpynrsf3jy8kmm3bavigb7gns` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer`
--

LOCK TABLES `offer` WRITE;
/*!40000 ALTER TABLE `offer` DISABLE KEYS */;
INSERT INTO `offer` VALUES (1,'MON,TUE,WED,THU,FRI','2025-05-27 13:52:45.413300','2025-06-28 23:59:59.000000',_binary '',200,500,'SUMMER2024','Get amazing discounts on all summer special menu items',25,'Summer Special Discount','WALK_IN','2024-04-01 00:00:00.000000','Valid on minimum order value of ₹500. Maximum discount up to ₹200. Not valid on beverages.','2025-05-27 13:52:45.413300',1),(3,'MON,TUE,WED,THU,FRI','2025-05-22 10:48:27.538637','2025-06-27 23:59:59.000000',_binary '',200,500,'SUMMER9024','Get amazing discounts on all summer special menu items',25,'Summer Special Discount','WALK_IN','2024-04-01 00:00:00.000000','Valid on minimum order value of ₹500. Maximum discount up to ₹200. Not valid on beverages.','2025-05-28 10:48:27.538637',2);
/*!40000 ALTER TABLE `offer` ENABLE KEYS */;
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
