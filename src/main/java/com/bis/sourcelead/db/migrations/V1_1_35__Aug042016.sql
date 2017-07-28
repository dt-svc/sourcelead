ALTER TABLE `sourcelead`.`ACTION_ROLES` 
DROP FOREIGN KEY `ACTION_ROLES_ibfk_5`;
ALTER TABLE `sourcelead`.`ACTION_ROLES` 
DROP COLUMN `MODULE_ID`,
DROP INDEX `MODULE_ID` ;


CALL sourcelead.AlterTableAddColumn ('sourcelead','TABS_ACTIONS','MODULE_ID','BIGINT NULL AFTER `TAB_ID`');
ALTER TABLE `sourcelead`.`TABS_ACTIONS` ADD FOREIGN KEY(MODULE_ID) REFERENCES MODULES(ID);


UPDATE `sourcelead`.`TABS_ACTIONS` SET `MODULE_ID`='1' WHERE `ID`='1';
UPDATE `sourcelead`.`TABS_ACTIONS` SET `MODULE_ID`='1' WHERE `ID`='2';
UPDATE `sourcelead`.`TABS_ACTIONS` SET `MODULE_ID`='3' WHERE `ID`='3';
UPDATE `sourcelead`.`TABS_ACTIONS` SET `MODULE_ID`='3' WHERE `ID`='4';
UPDATE `sourcelead`.`TABS_ACTIONS` SET `MODULE_ID`='3' WHERE `ID`='5';
UPDATE `sourcelead`.`TABS_ACTIONS` SET `MODULE_ID`='3' WHERE `ID`='6';
UPDATE `sourcelead`.`TABS_ACTIONS` SET `MODULE_ID`='1' WHERE `ID`='7';



CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','ATS','VARCHAR(5) NULL AFTER `ORG_DESCRIPTION`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','VMS','VARCHAR(5) NULL AFTER `ATS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','ORG','TIMESHEET','VARCHAR(5) NULL AFTER `VMS`');

UPDATE `sourcelead`.`ORG` SET `ATS`='Y', `VMS`='Y' WHERE `ID`='1';


INSERT INTO `sourcelead`.`TABS` (`ID`, `TAB_NAME`, `TAB_ORDER`) VALUES ('8', 'Jobs', '6');
INSERT INTO `sourcelead`.`TABS` (`ID`, `TAB_NAME`, `TAB_ORDER`) VALUES ('9', 'Vendors', '7');
INSERT INTO `sourcelead`.`TABS` (`ID`, `TAB_NAME`, `TAB_ORDER`) VALUES ('10', 'Candidates', '8');


INSERT INTO `sourcelead`.`TABS_ACTIONS` (`ID`, `ACTION_NAME`, `TAB_ID`, `MODULE_ID`) VALUES ('8', 'Jobs', '8', '2');
INSERT INTO `sourcelead`.`TABS_ACTIONS` (`ID`, `ACTION_NAME`, `TAB_ID`, `MODULE_ID`) VALUES ('9', 'Vendors', '9', '2');
INSERT INTO `sourcelead`.`TABS_ACTIONS` (`ID`, `ACTION_NAME`, `TAB_ID`, `MODULE_ID`) VALUES ('10', 'Candidates', '10', '2');




INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('8', '1', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('8', '2', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('8', '3', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('8', '4', 'Y', '1');

INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('9', '1', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('9', '2', 'N', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('9', '3', 'N', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('9', '4', 'Y', '1');

INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('10', '1', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('10', '2', 'N', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('10', '3', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('10', '4', 'Y', '1');

