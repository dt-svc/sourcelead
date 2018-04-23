
#Email to project manager who is assigned to project while employee submit his timesheet
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToManagerAtTimesheetSubmission', 'Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'timeFrequency', '{{MailToManagerAtTimesheetSubmission.TimeFrequency}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Mail To Manager At Timesheet Submission', 'Email', 'Mail To Manager At Timesheet Submission', 'Used for Mail To Manager At Timesheet Submission', '{{MailToProjectManager.LoginUserFirstName}} {{MailToProjectManager.LoginUserLastName}} have submitted Timesheet', 'org', 'Best Regards,\n{{MailToProjectManager.LoginUserFirstName}} {{MailToProjectManager.LoginUserLastName}},\n{{MailToProjectManager.OrgName}}.', 'Hi {{MailToProjectManager.ManagerFirstName}} {{MailToProjectManager.ManagerLastName}},\n{{MailToProjectManager.LoginUserFirstName}} {{MailToProjectManager.LoginUserLastName}} have submitted timesheet for approval from {{MailToManagerAtTimesheetSubmission.TimeFrequency}}.','N','SLADMIN', 'com', 'in.com');


#Notification to project manager who is assigned to project while employee submit his timesheet
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToManagerAtTimesheetSubmission', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Mail To Manager At Timesheet Submission', 'Notification', 'Mail To Manager At Timesheet Submission', 'Used for Notification To Manager At Timesheet Submission', '{{MailToProjectManager.LoginUserFirstName}} {{MailToProjectManager.LoginUserLastName}} have submitted Timesheet', 'sourcelead', '', '{{MailToProjectManager.LoginUserFirstName}} {{MailToProjectManager.LoginUserLastName}} have submitted Timesheet','N','SLADMIN', 'com', 'in.com');



#Email to employee when timesheet approved
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimesheetApprovedByApprover', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Timesheet Approved By Approver', 'Email', 'Timesheet Approved By Approver', 'Used for Timesheet Approved Email', 'Timesheet Approved', 'org', 'Best Regards,\n{{MailToTeamMembers.LoginUserFirstName}} {{MailToTeamMembers.LoginUserLastName}},\n{{MailToTeamMembers.OrgName}}.', 'Hi {{MailToTeamMembers.EmployeeFirstName}} {{MailToTeamMembers.EmployeeLastName}},\nYour timesheet have been approved by {{MailToTeamMembers.ManagerFirstName}} {{MailToTeamMembers.ManagerLastName}} for the {{MailToManagerAtTimesheetSubmission.TimeFrequency}}.','N','SLADMIN', 'com', 'in.com');


#Notification to employee when timesheet approved
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimesheetApprovedByApprover', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Timesheet Approved By Approver', 'Notification', 'Timesheet Approved By Approver', 'Used for Timesheet Approved Notification', 'Timesheet approved of {{MailToManagerAtTimesheetSubmission.TimeFrequency}}', 'sourcelead', '', 'Timesheet approved of {{MailToManagerAtTimesheetSubmission.TimeFrequency}}','N','SLADMIN', 'com', 'in.com');



#Email to employee when timesheet rejected
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimesheetRejectedByApprover', 'Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectedReason', '{{TimesheetRejected.RejectedReason}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Timesheet Rejected By Approver', 'Email', 'Timesheet Rejected By Approver', 'Used for Timesheet Rejected Email', 'Timesheet Rejected', 'org', 'Best Regards,\n{{MailToTeamMembers.LoginUserFirstName}} {{MailToTeamMembers.LoginUserLastName}},\n{{MailToTeamMembers.OrgName}}.', 'Hi {{MailToTeamMembers.EmployeeFirstName}} {{MailToTeamMembers.EmployeeLastName}},\nYour timesheet has been rejected by {{MailToTeamMembers.ManagerFirstName}} {{MailToTeamMembers.ManagerLastName}} for the {{MailToManagerAtTimesheetSubmission.TimeFrequency}}.\nReason : {{TimesheetRejected.RejectedReason}}','N','SLADMIN', 'com', 'in.com');


#Notification to employee when timesheet rejected
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimesheetRejectedByApprover', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Timesheet Rejected By Approver', 'Notification', 'Timesheet Rejected By Approver', 'Used for Timesheet Rejected Notification', 'Timesheet Rejected of {{MailToManagerAtTimesheetSubmission.TimeFrequency}}', 'sourcelead', '', 'Timesheet Rejected of {{MailToManagerAtTimesheetSubmission.TimeFrequency}}','N','SLADMIN', 'com', 'in.com');
