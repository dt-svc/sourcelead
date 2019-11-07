#termination mail to employee
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('employeeTermination', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Employee Name', '{{employeeTermination.EmployeeName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Project Name', '{{employeeTermination.ProjectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Project StartDate', '{{employeeTermination.ProjectStartDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Project EndDate', '{{employeeTermination.ProjectEndDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Manager', '{{employeeTermination.Manager}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{employeeTermination.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginOrg', '{{employeeTermination.LoginOrg}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Employee Termination','Email', 'Employee Termination', 'notify termination of employee', '{{employeeTermination.EmployeeName}} your project have been end','org', '\nBest Regards,\n{{employeeTermination.LoginUser}}\n{{employeeTermination.LoginOrg}}.', 'Dear {{employeeTermination.EmployeeName}},\n Your {{employeeTermination.ProjectName}} project have been end.\n The brief details of the project are following,\n Project StartDate : {{employeeTermination.ProjectStartDate}}\n Project EndDate : {{employeeTermination.ProjectEndDate}}\n Manager : {{employeeTermination.Manager}}','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('employeeTermination', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Employee Termination', 'Notification', 'Employee Termination', 'notification to employee about termination', '{{employeeTermination.EmployeeName}} your project have been end', 'sourcelead', '', '{{employeeTermination.EmployeeName}} your project have been end','N','SLADMIN', 'com', 'in.com');


			
			
#Employee Termination Mail To Manager
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('employeeTerminationmailtomanager', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Employee Name', '{{employeeTerminationmailtomanager.EmployeeName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Manager', '{{employeeTerminationmailtomanager.Manager}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{employeeTerminationmailtomanager.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Reason', '{{employeeTerminationmailtomanager.Reason}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Employee Termination Mail To Manager','Email', 'Employee Termination Mail To Manager', 'notify termination of employee to manager', '{{employeeTerminationmailtomanager.EmployeeName}} have been terminated from the project','org', '\nBest Regards,\n{{employeeTerminationmailtomanager.LoginUser}}.', 'Dear {{employeeTerminationmailtomanager.Manager}},\n {{employeeTerminationmailtomanager.EmployeeName}} have been terminated from the project, Due to following reason\n Reason : {{employeeTerminationmailtomanager.Reason}}','N', 'SLADMIN', 'slead', 'sleadIN');

#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('employeeTerminationmailtomanager', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Employee Termination Mail To Manager', 'Notification', 'Employee Termination Mail To Manager', 'notification to manager about employee termination', '{{employeeTerminationmailtomanager.EmployeeName}} have been terminated from the project', 'sourcelead', '', '{{employeeTerminationmailtomanager.EmployeeName}} have been terminated from the project','N','SLADMIN', 'com', 'in.com');

		
			
#Reassign mail to employee
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('employeeReassign', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Employee Name', '{{employeeReassign.EmployeeName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Project Name', '{{employeeReassign.ProjectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Project StartDate', '{{employeeReassign.ProjectStartDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Project EndDate', '{{employeeReassign.ProjectEndDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Manager', '{{employeeReassign.Manager}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{employeeReassign.LoginUser}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Employee Reassign','Email', 'Employee Reassign', 'notify reassign of employee', 'You have reassigned for the project {{employeeReassign.ProjectName}}','org', '\nBest Regards,\n{{employeeReassign.LoginUser}}', 'Dear {{employeeReassign.EmployeeName}},\n You are reassigned for the project {{employeeReassign.ProjectName}}.\n The brief details of the project are following,\n Project StartDate : {{employeeReassign.ProjectStartDate}}\n Project EndDate : {{employeeReassign.ProjectEndDate}}\n Manager : {{employeeReassign.Manager}}','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('employeeReassign', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('Employee Reassign', 'Notification', 'Employee Reassign', 'notification to employee about reassign', 'You have reassigned for the project {{employeeReassign.ProjectName}}', 'sourcelead', '', 'You have reassigned for the project {{employeeReassign.ProjectName}}','N','SLADMIN', 'com', 'in.com');

