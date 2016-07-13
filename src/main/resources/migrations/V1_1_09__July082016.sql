
CALL sourcelead.AlterTableAddColumn ('sourcelead','EMPLOYEE','ORG_LOCATIONS_ID','BIGINT NULL AFTER `EMPLOYEE_STATE`');
ALTER TABLE `sourcelead`.`EMPLOYEE`  ADD FOREIGN KEY(ORG_LOCATIONS_ID) REFERENCES ORG_LOCATIONS(ID);


DROP TABLE IF EXISTS `sourcelead`.`ORG_RELATIONS`;
CREATE TABLE `sourcelead`.`ORG_RELATIONS` (
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
   FOREIGN KEY(LOCATION_ID) REFERENCES `sourcelead`.`ORG_LOCATIONS`(ID),
   FOREIGN KEY(CLIENT_LOCATION_ID) REFERENCES `sourcelead`.`ORG_LOCATIONS`(ID)
) ;


ALTER TABLE `sourcelead`.`PROJECT_MEMBERS` ADD COLUMN `PROJECT_END_DATE` DATE NULL DEFAULT NULL AFTER `PROJECT_ASSIGNED_DATE`;
ALTER TABLE `sourcelead`.`PROJECT_MEMBERS` ADD COLUMN `ROLE` VARCHAR(20) NULL DEFAULT NULL AFTER `PROJECT_END_DATE`;

ALTER TABLE `sourcelead`.`PROJECTS`
	ADD COLUMN `ORG_RELATION_ID` BIGINT NULL DEFAULT NULL AFTER `PROJECT_STATUS`,
	ADD FOREIGN KEY(ORG_RELATION_ID) REFERENCES ORG_RELATIONS(ID);
	
	
ALTER TABLE `sourcelead`.`PROJECTS` 
	DROP FOREIGN KEY `PROJECTS_ibfk_1`, 
	DROP COLUMN `PROJECT_MANAGER_USER_ID`,
	DROP INDEX `PROJECT_MANAGER_USER_ID`,
 
	DROP FOREIGN KEY `PROJECTS_ibfk_2`, 
	DROP COLUMN `CLIENT_ORG_ID`,
	DROP INDEX `CLIENT_ORG_ID` ;

	
RENAME TABLE `sourcelead`.`TASKS` TO `sourcelead`.`PROJECT_TASKS`;	
ALTER TABLE `sourcelead`.`PROJECT_TASKS`
	ADD COLUMN PROJECT_ID BIGINT DEFAULT NULL AFTER `DESCRIPTION`,
	ADD FOREIGN KEY(PROJECT_ID) REFERENCES `sourcelead`.`PROJECTS`(ID);



