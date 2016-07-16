
Bitbucket

    Teams
    Projects
    Repositories
    Snippets

    owner/repository
    Logged in as Prasannavadla9

    3

    pratap
    sourcelead

Source
Source Diff History
sourcelead / src / main / resources / migrations / V1_1_09__July082016.sql
Full commit
Blame Raw

 1
 2
 3
 4
 5
 6
 7
 8
 9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74

	



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

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Time Sheet Submission', 'Email', 'Time Sheet Submission', 'Used for Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'org', 'Best Regards,\n{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}}.', 'Hi {{TimeSheetSubmission.ApproverFirstName}} {{TimeSheetSubmission.ApproverLastName}},\n {{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted timesheet for approval from {{TimeSheetSubmission.TimeFrequency}}.\n Project Name : {{TimeSheetSubmission.ProjectName}}\n Client : {{TimeSheetSubmission.ClientName}}\n Invoice Frequency : {{TimeSheetSubmission.InvoiceFrequency}}','N','SLADMIN', 'slead', 'sleadIN');



#TimeSheetSubmission Notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimeSheetSubmission', 'Notification');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'firstName', '{{TimeSheetSubmission.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'lastName', '{{TimeSheetSubmission.LastName}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Time Sheet Submission', 'Notification', 'Time Sheet Submission', 'Used for Time Sheet Submission', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet', 'sourcelead', '', '{{TimeSheetSubmission.FirstName}} {{TimeSheetSubmission.LastName}} have submitted Timesheet','N','SLADMIN', 'slead', 'sleadIN');



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

UPDATE `sourcelead`.`USER` SET `USER_STATE`='ACTIVE' WHERE `ID`='1';
UPDATE `sourcelead`.`USER` SET `USER_STATE`='ACTIVE' WHERE `ID`='2';

INSERT INTO `sourcelead`.`ORG` (`ID`, `ORG_NAME`, `PRIMARY_CONTACT_USER_ID`, `WEBSITE`, `ORG_TYPE`, `ORG_CODE`, `STATUS`) VALUES ('1', 'Source Lead', '1', 'slead', 'Company', 'slead', 'ACTIVE');


INSERT INTO `sourcelead`.`ORG_LOCATIONS` (`ID`, `ORG_ID`, `PRIMARY_CONTACT_USER_ID`, `COUNTRY`, `LOCATION_CODE`, `STATUS`) VALUES ('1', '1', '1', 'India', 'sleadIN', 'ACTIVE');
INSERT INTO `sourcelead`.`ORG_LOCATIONS` (`ID`, `ORG_ID`, `PRIMARY_CONTACT_USER_ID`, `COUNTRY`, `LOCATION_CODE`, `STATUS`) VALUES ('2', '1', '2', 'United States', 'sleadUS', 'ACTIVE');

UPDATE `sourcelead`.`USER_ROLE` SET `ORG_LOCATIONS_ID`='1', `IS_PRIMARY`='1' WHERE `ID`='1';
UPDATE `sourcelead`.`USER_ROLE` SET `ORG_LOCATIONS_ID`='2', `IS_PRIMARY`='0' WHERE `ID`='2';
UPDATE `sourcelead`.`USER_ROLE` SET `ORG_LOCATIONS_ID`='1', `IS_PRIMARY`='1' WHERE `ID`='3';

    Blog
    Support
    Plans & pricing
    Documentation
    API
    Site status
    Version info
    Terms of service
    Privacy policy

    JIRA Software
    Confluence
    Bamboo
    SourceTree
    HipChat

Atlassian

