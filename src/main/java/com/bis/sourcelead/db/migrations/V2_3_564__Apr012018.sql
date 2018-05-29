#mamatha

#AssignEmployeeinProjecttask_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailtoAssignEmployeeinProjecttask', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Assignedmember', '{{MailtoAssignEmployeeinProjecttask.Assignedmember}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{MailtoAssignEmployeeinProjecttask.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginOrgName', '{{MailtoAssignEmployeeinProjecttask.LoginOrgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Priority', '{{MailtoAssignEmployeeinProjecttask.Priority}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Severity', '{{MailtoAssignEmployeeinProjecttask.Severity}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'TaskTitle', '{{MailtoAssignEmployeeinProjecttask.TaskTitle}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ProjectName', '{{MailtoAssignEmployeeinProjecttask.ProjectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'DueDate', '{{MailtoAssignEmployeeinProjecttask.DueDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Owner', '{{MailtoAssignEmployeeinProjecttask.Owner}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'link', '{{MailtoAssignEmployeeinProjecttask.link}}');
  
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('MailtoAssignEmployeeinProjecttask', 'Email', 'MailtoAssignEmployeeinProjecttask', 'The mail should forward when the user assigned task to particular member', 'New task Assigned', 'sourcelead', 'Best Regards, \n{{MailtoAssignEmployeeinProjecttask.LoginUser}}\n{{MailtoAssignEmployeeinProjecttask.LoginOrgName}}.', 'Dear {{MailtoAssignEmployeeinProjecttask.Assignedmember}},\n\nThis is to notify that you have been assigned to following task in {{MailtoAssignEmployeeinProjecttask.ProjectName}}\nTask Title : {{MailtoAssignEmployeeinProjecttask.TaskTitle}}\nPriority :{{MailtoAssignEmployeeinProjecttask.Priority}}\nSeverity : {{MailtoAssignEmployeeinProjecttask.Severity}}\nDue Date : {{MailtoAssignEmployeeinProjecttask.DueDate}}\nOwner :{{MailtoAssignEmployeeinProjecttask.Owner}} \n\n For more details login into the system Login \n{{MailtoAssignEmployeeinProjecttask.link}}', 'N', 'SLADMIN', 'com', 'in.com');

#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailtoAssignEmployeeinProjecttask', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('MailtoAssignEmployeeinProjecttask', 'Notification', 'MailtoAssignEmployeeinProjecttask', 'The mail should forward when the user assigned task to particular member', 'New task Assigned to you on {{MailtoAssignEmployeeinProjecttask.ProjectName}}', 'sourcelead', '', 'New task Assigned to you on {{MailtoAssignEmployeeinProjecttask.ProjectName}}','N','SLADMIN', 'com', 'in.com');


