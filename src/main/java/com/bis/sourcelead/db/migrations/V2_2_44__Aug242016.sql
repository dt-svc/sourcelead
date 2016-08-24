
UPDATE `sourcelead`.`TABS` SET `TAB_NAME`='Requirement' WHERE `ID`='8';


INSERT INTO `sourcelead`.`ROLES` (`ID`, `ROLE_NAME`, `DISPLAY_NAME`) VALUES ('5', 'ROLE_HR_MANAGER', 'HR Manager');
INSERT INTO `sourcelead`.`ROLES` (`ID`, `ROLE_NAME`, `DISPLAY_NAME`) VALUES ('6', 'ROLE_ACCOUNT_MANAGER', 'Account Manager');
INSERT INTO `sourcelead`.`ROLES` (`ID`, `ROLE_NAME`, `DISPLAY_NAME`) VALUES ('7', 'ROLE_SALES_MANAGER', 'Sales Manager');
INSERT INTO `sourcelead`.`ROLES` (`ID`, `ROLE_NAME`, `DISPLAY_NAME`) VALUES ('8', 'ROLE_RECRUITER', 'Recruiter');



#Dashboard Tab
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('1', '5', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('1', '6', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('1', '7', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('1', '8', 'Y', '1');

#Users Tab
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('2', '5', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('2', '6', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('2', '7', 'N', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('2', '8', 'N', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('2', '3', 'Y', '1');

#Projects Tab
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('3', '5', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('3', '6', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('3', '7', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('3', '8', 'N', '1');

#TimeSheet Tab
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('4', '5', 'N', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('4', '6', 'N', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('4', '7', 'N', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('4', '8', 'N', '1');

#Client Tab
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('7', '5', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('7', '6', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('7', '7', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('7', '8', 'N', '1');


#Requirment Tab
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('8', '5', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('8', '6', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('8', '7', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('8', '8', 'Y', '1');


#Vendor
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('9', '5', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('9', '6', 'N', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('9', '7', 'N', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('9', '8', 'N', '1');

#Candidates
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('10', '5', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('10', '6', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('10', '7', 'Y', '1');
INSERT INTO `sourcelead`.`ACTION_ROLES` (`ACTION_ID`, `ROLE_ID`, `IS_NOTIFY`, `ORG_ID`) VALUES ('10', '8', 'Y', '1');




CALL sourcelead.AlterTableAddColumn ('sourcelead','USER','REPORTING_MANAGER_ID','BIGINT NULL AFTER `STATUS`');
ALTER TABLE `sourcelead`.`USER` ADD FOREIGN KEY(REPORTING_MANAGER_ID) REFERENCES USER(ID);





UPDATE `sourcelead`.`TABS` SET `TAB_ORDER`='3' WHERE `ID`='7';
UPDATE `sourcelead`.`TABS` SET `TAB_ORDER`='4' WHERE `ID`='8';
UPDATE `sourcelead`.`TABS` SET `TAB_ORDER`='5' WHERE `ID`='9';
UPDATE `sourcelead`.`TABS` SET `TAB_ORDER`='6' WHERE `ID`='10';
UPDATE `sourcelead`.`TABS` SET `TAB_ORDER`='7' WHERE `ID`='3';
UPDATE `sourcelead`.`TABS` SET `TAB_ORDER`='8' WHERE `ID`='4';
UPDATE `sourcelead`.`TABS` SET `TAB_ID`='8', `TAB_ORDER`='8' WHERE `ID`='5';
UPDATE `sourcelead`.`TABS` SET `TAB_ID`='8', `TAB_ORDER`='8' WHERE `ID`='6';

