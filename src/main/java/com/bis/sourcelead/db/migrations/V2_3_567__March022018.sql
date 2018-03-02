CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_REQUEST','ORG_TYPE','VARCHAR(50) NULL AFTER `ID`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_REQUEST','DEMO_DATE','DATE NULL AFTER `COUNTRY`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_REQUEST','DEMO_TIME','VARCHAR(50) NULL AFTER `DEMO_DATE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_REQUEST','DEMO_TIME_ZONE','BIGINT NULL AFTER `DEMO_TIME`');
ALTER TABLE `sourcelead`.`ORG_REQUEST` ADD CONSTRAINT `FK1_TIME_ZONE_ID`  FOREIGN KEY  (`DEMO_TIME_ZONE`)  REFERENCES  `sourcelead`.`TIMEZONE` (`ID`);

