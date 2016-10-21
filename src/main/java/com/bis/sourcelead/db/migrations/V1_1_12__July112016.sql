CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','DOC_FILE','MEDIUMBLOB NULL  AFTER `STATUS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','FILE_CONTENT_TYPE','VARCHAR(50) NULL  AFTER `DOC_FILE`');


CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','JOB_TITLE','VARCHAR(100) NULL  AFTER `USER_CODE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','LOCATION','TEXT NULL  AFTER `JOB_TITLE`');



CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','ORG_DESCRIPTION','TEXT NULL AFTER `FILE_CONTENT_TYPE`');

CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','PHONE','VARCHAR(30) NULL AFTER `ZIP_CODE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','FAX','VARCHAR(30) NULL AFTER `PHONE`');
  


ALTER TABLE `sourcelead`.`EMPLOYEE` 
ADD COLUMN `EMPLOYEE_ID` VARCHAR(20) NULL AFTER `USER_ID`;


ALTER TABLE `sourcelead`.`EMPLOYEE` 
ADD COLUMN `RATE` VARCHAR(20) NULL AFTER `UPDATED_BY`,
ADD COLUMN `RATE_PER` VARCHAR(20) NULL AFTER `RATE`,
ADD COLUMN `DAILY_HOURS` VARCHAR(20) NULL AFTER `RATE_PER`;
