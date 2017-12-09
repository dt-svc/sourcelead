CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','IS_COMPANY_ONBOARDED','VARCHAR(10) NULL');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','DBA','VARCHAR(500) NULL');


CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','BUSINESS_TYPE','BIGINT NULL');
ALTER TABLE `sourcelead`.`ORG_LOCATIONS` ADD CONSTRAINT `FK_ORG_LOCATIONS_BUSINESS_TYPE`  FOREIGN KEY  (`BUSINESS_TYPE`)  REFERENCES  `sourcelead`.`LOOKUP_DATA` (`ID`);


CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','FEDERAL_EIN','VARCHAR(100) NULL');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','DEPOSIT_SCHEDULE ','VARCHAR(100) NULL');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','FILLING_FORM ','VARCHAR(100) NULL');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','UNEMPLOYMENT_TAX ','VARCHAR(100) NULL');


INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Corporation', 'Corporation', 'Company Onboard Business type');
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Scorporation', 'Scorporation', 'Company Onboard Business type');
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Proprietorship', 'Proprietorship', 'Company Onboard Business type');
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Limited liability', 'Limited liability', 'Company Onboard Business type');


DROP TABLE IF EXISTS `sourcelead`.`ORGLOCATION_STATE_TAX`;
CREATE TABLE `sourcelead`.`ORGLOCATION_STATE_TAX` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  ORGLOCATION_ID BIGINT,
  STATE VARCHAR(500) DEFAULT NULL,
  STATE_UNEMPLOYMENT_TAX VARCHAR(100) DEFAULT NULL,
  STATE_TAX_ACCOUNT_NUMBER  VARCHAR(100) DEFAULT NULL,
  UUID VARCHAR(50) DEFAULT NULL,
  STATUS INT DEFAULT NULL,
  CREATED_DATE DATE,
  CREATED_BY BIGINT,
  UPDATED_DATE DATE,
  UPDATED_BY BIGINT,
  PRIMARY KEY (`ID`),
  FOREIGN KEY(ORGLOCATION_ID) REFERENCES `sourcelead`.`ORG_LOCATIONS`(ID),
  FOREIGN KEY(CREATED_BY) REFERENCES `sourcelead`.`USER`(ID),
  FOREIGN KEY(UPDATED_BY) REFERENCES `sourcelead`.`USER`(ID)
) ;


