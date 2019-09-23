set foreign_key_checks = 0;
drop table IF EXISTS `webgeocities`;
set foreign_key_checks = 1;

CREATE TABLE `webgeocities` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `countryCode` varchar(2) COLLATE utf8_persian_ci DEFAULT NULL,
  `stateCode` varchar(20) COLLATE utf8_persian_ci DEFAULT NULL,
  `cityName` varchar(255) COLLATE utf8_persian_ci DEFAULT NULL,
  `cityNameAscii` varchar(255) COLLATE utf8_persian_ci DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `timezoneid` varchar(255) COLLATE utf8_persian_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `countryCode` (`countryCode`,`stateCode`,`cityName`)
);

alter table `webgeocities` convert to character set utf8 collate utf8_general_ci;
