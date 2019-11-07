CALL AlterTableAddColumn ('SUBMISSION_APPROVER_HIERARCHY','REOPENED_USER_ID','BIGINT NULL AFTER `STATUS`');
ALTER TABLE `SUBMISSION_APPROVER_HIERARCHY` ADD CONSTRAINT `SUBMISSION_APPROVER_HIERARCHY_FK3` FOREIGN KEY (`REOPENED_USER_ID`) REFERENCES `USER` (`ID`);

CALL AlterTableAddColumn ('SUBMISSION_APPROVER_HIERARCHY','REOPEN_REASON','TEXT NULL AFTER `REOPENED_USER_ID`');
CALL AlterTableAddColumn ('SUBMISSION_APPROVER_HIERARCHY','REOPEN_REJECTED_REASON','TEXT NULL AFTER `REOPEN_REASON`');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ReopenTimeSheet','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'superUserName', '{{ReopenTimeSheet.superUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerName', '{{ReopenTimeSheet.managerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeName', '{{ReopenTimeSheet.employeeName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{ReopenTimeSheet.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'frequencyType', '{{ReopenTimeSheet.frequencyType}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'frequencyPeriod', '{{ReopenTimeSheet.frequencyPeriod}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'totalHours', '{{ReopenTimeSheet.totalHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reason', '{{ReopenTimeSheet.reason}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'acceptURL', '{{ReopenTimeSheet.acceptURL}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectURL', '{{ReopenTimeSheet.rejectURL}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('ReopenTimeSheet','Email', 'ReopenTimeSheet', 'Used to reopen timesheet mail to super user' ,'Request from {{ReopenTimeSheet.managerName}}','org', 'Best Regards,\n{{ReopenTimeSheet.managerName}}.', 'Dear {{ReopenTimeSheet.superUserName}},\n\n Request for editing the timesheet of {{ReopenTimeSheet.employeeName}}.\n Project Name : {{ReopenTimeSheet.projectName}}\n Frequency : {{ReopenTimeSheet.frequencyType}}, {{ReopenTimeSheet.frequencyPeriod}}\n Total Hours : {{ReopenTimeSheet.totalHours}}\n Reason for Reopen : {{ReopenTimeSheet.reason}}\n Click on the following link for accepting the request and allow  to edit the timesheet.\n {{ReopenTimeSheet.acceptURL}} {{ReopenTimeSheet.rejectURL}}','N', 'SLADMIN', 'slead', 'sleadIN');


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('AcceptReopenTimeSheet','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'superUserName', '{{AcceptReopenTimeSheet.superUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerName', '{{AcceptReopenTimeSheet.managerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeName', '{{AcceptReopenTimeSheet.employeeName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{AcceptReopenTimeSheet.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginURL', '{{AcceptReopenTimeSheet.loginURL}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('AcceptReopenTimeSheet','Email', 'AcceptReopenTimeSheet', 'Used to accept reopen timesheet mail to requested user' ,'Request accepted for {{AcceptReopenTimeSheet.employeeName}} Timesheet','org', 'Best Regards,\n{{AcceptReopenTimeSheet.superUserName}}.', 'Dear {{AcceptReopenTimeSheet.managerName}},\n\n {{AcceptReopenTimeSheet.employeeName}} Timesheet can be edited for the {{AcceptReopenTimeSheet.projectName}}.\n For brief details login into the sourcelead system\n {{AcceptReopenTimeSheet.loginURL}}','N', 'SLADMIN', 'slead', 'sleadIN');

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectReopenTimeSheet','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'superUserName', '{{RejectReopenTimeSheet.superUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerName', '{{RejectReopenTimeSheet.managerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeName', '{{RejectReopenTimeSheet.employeeName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{RejectReopenTimeSheet.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reason', '{{RejectReopenTimeSheet.reason}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginURL', '{{RejectReopenTimeSheet.loginURL}}');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('RejectReopenTimeSheet','Email', 'RejectReopenTimeSheet', 'Used to reject reopen timesheet mail to requested user' ,'Request rejected for {{RejectReopenTimeSheet.employeeName}} Timesheet','org', 'Best Regards,\n{{RejectReopenTimeSheet.superUserName}}.', 'Dear {{RejectReopenTimeSheet.managerName}},\n\n {{RejectReopenTimeSheet.employeeName}} Timesheet can not be edited for the {{RejectReopenTimeSheet.projectName}}. The request was rejected due to following reason\n Reason : {{RejectReopenTimeSheet.reason}}\n For brief details login into the sourcelead system\n {{RejectReopenTimeSheet.loginURL}}','N', 'SLADMIN', 'slead', 'sleadIN');



