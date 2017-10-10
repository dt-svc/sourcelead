#Rejected Leave Email
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('RejectedLeave','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeeName', '{{RejectedLeave.EmployeeName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{RejectedLeave.ApproverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'leaveTypeName', '{{RejectedLeave.LeaveTypeName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'fromDate', '{{RejectedLeave.FromDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'toDate', '{{RejectedLeave.ToDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reason', '{{RejectedLeave.Reason}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrg', '{{RejectedLeave.LoginOrg}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Rejected Leave','Email', 'Rejected Leave', 'Used for Rejected Leave', 'Leave got rejected','org', 'Best Regards,\n{{RejectedLeave.ApproverName}}.', 'Dear {{RejectedLeave.EmployeeName}} \nWe are regret to inform you,your leave  got rejected  for the requested period. \nLeave Type : {{RejectedLeave.LeaveTypeName}} \nDuration : {{RejectedLeave.FromDate}} - {{RejectedLeave.ToDate}}. \nRejected Reason : {{RejectedLeave.Reason}}','N', 'SLADMIN', 'slead', 'sleadIN');

#Rejected Leave Notification

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectedLeaveNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('RejectedLeaveNotification', 'Notification', 'RejectedLeaveNotification', 'Send mail to RejectedLeaveNotification','org',' Leave got rejected for {{RejectedLeave.FromDate}} TO {{RejectedLeave.ToDate}}  duration.','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
 
#Approver Leave Email 

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ApproverLeave','Email');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeeName', '{{ApproverLeave.EmployeeName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverName', '{{ApproverLeave.ApproverName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'fromDate', '{{ApproverLeave.FromDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'toDate', '{{ApproverLeave.ToDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginOrg', '{{ApproverLeave.LoginOrg}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Approver Leave','Email', 'Approver Leave', 'Used for Approver Leave', 'Leave got approved','org', 'Best Regards,\n{{ApproverLeave.ApproverName}}.', 'Dear {{ApproverLeave.EmployeeName}} \nWe are happy to inform you,your leave {{ApproverLeave.FromDate}} - {{ApproverLeave.ToDate}} got approved.','N', 'SLADMIN', 'slead', 'sleadIN');

#Approver Leave Notification

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ApproverLeaveNotification', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ApproverLeaveNotification', 'Notification', 'ApproverLeaveNotification', 'Send mail to ApproverLeaveNotification','org',' Leave got approved for {{ApproverLeave.FromDate}} TO {{ApproverLeave.ToDate}}  duration.','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
 