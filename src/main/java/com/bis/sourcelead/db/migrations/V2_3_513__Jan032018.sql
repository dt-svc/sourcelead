DROP TABLE IF EXISTS `sourcelead`.`CONNECTIONS`; 
CREATE TABLE `sourcelead`.`CONNECTIONS` (
  `ID` bigint(20) NOT NULL,
  `FROM_UID` bigint(20) DEFAULT NULL,
  `TO_UID` bigint(20) DEFAULT NULL,
  `STATUS` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_connections` (`FROM_UID`),
  KEY `FK_connections1` (`TO_UID`),
  CONSTRAINT `FK_connections` FOREIGN KEY (`FROM_UID`) REFERENCES `user` (`ID`),
  CONSTRAINT `FK_connections1` FOREIGN KEY (`TO_UID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8