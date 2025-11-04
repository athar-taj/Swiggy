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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(255) NOT NULL,
  `otp` int NOT NULL,
  `otp_expiry` datetime(6) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `role` enum('ADMIN','DELIVERY_PERSON','OWNER','USER') DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2025-05-13 17:59:47.734463','mrtaj2372@gmail.com',_binary '',23.0258,72.562,'Priya Patel',0,NULL,'$2a$10$.V1bawgLEgeuG4Ho1lpP3.vJKrHJu8Iu/F8/RJVaVnG8mg3jJ1Qom','9826543120','ADMIN','2025-05-13 17:59:47.734463'),(2,'2025-05-22 18:07:56.776457','amit.sharma@example.com',_binary '',23.0325,72.551,'Amit Sharma',694891,'2025-05-22 18:17:57.145938','$2a$10$lPCYC5sMC3iuVkSu4pnGXun.Yq4MN4ifPC7UXmvNjcF045MTFVFVa','9876543210','USER','2025-05-22 18:07:56.776457'),(3,'2025-05-22 18:08:03.538612','sita.rao@example.com',_binary '',23.0235,72.5677,'Sita Rao',380673,'2025-05-22 18:18:03.631365','$2a$10$rdHvbQy.R/cphvno0jwmUueTAX/5hxB5SIXZtejKk0EKf6XtUBN9S','9988776655','USER','2025-05-22 18:08:03.538612'),(4,'2025-05-22 18:08:17.257663','ananya.ghosh@example.com',_binary '',23.0441,72.5522,'Ananya Ghosh',348620,'2025-05-22 18:18:17.424188','$2a$10$UtdO24kcgMI5cO3R1eUk/ONEFwNuSHgqA7xArSOJBpShZcRNYxzui','9034567890','USER','2025-05-22 18:08:17.257663'),(5,'2025-05-22 18:08:35.580993','mrtaj2372@gmail.com',_binary '',23.0256,72.5724,'Kiran Reddy',0,NULL,'$2a$10$CdCqocTtQhQKexzHocRE0e/31KSuLf3kzt50Kr9puvEk2QRiqqoRK','9123456789','USER','2025-05-22 18:08:35.580993'),(6,'2025-05-22 18:08:41.831971','meera.iyer@example.com',_binary '',23.0356,72.5723,'Meera Iyer',147860,'2025-05-22 18:18:41.925061','$2a$10$HkYc7a.KzcCJpYt89DRKJe9VBbxAgFbIBRTV9hIlPOnhkjdLasRl6','9988771122','USER','2025-05-22 18:08:41.831971'),(7,'2025-05-22 18:08:47.176506','vikram.patil@example.com',_binary '',23.0315,72.581,'Vikram Patil',214107,'2025-05-22 18:18:47.221441','$2a$10$ktyryM/uQv.CdF3Os2O8Te2B9x67madZJ7vsNmC7QbDrTXRVdllP2','9753124680','USER','2025-05-22 18:08:47.176506'),(8,'2025-05-22 18:08:59.585978','ayesha.khan@example.com',_binary '',23.0253,72.56,'Ayesha Khan',940097,'2025-05-22 18:18:59.667935','$2a$10$AsphI9b30EhA3FsiaWH6zu7cQXlrginaBA3ArY7u.6hk8tMyOYsMe','9876543211','USER','2025-05-22 18:08:59.585978'),(9,'2025-05-22 18:12:02.284018','manoj.verma@example.com',_binary '',23.0276,72.5513,'Manoj Verma',591945,'2025-05-22 18:22:02.558394','$2a$10$C3j71OMULfoMgR6GBHhbTOzEQlOOfKr5xP.NZyPk4ZXShogxwX8xS','9321564789','USER','2025-05-22 18:12:02.285007'),(10,'2025-05-22 18:12:11.422949','neha.sharma@example.com',_binary '',23.0259,72.5826,'Neha Sharma',908142,'2025-05-22 18:22:11.511968','$2a$10$Fm1UxZwBK9JWBGau01Hx8ObrKTiFOpkwwwugRoI33QpogrywUGykC','9713456789','USER','2025-05-22 18:12:11.422949');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(255) NOT NULL,
  `otp` int NOT NULL,
  `otp_expiry` datetime(6) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `role` enum('ADMIN','DELIVERY_PERSON','OWNER','USER') DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2025-05-13 17:59:47.734463','mrtaj2372@gmail.com',_binary '',23.0258,72.562,'Priya Patel',0,NULL,'$2a$10$.V1bawgLEgeuG4Ho1lpP3.vJKrHJu8Iu/F8/RJVaVnG8mg3jJ1Qom','9826543120','ADMIN','2025-05-13 17:59:47.734463'),(2,'2025-05-22 18:07:56.776457','amit.sharma@example.com',_binary '',23.0325,72.551,'Amit Sharma',694891,'2025-05-22 18:17:57.145938','$2a$10$lPCYC5sMC3iuVkSu4pnGXun.Yq4MN4ifPC7UXmvNjcF045MTFVFVa','9876543210','USER','2025-05-22 18:07:56.776457'),(3,'2025-05-22 18:08:03.538612','sita.rao@example.com',_binary '',23.0235,72.5677,'Sita Rao',380673,'2025-05-22 18:18:03.631365','$2a$10$rdHvbQy.R/cphvno0jwmUueTAX/5hxB5SIXZtejKk0EKf6XtUBN9S','9988776655','USER','2025-05-22 18:08:03.538612'),(4,'2025-05-22 18:08:17.257663','ananya.ghosh@example.com',_binary '',23.0441,72.5522,'Ananya Ghosh',348620,'2025-05-22 18:18:17.424188','$2a$10$UtdO24kcgMI5cO3R1eUk/ONEFwNuSHgqA7xArSOJBpShZcRNYxzui','9034567890','USER','2025-05-22 18:08:17.257663'),(5,'2025-05-22 18:08:35.580993','mrtaj2372@gmail.com',_binary '',23.0256,72.5724,'Kiran Reddy',0,NULL,'$2a$10$CdCqocTtQhQKexzHocRE0e/31KSuLf3kzt50Kr9puvEk2QRiqqoRK','9123456789','USER','2025-05-22 18:08:35.580993'),(6,'2025-05-22 18:08:41.831971','meera.iyer@example.com',_binary '',23.0356,72.5723,'Meera Iyer',147860,'2025-05-22 18:18:41.925061','$2a$10$HkYc7a.KzcCJpYt89DRKJe9VBbxAgFbIBRTV9hIlPOnhkjdLasRl6','9988771122','USER','2025-05-22 18:08:41.831971'),(7,'2025-05-22 18:08:47.176506','vikram.patil@example.com',_binary '',23.0315,72.581,'Vikram Patil',214107,'2025-05-22 18:18:47.221441','$2a$10$ktyryM/uQv.CdF3Os2O8Te2B9x67madZJ7vsNmC7QbDrTXRVdllP2','9753124680','USER','2025-05-22 18:08:47.176506'),(8,'2025-05-22 18:08:59.585978','ayesha.khan@example.com',_binary '',23.0253,72.56,'Ayesha Khan',940097,'2025-05-22 18:18:59.667935','$2a$10$AsphI9b30EhA3FsiaWH6zu7cQXlrginaBA3ArY7u.6hk8tMyOYsMe','9876543211','USER','2025-05-22 18:08:59.585978'),(9,'2025-05-22 18:12:02.284018','manoj.verma@example.com',_binary '',23.0276,72.5513,'Manoj Verma',591945,'2025-05-22 18:22:02.558394','$2a$10$C3j71OMULfoMgR6GBHhbTOzEQlOOfKr5xP.NZyPk4ZXShogxwX8xS','9321564789','USER','2025-05-22 18:12:02.285007'),(10,'2025-05-22 18:12:11.422949','neha.sharma@example.com',_binary '',23.0259,72.5826,'Neha Sharma',908142,'2025-05-22 18:22:11.511968','$2a$10$Fm1UxZwBK9JWBGau01Hx8ObrKTiFOpkwwwugRoI33QpogrywUGykC','9713456789','USER','2025-05-22 18:12:11.422949');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
>>>>>>> 2ae672392849595dc7c5a618b9a00634ab8f387c
