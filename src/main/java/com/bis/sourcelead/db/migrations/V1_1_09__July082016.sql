
CALL AlterTableAddColumn ('EMPLOYEE','ORG_LOCATIONS_ID','BIGINT NULL AFTER `EMPLOYEE_STATE`');
ALTER TABLE `EMPLOYEE`  ADD FOREIGN KEY(ORG_LOCATIONS_ID) REFERENCES ORG_LOCATIONS(ID);


DROP TABLE IF EXISTS `ORG_RELATIONS`;
CREATE TABLE `ORG_RELATIONS` (
   ID BIGINT NOT NULL AUTO_INCREMENT,
   CLIENT_LOCATION_ID BIGINT DEFAULT NULL,
   LOCATION_ID BIGINT DEFAULT NULL,
   RELATION_TYPE varchar(150) DEFAULT NULL,
   STATUS VARCHAR(20),
   CREATED_DATE DATE,
   CREATED_BY VARCHAR(10),
   UPDATED_DATE DATE,
   UPDATED_BY VARCHAR(10),
   PRIMARY KEY (`ID`),
   KEY `ID` (`ID`),
   FOREIGN KEY(LOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID),
   FOREIGN KEY(CLIENT_LOCATION_ID) REFERENCES `ORG_LOCATIONS`(ID)
) ;


ALTER TABLE `PROJECT_MEMBERS` ADD COLUMN `PROJECT_END_DATE` DATE NULL DEFAULT NULL AFTER `PROJECT_ASSIGNED_DATE`;
ALTER TABLE `PROJECT_MEMBERS` ADD COLUMN `ROLE` VARCHAR(20) NULL DEFAULT NULL AFTER `PROJECT_END_DATE`;

ALTER TABLE `PROJECTS`
	ADD COLUMN `ORG_RELATION_ID` BIGINT NULL DEFAULT NULL AFTER `PROJECT_STATUS`,
	ADD FOREIGN KEY(ORG_RELATION_ID) REFERENCES ORG_RELATIONS(ID);
	
	
ALTER TABLE `PROJECTS` 
	DROP FOREIGN KEY `PROJECTS_ibfk_1`, 
	DROP COLUMN `PROJECT_MANAGER_USER_ID`,
	DROP INDEX `PROJECT_MANAGER_USER_ID`,
 
	DROP FOREIGN KEY `PROJECTS_ibfk_2`, 
	DROP COLUMN `CLIENT_ORG_ID`,
	DROP INDEX `CLIENT_ORG_ID` ;

	
RENAME TABLE `TASKS` TO `PROJECT_TASKS`;	
ALTER TABLE `PROJECT_TASKS`
	ADD COLUMN PROJECT_ID BIGINT DEFAULT NULL AFTER `DESCRIPTION`,
	ADD FOREIGN KEY(PROJECT_ID) REFERENCES `PROJECTS`(ID);



DROP TABLE IF EXISTS `PROJECT_APPROVER_HIERARCHY`;
CREATE TABLE `PROJECT_APPROVER_HIERARCHY` (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  PROJECT_ID BIGINT DEFAULT NULL,
  APPROVER_USER_ID BIGINT DEFAULT NULL,
  LEVEL VARCHAR(20) DEFAULT NULL,
  STATUS VARCHAR(20),
  CREATED_DATE DATE,
  CREATED_BY VARCHAR(10),
  UPDATED_DATE DATE,
  UPDATED_BY VARCHAR(10),
  PRIMARY KEY (`ID`),
  FOREIGN KEY(PROJECT_ID) REFERENCES PROJECTS(ID),
  FOREIGN KEY(APPROVER_USER_ID) REFERENCES USER(ID)
);	

ALTER TABLE `EMPLOYEE` ADD COLUMN `Invoice_Frequency` VARCHAR(20) NULL DEFAULT NULL AFTER `ORG_LOCATIONS_ID`;



