/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.8.3-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: livrosLivres
-- ------------------------------------------------------
-- Server version	11.8.3-MariaDB-0+deb13u1 from Debian

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `tbl_Autor`
--

DROP TABLE IF EXISTS `tbl_Autor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_Autor` (
  `IdAutor` int(11) NOT NULL AUTO_INCREMENT,
  `Ativo` bit(1) DEFAULT NULL,
  `Citacao` varchar(255) DEFAULT NULL,
  `Descricao` varchar(255) DEFAULT NULL,
  `Imagem` varchar(255) DEFAULT NULL,
  `Nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IdAutor`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_Autor`
--

LOCK TABLES `tbl_Autor` WRITE;
/*!40000 ALTER TABLE `tbl_Autor` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `tbl_Autor` VALUES
(1,0x01,'Eu fico louco!!','Christian Figueiredo começou no YouTube com quinze anos de idade, quando criou o canal \'Eu Fico Loko\'. Aos 18 anos de idade, fundou uma empresa audiovisual, mas depois vendeu sua parte na empresa, seguindo carreira com enfoque total ao YouTube','https://upload.wikimedia.org/wikipedia/commons/7/70/Christian_Figueiredo.png','Christian Figueiredo'),
(2,0x01,'Eu sou louco!!','Paulo Coelho de Souza (Rio de Janeiro, 24 de agosto de 1947) é um escritor, letrista, jornalista e compositor brasileiro. Ocupa a 21.ª cadeira da Academia Brasileira de Letras','https://ondertexts.com/img/biography/paulo-coelho.jpg','pedro paulo coelho');
/*!40000 ALTER TABLE `tbl_Autor` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `tbl_Cliente`
--

DROP TABLE IF EXISTS `tbl_Cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_Cliente` (
  `ClienteId` int(11) NOT NULL AUTO_INCREMENT,
  `Ativo` bit(1) NOT NULL,
  `Cpf` varchar(255) DEFAULT NULL,
  `Nome` varchar(255) NOT NULL,
  `Senha` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Endereco` varchar(255) DEFAULT NULL,
  `Imagem` varchar(255) DEFAULT NULL,
  `Telefone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ClienteId`),
  UNIQUE KEY `UKkqro8m8b6qvnd61kee0l9p1ta` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_Cliente`
--

LOCK TABLES `tbl_Cliente` WRITE;
/*!40000 ALTER TABLE `tbl_Cliente` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `tbl_Cliente` VALUES
(1,0x01,'123.123.123-21','Livros Livres oficial','hb#vC8TlNNTIXCR@','livroslivresanddogs@gmail.com',NULL,'asdas',NULL),
(2,0x01,'123.123.123-21','Livros Livres oficial','gXteF3v5jZSuOPTc','livroslivresanddogs2@gmail.com',NULL,NULL,NULL);
/*!40000 ALTER TABLE `tbl_Cliente` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `tbl_Funcionario`
--

DROP TABLE IF EXISTS `tbl_Funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_Funcionario` (
  `FuncionarioId` int(11) NOT NULL AUTO_INCREMENT,
  `Ativo` bit(1) NOT NULL,
  `Cpf` varchar(255) DEFAULT NULL,
  `Nome` varchar(255) NOT NULL,
  `Senha` varchar(255) NOT NULL,
  `Matricula` varchar(255) NOT NULL,
  PRIMARY KEY (`FuncionarioId`),
  UNIQUE KEY `UKjj2i4a2safrxy4dhnvxlnql7` (`Matricula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_Funcionario`
--

LOCK TABLES `tbl_Funcionario` WRITE;
/*!40000 ALTER TABLE `tbl_Funcionario` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `tbl_Funcionario` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `tbl_Livro`
--

DROP TABLE IF EXISTS `tbl_Livro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_Livro` (
  `idLivro` int(11) NOT NULL AUTO_INCREMENT,
  `Ativo` bit(1) DEFAULT NULL,
  `DataPublicacao` date DEFAULT NULL,
  `Descricao` varchar(255) DEFAULT NULL,
  `Editora` varchar(255) DEFAULT NULL,
  `Estoque` int(11) DEFAULT NULL,
  `Genero` varchar(255) DEFAULT NULL,
  `Imagem` varchar(255) DEFAULT NULL,
  `ISBN` varchar(255) NOT NULL,
  `Nome` varchar(255) NOT NULL,
  `Paginas` int(11) NOT NULL,
  `idAutor` int(11) DEFAULT NULL,
  PRIMARY KEY (`idLivro`),
  UNIQUE KEY `UKldbl1cgwwe009pwkq9g9fbkxw` (`ISBN`),
  KEY `FKg7mdkkwregufbgds0q01ueei` (`idAutor`),
  CONSTRAINT `FKg7mdkkwregufbgds0q01ueei` FOREIGN KEY (`idAutor`) REFERENCES `tbl_Autor` (`IdAutor`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_Livro`
--

