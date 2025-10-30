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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delivery_address` varchar(255) NOT NULL,
  `delivery_city` varchar(255) DEFAULT NULL,
  `delivery_landmark` varchar(255) DEFAULT NULL,
  `delivery_status` enum('CANCELLED','DELIVERED','OUT_FOR_DELIVERY','PENDING') DEFAULT NULL,
  `menu_id` bigint NOT NULL,
  `order_status` enum('CANCELLED','DELIVERED','PENDING','PLACED') DEFAULT NULL,
  `pincode` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  `receiver_name` varchar(255) NOT NULL,
  `receiver_phone_number` varchar(255) NOT NULL,
  `restaurant_id` bigint NOT NULL,
  `total_price` double NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `current_latitude` double NOT NULL,
  `current_longitude` double NOT NULL,
  `delivery_partner_name` varchar(255) DEFAULT NULL,
  `delivery_partner_phone` varchar(255) DEFAULT NULL,
  `estimated_delivery_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2025-05-20 12:20:29.419589','123 Main Street, Apartment 4B','Mumbai','Near Central Park','PENDING',2,'PENDING','400001',299,2,'John Doe','9876543210',1,299,'2025-05-20 12:20:29.419589',1,0,0,NULL,NULL,NULL),(2,'2025-05-21 16:15:04.088664','123 Main Street, Apartment 4B','Mumbai','Near Central Park','PENDING',1,'PENDING','400001',299,2,'John Doe','9876543210',1,598,'2025-05-21 16:15:04.088664',2,0,0,NULL,NULL,NULL),(3,'2025-05-26 17:41:53.883237','123 Main Street, Apartment 4B','Mumbai','Near Central Park','PENDING',4,'PENDING','400001',299,2,'John Doe','9876543210',1,598,'2025-05-26 17:41:53.883237',3,0,0,NULL,NULL,NULL),(4,'2025-05-26 18:30:15.837628','123 Main Street, Apartment 4B','Mumbai','Near Central Park','PENDING',3,'PENDING','400001',299,2,'John Doe','9876543210',1,598,'2025-05-26 18:30:15.837628',5,0,0,NULL,NULL,NULL),(5,'2025-05-26 18:33:24.634968','123 Main Street, Apartment 4B','Mumbai','Near Central Park','PENDING',5,'PENDING','400001',299,2,'John Doe','9876543210',1,598,'2025-05-26 18:33:24.635965',6,0,0,NULL,NULL,NULL),(6,'2025-05-26 18:35:47.609073','123 Main Street, Apartment 4B','Mumbai','Near Central Park','PENDING',6,'PENDING','400001',299,2,'John Doe','9876543210',1,598,'2025-05-26 18:35:47.609073',8,0,0,NULL,NULL,NULL),(7,'2025-05-26 18:37:49.417588','123 Main Street, Apartment 4B','Mumbai','Near Central Park','PENDING',7,'PENDING','400001',299,2,'John Doe','9876543210',1,598,'2025-05-26 18:37:49.417588',4,0,0,NULL,NULL,NULL),(8,'2025-05-26 18:39:30.046963','123 Main Street, Apartment 4B','Mumbai','Near Central Park','PENDING',8,'PENDING','400001',299,2,'John Doe','9876543210',1,598,'2025-05-26 18:39:30.046963',1,0,0,NULL,NULL,NULL),(9,'2025-05-26 18:40:30.860114','123 Main Street, Apartment 4B','Mumbai','Near Central Park','PENDING',9,'PENDING','400001',299,2,'John Doe','9876543210',1,598,'2025-05-26 18:40:30.860114',4,0,0,NULL,NULL,NULL),(10,'2025-05-28 16:53:35.823092','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-05-28 16:53:35.823092',1,0,0,NULL,NULL,NULL),(11,'2025-05-28 16:57:37.592143','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-05-28 16:57:37.592143',1,51.5074,72.1278,'Delivery Partner Name','9876543211','12:30'),(12,'2025-05-28 17:19:13.060281','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-05-28 17:19:13.060281',1,51.5074,72.1278,'Delivery Partner Name','9876543211',NULL),(13,'2025-05-28 17:26:39.841234','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-05-28 17:26:39.841234',1,51.5074,72.1278,'Delivery Partner Name','9876543211','[[0, 25]] minutes'),(14,'2025-05-28 17:28:12.327821','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-05-28 17:28:12.327821',1,51.5074,72.1278,'Delivery Partner Name','9876543211','[0, 25] minutes'),(15,'2025-05-28 17:32:20.149914','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-05-28 17:32:20.149914',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null minutes'),(16,'2025-05-28 17:36:24.460970','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-05-28 17:36:24.460970',1,51.5074,72.1278,'Delivery Partner Name','9876543211','25'),(17,'2025-06-12 17:54:48.283947','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-06-12 17:54:48.283947',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(18,'2025-06-13 12:56:29.726161','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-06-13 12:56:29.726161',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(19,'2025-06-13 12:57:39.569110','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-06-13 12:57:39.569110',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(20,'2025-06-13 13:04:51.490444','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-06-13 13:04:51.490444',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(21,'2025-06-13 13:13:21.746942','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-06-13 13:13:21.746942',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(22,'2025-06-13 13:40:31.345683','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-06-13 13:40:31.345683',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(23,'2025-06-13 13:40:36.971368','123 Baker Street, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-06-13 13:40:36.971368',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(24,'2025-06-13 13:40:43.110090','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-06-13 13:40:43.110090',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(25,'2025-06-13 13:41:23.659468','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'John Doe','9876543210',5,498,'2025-06-13 13:41:23.659468',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(26,'2025-06-13 13:41:52.087705','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'sjw Doe','2876543210',5,498,'2025-06-13 13:41:52.087705',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(27,'2025-06-13 13:43:02.523420','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'sjw Doe','2876543210',5,498,'2025-06-13 13:43:02.523420',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(28,'2025-06-13 13:43:07.788792','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'sjw Doe','2876543210',5,498,'2025-06-13 13:43:07.788792',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(29,'2025-06-13 13:44:12.173344','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 13:44:12.173344',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(30,'2025-06-13 14:04:32.688080','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 14:04:32.688080',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(31,'2025-06-13 14:04:36.894215','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 14:04:36.894215',1,51.5074,72.1278,'Delivery Partner Name','9876543211','25 mins'),(32,'2025-06-13 14:04:38.759708','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 14:04:38.759708',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(33,'2025-06-13 14:04:40.628849','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 14:04:40.628849',1,51.5074,72.1278,'Delivery Partner Name','9876543211','25 mins'),(34,'2025-06-13 14:04:41.780217','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 14:04:41.780217',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(35,'2025-06-13 14:04:42.890442','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 14:04:42.890442',1,51.5074,72.1278,'Delivery Partner Name','9876543211','25 mins'),(36,'2025-06-13 14:04:43.856568','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 14:04:43.856568',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(37,'2025-06-13 14:04:44.988009','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 14:04:44.988009',1,51.5074,72.1278,'Delivery Partner Name','9876543211','25 mins'),(38,'2025-06-13 14:04:46.060457','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 14:04:46.060457',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(39,'2025-06-13 14:04:54.370720','123 Baker skiaija, London','London','Near Big Ben','PENDING',5,'PENDING','123456',249,2,'jenil Doe','2876543210',5,498,'2025-06-13 14:04:54.370720',1,51.5074,72.1278,'Delivery Partner Name','9876543211','25 mins'),(40,'2025-07-07 11:30:55.915205','123 Baker skiaija, London','London','Near Big Ben','PENDING',2,'PENDING','123456',299,2,'jenil Doe','2876543210',5,598,'2025-07-07 11:30:55.915205',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(41,'2025-07-07 11:31:45.192722','123 Baker skiaija, London','London','Near Big Ben','PENDING',2,'PENDING','123456',299,2,'jenil Doe','2876543210',5,598,'2025-07-07 11:31:45.192722',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins'),(42,'2025-07-07 11:35:16.745052','123 Baker skiaija, London','London','Near Big Ben','PENDING',2,'PENDING','123456',299,2,'jenil Doe','2876543210',5,598,'2025-07-07 11:35:16.745052',1,51.5074,72.1278,'Delivery Partner Name','9876543211','null mins');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-30 14:58:11
