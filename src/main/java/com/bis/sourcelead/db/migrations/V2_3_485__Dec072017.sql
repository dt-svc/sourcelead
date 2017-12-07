
CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','MEDICAL_CLEARENCE','VARCHAR(50) NULL AFTER `FILE_NAME`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','SECURITY_CLEARANCE','VARCHAR(50) NULL AFTER `MEDICAL_CLEARENCE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','OVER_TIME','VARCHAR(50) NULL AFTER `SECURITY_CLEARANCE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','BACKGROUNG_CHECK','VARCHAR(50) NULL AFTER `OVER_TIME`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','GENDER','VARCHAR(50) NULL AFTER `BACKGROUNG_CHECK`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','EXPERIENCE_LEVEL','BIGINT NULL AFTER `GENDER`');
ALTER TABLE `sourcelead`.`REQUIREMENT` ADD FOREIGN KEY FK1_EXPERIENCE_LEVEL_REQUIREMENT (EXPERIENCE_LEVEL) REFERENCES `sourcelead`.LOOKUP_DATA(ID);

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','CLIENT_MAX_RATE','VARCHAR(10) NULL AFTER `CLIENT_RATE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','REQUIREMENT','PROJECT_CLIENT_ID','BIGINT NULL AFTER `ORG_LOCATION_ID`');
ALTER TABLE `sourcelead`.`REQUIREMENT` ADD FOREIGN KEY FK1_PROJECT_CLIENT_ID_REQUIREMENT (PROJECT_CLIENT_ID) REFERENCES `sourcelead`.PROJECT_CLIENTS(ID);

INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'Level-1',  'Level-1', 'Experience Level', 'Level-1'); 
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'Level-2',  'Level-2', 'Experience Level', 'Level-2'); 
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'Level-3',  'Level-3', 'Experience Level', 'Level-3'); 
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'Level-3',  'Level-3', 'Experience Level', 'Level-3'); 
INSERT INTO `sourcelead`.`LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'Level-4',  'Level-4', 'Experience Level', 'Level-4'); 
 
