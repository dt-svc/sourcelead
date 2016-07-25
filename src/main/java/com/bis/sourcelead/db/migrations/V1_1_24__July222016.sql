

CALL sourcelead.AlterTableAddColumn ('sourcelead','PROJECTS','IS_MAIL_TO_TEAM_MEMBER','VARCHAR(3) NULL AFTER `PROJECT_STATUS`');
CALL sourcelead.AlterTableAddColumn ('sourcelead','PROJECTS','IS_MAIL_TO_PROJECT_MANAGER','VARCHAR(3) NULL AFTER `IS_MAIL_TO_TEAM_MEMBER`');


#Email to team members who are assigned to project
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToTeamMembers', 'Email');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserFirstName', '{{MailToTeamMembers.LoginUserFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUserLastName', '{{MailToTeamMembers.LoginUserLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'managerFirstName', '{{MailToTeamMembers.ManagerFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'managerLastName', '{{MailToTeamMembers.ManagerLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeeFirstName', '{{MailToTeamMembers.EmployeeFirstName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeeLastName', '{{MailToTeamMembers.EmployeeLastName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{MailToTeamMembers.ProjectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'orgName', '{{MailToTeamMembers.OrgName}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES ('Mail To Team Members', 'Email', 'Mail To Team Members', 'Used for Mail To Team Members', 'New Project Assigned', 'org', 'Best Regards,\n{{MailToTeamMembers.LoginUserFirstName}} {{MailToTeamMembers.LoginUserLastName}},\n{{MailToTeamMembers.OrgName}}.', 'Hi {{MailToTeamMembers.EmployeeFirstName}} {{MailToTeamMembers.EmployeeLastName}},\n {{MailToTeamMembers.LoginUserFirstName}} {{MailToTeamMembers.LoginUserLastName}} assigned you as a team member for the {{MailToTeamMembers.ProjectName}} and your project manager is {{MailToTeamMembers.ManagerFirstName}} {{MailToTeamMembers.ManagerLastName}}.','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');
