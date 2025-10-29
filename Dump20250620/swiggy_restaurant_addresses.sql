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
-- Table structure for table `restaurant_addresses`
--

DROP TABLE IF EXISTS `restaurant_addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant_addresses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `outlet` varchar(255) DEFAULT NULL,
  `pincode` varchar(255) NOT NULL,
  `state` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `restaurant_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6ab2ta6dust5omm00duxjv9mo` (`restaurant_id`),
  CONSTRAINT `FK6ab2ta6dust5omm00duxjv9mo` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_addresses`
--

LOCK TABLES `restaurant_addresses` WRITE;
/*!40000 ALTER TABLE `restaurant_addresses` DISABLE KEYS */;
INSERT INTO `restaurant_addresses` VALUES (2,'Terminal 1, Sardar Vallabhbhai Patel International Airport','Ahmedabad',NULL,23.0314594,72.5641077,'Sardar Vallabhbhai Patel International Airport Branch','380009','Gujarat',NULL,1),(4,'Law Garden, Ellisbridge','Ahmedabad',NULL,23.0332,72.5756,'Law Garden Branch','380006','Gujarat',NULL,3),(5,'Sabarmati Riverfront, Near Ellis Bridge','Ahmedabad',NULL,23.0846,72.5885,'Sabarmati Riverfront Branch','380005','Gujarat',NULL,4),(6,'Vastrapur Lake, Vastrapur','Ahmedabad',NULL,23.0343,72.5086,'Vastrapur Lake Branch','380015','Gujarat',NULL,2),(7,'Kankaria Lake, Maninagar','Ahmedabad',NULL,23.0332,72.5889,'Kankaria Lake Branch','380008','Gujarat',NULL,6),(8,'C G Road, Navrangpura','Ahmedabad',NULL,23.0227,72.5825,'C G Road Branch','380009','Gujarat',NULL,7),(9,'Prahlad Nagar, Satellite','Ahmedabad',NULL,23.0228,72.5276,'Prahlad Nagar Branch','380015','Gujarat',NULL,8),(10,'Bopal, South Bopal','Ahmedabad',NULL,23.0229,72.5093,'Bopal Branch','380058','Gujarat',NULL,9),(11,'Bopal, South Bopal','Ahmedabad',NULL,23.0229,72.5093,'Bopal Branch','380058','Gujarat',NULL,10),(12,'Bopal, South Bopal','Ahmedabad',NULL,23.0229,72.5093,'Bopal Branch','380058','Gujarat',NULL,11),(13,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,12),(14,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,13),(15,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,14),(16,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,15),(17,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,16),(18,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,17),(19,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,30),(20,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,31),(21,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,32),(22,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,33),(25,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,35),(26,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,36),(27,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,37),(28,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,38),(29,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,39),(30,'Bopal, South Bopal','Ahmedabad',NULL,23.0332,72.5889,'Bopal Branch','380058','Gujarat',NULL,40);
/*!40000 ALTER TABLE `restaurant_addresses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-20 12:06:10
