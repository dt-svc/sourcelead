

#TimeSheetSubmission Email
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimeSheetSubmission', 'Email');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'firstName', '{{TimeSheetSubmission.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'lastName', '{{TimeSheetSubmission.LastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverFirstName', '{{TimeSheetSubmission.ApproverFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverLastName', '{{TimeSheetSubmission.ApproverLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'timeFrequency', '{{TimeSheetSubmission.TimeFrequency}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{TimeSheetSubmission.ProjectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientName', '{{TimeSheetSubmission.ClientName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'invoiceFrequency', '{{TimeSheetSubmission.InvoiceFrequency}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Time Sheet Submission', 'Email', 'Time Sheet Submission', 'Used for Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'org', 'Best Regards,\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}}.', 'Hi {{TimeSheetSubmission.ApproverFirstName}} {{TimeSheetSubmission.ApproverLastName}},\n {{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted timesheet for approval from {{TimeSheetSubmission.TimeFrequency}}.\n Project Name : {{TimeSheetSubmission.ProjectName}}\n Client : {{TimeSheetSubmission.ClientName}}\n Invoice Frequency : {{TimeSheetSubmission.InvoiceFrequency}}','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



#TimeSheetSubmission Notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimeSheetSubmission', 'Notification');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'firstName', '{{TimeSheetSubmission.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'lastName', '{{TimeSheetSubmission.LastName}}');

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