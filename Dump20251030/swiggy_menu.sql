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
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount` double NOT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `restaurant_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  `total_rating` int NOT NULL,
  `rating` double NOT NULL,
  `menu_type` enum('BOTH','NON_VEG','VEG') DEFAULT NULL,
  `total_orders` int NOT NULL,
  `minimum_order_value` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKww84tou7nixng06lmxawvcre` (`category_id`),
  KEY `FKblwdtxevpl4mrds8a12q0ohu6` (`restaurant_id`),
  CONSTRAINT `FKblwdtxevpl4mrds8a12q0ohu6` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `FKww84tou7nixng06lmxawvcre` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'2025-05-20 10:17:00.792701','Aromatic basmati rice cooked with tender chicken pieces and authentic spices',10,_binary '','Chicken Biryani',299,'2025-05-20 10:17:00.792701',1,3,15,4.6,'VEG',56,500),(2,'2025-05-20 10:23:04.127537','Aromatic basmati rice cooked with tender chicken pieces and authentic spices',10,_binary '','Malai Tikka',299,'2025-05-20 10:23:04.127537',1,4,15,4.6,'NON_VEG',328,200),(3,'2025-05-20 10:25:22.887116','Aromatic basmati rice cooked with tender chicken pieces and authentic spices',10,_binary '','Rose Cabab',299,'2025-05-20 10:25:22.887116',1,7,434,3,'BOTH',564,180),(4,'2025-05-20 10:26:31.940485','Aromatic basmati rice cooked with tender chicken pieces and authentic spices',10,_binary '','DAhi wade',299,'2025-05-20 10:26:31.940485',1,2,155,4,'NON_VEG',524,260),(5,'2025-05-23 12:13:57.462826','Creamy tomato gravy with soft paneer cubes, infused with Indian spices',15,_binary '','Paneer Butter Masala',249,'2025-05-23 12:13:57.462826',1,7,165,4.1,'BOTH',575,554),(6,'2025-05-23 12:15:07.538421','Stir-fried noodles with mixed vegetables and Indo-Chinese sauces',10,_binary '','Veg Hakka Noodles',199,'2025-05-23 12:15:07.538421',1,1,35,3.5,'BOTH',582,220),(7,'2025-05-23 12:15:26.915997','Spicy chickpeas served with deep-fried bread',10,_binary '','Chole Bhature',149,'2025-05-23 12:15:26.915997',1,4,15,3.2,'NON_VEG',320,560),(8,'2025-05-23 12:15:51.250192','Crispy rice crepe filled with spiced mashed potatoes',5,_binary '','Masala Dosa',129,'2025-05-23 12:15:51.250192',1,6,55,1.6,'BOTH',318,300),(9,'2025-05-23 16:22:48.702555','Classic cheese pizza with tomato sauce and mozzarella',10,_binary '','Margherita Pizza',299,'2025-05-23 16:22:48.702555',1,2,55,4.5,'VEG',21,100),(10,'2025-05-23 16:22:55.328044','Grilled paneer cubes marinated in spices',5,_binary '','Paneer Tikka',199,'2025-05-23 16:22:55.328044',1,3,568,4.3,'NON_VEG',3215,50),(11,'2025-05-23 16:23:02.663501','Crispy vegetable patty with fresh toppings and sauces',8,_binary '','Veggie Burger',149,'2025-05-23 16:23:02.663501',1,4,62,3.6,'VEG',212,210),(12,'2025-05-23 16:23:27.321162','Soft Indian flatbread topped with butter',0,_binary '','Butter Naan',49,'2025-05-23 16:23:27.321162',1,2,5,5,'VEG',244,450),(13,'2025-05-23 16:23:32.962150','Stir-fried noodles with vegetables and soy sauce',5,_binary '','Hakka Noodles',159,'2025-05-23 16:23:32.962150',1,6,0,5,'VEG',564,125),(14,'2025-05-23 16:23:41.098050','Rich and gooey chocolate dessert served warm',10,_binary '','Chocolate Brownie',99,'2025-05-23 16:23:41.098050',1,7,0,3,'NON_VEG',248,55),(15,'2025-05-23 16:23:46.612657','Fresh salad with cucumber, tomato, olives, and feta cheese',12,_binary '','Greek Salad',119,'2025-05-23 16:23:46.612657',1,8,0,4,'BOTH',54,60),(16,'2025-05-26 16:38:52.097300','Fresh salad with cucumber, tomato, olives, and feta cheese',12,_binary '','Greek Salad',110,'2025-05-26 16:38:52.097300',1,8,0,0,'VEG',23,320),(17,'2025-05-26 16:39:58.917164','Fresh salad with cucumber, tomato, olives, and feta cheese',12,_binary '','Greek Salad',110,'2025-05-26 16:39:58.917164',3,8,0,0,'NON_VEG',897,90),(18,'2025-05-28 11:32:37.957755','A delicious veggie pizza topped with fresh vegetables and mozzarella cheese.',10,_binary '','Deluxe Veggie Pizza',499,'2025-05-28 11:32:37.957755',2,5,0,0,NULL,56,60);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
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
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount` double NOT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `restaurant_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  `total_rating` int NOT NULL,
  `rating` double NOT NULL,
  `menu_type` enum('BOTH','NON_VEG','VEG') DEFAULT NULL,
  `total_orders` int NOT NULL,
  `minimum_order_value` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKww84tou7nixng06lmxawvcre` (`category_id`),
  KEY `FKblwdtxevpl4mrds8a12q0ohu6` (`restaurant_id`),
  CONSTRAINT `FKblwdtxevpl4mrds8a12q0ohu6` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `FKww84tou7nixng06lmxawvcre` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'2025-05-20 10:17:00.792701','Aromatic basmati rice cooked with tender chicken pieces and authentic spices',10,_binary '','Chicken Biryani',299,'2025-05-20 10:17:00.792701',1,3,15,4.6,'VEG',56,500),(2,'2025-05-20 10:23:04.127537','Aromatic basmati rice cooked with tender chicken pieces and authentic spices',10,_binary '','Malai Tikka',299,'2025-05-20 10:23:04.127537',1,4,15,4.6,'NON_VEG',328,200),(3,'2025-05-20 10:25:22.887116','Aromatic basmati rice cooked with tender chicken pieces and authentic spices',10,_binary '','Rose Cabab',299,'2025-05-20 10:25:22.887116',1,7,434,3,'BOTH',564,180),(4,'2025-05-20 10:26:31.940485','Aromatic basmati rice cooked with tender chicken pieces and authentic spices',10,_binary '','DAhi wade',299,'2025-05-20 10:26:31.940485',1,2,155,4,'NON_VEG',524,260),(5,'2025-05-23 12:13:57.462826','Creamy tomato gravy with soft paneer cubes, infused with Indian spices',15,_binary '','Paneer Butter Masala',249,'2025-05-23 12:13:57.462826',1,7,165,4.1,'BOTH',575,554),(6,'2025-05-23 12:15:07.538421','Stir-fried noodles with mixed vegetables and Indo-Chinese sauces',10,_binary '','Veg Hakka Noodles',199,'2025-05-23 12:15:07.538421',1,1,35,3.5,'BOTH',582,220),(7,'2025-05-23 12:15:26.915997','Spicy chickpeas served with deep-fried bread',10,_binary '','Chole Bhature',149,'2025-05-23 12:15:26.915997',1,4,15,3.2,'NON_VEG',320,560),(8,'2025-05-23 12:15:51.250192','Crispy rice crepe filled with spiced mashed potatoes',5,_binary '','Masala Dosa',129,'2025-05-23 12:15:51.250192',1,6,55,1.6,'BOTH',318,300),(9,'2025-05-23 16:22:48.702555','Classic cheese pizza with tomato sauce and mozzarella',10,_binary '','Margherita Pizza',299,'2025-05-23 16:22:48.702555',1,2,55,4.5,'VEG',21,100),(10,'2025-05-23 16:22:55.328044','Grilled paneer cubes marinated in spices',5,_binary '','Paneer Tikka',199,'2025-05-23 16:22:55.328044',1,3,568,4.3,'NON_VEG',3215,50),(11,'2025-05-23 16:23:02.663501','Crispy vegetable patty with fresh toppings and sauces',8,_binary '','Veggie Burger',149,'2025-05-23 16:23:02.663501',1,4,62,3.6,'VEG',212,210),(12,'2025-05-23 16:23:27.321162','Soft Indian flatbread topped with butter',0,_binary '','Butter Naan',49,'2025-05-23 16:23:27.321162',1,2,5,5,'VEG',244,450),(13,'2025-05-23 16:23:32.962150','Stir-fried noodles with vegetables and soy sauce',5,_binary '','Hakka Noodles',159,'2025-05-23 16:23:32.962150',1,6,0,5,'VEG',564,125),(14,'2025-05-23 16:23:41.098050','Rich and gooey chocolate dessert served warm',10,_binary '','Chocolate Brownie',99,'2025-05-23 16:23:41.098050',1,7,0,3,'NON_VEG',248,55),(15,'2025-05-23 16:23:46.612657','Fresh salad with cucumber, tomato, olives, and feta cheese',12,_binary '','Greek Salad',119,'2025-05-23 16:23:46.612657',1,8,0,4,'BOTH',54,60),(16,'2025-05-26 16:38:52.097300','Fresh salad with cucumber, tomato, olives, and feta cheese',12,_binary '','Greek Salad',110,'2025-05-26 16:38:52.097300',1,8,0,0,'VEG',23,320),(17,'2025-05-26 16:39:58.917164','Fresh salad with cucumber, tomato, olives, and feta cheese',12,_binary '','Greek Salad',110,'2025-05-26 16:39:58.917164',3,8,0,0,'NON_VEG',897,90),(18,'2025-05-28 11:32:37.957755','A delicious veggie pizza topped with fresh vegetables and mozzarella cheese.',10,_binary '','Deluxe Veggie Pizza',499,'2025-05-28 11:32:37.957755',2,5,0,0,NULL,56,60);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
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
>>>>>>> 2ae672392849595dc7c5a618b9a00634ab8f387c
