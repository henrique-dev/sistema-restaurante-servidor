-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: restaurante
-- ------------------------------------------------------
-- Server version	5.7.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `arquivo`
--

DROP TABLE IF EXISTS `arquivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arquivo` (
  `id_arquivo` int(11) NOT NULL,
  `caminho` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_arquivo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arquivo`
--

LOCK TABLES `arquivo` WRITE;
/*!40000 ALTER TABLE `arquivo` DISABLE KEYS */;
INSERT INTO `arquivo` VALUES (1,NULL),(2,NULL),(3,NULL),(4,NULL),(5,NULL),(6,NULL),(7,NULL),(8,NULL),(9,NULL),(10,NULL),(11,NULL),(12,NULL),(13,NULL),(14,NULL),(15,NULL),(16,NULL),(17,NULL),(18,NULL),(19,NULL),(20,NULL),(21,NULL),(22,NULL),(23,NULL),(24,NULL),(25,NULL),(26,NULL),(27,NULL),(28,NULL),(29,NULL),(30,NULL),(31,NULL),(32,NULL),(33,NULL),(34,NULL),(35,NULL),(36,NULL),(37,NULL),(38,NULL),(39,NULL),(40,NULL),(41,NULL),(42,NULL),(43,NULL),(44,NULL),(45,NULL),(46,NULL),(47,NULL),(48,NULL);
/*!40000 ALTER TABLE `arquivo` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER base_modificada_arquivo AFTER INSERT ON arquivo FOR EACH ROW
BEGIN
UPDATE restaurante set ultima_modificacao_itens = now() WHERE id_restaurante = 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `cpf` varchar(15) NOT NULL,
  `telefone` varchar(15) NOT NULL,
  `email` varchar(150) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id_cliente`),
  KEY `fk_cliente_usuario1_idx` (`id_usuario`),
  CONSTRAINT `fk_cliente_usuario1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (0,'','','','',0),(9,'Ragerio Queiroz','02295260205','96992055072','argerio@gmail.com',28),(10,'Paulo Henrique ','01741053200','96991100443','henrique.pbgb@gmail.com',29),(11,'Renan','02782267280','96981243468','pororoca@mrfood.com',30);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente_id`
--

