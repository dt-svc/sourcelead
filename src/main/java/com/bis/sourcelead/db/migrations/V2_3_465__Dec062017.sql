CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','IS_COMPANY_ONBOARDED','VARCHAR(10) NULL');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','DBA','VARCHAR(500) NULL');


CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','BUSINESS_TYPE','BIGINT NULL');
ALTER TABLE `sourcelead`.`ORG_LOCATIONS` ADD CONSTRAINT `FK_ORG_LOCATIONS_BUSINESS_TYPE`  FOREIGN KEY  (`BUSINESS_TYPE`)  REFERENCES  `sourcelead`.`LOOKUP_DATA` (`ID`);


INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Corporation', 'Corporation', 'Company Onboard Business type');
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Scorporation', 'Scorporation', 'Company Onboard Business type');
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Proprietorship', 'Proprietorship', 'Company Onboard Business type');
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`) VALUES ( 'Limited liability', 'Limited liability', 'Company Onboard Business type');
