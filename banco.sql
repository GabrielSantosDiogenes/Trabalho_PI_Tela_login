-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: etec_db
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `partidas`
--

DROP TABLE IF EXISTS `partidas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partidas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `nivel` varchar(10) NOT NULL,
  `acertos` int DEFAULT '0',
  `erros` int DEFAULT '0',
  `tempo` int DEFAULT '0',
  `data_partida` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `partidas_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partidas`
--

LOCK TABLES `partidas` WRITE;
/*!40000 ALTER TABLE `partidas` DISABLE KEYS */;
INSERT INTO `partidas` VALUES (1,1,'facil',12,0,43,'2026-05-14 22:18:52');
/*!40000 ALTER TABLE `partidas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pecas`
--

DROP TABLE IF EXISTS `pecas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pecas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lado_a` varchar(100) NOT NULL,
  `lado_b` varchar(100) NOT NULL,
  `nivel` varchar(10) NOT NULL,
  `categoria` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pecas`
--

LOCK TABLES `pecas` WRITE;
/*!40000 ALTER TABLE `pecas` DISABLE KEYS */;
INSERT INTO `pecas` VALUES (1,'HCl','Ácido','facil','acido'),(2,'H2SO4','Ácido','facil','acido'),(3,'HNO3','Ácido','facil','acido'),(4,'NaOH','Base','facil','base'),(5,'Ca(OH)2','Base','facil','base'),(6,'NH4OH','Base','facil','base'),(7,'NaCl','Sal','facil','sal'),(8,'CaCO3','Sal','facil','sal'),(9,'CO2','Óxido','facil','oxido'),(10,'CaO','Óxido','facil','oxido'),(11,'Ácido Clorídrico','HCl','medio','acido'),(12,'Ácido Sulfúrico','H2SO4','medio','acido'),(13,'Ácido Nítrico','HNO3','medio','acido'),(14,'Hidróxido de Sódio','NaOH','medio','base'),(15,'Hidróxido de Cálcio','Ca(OH)2','medio','base'),(16,'Cloreto de Sódio','NaCl','medio','sal'),(17,'Carbonato de Cálcio','CaCO3','medio','sal'),(18,'Dióxido de Carbono','CO2','medio','oxido'),(19,'Óxido de Cálcio','CaO','medio','oxido'),(20,'Hidróxido de Amônio','NH4OH','medio','base'),(21,'Azeda e corrói metais','Ácido','dificil','acido'),(22,'Libera H+ em solução','Ácido','dificil','acido'),(23,'pH menor que 7','Ácido','dificil','acido'),(24,'Escorregadio ao tato','Base','dificil','base'),(25,'Libera OH- em solução','Base','dificil','base'),(26,'pH maior que 7','Base','dificil','base'),(27,'Formado por cátion e ânion','Sal','dificil','sal'),(28,'Produto de ácido + base','Sal','dificil','sal'),(29,'Formado por metal + oxigênio','Óxido','dificil','oxido'),(30,'Composto binário com oxigênio','Óxido','dificil','oxido'),(31,'Na2SO4','Sal','facil','sal'),(32,'Fe2O3','Óxido','facil','oxido');
/*!40000 ALTER TABLE `pecas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ra` varchar(20) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `turma` varchar(50) DEFAULT NULL,
  `tipo` varchar(10) DEFAULT 'aluno',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ra` (`ra`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'123456','senha123','João Silva',NULL,'aluno'),(2,'26009','214365','Gabriel Santos Diogenes','Química','aluno'),(4,'67890','67890','lucas brolezi','química','aluno');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-14 22:21:37