LOCK TABLES `tbl_Livro` WRITE;
/*!40000 ALTER TABLE `tbl_Livro` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `tbl_Livro` VALUES
(1,0x01,'2016-12-12','Fenômeno do YouTube, com mais de 2,5 milhão de seguidores e mais de 1 bilhão de visualizações eu seus vídeos, o autor conta histórias reais e inéditas - algumas delas um tanto constrangedoras - sobre sua infância e adolescência.','Novas Páginas',198,'Youtube','https://m.media-amazon.com/images/I/812gguHHE8L._SL1433_.jpg','123456789','Eu fico loko!',115,1),
(2,0x01,'2016-12-12','Fenômeno do YouTube, com mais de 2,5 milhão de seguidores e mais de 1 bilhão de visualizações eu seus vídeos, o autor conta MAIS histórias reais e inéditas - algumas delas um tanto constrangedoras - sobre sua infância e adolescência.','Novas Páginas',198,'Youtube','https://m.media-amazon.com/images/I/71YtyHxlsfL._SL1437_.jpg','1234567810','Eu fico loko 2',115,1),
(3,0x01,'1987-12-12','O Diário de um Mago é um livro escrito pelo brasileiro Paulo Coelho. Foi lançado em 1987, publicado em 150 países e traduzido para 40 idiomas.','Rocco',0,'Filosofia','https://static.skeelo.com/remote/320/480/100/https://skoob.s3.amazonaws.com/livros/150/O_DIARIO_DE_UM_MAGO_1449190501150SK1449190501B.jpg','1234567811','O Diário de um Mago',120,2);
/*!40000 ALTER TABLE `tbl_Livro` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `tbl_LivroAutor`
--

DROP TABLE IF EXISTS `tbl_LivroAutor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_LivroAutor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAutor` int(11) DEFAULT NULL,
  `idLivro` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4insybx33hovx351bssjf113c` (`idAutor`),
  KEY `FK2ren8wssypbgwfd6yybagpkia` (`idLivro`),
  CONSTRAINT `FK2ren8wssypbgwfd6yybagpkia` FOREIGN KEY (`idLivro`) REFERENCES `tbl_Livro` (`idLivro`),
  CONSTRAINT `FK4insybx33hovx351bssjf113c` FOREIGN KEY (`idAutor`) REFERENCES `tbl_Autor` (`IdAutor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_LivroAutor`
--

LOCK TABLES `tbl_LivroAutor` WRITE;
/*!40000 ALTER TABLE `tbl_LivroAutor` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `tbl_LivroAutor` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `tbl_UsuarioEmprestimo`
--

DROP TABLE IF EXISTS `tbl_UsuarioEmprestimo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_UsuarioEmprestimo` (
  `idEmprestimo` int(11) NOT NULL AUTO_INCREMENT,
  `Ativo` bit(1) NOT NULL,
  `Data_Coleta` date DEFAULT NULL,
  `Data_Devolucao` date DEFAULT NULL,
  `Data_EstendidaDevolucao` date DEFAULT NULL,
  `Data_PrevistaDevolucao` date DEFAULT NULL,
  `Data_SolicitacaoEmprestimo` date DEFAULT NULL,
  `Status` enum('ACEITO','ADIADO','CANCELADO','CRIADO','FINALIZADO','FINALIZADO_ATRASADO','PEDIDO') DEFAULT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `idLivro` int(11) DEFAULT NULL,
  PRIMARY KEY (`idEmprestimo`),
  KEY `FK8kkcm5dr3v80u4j6krb0v9ran` (`idUsuario`),
  KEY `FKfg75s1u2sb57iiju4apsgk8te` (`idLivro`),
  CONSTRAINT `FK8kkcm5dr3v80u4j6krb0v9ran` FOREIGN KEY (`idUsuario`) REFERENCES `tbl_Cliente` (`ClienteId`),
  CONSTRAINT `FKfg75s1u2sb57iiju4apsgk8te` FOREIGN KEY (`idLivro`) REFERENCES `tbl_Livro` (`idLivro`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_UsuarioEmprestimo`
--

LOCK TABLES `tbl_UsuarioEmprestimo` WRITE;
/*!40000 ALTER TABLE `tbl_UsuarioEmprestimo` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `tbl_UsuarioEmprestimo` VALUES
(1,0x01,'2025-11-12',NULL,'2026-07-28','2026-12-02','2025-11-12','ADIADO',1,2),
(2,0x01,'2025-11-12',NULL,'2026-11-24','2026-12-02','2025-11-12','ADIADO',1,1),
(3,0x01,'2025-11-12',NULL,NULL,'2026-12-02','2025-11-12','CRIADO',1,3),
(4,0x01,'2025-11-12',NULL,NULL,'2025-09-02','2025-11-12','CRIADO',2,3),
(5,0x01,'2025-11-12',NULL,NULL,'2024-03-02','2025-11-12','CRIADO',2,1),
(6,0x01,'2025-11-12',NULL,NULL,'2024-03-02','2025-11-12','CRIADO',2,2),
(7,0x01,NULL,NULL,NULL,'2026-01-01','2025-11-13','PEDIDO',1,1);
/*!40000 ALTER TABLE `tbl_UsuarioEmprestimo` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping routines for database 'livrosLivres'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-11-13 16:01:16