#TimeSheetSubmission Email
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimeSheetSubmission', 'Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'firstName', '{{TimeSheetSubmission.FirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'lastName', '{{TimeSheetSubmission.LastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverFirstName', '{{TimeSheetSubmission.ApproverFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverLastName', '{{TimeSheetSubmission.ApproverLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'timeFrequency', '{{TimeSheetSubmission.TimeFrequency}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{TimeSheetSubmission.ProjectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientName', '{{TimeSheetSubmission.ClientName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'invoiceFrequency', '{{TimeSheetSubmission.InvoiceFrequency}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Time Sheet Submission', 'Email', 'Time Sheet Submission', 'Used for Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'org', 'Best Regards,\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}}.', 'Hi {{TimeSheetSubmission.ApproverFirstName}} {{TimeSheetSubmission.ApproverLastName}},\n {{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted timesheet for approval from {{TimeSheetSubmission.TimeFrequency}}.\n Project Name : {{TimeSheetSubmission.ProjectName}}\n Client : {{TimeSheetSubmission.ClientName}}\n Invoice Frequency : {{TimeSheetSubmission.InvoiceFrequency}}','N','SLADMIN', 'com', 'in.com');



#TimeSheetSubmission Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimeSheetSubmission', 'Notification');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'firstName', '{{TimeSheetSubmission.FirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'lastName', '{{TimeSheetSubmission.LastName}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Time Sheet Submission', 'Notification', 'Time Sheet Submission', 'Used for Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'sourcelead', '', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet','N','SLADMIN', 'com', 'in.com');



DROP TABLE IF EXISTS EMAIL_NOTIFICATION;
CREATE TABLE EMAIL_NOTIFICATION(
  `ID` BIGINT unsigned NOT NULL AUTO_INCREMENT,
  `EMAIL_TO` VARCHAR(150) DEFAULT NULL,
  `EMAIL_FORM` VARCHAR(150) DEFAULT NULL,
  `SUBJECT` text,
  `BODY` text,
  `DELIVERY_STATUS` VARCHAR(3) DEFAULT NULL,
  `CREATED_DATE` DATE DEFAULT NULL,
  `CREATED_BY` VARCHAR(8) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID` (`ID`)
);


DROP TABLE IF EXISTS NOTIFICATIONS;
CREATE TABLE NOTIFICATIONS(
  `ID` BIGINT unsigned NOT NULL AUTO_INCREMENT,
  `NOTIFICATION_SCENARIO_ID` BIGINT unsigned DEFAULT NULL,
  `NOTIFICATION_TO` BIGINT ,
  `NOTIFICATION_FORM` BIGINT ,
  `BODY` text,
  `MODE` VARCHAR(10) DEFAULT NULL,
  `ACTION_LINK` VARCHAR(500) DEFAULT NULL,
  `ORG_CODE` VARCHAR(15) DEFAULT NULL,
  `LOCATION_CODE` VARCHAR(110) DEFAULT NULL,
  `CREATED_DATE` DATE DEFAULT NULL,
  `CREATED_BY` VARCHAR(8) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY(NOTIFICATION_SCENARIO_ID) REFERENCES `EMAIL_SCENARIO`(ID),
  FOREIGN KEY(NOTIFICATION_TO) REFERENCES `USER`(ID),
  FOREIGN KEY(NOTIFICATION_FORM) REFERENCES `USER`(ID)
);

UPDATE `USER` SET `USER_STATE`='ACTIVE' WHERE `ID`='1';
UPDATE `USER` SET `USER_STATE`='ACTIVE' WHERE `ID`='2';

INSERT INTO `ORG` (`ID`, `ORG_NAME`, `PRIMARY_CONTACT_USER_ID`, `WEBSITE`, `ORG_TYPE`, `ORG_CODE`, `STATUS`) VALUES ('1', 'Source Lead', '1', 'com', 'Company', 'slead', 'ACTIVE');


INSERT INTO `ORG_LOCATIONS` (`ID`, `ORG_ID`, `PRIMARY_CONTACT_USER_ID`, `COUNTRY`, `LOCATION_CODE`, `STATUS`) VALUES ('1', '1', '1', 'India', 'sleadIN', 'ACTIVE');
INSERT INTO `ORG_LOCATIONS` (`ID`, `ORG_ID`, `PRIMARY_CONTACT_USER_ID`, `COUNTRY`, `LOCATION_CODE`, `STATUS`) VALUES ('2', '1', '2', 'United States', 'sleadUS', 'ACTIVE');

UPDATE `USER_ROLE` SET `ORG_LOCATIONS_ID`='1', `IS_PRIMARY`='1' WHERE `ID`='1';
UPDATE `USER_ROLE` SET `ORG_LOCATIONS_ID`='2', `IS_PRIMARY`='0' WHERE `ID`='2';
UPDATE `USER_ROLE` SET `ORG_LOCATIONS_ID`='1', `IS_PRIMARY`='1' WHERE `ID`='3';




CALL AlterTableAddColumn ('ENTITY_TYPE','ENTITY_TYPE','VARCHAR(50) NULL AFTER `ENTITY_NAME`');
CALL AlterTableAddColumn ('EMAIL_SCENARIO','ENTITY_TYPE','VARCHAR(50) NULL AFTER `SCENARIO_NAME`');


UPDATE `ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=1;
UPDATE `ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=2;
UPDATE `ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=3;
UPDATE `ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=4;
UPDATE `ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=5;
UPDATE `ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=6;

UPDATE `EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email' WHERE  `ID`=1;
UPDATE `EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email' WHERE  `ID`=2;
UPDATE `EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email' WHERE  `ID`=3;
UPDATE `EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email', `TEMPLATE_TYPE`='org' WHERE  `ID`=4;
UPDATE `EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email', `TEMPLATE_TYPE`='org' WHERE  `ID`=5;
UPDATE `EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email', `TEMPLATE_TYPE`='org' WHERE  `ID`=6;

UPDATE `CLASS_FIELD` SET `FIELD_NAME`='verificationCode', `DELIM_STR`='{{ForgotPassword.verificationCode}}' WHERE  `ID`=5;
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('1', 'loginURL', '{{Registration.LoginURL}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('2', 'resetPasswordURL', '{{ForgotPassword.ResetPasswordURL}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`='Welcome !!!\nHi {{Registration.FirstName}} {{Registration.LastName}},\nIt\'s great to see that you\'ve started accessing sourcelead\n{{Registration.LoginURL}}\nI hope you have a wonderful time with  If you have any questions or comments, feel free to reach out to us at support@com' WHERE  `ID`=1;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Hi {{ForgotPassword.FirstName}} {{ForgotPassword.LastName}},\nWe received a request to reset your password for your SourceLead account.\nVerification Code : {{ForgotPassword.verificationCode}} \nTo reset your password process simply click the link below\n {{ForgotPassword.ResetPasswordURL}}\nIf you didn\'t ask to change your password ,don\'t worry! Your password is still safe and access the sourcelead with existing credentials.' WHERE  `ID`=2;

#TimeSheetSubmission Email
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimeSheetSubmission', 'Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'firstName', '{{TimeSheetSubmission.FirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'lastName', '{{TimeSheetSubmission.LastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'approverFirstName', '{{TimeSheetSubmission.ApproverFirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'approverLastName', '{{TimeSheetSubmission.ApproverLastName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'timeFrequency', '{{TimeSheetSubmission.TimeFrequency}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'projectName', '{{TimeSheetSubmission.ProjectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'clientName', '{{TimeSheetSubmission.ClientName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'invoiceFrequency', '{{TimeSheetSubmission.InvoiceFrequency}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Time Sheet Submission', 'Email', 'Time Sheet Submission', 'Used for Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'org', 'Best Regards,\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}}.', 'Hi {{TimeSheetSubmission.ApproverFirstName}} {{TimeSheetSubmission.ApproverLastName}},\n {{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted timesheet for approval from {{TimeSheetSubmission.TimeFrequency}}.\n Project Name : {{TimeSheetSubmission.ProjectName}}\n Client : {{TimeSheetSubmission.ClientName}}\n Invoice Frequency : {{TimeSheetSubmission.InvoiceFrequency}}','N','SLADMIN', 'com', 'in.com');



#TimeSheetSubmission Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimeSheetSubmission', 'Notification');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('8', 'firstName', '{{TimeSheetSubmission.FirstName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('8', 'lastName', '{{TimeSheetSubmission.LastName}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Time Sheet Submission', 'Notification', 'Time Sheet Submission', 'Used for Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'sourcelead', '', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet','N','SLADMIN', 'com', 'in.com');



DROP TABLE IF EXISTS EMAIL_NOTIFICATION;
CREATE TABLE EMAIL_NOTIFICATION(
  `ID` BIGINT unsigned NOT NULL AUTO_INCREMENT,
  `EMAIL_TO` VARCHAR(150) DEFAULT NULL,
  `EMAIL_FORM` VARCHAR(150) DEFAULT NULL,
  `SUBJECT` text,
  `BODY` text,
  `DELIVERY_STATUS` VARCHAR(3) DEFAULT NULL,
  `CREATED_DATE` DATE DEFAULT NULL,
  `CREATED_BY` VARCHAR(8) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID` (`ID`)
);


DROP TABLE IF EXISTS NOTIFICATIONS;
CREATE TABLE NOTIFICATIONS(
  `ID` BIGINT unsigned NOT NULL AUTO_INCREMENT,
  `NOTIFICATION_SCENARIO_ID` BIGINT unsigned DEFAULT NULL,
  `NOTIFICATION_TO` BIGINT ,
  `NOTIFICATION_FORM` BIGINT ,
  `BODY` text,
  `MODE` VARCHAR(10) DEFAULT NULL,
  `ACTION_LINK` VARCHAR(500) DEFAULT NULL,
  `ORG_CODE` VARCHAR(15) DEFAULT NULL,
  `LOCATION_CODE` VARCHAR(110) DEFAULT NULL,
  `CREATED_DATE` DATE DEFAULT NULL,
  `CREATED_BY` VARCHAR(8) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY(NOTIFICATION_SCENARIO_ID) REFERENCES `EMAIL_SCENARIO`(ID),
  FOREIGN KEY(NOTIFICATION_TO) REFERENCES `USER`(ID),
  FOREIGN KEY(NOTIFICATION_FORM) REFERENCES `USER`(ID)
);
