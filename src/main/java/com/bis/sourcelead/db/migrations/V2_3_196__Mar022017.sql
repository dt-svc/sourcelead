#ReopenTimeSheets_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ReopenTimeSheet', 'Notification');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ManagerName', '{{ReopenTimeSheet.ManagerName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'EmployeeName', '{{ReopenTimeSheet.EmployeeName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('ReopenTimeSheet', 'Notification', 'ReopenTimeSheet', 'Used to notify to superUser for reopen timesheet','org','Request by {{ReopenTimeSheet.ManagerName}} for editing the timesheet of {{ReopenTimeSheet.EmployeeName}}','N', 'SLADMIN', 'com', 'in.com');



#Accept_TimeShet_Reopen_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('AcceptReopenTimeSheet', 'Notification');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ProjectName', '{{AcceptReopenTimeSheet.ProjectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'EmployeeName', '{{AcceptReopenTimeSheet.EmployeeName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('AcceptReopenTimeSheet', 'Notification', 'AcceptReopenTimeSheet', 'Used to notify to User about acceptance of reopen timesheet','org','Request accepted for editing {{AcceptReopenTimeSheet.EmployeeName}} Timesheet for {{AcceptReopenTimeSheet.ProjectName}}','N', 'SLADMIN', 'com', 'in.com');


#reject_TimeShet_Reopen_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectReopenTimeSheet', 'Notification');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ProjectName', '{{RejectReopenTimeSheet.ProjectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'EmployeeName', '{{RejectReopenTimeSheet.EmployeeName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('RejectReopenTimeSheet', 'Notification', 'RejectReopenTimeSheet', 'Used to notify to User about rejecteance of reopen timesheet','org','Request Rejected for editing {{RejectReopenTimeSheet.EmployeeName}} Timesheet for {{RejectReopenTimeSheet.ProjectName}}','N', 'SLADMIN', 'com', 'in.com');



#RejectMailFromClientToVendor
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RejectMailFromClientToVendor', 'Notification');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'applicantName', '{{RejectMailFromClientToVendor.applicantName}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('RejectMailFromClientToVendor', 'Notification', 'RejectMailFromClient_To_Vendor', 'Used to notify vendor about candidate rejection','org','{{RejectMailFromClientToVendor.applicantName}} got Rejected','N', 'SLADMIN', 'com', 'in.com');

CALL AlterTableAddColumn ('NOTIFICATIONS','REJECT_ACTION_LINK','TEXT NULL AFTER `ACTION_LINK`');
CALL AlterTableAddColumn ('NOTIFICATIONS','SPOC_UUID','VARCHAR(250) NULL');

