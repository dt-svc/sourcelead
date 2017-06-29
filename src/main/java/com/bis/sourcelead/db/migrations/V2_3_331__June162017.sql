

CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','ORG_NAME','VARCHAR(500) NULL');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','MODULES_ID','TEXT AFTER `ORG_NAME`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','ORG_LOCATION_ADDRESS','TEXT AFTER `MODULES_ID`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','WEB_SITE','VARCHAR(500) NULL AFTER `ORG_LOCATION_ADDRESS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','ABOUT_COMPANY','TEXT NULL AFTER `WEB_SITE`');

UPDATE `sourcelead`.`ORG_LOCATIONS` SET `MODULES_ID`='2,3,4' WHERE `ID`='1';

UPDATE `sourcelead`.`ORG_LOCATIONS` SET `ORG_NAME`='Source Lead' WHERE `ID`='1';


