CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','USER_STATE','VARCHAR(50) NULL AFTER `IS_PRIMARY`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','PREVIOUS_USER_STATE','VARCHAR(50) NULL AFTER `USER_STATE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','USER_TYPE','VARCHAR(50) NULL AFTER `PREVIOUS_USER_STATE`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','USER_ROLE','TERMINATE_REASON','TEXT NULL AFTER `USER_TYPE`');

UPDATE `sourcelead`.`USER_ROLE` SET `USER_STATE`='ACTIVE' WHERE `ID`='1';
UPDATE `sourcelead`.`USER_ROLE` SET `USER_STATE`='ACTIVE' WHERE `ID`='2';
UPDATE `sourcelead`.`USER_ROLE` SET `USER_STATE`='ACTIVE' WHERE `ID`='3';

UPDATE `sourcelead`.`USER_ROLE` SET `USER_TYPE`='Admin' WHERE `ID`='1';
UPDATE `sourcelead`.`USER_ROLE` SET `USER_TYPE`='Admin' WHERE `ID`='2';
UPDATE `sourcelead`.`USER_ROLE` SET `USER_TYPE`='Employee' WHERE `ID`='3';
