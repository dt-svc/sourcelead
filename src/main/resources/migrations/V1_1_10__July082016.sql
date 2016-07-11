/*shivayogi*/
#TimesheetScheduler_Email
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimesheetScheduler', 'Email');

#class_field
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('9', 'firstName','{{TimesheetScheduler.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('9', 'lastName','{{TimesheetScheduler.LastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('9', 'weekFrequency','{{TimesheetScheduler.WeekFrequency}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Timesheet Scheduler', 'Email', 'Timesheet Scheduler', 'Used for Timesheet Scheduler', 'You need to update timesheet', 'sourcelead', 'Best Regards SourceLead', 'Hi{{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\n  You need to update the timesheet of {{TimesheetScheduler.WeekFrequency}}.So update as soon as possible', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


#TimesheetScheduler_Notification
#entity_type
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimesheetScheduler', 'Notification');

#class_field
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('10', 'firstName', '{{TimesheetScheduler.FirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('10', 'lastName', '{{TimesheetScheduler.LastName}}');

#email_scenario
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`,`TEMPLATE_TYPE`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Timesheet Scheduler', 'Notification', 'Timesheet Scheduler', 'Notification for Timesheet Scheduler', 'You need to update timesheet','sourcelead','N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');



/*shivayogi2*/
#TimesheetApproved_Email
#entity_type
INSERT INTO `sourcelead`.`entity_type` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimesheetApproved', 'Email');

#class_field
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('11', 'firstName', '{{TimesheetApproved.FirstName}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('11', 'lastName', '{{TimesheetApproved.LastName}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('11', 'approverFirstName', '{{TimesheetApproved.ApproverFirstName}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('11', 'approverLastName', '{{TimesheetApproved.ApproverLastName}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('11', 'weekFrequency', '{{TimesheetApproved.WeekFrequency}}');

#email_scenario
INSERT INTO `sourcelead`.`email_scenario` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Timesheet Approved', 'Email', 'Timesheet Approved', 'Used for Timesheet Approved Email', 'Timesheet approved', 'org', '{{TimesheetApproved.ApproverFirstName}}{{TimesheetApproved.ApproverLastName}}', 'Hi {{TimesheetScheduler.FirstName}}{{TimesheetScheduler.LastName}},\n Your timesheet have been approved by {{TimesheetApproved.ApproverFirstName}}{{TimesheetApproved.ApproverLastName}}  for the {{TimesheetApproved.WeekFrequency}}', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');





#Timesheet Approved_Notification
#entity_type
INSERT INTO `sourcelead`.`entity_type` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimesheetApproved', 'Notification');

#class_field
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('12', 'weekFrequency', '{{TimesheetApproved.WeekFrequency}}');


#email_scenario
INSERT INTO `sourcelead`.`email_scenario` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Timesheet Approved', 'Notification', 'Timesheet Approved', 'Notification for Timesheet Approved', 'org', 'Timesheet approved of {{TimesheetApproved.WeekFrequency}}', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


/*shivayogi kadagad3*/
#TimesheetApproved_Email
#entity_type
INSERT INTO `sourcelead`.`entity_type` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimesheetRejected', 'Email');

#class_field
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('13', 'firstName', '{{TimesheetRejected.FirstName}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('13', 'lastName', '{{TimesheetRejected.LastName}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('13', 'rejecteduserfirstName', '{{TimesheetRejected.rejecteduserFirstName}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('13', 'rejecteduserlastName', '{{TimesheetRejected.rejecteduserLastName}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('13', 'weekFrequency', '{{TimesheetRejected.WeekFrequency}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('13', 'projectName', '{{TimesheetRejected.ProjectName}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('13', 'reason', '{{TimesheetRejected.Reason}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('13', 'ApproverFirstName', '{{TimesheetRejected.ApproverFirstName}}');
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('13', 'ApproverLastName', '{{TimesheetRejected.ApproverLastName}}');



#email_scenario
INSERT INTO `sourcelead`.`email_scenario` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('TimesheetRejected', 'Email', 'TimesheetRejected', 'Used for Timesheet Rejected', 'Timesheet Rejected', 'org', 'Best Regards, \n {{TimesheetRejected.ApproverFirstName}} {{TimesheetRejected.ApproverLastName}}', 'Hi {{TimesheetRejected.FirstName}} {{TimesheetRejected.FirstName}},\n Your timesheet has been rejected by {{TimesheetRejected.rejecteduserFirstName}} {{TimesheetRejected.rejecteduserLastName}} of {{TimesheetRejected.WeekFrequency}}  for the {{TimesheetRejected.ProjectName}} {{TimesheetRejected.Reason}}.', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');






#Timesheet Approved_Notification
#entity_type
INSERT INTO `sourcelead`.`entity_type` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('TimesheetRejected','Notification');

#class_field
INSERT INTO `sourcelead`.`class_field` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ('14', 'weekFrequency', '{{TimesheetRejected.WeekFrequency}}');

#email_scenario
INSERT INTO `sourcelead`.`email_scenario` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('Timesheet Rejected', 'Notification', 'Timesheet Rejected', 'Notification for Timesheet Rejected', 'org', 'Timesheet approved of {{TimesheetRejected.WeekFrequency}}', 'N', 'SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