DROP TABLE IF EXISTS `sourcelead`.`PROJECT_APPROVER_HIERARCHY`;
CREATE TABLE `sourcelead`.`PROJECT_APPROVER_HIERARCHY` (
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

ALTER TABLE `sourcelead`.`EMPLOYEE` ADD COLUMN `Invoice_Frequency` VARCHAR(20) NULL DEFAULT NULL AFTER `ORG_LOCATIONS_ID`;
	

UPDATE `sourcelead`.`USER` SET `USER_STATE`='ACTIVE' WHERE `ID`='1';
UPDATE `sourcelead`.`USER` SET `USER_STATE`='ACTIVE' WHERE `ID`='2';

INSERT INTO `sourcelead`.`ORG` (`ID`, `ORG_NAME`, `PRIMARY_CONTACT_USER_ID`, `WEBSITE`, `ORG_TYPE`, `ORG_CODE`, `STATUS`) VALUES ('1', 'Source Lead', '1', 'sourcelead.com', 'Company', 'slead', 'ACTIVE');


INSERT INTO `sourcelead`.`ORG_LOCATIONS` (`ID`, `ORG_ID`, `PRIMARY_CONTACT_USER_ID`, `COUNTRY`, `LOCATION_CODE`, `STATUS`) VALUES ('1', '1', '1', 'India', 'sleadIN', 'ACTIVE');
INSERT INTO `sourcelead`.`ORG_LOCATIONS` (`ID`, `ORG_ID`, `PRIMARY_CONTACT_USER_ID`, `COUNTRY`, `LOCATION_CODE`, `STATUS`) VALUES ('2', '1', '2', 'United States', 'sleadUS', 'ACTIVE');

UPDATE `sourcelead`.`USER_ROLE` SET `ORG_LOCATIONS_ID`='1', `IS_PRIMARY`='1' WHERE `ID`='1';
UPDATE `sourcelead`.`USER_ROLE` SET `ORG_LOCATIONS_ID`='2', `IS_PRIMARY`='0' WHERE `ID`='2';
UPDATE `sourcelead`.`USER_ROLE` SET `ORG_LOCATIONS_ID`='1', `IS_PRIMARY`='1' WHERE `ID`='3';




CALL sourcelead.AlterTableAddColumn ('sourcelead','ENTITY_TYPE','ENTITY_TYPE','VARCHAR(50) NULL AFTER `ENTITY_NAME`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','EMAIL_SCENARIO','ENTITY_TYPE','VARCHAR(50) NULL AFTER `SCENARIO_NAME`');


UPDATE `sourcelead`.`ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=1;
UPDATE `sourcelead`.`ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=2;
UPDATE `sourcelead`.`ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=3;
UPDATE `sourcelead`.`ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=4;
UPDATE `sourcelead`.`ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=5;
UPDATE `sourcelead`.`ENTITY_TYPE` SET `ENTITY_TYPE`='Email' WHERE  `ID`=6;

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email' WHERE  `ID`=1;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email' WHERE  `ID`=2;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email' WHERE  `ID`=3;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email', `TEMPLATE_TYPE`='org' WHERE  `ID`=4;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email', `TEMPLATE_TYPE`='org' WHERE  `ID`=5;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `ENTITY_TYPE`='Email', `TEMPLATE_TYPE`='org' WHERE  `ID`=6;

UPDATE `sourcelead`.`CLASS_FIELD` SET `FIELD_NAME`='verificationCode', `DELIM_STR`='{{ForgotPassword.verificationCode}}' WHERE  `ID`=5;
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('1', 'loginURL', '{{Registration.LoginURL}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('2', 'resetPasswordURL', '{{ForgotPassword.ResetPasswordURL}}');

UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Welcome !!!\nHi {{Registration.FirstName}} {{Registration.LastName}},\nIt\'s great to see that you\'ve started accessing sourcelead\n{{Registration.LoginURL}}\nI hope you have a wonderful time with SourceLead. If you have any questions or comments, feel free to reach out to us at support@sourcelead.com' WHERE  `ID`=1;
UPDATE `sourcelead`.`EMAIL_SCENARIO` SET `BODY`='Hi {{ForgotPassword.FirstName}} {{ForgotPassword.LastName}},\nWe received a request to reset your password for your SourceLead account.\nVerification Code : {{ForgotPassword.verificationCode}} \nTo reset your password process simply click the link below\n {{ForgotPassword.ResetPasswordURL}}\nIf you didn\'t ask to change your password ,don\'t worry! Your password is still safe and access the sourcelead with existing credentials.' WHERE  `ID`=2;

#TimeSheetSubmission Email
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimeSheetSubmission', 'Email');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'firstName', '{{TimeSheetSubmission.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'lastName', '{{TimeSheetSubmission.LastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'approverFirstName', '{{TimeSheetSubmission.ApproverFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'approverLastName', '{{TimeSheetSubmission.ApproverLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'timeFrequency', '{{TimeSheetSubmission.TimeFrequency}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'projectName', '{{TimeSheetSubmission.ProjectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'clientName', '{{TimeSheetSubmission.ClientName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('7', 'invoiceFrequency', '{{TimeSheetSubmission.InvoiceFrequency}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Time Sheet Submission', 'Email', 'Time Sheet Submission', 'Used for Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'org', 'Best Regards,\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}}.', 'Hi {{TimeSheetSubmission.ApproverFirstName}} {{TimeSheetSubmission.ApproverLastName}},\n {{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted timesheet for approval from {{TimeSheetSubmission.TimeFrequency}}.\n Project Name : {{TimeSheetSubmission.ProjectName}}\n Client : {{TimeSheetSubmission.ClientName}}\n Invoice Frequency : {{TimeSheetSubmission.InvoiceFrequency}}','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



#TimeSheetSubmission Notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimeSheetSubmission', 'Notification');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('8', 'firstName', '{{TimeSheetSubmission.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('8', 'lastName', '{{TimeSheetSubmission.LastName}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Time Sheet Submission', 'Notification', 'Time Sheet Submission', 'Used for Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'sourcelead', '', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



DROP TABLE IF EXISTS sourcelead.EMAIL_NOTIFICATION;
CREATE TABLE `sourcelead`.EMAIL_NOTIFICATION(
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


DROP TABLE IF EXISTS sourcelead.NOTIFICATIONS;
CREATE TABLE `sourcelead`.NOTIFICATIONS(
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
  FOREIGN KEY(NOTIFICATION_SCENARIO_ID) REFERENCES `sourcelead`.`EMAIL_SCENARIO`(ID),
  FOREIGN KEY(NOTIFICATION_TO) REFERENCES `sourcelead`.`USER`(ID),
  FOREIGN KEY(NOTIFICATION_FORM) REFERENCES `sourcelead`.`USER`(ID)
);
