
DROP TABLE IF EXISTS CUSTOMER;
CREATE TABLE `CUSTOMER` (
  `id` varchar(100) NOT NULL,
  `customerName` varchar(45) DEFAULT NULL,
  `customerAddress` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `zipcode` varchar(45) DEFAULT NULL,
  `autoIncrement` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`autoIncrement`,`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) 
