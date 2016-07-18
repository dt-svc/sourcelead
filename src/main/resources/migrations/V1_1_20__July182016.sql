CALL sourcelead.AlterTableAddColumn ('sourcelead','ROLES','DISPLAY_NAME','VARCHAR(50) UNIQUE NULL AFTER `ROLE_NAME`');


UPDATE `sourcelead`.`ROLES` SET `DISPLAY_NAME`='Administrator' WHERE `ID`='1';
UPDATE `sourcelead`.`ROLES` SET `DISPLAY_NAME`='User' WHERE `ID`='2';
UPDATE `sourcelead`.`ROLES` SET `DISPLAY_NAME`='Approver' WHERE `ID`='3';
