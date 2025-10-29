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
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact_no` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `end_time` time(6) DEFAULT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `open_days` text,
  `outlet` varchar(255) DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  `restaurant_type` enum('NON_VEG','VEG') DEFAULT NULL,
  `start_time` time(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `avg_delivery_time` time(6) DEFAULT NULL,
  `cost_for_two` double DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `total_rating` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (1,'+919876543210','2025-05-19 12:13:56.763936','A wonderful dining experience','restaurant@example.com','22:00:00.000000',_binary '','restaurant-logos\\6140b29f-3de1-4869-81a6-e36dfc7601fd.PNG','Sample Restaurant','MONDAY,TUESDAY,WEDNESDAY','123 Main Street',1,'VEG','10:00:00.000000','2025-05-28 15:04:07.138115','00:25:00.000000',500,4.5,4),(2,'+919876543210','2025-05-19 13:02:36.559950','A wonderful dining experience','restaurant@example.com','22:00:00.000000',_binary '','restaurant-logos\\a5c79279-eba3-4978-9d06-7e5c05864e2f.PNG','Sample Restaurant','MONDAY,TUESDAY,WEDNESDAY','123 Main Street',1,'VEG','10:00:00.000000','2025-05-19 13:02:36.559950','00:20:00.000000',1000,3.5,54),(3,'+919876543210','2025-05-19 13:11:18.689196','A wonderful dining experience','restaurant@example.com','22:00:00.000000',_binary '','restaurant-logos\\7936eca9-5a03-4460-a86b-25e87d0794aa.PNG','Sample Restaurant','MONDAY,TUESDAY,WEDNESDAY','123 Main Street',1,'VEG','10:00:00.000000','2025-05-19 13:11:18.689196','00:15:00.000000',1235,4.1,200),(4,'+919876543210','2025-05-19 13:17:12.756265','A wonderful dining experience','restaurant@example.com','22:00:00.000000',_binary '','1747640832761-Capture.PNG','Sample Restaurant','MONDAY,TUESDAY,WEDNESDAY','123 Main Street',1,'VEG','10:00:00.000000','2025-05-19 13:17:12.756265','00:10:00.000000',1200,3.5,61),(5,'+919876543210','2025-05-19 13:42:52.899871','A wonderful dining experience','restaurant@example.com','22:00:00.000000',_binary '','1747642372905-Capture.PNG','Sample Restaurant','MONDAY,TUESDAY,WEDNESDAY','Main Street',1,'VEG','10:00:00.000000','2025-05-19 13:42:52.899871','00:30:00.000000',700,4.4,32),(6,'+919876543210','2025-05-19 13:43:05.158763','A wonderful dining experience','restaurant@examp89898le.com','22:00:00.000000',_binary '','1747642385160-Capture.PNG','Sample Restaurant','MONDAY,TUESDAY,WEDNESDAY','Nothing',1,'VEG','10:00:00.000000','2025-05-19 13:43:05.158763','00:20:00.000000',900,3.6,87),(7,'9875192829','2025-05-19 14:26:23.665556','A wonderful dining experience','restaurant@exam4343ple.com','22:00:00.000000',_binary '','1747644983678-Screenshot (3).png','Sample Restaurant','MONDAY,TUESDAY,WEDNESDAY','123 Main Street',1,'VEG','10:00:00.000000','2025-05-19 14:26:23.665556','00:10:00.000000',800,3.5,56),(8,'9875192829','2025-05-19 15:55:47.899968','A wonderful dining experience','restaurant@exam4343ple.com','22:00:00.000000',_binary '','1747650347907-Screenshot (3).png','Sample Restaurant','MONDAY,TUESDAY,WEDNESDAY','Low Street',1,'VEG','10:00:00.000000','2025-05-19 15:55:47.899968','00:24:00.000000',600,3.1,900),(9,'9875192829','2025-05-19 16:10:43.013492','A wonderful dining experience','restaurant@exam4343ple.com','22:00:00.000000',_binary '','1747651243020-Screenshot (3).png','Sample Restaurant','MONDAY,TUESDAY,WEDNESDAY','123 Main Street',1,'VEG','10:00:00.000000','2025-05-19 16:10:43.013492','00:20:00.000000',560,5,56),(10,'9875192829','2025-05-19 16:15:41.113039','A wonderful dining experience','restaurant@exam4343ple.com','22:00:00.000000',_binary '','1747651541119-Screenshot (3).png','Sample Restaurant','MONDAY,TUESDAY,WEDNESDAY','123 Main Street',1,'VEG','10:00:00.000000','2025-05-19 16:15:41.113039','00:30:00.000000',720,2.3,587),(11,'9875192829','2025-05-20 09:39:18.281096','A wonderful dining experience','restaurant@exam4343ple.com','22:00:00.000000',_binary '',NULL,'Restaurant','MONDAY,TUESDAY,WEDNESDAY','123 Main Street',1,'VEG','10:00:00.000000','2025-05-20 09:39:18.281096','00:35:00.000000',255,4.9,514),(12,'9123456780','2025-05-22 18:40:11.640243','Authentic Indian vegetarian cuisine','contact@spicegarden.com','21:30:00.000000',_binary '',NULL,'Spice Garden','MONDAY,FRIDAY,SUNDAY','456 Elm Avenue',2,'VEG','09:00:00.000000','2025-05-22 18:40:11.640243','00:25:00.000000',568,5,45),(13,'9812345678','2025-05-22 18:41:09.419306','A mix of global flavors','info@multicuisinehub.com','23:00:00.000000',_binary '',NULL,'MultiCuisine Hub','TUESDAY,WEDNESDAY,THURSDAY','789 Market Road',3,'NON_VEG','11:00:00.000000','2025-05-22 18:41:09.419306','00:30:00.000000',6878,2.5,152),(14,'9898989898','2025-05-22 18:41:28.651961','Healthy and organic vegetarian meals','hello@greenleaf.com','20:00:00.000000',_binary '',NULL,'Green Leaf Café','MONDAY,TUESDAY,SATURDAY','321 Wellness Street',4,'VEG','08:30:00.000000','2025-05-22 18:41:28.652959','00:50:00.000000',680,3.5,45),(15,'9777799999','2025-05-22 18:41:41.384159','Grilled non-veg specials and buffet','support@bbqnation.com','23:59:00.000000',_binary '',NULL,'BBQ Nation','FRIDAY,SATURDAY,SUNDAY','200 Flame Avenue',5,'NON_VEG','12:00:00.000000','2025-05-22 18:41:41.384159','00:40:00.000000',2380,3.6,557),(16,'9001234567','2025-05-22 18:41:57.320430','Traditional Punjabi dishes','booking@tasteofpunjab.com','22:30:00.000000',_binary '',NULL,'Taste of Punjab','MONDAY,TUESDAY,WEDNESDAY,THURSDAY','22 Cultural Plaza',6,'VEG','10:00:00.000000','2025-05-22 18:41:57.320430','00:10:00.000000',3560,4.6,457),(17,'9988776655','2025-05-22 18:42:04.961897','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'Biryani Express','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','9 Spice Lane',7,'NON_VEG','11:00:00.000000','2025-05-22 18:42:04.961922','00:15:00.000000',1500,3.1,54),(30,'9988776655','2025-05-26 16:34:55.265186','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'Royal Indian Kitchen','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','1 Main Lane',7,'NON_VEG','11:00:00.000000','2025-05-26 16:34:55.265186','00:15:00.000000',5000,NULL,0),(31,'9988776655','2025-05-29 12:17:27.746059','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'Royal Delhi Kitchen','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','1 Main Lane',5,'NON_VEG','11:00:00.000000','2025-05-29 12:17:27.746059','00:15:00.000000',5000,NULL,0),(32,'9988776655','2025-05-29 12:21:08.124940','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'Royal Delhi Kitchen','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','2 Main Lane',5,'NON_VEG','11:00:00.000000','2025-05-29 12:21:08.124940','00:15:00.000000',5000,NULL,0),(33,'9988776655','2025-05-29 12:24:29.148166','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'Royal Delhi Kitchen','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','6 Main Lane',5,'NON_VEG','11:00:00.000000','2025-05-29 12:24:29.148166','00:15:00.000000',5000,NULL,0),(34,'9988776655','2025-05-29 14:07:07.258930','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'Royal Delhi Kitchen','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','7 Main Lane',5,'NON_VEG','11:00:00.000000','2025-05-29 14:07:07.258930','00:15:00.000000',5000,NULL,0),(35,'9988776655','2025-05-29 14:10:52.246880','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'The Cape','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','7 Main Lane',3,'NON_VEG','11:00:00.000000','2025-05-29 14:10:52.246880','00:15:00.000000',5000,NULL,0),(36,'9988776655','2025-05-29 14:13:09.198100','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'The Cape','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','1 Main Lane',3,'NON_VEG','11:00:00.000000','2025-05-29 14:13:09.198100','00:15:00.000000',5000,NULL,0),(37,'9988776655','2025-05-29 15:34:30.678110','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'Green Vally','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','1 Main Lane',3,'NON_VEG','11:00:00.000000','2025-05-29 15:34:30.678110','00:15:00.000000',5000,NULL,0),(38,'9988776655','2025-05-29 15:49:53.829293','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'Green Vally','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','2 Main Lane',3,'NON_VEG','11:00:00.000000','2025-05-29 15:49:53.829293','00:15:00.000000',5000,NULL,0),(39,'9988776655','2025-05-29 15:53:48.939277','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'Green Vally','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','3 Main Lane',3,'NON_VEG','11:00:00.000000','2025-05-29 15:53:48.939277','00:15:00.000000',5000,NULL,0),(40,'9988776655','2025-05-29 15:55:14.310757','Fast and flavorful biryani options','orders@biryaniexpress.com','22:00:00.000000',_binary '',NULL,'Green Vally','WEDNESDAY,THURSDAY,FRIDAY,SATURDAY','4 Main Lane',3,'NON_VEG','11:00:00.000000','2025-05-29 15:55:14.310757','00:15:00.000000',5000,NULL,0);
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
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
