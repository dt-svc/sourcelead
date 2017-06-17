CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG_LOCATIONS','IS_PRIMARY','VARCHAR(10) NULL');

UPDATE `sourcelead`.`ORG_LOCATIONS` SET `IS_PRIMARY`='Y' WHERE `ID`='1';
