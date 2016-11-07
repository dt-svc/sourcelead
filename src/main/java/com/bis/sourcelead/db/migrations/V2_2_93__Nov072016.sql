CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','FROM_EMAIL','VARCHAR(250) NULL AFTER `STATUS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','FROM_EMAIL_PASSWORD','VARCHAR(250) NULL AFTER `FROM_EMAIL`');

UPDATE `sourcelead`.`ORG_LOCATIONS` SET `FROM_EMAIL`='donotreply@businessintelli.com' WHERE `ID`='1';
UPDATE `sourcelead`.`ORG_LOCATIONS` SET `FROM_EMAIL_PASSWORD`='Donotreply@123' WHERE `ID`='1';