DROP TABLE IF EXISTS `cliente_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente_id` (
  `id` int(11) NOT NULL,
  `quantidadelinhas` int(11) DEFAULT NULL,
  `id_atual` int(11) DEFAULT NULL,
  `id_ultimo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente_id`
--

LOCK TABLES `cliente_id` WRITE;
/*!40000 ALTER TABLE `cliente_id` DISABLE KEYS */;
INSERT INTO `cliente_id` VALUES (0,1,12,0);
/*!40000 ALTER TABLE `cliente_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complemento`
--

DROP TABLE IF EXISTS `complemento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complemento` (
  `id_complemento` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `preco` decimal(8,2) NOT NULL,
  `id_arquivo` int(11) NOT NULL,
  PRIMARY KEY (`id_complemento`),
  KEY `fk_complemento_arquivo1_idx` (`id_arquivo`),
  CONSTRAINT `fk_complemento_arquivo1` FOREIGN KEY (`id_arquivo`) REFERENCES `arquivo` (`id_arquivo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complemento`
--

LOCK TABLES `complemento` WRITE;
/*!40000 ALTER TABLE `complemento` DISABLE KEYS */;
INSERT INTO `complemento` VALUES (1,'Pimenta',1.35,1),(2,'Maionese',3.60,2),(3,'Limão',3.90,3),(4,'Tomate',5.80,4),(5,'Arroz',7.90,5),(6,'Cebola frita',6.80,21),(7,'Ketchup',2.50,22),(8,'Morango',0.00,42),(9,'Maçã',0.00,43),(10,'Abacaxi',0.00,44),(11,'Laranja',0.00,45);
/*!40000 ALTER TABLE `complemento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER base_modificada_complemento AFTER INSERT ON complemento FOR EACH ROW
BEGIN
UPDATE restaurante set ultima_modificacao_itens = now() WHERE id_restaurante = 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endereco` (
  `id_endereco` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `logradouro` varchar(150) NOT NULL,
  `bairro` varchar(100) NOT NULL,
  `complemento` varchar(100) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `cidade` varchar(45) NOT NULL,
  `cep` varchar(45) NOT NULL,
  `descricao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_endereco`),
  KEY `fk_endereco_cliente1_idx` (`id_cliente`),
  CONSTRAINT `fk_endereco_cliente1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (0,0,'','','','','','','Buscar no local'),(6,9,'Rua Capitão Euclides Rodrigues','Central','De esquina','388','Santana','68925192','Minha casa'),(7,10,'Avenida Brasil (Lot F Deus)','Fonte Nova','Prox a um comercio','53','Santana','68928313','Minha casa'),(8,9,'Rua d7','Vila amazonas','Rua da delegacia','186','Santana','00000000','Casa da damiles meu amor'),(9,11,'Avenida Presidente Getúlio Vargas','Central','D','974','Macapá','68900070','Minha casa'),(10,9,'Rua Capitão Euclides Rodrigues','Central','De esquina','388','Santana','68925192','Casa');
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco_id`
--

DROP TABLE IF EXISTS `endereco_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endereco_id` (
  `id` int(11) NOT NULL,
  `quantidadelinhas` int(11) DEFAULT NULL,
  `id_atual` int(11) DEFAULT NULL,
  `id_ultimo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco_id`
--

LOCK TABLES `endereco_id` WRITE;
/*!40000 ALTER TABLE `endereco_id` DISABLE KEYS */;
INSERT INTO `endereco_id` VALUES (0,3,11,'0'),(1,NULL,NULL,NULL),(2,NULL,NULL,NULL);
/*!40000 ALTER TABLE `endereco_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrega`
--

DROP TABLE IF EXISTS `entrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entrega` (
  `id_entrega` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `efetuada` tinyint(1) NOT NULL,
  `eventualidade` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_entrega`,`id_pedido`),
  KEY `fk_entrega_pedido1_idx` (`id_pedido`),
  CONSTRAINT `fk_entrega_pedido1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrega`
--

LOCK TABLES `entrega` WRITE;
/*!40000 ALTER TABLE `entrega` DISABLE KEYS */;
INSERT INTO `entrega` VALUES (73,76,0,0),(74,77,0,0),(75,78,1,0),(76,79,1,0),(77,80,0,0),(78,81,0,0),(79,82,0,0),(80,83,0,0),(81,84,0,0),(82,85,0,0),(83,86,0,0),(84,87,0,0),(85,88,0,0),(86,89,0,0),(87,90,0,0),(88,91,0,0),(89,92,0,0),(90,93,0,0),(91,94,0,0),(92,95,0,0),(93,96,0,0),(94,97,0,0),(95,98,0,0),(96,99,0,0),(97,100,0,0),(98,101,0,0),(99,102,0,0),(100,103,0,0),(101,104,0,0),(102,105,0,0),(103,106,0,0),(104,107,0,0),(105,108,0,0),(106,109,0,0),(107,110,0,0),(108,111,0,0),(109,112,0,0),(110,113,0,0),(111,114,0,0),(112,115,0,0),(113,116,0,0),(114,117,0,0),(115,118,0,0),(116,119,0,0),(117,120,0,0),(118,121,0,0);
/*!40000 ALTER TABLE `entrega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrega_id`
--

DROP TABLE IF EXISTS `entrega_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entrega_id` (
  `id` int(11) NOT NULL,
  `quantidadelinhas` int(11) DEFAULT NULL,
  `id_atual` int(11) DEFAULT NULL,
  `id_ultimo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrega_id`
--

LOCK TABLES `entrega_id` WRITE;
/*!40000 ALTER TABLE `entrega_id` DISABLE KEYS */;
INSERT INTO `entrega_id` VALUES (0,1,119,'0');
/*!40000 ALTER TABLE `entrega_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formapagamento`
--

DROP TABLE IF EXISTS `formapagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formapagamento` (
  `id_formapagamento` int(11) NOT NULL,
  `descricao` varchar(45) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  PRIMARY KEY (`id_formapagamento`),
  KEY `fk_formapagamento_cliente1_idx` (`id_cliente`),
  CONSTRAINT `fk_formapagamento_cliente1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formapagamento`
--

LOCK TABLES `formapagamento` WRITE;
/*!40000 ALTER TABLE `formapagamento` DISABLE KEYS */;
INSERT INTO `formapagamento` VALUES (0,'A dinheiro',0),(1,'Paypal',0),(2,'Cartao Credito - PagSeguro',0);
/*!40000 ALTER TABLE `formapagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formapagamento_id`
--

DROP TABLE IF EXISTS `formapagamento_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formapagamento_id` (
  `id` int(11) NOT NULL,
  `quantidadelinhas` int(11) DEFAULT NULL,
  `id_atual` int(11) DEFAULT NULL,
  `id_ultimo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formapagamento_id`
--

LOCK TABLES `formapagamento_id` WRITE;
/*!40000 ALTER TABLE `formapagamento_id` DISABLE KEYS */;
INSERT INTO `formapagamento_id` VALUES (0,1,1,0);
/*!40000 ALTER TABLE `formapagamento_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genero`
--

DROP TABLE IF EXISTS `genero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genero` (
  `id_genero` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id_genero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genero`
--

LOCK TABLES `genero` WRITE;
/*!40000 ALTER TABLE `genero` DISABLE KEYS */;
INSERT INTO `genero` VALUES (1,'Bebidas'),(2,'Verdes'),(3,'Carnes'),(4,'Molhados'),(5,'Secos'),(6,'Doces'),(7,'Salgados');
/*!40000 ALTER TABLE `genero` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER base_modificada_genero AFTER INSERT ON genero FOR EACH ROW
BEGIN
UPDATE restaurante set ultima_modificacao_itens = now() WHERE id_restaurante = 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `gerenciador_ids`
--

DROP TABLE IF EXISTS `gerenciador_ids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gerenciador_ids` (
  `id` int(11) NOT NULL,
  `quantidadelinhas` int(11) DEFAULT NULL,
  `arquivo_atual` int(11) DEFAULT NULL,
  `arquivo_ultimo` int(11) DEFAULT NULL,
  `tipo_atual` int(11) DEFAULT NULL,
  `tipo_ultimo` int(11) DEFAULT NULL,
  `restaurante_atual` int(11) DEFAULT NULL,
  `restaurante_ultimo` int(11) DEFAULT NULL,
  `genero_atual` int(11) DEFAULT NULL,
  `genero_ultimo` int(11) DEFAULT NULL,
  `item_atual` int(11) DEFAULT NULL,
  `item_ultimo` int(11) DEFAULT NULL,
  `complemento_atual` int(11) DEFAULT NULL,
  `complemento_ultimo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gerenciador_ids`
--

LOCK TABLES `gerenciador_ids` WRITE;
/*!40000 ALTER TABLE `gerenciador_ids` DISABLE KEYS */;
INSERT INTO `gerenciador_ids` VALUES (0,1,49,0,6,0,1,0,8,0,16,0,12,0);
/*!40000 ALTER TABLE `gerenciador_ids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id_item` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `descricao` varchar(200) NOT NULL,
  `preco` decimal(8,2) NOT NULL,
  `id_genero` int(11) NOT NULL,
  `modificavel` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_item`),
  KEY `fk_prato_generoprato1_idx` (`id_genero`),
  CONSTRAINT `fk_prato_generoprato1` FOREIGN KEY (`id_genero`) REFERENCES `genero` (`id_genero`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'Vatapá','O melhor vatapá da região',12.30,7,1),(2,'Coca Cola','Coca cola bem geladinha',5.50,1,0),(3,'Pudim','Pudim bem gostosinho',5.80,6,0),(4,'Feijoada','Feijoada bem adubada',8.40,4,1),(5,'Fanta laranja 2L','Essa fanta é fanta',5.50,1,0),(6,'Pizza','Pizza itarriana',15.80,7,1),(7,'Espoca Bucho','Pra espocar o buchinho',3.50,1,0),(8,'Sushi','Variedade de sushis',16.80,5,1),(9,'Maniçoba','Maniçoba fresquinha e bem bonitinha',5.00,4,1),(10,'Peixe frito','Peixe gostosinho e bem fritinho',10.70,3,1),(11,'Galinha assada','Galinha caipira assada',15.00,3,0),(12,'Carne grelhada','Carne de boi bem grelhada. A melhor da região.',20.00,3,0),(13,'Porco assado de forno','Porquinho assadinho',18.00,3,0),(14,'Milkshake','Bem geladinho e cremosinho',10.00,1,0),(15,'Suco','Suco de varios sabores. Você pode tentar combinar.',7.00,1,0);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER base_modificada_item AFTER INSERT ON item FOR EACH ROW
BEGIN
UPDATE restaurante set ultima_modificacao_itens = now() WHERE id_restaurante = 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `item_arquivo`
--

DROP TABLE IF EXISTS `item_arquivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_arquivo` (
  `id_item` int(11) NOT NULL,
  `id_arquivo` int(11) NOT NULL,
  PRIMARY KEY (`id_item`,`id_arquivo`),
  KEY `fk_arquivo_has_prato_prato1_idx` (`id_item`),
  KEY `fk_arquivo_has_prato_arquivo1_idx` (`id_arquivo`),
  CONSTRAINT `fk_arquivo_has_prato_arquivo1` FOREIGN KEY (`id_arquivo`) REFERENCES `arquivo` (`id_arquivo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_arquivo_has_prato_prato1` FOREIGN KEY (`id_item`) REFERENCES `item` (`id_item`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_arquivo`
--

LOCK TABLES `item_arquivo` WRITE;
/*!40000 ALTER TABLE `item_arquivo` DISABLE KEYS */;
INSERT INTO `item_arquivo` VALUES (1,6),(1,7),(1,8),(2,9),(2,10),(3,12),(3,13),(4,14),(4,15),(4,16),(5,17),(6,18),(6,19),(7,20),(8,23),(8,24),(9,25),(9,26),(9,27),(10,28),(10,29),(11,30),(11,31),(11,32),(11,33),(12,34),(12,35),(12,36),(13,37),(13,38),(13,39),(14,40),(14,41),(15,46),(15,47),(15,48);
/*!40000 ALTER TABLE `item_arquivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_complemento`
--

DROP TABLE IF EXISTS `item_complemento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_complemento` (
  `id_item` int(11) NOT NULL,
  `id_complemento` int(11) NOT NULL,
  PRIMARY KEY (`id_item`,`id_complemento`),
  KEY `fk_prato_has_complemento_complemento1_idx` (`id_complemento`),
  KEY `fk_prato_has_complemento_prato1_idx` (`id_item`),
  CONSTRAINT `fk_prato_has_complemento_complemento1` FOREIGN KEY (`id_complemento`) REFERENCES `complemento` (`id_complemento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_prato_has_complemento_prato1` FOREIGN KEY (`id_item`) REFERENCES `item` (`id_item`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_complemento`
--

LOCK TABLES `item_complemento` WRITE;
/*!40000 ALTER TABLE `item_complemento` DISABLE KEYS */;
INSERT INTO `item_complemento` VALUES (1,1),(4,1),(8,1),(9,1),(10,1),(6,2),(8,2),(10,3),(4,4),(6,4),(10,4),(1,5),(4,5),(8,5),(9,5),(10,5),(8,6),(9,6),(10,6),(8,7);
/*!40000 ALTER TABLE `item_complemento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_tipo`
--

DROP TABLE IF EXISTS `item_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_tipo` (
  `id_item` int(11) NOT NULL,
  `id_tipo` int(11) NOT NULL,
  PRIMARY KEY (`id_item`,`id_tipo`),
  KEY `fk_prato_has_tipo_tipo1_idx` (`id_tipo`),
  KEY `fk_prato_has_tipo_prato1_idx` (`id_item`),
  CONSTRAINT `fk_prato_has_tipo_prato1` FOREIGN KEY (`id_item`) REFERENCES `item` (`id_item`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_prato_has_tipo_tipo1` FOREIGN KEY (`id_tipo`) REFERENCES `tipo` (`id_tipo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_tipo`
--

LOCK TABLES `item_tipo` WRITE;
/*!40000 ALTER TABLE `item_tipo` DISABLE KEYS */;
INSERT INTO `item_tipo` VALUES (1,1),(4,1),(9,1),(1,2),(9,2),(8,3),(2,4),(3,4),(5,4),(6,4),(7,4),(10,4),(11,4),(12,4),(13,4),(14,4),(15,4),(12,5),(13,5),(15,5);
/*!40000 ALTER TABLE `item_tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido` (
  `id_pedido` int(11) NOT NULL,
  `datapedido` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `itens` json NOT NULL,
  `precototal` decimal(4,2) NOT NULL,
  `id_formapagamento` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_endereco` int(11) DEFAULT NULL,
  `pagamentoefetuado` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_pedido`),
  KEY `fk_pedido_formapagamento1_idx` (`id_formapagamento`),
  KEY `fk_pedido_cliente1_idx` (`id_cliente`),
  KEY `fk_pedido_endereco1_idx` (`id_endereco`),
  CONSTRAINT `fk_pedido_cliente1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_endereco1` FOREIGN KEY (`id_endereco`) REFERENCES `endereco` (`id_endereco`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_formapagamento1` FOREIGN KEY (`id_formapagamento`) REFERENCES `formapagamento` (`id_formapagamento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (76,'2019-01-05 22:25:07','[{\"id\": 2, \"nome\": \"Coca Cola 2L\", \"preco\": 5.5, \"quantidade\": 1, \"complementos\": []}]',8.50,1,10,7,1),(77,'2019-01-06 03:32:19','[{\"id\": 2, \"nome\": \"Coca Cola 2L\", \"preco\": 5.5, \"quantidade\": 1, \"complementos\": []}, {\"id\": 6, \"nome\": \"Pizza G\", \"preco\": 15.8, \"quantidade\": 1, \"complementos\": [{\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}]}]',27.90,1,9,6,1),(78,'2019-01-06 03:38:28','[{\"id\": 6, \"nome\": \"Pizza G\", \"preco\": 15.8, \"quantidade\": 1, \"complementos\": [{\"id\": 4, \"nome\": \"Tomate\", \"preco\": 5.8}, {\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}]}, {\"id\": 2, \"nome\": \"Coca Cola 2L\", \"preco\": 5.5, \"quantidade\": 1, \"complementos\": []}]',33.70,0,9,8,1),(79,'2019-01-06 04:20:11','[{\"id\": 1, \"nome\": \"Vatapá\", \"preco\": 12.3, \"quantidade\": 1, \"complementos\": [{\"id\": 5, \"nome\": \"Arroz\", \"preco\": 7.9}, {\"id\": 1, \"nome\": \"Pimenta\", \"preco\": 1.35}]}, {\"id\": 2, \"nome\": \"Coca Cola 2L\", \"preco\": 5.5, \"quantidade\": 1, \"complementos\": []}]',30.05,1,9,6,1),(80,'2019-01-06 05:10:18','[{\"id\": 5, \"nome\": \"Fanta laranja 2L\", \"preco\": 5.5, \"quantidade\": 1, \"complementos\": []}]',8.50,1,10,7,1),(81,'2019-01-06 05:12:42','[{\"id\": 2, \"nome\": \"Coca Cola 2L\", \"preco\": 5.5, \"quantidade\": 1, \"complementos\": []}]',8.50,1,10,7,1),(82,'2019-01-06 05:26:23','[{\"id\": 5, \"nome\": \"Fanta laranja 2L\", \"preco\": 5.5, \"quantidade\": 1, \"complementos\": []}]',8.50,1,10,7,1),(83,'2019-01-06 05:30:28','[{\"id\": 7, \"nome\": \"Espoca Bucho\", \"preco\": 3.5, \"quantidade\": 1, \"complementos\": []}]',6.50,1,10,7,1),(84,'2019-01-06 15:46:25','[{\"id\": 10, \"nome\": \"Peixe frito\", \"preco\": 10.7, \"quantidade\": 1, \"complementos\": [{\"id\": 3, \"nome\": \"Limão\", \"preco\": 3.9}, {\"id\": 5, \"nome\": \"Arroz\", \"preco\": 7.9}, {\"id\": 1, \"nome\": \"Pimenta\", \"preco\": 1.35}, {\"id\": 4, \"nome\": \"Tomate\", \"preco\": 5.8}, {\"id\": 6, \"nome\": \"Cebola frita\", \"preco\": 6.8}]}]',39.45,0,10,7,1),(85,'2019-01-06 15:49:32','[{\"id\": 2, \"nome\": \"Coca Cola 2L\", \"preco\": 5.5, \"quantidade\": 1, \"complementos\": []}]',8.50,0,10,7,1),(86,'2019-01-07 22:33:55','[{\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [{\"max\": 1, \"variacoes\": [{\"id\": 2, \"nome\": \"Média\", \"ordem\": 0, \"preco\": 5}]}, {\"max\": 3, \"variacoes\": [{\"id\": 5, \"nome\": \"4 Queijos\", \"ordem\": 0, \"preco\": 5}]}], \"quantidade\": 1, \"complementos\": []}]',28.80,0,10,0,1),(87,'2019-01-08 00:03:43','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}, {\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}, {\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}, {\"id\": 4, \"nome\": \"Tomate\", \"preco\": 5.8}]}]',49.50,0,9,6,1),(88,'2019-01-08 00:50:28','[{\"id\": 14, \"nome\": \"Milkshake\", \"preco\": 10, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}, {\"id\": 8, \"nome\": \"Sushi\", \"preco\": 16.8, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 6, \"nome\": \"Cebola frita\", \"preco\": 6.8}, {\"id\": 5, \"nome\": \"Arroz\", \"preco\": 7.9}, {\"id\": 7, \"nome\": \"Ketchup\", \"preco\": 2.5}]}, {\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 4, \"nome\": \"Família\", \"ordem\": 0, \"preco\": 10}]}, {\"max\": 0, \"variacoes\": [{\"id\": 5, \"nome\": \"4 Queijos\", \"ordem\": 0, \"preco\": 5}, {\"id\": 6, \"nome\": \"Calabresa\", \"ordem\": 0, \"preco\": 2}, {\"id\": 8, \"nome\": \"Frango com catupiri\", \"ordem\": 0, \"preco\": 5}]}], \"quantidade\": 1, \"complementos\": [{\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}]}]',88.40,0,9,6,1),(89,'2019-01-08 01:58:37','[{\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}, {\"id\": 1, \"nome\": \"Vatapá\", \"preco\": 12.3, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 5, \"nome\": \"Arroz\", \"preco\": 7.9}, {\"id\": 1, \"nome\": \"Pimenta\", \"preco\": 1.35}]}, {\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',45.85,1,9,6,1),(90,'2019-01-08 04:44:12','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 2, \"complementos\": []}, {\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 4, \"nome\": \"Família\", \"ordem\": 0, \"preco\": 10}]}, {\"max\": 0, \"variacoes\": [{\"id\": 6, \"nome\": \"Calabresa\", \"ordem\": 0, \"preco\": 2}]}], \"quantidade\": 1, \"complementos\": [{\"id\": 4, \"nome\": \"Tomate\", \"preco\": 5.8}, {\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}]}, {\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 4, \"nome\": \"Família\", \"ordem\": 0, \"preco\": 10}]}, {\"max\": 0, \"variacoes\": [{\"id\": 6, \"nome\": \"Calabresa\", \"ordem\": 0, \"preco\": 2}, {\"id\": 8, \"nome\": \"Frango com catupiri\", \"ordem\": 0, \"preco\": 5}]}], \"quantidade\": 1, \"complementos\": [{\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}, {\"id\": 4, \"nome\": \"Tomate\", \"preco\": 5.8}]}]',93.40,1,9,6,1),(91,'2019-01-08 04:53:14','[{\"id\": 13, \"nome\": \"Porco assado de forno\", \"preco\": 18, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}, {\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}, {\"id\": 15, \"nome\": \"Suco\", \"preco\": 7, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 11, \"nome\": \"Laranja\", \"preco\": 0}]}]',33.50,0,9,8,1),(92,'2019-01-08 05:05:15','[{\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 4, \"nome\": \"Família\", \"ordem\": 0, \"preco\": 10}]}, {\"max\": 0, \"variacoes\": [{\"id\": 5, \"nome\": \"4 Queijos\", \"ordem\": 0, \"preco\": 5}, {\"id\": 8, \"nome\": \"Frango com catupiri\", \"ordem\": 0, \"preco\": 5}]}], \"quantidade\": 1, \"complementos\": [{\"id\": 4, \"nome\": \"Tomate\", \"preco\": 5.8}, {\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}]}, {\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}, {\"id\": 3, \"nome\": \"Pudim\", \"preco\": 5.8, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',59.50,0,9,6,1),(93,'2019-01-08 14:14:39','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}, {\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 4, \"nome\": \"Família\", \"ordem\": 0, \"preco\": 10}]}, {\"max\": 0, \"variacoes\": [{\"id\": 5, \"nome\": \"4 Queijos\", \"ordem\": 0, \"preco\": 5}, {\"id\": 6, \"nome\": \"Calabresa\", \"ordem\": 0, \"preco\": 2}, {\"id\": 8, \"nome\": \"Frango com catupiri\", \"ordem\": 0, \"preco\": 5}]}], \"quantidade\": 1, \"complementos\": [{\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}, {\"id\": 4, \"nome\": \"Tomate\", \"preco\": 5.8}]}]',55.70,0,9,6,1),(94,'2019-01-08 14:18:24','[{\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 4, \"nome\": \"Família\", \"ordem\": 0, \"preco\": 10}]}, {\"max\": 0, \"variacoes\": [{\"id\": 5, \"nome\": \"4 Queijos\", \"ordem\": 0, \"preco\": 5}, {\"id\": 6, \"nome\": \"Calabresa\", \"ordem\": 0, \"preco\": 2}, {\"id\": 8, \"nome\": \"Frango com catupiri\", \"ordem\": 0, \"preco\": 5}]}], \"quantidade\": 1, \"complementos\": [{\"id\": 4, \"nome\": \"Tomate\", \"preco\": 5.8}, {\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}]}]',50.20,1,9,6,1),(95,'2019-01-08 14:30:21','[{\"id\": 14, \"nome\": \"Milkshake\", \"preco\": 10, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}, {\"id\": 10, \"nome\": \"Peixe frito\", \"preco\": 10.7, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 1, \"nome\": \"Pimenta\", \"preco\": 1.35}, {\"id\": 5, \"nome\": \"Arroz\", \"preco\": 7.9}]}, {\"id\": 4, \"nome\": \"Feijoada\", \"preco\": 8.4, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',41.35,1,9,6,1),(96,'2019-01-08 16:23:17','[{\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 4, \"nome\": \"Família\", \"ordem\": 0, \"preco\": 10}]}, {\"max\": 0, \"variacoes\": [{\"id\": 6, \"nome\": \"Calabresa\", \"ordem\": 0, \"preco\": 2}, {\"id\": 8, \"nome\": \"Frango com catupiri\", \"ordem\": 0, \"preco\": 5}]}], \"quantidade\": 1, \"complementos\": [{\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}, {\"id\": 4, \"nome\": \"Tomate\", \"preco\": 5.8}]}, {\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',50.70,0,9,6,1),(97,'2019-01-09 02:48:50','[{\"id\": 8, \"nome\": \"Sushi\", \"preco\": 16.8, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 6, \"nome\": \"Cebola frita\", \"preco\": 6.8}, {\"id\": 7, \"nome\": \"Ketchup\", \"preco\": 2.5}, {\"id\": 1, \"nome\": \"Pimenta\", \"preco\": 1.35}, {\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}, {\"id\": 5, \"nome\": \"Arroz\", \"preco\": 7.9}]}]',41.95,0,10,7,1),(98,'2019-01-09 04:15:08','[{\"id\": 15, \"nome\": \"Suco\", \"preco\": 7, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 9, \"nome\": \"Laranja\", \"ordem\": 0, \"preco\": 0}]}], \"quantidade\": 1, \"complementos\": []}]',10.00,0,10,7,1),(99,'2019-01-09 04:16:32','[{\"id\": 15, \"nome\": \"Suco\", \"preco\": 7, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 9, \"nome\": \"Laranja\", \"ordem\": 0, \"preco\": 0}]}], \"quantidade\": 1, \"complementos\": []}]',10.00,0,9,6,1),(100,'2019-01-09 13:30:33','[{\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 4, \"nome\": \"Família\", \"ordem\": 0, \"preco\": 10}]}, {\"max\": 0, \"variacoes\": [{\"id\": 6, \"nome\": \"Calabresa\", \"ordem\": 0, \"preco\": 2}]}], \"quantidade\": 1, \"complementos\": [{\"id\": 4, \"nome\": \"Tomate\", \"preco\": 5.8}, {\"id\": 2, \"nome\": \"Maionese\", \"preco\": 3.6}]}, {\"id\": 15, \"nome\": \"Suco\", \"preco\": 7, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 9, \"nome\": \"Laranja\", \"ordem\": 0, \"preco\": 0}]}], \"quantidade\": 1, \"complementos\": []}]',47.20,0,9,6,1),(101,'2019-01-10 20:34:39','[{\"id\": 15, \"nome\": \"Suco\", \"preco\": 7, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 9, \"nome\": \"Laranja\", \"ordem\": 0, \"preco\": 0}]}], \"quantidade\": 1, \"complementos\": []}, {\"id\": 3, \"nome\": \"Pudim\", \"preco\": 5.8, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',15.80,0,9,6,1),(102,'2019-01-11 03:53:45','[{\"id\": 14, \"nome\": \"Milkshake\", \"preco\": 10, \"variacoes\": [], \"quantidade\": 2, \"complementos\": []}, {\"id\": 9, \"nome\": \"Maniçoba\", \"preco\": 5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 5, \"nome\": \"Arroz\", \"preco\": 7.9}, {\"id\": 1, \"nome\": \"Pimenta\", \"preco\": 1.35}]}]',37.25,0,9,6,1),(103,'2019-01-11 04:11:46','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 2, \"complementos\": []}, {\"id\": 4, \"nome\": \"Feijoada\", \"preco\": 8.4, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 1, \"nome\": \"Pimenta\", \"preco\": 1.35}, {\"id\": 5, \"nome\": \"Arroz\", \"preco\": 7.9}]}, {\"id\": 4, \"nome\": \"Feijoada\", \"preco\": 8.4, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 1, \"nome\": \"Pimenta\", \"preco\": 1.35}, {\"id\": 5, \"nome\": \"Arroz\", \"preco\": 7.9}]}]',46.84,0,9,8,1),(104,'2019-01-11 18:55:38','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',8.08,1,10,7,1),(105,'2019-01-11 19:04:01','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',8.50,1,9,6,1),(106,'2019-01-12 02:21:41','[{\"id\": 4, \"nome\": \"Feijoada\", \"preco\": 8.4, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 5, \"nome\": \"Arroz\", \"preco\": 7.9}, {\"id\": 1, \"nome\": \"Pimenta\", \"preco\": 1.35}]}]',20.65,0,9,0,1),(107,'2019-01-12 02:23:46','[{\"id\": 9, \"nome\": \"Maniçoba\", \"preco\": 5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',7.60,0,9,6,1),(108,'2019-01-12 02:24:51','[{\"id\": 4, \"nome\": \"Feijoada\", \"preco\": 8.4, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',11.40,0,9,6,1),(109,'2019-01-12 02:26:03','[{\"id\": 4, \"nome\": \"Feijoada\", \"preco\": 8.4, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',11.40,1,9,8,1),(110,'2019-01-12 02:27:14','[{\"id\": 9, \"nome\": \"Maniçoba\", \"preco\": 5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": [{\"id\": 1, \"nome\": \"Pimenta\", \"preco\": 1.35}, {\"id\": 6, \"nome\": \"Cebola frita\", \"preco\": 6.8}]}]',16.15,0,9,6,1),(111,'2019-01-12 03:26:35','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',8.50,0,9,6,1),(112,'2019-01-12 06:16:57','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',8.08,2,9,6,1),(113,'2019-01-12 06:21:05','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',8.08,2,9,6,1),(114,'2019-01-12 06:38:20','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',8.50,2,9,6,1),(115,'2019-01-12 16:44:34','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',8.50,0,9,6,1),(116,'2019-01-12 16:46:39','[{\"id\": 6, \"nome\": \"Pizza\", \"preco\": 15.8, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 4, \"nome\": \"Família\", \"ordem\": 0, \"preco\": 10}]}, {\"max\": 0, \"variacoes\": [{\"id\": 6, \"nome\": \"Calabresa\", \"ordem\": 0, \"preco\": 2}]}], \"quantidade\": 1, \"complementos\": []}]',29.26,1,9,6,1),(117,'2019-01-12 16:56:04','[{\"id\": 15, \"nome\": \"Suco\", \"preco\": 7, \"variacoes\": [{\"max\": 0, \"variacoes\": [{\"id\": 9, \"nome\": \"Laranja\", \"ordem\": 0, \"preco\": 0}]}], \"quantidade\": 1, \"complementos\": []}]',9.50,0,9,6,1),(118,'2019-01-12 17:09:04','[{\"id\": 3, \"nome\": \"Pudim\", \"preco\": 5.8, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',8.80,0,10,0,1),(119,'2019-01-12 18:12:47','[{\"id\": 14, \"nome\": \"Milkshake\", \"preco\": 10, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',13.00,1,9,10,1),(120,'2019-01-12 19:26:28','[{\"id\": 8, \"nome\": \"Sushi\", \"preco\": 16.8, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',18.81,0,10,7,1),(121,'2019-01-14 22:10:48','[{\"id\": 2, \"nome\": \"Coca Cola\", \"preco\": 5.5, \"variacoes\": [], \"quantidade\": 1, \"complementos\": []}]',8.50,1,9,10,1);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER inserir_entrega AFTER INSERT ON pedido FOR EACH ROW
BEGIN
CALL utils_alocar_index_entrega(true, @index_atual);
INSERT INTO entrega VALUES (@index_atual, new.id_pedido, false, false);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `pedido_id`
--

DROP TABLE IF EXISTS `pedido_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido_id` (
  `id` int(11) NOT NULL,
  `quantidadelinhas` int(11) DEFAULT NULL,
  `id_atual` int(11) DEFAULT NULL,
  `id_ultimo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido_id`
--

LOCK TABLES `pedido_id` WRITE;
/*!40000 ALTER TABLE `pedido_id` DISABLE KEYS */;
INSERT INTO `pedido_id` VALUES (0,1,122,0);
/*!40000 ALTER TABLE `pedido_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pre_pedido`
--

DROP TABLE IF EXISTS `pre_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pre_pedido` (
  `id_pre_pedido` int(11) NOT NULL,
  `datapedido` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `itens` json NOT NULL,
  `precototal` decimal(8,2) NOT NULL,
  `id_formapagamento` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_endereco` int(11) NOT NULL,
  `token` varchar(128) DEFAULT NULL,
  `pagamentoEfetuado` tinyint(1) NOT NULL DEFAULT '0',
  `pagamentoRecusado` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_pre_pedido`),
  KEY `fk_pre_pedido_endereco1_idx` (`id_endereco`),
  KEY `fk_pre_pedido_formapagamento1_idx` (`id_formapagamento`),
  KEY `fk_pre_pedido_cliente1_idx` (`id_cliente`),
  CONSTRAINT `fk_pre_pedido_cliente1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pre_pedido_endereco1` FOREIGN KEY (`id_endereco`) REFERENCES `endereco` (`id_endereco`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pre_pedido_formapagamento1` FOREIGN KEY (`id_formapagamento`) REFERENCES `formapagamento` (`id_formapagamento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pre_pedido`
--

LOCK TABLES `pre_pedido` WRITE;
/*!40000 ALTER TABLE `pre_pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `pre_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pre_pedido_id`
--

DROP TABLE IF EXISTS `pre_pedido_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pre_pedido_id` (
  `id` int(11) NOT NULL,
  `quantidadelinhas` int(11) DEFAULT NULL,
  `id_atual` int(11) DEFAULT NULL,
  `id_ultimo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pre_pedido_id`
--

LOCK TABLES `pre_pedido_id` WRITE;
/*!40000 ALTER TABLE `pre_pedido_id` DISABLE KEYS */;
INSERT INTO `pre_pedido_id` VALUES (0,3,17,2),(1,NULL,16,NULL),(2,NULL,13,NULL);
/*!40000 ALTER TABLE `pre_pedido_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurante`
--

DROP TABLE IF EXISTS `restaurante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurante` (
  `id_restaurante` int(11) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `endereco` varchar(150) NOT NULL,
  `ultima_modificacao_itens` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_restaurante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurante`
--

LOCK TABLES `restaurante` WRITE;
/*!40000 ALTER TABLE `restaurante` DISABLE KEYS */;
INSERT INTO `restaurante` VALUES (1,'MRFOOD','AVENIDA 32','2019-01-09 03:42:49');
/*!40000 ALTER TABLE `restaurante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo`
--

DROP TABLE IF EXISTS `tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo` (
  `id_tipo` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id_tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo`
--

LOCK TABLES `tipo` WRITE;
/*!40000 ALTER TABLE `tipo` DISABLE KEYS */;
INSERT INTO `tipo` VALUES (1,'Típicas'),(2,'Regionais'),(3,'Japonesa'),(4,'Tradicional'),(5,'Especialidade');
/*!40000 ALTER TABLE `tipo` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER base_modificada_tipo AFTER INSERT ON tipo FOR EACH ROW
BEGIN
UPDATE restaurante set ultima_modificacao_itens = now() WHERE id_restaurante = 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `senha` varchar(150) NOT NULL,
  `token_sessao` varchar(150) DEFAULT NULL,
  `token_cadastro` varchar(10) DEFAULT NULL,
  `ativo` tinyint(1) NOT NULL DEFAULT '0',
  `verificado` tinyint(1) DEFAULT '0',
  `token_websocket` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nome_UNIQUE` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (0,'','',NULL,NULL,0,0,NULL),(28,'96992055072','12345','2DDF7EF3D9B0C7E5E25A010069F6BE54','81438C',1,1,'BB8A4B66134A7A3D121AFB8CC150F49D'),(29,'96991100443','root',NULL,'E1ABEC',1,1,'3386F97C980A7196F7F58FE06B1ED6B8'),(30,'96981243468','12345678','AD72835DAC40448A68A3749F87CA2E4E','104509',1,1,NULL),(31,'96991126476','',NULL,'89FE1B',0,0,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_id`
--

DROP TABLE IF EXISTS `usuario_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_id` (
  `id` int(11) NOT NULL,
  `quantidadelinhas` int(11) DEFAULT NULL,
  `id_atual` int(11) DEFAULT NULL,
  `id_ultimo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_id`
--

LOCK TABLES `usuario_id` WRITE;
/*!40000 ALTER TABLE `usuario_id` DISABLE KEYS */;
INSERT INTO `usuario_id` VALUES (0,1,32,0);
/*!40000 ALTER TABLE `usuario_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variacao`
--

DROP TABLE IF EXISTS `variacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `variacao` (
  `id_variacao` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `preco` decimal(8,2) NOT NULL,
  `id_item` int(11) NOT NULL,
  `grupo` int(11) NOT NULL,
  `max` int(11) NOT NULL,
  `ordem` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_variacao`),
  KEY `fk_variacao_item1_idx` (`id_item`),
  CONSTRAINT `fk_variacao_item1` FOREIGN KEY (`id_item`) REFERENCES `item` (`id_item`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variacao`
--

LOCK TABLES `variacao` WRITE;
/*!40000 ALTER TABLE `variacao` DISABLE KEYS */;
INSERT INTO `variacao` VALUES (1,'Pequena',0.00,6,0,1,1),(2,'Média',5.00,6,0,1,2),(3,'Grande',10.00,6,0,1,3),(4,'Família',10.00,6,0,1,4),(5,'4 Queijos',5.00,6,1,3,NULL),(6,'Calabresa',2.00,6,1,3,NULL),(7,'Frango',2.00,6,1,3,NULL),(8,'Frango com catupiri',5.00,6,1,3,NULL),(9,'Laranja',0.00,15,0,1,NULL),(10,'Maracujá',0.00,15,0,1,NULL),(11,'Abacate',0.00,15,0,1,NULL),(12,'Acerola',0.00,15,0,1,NULL),(13,'Tapereba',0.00,15,0,1,NULL),(14,'Uva',0.00,15,0,1,NULL),(15,'Graviola',0.00,15,0,1,NULL);
/*!40000 ALTER TABLE `variacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'restaurante'
--
/*!50003 DROP PROCEDURE IF EXISTS `atualizar_tokem_pre_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `atualizar_tokem_pre_pedido`(IN idpagamento VARCHAR(128), IN idcomprador VARCHAR(128))
BEGIN
SELECT id_pre_pedido FROM pre_pedido WHERE pre_pedido.token = idpagamento INTO @index_prepedido;
    IF (@index_prepedido IS NOT NULL) THEN
UPDATE pre_pedido SET token = idcomprador WHERE id_pre_pedido = @index_prepedido;
ELSE
SET @erro = 'Falha ao executar pagamento';
END IF;
    SELECT @erro erro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `existe_pre_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `existe_pre_pedido`(IN idcliente INT)
BEGIN
SELECT id_pre_pedido FROM pre_pedido WHERE id_cliente = idcliente;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_arquivo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_arquivo`(IN id_arquivo INT)
BEGIN
SELECT arquivo.caminho FROM arquivo WHERE arquivo.id_arquivo = id_arquivo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_data_ultima_alteracao_base` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_data_ultima_alteracao_base`(IN id INT)
BEGIN
SELECT 
restaurante.ultima_modificacao_itens
FROM restaurante
    WHERE restaurante.id_restaurante = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_endereco` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_endereco`(IN idcliente INT, IN idendereco INT)
BEGIN
SELECT
id_endereco,
        logradouro,
        bairro,
        complemento,
        numero,
        cidade,
        cep,
        descricao
FROM endereco WHERE endereco.id_cliente = idcliente AND endereco.id_endereco = idendereco;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_item` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_item`(IN id_item INT)
BEGIN
SELECT item.id_item, 
item.nome,            
            item.descricao,
            item.preco, 
            genero.id_genero, 
            genero.nome genero, 
            item.modificavel, 
            tipo.id_tipo, 
            tipo.nome tipo_nome
FROM item 
LEFT JOIN genero ON item.id_genero = genero.id_genero
LEFT JOIN item_tipo ON item.id_item = item_tipo.id_item
LEFT JOIN tipo ON item_tipo.id_tipo = tipo.id_tipo 
WHERE item.id_item = id_item
ORDER BY tipo.id_tipo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_arquivos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_arquivos`(IN id INT)
BEGIN
SELECT
arquivo.id_arquivo
FROM arquivo
LEFT JOIN item_arquivo ON arquivo.id_arquivo = item_arquivo.id_arquivo
        LEFT JOIN item ON item_arquivo.id_item = item.id_item
WHERE item.id_item = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_complementos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_complementos`()
BEGIN
SELECT 
id_complemento,
nome,        
        preco,
        arquivo.id_arquivo
FROM  complemento
    JOIN arquivo ON complemento.id_arquivo = arquivo.id_arquivo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_complementos_basico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_complementos_basico`()
BEGIN
SELECT
id_complemento,
        nome,
        preco
FROM complemento ORDER BY id_complemento;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_complementos_item` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_complementos_item`(IN id INT)
BEGIN
SELECT
complemento.id_complemento,
        complemento.nome,
        complemento.preco,
        complemento.id_arquivo
FROM
complemento
LEFT JOIN item_complemento ON complemento.id_complemento = item_complemento.id_complemento
    WHERE item_complemento.id_item = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_enderecos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_enderecos`(IN id_cliente INT)
BEGIN
SELECT
id_endereco,
        logradouro,
        bairro,
        complemento,
        numero,
        cidade,
        cep,
        descricao
FROM endereco WHERE endereco.id_cliente = id_cliente;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_formaspagamento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_formaspagamento`(IN id_cliente INT)
BEGIN
SELECT 
id_formapagamento,
        descricao 
FROM formapagamento WHERE formapagamento.id_cliente = id_cliente;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_generos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_generos`()
BEGIN
SELECT genero.id_genero, genero.nome FROM genero;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_itens` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_itens`()
BEGIN
SELECT 
item.id_item, 
item.nome,            
        item.preco, 
        item.descricao,
        genero.id_genero, 
        genero.nome genero, 
        item.modificavel,
        tipo.id_tipo, 
        tipo.nome tipo_nome        
FROM item 
LEFT JOIN genero ON item.id_genero = genero.id_genero
        LEFT JOIN item_tipo ON item.id_item = item_tipo.id_item
        LEFT JOIN tipo ON item_tipo.id_tipo = tipo.id_tipo        
order by item.id_item, genero.nome, tipo.id_tipo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_itens_basico` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_itens_basico`()
BEGIN
SELECT
item.id_item,
        item.nome,
        item.preco,
        item.modificavel,
        (select id_arquivo from item_arquivo where item_arquivo.id_item = item.id_item limit 1) id_arquivo
FROM item     
    ORDER BY item.id_item;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_pedidos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_pedidos`(IN idcliente INT)
BEGIN
SELECT
pedido.id_pedido,
        pedido.datapedido,
        pedido.precototal,
        pedido.id_endereco,
        formapagamento.descricao formapagamento_descricao,
        endereco.descricao endereco_descricao,
        entrega.efetuada entregue
FROM pedido
    LEFT JOIN formapagamento ON pedido.id_formapagamento = formapagamento.id_formapagamento
    LEFT JOIN endereco ON pedido.id_endereco = endereco.id_endereco
    LEFT JOIN entrega ON pedido.id_pedido = entrega.id_pedido
    WHERE pedido.id_cliente = idcliente
    ORDER BY pedido.datapedido DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_tipos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_tipos`()
BEGIN
SELECT tipo.id_tipo, tipo.nome FROM tipo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_variacoes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_variacoes`()
BEGIN
SELECT * FROM variacao ORDER BY id_item, grupo, ordem;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_lista_variacoes_item` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_lista_variacoes_item`(IN iditem INT)
BEGIN
SELECT * FROM variacao WHERE id_item = iditem ORDER BY grupo, ordem ASC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_pedido`(IN idcliente INT, IN idpedido INT)
BEGIN
SELECT 
        datapedido,
        itens,
        precototal,
        formapagamento.descricao formapagamento_descricao,
        endereco.descricao endereco_descricao
FROM pedido
    LEFT JOIN formapagamento ON pedido.id_formapagamento = formapagamento.id_formapagamento
    LEFT JOIN endereco ON pedido.id_endereco = endereco.id_endereco
    WHERE pedido.id_cliente = idcliente AND pedido.id_pedido = idpedido;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_perfil_cliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_perfil_cliente`(IN id INT)
BEGIN
SELECT
nome,
        cpf,
        telefone,
        email
FROM cliente
    WHERE cliente.id_cliente = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_preco_complemento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_preco_complemento`(IN lista_complementos VARCHAR(100))
BEGIN
SET @sql = concat("select id_complemento, preco from complemento where id_complemento in (", lista_complementos, ");");
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_preco_item` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_preco_item`(IN lista_itens VARCHAR(100))
BEGIN
SET @sql = concat("select id_item, preco from item where id_item in (", lista_itens, ");");
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_arquivo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_arquivo`()
BEGIN
CALL utils_alocar_index('arquivo', true, @index_atual);
    INSERT INTO arquivo (id_arquivo) VALUES (@index_atual);
    SELECT @index_atual id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_cliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_cliente`(IN nome VARCHAR(150), IN cpf VARCHAR(15), IN telefone VARCHAR(15), 
IN email VARCHAR(150), IN senha VARCHAR(150), IN token VARCHAR(10))
BEGIN     
SET @index_atual_usuario = (SELECT id_usuario FROM usuario WHERE usuario.nome = telefone 
AND usuario.token_cadastro = token AND usuario.verificado = TRUE AND usuario.ativo = FALSE);
    IF (@index_atual_usuario) THEN
UPDATE usuario SET usuario.senha = senha, usuario.ativo = true WHERE usuario.id_usuario = @index_atual_usuario;
CALL utils_alocar_index_cliente(true, @index_atual_cliente);
INSERT INTO cliente VALUES (@index_atual_cliente, nome, cpf, telefone, email, @index_atual_usuario);
ELSE
SET @erro = 'Não foi possível realizar o cadastro';
END IF;
SELECT @index_atual_cliente id, @erro erro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_complemento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_complemento`(IN nome VARCHAR(45), IN preco NUMERIC(4,2), IN id_arquivo INT)
BEGIN
CALL utils_alocar_index('complemento', true, @index_atual);
    INSERT INTO complemento VALUES (@index_atual, nome, preco, id_arquivo);
    SELECT @index_atual id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_endereco` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_endereco`(IN idcliente INT, IN logradouro VARCHAR(150), IN bairro VARCHAR(100), IN complemento VARCHAR(100),
IN numero VARCHAR(10), IN cidade VARCHAR(45), IN cep VARCHAR(45), IN descricao VARCHAR(45))
BEGIN 
    CALL utils_alocar_index_endereco(true, @index_atual_endereco);
    INSERT INTO endereco VALUES (@index_atual_endereco, idcliente, logradouro, bairro, complemento, numero, cidade, cep, descricao);
    SELECT @index_atual_endereco id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_genero` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_genero`(IN nome VARCHAR(45))
BEGIN
CALL utils_alocar_index('genero', true, @index_atual);
    INSERT INTO genero VALUES (@index_atual, nome);
    SELECT @index_atual id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_item` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_item`(IN nome VARCHAR(45), IN preco NUMERIC(4,2), IN descricao VARCHAR(200), IN id_genero INT, IN modificavel BOOLEAN)
BEGIN
CALL utils_alocar_index('item', true, @index_atual);
    INSERT INTO item VALUES (@index_atual, nome, descricao, preco, id_genero, modificavel);
SELECT @index_atual id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_item_arquivo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_item_arquivo`(IN id_item INT, IN id_arquivo INT)
BEGIN
INSERT INTO item_arquivo VALUES (id_item, id_arquivo);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_item_complemento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_item_complemento`(IN id_item INT, IN id_complemento INT)
BEGIN
INSERT INTO item_complemento VALUES (id_item, id_complemento);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_item_tipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_item_tipo`(IN id_item INT, IN id_tipo INT)
BEGIN
INSERT INTO item_tipo VALUES (id_item, id_tipo);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_pedido`(IN data_pedido TIMESTAMP, IN precototal DECIMAL(4,2), IN id_formapagamento INT, 
IN id_cliente INT, IN id_endereco INT, IN itens JSON)
BEGIN
CALL utils_alocar_index_pedido(true, @index_atual);
INSERT INTO pedido VALUES (@index_atual, now(), itens, precototal, id_formapagamento, id_cliente, id_endereco, true);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_pedido_de_pre_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_pedido_de_pre_pedido`(IN token VARCHAR(128))
BEGIN
SELECT pre_pedido.id_cliente, pre_pedido.id_pre_pedido FROM pre_pedido WHERE pre_pedido.token = token INTO @idcliente, @idprepedido;
IF (@idcliente IS NOT NULL) THEN
CALL utils_alocar_index_pedido(true, @index_atual);
INSERT INTO pedido 
SELECT 
@index_atual, 
                datapedido, 
                itens, 
                precototal, 
                id_formapagamento, 
                id_cliente, 
                id_endereco,
                true
FROM pre_pedido 
            WHERE pre_pedido.id_cliente = @idcliente;
            CALL remover_pre_pedido(@idprepedido);
ELSE
SET @erro = 'Pre-pedido não encontrado';
END IF;
SELECT @erro erro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_pre_cadastro_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_pre_cadastro_usuario`(IN nome VARCHAR(150), IN token VARCHAR(10))
BEGIN
    SET @existe = (SELECT COUNT(id_usuario) FROM usuario WHERE usuario.nome = nome);
    IF (!@existe) THEN    
CALL utils_alocar_index_usuario(true, @index_atual);
INSERT INTO usuario VALUES (@index_atual, nome, "", null, token, 0, 0, null);
ELSE
        SET @ativo = (SELECT usuario.ativo FROM usuario WHERE usuario.nome = nome);
        IF (@ativo) THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Este número de telefone já se encontra cadastrado';
ELSE
SIGNAL SQLSTATE '45001'
SET MESSAGE_TEXT = 'Este número de telefone está em pré-cadastro e ainda não foi ativado';
END IF;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_pre_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_pre_pedido`(IN data_pedido TIMESTAMP, IN precototal DECIMAL(4,2), IN id_formapagamento INT, 
IN idcliente INT, IN id_endereco INT, IN itens JSON, IN token VARCHAR(128))
BEGIN
SELECT pre_pedido.id_cliente FROM pre_pedido WHERE pre_pedido.id_cliente = idcliente INTO @index_prepedido;
    IF (@index_prepedido IS NULL) THEN
CALL utils_alocar_index_pre_pedido(true, @index_atual);
INSERT INTO pre_pedido values (@index_atual, now(), itens, precototal, id_formapagamento, idcliente, id_endereco, token, 0, 0);
ELSE
SET @erro = 'Existe um pre pedido em ação';
END IF;
    SELECT @erro erro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inserir_tipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_tipo`(IN nome VARCHAR(45))
BEGIN
CALL utils_alocar_index('tipo', true, @index_atual);
    INSERT INTO tipo VALUES (@index_atual, nome);
    SELECT @index_atual id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `invalidar_pre_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `invalidar_pre_pedido`(IN idcliente INT)
BEGIN
SELECT id_pre_pedido FROM pre_pedido WHERE id_cliente = idcliente INTO @index_prepedido;
    IF (@index_prepedido IS NOT NULL) THEN
DELETE FROM pre_pedido WHERE id_pre_pedido = @index_prepedido;
        CALL utils_desalocar_index_pre_pedido(@index_prepedido);
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `recuperar_pre_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `recuperar_pre_pedido`(IN idcliente INT)
BEGIN
SELECT
itens,
        precototal
FROM pre_pedido WHERE id_cliente = idcliente;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `remover_arquivo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `remover_arquivo`(IN id INT)
BEGIN
SET @existe = (SELECT COUNT(id_arquivo) FROM arquivo WHERE id_arquivo = id);
    IF (@existe) THEN
DELETE FROM arquivo WHERE id_arquivo = id;
        CALL utils_desalocar_index('arquivo', id);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `remover_complemento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `remover_complemento`(IN id INT)
BEGIN
SET @existe = (SELECT COUNT(id_complemento) FROM complemento WHERE id_complemento = id);
    IF (@existe) THEN
DELETE FROM complemento WHERE id_complemento = id;
        CALL utils_desalocar_index('complemento', id);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `remover_endereco` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `remover_endereco`(IN idcliente INT, IN idendereco INT)
BEGIN
SELECT COUNT(id_endereco) FROM endereco WHERE endereco.id_endereco = idendereco AND endereco.id_cliente = idcliente INTO @existe;
    IF (@existe IS NOT NULL) THEN
UPDATE pedido SET id_endereco = null WHERE pedido.id_endereco = idendereco;
DELETE FROM endereco WHERE id_endereco = idendereco;
        CALL utils_desalocar_index_endereco(idendereco);
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `remover_genero` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `remover_genero`(IN id INT)
BEGIN 
SET @existe = (SELECT COUNT(id_genero) FROM genero WHERE id_genero = id);
    IF (@existe) THEN
DELETE FROM genero WHERE id_genero = id;
        CALL utils_desalocar_index('genero', id);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `remover_pre_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `remover_pre_pedido`(IN idprepedido INT)
BEGIN
SELECT COUNT(id_cliente) FROM pre_pedido WHERE id_pre_pedido = idprepedido INTO @index_pedido;
    IF (@index_pedido IS NOT NULL) THEN
DELETE FROM pre_pedido WHERE id_pre_pedido = idprepedido;
        CALL utils_desalocar_index_pre_pedido(idprepedido);
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `remover_tipo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `remover_tipo`(IN id INT)
BEGIN 
SET @existe = (SELECT COUNT(id_tipo) FROM tipo WHERE id_tipo = id);
    IF (@existe) THEN
DELETE FROM tipo WHERE id_tipo = id;
        CALL utils_desalocar_index('tipo', id);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_alocar_index` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_alocar_index`(IN tabela VARCHAR(100), IN atualizar BOOLEAN, OUT proximo_index INT)
BEGIN 
SET @sql = concat("SELECT " , tabela , "_atual " , tabela , " from gerenciador_ids WHERE id=(SELECT " ,
tabela , "_ultimo FROM gerenciador_ids WHERE id=0) INTO @index_atual");
PREPARE stmt FROM @sql;
    EXECUTE stmt;    
    IF (atualizar) THEN
SET @sql = concat("SELECT " , tabela , "_ultimo FROM gerenciador_ids WHERE id=0 INTO @index_ultimo");
PREPARE stmt FROM @sql;    
EXECUTE stmt;
IF (@index_ultimo = 0) THEN
SET @sql = concat("UPDATE gerenciador_ids SET " , tabela , "_atual=" , (@index_atual+1) , " WHERE id=0");
PREPARE stmt FROM @sql;
EXECUTE stmt;
ELSE
SET @sql = concat("UPDATE gerenciador_ids SET " , tabela , "_atual=NULL WHERE id=?");
PREPARE stmt FROM @sql;
EXECUTE stmt USING @index_ultimo;
SET @sql = concat("UPDATE gerenciador_ids SET " , tabela , "_ultimo=" , (@index_ultimo-1) , " WHERE id=0");
PREPARE stmt FROM @sql;
EXECUTE stmt;
END IF;
END IF;    
    DEALLOCATE PREPARE stmt;
    SET proximo_index = @index_atual;    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_alocar_index_cliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_alocar_index_cliente`(IN atualizar BOOLEAN, OUT proximo_index INT)
BEGIN 
SET @index_atual = (SELECT id_atual FROM cliente_id WHERE id = (SELECT id_ultimo FROM cliente_id WHERE id = 0));
    IF (atualizar) THEN
SET @index_ultimo = (SELECT id_ultimo FROM cliente_id WHERE id = 0);
IF (@index_ultimo = 0) THEN
UPDATE cliente_id SET id_atual = (@index_atual + 1) WHERE id = 0;
ELSE
UPDATE cliente_id SET id_atual = null WHERE id = @index_ultimo;
UPDATE cliente_id SET id_ultimo = (@index_ultimo - 1) WHERE id = 0;
END IF;
END IF;
    SET proximo_index = @index_atual;        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_alocar_index_endereco` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_alocar_index_endereco`(IN atualizar BOOLEAN, OUT proximo_index INT)
BEGIN 
SET @index_atual = (SELECT id_atual FROM endereco_id WHERE id = (SELECT id_ultimo FROM endereco_id WHERE id = 0));
    IF (atualizar) THEN
SET @index_ultimo = (SELECT id_ultimo FROM endereco_id WHERE id = 0);
IF (@index_ultimo = 0) THEN
UPDATE endereco_id SET id_atual = (@index_atual + 1) WHERE id = 0;
ELSE
UPDATE endereco_id SET id_atual = null WHERE id = @index_ultimo;
UPDATE endereco_id SET id_ultimo = (@index_ultimo - 1) WHERE id = 0;
END IF;
END IF;
    SET proximo_index = @index_atual;        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_alocar_index_entrega` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_alocar_index_entrega`(IN atualizar BOOLEAN, OUT proximo_index INT)
BEGIN 
SET @index_atual = (SELECT id_atual FROM entrega_id WHERE id = (SELECT id_ultimo FROM entrega_id WHERE id = 0));
    IF (atualizar) THEN
SET @index_ultimo = (SELECT id_ultimo FROM entrega_id WHERE id = 0);
IF (@index_ultimo = 0) THEN
UPDATE entrega_id SET id_atual = (@index_atual + 1) WHERE id = 0;
ELSE
UPDATE entrega_id SET id_atual = null WHERE id = @index_ultimo;
UPDATE entrega_id SET id_ultimo = (@index_ultimo - 1) WHERE id = 0;
END IF;
END IF;
    SET proximo_index = @index_atual;        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_alocar_index_formapagamento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_alocar_index_formapagamento`(IN atualizar BOOLEAN, OUT proximo_index INT)
BEGIN 
SET @index_atual = (SELECT id_atual FROM formapagamento_id WHERE id = (SELECT id_ultimo FROM formapagamento_id WHERE id = 0));
    IF (atualizar) THEN
SET @index_ultimo = (SELECT id_ultimo FROM formapagamento_id WHERE id = 0);
IF (@index_ultimo = 0) THEN
UPDATE formapagamento_id SET id_atual = (@index_atual + 1) WHERE id = 0;
ELSE
UPDATE formapagamento_id SET id_atual = null WHERE id = @index_ultimo;
UPDATE formapagamento_id SET id_ultimo = (@index_ultimo - 1) WHERE id = 0;
END IF;
END IF;
    SET proximo_index = @index_atual;    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_alocar_index_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_alocar_index_pedido`(IN atualizar BOOLEAN, OUT proximo_index INT)
BEGIN 
SET @index_atual = (SELECT id_atual FROM pedido_id WHERE id = (SELECT id_ultimo FROM pedido_id WHERE id = 0));
    IF (atualizar) THEN
SET @index_ultimo = (SELECT id_ultimo FROM pedido_id WHERE id = 0);
IF (@index_ultimo = 0) THEN
UPDATE pedido_id SET id_atual = (@index_atual + 1) WHERE id = 0;
ELSE
UPDATE pedido_id SET id_atual = null WHERE id = @index_ultimo;
UPDATE pedido_id SET id_ultimo = (@index_ultimo - 1) WHERE id = 0;
END IF;
END IF;
    SET proximo_index = @index_atual;        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_alocar_index_pre_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_alocar_index_pre_pedido`(IN atualizar BOOLEAN, OUT proximo_index INT)
BEGIN 
SET @index_atual = (SELECT id_atual FROM pre_pedido_id WHERE id = (SELECT id_ultimo FROM pre_pedido_id WHERE id = 0));
    IF (atualizar) THEN
SET @index_ultimo = (SELECT id_ultimo FROM pre_pedido_id WHERE id = 0);
IF (@index_ultimo = 0) THEN
UPDATE pre_pedido_id SET id_atual = (@index_atual + 1) WHERE id = 0;
ELSE
UPDATE pre_pedido_id SET id_atual = null WHERE id = @index_ultimo;
UPDATE pre_pedido_id SET id_ultimo = (@index_ultimo - 1) WHERE id = 0;
END IF;
END IF;
    SET proximo_index = @index_atual;        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_alocar_index_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_alocar_index_usuario`(IN atualizar BOOLEAN, OUT proximo_index INT)
BEGIN 
SET @index_atual = (SELECT id_atual FROM usuario_id WHERE id = (SELECT id_ultimo FROM usuario_id WHERE id = 0));
    IF (atualizar) THEN
SET @index_ultimo = (SELECT id_ultimo FROM usuario_id WHERE id = 0);
IF (@index_ultimo = 0) THEN
UPDATE usuario_id SET id_atual = (@index_atual + 1) WHERE id = 0;
ELSE
UPDATE usuario_id SET id_atual = null WHERE id = @index_ultimo;
UPDATE usuario_id SET id_ultimo = (@index_ultimo - 1) WHERE id = 0;
END IF;
END IF;
    SET proximo_index = @index_atual;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_autenticar` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_autenticar`(IN nome VARCHAR(150), IN senha VARCHAR(150))
BEGIN
DECLARE idusuario INT;
    DECLARE erro VARCHAR(20);
    SET idusuario = (select usuario.id_usuario from usuario where usuario.nome = nome and usuario.senha = senha and usuario.ativo = true);
IF (idusuario IS NOT NULL) THEN
SELECT 
cliente.id_usuario, 
            cliente.id_cliente, 
            cliente.nome, 
            cliente.cpf, 
            cliente.telefone, 
            cliente.email, 
            erro
FROM cliente         
        WHERE cliente.id_usuario = idusuario; 
ELSE
SET erro = 'O USUÁRIO NÃO EXISTE';
SELECT erro;
END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_desalocar_index` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_desalocar_index`(IN tabela VARCHAR(100), IN index_para_remover INT)
BEGIN
SET @sql = CONCAT("SELECT " , tabela , "_atual FROM gerenciador_ids WHERE id=0 INTO @proximo_index");
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    IF (index_para_remover >= @proximo_index OR index_para_remover <= 0) THEN
SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Este index ainda não existe e logo não está alocado';
ELSE
SET @sql = CONCAT("SELECT COUNT(id) FROM gerenciador_ids WHERE " , tabela , "_atual=? INTO @existe");
        PREPARE stmt FROM @sql;
        SET @index_para_remover = index_para_remover;
        EXECUTE stmt USING @index_para_remover;        
        IF (@existe) THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Este index já existe mas não está alocado';
ELSE
SET @quantidade_linhas = (SELECT quantidadelinhas FROM gerenciador_ids WHERE id=0);
            SET @sql = CONCAT("SELECT " , tabela , "_ultimo FROM gerenciador_ids WHERE id=0 INTO @index_ultimo");            
            PREPARE stmt FROM @sql;
            EXECUTE stmt;            
            IF (@index_ultimo = (@quantidade_linhas - 1))  THEN
SET @sql = CONCAT("INSERT INTO gerenciador_ids (id, " , tabela , "_atual) values (?,?); ");
                PREPARE stmt FROM @sql;
                EXECUTE stmt USING @quantidade_linhas, @index_para_remover;                
                SET @sql = CONCAT("UPDATE gerenciador_ids SET quantidadelinhas=" , (@quantidade_linhas + 1), ", " , tabela , "_ultimo=? WHERE id=0");                
                PREPARE stmt FROM @sql;                
                EXECUTE stmt USING @quantidade_linhas;                
ELSE
SET @sql = CONCAT("UPDATE gerenciador_ids SET " , tabela , "_atual=? WHERE id=" , (@index_ultimo+1));
                PREPARE stmt FROM @sql;
                EXECUTE stmt USING @index_para_remover;                
                SET @sql = CONCAT("UPDATE gerenciador_ids SET " , tabela , "_ultimo=" , (@index_ultimo+1) , " WHERE id=0");
                PREPARE stmt FROM @sql;
                EXECUTE stmt;
END IF;
        END IF;
END IF;
    DEALLOCATE PREPARE stmt;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_desalocar_index_endereco` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_desalocar_index_endereco`(IN index_para_remover INT)
BEGIN
SET @proximo_index = (SELECT id_atual FROM endereco_id WHERE id = 0);
    IF (index_para_remover >= @proximo_index OR index_para_remover <= 0) THEN
SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Este index ainda não existe e logo não está alocado';
ELSE
SET @existe = (SELECT COUNT(id) FROM endereco_id WHERE id_atual = index_para_remover);
        IF (@existe) THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Este index já existe mas não está alocado';
ELSE
SET @quantidade_linhas = (SELECT quantidadelinhas FROM endereco_id WHERE id=0);
            SET @index_ultimo = (SELECT id_ultimo FROM endereco_id WHERE id = 0);            
            IF (@index_ultimo = (@quantidade_linhas - 1))  THEN
INSERT INTO endereco_id (id, id_atual) values (@quantidade_linhas, index_para_remover);
UPDATE endereco_id SET quantidadelinhas = (@quantidade_linhas + 1), id_ultimo = @quantidade_linhas WHERE id = 0;                
ELSE
UPDATE endereco_id SET id_atual = index_para_remover WHERE id = (@index_ultimo + 1);
UPDATE endereco_id SET id_ultimo = (@index_ultimo + 1) WHERE id = 0;
END IF;
        END IF;
END IF;    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_desalocar_index_pre_pedido` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_desalocar_index_pre_pedido`(IN index_para_remover INT)
BEGIN
SET @proximo_index = (SELECT id_atual FROM pre_pedido_id WHERE id = 0);
    IF (index_para_remover >= @proximo_index OR index_para_remover <= 0) THEN
SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Este index ainda não existe e logo não está alocado';
ELSE
SET @existe = (SELECT COUNT(id) FROM pre_pedido_id WHERE id_atual = index_para_remover);
        IF (@existe) THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Este index já existe mas não está alocado';
ELSE
SET @quantidade_linhas = (SELECT quantidadelinhas FROM pre_pedido_id WHERE id=0);
            SET @index_ultimo = (SELECT id_ultimo FROM pre_pedido_id WHERE id = 0);            
            IF (@index_ultimo = (@quantidade_linhas - 1))  THEN
INSERT INTO pre_pedido_id (id, id_atual) values (@quantidade_linhas, index_para_remover);
UPDATE pre_pedido_id SET quantidadelinhas = (@quantidade_linhas + 1), id_ultimo = @quantidade_linhas WHERE id = 0;                
ELSE
UPDATE pre_pedido_id SET id_atual = index_para_remover WHERE id = (@index_ultimo + 1);
UPDATE pre_pedido_id SET id_ultimo = (@index_ultimo + 1) WHERE id = 0;
END IF;
        END IF;
END IF;    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_inserir_token_web_socket` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_inserir_token_web_socket`(IN idcliente INT, IN tokenwebsocket VARCHAR(128))
BEGIN
UPDATE usuario SET usuario.token_websocket = tokenwebsocket WHERE usuario.id_usuario = 
    (SELECT cliente.id_usuario FROM cliente WHERE cliente.id_cliente = idcliente);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_invalidar_sessao` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_invalidar_sessao`(IN id INT)
BEGIN
UPDATE usuario SET token_sessao = null WHERE usuario.id_usuario = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_recuperar_sessao_cliente_pra_pagamento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_recuperar_sessao_cliente_pra_pagamento`(IN idcomprador VARCHAR(128))
BEGIN
SELECT usuario.token_websocket FROM usuario 
    LEFT JOIN cliente ON usuario.id_usuario = cliente.id_usuario
    LEFT JOIN pre_pedido ON cliente.id_cliente = pre_pedido.id_cliente
    WHERE pre_pedido.token = idcomprador;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_validar_numero` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_validar_numero`(IN telefone VARCHAR(150), IN token VARCHAR(10))
BEGIN
DECLARE erro VARCHAR(50);
    DECLARE idusuario INT;
    SET erro = NULL;
    SET idusuario = (SELECT id_usuario FROM usuario WHERE usuario.nome = telefone AND usuario.token_cadastro = token);
IF (idusuario IS NOT NULL) THEN
UPDATE usuario SET  usuario.verificado = true WHERE usuario.id_usuario = idusuario;
ELSE
SET erro = 'Não foi possível executar esta ação';
END IF;
    SELECT erro;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_validar_sessao` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ALLOW_INVALID_DATES,ERROR_FOR_DIVISION_BY_ZERO,TRADITIONAL,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_validar_sessao`(IN id INT, IN token VARCHAR(150))
BEGIN
UPDATE usuario SET token_sessao = token WHERE usuario.id_usuario = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `utils_verificar_sessao` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `utils_verificar_sessao`(IN token1 VARCHAR(128))
BEGIN
SELECT id_usuario FROM usuario WHERE usuario.token_sessao = token1 INTO @index_usuario;
    SELECT @index_usuario id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-15 10:46:15
