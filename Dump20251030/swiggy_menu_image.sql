<<<<<<< HEAD
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
-- Table structure for table `menu_image`
--

DROP TABLE IF EXISTS `menu_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image` varchar(255) NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1kys9vy0wgep39mnixg7edn4m` (`menu_id`),
  CONSTRAINT `FK1kys9vy0wgep39mnixg7edn4m` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_image`
--

LOCK TABLES `menu_image` WRITE;
/*!40000 ALTER TABLE `menu_image` DISABLE KEYS */;
INSERT INTO `menu_image` VALUES (1,'menu-items\\13028a16-a715-4d04-a85c-a85b2b35b7ca.png',1),(2,'menu-items\\14eab669-faef-4d3d-a528-2db070292593.PNG',1),(3,'menu-items\\89e15779-5e3d-4aa6-bb30-567e295464be.png',2),(4,'menu-items\\538d1cc3-b767-444f-b04d-501514720ff5.PNG',2),(5,'menu-items\\ffaffd81-2ae1-4121-b1bf-c2a2b7ee8d7e.png',3),(6,'menu-items\\e58885fb-05e6-4904-9155-104e377f5fa6.PNG',3),(7,'menu-items\\dfcf96d8-48ee-489c-ac74-73d61ba39a2e.png',4),(8,'menu-items\\5eea3bd4-86cb-445c-8925-3a59b9ffeddf.PNG',4),(9,'menu.jpg',5),(10,'menu.jpg',5),(11,'menu.jpg',6),(12,'menu.jpg',6),(13,'menu.jpg',7),(14,'menu.jpg',7),(15,'menu.jpg',8),(16,'menu.jpg',8),(17,'menu.jpg',9),(18,'menu.jpg',9),(19,'menu.jpg',10),(20,'menu.jpg',10),(21,'menu.jpg',11),(22,'menu.jpg',11),(23,'menu.jpg',12),(24,'menu.jpg',12),(25,'menu.jpg',13),(26,'menu.jpg',13),(27,'menu.jpg',14),(28,'menu.jpg',14),(29,'menu.jpg',15),(30,'menu.jpg',15),(31,'menu.jpg',16),(32,'menu.jpg',16),(33,'menu.jpg',17),(34,'menu.jpg',17),(35,'menu.jpg',18),(36,'menu.jpg',18);
/*!40000 ALTER TABLE `menu_image` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-30 14:58:13
=======
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
-- Table structure for table `menu_image`
--

DROP TABLE IF EXISTS `menu_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image` varchar(255) NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1kys9vy0wgep39mnixg7edn4m` (`menu_id`),
  CONSTRAINT `FK1kys9vy0wgep39mnixg7edn4m` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_image`
--

LOCK TABLES `menu_image` WRITE;
/*!40000 ALTER TABLE `menu_image` DISABLE KEYS */;
INSERT INTO `menu_image` VALUES (1,'menu-items\\13028a16-a715-4d04-a85c-a85b2b35b7ca.png',1),(2,'menu-items\\14eab669-faef-4d3d-a528-2db070292593.PNG',1),(3,'menu-items\\89e15779-5e3d-4aa6-bb30-567e295464be.png',2),(4,'menu-items\\538d1cc3-b767-444f-b04d-501514720ff5.PNG',2),(5,'menu-items\\ffaffd81-2ae1-4121-b1bf-c2a2b7ee8d7e.png',3),(6,'menu-items\\e58885fb-05e6-4904-9155-104e377f5fa6.PNG',3),(7,'menu-items\\dfcf96d8-48ee-489c-ac74-73d61ba39a2e.png',4),(8,'menu-items\\5eea3bd4-86cb-445c-8925-3a59b9ffeddf.PNG',4),(9,'menu.jpg',5),(10,'menu.jpg',5),(11,'menu.jpg',6),(12,'menu.jpg',6),(13,'menu.jpg',7),(14,'menu.jpg',7),(15,'menu.jpg',8),(16,'menu.jpg',8),(17,'menu.jpg',9),(18,'menu.jpg',9),(19,'menu.jpg',10),(20,'menu.jpg',10),(21,'menu.jpg',11),(22,'menu.jpg',11),(23,'menu.jpg',12),(24,'menu.jpg',12),(25,'menu.jpg',13),(26,'menu.jpg',13),(27,'menu.jpg',14),(28,'menu.jpg',14),(29,'menu.jpg',15),(30,'menu.jpg',15),(31,'menu.jpg',16),(32,'menu.jpg',16),(33,'menu.jpg',17),(34,'menu.jpg',17),(35,'menu.jpg',18),(36,'menu.jpg',18);
/*!40000 ALTER TABLE `menu_image` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-30 14:58:13
>>>>>>> 2ae672392849595dc7c5a618b9a00634ab8f387c
