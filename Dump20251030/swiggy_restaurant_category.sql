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
-- Table structure for table `restaurant_category`
--

DROP TABLE IF EXISTS `restaurant_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant_category` (
  `restaurant_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  KEY `FKq2v7vgpohkb4eojo5xdjf2uxr` (`category_id`),
  CONSTRAINT `FKq2v7vgpohkb4eojo5xdjf2uxr` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_category`
--

LOCK TABLES `restaurant_category` WRITE;
/*!40000 ALTER TABLE `restaurant_category` DISABLE KEYS */;
INSERT INTO `restaurant_category` VALUES (12,3),(12,6),(12,8),(13,2),(13,4),(13,9),(14,1),(14,5),(15,6),(15,7),(16,2),(16,4),(16,8),(17,3),(17,7),(17,9),(18,3),(18,7),(18,9),(19,3),(19,7),(19,9),(20,3),(20,7),(20,9),(21,3),(21,7),(21,9),(22,3),(22,7),(22,9),(23,3),(23,7),(23,9),(24,3),(24,7),(24,9),(25,3),(25,7),(25,9),(26,3),(26,7),(26,9),(27,3),(27,7),(27,9),(28,3),(28,7),(28,9),(29,3),(29,7),(29,9),(30,3),(30,7),(30,9),(31,3),(31,7),(31,9),(32,3),(32,7),(32,9),(33,3),(33,7),(33,9),(34,3),(34,7),(34,9),(35,3),(35,7),(35,9),(36,3),(36,7),(36,9),(37,3),(37,7),(37,9),(38,3),(38,7),(38,9),(39,3),(39,7),(39,9),(40,3),(40,7),(40,9);
/*!40000 ALTER TABLE `restaurant_category` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-30 14:58:10
